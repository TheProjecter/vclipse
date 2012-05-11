package org.vclipse.vcml.ui.generic_graph_view.utils;

import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.vclipse.vcml.ui.generic_graph_view.utils.DescriptionProvider;
import org.vclipse.vcml.vcml.CharacteristicValue;
import org.vclipse.vcml.vcml.Classification;
import org.vclipse.vcml.vcml.ConfigurationProfileEntry;
import org.vclipse.vcml.vcml.NumberListEntry;
import org.vclipse.vcml.vcml.NumericCharacteristicValue;
import org.vclipse.vcml.vcml.NumericInterval;
import org.vclipse.vcml.vcml.NumericLiteral;
import org.vclipse.vcml.vcml.Procedure;
import org.vclipse.vcml.vcml.SimpleDocumentation;
import org.vclipse.vcml.vcml.SymbolicType;
import org.vclipse.vcml.vcml.VCObject;

@SuppressWarnings("all")
public class NameProvider {
  protected static String _display(final VCObject vcobject) {
    String _description = DescriptionProvider.description(vcobject);
    final String description = (_description + "\n");
    EClass _eClass = vcobject.eClass();
    String _name = _eClass.getName();
    String _lowerCase = _name.toLowerCase();
    String _plus = (" (" + _lowerCase);
    String _plus_1 = (_plus + ")");
    final String classstring = (_plus_1 + "\n");
    final String name = NameProvider.name(vcobject);
    boolean _equals = Objects.equal(description, null);
    if (_equals) {
      return (classstring + name);
    } else {
      String _plus_2 = (description + classstring);
      return (_plus_2 + name);
    }
  }
  
  protected static String _display(final Classification classification) {
    org.vclipse.vcml.vcml.Class _cls = classification.getCls();
    return NameProvider.display(_cls);
  }
  
  protected static String _display(final ConfigurationProfileEntry entry) {
    int _sequence = entry.getSequence();
    String _plus = ("" + Integer.valueOf(_sequence));
    String _plus_1 = (_plus + " ");
    Procedure _dependency = entry.getDependency();
    String _name = _dependency.getName();
    return (_plus_1 + _name);
  }
  
  protected static String _display(final NumericCharacteristicValue value) {
    final NumberListEntry entry = value.getEntry();
    if ((entry instanceof NumericInterval)) {
      String _lowerBound = ((NumericInterval) entry).getLowerBound();
      String _plus = (_lowerBound + " .. ");
      String _upperBound = ((NumericInterval) entry).getUpperBound();
      return (_plus + _upperBound);
    } else {
      if ((entry instanceof NumericLiteral)) {
        return ((NumericLiteral) entry).getValue();
      }
    }
    return "";
  }
  
  protected static String _display(final SymbolicType type) {
    EList<CharacteristicValue> _values = type.getValues();
    final Function1<CharacteristicValue,String> _function = new Function1<CharacteristicValue,String>() {
        public String apply(final CharacteristicValue value) {
          String _name = value.getName();
          return _name;
        }
      };
    List<String> _map = ListExtensions.<CharacteristicValue, String>map(_values, _function);
    return IterableExtensions.join(_map, ", ");
  }
  
  protected static String _display(final SimpleDocumentation documentation) {
    String _value = documentation.getValue();
    return ("Documentation: " + _value);
  }
  
  public static String name(final VCObject vcobject) {
    return vcobject.getName();
  }
  
  public static String display(final EObject documentation) {
    if (documentation instanceof SimpleDocumentation) {
      return _display((SimpleDocumentation)documentation);
    } else if (documentation instanceof SymbolicType) {
      return _display((SymbolicType)documentation);
    } else if (documentation instanceof Classification) {
      return _display((Classification)documentation);
    } else if (documentation instanceof ConfigurationProfileEntry) {
      return _display((ConfigurationProfileEntry)documentation);
    } else if (documentation instanceof NumericCharacteristicValue) {
      return _display((NumericCharacteristicValue)documentation);
    } else if (documentation instanceof VCObject) {
      return _display((VCObject)documentation);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(documentation).toString());
    }
  }
}
