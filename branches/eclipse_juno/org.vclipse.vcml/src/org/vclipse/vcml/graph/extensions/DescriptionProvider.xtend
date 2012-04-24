package org.vclipse.vcml.graph.extensions

import org.vclipse.vcml.vcml.Material
import org.vclipse.vcml.vcml.SimpleDescription
import org.vclipse.vcml.vcml.Class
class DescriptionProvider {
	
	def static dispatch String description(Material material) {
		(material.description as SimpleDescription).value
	}
	
	def static dispatch String description(Class klass) {
		(klass.description as SimpleDescription).value
	}
} 