package org.vclipse.refactoring.tests;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipselabs.xtext.utils.unittesting.XtextTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vclipse.refactoring.IRefactoringConfiguration;
import org.vclipse.refactoring.core.RefactoringContext;
import org.vclipse.refactoring.core.RefactoringType;
import org.vclipse.refactoring.tests.utils.RefactoringResourcesLoader;
import org.vclipse.refactoring.tests.utils.RefactoringTestInjectorProvider;
import org.vclipse.refactoring.utils.Extensions;
import org.vclipse.vcml.vcml.Characteristic;

@RunWith(value = XtextRunner.class)
@InjectWith(value = RefactoringTestInjectorProvider.class)
@SuppressWarnings("all")
public class ConfigurationTests extends XtextTest {
  @Inject
  private Extensions extensions;
  
  @Inject
  private RefactoringResourcesLoader resourcesLoader;
  
  public ConfigurationTests() {
    super(new Function0<String>() {
      public String apply() {
        String _simpleName = ConfigurationTests.class.getSimpleName();
        return _simpleName;
      }
    }.apply());
  }
  
  @Test
  public void testRefactoringConfiguration() {
    final ArrayList<EObject> entries = this.resourcesLoader.getResourceContents(RefactoringResourcesLoader.CAR_DESCRIPTION);
    Iterable<Characteristic> _filter = Iterables.<Characteristic>filter(entries, Characteristic.class);
    final Iterator<Characteristic> iterator = _filter.iterator();
    boolean _hasNext = iterator.hasNext();
    if (_hasNext) {
      final Characteristic entry = iterator.next();
      final IRefactoringConfiguration configuration = this.extensions.<IRefactoringConfiguration>getInstance(IRefactoringConfiguration.class, entry);
      EReference _vcmlModel_Objects = RefactoringResourcesLoader.VCML_PACKAGE.getVcmlModel_Objects();
      final RefactoringContext context = RefactoringContext.create(entry, _vcmlModel_Objects, RefactoringType.Replace);
      final boolean initialize = configuration.initialize(context);
      Assert.assertEquals("context initialized", Boolean.valueOf(true), Boolean.valueOf(initialize));
      final List<? extends EStructuralFeature> features = configuration.provideFeatures(context);
      boolean _isEmpty = features.isEmpty();
      boolean _not = (!_isEmpty);
      Assert.assertTrue(_not);
      EReference _vcmlModel_Objects_1 = RefactoringResourcesLoader.VCML_PACKAGE.getVcmlModel_Objects();
      boolean _contains = features.contains(_vcmlModel_Objects_1);
      Assert.assertTrue(_contains);
    }
  }
}
