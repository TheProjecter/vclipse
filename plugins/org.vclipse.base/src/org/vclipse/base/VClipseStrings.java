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
package org.vclipse.base;

import java.util.List;

import com.google.common.collect.Lists;

public class VClipseStrings extends org.eclipse.xtext.util.Strings {

	// copied from org.eclipse.xtext.util.Strings, but removed \\u conversion
	
	/**
	 * Mostly copied from {@link java.util.Properties#loadConvert}
	 */
	public static String convertFromJavaString(String javaString) {
		char[] in = javaString.toCharArray();
		int off = 0;
		int len = javaString.length();
		char[] convtBuf = new char[len];
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 't')
					aChar = '\t';
				else if (aChar == 'r')
					aChar = '\r';
				else if (aChar == 'n')
					aChar = '\n';
				else if (aChar == 'f')
					aChar = '\f';
				else if (aChar == 'b')
					aChar = '\b';
				else if (aChar == '"')
					aChar = '\"';
				out[outLen++] = aChar;
			}
			else {
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/**
	 * Mostly copied from {@link java.util.Properties#saveConvert}
	 */
	public static String convertToJavaString(String theString) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
				case ' ':
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					break;
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					break;
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					break;
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					break;
				case '\b':
					outBuffer.append('\\');
					outBuffer.append('b');
					break;
				case '"':
					outBuffer.append('\\');
					outBuffer.append('"');
					break;
				default:
					outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 
	 */
	public static List<String> splitCamelCase(String string) {
		boolean upperCase = false;
		List<Integer> indexes = Lists.newArrayList();
		for(int i=0; i<string.length(); i++) {
			if(Character.isUpperCase(string.charAt(i))) {
				if(!upperCase) {
					indexes.add(i);
					upperCase = true;
				}
			} else {
				if(indexes.contains(i - 1)) {
					upperCase = false;
				}
				if(upperCase && i > 0) {
					indexes.add(i-1);
					upperCase = false;
				}
			}
		}
		if(indexes.isEmpty()) {
			return Lists.newArrayList(string);
		}
		List<String> parts = Lists.newArrayList();
		int prev = -1;
		for(Integer index : indexes) {
			if(prev == -1) {
				prev = index;
				if(prev > 0) {
					parts.add(string.substring(0, prev));
				}
			} else {
				parts.add(string.substring(prev, index));
				prev = index;
			}
			if(indexes.indexOf(index) == indexes.size() - 1) {
				parts.add(string.substring(index, string.length()));
			}
		}
		return parts;
	}
	
	/**
	 * 
	 */
	public static String appendTo(String string, List<String> parts, boolean changeToLower) {
		StringBuilder builder = new StringBuilder(string);
		for(String part : parts) {
			builder.append(changeToLower ? part.toLowerCase() : part);
			if(parts.indexOf(string) != parts.size()-1) {
				builder.append(" ");					
			}
		}
		return builder.toString();
	}
}
