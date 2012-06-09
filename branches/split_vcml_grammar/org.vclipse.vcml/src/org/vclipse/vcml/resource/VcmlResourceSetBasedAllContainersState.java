package org.vclipse.vcml.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.inject.Inject;

public class VcmlResourceSetBasedAllContainersState  implements IAllContainersState {
	
	@Inject
	DependencySourceUtils sourceUtils;
	
	public List<String> getVisibleContainerHandles(String handle) {
		return Collections.singletonList(handle);
	}

	public Collection<URI> getContainedURIs(String containerHandle) {
		return Collections.singletonList(URI.createURI(containerHandle));
	}

	public boolean isEmpty(String containerHandle) {
		return false;
	}

	public String getContainerHandle(URI uri) {
		return sourceUtils.getVcmlResourceURI(uri).toString();
	}
}
