
package org.vclipse.vcml;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class ConditionStandaloneSetup extends ConditionStandaloneSetupGenerated{

	public static void doSetup() {
		new ConditionStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

