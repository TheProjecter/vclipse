package org.vclipse.vcml.tests

import org.eclipselabs.xtext.utils.unittesting.XtextTest
import org.junit.Test
import org.eclipse.xtext.junit4.InjectWith
import org.junit.runner.RunWith
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.vclipse.vcml.ConstraintInjectorProvider
import org.junit.Ignore



@InjectWith(typeof(ConstraintInjectorProvider))
@RunWith(typeof(XtextRunner2))

class ConstraintTest extends XtextTest {


def testParserRule(CharSequence textToParse, String ruleName) {	//TODO: move to new class which extends XtextTest
        testParserRule(textToParse.toString, ruleName)
}

@Test
def void parseConstraintTest1() {
	'''
    objects: Obj is_a (300)MyClass . 
    restrictions:
      MyClass.Param1 in ('A', 'B') if
        MyClass.POWER = 'AC',
      MyClass.CORE in ('A', 'B') if
        MyClass.POWER = 'DC'. 
    inferences: MyClass.CORE. 
	'''.testParserRule("ConstraintSource")
}

@Test
def void parseConstraintTest2() {
	testParserRule('''
    objects: Obj is_a (300)MyClass . 
    restrictions:
      MyClass.Param1 in ('A', 'B') if
        MyClass.POWER = 'AC',
      MyClass.CORE in ('A', 'B') if
        MyClass.POWER = 'DC'. 
    inferences: MyClass.CORE. 
	'''.toString, "Constraint")
}


}