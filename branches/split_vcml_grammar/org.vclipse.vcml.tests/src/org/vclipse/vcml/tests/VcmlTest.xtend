package org.vclipse.vcml.tests

import org.eclipse.xtext.junit4.InjectWith
import org.vclipse.vcml.VCMLInjectorProvider
import org.junit.runner.RunWith
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import org.vclipse.vcml.vcml.Model
import org.eclipselabs.xtext.utils.unittesting.XtextTest
import org.junit.Test
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Ignore

@InjectWith(typeof(VCMLInjectorProvider))
@RunWith(typeof(XtextRunner2))

class VcmlTest extends XtextTest {

// @Inject ParseHelper<Model> parser

def testParserRule(CharSequence textToParse, String ruleName) {
        testParserRule(textToParse.toString, ruleName)
}

@Test
def void fileTest() {
	setResourceRoot("classpath:/org.vclipse.vcml.tests/resources")
	//setResourceRoot("file:/resources")
	testFile("VcmlTest/characteristictest.vcml");
}

@Test
def void parseImportTest() {
	'''import "platform:/resource/org.vclipse.vcml.mm/src/org/vclipse/vcml/mm/VCML.ecore"
	'''.testParserRule("Import")
}

@Test
def void parseOptionTest() {
	'''ECM = "engineering change master" 
	'''.testParserRule("Option")
}

@Test
def void parseBillOfMaterialTest() {
	'''
		billofmaterial {
			items {
				1
				item1
				dependencies {
					item0
					2 item2
					3 item3
				}
			}
		}
	'''.testParserRule("BillOfMaterial")
}

@Test
def void parseCharacteristicSymbolicTest() {
	'''
		characteristic CSTIC1 {
			description "test"
			documentation "test-documentation"
			symbolic {numberOfChars 30
				[caseSensitive]
				values {'A' 'B' 'C'}
			}
			status locked
			group "Group1"
			[
				additionalValues
				noDisplay
				multiValue
			]
			dependencies {
				dependency1
				dependency2
			}
		}
	'''.testParserRule("Characteristic")
}

@Test
def void parseCharacteristicNumericTest() {
	'''
		characteristic CSTIC1 {
			description "test"
			documentation "test-documentation"
			numeric {
				numberOfChars 30
				decimalPlaces 2
				unit "unit1"
				[
					negativeValuesAllowed
					intervalValuesAllowed
				]
			}
			status locked
			group "Group1"
			[
				additionalValues
				noDisplay
				multiValue
			]
			dependencies {
				dependency1
				dependency2
			}
		}
	'''.testParserRule("Characteristic")
}

@Test
def void parseCharacteristicDateTest() {
	'''
		characteristic CSTIC1 {
			description "test"
			documentation "test-documentation"
			date {
				[intervalValuesAllowed]
				values {
					01.01.2012-02.01.2012 {
						documentation "Date Documentation"
						dependencies {
							dependency3
						}
					}
				}
			}
			status Locked
			group "Group1"
			[
				additionalValues
				noDisplay
				multiValue
			]
			dependencies {
				dependency1
				dependency2
			}
		}
		'''.testParserRule("Characteristic")
}

@Test
def void parseClassTest() {
	'''
		class (300) DE {
			description"A Test Class"
			status Released
			group "TestGroup"
			characteristics {
				DE
			}
			superclasses {
				(300) myId
			}
		}
	'''.testParserRule("Class")
}

@Test
def void parseConfigurationProfileTest() {
	'''
		configurationprofile testprofile {
			status locked
			bomapplication bomid
			uidesign designid
			netid
			
			dep01		// DependencyNet
			dep02
			
			1 dep03		// ConfigurationProfileEntry
			2 dep04
		}
	'''.testParserRule("ConfigurationProfile")
}

@Test
def void parseDependencyNetTest() {
	'''
			dependencynet netid {
				description "A test profile"
				documentation "A test profile documentation"
				status Released
				group "Dependency Group"
				
				testconstraint
			}
	'''.testParserRule("DependencyNet")
}

@Test
def void parseConstraintTest() {
	'''
	constraint testconstraint {
		description " A test constraint"
		documentation "A test constraint documentation"
		status Locked
		group "Constraint Group"
	}
	'''.testParserRule("Constraint")
}

@Test
def void idTest() {
	testTerminal("abc", "ID")
	testTerminal("abc4", "ID")
	testTerminal("_abc", "ID")
	
	testNotTerminal("1abc", "ID")
	testNotTerminal("#abc", "ID")
}
	
@Test
def void stringTest() {
	testTerminal('''"\\t"'''.toString, "STRING")
	
	testTerminal('''"foo"'''.toString, "STRING")
	
	testTerminal('''"\\tbar"'''.toString, "STRING")
}

@Test
def void symbolTest() {
	testTerminal("'a'", "SYMBOL")
	
	testTerminal("'\b'", "SYMBOL")
	
	testNotTerminal("'\t'", "SYMBOL")
}

}