package org.vclipse.refactoring.tests;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipselabs.xtext.utils.unittesting.XtextTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vclipse.refactoring.core.DefaultRefactoringExecuter;
import org.vclipse.refactoring.core.RefactoringContext;
import org.vclipse.refactoring.core.RefactoringType;
import org.vclipse.refactoring.tests.utils.RefactoringResourcesLoader;
import org.vclipse.refactoring.tests.utils.RefactoringTestInjectorProvider;
import org.vclipse.refactoring.utils.EntrySearch;
import org.vclipse.refactoring.utils.Extensions;
import org.vclipse.vcml.refactoring.VCMLRefactoring;
import org.vclipse.vcml.vcml.Characteristic;

@RunWith(value = XtextRunner.class)
@InjectWith(value = RefactoringTestInjectorProvider.class)
@SuppressWarnings("all")
public class SearchTests extends XtextTest {
  @Inject
  private EntrySearch search;
  
  @Inject
  private RefactoringResourcesLoader resourcesLoader;
  
  @Inject
  private Extensions extensions;
  
  public SearchTests() {
    super(new Function0<String>() {
      public String apply() {
        String _simpleName = SearchTests.class.getSimpleName();
        return _simpleName;
      }
    }.apply());
  }
  
  @Test
  public void testFindObject() {
    final ArrayList<EObject> entries = this.resourcesLoader.getResourceContents(RefactoringResourcesLoader.CAR_DESCRIPTION);
    boolean _isEmpty = entries.isEmpty();
    boolean _not = (!_isEmpty);
    Assert.assertTrue(_not);
    int _size = entries.size();
    int _divide = (_size / 2);
    int _plus = (_divide + 1);
    final EObject entry = entries.get(_plus);
    EObject findEntry = this.search.findEntry(entry, entries);
    Assert.assertNotNull(findEntry);
    final EObject jft = EcoreFactory.eINSTANCE.createEObject();
    EObject _findEntry = this.search.findEntry(jft, entries);
    findEntry = _findEntry;
    Assert.assertNull(findEntry);
  }
  
  @Test
  public void testFindByTypeAndName() {
    final ArrayList<EObject> entries = this.resourcesLoader.getResourceContents(RefactoringResourcesLoader.CAR_DESCRIPTION);
    boolean _isEmpty = entries.isEmpty();
    boolean _not = (!_isEmpty);
    Assert.assertTrue(_not);
    EClass _selectionCondition = RefactoringResourcesLoader.VCML_PACKAGE.getSelectionCondition();
    EObject findEntry = this.search.<EObject>findEntry("SEL_COND", _selectionCondition, entries);
    Assert.assertNotNull(findEntry);
    EClass _characteristic = RefactoringResourcesLoader.VCML_PACKAGE.getCharacteristic();
    EObject _findEntry = this.search.<EObject>findEntry("SEL_COND", _characteristic, entries);
    findEntry = _findEntry;
    Assert.assertNotNull(findEntry);
    EClass _class_ = RefactoringResourcesLoader.VCML_PACKAGE.getClass_();
    EObject _findEntry_1 = this.search.<EObject>findEntry("(300)CAR", _class_, entries);
    findEntry = _findEntry_1;
    Assert.assertNotNull(findEntry);
    EClass _material = RefactoringResourcesLoader.VCML_PACKAGE.getMaterial();
    EObject _findEntry_2 = this.search.<EObject>findEntry("ENGINE_2400", _material, entries);
    findEntry = _findEntry_2;
    Assert.assertNotNull(findEntry);
    EClass _characteristic_1 = RefactoringResourcesLoader.VCML_PACKAGE.getCharacteristic();
    EObject _findEntry_3 = this.search.<EObject>findEntry("ENGINE_2400", _characteristic_1, entries);
    findEntry = _findEntry_3;
    Assert.assertNull(findEntry);
    EClass _material_1 = RefactoringResourcesLoader.VCML_PACKAGE.getMaterial();
    EObject _findEntry_4 = this.search.<EObject>findEntry("FUELCONSUMPTION", _material_1, entries);
    findEntry = _findEntry_4;
    Assert.assertNull(findEntry);
  }
  
  @Test
  public void testSearchByName() {
    final ArrayList<EObject> entries = this.resourcesLoader.getResourceContents(RefactoringResourcesLoader.CAR_DESCRIPTION);
    boolean _isEmpty = entries.isEmpty();
    boolean _not = (!_isEmpty);
    Assert.assertTrue(_not);
    EClass _dependencyNet = RefactoringResourcesLoader.VCML_PACKAGE.getDependencyNet();
    final EObject entry = this.search.<EObject>findEntry("DEP_NET", _dependencyNet, entries);
    Assert.assertNotNull(entry);
    final ArrayList<EObject> namedEntries = Lists.<EObject>newArrayList(entry);
    int _size = namedEntries.size();
    Assert.assertEquals(1, _size);
  }
  
  @Test
  public void testWithRefactorings() {
    ArrayList<EObject> entries = this.resourcesLoader.getResourceContents(RefactoringResourcesLoader.CAR_DESCRIPTION);
    EObject firstEntry = entries.get(0);
    final Resource resource = firstEntry.eResource();
    final VCMLRefactoring refactoringExecuter = this.extensions.<VCMLRefactoring>getInstance(VCMLRefactoring.class, firstEntry);
    boolean _equals = Objects.equal(refactoringExecuter, null);
    if (_equals) {
      String _plus = ("can not find re-factoring executer for " + firstEntry);
      Assert.fail(_plus);
    }
    EClass _class_ = RefactoringResourcesLoader.VCML_PACKAGE.getClass_();
    EObject _findEntry = this.search.<EObject>findEntry("(300)CAR", _class_, entries);
    org.vclipse.vcml.vcml.Class klass = ((org.vclipse.vcml.vcml.Class) _findEntry);
    Assert.assertNotNull(klass);
    EList<Characteristic> _characteristics = klass.getCharacteristics();
    final Function1<Characteristic,String> _function = new Function1<Characteristic,String>() {
        public String apply(final Characteristic current) {
          String _name = current.getName();
          return _name;
        }
      };
    Map<String,Characteristic> mapped = IterableExtensions.<String, Characteristic>toMap(_characteristics, _function);
    Set<String> _keySet = mapped.keySet();
    boolean _contains = _keySet.contains("ENGINE");
    Assert.assertTrue(_contains);
    EClass _characteristic = RefactoringResourcesLoader.VCML_PACKAGE.getCharacteristic();
    EObject cstic = this.search.<EObject>findEntry("ENGINE", _characteristic, entries);
    Assert.assertNotNull(cstic);
    EReference _vcmlModel_Objects = RefactoringResourcesLoader.VCML_PACKAGE.getVcmlModel_Objects();
    final RefactoringContext context = RefactoringContext.create(cstic, _vcmlModel_Objects, RefactoringType.Replace);
    context.addAttribute(DefaultRefactoringExecuter.BUTTON_STATE, Boolean.valueOf(true));
    refactoringExecuter.refactoring_Replace_objects(context);
    EList<EObject> _contents = resource.getContents();
    EObject _get = _contents.get(0);
    firstEntry = _get;
    ArrayList<EObject> _allEntries = this.resourcesLoader.getAllEntries(firstEntry);
    entries = _allEntries;
    EClass _characteristic_1 = RefactoringResourcesLoader.VCML_PACKAGE.getCharacteristic();
    EObject _findEntry_1 = this.search.<EObject>findEntry("ENGINE", _characteristic_1, entries);
    cstic = _findEntry_1;
    Assert.assertNull("not existent after re-factoring", cstic);
    EClass _class__1 = RefactoringResourcesLoader.VCML_PACKAGE.getClass_();
    EObject _findEntry_2 = this.search.<EObject>findEntry("(300)CAR", _class__1, entries);
    klass = ((org.vclipse.vcml.vcml.Class) _findEntry_2);
    Assert.assertNotNull(klass);
    EList<Characteristic> _characteristics_1 = klass.getCharacteristics();
    final Function1<Characteristic,String> _function_1 = new Function1<Characteristic,String>() {
        public String apply(final Characteristic current) {
          String _name = current.getName();
          return _name;
        }
      };
    Map<String,Characteristic> _map = IterableExtensions.<String, Characteristic>toMap(_characteristics_1, _function_1);
    mapped = _map;
    Set<String> _keySet_1 = mapped.keySet();
    boolean _contains_1 = _keySet_1.contains("ENGINE");
    Assert.assertFalse(_contains_1);
  }
}
