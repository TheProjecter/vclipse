package org.vclipse.vcml.ui.actions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;
import org.vclipse.vcml.parser.antlr.lexer.InternalVCMLLexer;
import org.vclipse.vcml.ui.actions.characteristic.CharacteristicReader;
import org.vclipse.vcml.ui.actions.variantfunction.VariantFunctionReader;
import org.vclipse.vcml.ui.internal.VCMLActivator;
import org.vclipse.vcml.vcml.Characteristic;
import org.vclipse.vcml.vcml.CharacteristicReference_P;
import org.vclipse.vcml.vcml.Literal;
import org.vclipse.vcml.vcml.Model;
import org.vclipse.vcml.vcml.PFunction;
import org.vclipse.vcml.vcml.VariantFunction;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Injector;
import com.sap.conn.jco.JCoException;

public class StringParser {

	private final PolymorphicDispatcher<Void> parserDispatcher = new PolymorphicDispatcher<Void>("parse", 3, 3,
			Collections.singletonList(this), new ErrorHandler<Void>() {
				public Void handle(Object[] params, Throwable e) {
					return null;
				}
			});
	
	private InternalVCMLLexer internalVcmlLexer; 
	private CharacteristicReader csticReader;
	private VariantFunctionReader variantFunctionReader;
	
	public StringParser() {
		Injector injector = VCMLActivator.getInstance().getInjector("org.vclipse.vcml.VCML");
    	internalVcmlLexer = injector.getInstance(InternalVCMLLexer.class);
    	csticReader = new CharacteristicReader();
    	variantFunctionReader = new VariantFunctionReader();
	}
	
	public void parse(EObject parent, String textRepresentation, Model vcmlModel) {
		parserDispatcher.invoke(parent, textRepresentation, vcmlModel);
	}
	
	protected void parse(PFunction function, String textRepresentation, Model vcmlModel) {
		internalVcmlLexer.setCharStream(new ANTLRStringStream(textRepresentation));
		HashSet<String> seenObjects = new HashSet<String>(); 
    	for(Token lastToken=null, currentToken=internalVcmlLexer.nextToken(); currentToken.getText() !=null; currentToken=internalVcmlLexer.nextToken()) {
    		
    		if(currentToken.getType() == InternalVCMLLexer.RULE_WS 
    				|| currentToken.getType() == InternalVCMLLexer.KEYWORD_100
    					|| currentToken.getType() == InternalVCMLLexer.KEYWORD_8) {
    			continue;
    		}
    		
    		if(currentToken.getType() == InternalVCMLLexer.RULE_ID) {
    			if(lastToken != null) {
    				if(lastToken.getType() == InternalVCMLLexer.KEYWORD_138) {
    					String variantFunctionName = currentToken.getText();
    					try {
							VariantFunction variantFunction = variantFunctionReader.read(variantFunctionName, vcmlModel, new NullProgressMonitor(), seenObjects, true);
							seenObjects.add(variantFunction.getName());
							function.setFunction(variantFunction);
    					} catch (JCoException e) {
							e.printStackTrace();
						}
    				} else if(lastToken.getType() == InternalVCMLLexer.KEYWORD_2 || lastToken.getType() == InternalVCMLLexer.KEYWORD_6) {
    					String text = currentToken.getText();
    					try {
							Characteristic characteristic = csticReader.read(text, vcmlModel, new NullProgressMonitor(), seenObjects, true);
							if(characteristic != null) {
								seenObjects.add(characteristic.getName());								
							}
						} catch (JCoException e) {
							e.printStackTrace();
						}
    				} else if(lastToken.getType() == InternalVCMLLexer.KEYWORD_13) {
    					final String text = currentToken.getText();
    					Iterator<Literal> filter = Iterables.filter(function.getValues(), new Predicate<Literal>() {
							@Override
							public boolean apply(Literal input) {
								if(input instanceof CharacteristicReference_P) {
									return ((CharacteristicReference_P)input).getCharacteristic() == null;
								}
								return false;
							}
						}).iterator();
    					if(filter.hasNext()) {
    						Literal literal = filter.next();
    						try {
    							Characteristic characteristic = csticReader.read(text, vcmlModel, new NullProgressMonitor(), seenObjects, true);
    							((CharacteristicReference_P)literal).setCharacteristic(characteristic);
    						} catch(JCoException exception) {
    							exception.printStackTrace();
    						}
    					}
    				}
    			}
    		}
    		lastToken = currentToken;
    	}
    }
}
