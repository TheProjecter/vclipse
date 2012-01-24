package org.vclipse.configscan.impl.model;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;
import org.eclipse.ui.progress.IElementCollector;
import org.eclipse.xtext.ui.util.ResourceUtil;
import org.vclipse.configscan.ConfigScanImageHelper;
import org.vclipse.configscan.ConfigScanPlugin;
import org.vclipse.configscan.IConfigScanImages;
import org.vclipse.configscan.IConfigScanRemoteConnections.RemoteConnection;
import org.vclipse.configscan.IConfigScanReverseXmlTransformation;
import org.vclipse.configscan.IConfigScanRunner;
import org.vclipse.configscan.IConfigScanXMLProvider;
import org.vclipse.configscan.ITestObjectFilter;
import org.vclipse.configscan.utils.DocumentUtility;
import org.vclipse.configscan.utils.TestCaseUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.sap.conn.jco.JCoException;

public class TestRunAdapter implements IDeferredWorkbenchAdapter {

	// names for options
	public static final String SKIP_MATERIAL_TESTS = "SkipMaterialTests";
	public static final String KBOBJECT = "kbobject";
	public static final String RTV = "rtv";
	
	@Inject
	private ConfigScanImageHelper imageHelper;
	
	@Inject
	private DocumentUtility documentUtility;
	
	@Inject
	private IConfigScanReverseXmlTransformation reverseXmlTransformation;
	
	@Inject
	private IConfigScanRunner runner;
	
	@Inject
	private TestCaseUtility testCaseUtility;
	
	@Inject
	private ITestObjectFilter filter;
	
	private RemoteConnection connection;
	
	private EObject testModel;
	
	private IConfigScanXMLProvider xmlProvider;
	
	private TestCase testCase;
	
	private Document inputDocument;
	
	private Document logDocument;
	
	private Map<String, Object> options;
	
	private Map<Element, URI> input2Uri;
	
	private Map<Element, Element> input2Log;
	
	public TestRunAdapter() {
		options = Maps.newHashMap();
		input2Uri = Maps.newHashMap();
		input2Log = Maps.newHashMap();
	}
	
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public TestCase getTestCase() {
		return testCase;
	}
	
	public void setTestModel(EObject testModel) {
		this.testModel = testModel;
	}
	
	public void setFilter(ITestObjectFilter filter) {
		this.filter = filter;
	}
	
	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}
	
	public void setXmlProvider(IConfigScanXMLProvider provider) {
		this.xmlProvider = provider;
	}
	
	public void setConnection(RemoteConnection connection) {
		this.connection = connection;
	}
	
	public RemoteConnection getConnection() {
		return connection;
	}

	public EObject getTestModel() {
		return testModel;
	}
	
	public Document getLogDocument() {
		return logDocument;
	}
	
	public Document getInputDocument() {
		return inputDocument;
	}
	
	public Object[] getChildren(Object object) {
		return testCase.getChildren().toArray();
	}
	
	public Map<Element, URI> getInputUri() {
		return input2Uri;
	}
	
	public Map<Element, Element> getInputLog() {
		return input2Log;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return imageHelper.getImageDescriptor(IConfigScanImages.TESTS);
	}

	public String getLabel(Object object) {
		return testCase.getTitle();
	}

	public Object getParent(Object object) {
		return testCase.getParent();
	}

	public void fetchDeferredChildren(Object object, IElementCollector collector, IProgressMonitor monitor) {
		if(testCase.getChildren().isEmpty()) {
			monitor.beginTask("Running tests for " + testModel.eResource().getURI().lastSegment() + " and " + connection.getDescription() + " connection", IProgressMonitor.UNKNOWN);

			input2Uri = Maps.newHashMap();
			inputDocument = xmlProvider.transform(testModel, filter, input2Uri);

			if(monitor.isCanceled()) {
				monitor.done();
				return;
			}
			String parseResult = documentUtility.parse(inputDocument);
			String materialNumber = xmlProvider.getMaterialNumber(testModel);
			
			try {
				if(monitor.isCanceled()) {
					monitor.done();
					return;
				}
				String result = runner.execute(parseResult, connection, materialNumber, ResourceUtil.getFile(testModel.eResource()));
				logDocument = documentUtility.parse(result);
				
				if(monitor.isCanceled()) {
					monitor.done();
					return;
				}
				input2Log = reverseXmlTransformation.computeConfigScanMap(logDocument, inputDocument);
				Node logSession = documentUtility.getLogSession(logDocument);
				if(logSession != null) {
					testCase.addTestCase(
							testCaseUtility.createTestCase(
									(Element)logSession, testCase, documentUtility, options, input2Uri, input2Log));
				}
			} catch (JCoException exception) {
				ConfigScanPlugin.log(exception.getMessage(), IStatus.ERROR);
			} catch (CoreException exception) {
				ConfigScanPlugin.log(exception.getMessage(), IStatus.ERROR);
			}
		}
		
		if(monitor.isCanceled()) {
			monitor.done();
			return;
		}
		for(TestCase childTestCase : testCase.getChildren()) {
			collector.add(childTestCase, monitor);
		}
		monitor.done();
	}
	
	public boolean isContainer() {
		return true;
	}

	public ISchedulingRule getRule(Object object) {
		// not used
		return null;
	}
}
