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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="StringParser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class StringParser {

	public StringParser(String pattern) {
		_builder = pattern;

		String regex = escapeRegex(pattern);

		Matcher matcher = _fragmentPattern.matcher(pattern);

		while (matcher.find()) {
			String chunk = matcher.group();

			StringParserFragment stringParserFragment =
				new StringParserFragment(chunk);

			_stringParserFragments.add(stringParserFragment);

			_builder = _builder.replace(chunk, stringParserFragment.getToken());

			regex = regex.replace(
				escapeRegex(chunk),
				StringPool.OPEN_PARENTHESIS.concat(
					stringParserFragment.getPattern().concat(
						StringPool.CLOSE_PARENTHESIS)));
		}

		_pattern = Pattern.compile(regex);
	}

	public String build(Map<String, String> parameters) {
		String s = _builder;

		for (StringParserFragment stringParserFragment :
				_stringParserFragments) {

			String value = parameters.get(stringParserFragment.getName());

			if (value == null) {
				return null;
			}

			if (_stringEncoder != null) {
				value = _stringEncoder.encode(value);
			}

			if (!stringParserFragment.matches(value)) {
				return null;
			}

			s = s.replace(stringParserFragment.getToken(), value);
		}

		for (StringParserFragment stringParserFragment :
				_stringParserFragments) {

			parameters.remove(stringParserFragment.getName());
		}

		return s;
	}

	public static String escapeRegex(String s) {
		Matcher matcher = _escapeRegexPattern.matcher(s);

		return matcher.replaceAll("\\\\$0");
	}

	public boolean parse(String s, Map<String, String> parameters) {
		Matcher matcher = _pattern.matcher(s);

		if (!matcher.matches()) {
			return false;
		}

		for (int i = 1; i <= _stringParserFragments.size(); i++) {
			StringParserFragment stringParserFragment =
				_stringParserFragments.get(i - 1);

			String value = matcher.group(i);

			if ((_stringEncoder != null) && !stringParserFragment.isRaw()) {
				value = _stringEncoder.decode(value);
			}

			parameters.put(stringParserFragment.getName(), value);
		}

		return true;
	}

	public void setStringEncoder(StringEncoder stringEncoder) {
		_stringEncoder = stringEncoder;
	}

	private static Pattern _escapeRegexPattern = Pattern.compile(
		"[\\{\\}\\(\\)\\[\\]\\*\\+\\?\\$\\^\\.\\#\\\\]");
	private static Pattern _fragmentPattern = Pattern.compile("\\{.+?\\}");

	private String _builder;
	private StringEncoder _stringEncoder;
	private List<StringParserFragment> _stringParserFragments =
		new ArrayList<StringParserFragment>();
	private Pattern _pattern;

}