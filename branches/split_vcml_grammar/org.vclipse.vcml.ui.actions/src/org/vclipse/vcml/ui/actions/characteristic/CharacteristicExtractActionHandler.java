/*******************************************************************************
 * Copyright (c) 2010 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     webXcerpt Software GmbH - initial creator
 ******************************************************************************/
package org.vclipse.vcml.ui.actions.characteristic;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.vclipse.vcml.ui.outline.actions.IVcmlOutlineActionHandler;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.Model;

import com.sap.conn.jco.JCoException;

public class CharacteristicExtractActionHandler extends CharacteristicReader implements IVcmlOutlineActionHandler<Characteristic> {

	public boolean isEnabled(Characteristic object) {
		return isConnected();
	}

	public void run(Characteristic cstic, Resource resource, IProgressMonitor monitor, Set<String> seenObjects) throws JCoException {
		read(cstic.getName(), (Model)resource.getContents().get(0), monitor, seenObjects, false);
	}

}
