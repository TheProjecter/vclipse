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
package org.vclipse.vcml.diff;

import org.osgi.framework.BundleContext;
import org.vclipse.base.ui.BaseUiPlugin;
import org.vclipse.vcml.diff.injection.DiffModule;

public class VcmlDiffPlugin extends BaseUiPlugin {

	static {
		ID = "org.vclipse.vcml.diff";
	}

	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		injectionModule = new DiffModule(this);
	}
}
