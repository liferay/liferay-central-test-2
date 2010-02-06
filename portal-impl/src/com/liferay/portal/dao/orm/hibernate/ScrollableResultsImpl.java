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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.ScrollableResults;

/**
 * <a href="ScrollableResultsImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ScrollableResultsImpl implements ScrollableResults {

	public ScrollableResultsImpl(
		org.hibernate.ScrollableResults scrollableResults) {

		_scrollableResults = scrollableResults;
	}

	public boolean first() throws ORMException {
		try {
			return _scrollableResults.first();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object[] get() throws ORMException {
		try {
			return _scrollableResults.get();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public Object get(int i) throws ORMException {
		try {
			return _scrollableResults.get(i);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public boolean last() throws ORMException {
		try {
			return _scrollableResults.last();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public boolean next() throws ORMException {
		try {
			return _scrollableResults.next();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public boolean previous() throws ORMException {
		try {
			return _scrollableResults.previous();
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}
	}

	public boolean scroll(int i) throws ORMException {
		try {
			return _scrollableResults.scroll(i);
		}
		catch (Exception e) {
			throw ExceptionTranslator.translate(e);
		}

	}

	private org.hibernate.ScrollableResults _scrollableResults;

}