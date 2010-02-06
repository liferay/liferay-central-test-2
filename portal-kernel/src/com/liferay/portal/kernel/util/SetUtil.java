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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="SetUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SetUtil {

	public static <E> Set<E> fromArray(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new HashSet<E>();
		}

		Set<E> set = new HashSet<E>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set<Long> fromArray(long[] array) {
		if ((array == null) || (array.length == 0)) {
			return new HashSet<Long>();
		}

		Set<Long> set = new HashSet<Long>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	@SuppressWarnings("unchecked")
	public static <E> Set<E> fromCollection(Collection<E> c) {
		if ((c != null) && (Set.class.isAssignableFrom(c.getClass()))) {
			return (Set)c;
		}

		if ((c == null) || (c.size() == 0)) {
			return new HashSet<E>();
		}

		return new HashSet<E>(c);
	}

	public static <E> Set<E> fromEnumeration(Enumeration<E> enu) {
		Set<E> set = new HashSet<E>();

		while (enu.hasMoreElements()) {
			set.add(enu.nextElement());
		}

		return set;
	}

	public static Set<String> fromFile(File file) throws IOException {
		Set<String> set = new HashSet<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(file));

		String s = StringPool.BLANK;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			set.add(s);
		}

		unsyncBufferedReader.close();

		return set;
	}

	public static Set<String> fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static <E> Set<E> fromIterator(Iterator<E> itr) {
		Set<E> set = new HashSet<E>();

		while (itr.hasNext()) {
			set.add(itr.next());
		}

		return set;
	}

	public static <E> Set<E> fromList(List<E> array) {
		if ((array == null) || (array.size() == 0)) {
			return new HashSet<E>();
		}

		return new HashSet<E>(array);
	}

	public static Set<String> fromString(String s) {
		return fromArray(StringUtil.split(s, StringPool.NEW_LINE));
	}

}