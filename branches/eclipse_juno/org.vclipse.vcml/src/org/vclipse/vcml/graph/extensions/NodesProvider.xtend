package org.vclipse.vcml.graph.extensions

import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.Classification
import org.vclipse.vcml.vcml.VCObject
import org.vclipse.vcml.vcml.Class
class NodesProvider {
	
	def static dispatch Iterable<Classification> nodes(Material material) {
		material.classifications
	}
	 
	def static dispatch Iterable<VCObject> nodes(Class klass) {
		
	}
}