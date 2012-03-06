package org.vclipse.vcml.diff.compare;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

public class DiffMessageAcceptor implements ValidationMessageAcceptor {

	private Map<String, Issue> issues;
	
	@Inject
	private IQualifiedNameProvider nameProvider;
	
	public DiffMessageAcceptor() {
		issues = Maps.newHashMap();
	}

	public Issue getIssue(EObject object) {
		QualifiedName qualifiedName = nameProvider.apply(object);
		if(qualifiedName != null) {
			String lastSegment = qualifiedName.getLastSegment();
			return issues.get(lastSegment);
		}
		return null;
	}
	
	public boolean hasMessages() {
		return !issues.isEmpty();
	}
	
	@Override
	public void acceptError(String message, EObject object, EStructuralFeature feature, int index, String code, String... issueData) {
		IssueImpl issue = new Issue.IssueImpl();
		issue.setType(CheckType.NORMAL);
		issue.setSeverity(Severity.ERROR);
		
		Object childObject = object.eGet(feature);
		if(childObject instanceof EObject) {
			EObject entry = (EObject)childObject;
			ICompositeNode node = NodeModelUtils.getNode(entry);
			if(node != null) {
				issue.setLength(node.getTotalLength());
				issue.setLineNumber(node.getStartLine());
				issue.setOffset(node.getTotalOffset());
				issue.setUriToProblem(EcoreUtil.getURI(entry));
			}
		}
	}

	@Override
	public void acceptError(String message, EObject object, int offset, int length, String code, String... issueData) {
		
	}

	@Override
	public void acceptWarning(String message, EObject object, EStructuralFeature feature, int index, String code, String... issueData) {
		
	}

	@Override
	public void acceptWarning(String message, EObject object, int offset, int length, String code, String... issueData) {
		
	}

	@Override
	public void acceptInfo(String message, EObject object, EStructuralFeature feature, int index, String code, String... issueData) {
		
	}

	@Override
	public void acceptInfo(String message, EObject object, int offset, int length, String code, String... issueData) {
		
	}
}
