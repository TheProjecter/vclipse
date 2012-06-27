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
package org.vclipse.vcml.ui.actions.variantfunction;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.vclipse.vcml.ui.actions.BAPIUtils;
import org.vclipse.vcml.ui.outline.actions.IVcmlOutlineActionHandler;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.VariantFunction;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class VariantFunctionDeleteActionHandler extends BAPIUtils implements IVcmlOutlineActionHandler<VariantFunction>{

	@Override
	public boolean isEnabled(VariantFunction object) {
		return isConnected();
	}

	@Override
	public void run(VariantFunction object, Resource resource, IProgressMonitor monitor, Set<String> seenObjects, List<Option> options) throws JCoException {
		String name = object.getName();
		beginTransaction();
		JCoFunction function = getJCoFunction("CAMA_FUNCTION_MAINTAIN", monitor);
		// TODO insert ipl
		JCoTable varFunctionBasicData = function.getTableParameterList().getTable("VAR_FUNCTION_BASIC_DATA");
		varFunctionBasicData.appendRow();
		varFunctionBasicData.setValue("VFUNC_NAME", name);
		varFunctionBasicData.setValue("FLDELETE", "X");
		try {
			execute(function, monitor, "DELETE " + name);
			endTransaction();
		} catch (AbapException e) {
			handleAbapException(e);
		}
	}

}	
