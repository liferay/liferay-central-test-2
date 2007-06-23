/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

/**
 * <a href="ArrayUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ArrayUtil {

	public static Boolean[] append(Boolean[] array, Boolean obj) {
		Boolean[] newArray = new Boolean[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Double[] append(Double[] array, Double obj) {
		Double[] newArray = new Double[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Integer[] append(Integer[] array, Integer obj) {
		Integer[] newArray = new Integer[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Long[] append(Long[] array, Long obj) {
		Long[] newArray = new Long[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Object[] append(Object[] array, Object obj) {
		Object[] newArray = new Object[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Object[][] append(Object[][] array, Object[] obj) {
		Object[][] newArray = new Object[array.length + 1][];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Short[] append(Short[] array, Short obj) {
		Short[] newArray = new Short[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static String[] append(String[] array, String obj) {
		String[] newArray = new String[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static String[][] append(String[][] array, String[] obj) {
		String[][] newArray = new String[array.length + 1][];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = obj;

		return newArray;
	}

	public static Boolean[] append(Boolean[] array1, Boolean[] array2) {
		Boolean[] newArray = new Boolean[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Double[] append(Double[] array1, Double[] array2) {
		Double[] newArray = new Double[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Integer[] append(Integer[] array1, Integer[] array2) {
		Integer[] newArray = new Integer[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Long[] append(Long[] array1, Long[] array2) {
		Long[] newArray = new Long[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Object[] append(Object[] array1, Object[] array2) {
		Object[] newArray = new Object[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Object[][] append(Object[][] array1, Object[][] array2) {
		Object[][] newArray = new Object[array1.length + array2.length][];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static Short[] append(Short[] array1, Short[] array2) {
		Short[] newArray = new Short[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static String[] append(String[] array1, String[] array2) {
		String[] newArray = new String[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static String[][] append(String[][] array1, String[][] array2) {
		String[][] newArray = new String[array1.length + array2.length][];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	public static void combine(
		Object[] array1, Object[] array2, Object[] combinedArray) {

		System.arraycopy(array1, 0, combinedArray, 0, array1.length);

		System.arraycopy(
			array2, 0, combinedArray, array1.length, array2.length);
	}

	public static boolean contains(Object[] array, Object obj) {
		if (array == null) {
			return false;
		}
		else {
			for (int i = 0; i < array.length; i++) {
				if (obj.equals(array[i])) {
					return true;
				}
			}

			return false;
		}
	}

	public static int getLength(Object[] array) {
		if (array == null) {
			return 0;
		}
		else {
			return array.length;
		}
	}

	public static Object getValue(Object[] array, int pos) {
		if ((array == null) || (array.length <= pos)) {
			return null;
		}
		else {
			return array[pos];
		}
	}

}