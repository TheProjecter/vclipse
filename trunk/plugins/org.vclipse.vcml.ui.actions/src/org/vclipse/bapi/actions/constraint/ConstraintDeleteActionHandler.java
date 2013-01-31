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
package org.vclipse.bapi.actions.constraint;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.vclipse.bapi.actions.BAPIUtils;
import org.vclipse.bapi.actions.IBAPIActionRunner;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.DependencyNet;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.VCObject;
import org.vclipse.vcml.vcml.VcmlModel;
import org.vclipse.vcml.vcml.VcmlPackage;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

public class ConstraintDeleteActionHandler  extends BAPIUtils implements IBAPIActionRunner<Constraint> {

	public boolean isEnabled(Constraint object) {
		return isConnected();
	}

	public void run(Constraint object, Resource resource, IProgressMonitor monitor, Map<String, VCObject> seenObjects, List<Option> globalOptions) throws JCoException {
		// determine name of containing dependencyNet
		// TODO implement finding containing dependency net with ECoreUtils
		DependencyNet dependencyNet = null;
		VcmlModel model = (VcmlModel)object.eContainer();
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
		
		handleOptions(object.getOptions(), globalOptions, ipl, "CHANGE_NO", null);
		
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
