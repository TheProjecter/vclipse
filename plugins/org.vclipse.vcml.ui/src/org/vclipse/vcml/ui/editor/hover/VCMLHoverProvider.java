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
package org.vclipse.vcml.ui.editor.hover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.vclipse.base.naming.IClassNameProvider;
import org.vclipse.vcml.documentation.VCMLAdditionalInformationProvider;
import org.vclipse.vcml.documentation.VCMLDescriptionProvider;
import org.vclipse.vcml.documentation.VCMLDocumentationProvider;
import org.vclipse.vcml.utils.DependencySourceUtils;
import org.vclipse.vcml.utils.DescriptionHandler;
import org.vclipse.vcml.utils.VcmlUtils;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicType;
import org.vclipse.vcml.vcml.CharacteristicValue;
import org.vclipse.vcml.vcml.Dependency;
import org.vclipse.vcml.vcml.Description;
import org.vclipse.vcml.vcml.Language;
import org.vclipse.vcml.vcml.MultiLanguageDescription;
import org.vclipse.vcml.vcml.MultiLanguageDescriptions;
import org.vclipse.vcml.vcml.SymbolicType;
import org.vclipse.vcml.vcml.VCObject;

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
	
	@Inject
	private DependencySourceUtils sourceUtils;
	
	@Inject
	private IPreferenceStore preferenceStore;
	
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

	protected String getHoverInfoAsHtml(EObject eObject) {
		if(!(eObject instanceof VCObject) || !hasHover(eObject))
			return null;
		VCObject vcObject = (VCObject)eObject;
		final StringBuffer buffer = new StringBuffer();
		buffer.append(getFirstLine(vcObject));
		String description = descriptionProvider.getDocumentation(vcObject);
		if (description!=null && description.length()>0) {
			buffer.append("<br/>");
			buffer.append(description);
		}
		String documentation = documentationProvider.getDocumentation(vcObject);
		if (documentation!=null && documentation.length()>0) {
			buffer.append("<br/>");
			buffer.append(documentation);
		}
		String additionalInformation = additionalInformationProvider.getDocumentation(vcObject);
		if (additionalInformation!=null && additionalInformation.length()>0) {
			buffer.append("<br/>");
			buffer.append(additionalInformation);
		}
		String multilineCommentDocumentation = getDocumentation(vcObject);
		if (multilineCommentDocumentation!=null && multilineCommentDocumentation.length()>0) {
			buffer.append("<p>");
			buffer.append(multilineCommentDocumentation);
			buffer.append("</p>");
		}
		if (vcObject.getDescription()!=null) { // if there is a body
			if(vcObject instanceof Characteristic) {
				Characteristic characteristic = (Characteristic)vcObject;
				CharacteristicType type = characteristic.getType();
				if(type instanceof SymbolicType) {
					SymbolicType symbolicType = (SymbolicType)type;
					if(!symbolicType.getValues().isEmpty()) {
						final Language defaultLanguage = VcmlUtils.getDefaultLanguage();
						buffer.append("<br/><br/>Values:<ul>");
						for(CharacteristicValue value : symbolicType.getValues()) {
							Description value_description = value.getDescription();
							if(value_description == null) {
								continue;
							}
							buffer.append("<li><b>" + value.getName() + "</b>");
							new DescriptionHandler() {
								@Override
								public void handleSingleDescription(Language language, String value) {
									buffer.append("<li><b>" + language.getName() + "</b>" + value == null ? "" : ": <em>" + value + "</em></li>");
								}
								@Override
								public Object caseMultiLanguageDescriptions(MultiLanguageDescriptions object) {
									for(MultiLanguageDescription mld : object.getDescriptions()) {
										if(mld.getLanguage() == defaultLanguage) {
											return doSwitch(mld);
										} 
									}
									return super.caseMultiLanguageDescriptions(object);
								}
								@Override
								public Object caseMultiLanguageDescription(MultiLanguageDescription description) {
									handleSingleDescription(description.getLanguage(), description.getValue());
									return this;
								}
								
							}.doSwitch(value_description);
							buffer.append("</li>");
						}
						buffer.append("</ul>");					
					}
				}
			}

			if(vcObject instanceof Dependency) {
				try {
					InputStream is = sourceUtils.getInputStream((Dependency)vcObject);
					if (is!=null) {
						buffer.append("<p><pre>");
						BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
						String line;
						while ((line = br.readLine()) != null) {
							boolean isCommentLine = line.startsWith("*");
							if (isCommentLine) {
								buffer.append("<span style='color: #008000'>");
							}
							buffer.append(line);
							if (isCommentLine) {
								buffer.append("</span>");
							}
							buffer.append("<br/>");
						}
						buffer.append("</pre></p>");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return buffer.toString();
	}
	
}
