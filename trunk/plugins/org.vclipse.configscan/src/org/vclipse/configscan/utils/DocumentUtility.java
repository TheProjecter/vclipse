package org.vclipse.configscan.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.IStatus;
import org.vclipse.configscan.ConfigScanPlugin;
import org.vclipse.configscan.impl.model.TestRunAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentUtility {

	public static final String LOG_RESULTS = "log_results";
	
	public static final String LOG_RESULT = "log_result";
	
	public static final String LOG_SESSION = "log_session";
	
	public static final String NODE_NAME_LOG_MSG = "log_msg";
	
	public static final String LOG_HEADER = "log_header";
	
	public static final String LOG_UNDOTG = "log_undotg";
	
	public static final String END_SESSION = "End session";
	
	public static final String TITLE = "title";
	
	public static final String ATTRIBUTE_STATUS = "status";
	
	public static final String STATUS_SUCCESS = "S";
	
	public static final String INPUT = "input";
	
	public static final String NAME = "name";
	
	public static final String DATE = "date";
	
	// do not use injection for document builder -> there is a NullPointerException
	// during fetching the deferred nodes in the tree viewer
	private DocumentBuilder documentBuilder;
	
	public DocumentUtility() {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException exception) {
			ConfigScanPlugin.log(exception.getMessage(), IStatus.ERROR);
		}
	}
	
	public Node getLogSession(Document document) {
		Node logResultNode = document.getDocumentElement().getFirstChild().getNextSibling();
		String nodeName = logResultNode.getNodeName();
		if(!LOG_RESULT.equals(nodeName)) {
			throw new IllegalArgumentException("Expected log_result tag in the document. The tag was " + nodeName);
		} else {
			NodeList childNodes = logResultNode.getChildNodes();
			for(int i=0; i<childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				nodeName = item.getNodeName();
				if(item.getNodeType() == Node.ELEMENT_NODE) { 
					if(LOG_SESSION.equals(nodeName)) {
						return item;
					}
				}
			}
		}
		return null;
	}
	
	public Node getLogResult(Document document) {
		return document.getDocumentElement().getFirstChild().getNextSibling();
	}
	
	public Node getLogResults(Document document) {
		return document.getDocumentElement();
	}
	
	public boolean hasSuccessStatus(Element element) {
		return STATUS_SUCCESS.equals(element.getAttribute(ATTRIBUTE_STATUS));
	}
	
	public Document parse(String xmlString) {
		try {
			return documentBuilder.parse(new org.xml.sax.InputSource(new StringReader(xmlString)));
		} catch (SAXException e) {
			return documentBuilder.newDocument();
		} catch (IOException e) {
			return documentBuilder.newDocument();
		}
	}
	
	public String parse(Document document) {
		try {		
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document.getDocumentElement()), 
					new StreamResult(new OutputStreamWriter(outputStream)));			
			return outputStream.toString();
		} catch (TransformerException e) {
			return null;
		}
	}

	public int getNumberOfFailure(Element element) {
		int failure = 0;
		NodeList list = element.getElementsByTagName(NODE_NAME_LOG_MSG);
		for(int i = 0; i<list.getLength(); i++) {
			Node node = list.item(i);
			if(node instanceof Element) {
				if(!hasSuccessStatus((Element)node)) {
					failure++;
				}
			}
		}
		return failure;
	}
	
	public int getNumberOfRuns(Element element) {
		return element.getElementsByTagName(NODE_NAME_LOG_MSG).getLength();
	}
	
	public int getNumberOfSuccess(Element element) {
		int success = 0;
		NodeList list = element.getElementsByTagName(NODE_NAME_LOG_MSG);
		for(int i = 0; i<list.getLength(); i++) {
			Node node = list.item(i);
			if(node instanceof Element) {
				if(hasSuccessStatus((Element) node)) {
					success++;
				}
			}
		}
		return success;
	}

	public boolean passesNodeFilter(Element element) {
		// we do not want see following elements in the tree
		String name = element.getNodeName();
		String title = element.getAttribute(TITLE);
		if(LOG_RESULTS.equals(name) 
				|| LOG_HEADER.equals(name) 
					|| LOG_UNDOTG.equals(name) 
						|| END_SESSION.equals(name) 
							|| END_SESSION.equals(title)) {
			return false;
		}
		return true;
	}
	
	public boolean passesOptionsFilter(Element element, Map<String, Object> options) {
		Object object = options.get(TestRunAdapter.SKIP_MATERIAL_TESTS);
		if(object instanceof Boolean && (Boolean)object) {
			String commandAttribute = element.getAttribute("cmd");
			if(commandAttribute.equals("CHECK ITEM QUANTITY")
					|| commandAttribute.equals("CHECK ITEM EXIST")
						|| commandAttribute.equals("CHECK ITEM STATUS")) {
				return false;
			}
		}
		return true;
	}

	public Document newDocument() {
		return documentBuilder.newDocument();
	}

	public Document parse(InputStream imputStream) {
		try {
			return documentBuilder.parse(imputStream);
		} catch (IOException exception) {
			ConfigScanPlugin.log(exception.getMessage(), IStatus.ERROR);
		} catch (SAXException exception) {
			ConfigScanPlugin.log(exception.getMessage(), IStatus.ERROR);
		}
		return null;
	}
}
