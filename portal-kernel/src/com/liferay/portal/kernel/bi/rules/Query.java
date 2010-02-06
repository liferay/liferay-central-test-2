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

package com.liferay.portal.kernel.bi.rules;

import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="Query.java.html"><b><i>View Source</i></b></a>
 *
 * <a href="RuleEngineQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Query implements Serializable {

	public static Query createCustomQuery(
		String identifier, String queryName) {

		if (Validator.isNull(identifier)) {
			throw new IllegalArgumentException("Query idenfier is null.");
		}

		if (Validator.isNull(queryName)) {
			throw new IllegalArgumentException("Query string is null.");
		}

		return new Query(identifier, QueryType.CUSTOM, queryName);
	}

	public static Query createStandardQuery() {
		return new Query(null, QueryType.STANDARD, null);
	}

	public void addArgument(Object object) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		_arguments.add(object);
	}

	public void addArguments(List<?> arguments) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		_arguments.addAll(arguments);
	}

	public void addArguments(Object[] arguments) {
		if (_queryType.equals(QueryType.STANDARD)) {
			throw new IllegalStateException(
				"Standard queries cannot accept query arguments");
		}

		if ((arguments != null) && (arguments.length > 0)) {
			_arguments.addAll(Arrays.asList(arguments));
		}
	}

	public Object[] getArguments() {
		return _arguments.toArray(new Object[_arguments.size()]);
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getQueryName() {
		return _queryName;
	}

	public QueryType getQueryType() {
		return _queryType;
	}

	private Query(String identifier, QueryType queryType, String queryName) {
		_identifier = identifier;
		_queryType = queryType;
		_queryName = queryName;
	}

	private List<Object> _arguments = new ArrayList<Object>();
	private String _identifier;
	private String _queryName;
	private QueryType _queryType;

}