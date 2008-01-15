/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="Tuple.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class Tuple {

	public Tuple(Object o0, Object o1) {
		_objArr = new Object[] {o0, o1};
	}

	public Tuple(Object o0, Object o1, Object o2) {
		_objArr = new Object[] {o0, o1, o2};
	}

	public Tuple(Object[] objArr) {
		_objArr = objArr;
	}

	public boolean equals(Object x) {
		if (!(x instanceof Tuple)) {
			return false;
		}

		Tuple tuple = (Tuple)x;

		if (tuple._objArr.length != _objArr.length) {
			return false;
		}

		for (int i = 0; i < _objArr.length; i++) {
			if ((tuple._objArr != null) && (_objArr[i] != null) &&
				!_objArr[i].equals(tuple._objArr[i])) {

				return false;
			}
			else if ((tuple._objArr[i]) == null || (_objArr[i] == null)) {
				return false;
			}
		}

		return true;
	}

	public int hashCode() {
		int hashCode = 0;

		for (int i = 0; i < _objArr.length; i++) {
			hashCode = hashCode ^ _objArr[i].hashCode();
		}

		return hashCode;
	}

	public Object getObject(int i) {
		return _objArr[i];
	}

	private Object[] _objArr;

}