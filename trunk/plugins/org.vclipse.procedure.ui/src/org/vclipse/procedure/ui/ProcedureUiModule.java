/*
 * generated by Xtext
 */
package org.vclipse.procedure.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.vclipse.procedure.ui.refactoring.PreviewEntityComputer;
import org.vclipse.refactoring.IPreviewEntityComputer;
import org.vclipse.refactoring.IRefactoringConfiguration;
import org.vclipse.refactoring.IRefactoringExecuter;
import org.vclipse.refactoring.IRefactoringUIConfiguration;
import org.vclipse.vcml.refactoring.VCMLCustomisation;
import org.vclipse.vcml.refactoring.VCMLRefactoring;
import org.vclipse.vcml.ui.refactoring.VCMLUICustomisation;
import org.vclipse.vcml.ui.resources.VcmlResourcesStateProvider;

import com.google.inject.Provider;

/**
 * Use this class to register components to be used within the IDE.
 */
public class ProcedureUiModule extends org.vclipse.procedure.ui.AbstractProcedureUiModule {
	
	public ProcedureUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	public Provider<IAllContainersState> provideIAllContainersState() {
		return VcmlResourcesStateProvider.getInstance();
	}
	
	/**
	 * Vcml Re-factoring bindings
	 */
	public Class<? extends IPreviewEntityComputer> bindRelevantEntityComputer() {
		return PreviewEntityComputer.class;
	}
	
	public Class<? extends IRefactoringConfiguration> bindRefactoringConfiguration() {
		return VCMLCustomisation.class;
	}
	
	public Class<? extends IRefactoringUIConfiguration> bindRefactoringUIConfiguration() {
		return VCMLUICustomisation.class;
	}
	
	public Class<? extends IRefactoringExecuter> bindRefactoringExecuter() {
		return VCMLRefactoring.class;
	}
}
