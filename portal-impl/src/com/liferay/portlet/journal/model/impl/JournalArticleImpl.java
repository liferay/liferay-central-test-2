/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.LocaleTransformerListener;

import java.io.StringReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalArticleImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleImpl
	extends JournalArticleModelImpl implements JournalArticle {

	public static final double DEFAULT_VERSION = 1.0;

	public static final String[] TYPES =
		PropsUtil.getArray(PropsUtil.JOURNAL_ARTICLE_TYPES);

	public static final String PORTLET = "portlet";

	public static final String STAND_ALONE = "stand-alone";

	public JournalArticleImpl() {
	}

	public String[] getAvailableLocales() {
		String[] availableLocales = new String[0];

		if (isTemplateDriven()) {
			try {
				SAXReader reader = new SAXReader();

				Document doc = reader.read(new StringReader(getContent()));

				Element root = doc.getRootElement();

				availableLocales = StringUtil.split(
					root.attributeValue("available-locales"));
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return availableLocales;
	}

	public String getContentByLocale(String languageId){
		LocaleTransformerListener listener = new LocaleTransformerListener();

		listener.setLanguageId(languageId);

		return listener.onXml(getContent());
	}

	public String getDefaultLocale() {
		String xml = getContent();

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		if ((Validator.isNull(xml)) ||
			(xml.indexOf("<dynamic-element name=\"") == -1)) {

			return defaultLanguageId;
		}

		try {
			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			return root.attributeValue("default-locale", defaultLanguageId);

		}
		catch (Exception e) {
			_log.error(e);
		}

		return defaultLanguageId;
	}

	public boolean isTemplateDriven() {
		if (Validator.isNull(getStructureId())) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactory.getLog(JournalArticleImpl.class);

}