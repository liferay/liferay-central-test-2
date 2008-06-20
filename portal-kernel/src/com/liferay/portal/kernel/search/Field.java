/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="Field.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Allen Chiang
 *
 */
public class Field {

	public static final String COMPANY_ID = "companyId";

	public static final String COMMENTS = "comments";

	public static final String CONTENT = "content";

	public static final String DESCRIPTION = "description";

	public static final String ENTRY_CLASS_NAME = "entryClassName";

	public static final String ENTRY_CLASS_PK = "entryClassPK";

	public static final String GROUP_ID = "groupId";

	public static final String MODIFIED = "modified";

	public static final String NAME = "name";

	public static final String PORTLET_ID = "portletId";

	public static final String PROPERTIES = "properties";

	public static final String TAGS_ENTRIES = "tagsEntries";

	public static final String TITLE = "title";

	public static final String TYPE = "type";

	public static final String UID = "uid";

	public static final String URL = "url";

	public static final String USER_ID = "userId";

	public static final String USER_NAME = "userName";

	public Field() {
	}

	public Field(String name, String value, boolean tokenized) {
		this(name, new String[] {value}, tokenized);
	}

	public Field(String name, String[] values, boolean tokenized) {
		_name = name;
		_values = values;
		_tokenized = tokenized;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public boolean isTokenized() {
		return _tokenized;
	}

	public void setTokenized(boolean type) {
		_tokenized = type;
	}

	public String getValue() {
		if ((_values != null) && (_values.length > 0)) {
			return _values[0];
		}
		else {
			return null;
		}
	}

	public void setValue(String value) {
		setValues(new String[] {value});
	}

	public String[] getValues() {
		return _values;
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private String _name;
	private boolean _tokenized;
	private String[] _values;

}