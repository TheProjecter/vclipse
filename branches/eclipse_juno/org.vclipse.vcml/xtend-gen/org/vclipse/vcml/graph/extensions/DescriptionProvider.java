package org.vclipse.vcml.graph.extensions;

import java.util.Arrays;
import org.vclipse.vcml.vcml.Description;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.SimpleDescription;
import org.vclipse.vcml.vcml.VCObject;

@SuppressWarnings("all")
public class DescriptionProvider {
  protected static String _description(final Material material) {
    Description _description = material.getDescription();
    String _value = ((SimpleDescription) _description).getValue();
    return _value;
  }
  
  protected static String _description(final org.vclipse.vcml.vcml.Class klass) {
    Description _description = klass.getDescription();
    String _value = ((SimpleDescription) _description).getValue();
    return _value;
  }
  
  public static String description(final VCObject klass) {
    if (klass instanceof org.vclipse.vcml.vcml.Class) {
      return _description((org.vclipse.vcml.vcml.Class)klass);
    } else if (klass instanceof Material) {
      return _description((Material)klass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(klass).toString());
    }
  }
}
