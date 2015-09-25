

# VCML Documentation #

Please note that this document is _not_ an introduction into SAP Variant Configuration.
The [official documentation by SAP on LO-VC](http://help.sap.com/printdocu/core/print46c/en/data/pdf/LOVC/LOVC.pdf) or the book [Variant Configuration with SAP](http://www.sap-press.com/products/Variant-Configuration-with-SAP-(2nd-Edition).html) by Uwe Blumöhr, Manfred Münch, and Marin Ukalovic
as well as the SAP training courses provide introductory as well as in-depth information.

## Introduction to VCML Model Structure ##

VCML provides a textual language to represent complete product models of the SAP Variant Configurator (LO-VC) or SAP IPC.

VCML source code is organized in two different kinds of files:
  * files with extension `.vcml` contain all objects of a product model, e.g., characteristics, classes, configuration profiles, or dependency nets.
  * files with extensions `.cons`, `.proc`, `.sel`, and `.pre` contain the source code of dependencies of type _constraint_, _procedure_, _selection condition_, and _precondition_, respectively, in the respective language for dependencies as defined by SAP. These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the dependency files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency as defined in the VCML file, with the respective extension given above.

In the following the VCML file structure and the VCML-specific language syntax is described.
Each `.vcml` file contains the textual representation of a VCML model. It is built of three basic building blocks: imports, options, and definitions of VC objects, as shown below:

```
VcmlModel :	
	Import*
	('options' '{' Option* '}')?
   	VCObject*
```

Note that these building blocks must be entered in the given order.

For a complete overview on VCML grammar you can have a look into the parser definition file [VCML.xtext](http://code.google.com/a/eclipselabs.org/p/vclipse/source/browse/org.vclipse.vcml/src/org/vclipse/vcml/VCML.xtext).
Please refer to the GeneralGrammarDefinitions for basic grammar language definitions used by VCML.

## Imports ##

The model can contain several import statements to import VC objects from other `.vcml` files.
The VC objects in the imported VCML files are then visible inside the importing VCML file.

Applications:
  * Re-use of common data: information which is required for several or even all models can be stored in a central place and reused  wherever it is needed/applicable.
  * Create variants of a model: you can create variants of an existing model by importing the complete model and just modify several dedicated definitions. The original model is preserved.
> > Note: In this case all modifications done in the original model will automatically apply to the variant, too.

An import is defined as follows:
```
Import :
	'import' String
```

The import statement consists of the keyword `import`, followed by a `String`. This string represents the path to the source of the file or data which is to be imported.
The path can be given as absolute path or as relative path with respect to the `.vcml` file.

Example of an import statement with absolute path:
```
import "platform:/resource/PROJECTNAME/FOLDERNAME/FILENAME.vcml"
```

Example of an import statement with relative path:
```
import "../../FOLDERNAME/FILENAME.vcml"
```

## Options ##

Optionally, a VCML model can contain one or more options. These options influence how objects are processed.

```
Option :
	Optiontype '=' String
```

An option definition starts with the option type, the equals `=` symbol, and the value of the option, given as a `String`.

Currently, the following option types are defined:
  * `UPS`: When IDocs are created from the VCML document, the value of the UPS option is used as object key for the generated ALE distribution packet.
  * `ECM`: The value of the ECM option is used as engineering change master for all objects.
  * `KeyDate`: The value of the KeyDate option is used as key date for all objects.

Option definitions at the beginning of a file are valid for all VC objects. Additionally, options can be defined for individual VC objects. These VC object specific options override the general options.

Example for a general and VC-object individual ECM option:
```
ECM = "AB123"
billofmaterial CAR [ECM = "DE987"]
```

## VC Objects Overview ##

Each file can contain several top level VC objects. They contain all definitions required to make the product model usable in SAP Variant Configuration.

In the `.vcml` file the following kinds of VC objects can be defined:

```
VCObject : 
		BillOfMaterial
	|	Characteristic
	| 	Class
	| 	ConfigurationProfile
	| 	Constraint 
	| 	DependencyNet
	| 	InterfaceDesign
	| 	Material
	| 	Precondition 
	| 	Procedure 
	| 	SelectionCondition 
	| 	VariantFunction
	| 	VariantTable
	| 	VariantTableContent
```

The following table gives a short information about the purpose of each kind of VC object. For definition and examples see the dedicated chapters in the [detailed descriptions of the VC Objects](VCMLLanguage#Detailed_Descriptions_of_the_VC_Objects.md). For further information see the official SAP documentation.
| **VC Object** | **Purpose** |
|:--------------|:------------|
| [Bill of Material](VCMLLanguage#Bill_of_Material.md) | The BOM contains all items (materials) which are required to cover all variants of the product and assigns them to the product model. |
| [Characteristic](VCMLLanguage#Characteristic.md) | The characteristics precisely define all selectable options or features of a configurable product, together with their feature values. Characteristics can be assigned object dependencies and/or restrictions to ensure that the configurations are consistent and complete, i.e. they only contain valid combinations of features and feature values. Characteristics can be used for static information (display only) as well as for variable options. These variable options again may or may not allow user input. |
| [Class](VCMLLanguage#Class.md) | In the standard SAP the class type 300 is the class type for variants. In the `.vcml` file all characteristics defined for the product model are assigned to this class to enable their usage for material configuration.|
| [Configuration Profile](VCMLLanguage#Configuration_Profile.md) | The configuration profile is mandatory for each configurable object. It contains general settings, like the product material??, the user interface design as well as standard networks (NPs) ?? and general procedures (PPs)?? which are to be used. Dependencies are also defined in the configuration profile.  The definitions depend on the object type. |
| [Constraint](VCMLLanguage#Constraint.md) | Constraints monitor the consistency of a configuration. They describe dependencies between characteristics and contain information on which conditions must be fulfilled if the configuration is to be consistent. According to SAP constraints must belong to a dependency net. |
| [Dependency Net](VCMLLanguage#Dependency_Net.md) | The dependency net groups all constraints for a product model and provides like a single point of entry to the constraints of the whole configuration. |
| [Interface Design](VCMLLanguage#Interface_Design.md) | The interface design supports grouping of characteristics to enable a structured layout. |
| [Material](VCMLLanguage#Material.md) | Material is used to define name and type of every single configurable item of the product as well as for the whole product. For product material, additionally  general features like type, assigned bill of materials, classes and configuration profiles which are used for the model are defined. |
| [Precondition](VCMLLanguage#Precondition.md) | Preconditions support restricting or hiding of characteristics or characteristic values in case that they are only valid if the precondition is fulfilled. This ensures that a configuration is consistent. |
| [Procedure](VCMLLanguage#Procedure.md) | Procedures allow to internally/automatically modify values in the model. They can be assigned to characteristics, characteristic values, the configuration profile, BOM items and operations, which will then trigger a specified value change.|
| [Selection Condition](VCMLLanguage#Selection_Condition.md) | The assignment of a selection condition ensures that all objects required for a variant are selected. It can be assigned e.g. to characteristics and BOM items.  |
| [Variant Function](VCMLLanguage#Variant_Function.md) | Variant functions allow to create more complex object dependencies than possible with constraint, precondition, procedure, or selection condition.  |
| [Variant Table](VCMLLanguage#Variant_Table.md) | A variant table supports restriction of characteristic values to valid combinations.  |
| [Variant Table Content](VCMLLanguage#Variant_Table_Content.md) | Here the content of a variant table is defined.|

The minimum requirement for a configurable product is the definition of the material (for the product itself as well as for the individual items), a characteristic with selectable values and the assignment of a class and a configuration profile. By adding additional characteristics and other kinds of VC objects any product however complex can be modeled for variant configuration.

Each declaration of a VC object itself consists of the dedicated keyword as listed in  [Keywords for VC Object Declaration](VCMLLanguage#Keywords_for_VC_Object_Declaration.md), followed by the mandatory assignment of a name, and an optional body.
The body can contain other objects or references to other VC objects defined elsewhere in the `.vcml` file.
There are some types of identifiers and basic sublevel objects/features that are used in a number of VC objects while most sublevel objects/features are specific to a VC object.
The identifiers and sublevel objects/features themselves are separately declared later on in the vcml file. Using this hierarchical declaration structure, all identifiers and sub level objects/features are defined only once and can be used/referenced by all other objects and VC objects.
[Identifier, Symbol, Extended Identifier and Classname](VCMLLanguage#Identifier,_Symbol,_Extended_Identifier_and_Classname.md) provides more information on the identifiers syntax and usage for the different VC objects.
[Basic Options/Features](VCMLLanguage#Basic_Options/Features.md) provides more information on the common basic objects.
The declaration of the sublevel objects/features specific to VC objects is provided in [Detailed Descriptions of the VC Objects](VCMLLanguage#Detailed_Descriptions_of_the_VC_Objects.md).

### Basic Options/Features ###
The following chapters give a brief overview of the basic options/features. For detailed information see the official SAP documentation.

#### Description ####

Nearly every VC object has a description. For some VC objects it is mandatory and for some it is optional, see the respective sub chapter in the [detailed descriptions of the VC Objects](VCMLLanguage#Detailed_Descriptions_of_the_VC_Objects.md).
Descriptions can be single-language descriptions or multi-language descriptions. All SAP-supported ISO language codes can be used.
In the case of single-language descriptions, the default language defined in VClipse preferences is used, if specified. Otherwise the English language (EN) is used.
Descriptions are limited to 30 characters. In case longer texts are required, you can use the object/feature documentation, see [Documentation](VCMLLanguage#Documentation.md).

A description is defined as follows:
```
Description:
	SimpleDescription | MultiLanguageDescriptions

SimpleDescription:
	'description' STRING

MultiLanguageDescriptions : 
	{MultiLanguageDescriptions}
	'description' '{' MultiLanguageDescription* '}'

MultiLanguageDescription :
	Language STRING
```

Both description definitions start with the keyword `description`. In case of a simple description the keyword is followed by a single `STRING`.
In case of a multi-language description the keyword is followed by the 2-character keyword for the language definition and the `STRING` with the text in the respective language. You can add any number of languages.
See [Keywords for Language Declaration](VCMLLanguage#Keywords_for_Language_Declaration.md) for the supported keywords for `Language`.

Example for a single-language description:
```
description "the description of the VC object"
```

Example for a multi-language description:
```
description {
  EN "the description of the VC object"
  DE "die Beschreibung des VC-Objekts"
  FR "la description de l'objet VC"
}
```

#### Documentation ####

Several VC objects can have an optional feature documentation, which allows to give detailed information using more than the 30 characters supported by the description.
Same as with descriptions, documentations can be single-language or multi-language.
In the case of single-language documentations, the default language defined in VClipse preferences is used, if specified. Otherwise the English language (EN) is used.

A documentation is defined as follows:
```
Documentation:
	SimpleDocumentation | MultiLanguageDocumentations

SimpleDocumentation :
	'documentation' STRING
	
MultipleLanguageDocumentation :
	{MultipleLanguageDocumentation}
	'documentation' '{' MultipleLanguageDocumentation_LanguageBlock* '}'
	;

MultipleLanguageDocumentation_LanguageBlock :
	Language (FormattedDocumentationBlock)*
	;

FormattedDocumentationBlock :
	STRING ('format' STRING)?
```

Both documentation definitions start with the keyword `documentation`. In case of a simple documentation the keyword is followed by a single `STRING`.
In case of a multi-language documentation the keyword is followed by the 2-character keyword for the language definition and the `STRING` with the text in the respective language. To attach a format information to a text block, you can optionally add the keyword `format` followed by a `STRING`.
You can add any number of languages. [Keywords for Language Declaration](VCMLLanguage#Keywords_for_Language_Declaration.md) lists all supported keywords for `Language`.

Example for a single-language documentation:
```
documentation "the detailed documentation of the VC object"
```

Example for a multi-language documentation with format information for the German text block:
```
documentation {
  EN "the detailed documentation of the VC object"
  DE "die detaillierte Dokumentation des VC-Objekts" format "bold"		
}
```

<a href='Hidden comment: 
check on format definition
'></a>

#### Status ####

Many VC objects can have an optional feature status, which gives information on the life cycle status of the object and is relevant for the SAP class maintenance. With the assignment of the respective status value you can set an object to be usable for variant configuration or you can temporarily or even permanently stop its usage.

A status is defined as follows:
```
Status:
	'status' ('released' | 'inPreparation' | 'locked')
```
The status definition starts with the keyword `status` which is followed by one of the values `released`, `inPreparation`, or `locked`.

Example for a status is
```
status released
```

#### Group ####

Many VC objects can have an optional feature group, which refers to the SAP feature characteristics group. Assigning the VC object to a group enables you to easily search for all VC objects of the same group.

A group is defined as follows:
```
Group:
	'group' STRING
```
The group definition starts with the keyword `group` which is followed by a `STRING` defining the name of the group.
In SAP, groups are defined in customization and thus have a restricted range of possible values.
In VCML, they are written as strings and are therefore not restricted regarding the character sequence. Still, the length of a group name is restricted to 10 characters.

Example for a group:
```
group "PROD_CAR"
```

## Detailed Descriptions of the VC Objects ##

In this chapter, all VC objects are described in detail with their corresponding individual sub-level objects/features and attributes. They are listed in alphabetical order.
[VC Objects Overview](VCMLLanguage#VC_Objects_Overview.md) provides general information on VC objects.

### Bill of Material ###

The bill of material is a list of configurable items.
The bill of material must be defined for the complete product, which is then the master BOM for all product variants.
This master BOM can directly list every single item of the product, see example 1, or list other BOMs for groups of items, like BOMs for engine, gear, etc. as shown in example 2.
You can also mix single items and BOMs in a BOM definition.
To distinguish between mandatory material and variable, i.e. configurable material, the object/feature selection condition is used.
Declaration of a selection condition makes the material a variable part, i.e. it will only be added to the product if a specified condition is fulfilled.

A bill of material is defined as follows:

```
BillOfMaterial :
	'billofmaterial' EXTENDED_ID ('[' Option* ']')? '{'
		'material' '{'EXTENDED_ID'}'?
		'items' '{' BOMItem* '}'?
	'}'

BOMItem :
	BOMItem_Material | BOMItem_Class

BOMItem_Material :
	INT EXTENDED_ID
	('dependencies' '{'
		EXTENDED_ID?
	 	ConfigurationProfileEntry*
	'}')?

BOMItem_Class :
	INT CLASSNAME
	('dependencies' '{'
		EXTENDED_ID?
	 	ConfigurationProfileEntry*
	'}')?
	
ConfigurationProfileEntry :
	INT EXTENDED_ID
```

The bill of material definition starts with the keyword `billofmaterial` followed by the name of the bill of material, textually represented as an `EXTENDED_ID`.

This can be enhanced by an optional body with two optional features, `material` and `items`.
The material referenced here is the material of the product, consisting of the keyword `material` followed by the name of the corresponding material represented by an `EXTENDED_ID`.
The feature items consists of the keyword `items` followed by a list of all BOM items, i.e. all configurable items belonging to the product.

BOM items can either be materials or classes (class nodes). Their definition starts with an integer number `INT`, representing the item number. This is followed by the name of this dedicated item, textually represented as an `EXTENDED_ID` for materials or the `CLASSNAME` for classes.
Optionally, `dependencies` can be added giving the reference to a [Selection Condition](VCMLLanguage#Selection_Condition.md), and to procedures using `ConfigurationProfileEntry`.
The configuration profile entry is defined by an integer referring to the sequence of the entry and a reference to a procedure, textually represented as an `EXTENDED_ID`.

Example 1 for BOM with single items:
```
billofmaterial 'bom_car' {
	material 'CAR'
	items {
		10 Engine_1500
		 dependencies { SELC_CAR1
	  			10 PP_CAR1 }
		20 Engine_1800
		 dependencies { 10 PP_CAR2 }	
		30 Engine_2100
		 dependencies { 10 PP_CAR3 }	
		40 Engine_2400
		 dependencies { 10 PP_CAR4	
		 		20 PP_CAR_5 }
		50 Gear_4
		60 Gear_4automatic
		70 Gear_5
		80 Gear5automatic
		90 Interior_01
		100 Interior_02
		110 Interior_03
		120 Colour_black
		130 Colour_white
		140 Colour_red
	}
}
```

Example 2 for BOM with further BOMs and single items:
```
billofmaterial 'bom_car' {
	material 'CAR'
	items {
		10 bom_engine
		20 bom_gear
		30 bom_interior
		40 bom_colour
		50 stereo
		60 navigation system
	}
}
billofmaterial 'bom_engine' {
	material 'engine'
	items {
		10 Engine_1500
		 dependencies { 10 PP_CAR1 }
		20 Engine_1800
		 dependencies { 10 PP_CAR2 }
		30 Engine_2100
		 dependencies { 10 PP_CAR3 }
		40 Engine_2400
		 dependencies { 10 PP_CAR4
		 		20 CAR5 }
	}
}
billofmaterial 'bom_gear' {
	material 'gear'
	items {
		10 Gear_4
		20 Gear_4automatic
		30 Gear_5
		40 Gear5automatic
	}
}
billofmaterial 'bom_interior' {
	material 'interior'
	items {
		10 Interior_01
		20 Interior_02
		30 Interior_03
	}
}
billofmaterial 'bom_colour' {
	material 'colour'
	items {
		10 Colour_black
		20 Colour_white
		30 Colour_red
	}
}
```

### Characteristic ###
Characteristics are used to describe precisely of which elements a product is made of, and which values these elements can have. Each variable property requires a characteristic.

A characteristic is defined as follows:
```
Characteristic :
	'characteristic' EXTENDED_ID ('[' Option* ']')? ('{'
		( Description
		& Documentation?
		& CharacteristicType
		& Status?
		& Group?
		& ('[' ('additionalValues'?
			& 'required'?
			& 'restrictable'?
			& 'noDisplay'?
			& 'notReadyForInput'?
			& 'multiValue'?
			& 'displayAllowedValues'?
			& ('table' XID 'field' XID)?)
			']')?
		& CharacteristicOrValueDependencies?
		)
	'}')?
```
A characteristic definition starts with the keyword `characteristic` followed by a `EXTENDED_ID` defining the name of the characteristic.
The characteristic can optionally be enhanced by [Options](VCMLLanguage#Options.md) and a body. If the body is added, a  [Description](VCMLLanguage#Description.md) and the [Characteristic Type](VCMLLanguage#Characteristic_Types.md) are mandatory features while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), [Group](VCMLLanguage#Group.md), a number of [attributes](VCMLLanguage#Characteristic_Attributes.md) enclosed in square brackets `[ ]` and [dependencies](VCMLLanguage#Characteristic_Dependencies.md) are optional.
The entries for the optional features in the body can be made in any order. A characteristic definition without body can be used e.g. to include characteristics as placeholders whose definition will be completed later on.

#### Characteristic Types ####
Three characteristic types are available in VCML, allowing for different types of characteristic values. They are defined as follows:
```
CharacteristicType :
	NumericType | SymbolicType | DateType
```

The **numeric type characteristic** is defined as follows:
```
NumericType : 
	'numeric' '{'
		( ('numberOfChars' INT)
		& ('decimalPlaces' INT)
		& ('unit' STRING)?
		& ('[' ( 'negativeValuesAllowed'?
		& 'intervalValuesAllowed'?)
	 	']')?
		& ('values' '{' NumericCharacteristicValue* '}')?
		)
	'}'

NumericCharacteristicValue :
	'*'?
	NumberListEntryForValues ('{'
		( Documentation?
		& CharacteristicOrValueDependencies?)
		'}')?
```
The numeric characteristic definition starts with the keyword `numeric` and is followed by a mandatory body. The body includes the mandatory objects/features `numberOfChars` and `decimalPlaces`.
The keyword `numberOfChars` followed by an integer number defines the maximum number of digits which is allowed for the values of this characteristic, e.g. 3 to allow values up to 999.
The keyword `decimalPlaces` followed by an integer number defines the maximum number of digits after the decimal point which is allowed for the values of this characteristic, e.g. 2 to allow values with an accuracy of one-hundreth.
Note that the number of characters includes the number of decimal places if defined. Additionally, `unit` and `values` as well as optional attributes can be defined.
The feature unit allows to add a unit of measurement by giving a string, like `"pc"` for pieces or `"l"` for liter, etc.
The feature values allows to define any number of single values or a value range, which can be enhanced by an individual documentation and/or dependencies. The asterisk `*` preceding a value defines it as default value.

Examples for characteristics with numeric type:
```
characteristic ENGINE {
	description "the possible engines for a car"
	numeric { 
		numberOfChars 4
		decimalPlaces 0
		unit "ccm"
		values {1500 1800 2100 2400}
	} 
	status released
	group "PROD_CAR"
}

characteristic ENGINE {
	description "the possible engines for a car"
	numeric { 
		numberOfChars 2
		decimalPlaces 1
		unit "l"
		values {1.5 *1.8 2.1 2.4}			//The default value is 1.8
	} 
	status released
	group "PROD_CAR"
}
characteristic keynumber {
	description "number of the keys for the car"
	numeric {
		numberOfChars 2
		decimalPlaces 0
		[intervalValuesAllowed]
		values { (*2 - 20) {documentation "Many keys may be required for several drivers in big companies" }}
	}
	status released
}
```

The **symbolic type characteristic** allows to define non-numeric names as characteristic values, including boolean true/false decisions.

It is defined as follows:
```
SymbolicType :
	'symbolic' '{'
		( ('numberOfChars' INT)
		& ('[' 'caseSensitive'? ']')?
		& ('values' '{' CharacteristicValue* '}')?
		)
	'}'

CharacteristicValue :
	default?='*'?
	EXTENDED_ID
		('{'
	  	( Description?
	  	& Documentation?
	  	& CharacteristicOrValueDependencies?
	 	)
		'}')?
```
The symbolic characteristic definition starts with the keyword `symbolic` and is followed by a mandatory body. The body includes the mandatory object/feature `numberOfChars`.
The keyword `numberOfChars` followed by an integer number defines the maximum number of characters which is allowed for the names of the characteristic values.
Additionally, the feature `values` and the optional attribute `caseSensitive` can be defined.
The feature values allows to define any number of names, which can be enhanced by an individual description, documentation and/or dependencies. The value name of the symbolic characteristic type is textually represented as an `EXTENDED_ID`. The asterisk `*` preceding a value defines it as default value.
By giving the attribute `caseSensitive` you can distinguish between entries made in upper or lower case.

Examples for characteristics with symbolic type:
```
characteristic gear {
	description "selectable types of gears"
	symbolic {
		numberOfChars 15
		[caseSensitive]
		values { *G_4 {description "4 gear" } G_4a {description "4 gear automatic" } G_5 {description "5 gear" } G_5a {description "5 gear automatic" } }
	}
	status released
}

characteristic model {
	description "available car models"
	symbolic {
		numberOfChars 15
		values { *Alpha Beta Gamma Delta Epsilon }		// since the attribute caseSensitive is not given, the user may enter Alpha as alpha, Alpha, ALPHA, alPha or any other lower/upper case combination as valid model name
	}
	status released
}

characteristic convertible {
	description "Do you want a convertible?"
	symbolic {
		numberOfChars 1
		[ caseSensitive ]
		values { 'F' { description "no" } 'T' { description "yes" } }
	}
	status released
}
```

The **date characteristic type** is defined as follows:
```
DateType : 
	'date' '{'
		( ('[' 'intervalValuesAllowed'? ']')?
		& ('values' '{' DateCharacteristicValue* '}')?
		)
	'}'
	
DateCharacteristicValue :
	default?='*'?
	(DATE ('-' DATE)?
		('{'
	  	( Documentation?
	  	& CharacteristicOrValueDependencies?)
		'}')?
	);

DATE : INT '.' INT '.' INT;
```

The date characteristic definition starts with the keyword `date` and is followed by a mandatory body. The body includes the optional attribute `intervalValuesAllowed` and object/feature `values`.
If the attribute `intervalValuesAllowed` is entered, you may define date ranges (from - to) in the `values` feature.
The feature values allows to define any number of dates or date ranges, which can be enhanced by an individual documentation and/or dependencies. The value name of the date characteristic type is textually represented as `DATE` of format `INT.INT.INT` in the order day. month.year.
The asterisk `*` preceding a value defines it as default value. To allow input of any date skip `values` in the definition.

<a href='Hidden comment: 
Clarify: body is mandatory but both attribute and values is optional??  reason?
'></a>

Examples for characteristics with date type:
```
characteristic Deliverydate {
	description "enter the required delivery date"
	date {
		values { 1.5.2013 15.5.2013 31.5.2013 }
	}
	status released
}
characteristic Deliverydate {
	description "enter the required delivery time range"
	date {
		[intervalValuesAllowed]
		values { 01.05.13 - 31.05.13 }
	}
	status released
}
```

#### Characteristic Attributes ####
The following optional attributes are used as boolean flags to specify certain behaviour for the characteristic. For detailed information on this attributes see also the official SAP documentation.
The attributes can be entered in any order within square brackets.
  * `additionalValues`.... allows other than the values defined via characteristic type to be entered
  * `required`.... a value must be entered/selected for the characteristic, it must not be left empty
  * `restrictable`.... the characteristic values can be restricted dynamically depending e.g. on other entries made during variant configuration
  * `noDisplay`.... the characteristic is not displayed in the variant configuration. This allows to create characteristics which do not directly depend on user input but rather on other characteristics, e.g. for internal calculations
  * `notReadyForInput`.... does not allow manual user input, instead the value can be set e.g. by dependencies
  * `multiValue`.... this characteristic allows input or selection of multiple values
  * `displayAllowedValues`.... all allowed values are displayed for user selection
  * `('table' XID 'field' XID)`....

<a href='Hidden comment: 
Add description to table and field
'></a>

Example for attribute usage for an info text characteristic:
```
characteristic INFO_convertible {
	description "non-availability of convertib"
	documentation "Information on non-availability of convertibles for selected model"
	symbolic {
	numberOfChars 30
	[ restrictable notReadyForInput ]					// restrictable, since the message shall only be displayed for respective model selection. Not ready for input, since only an info text shall be displayed and input is not possible.
	values {
	info { 
		description "For the selected model there i"
		documentation "For the selected model there is no convertible available." 
		}		
	}
	}
	status released
}
```

#### Characteristic Dependencies ####
Dependencies, i.e. conditions, procedures, or constraints can be attached to characteristics or to single characteristic values.

Characteristic or value dependencies are defined as follows:
```
CharacteristicOrValueDependencies :
	'dependencies' '{'
		EXTENDED_ID*
	'}'
```

The dependency definition starts with the keyword `dependencies` and is followed by the name(s) of the attached depedency/ies, which is/are textually represented by `EXTENDED_ID`.
The source code of dependencies of type _constraint_, _procedure_, _selection condition_, and _precondition_, respectively is stored in files with extensions `.cons`, `.proc`, `.sel`, and `.pre`, in the respective language for dependencies as defined by SAP.
These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the dependency files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency, with the respective extension given above.

Example for characteristic with dependency:
```
characteristic convertible {
	description "Do you want a convertible?"
	symbolic {
		numberOfChars 1
		[ caseSensitive ]
		values { 'F' { description "no" } 'T' { description "yes" } }
	}
	status released
	dependencies { CN_CAR_12C3C45 }
}
```

### Class ###

The main purpose of classes is to reference characteristics and attach them to [materials](VCMLLanguage#Material.md) of the model.

A class is defined as follows:
```
Class :
	'class' CLASSNAME ('[' Option* ']')? ('{' 
		( Description
		& Status?
		& Group?
		& 'characteristics' '{' EXTENDED_ID* '}'
		& ('superclasses' '{' CLASSNAME* '}')?
	'}')?

CLASSNAME = '(' INT ')' EXTENDED_ID
```

A class definition starts with the keyword `class` followed by a `CLASSNAME`.
The `CLASSNAME` consists of an integer number enclosed in parentheses defining the class type and an `EXTENDED_ID` defining the name of the class.
The class type must be valid for usage in variant configuration, like (300).

The class can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body.
If the body is added, a [Description](VCMLLanguage#Description.md) is a mandatory feature while [Status](VCMLLanguage#Status.md) and [Group](VCMLLanguage#Group.md) are optional.
All [Characteristics](VCMLLanguage#Characteristics.md) must be referenced here with the keyword `characteristics` and their name, textually represented as an `EXTENDED_ID`.
The number of characteristics in one class is restricted to 999. This total number of characteristics can be extended by adding references to superclasses, since a class inherits all characteristics of its superclasses.
The `superclasses` can be referenced with the keyword and their `CLASSNAME` which is defined as described above.
The entries for the optional features in the body can be made in any order.

A class definition without body can be used e.g. to include general classes in the product model which are defined somewhere else.

Example for a class definition without body stating only the classname:

```
class (300)CAR
```

Example for a class definition with body, including all optional features:

```
class (300)CAR {
	description "this is the class for cars"
	status released
	group "PROD_CAR"
	characteristics {
		ENGINE
		GEAR
		COLOUR
		INTERIOR
	}
	superclasses { (300)CONVERTIBLE }
}
```

### Configuration Profile ###

The configuration profile is a mandatory VC object for every variant configuration model. It provides the central settings applying to the model, the assignment of the dependency net(s) containing all constraints, and the procedures.

The configuration profile is defined as follows:
```
ConfigurationProfile :
	'configurationprofile' EXTENDED_ID ('['Option* ']')? '{'
		( ('material' '{'EXTENDED_ID'})?
		& Status?
		& ('bomapplication' EXTENDED_ID)?
		& ('uidesign' '{'EXTENDED_ID'})?
		& ('fixing' Fixing)?
		)
		EXTENDED_ID*
		ConfigurationProfileEntry*
	'}'

Fixing :
	'fixing' ('entry' | 'top-down' | 'bottom-up' | 'none')
	
ConfigurationProfileEntry :
	INT EXTENDED_ID
```

The configuration profile definition starts with the keyword `configurationprofile` followed by the name of the profile, textually represented as an `EXTENDED_ID`.
This can be encanced by optional [options](VCMLLanguage#Options.md) and a mandatory body. The body contains several optional features, [material](VCMLLanguage#Material.md), [Status](VCMLLanguage#Status.md), `bomapplication`, `uidesign`, and `fixing`.
The keyword `material` followed by an `EXTENDED_ID` defining the name of the master material assigns the configuration profile to a specific material.
The keyword `bomapplication` followed by an `EXTENDED_ID` defining the name of the BOM application selects the BOM explosion level which is to be used for the configuration model.
The keyword `uidesign` followed by an `EXTENDED_ID` defining the name of the user interface design assigns a configuration user [interface design](VCMLLanguage#Interface_Design.md).
The fixing definition starts with the keyword `fixing` which is followed by one of the values `entry`, `top-down`, `bottom-up`, or `none`. This allows to control the handling of order BOMs, see official SAP documentation for more information.
The entries for the optional features in the body can be made in any order.

Additionally, [dependency net(s)](VCMLLanguage#Dependency_Net.md) for the configuration model are assigned, by listing their names textually represented as `EXTENDED_ID`, as well as procedures using `configurationprofileentries`.
The configuration profile entry is defined by an integer referring to the sequence of the entry and a reference to a procedure, textually represented as an `EXTENDED_ID`.

Example for a configuration profile declaration:
```
configurationprofile PRODUCTS_CAR {
	material {CAR}
	status released
	bomapplication SD01
	uidesign {PRODUCTS_CAR}
	DEP_NET_CAR
	10 PP_CAR1
	20 PP_CAR2
	30 PP_GEN_01
	}
```

### Constraint ###

Constraints belong to the object dependencies and declare the configuration rules for the model, like setting of characteristic values and checking values to ensure consistency.
All constraint names are listed in the [dependency net](VCMLLanguage#Dependency_Net.md) of the configuration model.
The source code of every constraint is stored in an individual file with extension in the respective language for dependencies as defined by SAP. These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the constraint files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency, with the extension `.cons`.

A constraint is defined as follows:
```
Constraint :
	'constraint' EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& Documentation?
		& Status?
		& Group?
		)
  '}')?  
```

A constraint definition starts with the keyword `constraint` followed by an `EXTENDED_ID` defining the name of the dependency net.
The constraint definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body.
If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.

Example for a constraint declaration:
```
constraint CN_GEAR1500 {
	description "gears valid for engine 1500"
	status released
	group "PROD_CAR"
	}
```

The constraint `CN_GEAR1500` must be assigned to the characteristic `gear`.

The file `CN_GEAR1500.cons` contains the source code of the constraint:
```
objects: CARS is_a (300)CAR . 
restrictions:
	CARS.CAR_gear = 'Gear_4' if 
	CARS.CAR_engine = '1500',
	CARS.CAR_gear in ('Gear_4', 'Gear_4automatic', 'Gear_5', 'Gear_5automatic') if
	CARS.CAR_engine <> '1500'.
inferences: CARS.CAR_gear.
```

### Dependency Net ###

Each configuration model requires a dependency net which contains all relevant [constraints](VCMLLanguage#Constraint.md), i.e. the declaration of the configuration rules for the model.
In SAP it is also called constraint net. The dependency net must be assigned to the [configuration profile](VCMLLanguage#Configuration_Profile.md).

A dependency net is defined as follows:
```
DependencyNet :
	'dependencynet' EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& Documentation?
		& Status?
		& Group?
		)
		EXTENDED_ID*
  '}')?  
```

A dependency net definition starts with the keyword `dependencynet` followed by an `EXTENDED_ID` defining the name of the dependency net.

The dependency net definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body.
If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for these features in the body can be made in any order.
Additionally, all constraints are listed with their names, textually defined by extended identifiers `EXTENDED_ID`.

Example for a dependency net declaration:
```
dependencynet DEP_NET_CAR {
	description "This is a constraint net for the car"
	status released
	group "PROD_CAR"
	CN_CAR1
	CN_CAR2
	CN_CHECK1
}
```

### Interface Design ###

The definition of an interface design allows to structure, that is to group the characteristics display for value assignment. To use an interface design, it must be assigned to the [configuration profile](VCMLLanguage#Configuration_Profile.md).
If no interface design is defined, all characteristics are displayed in a single tab named `General`.

An interface design is defined as follows:
```
InterfaceDesign :
	'interfacedesign' EXTENDED_ID ('['Option* ']')? ('{'
		CharacteristicGroup*
	'}')?  

CharacteristicGroup :
	'characteristicgroup' EXTENDED_ID '{'
		Description
		EXTENDED_ID*
	'}'
```

An interface design definition starts with the keyword `interfacedesign` followed by an `EXTENDED_ID` defining the name of the interface design.
The interface design definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body, containing any number of `CharacteristicGroup` definitions. These start with the keyword `Characteristicgroup` followed by an `EXTENDED_ID` defining the name of the characteristic group.
Additionally, a language dependent description and an `EXTENDED_ID` for every characteristic that shall be displayed in this group are mandatory.

Example for an interface design declaration:
```
interfacedesign UI_DESIGN {
	characteristicgroup Info {
		description "General Information"
		Headline
		Info1
		Info2
		Info3
	}
	characteristicgroup FunEl {
		description "Functional Elements"
		ENGINE
		GEAR
		STEREO
		NAVI
	}
	characteristicgroup DesignEl {
		description "Design Elements"
		COLOUR
		INTERIOR
	}
}
```

### Material ###

A material is used to define name and type of both a whole product which itself consists of several configurable items as well as every single line item.
For example both the car and all available parts as engines, gears, colors, etc. are declared as material.
For product material, besides name and type general features can be added, like the assignment of classes, classification, bill of materials, and configuration profiles which apply to the product.
Please note, that material objects have no documentation, group, and status features.

A material is defined as follows:
```
Material :
	'material' EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& 'type' EXTENDED_ID
		& 'billofmaterials' '{' EXTENDED_ID* '}'?
		& 'classes' '{' Classification* '}'?
		& 'configurationprofiles' '{' EXTENDED_ID* '}'?
		)
	'}')?

Classification
	CLASSNAME ('{' ValueAssignment* '}')?

ValueAssignment :
	EXTENDED_ID* '=' (NumericLiteral | SymbolicLiteral)+
```

A material definition starts with the keyword `material` followed by an `EXTENDED_ID` defining the name of the material.

The material definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body.

If the body is added, [Description](VCMLLanguage#Description.md) and `type` are mandatory features. The type is defined by an identifier (`ID`), typical types are `KMAT` (configurable material) or `HAWA` (trading goods).
Additionally, [bill of materials](VCMLLanguage#Bill_of_Materials.md), [configurationprofile](VCMLLanguage#Configuration_Profile.md) and [classes](VCMLLanguage#Class.md) can be referenced.
These features are required when specifying the material of a complete product, see also example below.

By referencing the `bill of material` all configurable items available for the product are assigned to the product as BOM items. Thus the bill of material is like a master BOM. The items valid for a specific variant configuration are picked from this master BOM.
Adding `configuration profiles` to a material allows to define general settings and further object dependencies.
The references to classes allow to classify the product material with a set of classes. Additionally, values can be assigned to characteristics by means of classification.
The entries for the optional features in the body can be made in any order.

Example for a material definition for a product, i.e. including a bill of material, a class, and a configuration profile:
```
material CAR {
	description "A configurable car"
	type KMAT
	classes { (300)CAR }
	billofmaterials { 'bom_car' }
	configurationprofiles { 'profile_car' }
}
```

Example for a material definition for line items without any optional features:
```
material Engine_1500 {
	description "Small engine"
	type HAWA
}

material Engine_2400 {
	description "Big engine"
	type HAWA
}
```

### Precondition ###

The VC object precondition belongs to the object dependencies and can be assigned to characteristics and charcteristic values. Individual characteristics or characteristic values are hidden in case the circumstances defined in the precondition are met (i.e. the given value is selected or **no** value is selected).
The source code of every precondition is stored in an individual file in the respective language for dependencies as defined by SAP. These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the precondition files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency, with the extension `.pre`.

A precondition is defined as follows:
```
Precondition :
	'precondition' EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& Documentation?
		& Status?
		& Group?
		)
	'}')?
```

A precondition definition starts with the keyword `precondition` followed by an `EXTENDED_ID` defining the name of the precondition.
The precondition definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body. If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.

Example for a precondition _selection of engines 2100 or 2400 are precondition for allowing automatic gear values_:
```
precondition PRECOND_AUTGEAR {
	description "engine 1500, 1800 allows no automatic gear"
	status released
	group "PROD_CAR"
	}
```

The precondition `PRECOND_AUTGEAR` must be assigned to the values `gear_4automatic` and `gear_5automatic`.

The file `PRECOND_AUTGEAR.pre` contains the source code of the precondition:
```
$ROOT.ENGINE in ('2100', '2400')
```

### Procedure ###

The VC object procedure belongs to the object dependencies and controls characteristic values in the configuration model. It can be assigned to the configuration profile, characteristics or characteristic values. Procedures allow to infer values for characteristics, e.g. to set or to override default values. See official SAP documentation for more information.
The source code of every procedure is stored in an individual file in the respective language for dependencies as defined by SAP. These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the procedure files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency, with the extension `.proc`.

A procedure is defined as follows:
```
Procedure :
	'procedure' EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& Documentation?
		& Status?
		& Group?
		)
    '}')?
```

A procedure definition starts with the keyword `procedure` followed by an `EXTENDED_ID` defining the name of the procedure.
The procedure definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body. If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.

Example for a procedure _set 4 gear in case for engine value '1500' is selected_:
```
procedure PROC_INTQ {
	description "set gear for engine 1500"
	documentation "documentation"
	status released
	group "PROD_CAR"
}
```

The procedure `PROC_INTQ` must be assigned to the value `'1500'` of the characteristic `gear`.

The file `PROC_INTQ.proc` contains the source code of the precondition:
```
$self.GEAR = 'Gear_4' if $root.ENGINE = '1500'
```

### Selection Condition ###

The VC object selction condition belongs to the object dependencies. Selection conditions can be assigned e.g. to [characteristics](VCMLLanguage#Characteristics.md) and to BOM items in the [bill of material](VCMLLanguage#Bill_of_Material.md) definition. They define under which condition a characteristic must be assigned a value or a BOM item has to be added to the configuration result.
The source code of every selction condition is stored in an individual file in the respective language for dependencies as defined by SAP. These files have to be located in a dedicated sub folder: for a VCML file with name `FILENAME.vcml`, the selction condition files have to be located in a folder `FILENAME-dep`. The name of the dependency source-code file must be the same as the name of the dependency, with the extension `.sel`.

A selection condition is defined as follows:
```
SelectionCondition :
	'selectioncondition'  EXTENDED_ID ('['Option* ']')? ('{'
		( Description
		& Documentation?
		& Status?
		& Group?
		)
  '}')?
```

A selction condition definition starts with the keyword `selctioncondition` followed by an `EXTENDED_ID` defining the name of the selction condition.
The selction condition definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body. If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Documentation](VCMLLanguage#Documentation.md), [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.

Example for a selection condition _ensuring to add a navi in case of engine 2100 or 2400 is selected_:
```
selectioncondition SELCOND_NAVI {
	description "navigation system"
	documentation "adds navigation system if engine 2100 or 2400 is selected"
	status released
}
```

The selection condition `SELCOND_NAVI` must be assigned to the BOM item `NAVI`.

The file `SELCOND_NAVI.sel` contains the source code of the precondition:
```
$ROOT.ENGINE in ('2100', '2400')
```

### Variant Function ###

A variant function can be used in object dependencies, i.e. constraints, preconditions, procedures, and selection conditions to realize creation of more complex dependencies. See official SAP documentation for more information.
Only the interface of a variant function is defined here. ABAP respectively Java code is not defined in VCML.

A variant function is defined as follows:
```
VariantFunction :
	'variantfunction' EXTENDED_ID ('[' Option* ']')? ('{'
		( Description
		& Status?
		& Group?
		& ('arguments' '{' VariantFunctionArgument* '}')
		)
		'}')?
	
VariantFunctionArgument : 'in'? EXTENDED_ID
```

A variant function definition starts with the keyword `variantfunction` followed by an EXTENDED\_ID defining the name of the variant function. The variant function definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body. If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.
The arguments of the function are defined with the keyword `arguments` followed by any number of `VariantFunctionArgument`. A variant function argument consists of a mandatory EXTENDED\_ID referring to a characteristic. If the optional keyword `in` is given, the EXTENDED\_ID refers to an **input** characteristic.
Otherwise the EXTENDED\_ID belongs to an **output** characteristic.

Example for a variant function _to set the output value for fuel consumption depending on selection of engine and gear_:
```
variantfunction VAR_FUNC {
	( description "description"
	group "group"
	status locked
	arguments {
		in ENGINE 
		in GEAR 
		FUELCONSUMPTION   
  }
}
```

### Variant Table ###

A variant table contains the valid combinations of values for different characteristics and can be used in object dependencies, i.e. constraints, preconditions, procedures, and selection conditions. Depending on where the table is used it allows to check the consistency of the entered values, to infer or to restrict values. See official SAP documentation for more information.
A VC object variant table defines the table structure and additionally requires the VC object [Variant Table Content](VCMLLanguage#Variant_Table_Content.md) providing the table entries.

The structure (scheme) of a variant table is defined as follows:
```
VariantTable :
	'varianttable' EXTENDED_ID ('[' Option* ']')? ('{'
	( Description
	& Status?
	& Group?
	& ('arguments' '{' VariantTableArgument* '}') 
	'}')?
    
VariantTableArgument : 'key'? EXTENDED_ID
```

A variant table definition starts with the keyword `varianttable` followed by an `EXTENDED_ID` defining the name of the variant table.
The variant table definition can optionally be enhanced by [options](VCMLLanguage#Options.md) and a body. If the body is added, [Description](VCMLLanguage#Description.md) is a mandatory feature, while [Status](VCMLLanguage#Status.md), and [Group](VCMLLanguage#Group.md) are optional. The entries for the features in the body can be made in any order.
The columns of the table are defined with the keyword `arguments` followed by any number of `VariantTableArgument`. A variant table argument consists of the optional keyword `key` marking the column as the key column and a mandatory EXTENDED\_ID referring to the characteristic.

Example for a variant table _containing the columns ENGINE and GEAR_:
```
varianttable VAR_TAB {
  description "depend. engine and gear"
  status inPreparation
  arguments {
	key ENGINE
	GEAR
  }
}
```

### Variant Table Content ###

The VC object variant table content provides the value entries for a [variant table](VCMLLanguage#Variant_Table.md) listing the valid combinations row by row.
The number of values given per row must be the same as the number of columns defined in the variant table.

The variant table content is defined as follows:
```
VariantTableContent	:	
	'varianttablecontent' EXTENDED_ID ('[' Option* ']')? '{'
		Row*
		'}'

Row :	{Row}
		'row' (SymbolicLiteral | NumericLiteral)*
```

A variant table content definition starts with the keyword `varianttablecontent` followed by an `EXTENDED_ID` defining the name of the respective variant table. Optionally, [options](VCMLLanguage#Options.md) can be defined.
Additionally, one or several rows containing the values are required. Each row definition starts with the keyword `row` followed by the values, textually represented as `SymbolicLiteral` or `NumericLiteral`.

Example for a variant table content _defining the valid value combinations for engine and gear (1500 ccm and 1800 ccm do not allow automatic gear)_:
```
varianttablecontent VAR_TAB {
	{row '1500' gear_4
	row '1500' gear_5
	row '1800' gear_4
	row '1800' gear_5
	row '2100' gear_4
	row '2100' gear_4automatic
	row '2100' gear_5	
	row '2100' gear_5automatic
	row '2400' gear_4
	row '2400' gear_4automatic
	row '2400' gear_5
	row '2400' gear_5automatic
  }
}
```

## General Language Constructs ##

The VCMl language contains various general constructs providing basic definitions which are required repeatedly in a `.vcml` file.
Some constructs, like whitespace or empty lines support the structuring of the file to improve readability.
Another construct allows to make comments for additional information. These are not part of the model and will not be interpreted as code.
There are several constructs which define different kinds of character sequences, like for example strings, symbols, identifiers or even dedicated keywords, which can be assigned to object values or objects.
Operators are also generally defined to support different kind of assignments between objects/features and object values.
For more information see also the [Xtext grammar documentation](http://www.eclipse.org/Xtext/documentation.html#grammarLanguage).

### Whitespace and Empty Line ###

Whitespace and empty lines can be inserted at any place in the `.vcml` file for better readability and structuring of the file content.
Except for keywords which must be preceded and followed by a whitespace to be detectable, whitespace and empty lines are irrelevant for the model and not interpreted as code.

### Comments ###

VCML offers single-line comments and block comments. The comment can start at any place in a line. They are written like in Java or C.
Single-line comments do start with `//` and automatically end with the end of the line.
Block comments start with `/*` and end with `*/`. They can but need not run over several lines. Block comments may not be nested.
Block comments not only allow to give detailed additional information but they can also be used for other purposes:
  * Enter incomplete definitions of objects as block comments which will be completed later and only then included in the model code.
  * Temporarily exclude parts of the model by enclosing it in the block comment syntax, without deleting it completely from the model.

Note that comments can not be used within [Strings](VCMLLanguage#Strings.md).

Examples for single-line comments:

```
// this is a single line comment, starting in the first column of the line
    // this is another single line comment which does not start in the first column
import "platform:/resource/PROJECTNAME/FOLDERNAME/FILENAME.vcml"	//This is an import with an absolute path. (=comment)
```

Examples for block comments:

```
/* this is a block comment
running over two lines
*/

import /*with an absolute path*/ "platform:/resource/PROJECTNAME/FOLDERNAME/FILENAME.vcml "

/*note, that the path has to be completed later on:
import "platform:/resource/??"
*/
```

### Strings ###

Strings are character sequences enclosed in double quotes. They are used e.g. for description and documentation of objects/features.
A string can even be empty. Escape sequences starting with `\` allow to write strings containing special characters, like the newline `\n` or tab character `\t` or the double quote character `\"`.
The contents of strings is case sensitive.

Sample strings are:
```
"" // the empty string
"A sample string"
"A SAMPLE STRING" // another sample string, different from the string before due to case sensitivity
"This is a tab\tand this is a newline\ncharacter" // a string containing special characters for tab and newline
"This is a string with a word written in \"quotes\""
"The backslash character itself can be escaped by writing it twice \\"
"The line comment character sequence // does not introduce a comment in strings"
```

### Identifier, Symbol, Extended Identifier and Classname ###

These constructs are used to define names for objects/features. They allow to build names of different complexity with varying character sets.
Some dedicated character sequences are reserved as [keywords](VCMLLanguage#Keywords.md) and thus cannot be used as identifier, symbol or classname.

#### Identifier ####

The simplest construction is the identifier, which is a character sequence beginning with a lower/upper character (only
ASCII characters are allowed) or underscore, which can be followed by further lower/upper characters, underscore
or numbers in any order. Identifiers are case insensitive.

Examples for valid simple identifiers:
```
_4gear
Engine_1500
wIndOWs_7
color
```

Examples for **invalid** simple identifiers:
```
4gear			//Number  not allowed as first character
Engine-1500		//- not allowed 
```

#### Symbols ####

To allow special characters like `:`, `-`, `+`, etc., or numbers at the first position in character sequences for names of objects/features, the whole character sequence has to be enclosed in siongle quotes.
This is called a symbol. Symbols are case-insensitive and can even be empty.
Note, that escape characters and single quote itself are not allowed.

Examples for valid symbols:
```
'4gear'
'Engine-1500'
'wIndOWs:7'
```

#### Extended Identifier ####

The extended identifier allows to use either an identifier or a symbol as a name and can be used for most objects/features, except e.g. for [classes](VCMLLanguage#Class.md).
Extended identifier can not be empty.

Examples for valid extended identifiers:
```
_4gear
Engine_1500
wIndOWs_7
color
'4gear'
'Engine-1500'
'wIndOWs:7'
```

#### Classname ####

The classname is a special construct to define the name of the VC object kind Class.
It consists of an integer number encloseded by parentheses and a following extended identifier.

Examples for valid classnames:
```
(300)OpSys 
(200) car_kit4
(10)_gears
(100) 'Engines-SVU'
```

### Keywords ###

Following character sequences are used as keywords in the VCML language and can not be used therefore as identifiers. They are case sensitive.
In addition, there are a number of two-character-sequences to define different languages for description and documentation. These are in line with SAP definitions.

#### Keywords for VC Object Declaration ####
```
- billofmaterial			- material
- characteristic			- precondition
- class   				- procedure
- configurationprofile			- selectioncondition
- constraint				- variantfunction
- dependencynet				- varianttable 
- interfacedesign			- varianttablecontent
```


#### Keywords for Declaration of Other Objects/Features and Arguments ####
```
- description				- characteristicgroup
- documentation				- configurationprofiles
- group   				- classes
- status				- billofmaterials
- type					- arguments
- configurationprofileentry		- format
- table					- field
- additionalValues			- displayAllowedvalues
- multiValue				- noDisplay
- notReadyForInput			- required
- restrictable				- negativeValuesAllowed
- intervalValuesAllowed			- caseSensitive
- key					- released
- inPreparation				- locked
- entry					- top-down
- bottom-up				- none
```

#### Keywords for Language Declaration ####
```
Language:
	  AF | AR | BG | CA | CS | DA | DE | EL | EN | ES | ET | FI | FR | HE
	| HR | HU | ID | IS | IT | JA | KO | LT | LV | MS | NL | NO | PL | PT
	| RO | RU | SH | SK | SL | SR | SV | TH | TR | UK | Z1 | ZF | ZH
```


### Literals and Numeric Intervals ###

Literals are used for value assignment. Two types of literals are defined:
  * Numeric literal, which can be positive or negative integer with or without decimal places. Additionally positive or negative exponents are allowed using the character `e`.
  * Symbolic literal, which can be a character sequence as described in [Symbols](VCMLLanguage#Symbols.md). Note that `F` and `T` are used as keywords for false and true (no and yes) literals in boolean context.

Examples for usage of literals:
```
characteristic colour {
	description "colour of the car"
	symbolic {
		numberOfChars 30
	values { 'bk' { description "black" } 'wh' { description "white" } 'rd' { description "red" } }
}
status released
}

characteristic seatnumber {
	description "Number of seats"
	numeric {
	numberOfChars 1
	decimalPlaces 0
	values { 2 4 5 7 }
	}
status released
}

characteristic 'automatic_gear' {
	description "Selction of gear type"
	symbolic {
		numberOfChars 1
		[ caseSensitive ]
		values { 'F' { description "no" } 'T' { description "yes" } }
	}
status released
[ restrictable ]
}
```

To describe numeric intervals the hyphen character `-` is used between the lower bound number and the upper bound number. Additionally you can use standard operator characters to define interval boundaries according to your needs.

Examples for valid numeric intervals:
```
characteristic seatnumber {
	description "Number of seats"
	numeric {
	numberOfChars 1
	decimalPlaces 0
	values { 2 - 7 }
	}
status released
}
	
characteristic keynumber {
	description "Number of keys for the car"
	numeric {
	numberOfChars 2
	decimalPlaces 0
	values { <= 20 }
	}
status released
}
```