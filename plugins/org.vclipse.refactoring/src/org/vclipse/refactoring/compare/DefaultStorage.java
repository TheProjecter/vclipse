package org.vclipse.refactoring.compare;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.serializer.ISerializer;

import com.google.common.base.Charsets;

public class DefaultStorage implements IEncodedStorage {

	protected String DEFAULT_NAME = "no name";
	protected String DEFAULT_CONTENT = "";
	protected String DEFAULT_ENCODING = Charsets.UTF_8.name();
	
	protected ISerializer serializer;
	protected IQualifiedNameProvider nameProvider;
	
	public DefaultStorage() {
		
	}
	
	public DefaultStorage(ISerializer serializer, IQualifiedNameProvider nameProvider) {
		if(serializer == null) {
			throw new IllegalArgumentException("Serializer is null.");
		}
		this.serializer = serializer;
		this.nameProvider = nameProvider;
	}
	
	@Override
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(DEFAULT_CONTENT.getBytes());
	}
	
	@Override
	public String getCharset() throws CoreException {
		return DEFAULT_ENCODING;
	}
	
	@Override
	public String getName() {
		return DEFAULT_NAME;
	}

	@Override
	public IPath getFullPath() {
		return null;
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return null;
	}
}
