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
package org.vclipse.vcml.formatting;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.vclipse.vcml.vcml.Language;
import org.vclipse.vcml.services.VCMLGrammarAccess.CharacteristicValueElements;
import org.vclipse.vcml.services.VCMLGrammarAccess.ConstraintSourceElements;
import org.vclipse.vcml.services.VCMLGrammarAccess.MultiLanguageDescriptionElements;
import org.vclipse.vcml.services.VCMLGrammarAccess.ProcedureSourceElements;


/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it 
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class VCMLFormatter extends AbstractDeclarativeFormatter {
	
	private static Set<String> linewrapKeywords = new HashSet<String>();
	static {
		linewrapKeywords.add("description");
		linewrapKeywords.add("documentation");
		linewrapKeywords.add("status");
		linewrapKeywords.add("group");
		linewrapKeywords.add("type");
		linewrapKeywords.add("symbolic");
		linewrapKeywords.add("numeric");
		linewrapKeywords.add("values");
		linewrapKeywords.add("source");
		linewrapKeywords.add("characteristics");
		linewrapKeywords.add("classes");
		linewrapKeywords.add("billofmaterial");
		linewrapKeywords.add("bomapplication");
		linewrapKeywords.add("uidesign");
		linewrapKeywords.add("arguments");
		linewrapKeywords.add("[");
		for (Language l : Language.VALUES) { // FIXME these enums are not linewrapped. Perhaps the mechanism does not work for enums?
			linewrapKeywords.add(l.getLiteral());
		}
	};
	
	@Override
	protected void configureFormatting(FormattingConfig c) {
		org.vclipse.vcml.services.VCMLGrammarAccess f = (org.vclipse.vcml.services.VCMLGrammarAccess) getGrammarAccess();

	    c.setAutoLinewrap(70); // SAP dependencies are limited to 72 characters // FIXME this does not work in Xtext 0.7.2
	    
	    // generic formatting code (ideas by Moritz Eysholdt and Sebastian Benz, eclipse.modeling.tmf, 2009-07-22)
	    Iterable<Keyword> keywords = GrammarUtil.containedKeywords(f.getGrammar());

	    // dots
	    for (Keyword currentKeyword : keywords) {
	    	if (".".equals(currentKeyword.getValue())) {
	    		c.setNoSpace().around(currentKeyword);
	    	}
	    }

	    // braces
	    // note: this assumes that matching braces are also "matching" in the grammar and that the order of keywords is like in grammar
	    Stack<Keyword> openBraceStack = new Stack<Keyword>();
	    for (Keyword currentKeyword : keywords) {
	    	if ("{".equals(currentKeyword.getValue())) {
	    		openBraceStack.push(currentKeyword);
	    		c.setLinewrap().after(currentKeyword);
	    		c.setIndentationIncrement().after(currentKeyword);
	    	} else if ("}".equals(currentKeyword.getValue())) {
	    		if (!openBraceStack.isEmpty()) {
	    			c.setLinewrap().after(currentKeyword);
	    			c.setLinewrap().before(currentKeyword);
	    			c.setIndentationDecrement().before(currentKeyword);
	    			openBraceStack.pop();
	    		}
	    	}
	    }

	    // keyword sets
	    for (Keyword currentKeyword : keywords) {
	    	if (linewrapKeywords.contains(currentKeyword.getValue())) {
	    		c.setLinewrap().before(currentKeyword);
	    	}
	    }

	    // FIXME this does not seem to work
	    {
	    	MultiLanguageDescriptionElements elements = f.getMultiLanguageDescriptionAccess();
	    	c.setLinewrap().before(elements.getLanguageLanguageEnumRuleCall_0_0());
	    }
	    
	    {	// names of characteristic values
	    	CharacteristicValueElements elements = f.getCharacteristicValueAccess();
	    	c.setLinewrap().before(elements.getNameSYMBOLTerminalRuleCall_0_0());
	    }
	    
	    // SAPObject definitions on toplevel
	    // (not done with linewrapKeywords because this would apply to nested definitions as well)
	    c.setLinewrap(2).before(f.getClassAccess().getClassKeyword_0());
	    c.setLinewrap(2).before(f.getCharacteristicAccess().getCharacteristicKeyword_0());
	    c.setLinewrap(2).before(f.getConstraintAccess().getConstraintKeyword_0());
	    c.setLinewrap(2).before(f.getDependencyNetAccess().getDependencynetKeyword_0());
	    c.setIndentationIncrement().before(f.getDependencyNetAccess().getDescriptionAssignment_2_1_0());
	    c.setLinewrap(2).before(f.getInterfaceDesignAccess().getInterfacedesignKeyword_0());
	    c.setLinewrap(2).before(f.getMaterialAccess().getMaterialKeyword_0());
	    c.setLinewrap(2).before(f.getPreconditionAccess().getPreconditionKeyword_0());
	    c.setLinewrap(2).before(f.getProcedureAccess().getProcedureKeyword_0());
	    c.setLinewrap(2).before(f.getSelectionConditionAccess().getSelectionconditionKeyword_0());
	    c.setLinewrap(2).before(f.getVariantFunctionAccess().getVariantfunctionKeyword_0());
	    c.setLinewrap(2).before(f.getVariantTableAccess().getVarianttableKeyword_0());

	    {
	    	// constraint source (linewrap after . : ,)
	    	ConstraintSourceElements elements = f.getConstraintSourceAccess();
	    	c.setLinewrap().after(elements.getColonKeyword_1());
	    	c.setLinewrap().after(elements.getColonKeyword_5_1());
	    	c.setLinewrap().after(elements.getColonKeyword_7());
	    	c.setLinewrap().after(elements.getColonKeyword_11_1());
	    	c.setLinewrap().after(elements.getCommaKeyword_3_0());
	    	c.setLinewrap().after(elements.getCommaKeyword_9_0());
	    	c.setLinewrap().after(elements.getCommaKeyword_11_3_0());
	    	c.setLinewrap().after(elements.getFullStopKeyword_4());
	    	c.setLinewrap().after(elements.getFullStopKeyword_5_3());
	    	c.setLinewrap().after(elements.getFullStopKeyword_10());
	    	c.setLinewrap().after(elements.getFullStopKeyword_11_4());
	    	c.setIndentation(elements.getColonKeyword_1(), elements.getFullStopKeyword_4());
	    	c.setIndentation(elements.getColonKeyword_5_1(), elements.getFullStopKeyword_5_3());
	    	c.setIndentation(elements.getColonKeyword_7(), elements.getFullStopKeyword_10());
	    	c.setIndentation(elements.getColonKeyword_11_1(), elements.getFullStopKeyword_11_4());
	    }
	    {
	    	// procedure source (linewrap after ,)
	    	ProcedureSourceElements elements = f.getProcedureSourceAccess();
	    	c.setLinewrap().after(elements.getCommaKeyword_1_0());
	    }
    	c.setLinewrap(2).after(f.getDEPENDENCY_COMMENTRule());
	}
}
