package org.vclipse.vcml.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.xtext.EcoreUtil2;
import org.vclipse.vcml.vcml.ConditionSource;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.ConstraintSource;
import org.vclipse.vcml.vcml.Dependency;
import org.vclipse.vcml.vcml.Precondition;
import org.vclipse.vcml.vcml.Procedure;
import org.vclipse.vcml.vcml.ProcedureSource;
import org.vclipse.vcml.vcml.SelectionCondition;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DependencySourceUtils {
	
	public static final String EXTENSION_CONSTRAINT = "cons";
	public static final String EXTENSION_PRECONDITION = "pre";
	public static final String EXTENSION_PROCEDURE = "proc";
	public static final String EXTENSION_SELECTIONCONDITION = "sel";
	public static final String EXTENSION_VCML = "vcml";

	public static final String SUFFIX_SOURCEFOLDER = "-dep";

	public EObject getSource(Dependency object) {
		Resource resource = object.eResource();
		URI sourceURI = getSourceURI(object);
		List<EObject> contents = Lists.newArrayList();
		try {
			contents = resource.getResourceSet().getResource(sourceURI, true).getContents();
		} catch(Exception exception) {
			// resource does not exists
			return null;
		}
		if(!contents.isEmpty()) {
			return contents.get(0);
		}
		return null;
		
	}
	
	public ProcedureSource getProcedureSource(Procedure object) {
		return (ProcedureSource)getSource(object);
	}
	
	public ConstraintSource getConstraintSource(Constraint object) {
		return (ConstraintSource)getSource(object);
	}
	
	public ConditionSource getPreconditionSource(Precondition object) {
		return (ConditionSource)getSource(object);
	}
	
	public ConditionSource getSelectionConditionSource(SelectionCondition object) {
		return (ConditionSource)getSource(object);
	}
	
	public InputStream getInputStream(Dependency object) throws IOException {
		URIConverter uriConverter = URIConverter.INSTANCE;
		return uriConverter.createInputStream(getSourceURI(object));
	}
	
	public OutputStream getOutputStream(Dependency object) throws IOException {
		URIConverter uriConverter = URIConverter.INSTANCE;
		return uriConverter.createOutputStream(getSourceURI(object));
	}
	
	public String getFilename(Dependency object) {
		if (object instanceof Procedure) {
			return getFilename((Procedure)object);
		} else if (object instanceof Constraint) {
			return getFilename((Constraint)object);
		} else if (object instanceof Precondition) {
			return getFilename((Precondition)object);
		} else if (object instanceof SelectionCondition) {
			return getFilename((SelectionCondition)object);
		} else {
			throw new IllegalArgumentException("unknown dependency " + object);
		}
	}
	
	private String getFilename(Constraint object) {
		return encode(object.getName()) + "." + EXTENSION_CONSTRAINT;
	}

	private String getFilename(Precondition object) {
		return encode(object.getName()) + "." + EXTENSION_PRECONDITION;
	}

	private String getFilename(Procedure object) {
		return encode(object.getName()) + "." + EXTENSION_PROCEDURE;
	}

	private String getFilename(SelectionCondition object) {
		return encode(object.getName()) + "." + EXTENSION_SELECTIONCONDITION;
	}

	private String encode(String s) {
		try {
			// Hack: we need to encode the encoded URL again. Otherwise, a new folder A would be created for a dependency with name A/B.
			return URLEncoder.encode(URLEncoder.encode(s, "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new WrappedException(e);
		}
	}
	
	public URI getSourceURI(Dependency object) {
		URIConverter uriConverter = URIConverter.INSTANCE;
		URI baseUri = EcoreUtil2.getNormalizedResourceURI(object).trimFileExtension();
		baseUri = baseUri.trimSegments(1).appendSegment(baseUri.lastSegment() + SUFFIX_SOURCEFOLDER).appendSegment(getFilename(object));
		if(!uriConverter.exists(baseUri, Maps.newHashMap())) {
			String fileName = baseUri.trimFileExtension().lastSegment();
			if(fileName.equals(fileName.toUpperCase())) {
				return URI.createURI(baseUri.toString().replace(fileName, fileName.toLowerCase()));
			} else {
				return URI.createURI(baseUri.toString().replace(fileName, fileName.toUpperCase()));
			}
		}
		return baseUri;
	}
	
	public URI getVcmlResourceURI(URI dependencySourceUri) {
		URI folderURI = dependencySourceUri.trimSegments(1);
		String folderName = folderURI.lastSegment();
		if (folderName.endsWith(SUFFIX_SOURCEFOLDER)) {
			return folderURI.trimSegments(1).appendSegment(folderName.substring(0,folderName.length() - SUFFIX_SOURCEFOLDER.length())).appendFileExtension(EXTENSION_VCML);
		}
		return URI.createURI(folderName); // TODO currently not clear what to do in this situation
	}
	
}
