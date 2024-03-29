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
package org.vclipse.idoc;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.serializer.ISerializer;
import org.vclipse.idoc.conversion.IDocValueConverter;
import org.vclipse.idoc.formatting.IDocSerializer;
import org.vclipse.idoc.iDoc.IDocFactory;
import org.vclipse.idoc.resource.IDocResourceDescriptionManager;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class IDocRuntimeModule extends AbstractIDocRuntimeModule {

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return IDocValueConverter.class;
	}

	// use own serializer since this is much more performant
	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return IDocSerializer.class;
	}

	public Class<? extends IResourceDescription.Manager> bindIResourceDescriptionManager() {
		return IDocResourceDescriptionManager.class;
	}

	public IDocFactory bindIDocFactory() {
		return IDocFactory.eINSTANCE;
	}
	
}
