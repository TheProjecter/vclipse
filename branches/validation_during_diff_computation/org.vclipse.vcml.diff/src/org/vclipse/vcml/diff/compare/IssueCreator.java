package org.vclipse.vcml.diff.compare;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;
import org.vclipse.vcml.vcml.Characteristic;

public class IssueCreator {

	private final PolymorphicDispatcher<Issue> issueCreator 
		= new PolymorphicDispatcher<Issue>("issue", 2, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Issue> get()) {
		@Override
		protected Issue handleNoSuchMethod(final Object... params) {
			return null;
		}
	};

	public Issue createIssue(EObject object, EReference reference) {
		return issueCreator.invoke(object, reference);
	}
	
	protected Issue issue(Characteristic cstic, EReference reference) {
		IssueImpl issue = new Issue.IssueImpl();
		issue.setType(CheckType.NORMAL);
		issue.setMessage("Change not allowed");
		issue.setCode("Compare Issue");
		
		EObject containedObject = (EObject)cstic.eGet(reference);
		
		ICompositeNode node = NodeModelUtils.getNode(containedObject);
		issue.setLength(node.getTotalLength());
		issue.setLineNumber(node.getStartLine() + 1);
		issue.setOffset(node.getTotalOffset());
		issue.setSeverity(Severity.ERROR);
		issue.setUriToProblem(EcoreUtil.getURI(containedObject));
		return issue;
	}
}
