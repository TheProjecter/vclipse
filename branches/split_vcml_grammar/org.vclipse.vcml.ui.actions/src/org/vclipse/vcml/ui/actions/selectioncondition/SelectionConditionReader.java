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
package org.vclipse.vcml.ui.actions.selectioncondition;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.vclipse.vcml.ui.actions.BAPIUtils;
import org.vclipse.vcml.utils.VcmlUtils;
import org.vclipse.vcml.vcml.ConditionSource;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.SelectionCondition;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;

public class SelectionConditionReader extends BAPIUtils {

	public SelectionCondition read(String selectionConditionName, Resource resource, IProgressMonitor monitor, Set<String> seenObjects, boolean recurse) throws JCoException {
		if (!seenObjects.add("SelectionCondition/" + selectionConditionName)) {
			return null;
		}
		if(monitor.isCanceled()) {
			return null;
		}
		SelectionCondition object = VCML.createSelectionCondition();
		object.setName(selectionConditionName);
		Model model = (Model)resource.getContents().get(0);
		model.getObjects().add(object);
		JCoFunction function = getJCoFunction("CARD_DEPENDENCY_READ", monitor);
		JCoParameterList ipl = function.getImportParameterList();
		ipl.setValue("DEPENDENCY", selectionConditionName);
		// if the following flags are not checked, then the function performs just an existence check
		ipl.setValue("FL_WITH_BASIC_DATA", "X");
		ipl.setValue("FL_WITH_DESCRIPTION", "X");
		ipl.setValue("FL_WITH_DOCUMENTATION", "X");
		ipl.setValue("FL_WITH_SOURCE", "X");
		try {
			execute(function, monitor, selectionConditionName);
			JCoStructure dependencyData = function.getExportParameterList().getStructure("DEPENDENCY_DATA");
			String depType = dependencyData.getString("DEP_TYPE");
			if (!"SEL".equals(depType))
				error.println("ERROR: " + selectionConditionName + " is not a selection condition - it has dependency type " + depType);
			object.setStatus(VcmlUtils.createStatusFromInt(dependencyData.getInt("STATUS")));
			object.setGroup(nullIfEmpty(dependencyData.getString("GROUP")));
			JCoParameterList tpl = function.getTableParameterList();
			object.setDescription(readDescription(tpl.getTable("DESCRIPTION"), "LANGUAGE_ISO", "LANGUAGE", "DESCRIPT"));
			object.setDocumentation(readMultiLanguageDocumentations(tpl.getTable("DOCUMENTATION")));
			readSource(tpl.getTable("SOURCE"), object);
			
			ConditionSource selectionConditionSource = sourceUtils.getSelectionConditionSource(object);
			sapRequestObjectLinker.setSeenObjects(seenObjects);
			sapRequestObjectLinker.setOutput(object.eResource());
			sapRequestObjectLinker.linkModel(selectionConditionSource, new ListBasedDiagnosticConsumer());
		} catch (AbapException e) {
			handleAbapException(e);
		}
		return object;
	}

}
