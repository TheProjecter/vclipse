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
package org.vclipse.vcml.ui.actions.dependencynet;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.vclipse.vcml.ui.actions.BAPIUtils;
import org.vclipse.vcml.ui.actions.constraint.ConstraintReader;
import org.vclipse.vcml.utils.VCMLObjectUtils;
import org.vclipse.vcml.utils.VCMLProxyFactory;
import org.vclipse.vcml.utils.VcmlUtils;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.DependencyNet;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.Option;

import com.google.inject.Inject;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class DependencyNetReader extends BAPIUtils {

	@Inject
	private ConstraintReader constraintReader;

	public DependencyNet read(String depNetName, Model vcmlModel, IProgressMonitor monitor, Set<String> seenObjects, List<Option> options, boolean recurse) throws JCoException {
		if(depNetName == null || !seenObjects.add("DependencyNet/" + depNetName.toUpperCase())) {
			return null;
		}
		DependencyNet object = VCML.createDependencyNet();
		object.setName(depNetName);
		JCoFunction function = getJCoFunction("CARD_CONSTRAINT_NET_READ", monitor);
		JCoParameterList ipl = function.getImportParameterList();
		ipl.setValue("CONSTRAINT_NET", depNetName);
		
		handleOptions(options, ipl, "CHANGE_NO", "DATE");
		
		try {
			execute(function, monitor, depNetName);
			JCoParameterList epl = function.getExportParameterList();
			JCoStructure dependencyData = epl.getStructure("BASIC_DATA");
			String depType = dependencyData.getString("DEP_TYPE");
			if (!"CNET".equals(depType))
				error.println("ERROR: " + depNetName + " is not a dependency net - it has dependency type " + depType);
			object.setStatus(VcmlUtils.createStatusFromInt(dependencyData.getInt("STATUS")));
			object.setGroup(nullIfEmpty(dependencyData.getString("GROUP")));
			JCoParameterList tpl = function.getTableParameterList();
			object.setDescription(readDescription(tpl.getTable("DESCRIPTION"), "LANGUAGE_ISO", "LANGUAGE", "DESCRIPT"));
			object.setDocumentation(readMultiLanguageDocumentations(tpl.getTable("DOCUMENTATION")));
			JCoTable constraints = tpl.getTable("CONSTRAINTS");
			if (constraints.getNumRows() > 0) {
				for (int i = 0; i < constraints.getNumRows(); i++) {
					constraints.setRow(i);
					String constraintName = constraints.getString("DEPENDENCY");
					Constraint constraint = null;
					if (recurse) {
						if(monitor.isCanceled()) {
							return null;
						}
						constraint = constraintReader.read(constraintName, vcmlModel.eResource(), monitor, seenObjects, options, recurse);
					}
					if (constraint==null) {
						constraint = VCMLProxyFactory.createConstraintProxy(vcmlModel.eResource(), constraintName);
					}
					object.getConstraints().add(constraint);
				}
				VCMLObjectUtils.sortConstraints(object.getConstraints());
			}
		} catch (AbapException e) {
			handleAbapException(e);
		}
		vcmlModel.getObjects().add(object);
		return object;
	}

}