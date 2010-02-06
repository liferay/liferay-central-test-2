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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="PropertiesTransformerListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropertiesTransformerListener extends TransformerListener {

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

		s = replace(s);

		return s;
	}

	public String onOutput(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		s = replace(s);

		return s;
	}

	/**
	 * Replace the properties in a given string with their values fetched from
	 * the template GLOBAL-PROPERTIES.
	 *
	 * @return the processed string
	 */
	protected String replace(String s) {
		Map<String, String> tokens = getTokens();

		String templateId = tokens.get("template_id");

		if ((templateId == null) ||
			((templateId != null) && (templateId.equals(_GLOBAL_PROPERTIES)))) {

			// Return the original string if no template ID is specified or if
			// the template ID is GLOBAL-PROPERTIES to prevent an infinite loop.

			return s;
		}

		Properties props = new Properties();

		try {
			Map<String, String> newTokens = new HashMap<String, String>();

			MapUtil.copy(tokens, newTokens);

			newTokens.put("template_id", _GLOBAL_PROPERTIES);

			long groupId = GetterUtil.getLong(tokens.get("group_id"));

			String script = JournalUtil.getTemplateScript(
				groupId, _GLOBAL_PROPERTIES, newTokens, getLanguageId());

			PropertiesUtil.load(props, script);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		if (props.size() == 0) {
			return s;
		}

		String[] escapedKeys = new String[props.size()];
		String[] escapedValues = new String[props.size()];

		String[] keys = new String[props.size()];
		String[] values = new String[props.size()];

		String[] tempEscapedKeys = new String[props.size()];
		String[] tempEscapedValues = new String[props.size()];

		int counter = 0;

		Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			String escapedKey =
				StringPool.AT + StringPool.AT + key + StringPool.AT +
					StringPool.AT;

			String actualKey = StringPool.AT + key + StringPool.AT;

			String tempEscapedKey =
				TokensTransformerListener.TEMP_ESCAPED_AT_OPEN +
					key + TokensTransformerListener.TEMP_ESCAPED_AT_CLOSE;

			escapedKeys[counter] = escapedKey;
			escapedValues[counter] = tempEscapedKey;

			keys[counter] = actualKey;
			values[counter] = value;

			tempEscapedKeys[counter] = tempEscapedKey;
			tempEscapedValues[counter] = actualKey;

			counter++;
		}

		s = StringUtil.replace(s, escapedKeys, escapedValues);

		s = StringUtil.replace(s, keys, values);

		s = StringUtil.replace(s, tempEscapedKeys, tempEscapedValues);

		return s;
	}

	private static final String _GLOBAL_PROPERTIES = "GLOBAL-PROPERTIES";

	private static Log _log =
		LogFactoryUtil.getLog(PropertiesTransformerListener.class);

}