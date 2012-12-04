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
package org.vclipse.refactoring.changes;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.vclipse.refactoring.IRefactoringUIContext;
import org.vclipse.refactoring.core.DiffNode;

import com.google.common.collect.Lists;

public class RootChange extends CompositeChange {

	private boolean performed = false;
	
	private DiffNode previewNode;
	
	public RootChange(IRefactoringUIContext context) {
		super("Changes during the re-factoring process.", new Change[0]);
		ModelChange modelChange = new ModelChange(context);
		add(modelChange);
	}
	
	@Override
 	public Change perform(IProgressMonitor pm) throws CoreException {
		if(!performed) {
			for(Change change : getChildren()) {
				ModelChange modelChange = (ModelChange)change;
				modelChange.perform(pm);
			}
			performed = true;
		}
		return null;
	}
	
	@Override
	public Object getModifiedElement() {
		if(getChildren().length != 0) {
			ModelChange modelChange = ((ModelChange)getChildren()[0]);
			return modelChange.getModifiedElement();
		}
		return null;
	}

	public List<Change> getEntries() {
		return Lists.newArrayList(getChildren());
	}
	
	public DiffNode getDiffNode() {
		return previewNode;
	}
}
