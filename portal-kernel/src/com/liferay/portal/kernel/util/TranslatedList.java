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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <a href="TranslatedList.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class TranslatedList<E, F> extends ListWrapper<E> {

	public TranslatedList(List<E> newList, List<F> oldList) {
		super(newList);

		_oldList = oldList;
	}

	public boolean add(E o) {
		_oldList.add(toOldObject(o));

		return super.add(o);
	}

	public void add(int index, E element) {
		_oldList.add(index, toOldObject(element));

		super.add(index, element);
	}

	public boolean addAll(Collection<? extends E> c) {
		for (E o : c) {
			_oldList.add(toOldObject(o));
		}

		return super.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		for (E o : c) {
			_oldList.add(index++, toOldObject(o));
		}

		return super.addAll(c);
	}

	public boolean remove(Object o) {
		_oldList.remove(toOldObject((E)o));

		return super.remove(o);
	}

	public E remove(int index) {
		_oldList.remove(index);

		return super.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		List<F> tempList = new ArrayList<F>();

		for (Object o : c) {
			tempList.add(toOldObject((E)o));
		}

		_oldList.removeAll(tempList);

		return super.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		List<F> tempList = new ArrayList<F>();

		for (Object o : c) {
			tempList.add(toOldObject((E)o));
		}

		_oldList.retainAll(tempList);

		return super.retainAll(c);
	}

	public E set(int index, E element) {
		_oldList.set(index, toOldObject(element));

		return super.set(index, element);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		List<E> newList = super.subList(fromIndex, toIndex);
		List<F> oldList = _oldList.subList(fromIndex, toIndex);

		return newInstance(newList, oldList);
	}

	protected abstract TranslatedList<E, F> newInstance(
		List<E> newList, List<F> oldList);

	protected abstract F toOldObject(E o);

	private List<F> _oldList;

}