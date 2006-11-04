/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.util.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.dialect.Dialect;

/**
 * <a href="QueryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class QueryUtil {

	public static final int ALL_POS = -1;

	public static Iterator iterate(
		Query query, Dialect dialect, int begin, int end) {

		return list(query, dialect, begin, end).iterator();
	}

	public static List list(Query query, Dialect dialect, int begin, int end) {
		if ((begin == ALL_POS) && (end == ALL_POS)) {
			return query.list();
		}
		else {
			if (dialect.supportsLimit()) {
				query.setMaxResults(end - begin);
				query.setFirstResult(begin);

				return query.list();
			}
			else {
				List list = new ArrayList();

				ScrollableResults sr = query.scroll();

				if (sr.first() && sr.scroll(begin)) {
					for (int i = begin; i < end; i++) {
						Object obj  = sr.get(0);

						list.add(obj);

						if (!sr.next()) {
							break;
						}
					}
				}

				return list;
			}
		}
	}

	public static Comparable[] getPrevAndNext(
		Query query, int count, OrderByComparator obc, Comparable comparable) {

		Comparable[] array = new Comparable[3];

		ScrollableResults sr = query.scroll();

		if (sr.first()) {
			while (true) {
				Object obj = sr.get(0);

				if (obj == null) {
					break;
				}

				Comparable curComparable = (Comparable)obj;

				int value = obc.compare(comparable, curComparable);

				if (value == 0) {
					if (!comparable.equals(curComparable)) {
						break;
					}

					array[1] = curComparable;

					if (sr.previous()) {
						array[0] = (Comparable)sr.get(0);
					}

					sr.next();

					if (sr.next()) {
						array[2] = (Comparable)sr.get(0);
					}

					break;
				}

				if (count == 1) {
					break;
				}

				count = (int)Math.ceil(count / 2.0);

				if (value < 0) {
					if (!sr.scroll(count * -1)) {
						break;
					}
				}
				else {
					if (!sr.scroll(count)) {
						break;
					}
				}
			}
		}

		return array;
	}

}