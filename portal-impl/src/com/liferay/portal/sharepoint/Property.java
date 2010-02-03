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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="Property.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class Property implements ResponseElement {

	public static final String OPEN_PARAGRAPH = "<p>";

	public Property(String key, String value) {
		this(key, value, true);
	}

	public Property(String key, ResponseElement value) {
		this(key, StringPool.NEW_LINE + value.parse(), false);
	}

	public Property(String key, String value, boolean newLine) {
		_key = key;
		_value = value;
		_newLine = newLine;
	}

	public String parse() {
		StringBundler sb = new StringBundler(5);

		sb.append(OPEN_PARAGRAPH);
		sb.append(_key);
		sb.append(StringPool.EQUAL);
		sb.append(_value);

		if (_newLine) {
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _key;
	private String _value;
	private boolean _newLine;

}