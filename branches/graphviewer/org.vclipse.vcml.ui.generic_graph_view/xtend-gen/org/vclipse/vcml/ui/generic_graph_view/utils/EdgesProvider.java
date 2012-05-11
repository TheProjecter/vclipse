package org.vclipse.vcml.ui.generic_graph_view.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.vclipse.vcml.vcml.BOMItem;
import org.vclipse.vcml.vcml.BillOfMaterial;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicGroup;
import org.vclipse.vcml.vcml.CharacteristicType;
import org.vclipse.vcml.vcml.CharacteristicValue;
import org.vclipse.vcml.vcml.DateCharacteristicValue;
import org.vclipse.vcml.vcml.DateType;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.NumericCharacteristicValue;
import org.vclipse.vcml.vcml.NumericType;
import org.vclipse.vcml.vcml.SymbolicType;

@SuppressWarnings("all")
public class EdgesProvider {
  protected static Iterable<? extends Object> _edges(final EObject vcobject) {
    return Lists.<Object>newArrayList();
  }
  
  protected static Iterable<? extends Object> _edges(final Material material) {
    EList<BillOfMaterial> _billofmaterials = material.getBillofmaterials();
    final Function1<BillOfMaterial,EList<BOMItem>> _function = new Function1<BillOfMaterial,EList<BOMItem>>() {
        public EList<BOMItem> apply(final BillOfMaterial it) {
          EList<BOMItem> _items = it.getItems();
          return _items;
        }
      };
    List<EList<BOMItem>> _map = ListExtensions.<BillOfMaterial, EList<BOMItem>>map(_billofmaterials, _function);
    Iterable<BOMItem> _flatten = Iterables.<BOMItem>concat(_map);
    final Function1<BOMItem,Material> _function_1 = new Function1<BOMItem,Material>() {
        public Material apply(final BOMItem item) {
          Material _material = item.getMaterial();
          return _material;
        }
      };
    Iterable<Material> _map_1 = IterableExtensions.<BOMItem, Material>map(_flatten, _function_1);
    final Function1<Material,Boolean> _function_2 = new Function1<Material,Boolean>() {
        public Boolean apply(final Material childMaterial) {
          String _type = childMaterial.getType();
          boolean _equals = _type.equals("ZKMT");
          return Boolean.valueOf(_equals);
        }
      };
    Iterable<Material> _filter = IterableExtensions.<Material>filter(_map_1, _function_2);
    return _filter;
  }
  
  protected static List<? extends Object> _hiddenEdges(final EObject vcobject) {
    return Lists.<Object>newArrayList();
  }
  
  protected static List<? extends Object> _hiddenEdges(final Characteristic characteristic) {
    final ArrayList<EObject> edges = Lists.<EObject>newArrayList();
    final CharacteristicType type = characteristic.getType();
    if ((type instanceof SymbolicType)) {
      EList<CharacteristicValue> _values = ((SymbolicType) type).getValues();
      Iterables.<EObject>addAll(edges, _values);
    } else {
      if ((type instanceof NumericType)) {
        EList<NumericCharacteristicValue> _values_1 = ((NumericType) type).getValues();
        Iterables.<EObject>addAll(edges, _values_1);
      } else {
        if ((type instanceof DateType)) {
          EList<DateCharacteristicValue> _values_2 = ((DateType) type).getValues();
          Iterables.<EObject>addAll(edges, _values_2);
        }
      }
    }
    return edges;
  }
  
  protected static List<? extends Object> _hiddenEdges(final CharacteristicGroup csticGroup) {
    return csticGroup.getCharacteristics();
  }
  
  public static Iterable<? extends Object> edges(final EObject material) {
    if (material instanceof Material) {
      return _edges((Material)material);
    } else if (material != null) {
      return _edges(material);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(material).toString());
    }
  }
  
  public static List<? extends Object> hiddenEdges(final EObject characteristic) {
    if (characteristic instanceof Characteristic) {
      return _hiddenEdges((Characteristic)characteristic);
    } else if (characteristic instanceof CharacteristicGroup) {
      return _hiddenEdges((CharacteristicGroup)characteristic);
    } else if (characteristic != null) {
      return _hiddenEdges(characteristic);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(characteristic).toString());
    }
  }
}
