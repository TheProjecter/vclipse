package org.vclipse.vcml.graph.extensions;

import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.vclipse.vcml.vcml.Classification;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.VCObject;

@SuppressWarnings("all")
public class NodesProvider {
  protected static Iterable<Classification> _nodes(final Material material) {
    EList<Classification> _classifications = material.getClassifications();
    return _classifications;
  }
  
  protected static Iterable<VCObject> _nodes(final org.vclipse.vcml.vcml.Class klass) {
    return null;
  }
  
  public static Iterable<? extends EObject> nodes(final VCObject klass) {
    if (klass instanceof org.vclipse.vcml.vcml.Class) {
      return _nodes((org.vclipse.vcml.vcml.Class)klass);
    } else if (klass instanceof Material) {
      return _nodes((Material)klass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(klass).toString());
    }
  }
}
