/*
 * generated by Xtext
 */
package org.vclipse.dependency.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Use this class to register components to be used within the IDE.
 */
public class DependencyUiModule extends org.vclipse.dependency.ui.AbstractDependencyUiModule {
	
	public DependencyUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.getWorkspaceProjectsState();
	}
}
