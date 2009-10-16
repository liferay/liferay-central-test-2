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

package com.liferay.portal.kernel.concurrent;

/**
 * This class is used as key to query on the ReadWriteLockRegistry.
 * This class has to be immutable and defined proper equals/hashCode methods.
 * ReadWriteLockRegistry dependons on these features to work correctly.
 *
 * <b>Warning!</b>
 * To ensure those required features the parameter T _key has also to be
 * immutable and defined proper equals/hashCode methods.
 *
 * <a href="ReadWriteLockKey.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ReadWriteLockKey<T> {

	public ReadWriteLockKey(T key, boolean writeLock) {
		_key = key;
		_writeLock = writeLock;
	}

	public T getKey() {
		return _key;
	}

	public boolean isWriteLock() {
		return _writeLock;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ReadWriteLockKey<T> other = (ReadWriteLockKey<T>) obj;
		if (this._key != other._key
			&& (this._key == null || !this._key.equals(other._key))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + (this._key != null ? this._key.hashCode() : 0);
		return hash;
	}

	private final T _key;
	private final boolean _writeLock;

}