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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="ReadWriteLockKey.java.html"><b><i>View Source</i></b></a>
 *
 * Represents a key that is used by ReadWriteLockRegistry. T must also be
 * immutable and properly implement the equals and hashCode methods. <a
 * href="ReadWriteLockKey.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 * @see	   ReadWriteLockRegistry
 */
public class ReadWriteLockKey<T> {

	public ReadWriteLockKey(T key, boolean writeLock) {
		_key = key;
		_writeLock = writeLock;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof ReadWriteLockKey<?>)) {
			return false;
		}

		ReadWriteLockKey<T> readWriteLockKey = (ReadWriteLockKey<T>)obj;

		if (Validator.equals(_key, readWriteLockKey._key)) {
			return true;
		}

		return true;
	}

	public T getKey() {
		return _key;
	}

	public int hashCode() {
		return _key.hashCode();
	}

	public boolean isWriteLock() {
		return _writeLock;
	}

	private final T _key;
	private final boolean _writeLock;

}