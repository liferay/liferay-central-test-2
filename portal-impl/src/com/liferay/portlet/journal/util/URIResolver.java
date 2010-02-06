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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;

import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * <a href="URIResolver.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class URIResolver implements javax.xml.transform.URIResolver {

	public URIResolver(Map<String, String> tokens, String languageId) {
		_tokens = tokens;
		_languageId = languageId;
	}

	public Source resolve(String href, String base) {
		try {
			String content = null;

			int templatePathIndex = href.indexOf(_GET_TEMPLATE_PATH);

			if (templatePathIndex >= 0) {
				int templateIdIndex =
					templatePathIndex + _GET_TEMPLATE_PATH.length();

				long groupId = GetterUtil.getLong(_tokens.get("group_id"));
				String templateId =
					href.substring(templateIdIndex, href.length());

				content = JournalUtil.getTemplateScript(
					groupId, templateId, _tokens, _languageId);
			}
			else {
				content = HttpUtil.URLtoString(href);
			}

			return new StreamSource(new UnsyncStringReader(content));
		}
		catch (Exception e) {
			_log.error(href + " does not reference a valid template");

			return null;
		}
	}

	private static final String _GET_TEMPLATE_PATH =
		"/c/journal/get_template?template_id=";

	private static Log _log = LogFactoryUtil.getLog(URIResolver.class);

	private Map<String, String> _tokens;
	private String _languageId;

}