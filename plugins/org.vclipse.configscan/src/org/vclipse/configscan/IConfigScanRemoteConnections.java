package org.vclipse.configscan;
/*******************************************************************************
 * Copyright (c) 2012 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    webXcerpt Software GmbH - initial creator
 ******************************************************************************/


import java.util.List;

import org.vclipse.configscan.implementation.ConfigScanRemoteConnections;

import com.google.inject.ImplementedBy;
import com.sap.conn.jco.JCoException;

/**
 * reads ConfigScan remote configuration data from table ZFSB_REMOTE_ENG 
 */
@ImplementedBy(ConfigScanRemoteConnections.class)
public interface IConfigScanRemoteConnections {

	public List<? extends RemoteConnection> readConfigScanRemoteConnections() throws JCoException;
	
	public interface RemoteConnection {
		public String getMandant();
		public String getDescription();
		public String getEngine();
		public String getRfcDest();
		public String getLogSys();
		public String getXcmScenario();
		public String getIpcConfigUi();
	}

}
