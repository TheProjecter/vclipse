/*
 * generated by Xtext
 */
package org.vclipse.condition;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.parsetree.reconstr.ITokenSerializer;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.serializer.ISerializer;
import org.vclipse.dependency.resource.DependencyResourceDescriptionStrategy;
import org.vclipse.vcml.conversion.VCMLValueConverter;
import org.vclipse.vcml.formatting.VCMLCrossReferenceSerializer;
import org.vclipse.vcml.formatting.VCMLSerializer;
import org.vclipse.vcml.validation.VCMLLinkingDiagnosticMessageProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class ConditionRuntimeModule extends org.vclipse.condition.AbstractConditionRuntimeModule {

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return VCMLValueConverter.class;
	}
	
	public Class<? extends ILinkingDiagnosticMessageProvider.Extended> bindILinkingDiagnosticMessageProvider() {
		return VCMLLinkingDiagnosticMessageProvider.class;
	}

	public Class<? extends ITokenSerializer.ICrossReferenceSerializer> bindICrossReferenceSerializer() {
		return VCMLCrossReferenceSerializer.class;
	}
	
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return VCMLSerializer.class;
	}

	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return DependencyResourceDescriptionStrategy.class;
	}

}