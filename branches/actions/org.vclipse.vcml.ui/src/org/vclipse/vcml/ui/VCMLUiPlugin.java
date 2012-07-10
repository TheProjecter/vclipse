/*******************************************************************************
 * Copyright (c) 2010 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    webXcerpt Software GmbH - initial creator
 ******************************************************************************/
package org.vclipse.vcml.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.vclipse.vcml.ui.internal.VCMLActivator;

import com.google.inject.Injector;


/**
 * 
 */
public class VCMLUiPlugin extends VCMLActivator {

	/**
	 * 
	 */
	public static final String ID = "org.vclipse.vcml.ui";

	private static VCMLUiPlugin plugin;
	
	public static VCMLUiPlugin getDefault() {
		if(plugin == null) {
			plugin = new VCMLUiPlugin();
		}
		return plugin;
	}
	
	public Injector getInjector() {
		return getInstance().getInjector("org.vclipse.vcml.VCML");
	}
	
	/**
	 * @param status
	 */
	public static void log(final IStatus status) {
		getInstance().getLog().log(status);
	}
	
	/**
	 * @param message
	 * @param thr
	 */
	public static void log(final String message, final Throwable thr) {
		log(new Status(IStatus.ERROR, ID, IStatus.ERROR, message, thr));		
	}
	
	/**
	 * @param deleteEnabledImage
	 * @return
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		return getInstance().getImageRegistry().getDescriptor(key);
	}
	
	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
	 */
	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		addImage(IUiConstants.DOC_HIERARCHY_IMAGE, "icons/actions/doc_hierarchy.png");
		addImage(IUiConstants.SAP_HIERARCHY_IMAGE, "icons/actions/sap_hierarchy.png");
	}

	/**
	 * @param name
	 * @param path
	 */
	private void addImage(String name, String path) {
		Image image = imageDescriptorFromPlugin(ID, path).createImage();
		getInstance().getImageRegistry().put(name, image);
	}
}