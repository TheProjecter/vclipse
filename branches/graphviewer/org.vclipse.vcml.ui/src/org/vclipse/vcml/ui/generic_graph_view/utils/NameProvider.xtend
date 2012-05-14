package org.vclipse.vcml.ui.generic_graph_view.utils

import org.vclipse.vcml.vcml.Classification
import org.vclipse.vcml.vcml.ConfigurationProfileEntry
import org.vclipse.vcml.vcml.NumericCharacteristicValue
import org.vclipse.vcml.vcml.NumericInterval
import org.vclipse.vcml.vcml.NumericLiteral
import org.vclipse.vcml.vcml.SimpleDocumentation
import org.vclipse.vcml.vcml.SymbolicType
import org.vclipse.vcml.vcml.VCObject

class NameProvider {
	  
	def static dispatch String display(VCObject vcobject) {
		val description = DescriptionProvider::description(vcobject) + "\n"
		val classstring = " (" + vcobject.eClass.name.toLowerCase + ")" + "\n"
		val name = name(vcobject)
		if(description == null) {
			return classstring + name
		} else {
			return description + classstring + name
		}
	} 
	
	def static dispatch String display(Classification classification) {
		return display(classification.cls)
	}
	  
	def static dispatch String display(ConfigurationProfileEntry entry) {
		return "" + entry.sequence + " " + entry.dependency.name
	}
 
	def static dispatch String display(NumericCharacteristicValue value) {
		val entry = value.entry
		if(entry instanceof NumericInterval) {
			return (entry as NumericInterval).lowerBound + " .. " + (entry as NumericInterval).upperBound 
		} else if(entry instanceof NumericLiteral) {
			return (entry as NumericLiteral).value
		}
		return ""
	}
	
	def static dispatch String display(SymbolicType type) {
		return type.values.map(value | value.name).join(", ") 
	}
	
	def static dispatch String display(SimpleDocumentation documentation) {
		return "Documentation: " + documentation.value
	}
	
	def static String name(VCObject vcobject) {
		return vcobject.name
	}
}