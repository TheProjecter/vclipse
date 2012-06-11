package org.vclipse.vcml.tests

import org.eclipselabs.xtext.utils.unittesting.XtextTest
import org.eclipse.xtext.junit4.InjectWith
import org.junit.runner.RunWith
import org.vclipse.vcml.DependencyInjectorProvider
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2
import org.junit.Test

@InjectWith(typeof(DependencyInjectorProvider))
@RunWith(typeof(XtextRunner2))

class DependencyTest extends XtextTest {

def testParserRule(CharSequence textToParse, String ruleName) {	//TODO: move to new class which extends XtextTest
        testParserRule(textToParse.toString, ruleName)
}

@Test
def void fileTest() {
	testFile("VcmlTest/dependencytest.vcml");
}

@Test
def void expressionTest1() {
	'''
		+ (7 * 3) + (2 * 3) 
	'''.testParserRule("Expression")
}

@Test
def void expressionTest2() {
	'''
		++- (7 * 3) + (2 * 3) 
	'''.testParserRule("Expression")
}

@Test
def void expressionTest3() {
	'''
		++- (7 * 3) * (2 * 3) 
	'''.testParserRule("Expression")
}

@Test
def void expressionTest4() {
	'''
		++- (7 + 3) * (2 * 3) 
	'''.testParserRule("Expression")
}

@Test
def void expressionTest5() {
	'''
		('a' * 'b') * ('b' * 7) 
	'''.testParserRule("Expression")
}

@Test
def void expressionTest6() {
	'''
		(sin (7) * 'b') * (cos('b') * 7) 
	'''.testParserRule("Expression")
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
def void dependencyCommentTest() {
	testTerminal('''*asdf
	'''.toString, "DEPENDENCY_COMMENT")
	
	testNotTerminal('''foo'''.toString, "DEPENDENCY_COMMENT")
	
	testNotTerminal('''\\tbar'''.toString, "DEPENDENCY_COMMENT")
}

@Test
def void symbolTest() {
	testTerminal("'a'", "SYMBOL")
	
	testTerminal("'\b'", "SYMBOL")
	
	testNotTerminal("'\t'", "SYMBOL")
}

}