package org.vclipse.dependency.ui;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.xtext.ui.editor.toggleComments.DefaultSingleLineCommentHelper;

public class SingleLineCommentHelper extends DefaultSingleLineCommentHelper {

	@Override
	public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
		return super.getDefaultPrefixes(sourceViewer, contentType);
		
		
		//return new String[]{"*"};
	}
}
