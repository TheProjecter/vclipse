package org.vclipse.vcml.ui.outline.actions;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class VcmlOutlineActionProvider implements Provider<VCMLOutlineAction> {

	@Inject
	private IPreferenceStore preferenceStore;
	
	@Inject
	private IContentOutlinePage outlinePage;
	
	public VCMLOutlineAction get() {
		return new VCMLOutlineAction(preferenceStore, outlinePage);
	}
}
