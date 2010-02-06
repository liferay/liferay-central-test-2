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

import java.io.Serializable;

/**
 * <a href="ObjectValuePair.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ObjectValuePair<K, V> implements Serializable {

	public ObjectValuePair() {
	}

	public ObjectValuePair(K key, V value) {
		_key = key;
		_value = value;
	}

	public K getKey() {
		return _key;
	}

	public void setKey(K key) {
		_key = key;
	}

	public V getValue() {
		return _value;
	}

	public void setValue(V value) {
		_value = value;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ObjectValuePair<K, V> ovp = (ObjectValuePair<K, V>)obj;

		K key = ovp.getKey();

		if (_key.equals(key)) {
			return true;
		}
		else {
			return false;
		}
	}

	private K _key;
	private V _value;

}