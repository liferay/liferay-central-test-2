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

package com.liferay.util;

import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * <a href="Autocomplete.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Autocomplete {

	public static String arrayToXml(String[][] array) {
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\"?>");

		sb.append("<ajaxresponse>");

		for (int i = 0; i < array.length; i++) {
			String key = array[i][0];
			String value = array[i][1];

			sb.append("<item>");
			sb.append("<text><![CDATA[");
			sb.append(key);
			sb.append("]]></text>");
			sb.append("<value><![CDATA[");
			sb.append(value);
			sb.append("]]></value>");
			sb.append("</item>");
		}

		sb.append("</ajaxresponse>");

		return sb.toString();
	}

	public static String[][] listToArray(
		List list, String keyParam, String valueParam) {

		String[][] array = new String[list.size()][2];

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);

			Object key = BeanUtil.getObject(bean, keyParam);

			if (key != null) {
				key = StringPool.BLANK;
			}

			Object value = BeanUtil.getObject(bean, valueParam);

			if (value != null) {
				value = StringPool.BLANK;
			}

			array[i][0] = key.toString();
			array[i][1] = value.toString();
		}

		return array;
	}

	public static String listToXml(
		List list, String keyParam, String valueParam) {

		return arrayToXml(listToArray(list, keyParam, valueParam));
	}

}