package org.vclipse.vcml.resource;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.vclipse.vcml.vcml.ConditionSource;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.ConstraintSource;
import org.vclipse.vcml.vcml.Dependency;
import org.vclipse.vcml.vcml.Precondition;
import org.vclipse.vcml.vcml.Procedure;
import org.vclipse.vcml.vcml.ProcedureSource;
import org.vclipse.vcml.vcml.SelectionCondition;

public class VCObjectSourceUtils {
	
	public EObject getSource(Dependency object) {
		if (object == null) {
			return null;
		} else if (object instanceof Procedure) {
			return getSource((Procedure)object);
		} else if (object instanceof Constraint) {
			return getSource((Constraint)object);
		} else if (object instanceof Precondition) {
			return getSource((Precondition)object);
		} else if (object instanceof SelectionCondition) {
			return getSource((SelectionCondition)object);
		} else {
			throw new IllegalArgumentException("unknown dependency " + object);
		}
	}
	
	public ProcedureSource getSource(Procedure object) {
		return (ProcedureSource)getSource(object, ".proc");
	}
	
	public ConstraintSource getSource(Constraint object) {
		return (ConstraintSource)getSource(object, ".cons");
	}
	
	public ConditionSource getSource(Precondition object) {
		return (ConditionSource)getSource(object, ".pre");
	}
	
	public ConditionSource getSource(SelectionCondition object) {
		return (ConditionSource)getSource(object, ".sel");
	}
	
	private EObject getSource(Dependency object, String suffix) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(object);
		Resource resource = object.eResource();
		URI uri = resource.getURI();
		String lastSegment = uri.lastSegment();
		String path = lastSegment.replace(".vcml", "").replace(".cml2", "_cml2").concat("/" + name.toLowerCase()).concat(suffix);
		path = uri.toString().replace(lastSegment, path);
		EList<EObject> contents = resource.getResourceSet().getResource(URI.createURI(path), true).getContents();
		if(!contents.isEmpty()) {
			return contents.get(0);
		}
		return null;
	}
}
