package org.vclipse.vcml.resource;

import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;

import com.google.inject.Inject;

public class VcmlStateBasedContainerManager extends StateBasedContainerManager {

	@Inject
	private VcmlContainerStateProvider containerStateProvider;
	
	@Override
	protected String internalGetContainerHandle(IResourceDescription description, IResourceDescriptions resourceDescriptions) {
		return containerStateProvider.get(resourceDescriptions).getContainerHandle(description.getURI());
	}
}
