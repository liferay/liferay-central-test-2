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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.kernel.util.UnmodifiableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="QueryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class QueryUtil {

	public static final int ALL_POS = -1;

	public static Iterator<?> iterate(
		Query query, Dialect dialect, int start, int end) {

		return iterate(query, dialect, start, end, true);
	}

	public static Iterator<?> iterate(
		Query query, Dialect dialect, int start, int end,
		boolean unmodifiable) {

		return list(query, dialect, start, end).iterator();
	}

	public static List<?> list(
		Query query, Dialect dialect, int start, int end) {

		return list(query, dialect, start, end, true);
	}

	public static List<?> list(
		Query query, Dialect dialect, int start, int end,
		boolean unmodifiable) {

		if ((start == ALL_POS) && (end == ALL_POS)) {
			return query.list(unmodifiable);
		}
		else {
			if (dialect.supportsLimit()) {
				query.setMaxResults(end - start);
				query.setFirstResult(start);

				return query.list(unmodifiable);
			}
			else {
				List<Object> list = new ArrayList<Object>();

				ScrollableResults sr = query.scroll();

				if (sr.first() && sr.scroll(start)) {
					for (int i = start; i < end; i++) {
						Object obj = sr.get(0);

						list.add(obj);

						if (!sr.next()) {
							break;
						}
					}
				}

				if (unmodifiable) {
					return new UnmodifiableList(list);
				}
				else {
					return list;
				}
			}
		}
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num) {

		return randomList(query, dialect, total, num, true);
	}

	public static List<?> randomList(
		Query query, Dialect dialect, int total, int num,
		boolean unmodifiable) {

		if ((total == 0) || (num == 0)) {
			return new ArrayList<Object>();
		}

		if (num >= total) {
			return list(query, dialect, ALL_POS, ALL_POS, true);
		}

		int[] scrollIds = Randomizer.getInstance().nextInt(total, num);

		List<Object> list = new ArrayList<Object>();

		ScrollableResults sr = query.scroll();

		for (int i = 0; i < scrollIds.length; i++) {
			if (sr.scroll(scrollIds[i])) {
				Object obj = sr.get(0);

				list.add(obj);

				sr.first();
			}
		}

		if (unmodifiable) {
			return new UnmodifiableList(list);
		}
		else {
			return list;
		}
	}

	public static Comparable<?>[] getPrevAndNext(
		Query query, int count, OrderByComparator obc,
		Comparable<?> comparable) {

		Comparable<?>[] array = new Comparable[3];

		List<?> entries = query.list();

		List<?> sortedEntries = new ArrayList(entries);

		Collections.sort(sortedEntries, obc);

		int index = Collections.binarySearch(sortedEntries, comparable, obc);

		array[1] = (Comparable<?>)sortedEntries.get(index);

		if (index != 0) {
			array[0] = (Comparable<?>) sortedEntries.get(index-1);
		}

		if (index != (count-1)) {
			array[2] = (Comparable<?>) sortedEntries.get(index + 1);
		}

		return array;
	}

	private static Log _log = LogFactoryUtil.getLog(QueryUtil.class);

}