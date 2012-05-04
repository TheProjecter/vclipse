package org.vclipse.vcml.ui.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicGroup;
import org.vclipse.vcml.vcml.Classification;
import org.vclipse.vcml.vcml.ConfigurationProfile;
import org.vclipse.vcml.vcml.ConfigurationProfileEntry;
import org.vclipse.vcml.vcml.Constraint;
import org.vclipse.vcml.vcml.DependencyNet;
import org.vclipse.vcml.vcml.InterfaceDesign;
import org.vclipse.vcml.vcml.Material;

@SuppressWarnings("all")
public class NodesProvider {
  protected static List<? extends Object> _nodes(final EObject object) {
    ArrayList<?> _newArrayList = Lists.newArrayList();
    return _newArrayList;
  }
  
  protected static List<? extends Object> _nodes(final InterfaceDesign interfaceDesign) {
    EList<CharacteristicGroup> _characteristicGroups = interfaceDesign.getCharacteristicGroups();
    return _characteristicGroups;
  }
  
  protected static List<? extends Object> _nodes(final org.vclipse.vcml.vcml.Class klass) {
    EList<Characteristic> _characteristics = klass.getCharacteristics();
    return _characteristics;
  }
  
  protected static List<? extends Object> _nodes(final DependencyNet dependencyNet) {
    EList<Constraint> _constraints = dependencyNet.getConstraints();
    return _constraints;
  }
  
  protected static ArrayList<? extends Object> _hiddenNodes(final EObject object) {
    ArrayList<?> _newArrayList = Lists.newArrayList();
    return _newArrayList;
  }
  
  protected static ArrayList<? extends Object> _hiddenNodes(final Material material) {
    final ArrayList<EObject> nodes = Lists.<EObject>newArrayList();
    EList<Classification> _classifications = material.getClassifications();
    final Function1<Classification,org.vclipse.vcml.vcml.Class> _function = new Function1<Classification,org.vclipse.vcml.vcml.Class>() {
        public org.vclipse.vcml.vcml.Class apply(final Classification classification) {
          org.vclipse.vcml.vcml.Class _cls = classification.getCls();
          return _cls;
        }
      };
    List<org.vclipse.vcml.vcml.Class> _map = ListExtensions.<Classification, org.vclipse.vcml.vcml.Class>map(_classifications, _function);
    Iterables.<EObject>addAll(nodes, _map);
    EList<ConfigurationProfile> _configurationprofiles = material.getConfigurationprofiles();
    final Function1<ConfigurationProfile,EList<DependencyNet>> _function_1 = new Function1<ConfigurationProfile,EList<DependencyNet>>() {
        public EList<DependencyNet> apply(final ConfigurationProfile configurationprofile) {
          EList<DependencyNet> _dependencyNets = configurationprofile.getDependencyNets();
          return _dependencyNets;
        }
      };
    List<EList<DependencyNet>> _map_1 = ListExtensions.<ConfigurationProfile, EList<DependencyNet>>map(_configurationprofiles, _function_1);
    Iterable<DependencyNet> _flatten = Iterables.<DependencyNet>concat(_map_1);
    Iterables.<EObject>addAll(nodes, _flatten);
    EList<ConfigurationProfile> _configurationprofiles_1 = material.getConfigurationprofiles();
    final Function1<ConfigurationProfile,EList<ConfigurationProfileEntry>> _function_2 = new Function1<ConfigurationProfile,EList<ConfigurationProfileEntry>>() {
        public EList<ConfigurationProfileEntry> apply(final ConfigurationProfile configurationprofile) {
          EList<ConfigurationProfileEntry> _entries = configurationprofile.getEntries();
          return _entries;
        }
      };
    List<EList<ConfigurationProfileEntry>> _map_2 = ListExtensions.<ConfigurationProfile, EList<ConfigurationProfileEntry>>map(_configurationprofiles_1, _function_2);
    Iterable<ConfigurationProfileEntry> _flatten_1 = Iterables.<ConfigurationProfileEntry>concat(_map_2);
    Iterables.<EObject>addAll(nodes, _flatten_1);
    return nodes;
  }
  
  public static List<? extends Object> nodes(final EObject klass) {
    if (klass instanceof org.vclipse.vcml.vcml.Class) {
      return _nodes((org.vclipse.vcml.vcml.Class)klass);
    } else if (klass instanceof DependencyNet) {
      return _nodes((DependencyNet)klass);
    } else if (klass instanceof InterfaceDesign) {
      return _nodes((InterfaceDesign)klass);
    } else if (klass != null) {
      return _nodes(klass);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(klass).toString());
    }
  }
  
  public static ArrayList<? extends Object> hiddenNodes(final EObject material) {
    if (material instanceof Material) {
      return _hiddenNodes((Material)material);
    } else if (material != null) {
      return _hiddenNodes(material);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(material).toString());
    }
  }
}
