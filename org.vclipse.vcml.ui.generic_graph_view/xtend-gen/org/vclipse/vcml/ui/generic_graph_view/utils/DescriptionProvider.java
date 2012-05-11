package org.vclipse.vcml.ui.generic_graph_view.utils;

import java.util.Arrays;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.Description;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.SimpleDescription;
import org.vclipse.vcml.vcml.VCObject;

@SuppressWarnings("all")
public class DescriptionProvider {
  protected static String _description(final VCObject vcobject) {
    return "";
  }
  
  protected static String _description(final Material material) {
    Description _description = material.getDescription();
    return ((SimpleDescription) _description).getValue();
  }
  
  protected static String _description(final Characteristic characteristic) {
    Description _description = characteristic.getDescription();
    return ((SimpleDescription) _description).getValue();
  }
  
  public static String description(final VCObject characteristic) {
    if (characteristic instanceof Characteristic) {
      return _description((Characteristic)characteristic);
    } else if (characteristic instanceof Material) {
      return _description((Material)characteristic);
    } else if (characteristic != null) {
      return _description(characteristic);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(characteristic).toString());
    }
  }
}
