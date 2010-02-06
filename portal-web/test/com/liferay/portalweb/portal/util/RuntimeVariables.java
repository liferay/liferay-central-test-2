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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.ContextReplace;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="RuntimeVariables.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RuntimeVariables {

	public static String getValue(String key) {
		return _instance._getValue(key);
	}

	public static String replace(String text) {
		return _instance._replace(text);
	}

	public static void setValue(String key, String value) {
		_instance._setValue(key, value);
	}

	private RuntimeVariables() {
		File file = new File(StringPool.PERIOD);

		String absolutePath = file.getAbsolutePath();

		if (absolutePath.endsWith(StringPool.PERIOD)) {
			absolutePath = absolutePath.substring(0, absolutePath.length() - 1);

			_sourceDir = absolutePath;
		}
	}

	private String _getValue(String key) {
		return _runtimeVariables.get(key);
	}

	private String _replace(String text) {

		// Root directory

		text = StringUtil.replace(text, "L:\\portal\\build\\", _sourceDir);

		// Theme output directory

		text = StringUtil.replace(
			text, "\\test-output\\brochure\\",
			"\\test-output\\" + SeleniumUtil.getTimestamp() + "\\" +
				ThemeIds.getThemeId() + "\\");

		// Select theme

		text = StringUtil.replace(
			text, "//a[contains(@href, 'brochure_WAR_brochuretheme')]",
			"//a[contains(@href, '" + ThemeIds.getThemeId() + "')]");

		if (Validator.isNotNull(TestPropsValues.CLUSTER_NODE_1)) {
			text = StringUtil.replace(
				text, "[$CLUSTER_NODE_1$]", TestPropsValues.CLUSTER_NODE_1);
		}

		if (Validator.isNotNull(TestPropsValues.CLUSTER_NODE_2)) {
			text = StringUtil.replace(
				text, "[$CLUSTER_NODE_2$]", TestPropsValues.CLUSTER_NODE_2);
		}

		if (_contextReplace == null) {
			return text;
		}
		else {
			return _contextReplace.replace(text);
		}
	}

	private void _setValue(String key, String value) {
		_runtimeVariables.put(key, value);

		_contextReplace = new ContextReplace(_runtimeVariables);
	}

	private static RuntimeVariables _instance = new RuntimeVariables();

	private String _sourceDir;
	private ContextReplace _contextReplace;
	private Map<String, String> _runtimeVariables =
		new HashMap<String, String>();

}