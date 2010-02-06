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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;

import java.util.List;

/**
 * <a href="ScrollableResultsImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class ScrollableResultsImpl implements ScrollableResults {

	public ScrollableResultsImpl(List<?> results) {
		_results = results;
		_last = _results.size();
	}

	public boolean first() throws ORMException {
		if (_results.isEmpty()) {
			return false;
		}

		_current = 1;

		return true;
	}

	public Object[] get() throws ORMException {
		Object[] result = null;

		Object object = _results.get(_current - 1);

		if (object instanceof Object[]) {
			result = (Object[])object;
		}
		else {
			result = new Object[] {object};
		}

		return result;
	}

	public Object get(int i) throws ORMException {
		Object result = null;

		Object object = _results.get(_current - 1);

		if (object instanceof Object[]) {
			result = ((Object[])object)[i];
		}
		else {
			result = object;
		}

		return result;
	}

	public boolean last() throws ORMException {
		if (_results.isEmpty()) {
			return false;
		}

		_current = _last;

		return true;
	}

	public boolean next() throws ORMException {
		if (_current == _last) {
			return false;
		}

		_current++;

		return true;
	}

	public boolean previous() throws ORMException {
		if (_current == 1) {
			return false;
		}

		_current--;

		return true;
	}

	public boolean scroll(int i) throws ORMException {
		if (_current + i < 1 || _current + i > _last ) {
			return false;
		}

		_current += i;

		return true;
	}

	private int _current = 0;
	private int _last = 0;
	private List<?> _results;

}