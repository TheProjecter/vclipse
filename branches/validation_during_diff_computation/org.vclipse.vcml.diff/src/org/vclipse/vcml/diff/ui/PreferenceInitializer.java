/**
 * 
 */
package org.vclipse.vcml.diff.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.vclipse.vcml.diff.IDiffFilter;

import com.google.inject.Inject;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private IPreferenceStore preferenceStore;
	
	@Inject
	public PreferenceInitializer(IPreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
	}
	
	@Override
	public void initializeDefaultPreferences() {
		preferenceStore.setDefault(IDiffFilter.CHARACTERISTIC_IGNORE_VALUE_ORDER, false);
		preferenceStore.setDefault(IDiffFilter.CLASS_IGNORE_CHARACTERISTIC_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.DEPENDENCY_NET_IGNORE_CONSTRAINTS_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.MATERIAL_IGNORE_BOMS_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.MATERIAL_IGNORE_CLASSES_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.MATERIAL_IGNORE_CONFIGURATION_PROFILE_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.VARIANT_FUNCTION_IGNORE_ARGUMENTS_ORDER, true);
		preferenceStore.setDefault(IDiffFilter.VARIANT_TABLE_IGNORE_ARGUMENTS_ORDER, true);
	}
}
