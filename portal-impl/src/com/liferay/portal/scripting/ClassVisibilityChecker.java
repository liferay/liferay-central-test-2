/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="ClassVisibilityChecker.java.html"><b><i>View Source</i></b></a>
*
* @author Alberto Montero
*/
public class ClassVisibilityChecker {

	public static final String ALL_CLASSES = "all_classes";

	public ClassVisibilityChecker(
		Set<String> visibleGroups, Set<String> visibleIndividualItems) {

		_allowAll = false;

		if (Validator.isNull(visibleGroups) &&
			visibleGroups.contains(ALL_CLASSES)) {

			_allowAll = true;
		}

		_denyAll = _prohibitedClasses.contains(ALL_CLASSES) ? true : false;

		if (!_allowAll && !_denyAll) {
			_visibleGroups = new HashSet<Pattern>();
			for (String groupSpecifier: visibleGroups) {
				_visibleGroups.add(Pattern.compile(groupSpecifier));
			}

			if (Validator.isNotNull(visibleIndividualItems)) {
				_visibleIndividualitems = visibleIndividualItems;
			}
			else {
				_visibleIndividualitems = new HashSet<String>();
			}
		}
	}

	public boolean isVisible(String fullClassName) {
		if (_denyAll || _prohibitedClasses.contains(fullClassName)) {
			return false;
		}
		else {
			if (_allowAll || _visibleIndividualitems.contains(fullClassName)) {
				return true;
			}
			else {
				for (Pattern groupSpecifierPattern: _visibleGroups) {
					Matcher matcher = groupSpecifierPattern.matcher(
						fullClassName);
					if (matcher.find()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static boolean _allowAll;
	private static boolean _denyAll;
	private static final Set<String> _prohibitedClasses = new HashSet<String>(
		Arrays.asList(
			PropsValues.USER_DEFINED_SERVER_SIDE_SCRIPT_PROHIBITED_CLASSES));
	private Set<Pattern> _visibleGroups;
	private Set<String> _visibleIndividualitems;

}