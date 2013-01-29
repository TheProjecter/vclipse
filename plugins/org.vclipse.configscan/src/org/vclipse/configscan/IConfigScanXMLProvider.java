package org.vclipse.configscan;
/*******************************************************************************
 * Copyright (c) 2012 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    webXcerpt Software GmbH - initial creator
 ******************************************************************************/

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.vclipse.configscan.impl.ConfigScanXmlProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.ImplementedBy;

@ImplementedBy(ConfigScanXmlProvider.class)
public interface IConfigScanXMLProvider {
	
	public Document transform(EObject model, ITestObjectFilter filter, Map<Element, URI> map, Map<Object, Object> options);
	
	public String getMaterialNumber(EObject model);
}
