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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="ListUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ListUtil {

	public static <E> List<E> copy(List<E> master) {
		if (master == null) {
			return null;
		}

		return new ArrayList<E>(master);
	}

	public static <E> void copy(List<E> master, List<? super E> copy) {
		if ((master == null) || (copy == null)) {
			return;
		}

		copy.clear();

		copy.addAll(master);
	}

	public static void distinct(List<?> list) {
		distinct(list, null);
	}

	public static <E> void distinct(List<E> list, Comparator<E> comparator) {
		if ((list == null) || (list.size() == 0)) {
			return;
		}

		Set<E> set = null;

		if (comparator == null) {
			set = new TreeSet<E>();
		}
		else {
			set = new TreeSet<E>(comparator);
		}

		Iterator<E> itr = list.iterator();

		while (itr.hasNext()) {
			E obj = itr.next();

			if (set.contains(obj)) {
				itr.remove();
			}
			else {
				set.add(obj);
			}
		}
	}

	public static <E> List<E> fromArray(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return new ArrayList<E>();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> fromCollection(Collection<E> c) {
		if ((c != null) && (List.class.isAssignableFrom(c.getClass()))) {
			return (List)c;
		}

		if ((c == null) || (c.size() == 0)) {
			return new ArrayList<E>();
		}

		List<E> list = new ArrayList<E>(c.size());

		list.addAll(c);

		return list;
	}

	public static <E> List<E> fromEnumeration(Enumeration<E> enu) {
		List<E> list = new ArrayList<E>();

		while (enu.hasMoreElements()) {
			E obj = enu.nextElement();

			list.add(obj);
		}

		return list;
	}

	public static List<String> fromFile(File file) throws IOException {
		List<String> list = new ArrayList<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(file));

		String s = StringPool.BLANK;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			list.add(s);
		}

		unsyncBufferedReader.close();

		return list;
	}

	public static List<String> fromFile(String fileName) throws IOException {
		return fromFile(new File(fileName));
	}

	public static List<String> fromString(String s) {
		return fromArray(StringUtil.split(s, StringPool.NEW_LINE));
	}

	public static <E> List<E> sort(List<E> list) {
		return sort(list, null);
	}

	public static <E> List<E> sort(
		List<E> list, Comparator<? super E> comparator) {

		if (UnmodifiableList.class.isAssignableFrom(list.getClass())) {
			list = copy(list);
		}

		Collections.sort(list, comparator);

		return list;
	}

	public static <E> List<E> subList(List<E> list, int start, int end) {
		List<E> newList = new ArrayList<E>();

		int normalizedSize = list.size() - 1;

		if ((start < 0) || (start > normalizedSize) || (end < 0) ||
			(start > end)) {

			return newList;
		}

		for (int i = start; i < end && i <= normalizedSize; i++) {
			newList.add(list.get(i));
		}

		return newList;
	}

	public static List<Boolean> toList(boolean[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Boolean> list = new ArrayList<Boolean>(array.length);

		for (boolean value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Double> toList(double[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Double> list = new ArrayList<Double>(array.length);

		for (double value : array) {
			list.add(value);
		}

		return list;
	}

	public static <E> List<E> toList(E[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		return new ArrayList<E>(Arrays.asList(array));
	}

	public static List<Float> toList(float[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Float> list = new ArrayList<Float>(array.length);

		for (float value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Integer> toList(int[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Integer> list = new ArrayList<Integer>(array.length);

		for (int value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Long> toList(long[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Long> list = new ArrayList<Long>(array.length);

		for (long value : array) {
			list.add(value);
		}

		return list;
	}

	public static List<Short> toList(short[] array) {
		if ((array == null) || (array.length == 0)) {
			return Collections.emptyList();
		}

		List<Short> list = new ArrayList<Short>(array.length);

		for (short value : array) {
			list.add(value);
		}

		return list;
	}

	public static String toString(List<?> list, String param) {
		return toString(list, param, StringPool.COMMA);
	}

	public static String toString(
		List<?> list, String param, String delimiter) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);

			Object value = BeanPropertiesUtil.getObject(bean, param);

			if (value == null) {
				value = StringPool.BLANK;
			}

			sb.append(value.toString());

			if ((i + 1) != list.size()) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

}