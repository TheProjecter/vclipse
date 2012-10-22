package org.vclipse.refactoring.compare;

import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.SaveOptions.Builder;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.StringInputStream;

public class MultipleEntriesStorage extends DefaultStorage {

	private EObject[] entries;
	
	public MultipleEntriesStorage(ISerializer serializer, IQualifiedNameProvider nameProvider, EObject ... objects) {
		super(serializer, nameProvider);
		entries = objects;
	}
	
	@Override
	public InputStream getContents() throws CoreException {
		Builder saveOptionsBuilder = SaveOptions.newBuilder();
		SaveOptions options = saveOptionsBuilder.noValidation().format().getOptions();
		StringBuffer contentsBuffer = new StringBuffer();
		for(int i=0, size=entries.length-1; i<=size; i++) {
			String content = serializer.serialize(entries[i], options);
			contentsBuffer.append(content.trim());
			if(i != size) {
				contentsBuffer.append("\n");
			}
		}
		return new StringInputStream(contentsBuffer.toString());
	}

	@Override
	public String getName() {
		if(entries.length == 0) {
			return super.getName();
		} else {
			return entries[0].eClass().getName();
		}
	}
	
	@Override
	public IPath getFullPath() {
		if(entries.length == 0) {
			return null;
		} else {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			EObject entry = entries[0];
			URI uri = EcoreUtil.getURI(entry);
			String fragment = uri.fragment();
			if(fragment == null || fragment.isEmpty()) {
				return root.findMember(uri.toString()).getFullPath();
			} else {
				IResource member = root.findMember(uri.toPlatformString(true));
				return member == null ? null : member.getFullPath();
			}
		}
	}
}
