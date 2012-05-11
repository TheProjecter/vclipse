package org.vclipse.vcml.ui.generic_graph_view.utils

import com.google.common.collect.Lists
import org.eclipse.emf.ecore.EObject
import org.vclipse.vcml.vcml.Class
import org.vclipse.vcml.vcml.DependencyNet
import org.vclipse.vcml.vcml.InterfaceDesign
import org.vclipse.vcml.vcml.Material
class NodesProvider {
	 
	def static dispatch nodes(EObject object) {
		Lists::newArrayList
	}
	
	def static dispatch nodes(InterfaceDesign interfaceDesign) {
		interfaceDesign.characteristicGroups
	}
	
	def static dispatch nodes(Class klass) {
		klass.characteristics
	}
	
	def static dispatch nodes(DependencyNet dependencyNet) {
		dependencyNet.constraints
	}
	
	def static dispatch hiddenNodes(EObject object) {
		Lists::newArrayList
	}

	def static dispatch hiddenNodes(Material material) {
		val nodes = Lists::<EObject>newArrayList
		nodes.addAll(material.classifications.map(classification | classification.cls))
		nodes.addAll(material.configurationprofiles.map(configurationprofile | configurationprofile.dependencyNets).flatten)
		nodes.addAll(material.configurationprofiles.map(configurationprofile | configurationprofile.entries).flatten)
		return nodes
	}
}