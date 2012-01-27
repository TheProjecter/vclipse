package org.vclipse.configscan.impl.model;

import org.eclipse.emf.common.util.URI;
import org.vclipse.configscan.utils.DocumentUtility;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TestCase {
	
	public enum Status {
		SUCCESS, FAILURE
	}
	
	public TestCase(TestCase parent) {
		this.parent = parent;
	}
	
	private TestCase parent;
	
	private URI sourceURI;
	
	private Node inputNode;
	
	private Node logNode;
	
	public String getTitle() {
		if(logNode instanceof Element) {
			return ((Element)logNode).getAttribute(DocumentUtility.ATRRIBUTE_TITLE);			
		} 
		return null;
	}
	
	public Status getStatus() {
		if(logNode instanceof Element) {
			String statusAttribute = ((Element)logNode).getAttribute(DocumentUtility.ATTRIBUTE_STATUS);
			return statusAttribute.equals(DocumentUtility.STATUS_SUCCESS) ? Status.SUCCESS : Status.FAILURE;
		}
		return Status.FAILURE;
	}
	
	public void setParent(TestCase parent) {
		this.parent = parent;
	}
	
	public void setSourceURI(URI sourceURI) {
		this.sourceURI = sourceURI;
	}
	
	public void setInputElement(Node inputNode) {
		this.inputNode = inputNode;
	}
	
	public void setLogElement(Node logNode) {
		this.logNode = logNode;
	}

	public TestCase getParent() {
		return parent;
	}

	public URI getSourceURI() {
		return sourceURI;
	}

	public Node getInputElement() {
		return inputNode;
	}

	public Node getLogElement() {
		return logNode;
	}

	public TestCase getRoot() {
		return getRoot(this);
	}
	
	protected TestCase getRoot(TestCase testCase) {
		TestCase parent = testCase.getParent();
		if(parent == null) {
			return testCase;
		} else {
			return getRoot(parent);
		}
	}
	
	@Override
	public String toString() {
		return "Title={" + getTitle() + "} " +
				"Status={" + getStatus() + 
				"} Source uri={" + sourceURI.toString() + 
				"}" +  " Parent={" + parent == null 
				? null : parent.getTitle() + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result + getStatus().hashCode();
		result = prime * result + ((sourceURI == null) ? 0 : sourceURI.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;			
		} else if(object == null) {
			return false;
		} else if(getClass() != object.getClass()) {
			return false;
		}
		TestCase other = (TestCase) object;
		String title = getTitle();
		if(title == null) {
			if(other.getTitle() != null) {
				return false;
			}
		} else if(title.equals(other.getTitle())) {
			if(getStatus().equals(other.getStatus())) {
				if(sourceURI == null) {
					if(other.getSourceURI() != null) {
						return false;
					}
				} else if(sourceURI.equals(other.getSourceURI())) {
					return true;
				}
			}
		}
		return false;
	}
}
