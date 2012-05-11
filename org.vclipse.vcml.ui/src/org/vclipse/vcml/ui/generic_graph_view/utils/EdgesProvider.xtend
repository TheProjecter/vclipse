package org.vclipse.vcml.ui.generic_graph_view.utils

import com.google.common.collect.Lists
import org.eclipse.emf.ecore.EObject
import org.vclipse.vcml.vcml.Characteristic
import org.vclipse.vcml.vcml.CharacteristicGroup
import org.vclipse.vcml.vcml.DateType
import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.NumericType
import org.vclipse.vcml.vcml.SymbolicType
class EdgesProvider {
	 
	def static dispatch edges(EObject vcobject) {
		return Lists::newArrayList
	} 

	def static dispatch edges(Material material) {
		material.billofmaterials.map[items].flatten.map[item | item.material].filter(childMaterial | childMaterial.^type.equals("ZKMT"))
	}
	
	def static dispatch hiddenEdges(EObject vcobject) {
		return Lists::newArrayList
	} 
	 
	def static dispatch hiddenEdges(Characteristic characteristic) {
		val edges = Lists::<EObject>newArrayList
		val type = characteristic.type
		if(type instanceof SymbolicType) {
			edges.addAll((type as SymbolicType).values)
		} else if(type instanceof NumericType) {
			edges.addAll((type as NumericType).values)
		} else if(type instanceof DateType) {
			edges.addAll((type as DateType).values)
		}
		return edges
	}
	 
	def static dispatch hiddenEdges(CharacteristicGroup csticGroup) {
		return csticGroup.characteristics
	} 
}