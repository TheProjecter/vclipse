/*
 * generated by Xtext
 */
package org.vclipse.idoc.validation

import org.eclipse.xtext.validation.Check
import org.vclipse.idoc.iDoc.IDocPackage
import org.vclipse.idoc.iDoc.Segment
import org.vclipse.idoc.iDoc.StringField
import org.eclipse.xtext.validation.ValidationMessageAcceptor

//import org.eclipse.xtext.validation.Check

/**
 * Custom validation rules. 
 *
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class IDocValidator extends AbstractIDocValidator {

	private static val LENGTH_LIMIT = 72;
	
	@Check
	def checkSegment(Segment segment) {
		if ("E1CUKNM".equals(segment.type)) {
			for (field : segment.fields) {
				if ("LINE".equals(field.name) && field instanceof StringField) {
					val stringfield = field as StringField;
					val length = stringfield.value.length;
					if (length > LENGTH_LIMIT) {
						error("Length of E1CUKNM LINE should be " + LENGTH_LIMIT + " characters maximum, but is " + length + " characters", stringfield, 
								IDocPackage$Literals::STRING_FIELD__VALUE, 
									ValidationMessageAcceptor::INSIGNIFICANT_INDEX); 
					}
				}
			}
		}
	}
}
