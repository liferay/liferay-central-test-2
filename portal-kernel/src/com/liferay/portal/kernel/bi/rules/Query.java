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

package com.liferay.portal.kernel.bi.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="RuleEngineQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class Query {

	public void addArgument(Object object) {
		_arguments.add(object);
	}

	public void addArguments(List<?> arguments) {
		_arguments.addAll(arguments);
	}

	public void addArguments(Object[] arguments) {
		if ((arguments != null) && (arguments.length > 0)) {
			_arguments.addAll(Arrays.asList(arguments));
		}
	}

	public Object[] getArguments() {
		return _arguments.toArray(new Object[_arguments.size()]);
	}

	public String getQueryString() {
		return _queryString;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;
	}

	private List<Object> _arguments = new ArrayList<Object>();
	private String _queryString;

}