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
package org.vclipse.vcml.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.vclipse.vcml.utils.DescriptionHandler;
import org.vclipse.vcml.utils.VCMLUtils;
import org.vclipse.vcml.vcml.Assignment;
import org.vclipse.vcml.vcml.BOMItem;
import org.vclipse.vcml.vcml.BillOfMaterial;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicGroup;
import org.vclipse.vcml.vcml.CharacteristicOrValueDependencies;
import org.vclipse.vcml.vcml.CharacteristicReference_P;
import org.vclipse.vcml.vcml.CharacteristicValue;
import org.vclipse.vcml.vcml.Class;
import org.vclipse.vcml.vcml.CompoundStatement;
import org.vclipse.vcml.vcml.ConditionSource;
import org.vclipse.vcml.vcml.ConditionalStatement;
import org.vclipse.vcml.vcml.ConfigurationProfile;
import org.vclipse.vcml.vcml.ConfigurationProfileEntry;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.ConstraintSource;
import org.vclipse.vcml.vcml.DelDefault;
import org.vclipse.vcml.vcml.DependencyNet;
import org.vclipse.vcml.vcml.Description;
import org.vclipse.vcml.vcml.Documentation;
import org.vclipse.vcml.vcml.FormattedDocumentationBlock;
import org.vclipse.vcml.vcml.Function;
import org.vclipse.vcml.vcml.InterfaceDesign;
import org.vclipse.vcml.vcml.IsInvisible;
import org.vclipse.vcml.vcml.IsSpecified_C;
import org.vclipse.vcml.vcml.IsSpecified_P;
import org.vclipse.vcml.vcml.Language;
import org.vclipse.vcml.vcml.LocalPrecondition;
import org.vclipse.vcml.vcml.LocalSelectionCondition;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.MultiLanguageDescription;
import org.vclipse.vcml.vcml.MultiLanguageDescriptions;
import org.vclipse.vcml.vcml.MultipleLanguageDocumentation;
import org.vclipse.vcml.vcml.MultipleLanguageDocumentation_LanguageBlock;
import org.vclipse.vcml.vcml.NumericType;
import org.vclipse.vcml.vcml.Precondition;
import org.vclipse.vcml.vcml.Procedure;
import org.vclipse.vcml.vcml.ProcedureLocation;
import org.vclipse.vcml.vcml.ProcedureSource;
import org.vclipse.vcml.vcml.SelectionCondition;
import org.vclipse.vcml.vcml.SetDefault;
import org.vclipse.vcml.vcml.SymbolicType;
import org.vclipse.vcml.vcml.Table;
import org.vclipse.vcml.vcml.VariantFunction;
import org.vclipse.vcml.vcml.VariantTable;
import org.vclipse.vcml.vcml.util.VcmlSwitch;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class VCMLLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public VCMLLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String image(BillOfMaterial element) {
		return "b_slis.gif";
	}
	
	public String image(BOMItem element) {
		return "b_slip.gif";
	}
	
	public String image(Characteristic element) {
		return "s_chaa.gif";
	}
	
	public String image(CharacteristicGroup element) {
		return "b_aboa.gif";
	}

	public String image(CharacteristicValue element) {
		return "b_kons.gif";
	}

	public String image(Class element) {
		return "b_clas.gif";
	}
	
	public String image(ConfigurationProfile element) {
		return "b_conf.gif";
	}

	public String image(Constraint element) {
		return "snapgr.gif";
	}
	
	public String image(DependencyNet element) {
		return "magrid.gif";
	}
	
	public String image(InterfaceDesign element) {
		return "bwvisu.gif";
	}
	
	public String image(LocalPrecondition element) {
		return "b_mbed.gif";
	}
	
	public String image(LocalSelectionCondition element) {
		return "b_bedi.gif";
	}
	
	public String image(Material element) {
		return "b_matl.gif";
	}
	
	public String image(Precondition element) {
		return "b_mbed.gif";
	}
	
	public String image(Procedure element) {
		return "b_bwfo.gif";
	}
	
	public String image(SelectionCondition element) {
		return "b_bedi.gif";
	}
	
	public String image(VariantFunction element) {
		return "b_abal.gif";
	}
	
	public String image(VariantTable element) {
		return "dbtabl.gif";
	}
	
	public String image(CharacteristicOrValueDependencies element) {
		return "b_rela.gif";
	}
	
	public String image(Description element) {
		return "b_text.gif";
	}
	
	public String image(Documentation element) {
		return "b_anno.gif";
	}
	
	public String text(BillOfMaterial element) {
		return "BOM";
	}

	public String text(BOMItem element) {
		return element.getItemnumber() + " " + element.getMaterial().getName();
	}

	public StyledString text(Characteristic element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(Class element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(Constraint element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(DependencyNet element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(Material element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(Precondition element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(Procedure element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(SelectionCondition element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(VariantFunction element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	public StyledString text(VariantTable element) {
		return createStyledString(element.getName(), element.getDescription());
	}
	
	private StyledString createStyledString(String name, Description description) { 
		final StyledString result = new StyledString(name); 
		new DescriptionHandler() {
			private Language defaultLanguage = VCMLUtils.getDefaultLanguage(); 
			@Override
			public void handleSingleDescription(Language language, String value) {
				if (defaultLanguage.equals(language)) {
					result.append(new StyledString(" " + value, StyledString.DECORATIONS_STYLER)); 
				}
			}
		}.handleDescription(description);
		return result;
	}
	
	public String text(MultiLanguageDescriptions element) {
		final StringBuffer label = new StringBuffer();
		new DescriptionHandler() {
			private Language defaultLanguage = VCMLUtils.getDefaultLanguage(); 
			@Override
			public void handleSingleDescription(Language language, String value) {
				int length = label.length();
				if (length==0 || defaultLanguage.equals(language)) {
					label.delete(0, length);
					label.append(value);
				}
			}
		}.handleDescription(element);
		return label.toString();
	}
	
	public String text(MultiLanguageDescription element) {
		return element.getLanguage() + " " + element.getValue();
	}
	
	public String text(MultipleLanguageDocumentation element) {
		final StringBuffer label = new StringBuffer();
		new VcmlSwitch<Object>() {
			private Language defaultLanguage = VCMLUtils.getDefaultLanguage(); 
			@Override
			public Object caseFormattedDocumentationBlock(
					FormattedDocumentationBlock object) {
				label.append(object.getValue());
				label.append(' ');
				return this;
			}
			@Override
			public Object caseMultipleLanguageDocumentation_LanguageBlock(
					MultipleLanguageDocumentation_LanguageBlock object) {
				int length = label.length();
				if (length==0 || defaultLanguage.equals(object.getLanguage())) {
					label.delete(0, length);
					for(FormattedDocumentationBlock fdb : object.getFormattedDocumentationBlocks()) {
						doSwitch(fdb);
					}
				}
				return this;
			}
			@Override
			public Object caseMultipleLanguageDocumentation(
					MultipleLanguageDocumentation object) {
				for(MultipleLanguageDocumentation_LanguageBlock lb : object.getLanguageblocks()) {
					doSwitch(lb);
				}
				return this;
			}
		}.doSwitch(element);
		return label.toString();
	}
	
	public String text(FormattedDocumentationBlock element) {
		String format = element.getFormat();
		return (format==null ? "" : (format + " ")) + element.getValue();
	}
	
	public String text(ConfigurationProfileEntry element) {
		return element.getSequence() + " " + text(element.getDependency());
	}
	
	public String text(SymbolicType element) {
		return "CHAR " + element.getNumberOfChars();
	}
	
	public String text(NumericType element) {
		return "NUM " + element.getNumberOfChars() + "." + element.getDecimalPlaces();
	}

	public String text(Assignment element) {
		return text(element.getCharacteristic()) + " = ...";
	}
	
	public String text(SetDefault element) {
		return text(element.getCharacteristic()) + " ?= ...";
	}
	
	public String text(DelDefault element) {
		return "$del_default " + text(element.getCharacteristic());
	}
	
	public String text(ConditionalStatement element) {
		return "... if ...";
	}
	
	public String text(CharacteristicReference_P element) {
		ProcedureLocation location = element.getLocation();
		return (location!=null ? location.getLiteral() + "." : "") + text(element.getCharacteristic());
	}
	
	public String text(ProcedureSource element) {
		return "source";
	}

	public String text(ConstraintSource element) {
		return "source";
	}

	public String text(ConditionSource element) {
		return "source";
	}

	public String text(LocalPrecondition element) {
		return "local precondition";
	}

	public String text(LocalSelectionCondition element) {
		return "local selection condition";
	}

	public String text(CharacteristicOrValueDependencies element) {
		return "dependencies";
	}
	
	public String text(Function element) {
		return text(element.getFunction()) + " (...)";
	}
	
	public String text(Table element) {
		return text(element.getTable()) + " (...)";
	}

	public String text(CompoundStatement element) {
		return "(..., ...)";
	}
	
	public String text(IsSpecified_C element) {
		return text(element.getCharacteristic()) + "is specified";
	}

	public String text(IsSpecified_P element) {
		return text(element.getCharacteristic()) + "is specified";
	}

	public String text(IsInvisible element) {
		return text(element.getCharacteristic()) + "is invisible ";
	}

	public String image(MultiLanguageDescription element) {
		return languageIcon(element.getLanguage());
	}
	
	public String image(MultipleLanguageDocumentation_LanguageBlock element) {
		return languageIcon(element.getLanguage());
	}
	
	private String languageIcon (Language language) {
		switch (language) {
			case EN : return "gb.png";
			case DE : return "de.png";
			case ES : return "es.png";
			case FR : return "fr.png";
			case JA : return "jp.png";
			case PT : return "pt.png";
			case RU : return "ru.png";
			default : return "";
		}
	}

}
