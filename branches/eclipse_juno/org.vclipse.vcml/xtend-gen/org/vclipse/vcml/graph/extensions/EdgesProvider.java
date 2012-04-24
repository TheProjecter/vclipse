package org.vclipse.vcml.graph.extensions;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.vclipse.vcml.vcml.BOMItem;
import org.vclipse.vcml.vcml.BillOfMaterial;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicGroup;
import org.vclipse.vcml.vcml.ConfigurationProfile;
import org.vclipse.vcml.vcml.InterfaceDesign;
import org.vclipse.vcml.vcml.Material;
import org.vclipse.vcml.vcml.VCObject;

@SuppressWarnings("all")
public class EdgesProvider {
  protected static Iterable<Material> _edges(final Material material) {
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
  
  protected static Iterable<VCObject> _edges(final Class klass) {
    return null;
  }
  
  protected static Iterable<VCObject> _edges(final InterfaceDesign interfaceDesign) {
    return null;
  }
  
  protected static Iterable<VCObject> _edges(final CharacteristicGroup csticGroup) {
    return null;
  }
  
  protected static Iterable<VCObject> _edges(final Characteristic characteristic) {
    return null;
  }
  
  protected static Iterable<VCObject> _edges(final ConfigurationProfile configurationProfile) {
    return null;
  }
  
  public static Iterable<? extends VCObject> edges(final Object characteristic) {
    if (characteristic instanceof Characteristic) {
      return _edges((Characteristic)characteristic);
    } else if (characteristic instanceof InterfaceDesign) {
      return _edges((InterfaceDesign)characteristic);
    } else if (characteristic instanceof Material) {
      return _edges((Material)characteristic);
    } else if (characteristic instanceof CharacteristicGroup) {
      return _edges((CharacteristicGroup)characteristic);
    } else if (characteristic instanceof ConfigurationProfile) {
      return _edges((ConfigurationProfile)characteristic);
    } else if (characteristic instanceof Class) {
      return _edges((Class)characteristic);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(characteristic).toString());
    }
  }
}
