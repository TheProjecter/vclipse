package org.vclipse.vcml.ui.templates;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.TemplateVariable;
import org.eclipse.xtext.ui.editor.templates.AbstractTemplateVariableResolver;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContext;
import org.vclipse.vcml.utils.DependencySourceUtils;

import com.google.inject.Inject;

public class DependencyResolver extends AbstractTemplateVariableResolver {

	@Inject
	private DependencySourceUtils dependencySourceUtils;
	
	public DependencyResolver() {
		super("Dependency", "Values for a dependency variable");
	}
	
	@Override
	public List<String> resolveValues(TemplateVariable variable, XtextTemplateContext xtextTemplateContext) {
		IDocument document = xtextTemplateContext.getDocument();
		return null;
	}
}
