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

package com.liferay.portal.scripting;

import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="ClassVisibilityChecker.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class ClassVisibilityChecker {

	public static final String ALL_CLASSES = "all_classes";

	public ClassVisibilityChecker(Set<String> allowedClasses) {
		if ((allowedClasses != null) && allowedClasses.contains(ALL_CLASSES)) {
			_allowAll = true;
		}

		if (_forbiddenClasses.contains(ALL_CLASSES)) {
			_denyAll = true;
		}

		if (!_allowAll && !_denyAll) {
			_allowedPatterns = new HashSet<Pattern>();

			for (String allowedClass : allowedClasses) {
				Pattern allowedPattern = Pattern.compile(allowedClass);

				_allowedPatterns.add(allowedPattern);
			}
		}
	}

	public boolean isVisible(String className) {
		if (_denyAll || _forbiddenClasses.contains(className)) {
			return false;
		}

		if (_allowAll) {
			return true;
		}

		for (Pattern allowedPattern: _allowedPatterns) {
			Matcher matcher = allowedPattern.matcher(className);

			if (matcher.find()) {
				return true;
			}
		}

		return false;
	}

	private static Set<String> _forbiddenClasses = new HashSet<String>(
		Arrays.asList(PropsValues.SCRIPTING_FORBIDDEN_CLASSES));

	private boolean _allowAll;
	private Set<Pattern> _allowedPatterns;
	private boolean _denyAll;

}