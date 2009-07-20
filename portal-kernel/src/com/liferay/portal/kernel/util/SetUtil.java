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

package com.liferay.portal.kernel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetUtil {

	public static Set<Long> fromArray(long[] array) {
		if ((array == null) || (array.length == 0)) {
			return new HashSet();
		}

		Set<Long> set = new HashSet<Long>(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set fromArray(Object[] array) {
		if ((array == null) || (array.length == 0)) {
			return new HashSet();
		}

		Set set = new HashSet(array.length);

		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}

		return set;
	}

	public static Set fromCollection(Collection c) {
		if ((c != null) && (c instanceof Set)) {
			return (Set)c;
		}

		if ((c == null) || (c.size() == 0)) {
			return new HashSet();
		}

		Set set = new HashSet(c.size());

		Iterator itr = c.iterator();

		while (itr.hasNext()) {
			set.add(itr.next());
		}

		return set;
	}

	public static Set fromEnumeration(Enumeration enu) {
		Set set = new HashSet();

		while (enu.hasMoreElements()) {
			set.add(enu.nextElement());
		}

		return set;
	}

	public static Set fromIterator(Iterator itr) {
		Set set = new HashSet();

		while (itr.hasNext()) {
			set.add(itr.next());
		}

		return set;
	}

	public static Set fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static Set fromFile(File file) throws IOException {
		Set set = new HashSet();

		BufferedReader br = new BufferedReader(new FileReader(file));

		String s = StringPool.BLANK;

		while ((s = br.readLine()) != null) {
			set.add(s);
		}

		br.close();

		return set;
	}

	public static Set fromList(List array) {
		if ((array == null) || (array.size() == 0)) {
			return new HashSet();
		}

		Set set = new HashSet(array.size());

		for (int i = 0; i < array.size(); i++) {
			set.add(array.get(i));
		}

		return set;
	}

	public static Set fromString(String s) {
		return fromArray(StringUtil.split(s, StringPool.NEW_LINE));
	}

}