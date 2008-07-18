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

package com.liferay.portal.mirage.util;

import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <a href="SmartCriteria.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SmartCriteria {

	public SmartCriteria() {
		_searchCriteria = new SearchCriteria();
	}

	public void add(String name, boolean value) {
		add(name, String.valueOf(value));
	}

	public void add(String name, double value) {
		add(name, String.valueOf(value));
	}

	public void add(String name, int value) {
		add(name, String.valueOf(value));
	}

	public void add(String name, long value) {
		add(name, String.valueOf(value));
	}

	public void add(String name, short value) {
		add(name, String.valueOf(value));
	}

	public void add(String name, String value) {
		SearchFieldValue searchField = new SearchFieldValue();

		searchField.setFieldName(name);
		searchField.setFieldValues(new String[] {value});

		_searchFields.add(searchField);
	}

	public SearchCriteria getCriteria() {
		_searchCriteria.setSearchFieldValues(_searchFields);

		return _searchCriteria;
	}

	public void setAndOperator(boolean andOperator) {
		_searchCriteria.setMatchAnyOneField(andOperator);
	}

	public void setOrderByComparator(Comparator<?> comparator) {
		_searchCriteria.setOrderByComparator(comparator);
	}

	private SearchCriteria _searchCriteria;
	private List<SearchFieldValue> _searchFields =
		new ArrayList<SearchFieldValue>();

}