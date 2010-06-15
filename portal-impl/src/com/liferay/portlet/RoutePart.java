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

package com.liferay.portlet;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="RoutePart.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class RoutePart {

	public RoutePart(String fragment) {
		fragment = fragment.substring(1, fragment.length() - 1);

		if (Validator.isNull(fragment)) {
			throw new IllegalArgumentException("Fragment is null");
		}

		String[] fragmentParts = fragment.split(StringPool.COLON, 2);

		if (fragmentParts.length == 2) {
			String pattern = fragmentParts[0];

			if (Validator.isNull(pattern)) {
				throw new IllegalArgumentException("Pattern is null");
			}

			_pattern = Pattern.compile(pattern);
			_name = fragmentParts[1];
		}
		else {
			_pattern = _defaultPattern;
			_name = fragmentParts[0];
		}

		if (Validator.isNull(_name)) {
			throw new IllegalArgumentException("Name is null");
		}

		_fragmentName = StringPool.OPEN_CURLY_BRACE.concat(_name).concat(
			StringPool.CLOSE_CURLY_BRACE);
	}

	public String getFragmentName() {
		return _fragmentName;
	}

	public String getName() {
		return _name;
	}

	public String getPattern() {
		return _pattern.toString();
	}

	public boolean matches(String parameter) {
		Matcher matcher = _pattern.matcher(parameter);

		return matcher.matches();
	}

	private static Pattern _defaultPattern = Pattern.compile("[^/\\.]+");

	private String _fragmentName;
	private String _name;
	private Pattern _pattern;

}