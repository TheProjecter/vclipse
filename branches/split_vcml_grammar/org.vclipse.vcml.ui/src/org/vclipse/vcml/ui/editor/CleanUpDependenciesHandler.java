package org.vclipse.vcml.ui.editor;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.vclipse.base.ui.util.VClipseResourceUtil;
import org.vclipse.vcml.utils.DependencySourceUtils;

import com.google.inject.Inject;

public class CleanUpDependenciesHandler extends AbstractHandler implements IHandler {

	@Inject 
	private EObjectAtOffsetHelper offsetHelper;
	
	@Inject
	private VClipseResourceUtil resourceUtil;
	
	@Inject
	private VcmlResourceContainerState containerState;
	
	@Inject
	private DependencySourceUtils sourceUtils;
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		XtextEditor xtextEditor = EditorUtils.getActiveXtextEditor(event);
		IResource resource = xtextEditor.getResource();
		
		if(resource instanceof IFile) {
			IFile file = (IFile)resource;
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			String concat = uri.trimFileExtension().toString().concat(DependencySourceUtils.SUFFIX_SOURCEFOLDER);
			Collection<URI> containedURIs = containerState.getContainedURIs(concat);
			
			XtextResource vcmlResource = (XtextResource)resourceUtil.getResource((IFile)resource);
			Object applicationContext = event.getApplicationContext();
			if(applicationContext instanceof EvaluationContext) {
				Object defaultVariable = ((EvaluationContext)applicationContext).getDefaultVariable();
				if(defaultVariable instanceof ITextSelection) {
					EObject elementAt = offsetHelper.resolveElementAt(vcmlResource, ((ITextSelection)defaultVariable).getOffset());
					
					
				}
			}
		}
		Command command = event.getCommand();
		Map parameters = event.getParameters();
		Object trigger = event.getTrigger();
		return null;
	}

}
