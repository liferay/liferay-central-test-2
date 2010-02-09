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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Converter;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Iterator;
import java.util.Map;

/**
 * <a href="HibernateConfigurationConverter.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-5363.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class HibernateConfigurationConverter implements Converter<String> {

	public String convert(String input) {
		String output = input;

		try {
			output = doConvert(input);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return output;
	}

	public void setClassNames(Map<String, String> classNames) {
		_classNames = classNames;
	}

	protected String doConvert(String input) throws Exception {
		if ((_classNames == null) || _classNames.isEmpty()) {
			return input;
		}

		Document document = SAXReaderUtil.read(input);

		Element rootElement = document.getRootElement();

		Iterator<Element> itr = rootElement.elementIterator("class");

		while (itr.hasNext()) {
			Element classElement = itr.next();

			String oldName = classElement.attributeValue("name");

			String newName = _classNames.get(oldName);

			if (newName != null) {
				classElement.addAttribute("name", newName);
			}
		}

		return document.asXML();
	}

	private static Log _log = LogFactoryUtil.getLog(
		HibernateConfigurationConverter.class);

	private Map<String, String> _classNames;

}