/*
 * generated by Xtext
 */
package org.vclipse.configscan.vcmlt;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.vclipse.base.INameProvider;
import org.vclipse.base.NullQualifiedNameConverter;
import org.vclipse.configscan.vcmlt.naming.SapNameProvider;
import org.vclipse.vcml.conversion.VCMLValueConverter;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class VcmlTRuntimeModule extends org.vclipse.configscan.vcmlt.AbstractVcmlTRuntimeModule {

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return VCMLValueConverter.class;
	}
	
	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return NullQualifiedNameConverter.class;
	}
	
	public Class<? extends INameProvider> bindINameProvider() {
		return SapNameProvider.class;
	}
}
