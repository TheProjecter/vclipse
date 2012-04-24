package org.vclipse.vcml.graph.extensions

import org.vclipse.vcml.vcml.Classification
import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.Class
import org.vclipse.vcml.vcml.VCObject
class NameProvider {
	
	def static dispatch String name(VCObject vcobject) {
		return vcobject.name
	}
	
	def static dispatch String name(Material material) {
		return material.name
	}
	
	def static dispatch String name(Class klass) {
		return klass.name
	}
	 
	def static dispatch String name(Classification classification) {
		return classification.cls.name
	}
}