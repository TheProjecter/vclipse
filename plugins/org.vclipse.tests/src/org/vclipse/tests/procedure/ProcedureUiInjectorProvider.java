/*
* generated by Xtext
*/
package org.vclipse.tests.procedure;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class ProcedureUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return org.vclipse.procedure.ui.internal.ProcedureActivator.getInstance().getInjector("org.vclipse.procedure.Procedure");
	}
	
}