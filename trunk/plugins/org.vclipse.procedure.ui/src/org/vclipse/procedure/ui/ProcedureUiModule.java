/*
 * generated by Xtext
 */
package org.vclipse.procedure.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.vclipse.vcml.ui.resources.VcmlResourceContainerState;

import com.google.inject.Provider;

/**
 * Use this class to register components to be used within the IDE.
 */
public class ProcedureUiModule extends org.vclipse.procedure.ui.AbstractProcedureUiModule {
	
	public ProcedureUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public Provider<IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.<IAllContainersState>provider(VcmlResourceContainerState.class);
	}
}
