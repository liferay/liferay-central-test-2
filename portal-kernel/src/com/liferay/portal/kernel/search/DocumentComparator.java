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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <a href="DocumentComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DocumentComparator implements Comparator<Document> {

	public DocumentComparator() {
		this(true, false);
	}

	public DocumentComparator(boolean asc, boolean caseSensitive) {
		_asc = asc;
		_caseSensitive = caseSensitive;
	}

	public void addOrderBy(String name) {
		addOrderBy(name, _asc, _caseSensitive);
	}

	public void addOrderBy(String name, boolean asc, boolean caseSensitive) {
		DocumentComparatorOrderBy orderBy = new DocumentComparatorOrderBy(
			name, asc, caseSensitive);

		_columns.add(orderBy);
	}

	public int compare(Document doc1, Document doc2) {
		for (DocumentComparatorOrderBy orderBy : _columns) {
			String value1 = doc1.get(orderBy.getName());
			String value2 = doc2.get(orderBy.getName());

			if (!orderBy.isAsc()) {
				String temp = value1;

				value1 = value2;
				value2 = temp;
			}

			int result = 0;

			if ((value1 != null) && (value2 != null)) {
				if (orderBy.isCaseSensitive()) {
					result = value1.compareTo(value2);
				}
				else {
					result = value1.compareToIgnoreCase(value2);
				}
			}

			if (result != 0) {
				return result;
			}
		}

		return 0;
	}

	private boolean _asc;
	private boolean _caseSensitive;
	private List<DocumentComparatorOrderBy> _columns =
		new ArrayList<DocumentComparatorOrderBy>();

}