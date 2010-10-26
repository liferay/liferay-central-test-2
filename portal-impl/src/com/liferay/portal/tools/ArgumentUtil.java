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

package com.liferay.portal.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ArgumentUtil {

	public static Map<String, String> getArguments(String[] args) {
		Map<String, String> arguments = new HashMap<String, String>();

		for(String arg : args) {
			int index = arg.indexOf('=');
			if (index <= 0) {
				throw new IllegalArgumentException("Bad argument : " + arg);
			}

			String key = arg.substring(0, index).trim();
			String value = arg.substring(index + 1).trim();

			if (key.startsWith("-D")) {
				key = key.substring(2);
				System.setProperty(key, value);
			}
			else {
				arguments.put(key, value);
			}
		}

		return arguments;
	}

}