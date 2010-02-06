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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * <a href="Sort.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class Sort implements Serializable {

	public static final int AUTO_TYPE = 2;

	public static final int CUSTOM_TYPE = 9;

	public static final int DOC_TYPE = 1;

	public static final int DOUBLE_TYPE = 7;

	public static final int FLOAT_TYPE = 5;

	public static final int INT_TYPE = 4;

	public static final int LONG_TYPE = 6;

	public static final int SCORE_TYPE = 0;

	public static final int STRING_TYPE = 3;

	public Sort() {
	}

	public Sort(String fieldName, boolean reverse) {
		this(fieldName, AUTO_TYPE, reverse);
	}

	public Sort(String fieldName, int type, boolean reverse) {
		_fieldName = fieldName;
		_type = type;
		_reverse = reverse;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public int getType() {
		return _type;
	}

	public boolean isReverse() {
		return _reverse;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setReverse(boolean reverse) {
		_reverse = reverse;
	}

	public void setType(int type) {
		_type = type;
	}

	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{fieldName=");
		sb.append(_fieldName);
		sb.append(", type=");
		sb.append(_type);
		sb.append(", reverse=");
		sb.append(_reverse);
		sb.append("}");

		return sb.toString();
	}

	private String _fieldName;
	private boolean _reverse;
	private int _type;

}