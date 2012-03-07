/*******************************************************************************
 * Copyright (c) 2010 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    webXcerpt Software GmbH - initial creator
 ******************************************************************************/
package org.vclipse.vcml.ui.quickfix;

import java.util.List;

import org.eclipse.compare.CompareUI;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.ComparisonResourceSnapshot;
import org.eclipse.emf.compare.ui.editor.ModelCompareEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;
import org.vclipse.vcml.diff.compare.VcmlCompare;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class VCMLQuickfixProvider extends DefaultQuickfixProvider {
	
	@Inject
	private VcmlCompare vcmlCompare;
	
	@Override
	public List<IssueResolution> getResolutions(Issue issue) {
		List<IssueResolution> resolutions = Lists.newArrayList();
		if(super.hasResolutionFor(issue.getCode())) {
			resolutions = super.getResolutions(issue);
		}
		resolutions.addAll(getResolutionsForLinkingIssue(issue));
		return resolutions;
	}

	@Fix("Unresolved_Material")
	public void fixUnresolved_Material(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create material " + linkText, "Create rule", null, 
				new DefaultSemanticModification("material " + linkText));
	}
	
	@Fix("Unresolved_GlobalDependency")
	public void fixUnresolved_GlobalDependency(Issue issue, IssueResolutionAcceptor acceptor) {
		fixUnresolved_SelectionCondition(issue, acceptor);
		fixUnresolved_Procedure(issue, acceptor);
		fixUnresolved_Precondition(issue, acceptor);
		fixUnresolved_Constraint(issue, acceptor);
	}
	
	@Fix("Unresolved_SelectionCondition")
	public void fixUnresolved_SelectionCondition(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create selection condition " + linkText, "Create rule", null, 
				new DefaultSemanticModification("selectioncondition " + linkText));
	}
	
	@Fix("Unresolved_Procedure")
	public void fixUnresolved_Procedure(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create procedure " + linkText, "Create rule", null, 
				new DefaultSemanticModification("procedure " + linkText));
	}
	
	@Fix("Unresolved_UnresolvedPrecondition")
	public void fixUnresolved_Precondition(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create precondition " + linkText, "Create rule", null, 
				new DefaultSemanticModification("precondition " + linkText));
	}
	
	@Fix("Unresolved_Constraint")
	public void fixUnresolved_Constraint(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create constraint " + linkText, "Create rule", null, 
				new DefaultSemanticModification("constraint " + linkText));
	}
	
	@Fix("Unresolved_Characteristic")
	public void fixUnresolved_Characteristic(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create characteristic " + linkText, "Create rule", null, 
				new DefaultSemanticModification("characteristic " + linkText));
	}
	
	@Fix("Unresolved_Class")
	public void fixUnresolved_Class(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create class " + linkText, "Create rule", null, 
				new DefaultSemanticModification("class " + linkText));
	}
	
	@Fix("Unresolved_InterfaceDesign")
	public void fixUnresolved_InterfaceDesign(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create interface design " + linkText, "Create rule", null, 
				new DefaultSemanticModification("interfacedesign " + linkText));
	}
	
	@Fix("Unresolved_DependencyNet")
	public void fixUnresolved_DependencyNet(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create dependency net " + linkText, "Create rule", null, 
				new DefaultSemanticModification("dependencynet " + linkText));
	}
	
	@Fix("Unresolved_VariantTable")
	public void fixUnresolved_VariantTable(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create variant table " + linkText, "Create rule", null, 
				new DefaultSemanticModification("varianttable " + linkText));
	}
	
	@Fix("Unresolved_VariantFunction")
	public void fixUnresolved_VariantFunction(Issue issue, IssueResolutionAcceptor acceptor) {
		String linkText = issue.getData()[0];
		acceptor.accept(issue, "Create variant function " + linkText, "Create rule", null, 
				new DefaultSemanticModification("variantfunction " + linkText));
	}
	
	@Fix("Compare_Issue")
	public void fixCompareIssue(final Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Show change in compare editor", "Opens the compare editor for objects where the change is applied.", null, new ISemanticModification() {
			public void apply(EObject element, IModificationContext context) throws Exception {
				String[] data = issue.getData();
				XtextResourceSet xtextResourceSet = new XtextResourceSet();
				Resource resource = xtextResourceSet.getResource(URI.createURI(data[0]), true);
				Resource resource2 = xtextResourceSet.getResource(URI.createURI(data[1]), true);
				
				vcmlCompare.compare(resource, resource2, new NullProgressMonitor());
				ComparisonResourceSnapshot comparisonSnapshot = vcmlCompare.getComparisonSnapshot();
				ModelCompareEditorInput input = new ModelCompareEditorInput(comparisonSnapshot);
				CompareUI.openCompareEditor(input, true);
			}
		});
	}
}
