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
 * <a href="Leaf.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class Leaf implements ResponseElement {

	public static final String OPEN_LI = "<li>";

	public Leaf(String key, ResponseElement value) {
		this(key, StringPool.NEW_LINE + value.parse(), true, false);
	}

	public Leaf(String key, String value, boolean useEqualSymbol) {
		this(key, value, useEqualSymbol, true);
	}

	public Leaf(
		String key, String value, boolean useEqualSymbol, boolean newLine) {

		_key = key;
		_value = value;
		_useEqualSymbol = useEqualSymbol;
		_newLine = newLine;
	}

	public String parse() {
		StringBundler sb = new StringBundler(7);

		if (_useEqualSymbol) {
			sb.append(OPEN_LI);

			sb.append(_key);
			sb.append(StringPool.EQUAL);
			sb.append(_value);
		}
		else {
			sb.append(OPEN_LI);
			sb.append(_key);

			sb.append(StringPool.NEW_LINE);

			sb.append(OPEN_LI);
			sb.append(_value);
		}

		if (_newLine) {
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _key;
	private String _value;
	private boolean _useEqualSymbol;
	private boolean _newLine;

}