package org.vclipse.configscan.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.vclipse.configscan.IConfigScanXMLProvider;
import org.vclipse.configscan.ITestObjectFilter;
import org.vclipse.configscan.utils.DocumentUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

public abstract class AbstractConfigScanXmlAction implements IObjectActionDelegate {

	public static final String CONFIGSCAN_EXTENSION = "xml";

	protected IStructuredSelection selection;
	
	@Inject 
	protected IConfigScanXMLProvider configScanXMLProvider;
	
	@Inject
	protected DocumentUtility documentUtility;
	
	@Inject
	protected ITestObjectFilter filter;
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if(selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection)selection;
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		
	}
	
	@Override
	public void run(IAction action) {
		final String extension = getFileExtension();
		Job convertJob = new Job(extension + " file to ConfigScan XML file") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Converting " + extension + " file to ConfigScan XML file", IProgressMonitor.UNKNOWN);
				Iterator<?> iterator = ((IStructuredSelection)selection).iterator();
				IFile firstElement = null;
				while(iterator.hasNext()) {
					Object object = iterator.next();
					if(object instanceof IFile) {
						IFile file = (IFile)object;
						if(firstElement == null) {
							firstElement = file;
						}
						Resource resource = new XtextResourceSet().getResource(URI.createURI(file.getLocationURI().toString()), true);
						if (resource == null) {
							throw new IllegalArgumentException("resource null");
						}
						EList<EObject> contents = resource.getContents();
						if (contents.size()==0) {
							throw new IllegalArgumentException("no contents");
						}
						EObject model = contents.get(0);
						testProperties(model);
						URI xmlFileExtension = resource.getURI().appendFileExtension(CONFIGSCAN_EXTENSION);
						try {
							OutputStream outputStream = new ExtensibleURIConverterImpl().createOutputStream(xmlFileExtension);
							Document doc = configScanXMLProvider.transform(model, filter, new HashMap<Element, URI>(), Maps.newHashMap());
							String output = documentUtility.serialize(doc);
							outputStream.write(output.getBytes());
							outputStream.close();
						} catch (IOException exception) {
							ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error during " + extension + " to ConfigScan Xml conversion", exception.getMessage(), Status.CANCEL_STATUS);
						}
					}
				}
				try {
					firstElement.getParent().refreshLocal(IResource.DEPTH_ONE, monitor);
				} catch (CoreException exception) {
					ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error during refresh action on " + firstElement.getParent().getName(), exception.getMessage(), Status.CANCEL_STATUS);
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		convertJob.setPriority(Job.BUILD);
		convertJob.schedule();
	}

	public abstract String getFileExtension();
	
	public abstract void testProperties(EObject model);
}