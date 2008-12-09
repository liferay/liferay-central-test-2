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
package com.liferay.portal.kernel.util;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/**
 * <a href="PrepopulatedList.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 * This is identical to <code>java.util.ArrayList</code> with the exception that
 * its constructor takes a prepopulated array as its element data.  This is
 * useful in cases when a temporary array is being used to populate a List since
 * it reduces an extra GC (like in
 * <code>com.liferay.portal.kernel.util.ListUtil.sort()</code>).
 */
public class PrepopulatedList <E> extends AbstractList<E>
	implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    private transient E[] _elementData;

    private int _size;

	public PrepopulatedList(E[] elements) {
		_elementData = elements;
		_size = elements.length;
	}

	public void add(int index, E element) {
		_rangeCheck(index, _size - 1);
		_ensureCapacity(_size + 1);

		System.arraycopy(
			_elementData, index, _elementData, index + 1, _size - index);
		_elementData[index] = element;
		_size++;
	}

	public E get(int index) {
		_rangeCheck(index);

		return _elementData[index];
	}

	public E remove(int index) {
		_rangeCheck(index);
		modCount++;

		E oldValue = _elementData[index];

		int numMoved = _size - index - 1;
		if (numMoved > 0) {
		    System.arraycopy(
		    	_elementData, index + 1, _elementData, index, numMoved);
		}

		_elementData[--_size] = null;

		return oldValue;
	}

	public E set(int index, E element) {
		_rangeCheck(index);

		E oldValue = _elementData[index];
		_elementData[index] = element;

		return oldValue;
	}

	public int size() {
		return _size;
	}

    private void _ensureCapacity(int minCapacity) {
    	int oldCapacity = _elementData.length;
    	modCount++;

    	if (minCapacity > oldCapacity) {
    	    Object oldData[] = _elementData;
    	    int newCapacity = (oldCapacity * 3) / 2 + 1;

    	    if (newCapacity < minCapacity) {
    	    	newCapacity = minCapacity;
    	    }

    	    _elementData = (E[])new Object[newCapacity];
    	    System.arraycopy(oldData, 0, _elementData, 0, _size);
    	}
    }

    private void _rangeCheck(int index) {
    	_rangeCheck(index, _size);
    }

    private void _rangeCheck(int index, int size) {
    	if (index >= size || index < 0) {
    	    throw new IndexOutOfBoundsException(
    	    	"Index " + index + " size " + _size);
        }
	}

}