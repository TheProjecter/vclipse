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
package org.vclipse.tests.refactoring;

import com.google.inject.Inject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.vclipse.refactoring.utils.EntrySearch;
import org.vclipse.refactoring.utils.Extensions;
import org.vclipse.tests.VClipseTestUtilities;
import org.vclipse.tests.refactoring.RefactoringInjectorProvider;

/* @RunWith(XtextRunner.class) */@InjectWith(RefactoringInjectorProvider.class)
@SuppressWarnings("all")
public class SearchTests /* implements XtextTest  */{
  @Inject
  private EntrySearch search;
  
  @Inject
  private VClipseTestUtilities resourcesLoader;
  
  @Inject
  private Extensions extensions;
  
  public SearchTests() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method super is undefined for the type SearchTests");
  }
  
  /* @Test
   */public void testFindObject() {
    throw new Error("Unresolved compilation problems:"
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nassertTrue cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertNull cannot be resolved");
  }
  
  /* @Test
   */public void testFindByTypeAndName() {
    throw new Error("Unresolved compilation problems:"
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nassertTrue cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertNotNull cannot be resolved");
  }
  
  /* @Test
   */public void testSearchByName() {
    throw new Error("Unresolved compilation problems:"
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nassertTrue cannot be resolved"
      + "\nassertNull cannot be resolved");
  }
  
  /* @Test
   */public void testWithRefactorings() {
    throw new Error("Unresolved compilation problems:"
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nAssert cannot be resolved to a type."
      + "\nfail cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertTrue cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertNull cannot be resolved"
      + "\nassertNotNull cannot be resolved"
      + "\nassertFalse cannot be resolved");
  }
}
