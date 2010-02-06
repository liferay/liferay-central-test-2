/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <a href="RegexTransformerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RegexTransformerUtil {

	public static List<Pattern> getPatterns() {
		return _instance._patterns;
	}

	public static List<String> getReplacements() {
		return _instance._replacements;
	}

	private RegexTransformerUtil() {
		_patterns = new ArrayList<Pattern>();
		_replacements = new ArrayList<String>();

		for (int i = 0; i < 100; i++) {
			String regex = PropsUtil.get(
				"journal.transformer.regex.pattern." + i);
			String replacement = PropsUtil.get(
				"journal.transformer.regex.replacement." + i);

			if (Validator.isNull(regex) || Validator.isNull(replacement)) {
				break;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Pattern " + regex + " will be replaced with " +
						replacement);
			}

			_patterns.add(Pattern.compile(regex));
			_replacements.add(replacement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RegexTransformerUtil.class);

	private static RegexTransformerUtil _instance = new RegexTransformerUtil();

	private List<Pattern> _patterns;
	private List<String> _replacements;

}