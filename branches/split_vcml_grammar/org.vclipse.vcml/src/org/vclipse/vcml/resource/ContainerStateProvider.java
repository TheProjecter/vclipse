package org.vclipse.vcml.resource;

import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersState;

public class ContainerStateProvider implements IAllContainersState.Provider {

	public IAllContainersState get(IResourceDescriptions context) {
		return new ResourceSetBasedAllContainersState();
	}
}
