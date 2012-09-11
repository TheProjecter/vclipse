/*******************************************************************************
 * Copyright (c) 2012 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     webXcerpt Software GmbH - initial creator
 ******************************************************************************/
package org.vclipse.refactoring.core;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;
import org.vclipse.refactoring.RefactoringPlugin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

public abstract class MethodCollector {

	protected Multimap<String, Method> name2Method;
	
	public MethodCollector() {
		name2Method = HashMultimap.create();
	}
	
	protected Object invoke(IRefactoringContext context, String prefix) {
		Object result = null;
		prefix = prefix + context.getType();
		Pair<EObject, Method> pair = getMethod(context.getSourceElement(), context.getStructuralFeature(), prefix);
		if(pair != null) {
			try {
				Method method = pair.getSecond();
				List<Object> params = Lists.newArrayList();
				if(method.getParameterTypes().length == 1) {
					params.add(context);
				} else if(method.getParameterTypes().length == 2) {
					params.add(context);
					params.add(pair.getFirst());
				}
				result = pair.getSecond().invoke(this, params.toArray());
			} catch(Exception exception) {
				RefactoringPlugin.log(exception.getMessage(), exception);
			} 
		}
		return result;
	}
	
	protected void collect(int paramCount) {
		for(Method method : getClass().getMethods()) {
			if(method.getParameterTypes().length == paramCount) {
				name2Method.put(method.getName(), method);
			}
		}
	}
	
	protected void collect(int paramCount, Class<?> ... paramTypes) {
		if(paramCount != paramTypes.length) {
			throw new IllegalArgumentException("paramCount should be equalt to paramTypes length");
		} else {
			List<Class<?>> requiredSignature = Lists.newArrayList(paramTypes);
			for(Method method : getClass().getMethods()) {
				List<Class<?>> existingSignature = Lists.newArrayList(method.getParameterTypes());
				if(equal(requiredSignature, existingSignature)) {
					name2Method.put(method.getName(), method);
				}
			}
		}
	}
	
	protected void collect(int paramCount, List<Integer> indexes, Class<?> ... types) {
		if(indexes.size() != types.length) {
			throw new IllegalArgumentException("The size of idexes list should be equal to the types length");
		} else {
			for(Method method : getClass().getMethods()) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if(parameterTypes.length == paramCount) {
					List<Class<?>> existingSignature = Lists.newArrayList();
					List<Class<?>> requiredSignature = Lists.newArrayList();
					for(int index : indexes) {
						existingSignature.add(parameterTypes[index]);
						requiredSignature.add(types[index]);
					}
					if(equal(existingSignature, requiredSignature)) {
						name2Method.put(method.getName(), method);
					}
				}
			}
		}
	}
	
	protected Pair<EObject, Method> getMethod(EObject object, EStructuralFeature feature, String prefix) {
		if(object == null || prefix==null || prefix.isEmpty()) {
			return null;
		}
		List<String> methodNames = Lists.newArrayList();
		if(feature != null) {
			// prefix_RefactoringType_feature
			methodNames.add(prefix + "_" + feature.getName());
			// prefix_RefactoringType_TypeName_feature
			methodNames.add(prefix + "_" + object.eClass().getName() + "_" + feature.getName());
		}
		
		// prefix_RefactoringType_TypeName
		methodNames.add(prefix + "_" + object.eClass().getName());
		// prefix_RefactoringType
		methodNames.add(prefix);
		
		for(String methodname : methodNames) {
			Pair<EObject, Method> pair = iterateMethodCollection(object, methodname);
			if(pair != null) {

				return pair;
			}
		}
		
		// try container
		return getMethod(object.eContainer(), feature, prefix);
	}
	
	protected Pair<EObject, Method> iterateMethodCollection(EObject object, String methodname) {
		Iterator<Method> iterator = name2Method.get(methodname).iterator();
		while(iterator.hasNext()) {
			Method current = iterator.next();
			return Tuples.create(object, current);
		}
		return null;
	}

	private boolean equal(List<Class<?>> signature_one, List<Class<?>> signature_two) {
		if(signature_one.size() != signature_two.size()) {
			return false;
		} else {
			for(int i=0; i<signature_one.size(); i++) {
				if(signature_one.get(i) != signature_two.get(i)) {
					return false;
				}
			}
		}
		return true;
	}
}