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
package org.vclipse.idoc2jcoidoc.actions;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.vclipse.base.ui.FileListHandler;
import org.vclipse.idoc2jcoidoc.IDoc2JCoIDocPlugin;
import org.vclipse.idoc2jcoidoc.views.IDocView;

public class TransformIDoc2AlePackage extends FileListHandler {

	@Override
	public void handleListVariable(Iterable<IFile> collection, ExecutionEvent event) {
		Iterator<IFile> iterator = collection.iterator();
		if(iterator.hasNext()) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			if(window != null) {
				try {
					IDocView view = (IDocView)window.getActivePage().showView(IDocView.ID);
					IFile file = iterator.next();				
					view.setInput(file);
				} catch (PartInitException exception) {
					IDoc2JCoIDocPlugin.log(exception.getMessage(), exception);
				}				
			}
		}
	}
}
