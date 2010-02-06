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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <a href="ListWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ListWrapper<E> implements List<E> {

	public ListWrapper(List<E> list) {
		_list = list;
	}

	public boolean add(E o) {
		return _list.add(o);
	}

	public void add(int index, E element) {
		_list.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		return _list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return _list.addAll(index, c);
	}

	public void clear() {
		_list.clear();
	}

	public boolean contains(Object o) {
		return _list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return _list.containsAll(c);
	}

	public E get(int index) {
		return _list.get(index);
	}

	public int indexOf(Object o) {
		return _list.indexOf(o);
	}

	public boolean isEmpty() {
		return _list.isEmpty();
	}

	public Iterator<E> iterator() {
		return _list.iterator();
	}

	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return _list.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return _list.listIterator(index);
	}

	public boolean remove(Object o) {
		return _list.remove(o);
	}

	public E remove(int index) {
		return _list.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return _list.retainAll(c);
	}

	public E set(int index, E element) {
		return _list.set(index, element);
	}

	public int size() {
		return _list.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return _list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	private List<E> _list;

}