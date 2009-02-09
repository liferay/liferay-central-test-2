/*
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

package com.liferay.portal.benchmark.generator.util;

/**
 * <a href="Id.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class Id {
	public Id(String name) {
		this(name, 0);
	}

	public Id(String name, long start) {
		_name = name;
		_currentId = start;
	}

	public synchronized long next() {
		return _currentId++;
	}

	public synchronized long getCurrentValue() {
		return _currentId;
	}

	public String getName() {
		return _name;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Id id = (Id)o;

		if (_name != null ? !_name.equals(id._name) : id._name != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result = _name != null ? _name.hashCode() : 0;
		result = 31 * result + (int)(_currentId ^ (_currentId >>> 32));
		return result;
	}

	public String toString() {
		return "Id{" +
				"_name='" + _name + '\'' +
				", _currentId=" + _currentId +
				'}';
	}

	private String _name;
	private long _currentId;
}