package org.vclipse.vcml.validation;

import org.eclipse.xtext.validation.Check;
import org.vclipse.vcml.vcml.ConditionSource;
 
public class ConditionJavaValidator extends DependencyJavaValidator {

	@Check
	public void checkConditionSource(ConditionSource source) {
		checkSource(source);
	}
}
