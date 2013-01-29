package org.vclipse.refactoring.tests.utils;

import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.vclipse.refactoring.RefactoringPlugin;
import org.vclipse.refactoring.guice.RefactoringModule;
import org.vclipse.refactoring.tests.utils.RefactoringResourcesLoader;

@SuppressWarnings("all")
public class RefactoringTestModule extends RefactoringModule {
  public RefactoringTestModule() {
    super(new Function0<RefactoringPlugin>() {
      public RefactoringPlugin apply() {
        RefactoringPlugin _instance = RefactoringPlugin.getInstance();
        return _instance;
      }
    }.apply());
  }
  
  public Class<? extends RefactoringResourcesLoader> bindRefactoringResourceLoader() {
    return RefactoringResourcesLoader.class;
  }
}
