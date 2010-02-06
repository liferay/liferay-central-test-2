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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="CustomSQLParam.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CustomSQLParam {

	public CustomSQLParam(String sql, Object value) {
		_sql = sql;
		_value = value;
	}

	public String getSQL() {
		return _sql;
	}

	public void process(QueryPos qPos) {
		if (_value instanceof Long) {
			Long valueLong = (Long)_value;

			if (Validator.isNotNull(valueLong)) {
				qPos.add(valueLong);
			}
		}
		else if (_value instanceof Long[]) {
			Long[] valueArray = (Long[])_value;

			for (int i = 0; i < valueArray.length; i++) {
				if (Validator.isNotNull(valueArray[i])) {
					qPos.add(valueArray[i]);
				}
			}
		}
		else if (_value instanceof String) {
			String valueString = (String)_value;

			if (Validator.isNotNull(valueString)) {
				qPos.add(valueString);
			}
		}
		else if (_value instanceof String[]) {
			String[] valueArray = (String[])_value;

			for (int i = 0; i < valueArray.length; i++) {
				if (Validator.isNotNull(valueArray[i])) {
					qPos.add(valueArray[i]);
				}
			}
		}
	}

	private String _sql;
	private Object _value;

}