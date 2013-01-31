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
package org.vclipse.configscan.impl.model;

public class TestCaseUtils {

	public boolean isDomainTest(TestCase testCase) {
		String title = testCase.getTitle();
		return title.startsWith("Check_Domain") && (title.contains("cstic:") || title.contains("not tested value:"));
	}
	
	public boolean isMultiValue(TestCase testCase) {
		// TODO implement
		return false;
	}
}
