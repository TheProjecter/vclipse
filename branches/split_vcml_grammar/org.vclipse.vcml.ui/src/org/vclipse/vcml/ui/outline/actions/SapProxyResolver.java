package org.vclipse.vcml.ui.outline.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.vclipse.vcml.ui.extension.IExtensionPointUtilities;

import com.google.inject.Inject;

public class SapProxyResolver {

	@Inject
	private IExtensionPointUtilities extensionPointReader;
	
	public void resolveProxies(EObject object, Set<String> seenObjects, Resource output) {
		TreeIterator<EObject> treeIterator = object.eAllContents();
		while(treeIterator.hasNext()) {
			for(EObject crossReference : treeIterator.next().eCrossReferences()) {
				if(crossReference.eIsProxy()) {
					Collection<IVCMLOutlineActionHandler<?>> handlers = extensionPointReader.getHandler(crossReference.eClass().getInstanceClassName());
					for(IVCMLOutlineActionHandler<?> handler : handlers) {
						if(handler.getClass().getSimpleName().contains("Extract")) {
							try {
								Method method = handler.getClass().getMethod("run", new Class[]{getInstanceType(crossReference), Resource.class, IProgressMonitor.class, Set.class});
								method.invoke(handler, new Object[]{crossReference, output == null ? object.eResource() : output, new NullProgressMonitor(), seenObjects});
							} catch (NoSuchMethodException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								Throwable targetException = e.getTargetException();
								if (targetException instanceof OutlineActionCanceledException) {
									break;
								} else {
									e.printStackTrace();
								}
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							} catch (Exception e) {
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
