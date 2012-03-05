package org.vclipse.vcml.diff.compare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.validation.Issue;
import org.vclipse.base.UriUtil;
import org.vclipse.vcml.parser.antlr.VCMLParser;
import org.vclipse.vcml.vcml.Import;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.OptionType;
import org.vclipse.vcml.vcml.VcmlFactory;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

public class Comparison {

	private static final VcmlFactory VCML_FACTORY = VcmlFactory.eINSTANCE;
	
	@Inject
	private DiffsHandlerSwitch diffsHandlerSwitch;
	
	@Inject
	private MarkerCreator markerCreator;
	
	@Inject
	private IssueCreator issueCreator;
	
	@Inject
	private IQualifiedNameProvider nameProvider;
	
	@Inject
	private VCMLParser vcmlParser;
	
	private Map<String, EReference> vcNameWithReference;
	
	public Comparison() {
		vcNameWithReference = Maps.newHashMap();
	}
	
	public void compare(IFile oldFile, IFile newFile, IFile resultFile, IProgressMonitor monitor) throws CoreException, InterruptedException, IOException {
		monitor.subTask("Initialising models for comparison...");
		
		// create resources from files
		ResourceSet set = new ResourceSetImpl();
		Resource newResource = set.getResource(URI.createURI(newFile.getLocationURI().toString()), true);
		Resource oldResource = set.getResource(URI.createURI(oldFile.getLocationURI().toString()), true);
		Resource resultResource = set.getResource(URI.createURI(resultFile.getLocationURI().toString()), true);
		
		// clean the result resource if it does exist
		EList<EObject> contents = resultResource.getContents();
		if(!contents.isEmpty()) {
			contents.clear();				
		}
		
		// compare the file contents
		compare(oldResource, newResource, resultResource, monitor);
		resultResource.save(SaveOptions.defaultOptions().toOptionsMap());

		createMarkers(resultFile, resultResource);
		
		// refresh the result file
		resultFile.refreshLocal(IResource.DEPTH_ONE, monitor);
	}

	public void createMarkers(IFile resultFile, Resource resultResource) throws FileNotFoundException, CoreException {
		resultFile.deleteMarkers(null, false, IResource.DEPTH_ONE);
		IParseResult parse = vcmlParser.parse(new FileReader(new File(resultResource.getURI().toFileString())));;
		EObject rootASTElement = parse.getRootASTElement();
		for(EObject object : rootASTElement.eContents()) {
			QualifiedName qualifiedName = nameProvider.apply(object);
			if(qualifiedName != null) {
				String lastSegment = qualifiedName.getLastSegment();
				if(vcNameWithReference.containsKey(lastSegment)) {
					Issue createIssue = issueCreator.createIssue(object, vcNameWithReference.get(lastSegment));
					if(createIssue != null) {
						markerCreator.createMarker(createIssue, resultFile, IMarker.PROBLEM);					
					}					
				}
			}
		}
	}
	
	public void compare(Resource oldResource, Resource newResource, Resource resultResource, IProgressMonitor monitor) throws InterruptedException, IOException {
		vcNameWithReference.clear();
		monitor.subTask("Comparing models...");
		Map<String, Object> options = new HashMap<String, Object>();   
		options.put(MatchOptions.OPTION_DISTINCT_METAMODELS, false);
		options.put(MatchOptions.OPTION_IGNORE_ID, false);
		options.put(MatchOptions.OPTION_IGNORE_XMI_ID, false);
		MatchModel matchModel = MatchService.doResourceMatch(newResource, oldResource, options);
		
		DiffModel diffModel = DiffService.doDiff(matchModel);
		monitor.worked(10);
		
		Model resultModel = VCML_FACTORY.createModel();		
		
		// compute the import uri -> the old file should be imported by the result file
		Import importStatement = VCML_FACTORY.createImport();
		importStatement.setImportURI(new UriUtil().computeImportUri(oldResource, resultResource));
		resultModel.getImports().add(importStatement);
	
		// get the ups option from the new file and provide it to the results file
		Model changedModel = VCML_FACTORY.createModel();
		List<EObject> newModelContent = newResource.getContents();
		if(!newModelContent.isEmpty()) {
			EObject object = newModelContent.get(0);
			if(object instanceof Model) {
				changedModel = (Model)newModelContent.get(0);
				for(Option option : changedModel.getOptions()) {
					if(OptionType.UPS.equals(option.getName())) {
						resultModel.getOptions().add(EcoreUtil2.copy(option));
					}
				}
			}
		}
		
		resultResource.getContents().add(resultModel);
		diffsHandlerSwitch.handleDiffModel(diffModel, resultModel, changedModel, monitor);
		vcNameWithReference = diffsHandlerSwitch.getReferences();
	}

	public boolean foundProblems() {
		return !vcNameWithReference.isEmpty();
	}
}
