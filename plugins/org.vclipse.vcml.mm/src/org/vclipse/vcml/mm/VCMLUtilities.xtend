/*******************************************************************************
 * Copyright (c) 2010 - 2013 webXcerpt Software GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *     	webXcerpt Software GmbH - initial creator
 * 		www.webxcerpt.com
 ******************************************************************************/
package org.vclipse.vcml.mm

import com.google.common.base.Strings
import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import java.text.DecimalFormat
import java.util.Collections
import java.util.Comparator
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.vclipse.base.naming.INameProvider
import org.vclipse.vcml.vcml.CharacteristicOrValueDependencies
import org.vclipse.vcml.vcml.CharacteristicType
import org.vclipse.vcml.vcml.CharacteristicValue
import org.vclipse.vcml.vcml.NumericCharacteristicValue
import org.vclipse.vcml.vcml.NumericInterval
import org.vclipse.vcml.vcml.NumericLiteral
import org.vclipse.vcml.vcml.NumericType
import org.vclipse.vcml.vcml.Option
import org.vclipse.vcml.vcml.OptionType
import org.vclipse.vcml.vcml.SymbolicType

import static java.lang.Character.*

/**
 * Utilities for VCML Objects.
 */
class VCMLUtilities {
	
	/**
	 * Returns an option with requested type, null if such an option does not exist as an entry.
	 */
	def Option getOption(List<Option> options, OptionType type) {
		for(option : options) {
			if(option.name == type) {
				return option
			}
		}
		return null
	}
	
	/**
	 * Returns a mapping name to value for a characteristic.
	 */
	def dispatch Map<String, EObject> getNameToValue(SymbolicType type) {
		val name2Value = Maps::<String, EObject>newHashMap
		for(value : (type as SymbolicType).values) {
			name2Value.put(value.name, value)
		}	
		return name2Value
	}
	
	/**
	 * Returns a mapping name to value for a characteristic.
	 */
	def dispatch Map<String, EObject> getNameToValue(NumericType type) {
		val name2Value = Maps::<String, EObject>newHashMap
		for(value : (type as NumericType).values) {
			val string = toString(value)
			if(string == null) {
				throw new IllegalArgumentException("Result of the computation should not be null.")
			}
			name2Value.put(string, value)
		}	
		return name2Value
	}
	
	/**
	 * Returns string representation.
	 */
	def String toString(NumericCharacteristicValue value) {
		val entry = value.entry
		if(entry instanceof NumericLiteral) {
			return (entry as NumericLiteral).value
		}
		// the interval values have to be formatted in the following way(the same format as in sap)
		// one can not extract dependencies otherwise
		var formatBuffer = new StringBuffer
		val csticType = EcoreUtil2::getContainerOfType(value, typeof(CharacteristicType))
		if(csticType instanceof NumericType) {
			val numericType = csticType as NumericType
			var decimal = numericType.decimalPlaces
			var numOfChars = numericType.numberOfChars
			while(numOfChars > 0) {
				formatBuffer.append("#")
				numOfChars = numOfChars - 1
				if(numOfChars % decimal == 0 && numOfChars > 1) {
					formatBuffer.append(",")
				}
			}
			if(decimal > 0) {
				formatBuffer.append(".")
				while(decimal > 0) {
					formatBuffer.append("0")
					decimal = decimal - 1
				}
			}
			
			val interval = entry as NumericInterval
			val resultBuffer = new StringBuffer()
			val decimalFormat = new DecimalFormat(formatBuffer.toString)
			resultBuffer.append(decimalFormat.format(new Double(interval.lowerBound)))
			resultBuffer.append(" - ")
			resultBuffer.append(decimalFormat.format(new Double(interval.upperBound)))
			resultBuffer.append(" ").append(numericType.unit.toLowerCase)
			var start = 0
			while(start < resultBuffer.length) {
				val _char = "" + resultBuffer.charAt(start)
				if(",".equals(_char)) {
					resultBuffer.replace(start, start + 1, ".")
				} 
				if(".".equals(_char)) {
					resultBuffer.replace(start, start + 1, ",")
				}
				start = start + 1
			}
			return resultBuffer.toString
		}
		return null
	}
	
	/**
	 * Dependencies of a value are set or returned.
	 */
	def dispatch CharacteristicOrValueDependencies processDependencies(CharacteristicValue value, CharacteristicOrValueDependencies dependencies) {
		if(dependencies == null) {
			return value.dependencies
		}
		value.dependencies = dependencies
	}
	
	/**
	 * Dependencies of a value are set or returned.
	 */
	def dispatch CharacteristicOrValueDependencies processDependencies(NumericCharacteristicValue value, CharacteristicOrValueDependencies dependencies) {
		if(dependencies == null) {
			return value.dependencies
		}
		value.dependencies = dependencies
	}
	
	/**
	 * Sorts a list with a comparator.
	 */
	def <T extends EObject> sortEntries(List<T> entries, Comparator<T> comparator) {
		val entriesCopy = Lists::<T>newArrayList(entries)
		Collections::sort(entriesCopy, comparator)
		entries.clear
		entries.addAll(entriesCopy)
		return
	}
	
	/**
	 * Searches for an entry with a given type and name in entries. 
	 * Returns the first match, null if there is no match.
	 */
	def <T extends EObject> findEntry(String name, EClass type, Iterable<T> entries, INameProvider nameProvider) {
		if(nameProvider == null) {
			return null
		}
		val iterator = entries.iterator
		if(iterator.hasNext) {
			val typedAndNamed = Iterables::filter(entries, [
				T entry |
					val entryName = nameProvider.getName(entry)
					if(Strings::isNullOrEmpty(entryName))
						return false
					return entryName.equals(name) && entry.eClass == type
			]).iterator
			if(typedAndNamed.hasNext) {
				return typedAndNamed.next
			}
		}
		return null
	}
}