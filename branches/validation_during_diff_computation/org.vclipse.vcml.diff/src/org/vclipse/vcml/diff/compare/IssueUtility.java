package org.vclipse.vcml.diff.compare;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Issue.IssueImpl;

public class IssueUtility {

	public void associateWith(IssueImpl issue, EObject object) {
		String[] data = issue.getData();
		if(data.length > 0) {
			String featureName = data[data.length - 1];
			EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
			Object containedObject = object.eGet(feature);
			if(containedObject instanceof EObject) {
				EObject containedEObject = (EObject)containedObject;
				ICompositeNode node = NodeModelUtils.getNode(containedEObject);
				if(node != null) {
					issue.setLength(node.getTotalLength());
					issue.setLineNumber(node.getStartLine());
					issue.setOffset(node.getTotalOffset());
				}
				issue.setUriToProblem(EcoreUtil.getURI(containedEObject));
			}
		}
	}
}
