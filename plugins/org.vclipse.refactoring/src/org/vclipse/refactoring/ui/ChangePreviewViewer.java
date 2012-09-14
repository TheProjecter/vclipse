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
package org.vclipse.refactoring.ui;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.internal.NullViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.ui.refactoring.ChangePreviewViewerInput;
import org.eclipse.ltk.ui.refactoring.IChangePreviewViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.ui.compare.InjectableViewerCreator;
import org.vclipse.base.ui.util.EObjectTypedElement;
import org.vclipse.refactoring.core.IChangeCompare;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class ChangePreviewViewer implements IChangePreviewViewer {

	@Inject
	private RefactoringUtility refactoringUtility;
	
	private Viewer viewer;
	
	private Composite composite;
	
	@Override
	public void createControl(Composite parent) {
		viewer = new NullViewer(parent);
		this.composite = parent;
	}

	@Override
	public Control getControl() {
		if(viewer != null) {
			return viewer.getControl();
		}
		return null;
	}

	@Override
	public void setInput(ChangePreviewViewerInput input) {
		Change change = input.getChange();
		if(change instanceof IChangeCompare) {
			IChangeCompare changeCompare = (IChangeCompare)change;
			EObject current = changeCompare.getCurrent();
			if(viewer instanceof NullViewer) {
				InjectableViewerCreator instance = refactoringUtility.getInstance(current, InjectableViewerCreator.class);
				if(instance != null) {
					viewer = instance.createViewer(composite, new CompareConfiguration());					
				}
			}
			EObject changed = changeCompare.getChanged();
			ISerializer serializer = refactoringUtility.getInstance(current, ISerializer.class);
			DiffNode diffNode = new DiffNode(new EObjectTypedElement(current, serializer), new EObjectTypedElement(changed, serializer));
			viewer.setInput(diffNode);
		}
	}
}