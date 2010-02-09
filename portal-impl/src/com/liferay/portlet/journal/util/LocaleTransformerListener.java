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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.List;

/**
 * <a href="LocaleTransformerListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class LocaleTransformerListener extends TransformerListener {

	public String onXml(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		s = replace(s);

		return s;
	}

	public String onScript(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		s = StringUtil.replace(s, "@language_id@", _requestedLocale);

		return s;
	}

	public String onOutput(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return s;
	}

	protected String replace(String xml) {
		if (xml == null) {
			return xml;
		}

		_requestedLocale = getLanguageId();

		try {
			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			String defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getDefault());

			String[] availableLocales = StringUtil.split(
				root.attributeValue("available-locales", defaultLanguageId));

			String defaultLocale = root.attributeValue(
				"default-locale", defaultLanguageId);

			boolean isSupportedLocale = false;

			for (int i = 0; i < availableLocales.length; i++) {
				if (availableLocales[i].equalsIgnoreCase(getLanguageId())) {
					isSupportedLocale = true;

					break;
				}
			}

			if (!isSupportedLocale) {
				setLanguageId(defaultLocale);
			}

			replace(root);

			xml = JournalUtil.formatXML(doc);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return xml;
	}

	protected void replace(Element root) {
		List<Element> children = root.elements();

		int listIndex = children.size() - 1;

		while (listIndex >= 0) {
			Element child = children.get(listIndex);

			String languageId = child.attributeValue(
				"language-id", getLanguageId());

			if (!languageId.equalsIgnoreCase(getLanguageId())) {
				root.remove(child);
			}
			else{
				replace(child);
			}

			listIndex--;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LocaleTransformerListener.class);

	private String _requestedLocale = StringPool.BLANK;

}