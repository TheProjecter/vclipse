
package org.vclipse.vcml;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ConstraintStandaloneSetup extends ConstraintStandaloneSetupGenerated{

	public static void doSetup() {
		new ConstraintStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

