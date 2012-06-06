package org.vclipse.vcml.resource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.vclipse.vcml.vcml.Condition;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.Procedure;

public class VCObjectSourceUtils {
	
	public EObject getSource(EObject object) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(object);
		Resource resource = object.eResource();
		URI uri = resource.getURI();
		String lastSegment = uri.lastSegment();
		String path = lastSegment.replace(".vcml", "").replace(".cml2", "_cml2").concat("/" + name.toLowerCase());
		if(object instanceof Procedure) {
			path = path.concat(".proc");			
		} else if(object instanceof Constraint) {
			path = path.concat(".cons");
		} else if(object instanceof Condition) {
			// there are 2 extensions for conditions => we should prove 
			// if the resource exists
			path = path.concat(".pre");
		}
		path = uri.toString().replace(lastSegment, path);
		try {
			EList<EObject> contents = resource.getResourceSet().getResource(URI.createURI(path), true).getContents();
			if(!contents.isEmpty()) {
				return contents.get(0);
			}
		} catch(Exception exception) {
			EList<EObject> contents = resource.getResourceSet().getResource(URI.createURI(path.replace(".pre", ".sel")), true).getContents();
			if(!contents.isEmpty()) {
				return contents.get(0);
			}
		}
		return null;
	}
}
