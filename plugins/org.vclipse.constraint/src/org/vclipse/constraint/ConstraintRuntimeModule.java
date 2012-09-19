/*
 * generated by Xtext
 */
package org.vclipse.constraint;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.formatting.INodeModelStreamer;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parsetree.reconstr.ITokenSerializer;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.serializer.ISerializer;
import org.vclipse.dependency.formatting.SourceCommentHandlingStreamer;
import org.vclipse.dependency.resource.DependencyResourceDescriptionStrategy;
import org.vclipse.vcml.conversion.VCMLValueConverter;
import org.vclipse.vcml.naming.CrossRefExtractingSimpleNameProvider;
import org.vclipse.vcml.serializer.VCMLCrossReferenceSerializer;
import org.vclipse.vcml.serializer.VCMLSerializer;
import org.vclipse.vcml.validation.VCMLLinkingDiagnosticMessageProvider;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class ConstraintRuntimeModule extends org.vclipse.constraint.AbstractConstraintRuntimeModule {

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
	
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return DependencyResourceDescriptionStrategy.class;
	}
	
	public Class<? extends INodeModelStreamer> bindINodeModelStreamer() {
		return SourceCommentHandlingStreamer.class;
	}
	
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return VCMLSerializer.class;
	}
	
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return CrossRefExtractingSimpleNameProvider.class;
	}
}
