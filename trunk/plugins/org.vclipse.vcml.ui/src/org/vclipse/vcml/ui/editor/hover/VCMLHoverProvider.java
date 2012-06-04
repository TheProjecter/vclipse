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
package org.vclipse.vcml.ui.editor.hover;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.vclipse.base.naming.IClassNameProvider;
import org.vclipse.vcml.documentation.VCMLAdditionalInformationProvider;
import org.vclipse.vcml.documentation.VCMLDescriptionProvider;
import org.vclipse.vcml.documentation.VCMLDocumentationProvider;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicType;
import org.vclipse.vcml.vcml.CharacteristicValue;
import org.vclipse.vcml.vcml.SymbolicType;

import com.google.inject.Inject;

public class VCMLHoverProvider extends DefaultEObjectHoverProvider {

	@Inject
	private IClassNameProvider classNameProvider;

	@Inject
	private IQualifiedNameProvider nameProvider;

	@Inject
	private VCMLAdditionalInformationProvider additionalInformationProvider;

	@Inject
	private VCMLDescriptionProvider descriptionProvider;

	@Inject
	private VCMLDocumentationProvider documentationProvider;

	@Override
	protected String getFirstLine(EObject o) {
		return getClassName(o) + " <b>" + nameProvider.getFullyQualifiedName(o) + "</b>";
	}

	protected String getClassName(EObject o) {
		return getClassNameProvider().getClassName(o);
	}

	protected IClassNameProvider getClassNameProvider() {
		return classNameProvider;
	}

	protected String getHoverInfoAsHtml(EObject object) {
		if(!hasHover(object))
			return null;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(getFirstLine(object));
		String description = descriptionProvider.getDocumentation(object);
		if (description!=null && description.length()>0) {
			buffer.append("<br/>");
			buffer.append(description);
		}
		String documentation = documentationProvider.getDocumentation(object);
		if (documentation!=null && documentation.length()>0) {
			buffer.append("<br/>");
			buffer.append(documentation);
		}
		String additionalInformation = additionalInformationProvider.getDocumentation(object);
		if (additionalInformation!=null && additionalInformation.length()>0) {
			buffer.append("<br/>");
			buffer.append(additionalInformation);
		}
		String multilineCommentDocumentation = getDocumentation(object);
		if (multilineCommentDocumentation!=null && multilineCommentDocumentation.length()>0) {
			buffer.append("<p>");
			buffer.append(multilineCommentDocumentation);
			buffer.append("</p>");
		}
		if(object instanceof Characteristic) {
			Characteristic characteristic = (Characteristic)object;
			CharacteristicType type = characteristic.getType();
			if(type instanceof SymbolicType) {
				SymbolicType symbolicType = (SymbolicType)type;
				if(!symbolicType.getValues().isEmpty()) {
					buffer.append("<br/><br/>Values:<ul>");
					for(CharacteristicValue value : symbolicType.getValues()) {
						buffer.append("<li><b>" + value.getName() + "</b>" 
								/*+ (value.getDescription()==null ? "" : ": <em>" + value.getDescription() + "</em>")*/ + "</li>");
					}
					buffer.append("</ul>");					
				}
			}
		}
		return buffer.toString();
	}
	
}