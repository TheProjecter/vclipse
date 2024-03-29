/*******************************************************************************
 * Copyright (c) 2010 - 2013 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     	webXcerpt Software GmbH - initial creator
 * 		www.webxcerpt.com
 ******************************************************************************/
package org.vclipse.idoc.formatting;

import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.vclipse.idoc.services.IDocGrammarAccess;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it 
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class IDocFormatter extends AbstractDeclarativeFormatter {
	
	@Override
	protected void configureFormatting(final FormattingConfig config) {
		final IDocGrammarAccess grammarAccess = (IDocGrammarAccess) getGrammarAccess();
	    config.setAutoLinewrap(120);
	    config.setLinewrap().before(grammarAccess.getIDocAccess().getRightCurlyBracketKeyword_7());
	    config.setLinewrap(2).after(grammarAccess.getIDocAccess().getRightCurlyBracketKeyword_7());
	    config.setIndentation(
	    		grammarAccess.getIDocAccess().getLeftCurlyBracketKeyword_4(),
	    		grammarAccess.getIDocAccess().getRightCurlyBracketKeyword_7());
	    config.setLinewrap().before(
	    		grammarAccess.getSegmentAccess().getSegmentKeyword_0());
	    config.setLinewrap().before(grammarAccess.getSegmentAccess().getRightCurlyBracketKeyword_5());
	    config.setIndentation(
	    		grammarAccess.getSegmentAccess().getLeftCurlyBracketKeyword_2(),
	    		grammarAccess.getSegmentAccess().getRightCurlyBracketKeyword_5());
	}
}
