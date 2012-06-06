package org.vclipse.vcml.resource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.vclipse.vcml.vcml.Condition;
import org.vclipse.vcml.vcml.ConditionSource;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.ConstraintSource;
import org.vclipse.vcml.vcml.Procedure;
import org.vclipse.vcml.vcml.ProcedureSource;

public class VCObjectSourceUtils {
	
	public ProcedureSource getSource(Procedure procedure) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(procedure);
		Resource resource = procedure.eResource();
		URI uri = resource.getURI();
		String lastSegment = uri.lastSegment();
		String filePath = lastSegment.replace(".vcml", "").replace(".cml2", "_cml2").concat("/" + name.toLowerCase() + ".proc");
		filePath = uri.toString().replace(lastSegment, filePath);
		Resource sourceResource = resource.getResourceSet().getResource(URI.createURI(filePath), true);
		EList<EObject> contents = sourceResource.getContents();
		if(!contents.isEmpty()) {
			EObject object = contents.get(0);
			if(object instanceof ProcedureSource) {
				return (ProcedureSource)object;
			}
		}
		return null;
	}
	
	public ConstraintSource getSource(Constraint constraint) {
		return null;
	}
	
	public ConditionSource getSource(Condition condition) {
		return null;
	}
}
