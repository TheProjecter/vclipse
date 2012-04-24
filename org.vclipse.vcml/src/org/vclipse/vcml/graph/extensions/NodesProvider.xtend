package org.vclipse.vcml.graph.extensions

import com.google.common.collect.Lists
import org.vclipse.vcml.vcml.Class
import org.vclipse.vcml.vcml.Classification
import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.VCObject
class NodesProvider {
	
	def static dispatch Iterable<VCObject> nodes(VCObject vcobject) {
		Lists::newArrayList()
	}
	
	def static dispatch Iterable<org.vclipse.vcml.vcml.Class> nodes(Classification classification) {
		Lists::newArrayList()
	}
	
	def static dispatch Iterable<Classification> nodes(Material material) {
		if(material.classifications == null) {
			Lists::newArrayList
		}
		material.classifications
	}
	 
	def static dispatch Iterable<VCObject> nodes(Class klass) {
		Lists::newArrayList
	}
}