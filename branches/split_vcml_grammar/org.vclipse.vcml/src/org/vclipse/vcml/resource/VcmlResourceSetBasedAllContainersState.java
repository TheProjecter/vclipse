package org.vclipse.vcml.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;

public class VcmlResourceSetBasedAllContainersState  implements IAllContainersState {
	
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
		return URI.createURI(uri.toString().replace(uri.segment(uri.segmentCount() - 2) + "/" + uri.segment(uri.segmentCount() - 1), uri.segment(uri.segmentCount() - 2).replaceAll("_cml2", ".cml2").concat(".vcml"))).toString();
	}
}
