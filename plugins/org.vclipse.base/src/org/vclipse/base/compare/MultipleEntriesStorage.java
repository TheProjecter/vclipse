/*******************************************************************************
 * Copyright (c) 2010 - 2013 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     	webXcerpt Software GmbH - initial creator
 * 		www.webxcerpt.com
 ******************************************************************************/
package org.vclipse.base.compare;

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
			EObject entry = entries[i];
			String content = serializer.serialize(entry, options);
			contentsBuffer.append(content.trim());
			if(i != size) {
				contentsBuffer.append("\n");
			}
		}
		return new StringInputStream(contentsBuffer.toString());
	}

	@Override
	public String getName() {
		return entries.length == 0 ? super.getName() : entries[0].eClass().getName();
	}
	
	@Override
	public IPath getFullPath() {
		if(entries.length > 0) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			EObject entry = entries[0];
			URI uri = EcoreUtil.getURI(entry);
			String fragment = uri.fragment();
			if(fragment == null || fragment.isEmpty()) {
				return root.findMember(uri.toString()).getFullPath();
			}
			IResource member = root.findMember(uri.toPlatformString(true));
			return member == null ? null : member.getFullPath();
		}
		return null;
	}
}
