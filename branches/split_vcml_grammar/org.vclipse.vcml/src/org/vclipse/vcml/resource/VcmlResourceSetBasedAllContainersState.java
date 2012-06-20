package org.vclipse.vcml.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.vclipse.vcml.utils.DependencySourceUtils;

import com.google.inject.Inject;

public class VcmlResourceSetBasedAllContainersState  implements IAllContainersState {
	
	@Inject
	private DependencySourceUtils sourceUtils;
	
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
		String string = sourceUtils.getVcmlResourceURI(uri).toString();
		if(string.contains("_diff.vcml")) {
			string = string.replace("_diff.vcml", ".vcml");
		}
		return string;
	}
}
