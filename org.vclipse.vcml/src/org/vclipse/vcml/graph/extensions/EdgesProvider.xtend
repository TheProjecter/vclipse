package org.vclipse.vcml.graph.extensions

import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.VCObject
import org.vclipse.vcml.vcml.InterfaceDesign
import org.vclipse.vcml.vcml.CharacteristicGroup
import org.vclipse.vcml.vcml.Characteristic
import org.vclipse.vcml.vcml.ConfigurationProfile
import org.vclipse.vcml.vcml.Classification
import org.vclipse.vcml.vcml.Class
class EdgesProvider {
	
	def static dispatch Iterable<Material> edges(Material material) {
		material.billofmaterials.map[items].flatten.map[item | item.material].filter(childMaterial | childMaterial.^type.equals("ZKMT"))
	}
 
 	def static dispatch Iterable<VCObject> edges(Classification classification) {
 	}
 
	def static dispatch Iterable<Characteristic> edges(Class klass) {
		klass.characteristics
	}
	
	def static dispatch Iterable<VCObject> edges(InterfaceDesign interfaceDesign) {
		
	}
	
	def static dispatch Iterable<VCObject> edges(CharacteristicGroup csticGroup) {
		
	}
	
	def static dispatch Iterable<VCObject> edges(Characteristic characteristic) {
		
	}
	
	def static dispatch Iterable<VCObject> edges(ConfigurationProfile configurationProfile) {
		
	}
}