/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="JSONServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class JSONServiceUtil {

	public static String getClassDescriptor(Type type) {
		String signature = type.toString();

		int pos = signature.indexOf("class ");

		if (pos != -1) {
			signature = signature.substring("class ".length());
		}
		else {
			Matcher matcher = _classDescriptorPattern.matcher(signature);

			while (matcher.find()) {
				String name = matcher.group(1);
				String brackets = matcher.group(2);

				StringBundler sb = new StringBundler(2);

				if (Validator.isNotNull(brackets)) {
					brackets = brackets.replace(
						StringPool.CLOSE_BRACKET, StringPool.BLANK);

					sb.append(brackets);
					sb.append(getFieldDescriptor(name));
				}
				else {
					sb.append(name);
				}

				signature = sb.toString();
			}
		}

		return signature;
	}

	public static String getFieldDescriptor(String name) {
		if (name.equals("boolean")) {
			name = "Z";
		}
		if (name.equals("byte")) {
			name = "B";
		}
		else if (name.equals("char")) {
			name = "C";
		}
		else if (name.equals("double")) {
			name = "D";
		}
		else if (name.equals("float")) {
			name = "F";
		}
		else if (name.equals("int")) {
			name = "I";
		}
		else if (name.equals("long")) {
			name = "J";
		}
		else if (name.equals("short")) {
			name = "S";
		}
		else {
			StringBundler sb = new StringBundler(3);

			sb.append(_REFERENCE_TYPE_PREFIX);
			sb.append(name);
			sb.append(StringPool.SEMICOLON);

			name = sb.toString();
		}

		return name;
	}

	private static String _REFERENCE_TYPE_PREFIX = "L";

	private static Pattern _classDescriptorPattern = Pattern.compile(
		"^(.*?)((\\[\\])*)$", Pattern.DOTALL);

}