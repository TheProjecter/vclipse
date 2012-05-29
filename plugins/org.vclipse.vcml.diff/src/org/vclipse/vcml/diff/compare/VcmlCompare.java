package org.vclipse.vcml.diff.compare;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.vclipse.base.UriUtil;
import org.vclipse.base.ui.util.VClipseResourceUtil;
import org.vclipse.vcml.parser.antlr.VCMLParser;
import org.vclipse.vcml.validation.VCMLJavaValidatorIssues;
import org.vclipse.vcml.vcml.Import;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.OptionType;
import org.vclipse.vcml.vcml.VcmlFactory;

import com.google.inject.Inject;

public class VcmlCompare {

	private static final VcmlFactory VCML_FACTORY = VcmlFactory.eINSTANCE;
	
	private @Inject DiffModelSwitch diffModelSwitch;
	
	private @Inject VCMLParser vcmlParser;
	
	private @Inject VClipseResourceUtil resourceUtils;
	
	private @Inject VCMLJavaValidatorIssues issuesValidator;
	
	public void compare(IFile oldFile, IFile newFile, IFile resultFile, IProgressMonitor monitor) throws CoreException, InterruptedException, IOException {
		monitor.subTask("Initialising models for comparison...");
		
		XtextResourceSet resourceSet = new XtextResourceSet();
		
		// create resources from files
		Resource newResource = resourceUtils.getResource(resourceSet, newFile);
		Resource oldResource = resourceUtils.getResource(resourceSet, oldFile);
		Resource resultResource = resourceUtils.getResource(resourceSet, resultFile);
		
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
	
	public void createMarkers(IFile resultFile, Resource resultResource) throws CoreException, IOException {
		EList<EObject> contents = resultResource.getContents();
		IParseResult parse = vcmlParser.parse(new FileReader(resultFile.getLocation().toFile()));
		EObject rootASTElement = parse.getRootASTElement();
		if(rootASTElement instanceof Model) {
			contents.clear();
			contents.add(rootASTElement);
		}
		
		issuesValidator.setIssues(diffModelSwitch.getIssues());
		issuesValidator.setResource(resultResource);
	}
	
	public void compare(Resource oldResource, Resource newResource, Resource resultResource, IProgressMonitor monitor) throws InterruptedException, IOException {
		monitor.subTask("Comparing models...");
		Map<String, Object> options = new HashMap<String, Object>();   
		options.put(MatchOptions.OPTION_DISTINCT_METAMODELS, false);
		options.put(MatchOptions.OPTION_IGNORE_ID, false);
		options.put(MatchOptions.OPTION_IGNORE_XMI_ID, false);
		MatchModel matchModel = MatchService.doResourceMatch(newResource, oldResource, options);
		
		DiffModel diffModel = DiffService.doDiff(matchModel);
		monitor.worked(10);
		
		Model resultModel = VCML_FACTORY.createModel();	
		EList<EObject> contents = resultResource.getContents();
		if(!contents.isEmpty()) {
			contents.clear();
		}
		contents.add(resultModel);
		
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
	
		diffModelSwitch.extractDifferences(diffModel, resultModel, changedModel, monitor);
		
		// compute the import uri -> the old file should be imported by the result file
		Import importStatement = VCML_FACTORY.createImport();
		importStatement.setImportURI(new UriUtil().computeImportUri(oldResource, resultResource));
		resultModel.getImports().add(importStatement);
	}
	
	public boolean reportedProblems() {
		return !diffModelSwitch.getIssues().isEmpty();
	}
}