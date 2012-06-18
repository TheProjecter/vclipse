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
package org.vclipse.vcml.ui.actions.constraint;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.vclipse.vcml.ui.actions.BAPIUtils;
import org.vclipse.vcml.ui.outline.actions.IVcmlOutlineActionHandler;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.DependencyNet;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.VcmlPackage;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class ConstraintDeleteActionHandler  extends BAPIUtils implements IVcmlOutlineActionHandler<Constraint> {

	public boolean isEnabled(Constraint object) {
		return isConnected();
	}

	public void run(Constraint object, Resource resource, IProgressMonitor monitor, Set<String> seenObjects, List<Option> options) throws JCoException {
		// determine name of containing dependencyNet
		// TODO implement finding containing dependency net with ECoreUtils
		DependencyNet dependencyNet = null;
		Model model = (Model)object.eContainer();
		for (Object o : EcoreUtil.getObjectsByType(model.getObjects(), VcmlPackage.Literals.DEPENDENCY_NET)) {
			DependencyNet depNet = (DependencyNet)o;
			if (depNet.getConstraints().contains(object)) {
				dependencyNet = depNet;
				break;
			}
		}

		beginTransaction();
		JCoFunction function = getJCoFunction("CAMA_CNET_CONSTRAINT_MAINTAIN", monitor);
		JCoParameterList ipl = function.getImportParameterList();
		
		handleOptions(options, ipl, "CHANGE_NO", null);
		
		ipl.setValue("CONSTRAINT", object.getName());
		if (dependencyNet!=null) {
			ipl.setValue("CONSTRAINT_NET", dependencyNet.getName());
		}
		ipl.setValue("DELETE_FLAG", "X");
		try {
			execute(function, monitor, "DELETE " + object.getName());
			endTransaction();
		} catch (AbapException e) {
			handleAbapException(e);
		}
	}


}
