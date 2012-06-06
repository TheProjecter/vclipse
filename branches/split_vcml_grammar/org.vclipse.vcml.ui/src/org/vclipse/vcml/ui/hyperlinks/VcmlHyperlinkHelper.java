package org.vclipse.vcml.ui.hyperlinks;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.vclipse.vcml.resource.VCObjectSourceUtils;
import org.vclipse.vcml.vcml.Procedure;

import com.google.inject.Inject;

public class VcmlHyperlinkHelper extends HyperlinkHelper {

	@Inject 
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;
	
	@Inject
	private VCObjectSourceUtils sourceUtils;
	
	@Override
	public void createHyperlinksByOffset(XtextResource resource, int offset, IHyperlinkAcceptor acceptor) {
		super.createHyperlinksByOffset(resource, offset, acceptor);
		EObject elementAt = eObjectAtOffsetHelper.resolveElementAt(resource, offset);
		if(elementAt instanceof Procedure) {
			EObject source = sourceUtils.getSource(elementAt);
			if(source != null) {
				createHyperlinksTo(resource, new Region(offset, SimpleAttributeResolver.NAME_RESOLVER.apply(elementAt).length()), source, acceptor);				
			}
		}
	}
}
