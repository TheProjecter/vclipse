package org.vclipse.vcml.diff.injection;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.vclipse.vcml.VCMLRuntimeModule;
import org.vclipse.vcml.diff.compare.IssueCreator;

public class DiffModule extends VCMLRuntimeModule {

	private AbstractUIPlugin plugin;
	
	public DiffModule(AbstractUIPlugin plugin) {
		this.plugin = plugin;
	}

	public AbstractUIPlugin bindPlugin() {
		return plugin;
	}
	
	public IPreferenceStore bindPreferenceStore() {
		return plugin.getPreferenceStore();
	}

	public Class<? extends MarkerCreator> bindMarkerCreator() {
		return MarkerCreator.class;
	}
	
	public Class<? extends IssueCreator> bindIssueCreator() {
		return IssueCreator.class;
	}
}
