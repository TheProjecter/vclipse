/*
 * generated by Xtext
 */
package org.vclipse.vcml;

import org.vclipse.vcml.resource.ContainerStateProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class ProcedureRuntimeModule extends org.vclipse.vcml.AbstractProcedureRuntimeModule {

	public Class<? extends org.eclipse.xtext.resource.containers.IAllContainersState.Provider> bindIAllContainersState$Provider() {
		return ContainerStateProvider.class;
	}
}
