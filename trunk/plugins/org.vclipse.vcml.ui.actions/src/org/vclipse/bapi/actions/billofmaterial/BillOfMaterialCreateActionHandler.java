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
package org.vclipse.bapi.actions.billofmaterial;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.vclipse.bapi.actions.BAPIUtils;
import org.vclipse.bapi.actions.IBAPIActionRunner;
import org.vclipse.vcml.vcml.BOMItem;
import org.vclipse.vcml.vcml.BillOfMaterial;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.Option;
import org.vclipse.vcml.vcml.VCObject;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class BillOfMaterialCreateActionHandler extends BAPIUtils implements IBAPIActionRunner<BillOfMaterial>{

	public boolean isEnabled(BillOfMaterial object) {
		return isConnected();
	}

	public void run(BillOfMaterial billOfMaterial, Resource resource, IProgressMonitor monitor, Map<String, VCObject> seenObjects, List<Option> globalOptions) throws JCoException {
		JCoFunction function = getJCoFunction("CSAP_MAT_BOM_CREATE", monitor);
		JCoParameterList ipl = function.getImportParameterList();
		Material material = billOfMaterial.getMaterial();
		if(material == null) {
			return;
		}
		String materialNumber = material.getName();
		String plant = getPlant();
		String bomUsage = getBomUsage();
		
		handleOptions(billOfMaterial.getOptions(), globalOptions, ipl, "CHANGE_NO", "VALID_FROM");
		
		ipl.setValue("MATERIAL", materialNumber);
		ipl.setValue("PLANT", plant);
		ipl.setValue("BOM_USAGE", bomUsage);
		JCoParameterList tpl = function.getTableParameterList();
		JCoTable tSTPO = tpl.getTable("T_STPO");
		for(BOMItem item : billOfMaterial.getItems()) {
			tSTPO.appendRow();
			tSTPO.setValue("ITEM_CATEG", "N");
			tSTPO.setValue("ITEM_NO", item.getItemnumber());
			tSTPO.setValue("COMPONENT", item.getMaterial().getName());
			tSTPO.setValue("COMP_QTY", 1);
			tSTPO.setValue("COMP_UNIT", "PC");
			// TODO sales-relevant flag
		}
		try {
			execute(function, monitor, materialNumber + " " + plant + " " + bomUsage);
		} catch (AbapException e) {
			handleAbapException(e);
		} 
		// System.err.println(function.toXML());
	}

}
