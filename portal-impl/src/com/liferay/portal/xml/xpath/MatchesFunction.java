/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xml.xpath;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;
import org.jaxen.function.StringFunction;

/**
 * @author Raymond Augé
 */
public class MatchesFunction implements Function {

	public Object call(Context context, List args)
		throws FunctionCallException {

		if (args.size() == 2) {
			return evaluate(
				args.get(0), args.get(1), context.getNavigator());
		}
		else if ((args.size() == 3) && (args.get(2) != null)) {
			return evaluate(
				args.get(0), args.get(1), args.get(2),
				context.getNavigator());
		}

		throw new FunctionCallException(
			"matches() requires two or three arguments.");
	}

	public static Boolean evaluate(
		Object strArg, Object regexArg, Navigator nav) {

		String str = StringFunction.evaluate(strArg, nav);
		String regex = StringFunction.evaluate(regexArg, nav);

		return evaluate(str, regex, 0, nav);
	}

	public static Boolean evaluate(
		Object strArg, Object regexArg, Object flagsArg, Navigator nav) {

		String str = StringFunction.evaluate(strArg, nav);
		String regex = StringFunction.evaluate(regexArg, nav);
		String flagsString = StringFunction.evaluate(flagsArg, nav);

		int flags = 0;

		for (int i = 0; i < flagsString.length(); i++) {
			if (flagsString.charAt(i) == 'i') {
				flags &= Pattern.CASE_INSENSITIVE;
			}
			else if (flagsString.charAt(i) == 'm') {
				flags &= Pattern.MULTILINE;
			}
			else if (flagsString.charAt(i) == 's') {
				flags &= Pattern.DOTALL;
			}
			else if (flagsString.charAt(i) == 'x') {
				flags &= Pattern.COMMENTS;
			}
		}

		return evaluate(str, regex, flags, nav);
	}

	public static Boolean evaluate(
		String str, String regex, int flags, Navigator nav) {

		ThreadLocalCache<Map<String,Pattern>> threadLocalPatterns =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.ETERNAL, MatchesFunction.class.getName());

		Map<String,Pattern> patterns = threadLocalPatterns.get(CACHE_KEY);

		if (patterns == null) {
			patterns = new HashMap<String,Pattern>();
		}

		Pattern pattern = patterns.get(regex);

		if (pattern != null) {
			Matcher matcher = pattern.matcher(str);

			return matcher.find();
		}

		pattern = Pattern.compile(regex, flags);

		patterns.put(regex, pattern);

		threadLocalPatterns.put(CACHE_KEY, patterns);

		Matcher matcher = pattern.matcher(str);

		return matcher.find();
	}

	private static final String CACHE_KEY = "matches";

}