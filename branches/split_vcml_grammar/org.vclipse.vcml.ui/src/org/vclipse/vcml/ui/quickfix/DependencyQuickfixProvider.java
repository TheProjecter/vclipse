
package org.vclipse.vcml.ui.quickfix;

import java.lang.reflect.Method;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.VCObject;
import org.vclipse.vcml.vcml.VcmlFactory;

public class DependencyQuickfixProvider extends DefaultQuickfixProvider {
	
	@Fix("Not_Existent_Source_Object")
	public void fixNotExistentSourceObject(Issue issue, IssueResolutionAcceptor acceptor) {
		String[] data = issue.getData();
		final String type = data[0];
		final String name = data[1];
		final String fileUri = data[2];
		
		String label = "Create vcobject with name " + name;
		String description = "Creates a new vcobject with name " + name;
		acceptor.accept(issue, label, description, null, new ISemanticModification() {
			public void apply(EObject element, IModificationContext context) throws Exception {
				ResourceSet resourceSet = element.eResource().getResourceSet();
				Resource resource = resourceSet.getResource(URI.createURI(fileUri), true);
				EList<EObject> contents = resource.getContents();
				if(!contents.isEmpty()) {
					Model model = (Model)contents.get(0);
					VcmlFactory factory = VcmlFactory.eINSTANCE;
					Method method = factory.getClass().getMethod("create" + type);
					if(method != null) {
						Object invoke = method.invoke(factory);
						if(invoke instanceof VCObject) {
							VCObject vcobject = (VCObject)invoke;
							vcobject.setName(name);
							model.getObjects().add(vcobject);
							resource.save(SaveOptions.defaultOptions().toOptionsMap());
						}
					}
				}
			}
		});
	}
}
