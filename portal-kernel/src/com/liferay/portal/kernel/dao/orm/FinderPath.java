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

package com.liferay.portal.kernel.dao.orm;

/**
 * <a href="FinderPath.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class FinderPath {

	public FinderPath(
		boolean entityCacheEnabled, boolean finderCacheEnabled,
		String className, String methodName, String[] params) {

		_entityCacheEnabled = entityCacheEnabled;
		_finderCacheEnabled = finderCacheEnabled;
		_className = className;
		_methodName = methodName;
		_params = params;
	}

	public String getClassName() {
		return _className;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String[] getParams() {
		return _params;
	}

	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	private String _className;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
	private String _methodName;
	private String[] _params;

}