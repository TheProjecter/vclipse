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
package org.vclipse.refactoring;

import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

public interface IRefactoringConfiguration {

	public void initialize(IRefactoringContext context);
	
	public List<? extends EStructuralFeature> features(IRefactoringContext context);
	
}
