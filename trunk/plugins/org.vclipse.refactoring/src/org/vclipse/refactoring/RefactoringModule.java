package org.vclipse.refactoring;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.vclipse.base.ui.util.ClasspathAwareImageHelper;
import org.vclipse.base.ui.util.IExtendedImageHelper;

public class RefactoringModule extends AbstractGenericModule {

	protected RefactoringPlugin plugin;
	
	public RefactoringModule(RefactoringPlugin plugin) {
		this.plugin = plugin;
	}
	
	public AbstractUIPlugin bindPlugin() {
		return plugin;
	}
	
	public IPreferenceStore bindPreferenceStore() {
		return plugin.getPreferenceStore();
	}
	
	public Class<? extends IExtendedImageHelper> bindImageHelper() {
		return ClasspathAwareImageHelper.class;
	}
}