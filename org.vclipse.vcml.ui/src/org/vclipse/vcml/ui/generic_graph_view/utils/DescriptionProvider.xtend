package org.vclipse.vcml.ui.generic_graph_view.utils

import org.vclipse.vcml.vcml.Characteristic
import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.SimpleDescription
import org.vclipse.vcml.vcml.VCObject
class DescriptionProvider {
	
	def static dispatch String description(VCObject vcobject) {
		return ""
	}
	 
	def static dispatch description(Material material) {
		return (material.description as SimpleDescription).value
	}
	  
	def static dispatch description(Characteristic characteristic) {
		return (characteristic.description as SimpleDescription).value
	}
}  