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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.vclipse.base.ui.compare.EObjectTypedElement;
import org.vclipse.refactoring.IRefactoringUIContext;
import org.vclipse.refactoring.RefactoringPlugin;
import org.vclipse.refactoring.core.DiffNode;
import org.vclipse.refactoring.core.RefactoringRunner;
import org.vclipse.refactoring.ui.UIRefactoringContext;
import org.vclipse.refactoring.utils.RefactoringUtility;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;

public class SourceCodeChanges extends CompositeChange {
	
	private RefactoringRunner runner;
	private RefactoringUtility utility;
	
	private EObject rootOriginal;
	private EObject rootCopy;
	
	private DiffNode previewNode;
	private IRefactoringUIContext context;
	
	private List<EObject> rootContents;
	private List<EObject> copyContents;
	
	private final ISerializer serializer;
	
	private boolean performed = false;
	
	private static class InputStreamProvider implements InputSupplier<InputStream> {

		private DiffNode diffNode;
		
		static InputStreamProvider getInstance(DiffNode diffNode) {
			return new InputStreamProvider(diffNode);
		}	
		
		public InputStreamProvider(DiffNode diffNode) {
			this.diffNode = diffNode;
		}
		
		@Override
		public InputStream getInput() throws IOException {
			try {
				EObjectTypedElement left = (EObjectTypedElement)diffNode.getLeft();
				InputStream contents = left.getContents();
				return contents;
			} catch(CoreException exception) {
				return null;
			}
		}
	}
	
	static String getChangeLabel(IRefactoringUIContext context) {
		EObject element = context.getSourceElement();
		Resource resource = element.eResource();
		if(resource == null) {
			System.err.println("resource = null");
		}
		return resource.getURI().lastSegment();
	}
	
	public SourceCodeChanges(IRefactoringUIContext context, RefactoringRunner runner, RefactoringUtility utility) {
 		super("Changes in " + getChangeLabel(context));
		this.context = context;
		this.runner = runner;
		this.utility = utility;
		
		EObject element = this.context.getSourceElement();
		rootOriginal = EcoreUtil.getRootContainer(element);
		
		rootContents = Lists.newArrayList(rootOriginal.eAllContents());
		rootContents.add(0, rootOriginal);
		
		serializer = utility.getInstance(ISerializer.class, rootOriginal);
		IParser parser = utility.getInstance(IParser.class, rootOriginal);
		ILinker linker = utility.getInstance(ILinker.class, rootOriginal);

		String string = serializer.serialize(rootOriginal);		
		IParseResult parseResult = parser.parse(new StringReader(string));
		Resource resource = rootOriginal.eResource();
		ResourceSet resourceSet = resource.getResourceSet();
		URI uri = resource.getURI();
		uri = URI.createURI(uri.trimFileExtension().toString() + ".refactored." + uri.fileExtension());
		try {
			resource = resourceSet.getResource(uri, true);
		} catch(Exception exception) {
			resource = resourceSet.getResource(uri, true);
		}
		resource.getContents().clear();
		rootCopy = parseResult.getRootASTElement();
		copyContents = Lists.newArrayList(rootCopy.eAllContents());
		copyContents.add(0, rootCopy);
		resource.getContents().add(rootCopy);
		
		linker.linkModel(rootCopy, new ListBasedDiagnosticConsumer());
		
		previewNode = new DiffNode(Differencer.CHANGE);
		previewNode.setLeft(new EObjectTypedElement(rootOriginal, serializer));
	}
	

	@Override
	public Object getModifiedElement() {
		return rootCopy;
	}
	
	public DiffNode getDiffNode() {
		return previewNode;
	}
	
	public IRefactoringUIContext getContext() {
		return context;
	}
	
	@Override
 	public Change perform(IProgressMonitor pm) throws CoreException {
		if(!performed) {
			StringBuffer taskBuffer = new StringBuffer("Initialising re-factoring operation for ").append(context.getLabel());
			final SubMonitor sm = SubMonitor.convert(pm, taskBuffer.toString(), 60);

			// creates a copy of a model and sets the source element to an equal one in the copied model
			EObject element = context.getSourceElement();
			EObject entry = utility.findEntry(element, copyContents);
			if(entry != null) {
				UIRefactoringContext uicontext = (UIRefactoringContext)context;
				final IRefactoringUIContext refactoringContext = uicontext.copy();
				refactoringContext.setSourceElement(entry);
				try {
					// executes re-factoring on the copy of the model
					context.getDocument().modify(new IUnitOfWork<Void, XtextResource>() {
						@Override
						public java.lang.Void exec(XtextResource resource) throws Exception {
							runner.refactor(refactoringContext);
							sm.worked(20);
							return null;
						}
					});
				} catch(Exception exception) {
					IStatus status = new Status(IStatus.ERROR, RefactoringPlugin.ID, exception.getMessage(), exception);
					throw new CoreException(status);
				}
				
				previewNode.setRight(new EObjectTypedElement(rootCopy, serializer));
				sm.worked(10);
				
				recordSourceCodeChanges(sm, refactoringContext);
				sm.worked(20);
				
				if(getChildren().length == 0) {
					add(new NoChange(null));
				}
				performed = true;	
				sm.worked(10);
			}
		}
		return null;
	}
	
	public RefactoringStatus refactor(final IProgressMonitor pm) throws CoreException {
		RefactoringStatus refactoringStatus = RefactoringStatus.create(Status.OK_STATUS);
		if(isEnabled()) {
			StringBuffer taskBuffer = new StringBuffer("Executing re-factoring:").append(context.getLabel());
			final List<Change> changes = Lists.newArrayList(getChildren());
			final SubMonitor sm = SubMonitor.convert(pm, taskBuffer.toString(), changes.size());
			final Map<SourceCodeChange, CoreException> exceptions = Maps.newHashMap();
			context.getDocument().modify(new IUnitOfWork<EObject, XtextResource>() {
				@Override
				public EObject exec(XtextResource state) throws Exception {
					for(Change change : changes) {
						if(change instanceof SourceCodeChange) {
							SourceCodeChange scc = (SourceCodeChange)change;
							if(scc.isEnabled()) {
								try {
									scc.refactor(sm);							
								} catch(CoreException exception) {
									exceptions.put(scc, exception);
								}
							}
							sm.worked(1);
						}
					}
					return null;
				}
			});
			
			if(!exceptions.isEmpty()) {
				taskBuffer = new StringBuffer("Collecting errors for ").append(context.getLabel());
				SubMonitor sm_exceptions = SubMonitor.convert(pm, taskBuffer.toString(), exceptions.size());
				for(Entry<SourceCodeChange, CoreException> exception : exceptions.entrySet()) {
					final SourceCodeChange scc = exception.getKey();
					final CoreException ce = exception.getValue();
					refactoringStatus.addEntry(IStatus.ERROR, ce.getMessage(), new RefactoringStatusContext() {
						@Override
						public Object getCorrespondingElement() {
							return scc.getModifiedElement();
						}
					}, RefactoringPlugin.ID, RefactoringStatusEntry.NO_CODE);
					sm_exceptions.worked(1);
				}
			}
		}
		return refactoringStatus;
	}
	
	private void recordSourceCodeChanges(IProgressMonitor pm, IRefactoringUIContext previewContext) {
		StringBuffer taskBuffer = new StringBuffer("Recording source code changes for re-factoring ").append(previewContext.getLabel());
		EMap<EObject, EList<FeatureChange>> objectChanges = runner.getChangeRecorder().endRecording().getObjectChanges();
		SubMonitor sm = SubMonitor.convert(pm, taskBuffer.toString(), objectChanges.size());
		List<SourceCodeChange> sourceCodeChanges = Lists.newArrayList();
		InputStreamProvider streamRootNode = InputStreamProvider.getInstance(previewNode);
		for(Entry<EObject, EList<FeatureChange>> entry : objectChanges.entrySet()) {
			EObject changed = entry.getKey();
			if(changed.eContainer() instanceof ChangeDescription) {
				sm.worked(1);
				continue;
			}
			EObject existingEntry = utility.findEntry(changed, rootContents);
			SourceCodeChange scc = new SourceCodeChange(utility, existingEntry, changed, entry.getValue());
			try {
				InputStreamProvider currentStream = InputStreamProvider.getInstance(scc.getDiffNode());
				if(ByteStreams.equal(streamRootNode, currentStream)) {
					markAsSynthetic();
					sourceCodeChanges.add(0, scc);
					sm.worked(1);
					continue;
				}
			} catch(IOException exception) {
				RefactoringPlugin.log(exception.getMessage(), exception);
			}
			sourceCodeChanges.add(scc);
			sm.worked(1);
		}
		for(SourceCodeChange change : sourceCodeChanges) {
			add(change);
		}
	}
}