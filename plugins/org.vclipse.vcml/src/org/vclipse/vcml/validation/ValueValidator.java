package org.vclipse.vcml.validation;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.vclipse.vcml.vcml.VcmlPackage;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ValueValidator extends AbstractDeclarativeValidator {

	public static final VcmlPackage VCML_PACKAGE = VcmlPackage.eINSTANCE;
	
	public static final String VALUES = "values";
	public static final String DEFAULT = "default";
	
	protected Set<EClass> getTypesToCheck() {
		return Sets.newHashSet(VCML_PACKAGE.getSymbolicType(), VCML_PACKAGE.getNumericType(), VCML_PACKAGE.getDateType());
	}
	
	@Override
	public void register(EValidatorRegistrar registrar) {
		// not registered for any package
	}
	
	@Check
	@SuppressWarnings("unchecked")
	public void checkSingleValue(EObject object) {
		if(getTypesToCheck().contains(object.eClass())) {
			EList<EReference> references = object.eClass().getEAllReferences();
			for(EReference reference : references) {
				if(VALUES.equals(reference.getName())) {
					List<EObject> defaultSet = Lists.newArrayList();
					for(EObject entry : (EList<EObject>)object.eGet(reference)) {
						for(EAttribute attribute : entry.eClass().getEAllAttributes()) {
							if(DEFAULT.equals(attribute.getName()) && (Boolean)entry.eGet(attribute)) {
								defaultSet.add(entry);
								break;
							}							
						}
					}
					if(defaultSet.size() > 1) {
						String message = "Only one default value is allowed.";
						for(EObject entry : defaultSet) {
							for(EAttribute attribute : entry.eClass().getEAllAttributes()) {
								String[] data = new String[]{"Remove the forbidden default attribute from the value.", EcoreUtil.getURI(entry).toString()};
								error(message, entry, attribute, "Forbidden_default_attribute", data);	
								break;
							}
						}
					}
				}
			}
		}		
	}
}