/**
 * Copyright (c) 2010 - 2013 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     	webXcerpt Software GmbH - initial creator
 * 		www.webxcerpt.com
 */
package org.vclipse.vcml.compare;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Map.Entry;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.util.ResourceUtil;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.Issue.IssueImpl;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.vclipse.base.naming.INameProvider;
import org.vclipse.vcml.compare.ModelChangesProcessor;
import org.vclipse.vcml.compare.ModelDifferencesEngine;
import org.vclipse.vcml.compare.ResourceChangesProcessor;
import org.vclipse.vcml.compare.ResourceDifferencesEngine;
import org.vclipse.vcml.compare.VCMLComparePlugin;

@SuppressWarnings("all")
public class VCMLCompareOperation {
  public static String ERRORS_FILE_EXTENSION = "_errors.txt";
  
  private static /* VcmlFactory */Object VCML_FACTORY /* Skipped initializer because of errors */;
  
  @Inject
  private /* IEqualityHelperFactory */Object equalityHelperFactory;
  
  @Inject
  private INameProvider vcmlNameProvider;
  
  @Inject
  private ModelChangesProcessor modelChangesProcessor;
  
  @Inject
  private ResourceChangesProcessor resourceChangesProcessor;
  
  @Inject
  private ModelDifferencesEngine modelDifferencesEngine;
  
  @Inject
  private ResourceDifferencesEngine resourceDifferencesEngine;
  
  @Inject
  private MarkerCreator markerCreator;
  
  private int peakForViewOutput = 10;
  
  /**
   * Compare operation for 2 vcml files. Results are extracted to the result file.
   */
  public void compare(final IFile oldFile, final IFile newFile, final IFile result, final IProgressMonitor monitor) throws Exception {
    monitor.subTask("Initialising models for comparison...");
    XtextResourceSet _xtextResourceSet = new XtextResourceSet();
    final XtextResourceSet set = _xtextResourceSet;
    IPath _fullPath = newFile.getFullPath();
    String path = _fullPath.toString();
    URI uri = URI.createPlatformResourceURI(path, true);
    final Resource newResource = set.getResource(uri, true);
    IPath _fullPath_1 = oldFile.getFullPath();
    String _string = _fullPath_1.toString();
    path = _string;
    URI _createPlatformResourceURI = URI.createPlatformResourceURI(path, true);
    uri = _createPlatformResourceURI;
    final Resource oldResource = set.getResource(uri, true);
    IPath _fullPath_2 = result.getFullPath();
    String _string_1 = _fullPath_2.toString();
    path = _string_1;
    URI _createPlatformResourceURI_1 = URI.createPlatformResourceURI(path, true);
    uri = _createPlatformResourceURI_1;
    final Resource resultResource = set.getResource(uri, true);
    this.compare(oldResource, newResource, resultResource, monitor);
    this.createMarkers(resultResource);
    this.handleErrors(result, monitor);
    result.refreshLocal(IResource.DEPTH_ONE, monitor);
  }
  
  /**
   * Compare operation for 2 vcml resources. Results are extracted to the result resource.
   */
  public void compare(final Resource oldResource, final Resource newResource, final Resource resultResource, final IProgressMonitor monitor) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nVcmlModel cannot be resolved to a type."
      + "\nVcmlModel cannot be resolved to a type."
      + "\nIdentifierEObjectMatcher cannot be resolved."
      + "\nDefaultComparisonFactory cannot be resolved."
      + "\nDefaultMatchEngine cannot be resolved."
      + "\nThe method diff is undefined for the type VCMLCompareOperation"
      + "\ncreateVcmlModel cannot be resolved"
      + "\nmatch cannot be resolved");
  }
  
  /**
   * Compare operation for 2 vcml models.
   */
  public void compare(final /* VcmlModel */Object oldModel, final /* VcmlModel */Object newModel, final /* VcmlModel */Object resultModel, final IProgressMonitor monitor) throws Exception {
    throw new Error("Unresolved compilation problems:"
      + "\nIdentifierEObjectMatcher cannot be resolved."
      + "\nDefaultComparisonFactory cannot be resolved."
      + "\nDefaultMatchEngine cannot be resolved."
      + "\nThe method diff is undefined for the type VCMLCompareOperation"
      + "\nmatch cannot be resolved");
  }
  
  /**
   * Provide issues and target resource on validator
   */
  public void createMarkers(final Resource result) {
    try {
      final IFile file = ResourceUtil.getFile(result);
      file.deleteMarkers("org.eclipse.core.resources.problemmarker", false, IResource.DEPTH_ONE);
      Multimap<String,IssueImpl> _collectedIssues = this.modelChangesProcessor.getCollectedIssues();
      Collection<Entry<String,IssueImpl>> _entries = _collectedIssues.entries();
      for (final Entry<String,IssueImpl> issueEntry : _entries) {
        IssueImpl _value = issueEntry.getValue();
        this.markerCreator.createMarker(_value, file, "org.eclipse.core.resources.problemmarker");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Returns true if there were some problems during compare operation, false otherwise.
   */
  public boolean reportedProblems() {
    final Multimap<String,IssueImpl> issues = this.modelChangesProcessor.getCollectedIssues();
    boolean _isEmpty = issues.isEmpty();
    return (!_isEmpty);
  }
  
  /**
   * Handles the errors provided by the changes processor. The output is streamed
   * to the error log view, if the amount is above peakForViewOutput, the stream is
   * switched to a file.
   */
  protected void handleErrors(final IFile result, final IProgressMonitor monitor) {
    try {
      final Multimap<Integer,Exception> errors = this.modelChangesProcessor.getErrors();
      int _size = errors.size();
      boolean _lessThan = (_size < this.peakForViewOutput);
      if (_lessThan) {
        Collection<Exception> _get = errors.get(Integer.valueOf(IStatus.ERROR));
        for (final Exception error : _get) {
          String _message = error.getMessage();
          VCMLComparePlugin.log(IStatus.ERROR, _message);
        }
      } else {
        String _name = result.getName();
        String _string = _name.toString();
        final String fileName = _string.replace(ModelChangesProcessor.DIFF_VCML_EXTENSION, VCMLCompareOperation.ERRORS_FILE_EXTENSION);
        IContainer _parent = result.getParent();
        Path _path = new Path(fileName);
        final IFile errorsFile = _parent.getFile(_path);
        StringInputStream _stringInputStream = new StringInputStream("");
        final StringInputStream stream = _stringInputStream;
        boolean _isAccessible = errorsFile.isAccessible();
        if (_isAccessible) {
          errorsFile.setContents(stream, true, true, monitor);
        } else {
          errorsFile.create(stream, IResource.DERIVED, monitor);
        }
        IPath _location = errorsFile.getLocation();
        File _file = _location.toFile();
        FileWriter _fileWriter = new FileWriter(_file);
        final FileWriter fileWriter = _fileWriter;
        Collection<Exception> _get_1 = errors.get(Integer.valueOf(IStatus.ERROR));
        for (final Exception error_1 : _get_1) {
          String _message_1 = error_1.getMessage();
          fileWriter.append(_message_1);
        }
        fileWriter.flush();
        errorsFile.refreshLocal(IResource.DEPTH_ONE, monitor);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
