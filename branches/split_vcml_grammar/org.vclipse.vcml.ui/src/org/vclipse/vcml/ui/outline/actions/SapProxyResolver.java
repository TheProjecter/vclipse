package org.vclipse.vcml.ui.outline.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.nodemodel.BidiIterator;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.vclipse.vcml.ui.extension.IExtensionPointUtilities;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.VcmlPackage;

import com.google.inject.Inject;

public class SapProxyResolver {

	@Inject
	private IExtensionPointUtilities extensionPointReader;
	
	public void resolveProxies(EObject object, Set<String> seenObjects, Resource output, List<Option> options) {
		BidiIterator<INode> iterator = NodeModelUtils.getNode(object).getChildren().iterator();
		while(iterator.hasNext()) {
			iterator = iterator.next().getAsTreeIterable().iterator();
			while(iterator.hasNext()) {
				INode childNode = iterator.next();
				EObject grammarElement = childNode.getGrammarElement();
				if(grammarElement instanceof CrossReference) {
					EObject semanticElement = childNode.getSemanticElement();
					Object referenceValue = semanticElement.eGet(GrammarUtil.getReference((CrossReference)grammarElement, semanticElement.eClass()));
					if(referenceValue instanceof EObject) {
						EObject eobject = (EObject)referenceValue;
						eobject.eSet(VcmlPackage.eINSTANCE.getVCObject_Name(), childNode.getText().trim());
						for(IVcmlOutlineActionHandler<?> handler : extensionPointReader.getHandler(eobject.eClass().getInstanceClassName())) {
							if(handler.getClass().getSimpleName().contains("Extract")) {
								try {
									Method method = handler.getClass().getMethod("run", new Class[]{getInstanceType(eobject), Resource.class, IProgressMonitor.class, Set.class, List.class});
									method.invoke(handler, new Object[]{eobject, output == null ? object.eResource() : output, new NullProgressMonitor(), seenObjects, options});
								} catch(InvocationTargetException exception) {
									if(exception.getTargetException() instanceof OutlineActionCanceledException) {
										break;
									} else {
										exception.printStackTrace();
									}
								} catch(Exception exception) {
									exception.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
	
	private Class<?> getInstanceType(EObject obj) throws ClassNotFoundException {
		return Class.forName(getInstanceTypeName(obj));
	}
	
	private String getInstanceTypeName(EObject obj) {
		return obj.eClass().getInstanceTypeName();
	}
}
