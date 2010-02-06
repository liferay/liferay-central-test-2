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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="TokensTransformerListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TokensTransformerListener extends TransformerListener {

	public static final String TEMP_ESCAPED_AT_OPEN =
		"[$TEMP_ESCAPED_AT_OPEN$]";

	public static final String TEMP_ESCAPED_AT_CLOSE =
		"[$_TEMP_ESCAPED_AT_CLOSE$]";

	public String onXml(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		return s;
	}

	public String onScript(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return replace(s);
	}

	public String onOutput(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return replace(s);
	}

	/**
	 * Replace the standard tokens in a given string with their values.
	 *
	 * @return the processed string
	 */
	protected String replace(String s) {
		Map<String, String> tokens = getTokens();

		Set<Map.Entry<String, String>> tokensSet = tokens.entrySet();

		if (tokensSet.size() == 0) {
			return s;
		}

		List<String> escapedKeysList = new ArrayList<String>();
		List<String> escapedValuesList = new ArrayList<String>();

		List<String> keysList = new ArrayList<String>();
		List<String> valuesList = new ArrayList<String>();

		List<String> tempEscapedKeysList = new ArrayList<String>();
		List<String> tempEscapedValuesList = new ArrayList<String>();

		for (Map.Entry<String, String> entry : tokensSet) {
			String key = entry.getKey();
			String value = GetterUtil.getString(entry.getValue());

			if (Validator.isNotNull(key)) {
				String escapedKey =
					StringPool.AT + StringPool.AT + key + StringPool.AT +
						StringPool.AT;

				String actualKey = StringPool.AT + key + StringPool.AT;

				String tempEscapedKey =
					TEMP_ESCAPED_AT_OPEN + key + TEMP_ESCAPED_AT_CLOSE;

				escapedKeysList.add(escapedKey);
				escapedValuesList.add(tempEscapedKey);

				keysList.add(actualKey);
				valuesList.add(value);

				tempEscapedKeysList.add(tempEscapedKey);
				tempEscapedValuesList.add(actualKey);
			}
		}

		s = StringUtil.replace(
			s,
			escapedKeysList.toArray(new String[escapedKeysList.size()]),
			escapedValuesList.toArray(new String[escapedValuesList.size()]));

		s = StringUtil.replace(
			s,
			keysList.toArray(new String[keysList.size()]),
			valuesList.toArray(new String[valuesList.size()]));

		s = StringUtil.replace(
			s,
			tempEscapedKeysList.toArray(new String[tempEscapedKeysList.size()]),
			tempEscapedValuesList.toArray(
				new String[tempEscapedValuesList.size()]));

		return s;
	}

	private static Log _log =
		LogFactoryUtil.getLog(TokensTransformerListener.class);

}