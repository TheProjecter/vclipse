package org.vclipse.vcml.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.vclipse.vcml.utils.DependencySourceUtils;
import org.vclipse.vcml.vcml.Import;
import org.vclipse.vcml.vcml.Model;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class VcmlResourceContainerState implements IAllContainersState {
	
	@Inject
	private DependencySourceUtils sourceUtils;
	
	public List<String> getVisibleContainerHandles(String handle) {
		List<String> visibleContainerHandles = Lists.newArrayList(handle);
		if(handle.contains("_diff")) {
			Resource resource = new ResourceSetImpl().getResource(URI.createURI(handle), true);
			if(resource != null) {
				EList<EObject> contents = resource.getContents();
				if(!contents.isEmpty()) {
					EObject eObject = contents.get(0);
					if(eObject instanceof Model) {
						for(Import immport : ((Model)eObject).getImports()) {
							String[] parts = immport.getImportURI().split("/");
							URI importedUri = URI.createURI(handle).trimSegments(parts.length - 1);
							for(int i=1; i<parts.length; i++) {
								importedUri = importedUri.appendSegment(parts[i]);
							}
							visibleContainerHandles.add(importedUri.toString());
						}
					}
				}
			}
		}
		return visibleContainerHandles;
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
