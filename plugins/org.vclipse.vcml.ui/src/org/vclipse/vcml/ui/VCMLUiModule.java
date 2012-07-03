/*******************************************************************************
 * Copyright (c) 2010 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    webXcerpt Software GmbH - initial creator
 ******************************************************************************/
/*
 * generated by Xtext
 */
package org.vclipse.vcml.ui;

import java.io.PrintStream;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.documentation.impl.MultiLineCommentDocumentationProvider;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextType;
import org.eclipse.xtext.ui.refactoring.ui.RenameRefactoringExecuter;
import org.eclipse.xtext.ui.shared.Access;
import org.vclipse.base.ui.util.ClasspathAwareImageHelper;
import org.vclipse.base.ui.util.IExtendedImageHelper;
import org.vclipse.vcml.ui.editor.hover.VCMLHoverProvider;
import org.vclipse.vcml.ui.extension.ExtensionPointUtilities;
import org.vclipse.vcml.ui.extension.IExtensionPointUtilities;
import org.vclipse.vcml.ui.hyperlinks.VcmlHyperlinkHelper;
import org.vclipse.vcml.ui.hyperlinks.VcmlHyperlinkLabelProvider;
import org.vclipse.vcml.ui.outline.VCMLOutlinePage;
import org.vclipse.vcml.ui.outline.VCMLOutlineTreeProvider;
import org.vclipse.vcml.ui.refactoring.VcmlRenameRefactoringExecutor;
import org.vclipse.vcml.ui.syntaxcoloring.VCMLAntlrTokenToAttributeIdMapper;
import org.vclipse.vcml.ui.templates.VcmlTemplateContextType;

import com.google.inject.Provider;

/**
 * Use this class to register components to be used within the IDE.
 */
public class VCMLUiModule extends org.vclipse.vcml.ui.AbstractVCMLUiModule {
	
	public VCMLUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return VCMLOutlinePage.class;
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return VCMLHoverProvider.class;
	}

	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return MultiLineCommentDocumentationProvider.class;
	}
	
	public PrintStream bindPrintStream() {
		return System.out;
	}
	
	public IWorkspaceRoot bindWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	public Class<? extends IExtensionPointUtilities> bindExtensionPointReader() {
		return ExtensionPointUtilities.class;
	}
	
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return VCMLAntlrTokenToAttributeIdMapper.class;
	}

	public com.google.inject.Provider<org.eclipse.xtext.resource.containers.IAllContainersState> provideIAllContainersState() {
		return org.eclipse.xtext.ui.shared.Access.getWorkspaceProjectsState();
	}

	@Override
	public Class<? extends IImageHelper> bindIImageHelper() {
		return ClasspathAwareImageHelper.class;
	}

	@Override
	public Class<? extends IOutlineTreeProvider> bindIOutlineTreeProvider() {
		return VCMLOutlineTreeProvider.class;
	}
	
	public Class<? extends XtextTemplateContextType> bindXtextTemplateContextType() {
		return VcmlTemplateContextType.class;
	}
	
	public Class<? extends IExtendedImageHelper> bindIExtendedImageHelper() {
		return ClasspathAwareImageHelper.class;
	}
	
	/**
	 * Refactoring
	 */
	public Provider<? extends RenameRefactoringExecuter> provideRenameRefactoringExecuter() {
		return Access.provider(VcmlRenameRefactoringExecutor.class);
	}
	
	/**
	 * Hyperlinks
	 */
	public void configureHyperlinkLabelProvider(com.google.inject.Binder binder) {
		binder.bind(ILabelProvider.class).annotatedWith(HyperlinkLabelProvider.class).to(VcmlHyperlinkLabelProvider.class);
	}
	
	public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return VcmlHyperlinkHelper.class;
	}
}
