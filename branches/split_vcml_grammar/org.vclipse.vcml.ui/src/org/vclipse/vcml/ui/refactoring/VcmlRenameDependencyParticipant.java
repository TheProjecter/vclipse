package org.vclipse.vcml.ui.refactoring;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.xtext.resource.SaveOptions;
import org.vclipse.base.ui.BaseUiPlugin;
import org.vclipse.base.ui.util.VClipseResourceUtil;
import org.vclipse.vcml.utils.DependencySourceUtils;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.VCObject;
import org.vclipse.vcml.vcml.VcmlPackage;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

public class VcmlRenameDependencyParticipant extends RenameParticipant {

	// private Logger logger = Logger.getLogger(VcmlRenameDependencyParticipant.class);
	
	@Inject
	private DependencySourceUtils dependencySourceUtils;
	
	@Inject
	private VClipseResourceUtil resourceUtil;
	
	@Override
	protected boolean initialize(Object object) {
		return object instanceof IFile && 
				Sets.newHashSet(DependencySourceUtils.EXTENSION_CONSTRAINT, 
						DependencySourceUtils.EXTENSION_PRECONDITION, 
							DependencySourceUtils.EXTENSION_PROCEDURE,
								DependencySourceUtils.EXTENSION_SELECTIONCONDITION).
									contains(((IFile)object).getFileExtension());
	}

	@Override
	public String getName() {
		return VcmlRenameDependencyParticipant.class.getSimpleName();
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor progressMonitor, CheckConditionsContext context) throws OperationCanceledException {
		return null;
	}

	@Override
	public Change createChange(IProgressMonitor progressMonitor) throws CoreException, OperationCanceledException {
		Object[] elements = getProcessor().getElements();
		if(elements.length > 0 && elements[0] instanceof IFile) {
			IFile file = (IFile)elements[0];
			final String fileName = file.getName().substring(0, file.getName().indexOf('.'));
			
			Resource dependencyResource = resourceUtil.getResource(file);
			URI vcmlResourceUri = dependencySourceUtils.getVcmlResourceURI(dependencyResource.getURI());
			EList<EObject> contents = resourceUtil.getResourceSet().getResource(vcmlResourceUri, true).getContents();
			if(!contents.isEmpty()) {
				Iterator<VCObject> iterator = Iterables.filter(((Model)contents.get(0)).getObjects(), new Predicate<VCObject>() {
					public boolean apply(VCObject object) {
						return object.getName().equals(fileName);
					}
				}).iterator();
				
				while(iterator.hasNext()) {
					VCObject vcobject = iterator.next();
					vcobject.eSet(VcmlPackage.eINSTANCE.getVCObject_Name(), getArguments().getNewName().replace("." + file.getFileExtension(), ""));
					try {
						vcobject.eResource().save(SaveOptions.newBuilder().format().getOptions().toOptionsMap());
					} catch(IOException exception) {
						BaseUiPlugin.log(exception.getMessage(), exception);
					}
					// rename refactoring for file objects is suited only for one object
					break;
				}
			}
		}
		// no change is required
		// the default implementation for resource rename operation do it for us
		return null;
	}
}
