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
package org.vclipse.idoc.validation;

import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.vclipse.idoc.iDoc.Field;
import org.vclipse.idoc.iDoc.IDocPackage;
import org.vclipse.idoc.iDoc.Segment;
import org.vclipse.idoc.iDoc.StringField;

public class IDocJavaValidator extends AbstractIDocJavaValidator {

	private static final int LENGTH_LIMIT = 72;
	
	@Check
	public void checkSegment(final Segment segment) {
		if ("E1CUKNM".equals(segment.getType())) {
			for (Field field : segment.getFields()) {
				if ("LINE".equals(field.getName()) && field instanceof StringField) {
					StringField stringfield= (StringField)field;
					final int length = stringfield.getValue().length();
					if (length > LENGTH_LIMIT) {
						error("Length of E1CUKNM LINE should be " + LENGTH_LIMIT + " characters maximum, but is " + length + " characters", stringfield, 
								IDocPackage.Literals.STRING_FIELD__VALUE, 
									ValidationMessageAcceptor.INSIGNIFICANT_INDEX); 
					}
				}
			}
		}
	}
}
