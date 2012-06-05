package org.vclipse.vcml.resource;

import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider;

public class VcmlContainerStateProvider extends ResourceSetBasedAllContainersStateProvider {

	public IAllContainersState get(IResourceDescriptions context) {
		return new VcmlResourceSetBasedAllContainersState();
	}
	
}
