/*******************************************************************************
 * Copyright (c) 2012 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     webXcerpt Software GmbH - initial creator
 ******************************************************************************/
package org.vclipse.refactoring.tests.utils

import org.eclipse.xtext.junit4.IInjectorProvider
import org.vclipse.vcml.VCMLRuntimeModule
import static com.google.inject.Guice.*

class RefactoringTestInjectorProvider implements IInjectorProvider {
 
	override getInjector() {
		val refactoringModule = new RefactoringTestModule
		val vcmlRuntimeModule = new VCMLRuntimeModule
		val injector = createInjector(refactoringModule, vcmlRuntimeModule)
		return injector
	}
}