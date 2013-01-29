package org.vclipse.refactoring.tests.utils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.io.InputStream;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipselabs.xtext.utils.unittesting.XtextTest;
import org.junit.Assert;
import org.vclipse.refactoring.RefactoringPlugin;
import org.vclipse.vcml.vcml.VcmlFactory;
import org.vclipse.vcml.vcml.VcmlPackage;

@SuppressWarnings("all")
public class RefactoringResourcesLoader extends XtextTest {
  public static String PREFIX = "/CarDescription/";
  
  public static String DEPENDENCIES_PREFIX = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.PREFIX + "car_description-dep/");
      return _plus;
    }
  }.apply();
  
  public static String CAR_DESCRIPTION = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.PREFIX + "car_description.vcml");
      return _plus;
    }
  }.apply();
  
  public static String DEPENDENCY_CONS = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.DEPENDENCIES_PREFIX + "CS_CAR1.cons");
      return _plus;
    }
  }.apply();
  
  public static String DEPENDENCY_PRE = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.DEPENDENCIES_PREFIX + "PRECOND.pre");
      return _plus;
    }
  }.apply();
  
  public static String DEPENDENCY_PROC = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.DEPENDENCIES_PREFIX + "PROC.proc");
      return _plus;
    }
  }.apply();
  
  public static String DEPENDENCY_SEL = new Function0<String>() {
    public String apply() {
      String _plus = (RefactoringResourcesLoader.DEPENDENCIES_PREFIX + "SEL_COND.sel");
      return _plus;
    }
  }.apply();
  
  public static VcmlPackage VCML_PACKAGE = VcmlPackage.eINSTANCE;
  
  public static VcmlFactory VCML_FACTORY = VcmlFactory.eINSTANCE;
  
  public ArrayList<EObject> getAllEntries(final EObject entry) {
    ArrayList<EObject> _xblockexpression = null;
    {
      final EObject rootContainer = EcoreUtil2.getRootContainer(entry);
      TreeIterator<EObject> _eAllContents = rootContainer.eAllContents();
      final ArrayList<EObject> entries = Lists.<EObject>newArrayList(_eAllContents);
      entries.add(0, rootContainer);
      _xblockexpression = (entries);
    }
    return _xblockexpression;
  }
  
  public Resource getResource(final String path) {
    EObject _resourceRoot = this.getResourceRoot(path);
    Resource _eResource = _resourceRoot.eResource();
    return _eResource;
  }
  
  public EObject getResourceRoot(final String path) {
    EObject _xblockexpression = null;
    {
      String _plus = (RefactoringPlugin.ID + path);
      final URI uri = URI.createPlatformPluginURI(_plus, true);
      final Resource resource = this.resourceSet.getResource(uri, true);
      final EList<EObject> contents = resource.getContents();
      EObject _xifexpression = null;
      boolean _isEmpty = contents.isEmpty();
      if (_isEmpty) {
        return null;
      } else {
        EObject _get = contents.get(0);
        _xifexpression = _get;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public InputStream getInputStream(final String path) {
    Class<? extends Object> _class = this.getClass();
    ClassLoader _classLoader = _class.getClassLoader();
    InputStream _resourceAsStream = _classLoader.getResourceAsStream(path);
    return _resourceAsStream;
  }
  
  public ArrayList<EObject> getResourceContents(final String path) {
    final EObject root = this.getResourceRoot(path);
    boolean _equals = Objects.equal(root, null);
    if (_equals) {
      return Lists.<EObject>newArrayList();
    } else {
      TreeIterator<EObject> _eAllContents = root.eAllContents();
      final ArrayList<EObject> contents = Lists.<EObject>newArrayList(_eAllContents);
      contents.add(0, root);
      return contents;
    }
  }
  
  public <T extends Object> void assertNotEmpty(final Iterable<T> entries) {
    boolean _isEmpty = IterableExtensions.isEmpty(entries);
    if (_isEmpty) {
      Assert.fail("no entries in the iterable.");
    }
  }
}
