package org.vclipse.refactoring.tests.utils;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.xtext.junit4.IInjectorProvider;
import org.vclipse.refactoring.tests.utils.RefactoringTestModule;
import org.vclipse.vcml.VCMLRuntimeModule;

@SuppressWarnings("all")
public class RefactoringTestInjectorProvider implements IInjectorProvider {
  public Injector getInjector() {
    RefactoringTestModule _refactoringTestModule = new RefactoringTestModule();
    final RefactoringTestModule refactoringModule = _refactoringTestModule;
    VCMLRuntimeModule _vCMLRuntimeModule = new VCMLRuntimeModule();
    final VCMLRuntimeModule vcmlRuntimeModule = _vCMLRuntimeModule;
    final Injector injector = Guice.createInjector(refactoringModule, vcmlRuntimeModule);
    return injector;
  }
}
