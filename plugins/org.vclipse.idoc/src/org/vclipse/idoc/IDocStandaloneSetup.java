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

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class IDocStandaloneSetup extends IDocStandaloneSetupGenerated{

	public static void doSetup() {
		new IDocStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

