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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="DocumentImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class DocumentImpl implements Document {

	public void add(Field field) {
		_fields.put(field.getName(), field);
	}

	public void addDate(String name, Date value) {
		if (value == null) {
			return;
		}

		addKeyword(name, _dateFormat.format(value));
	}

	public void addFile(String name, byte[] bytes, String fileExt) {
		InputStream is = new UnsyncByteArrayInputStream(bytes);

		addFile(name, is, fileExt);
	}

	public void addFile(String name, File file, String fileExt)
		throws IOException {

		InputStream is = new FileInputStream(file);

		addFile(name, is, fileExt);
	}

	public void addFile(String name, InputStream is, String fileExt) {
		addText(name, FileUtil.extractText(is, fileExt));
	}

	public void addKeyword(String name, boolean value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, Boolean value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, boolean[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, Boolean[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, double value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, Double value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, double[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, Double[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, int value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, int[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, Integer value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, Integer[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, long value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, Long value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, long[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, Long[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, short value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, Short value) {
		addKeyword(name, String.valueOf(value));
	}

	public void addKeyword(String name, short[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, Short[] values) {
		if (values == null) {
			return;
		}

		addKeyword(name, ArrayUtil.toStringArray(values));
	}

	public void addKeyword(String name, String value) {
		addKeyword(name, value, false);
	}

	public void addKeyword(String name, String value, boolean lowerCase) {
		if (lowerCase && Validator.isNotNull(value)) {
			value = value.toLowerCase();
		}

		_fields.put(name, new Field(name, value, false));
	}

	public void addKeyword(String name, String[] values) {
		if (values == null) {
			return;
		}

		_fields.put(name, new Field(name, values, false));
	}

	public void addModifiedDate() {
		addModifiedDate(new Date());
	}

	public void addModifiedDate(Date modifiedDate) {
		addDate(Field.MODIFIED, modifiedDate);
	}

	public void addText(String name, String value) {
		if (Validator.isNotNull(value)) {
			_fields.put(name, new Field(name, value, true));
		}
	}

	public void addUID(String portletId, long field1) {
		addUID(portletId, String.valueOf(field1));
	}

	public void addUID(
		String portletId, long field1, String field2) {

		addUID(portletId, String.valueOf(field1), field2);
	}

	public void addUID(String portletId, Long field1) {
		addUID(portletId, field1.longValue());
	}

	public void addUID(
		String portletId, Long field1, String field2) {

		addUID(portletId, field1.longValue(), field2);
	}

	public void addUID(String portletId, String field1) {
		addUID(portletId, field1, null);
	}

	public void addUID(
		String portletId, String field1, String field2) {

		addUID(portletId, field1, field2, null);
	}

	public void addUID(
		String portletId, String field1, String field2, String field3) {

		addUID(portletId, field1, field2, field3, null);
	}

	public void addUID(
		String portletId, String field1, String field2, String field3,
		String field4) {

		String uid = portletId + _UID_PORTLET + field1;

		if (field2 != null) {
			uid += _UID_FIELD + field2;
		}

		if (field3 != null) {
			uid += _UID_FIELD + field3;
		}

		if (field4 != null) {
			uid += _UID_FIELD + field4;
		}

		addKeyword(Field.UID, uid);
	}

	public String get(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return StringPool.BLANK;
		}

		return field.getValue();
	}

	public Date getDate(String name) throws ParseException {
		return _dateFormat.parse(get(name));
	}

	public Map<String, Field> getFields() {
		return _fields;
	}

	public String[] getValues(String name) {
		Field field = _fields.get(name);

		if (field == null) {
			return new String[] {StringPool.BLANK};
		}

		return field.getValues();
	}

	public void setFields(Map<String, Field> fields) {
		_fields = fields;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.OPEN_CURLY_BRACE);

		int i = 0;

		for (Field field : _fields.values()) {
			if (i > 0) {
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}

			sb.append(field.getName());
			sb.append(StringPool.EQUAL);
			sb.append(field.getValues());
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final String _DATE_FORMAT_PATTERN = "yyyyMMddHHmmss";

	private static final String _UID_FIELD = "_FIELD_";

	private static final String _UID_PORTLET = "_PORTLET_";

	private DateFormat _dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
		_DATE_FORMAT_PATTERN);
	private Map<String, Field> _fields = new HashMap<String, Field>();

}