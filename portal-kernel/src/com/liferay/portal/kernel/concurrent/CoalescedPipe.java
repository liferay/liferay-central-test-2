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

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <a href="CoalescedPipe.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class CoalescedPipe<E> {

	public CoalescedPipe() {
		this(null);
	}

	public CoalescedPipe(Comparator<E> comparator) {
		_comparator = comparator;
		_headElementLink = new ElementLink<E>(null);
		_lastElementLink = _headElementLink;
		_notEmptyCondition = _takeLock.newCondition();
	}

	public long coalescedCount() {
		return _coalescedCount.get();
	}

	public int pendingCount() {
		return _pendingCount.get();
	}

	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new NullPointerException();
		}

		int pendingElements = -1;

		_putLock.lockInterruptibly();

		try {
			if (_coalesceElement(e)) {
				return;
			}

			_lastElementLink = new ElementLink<E>(e);

			_lastElementLink._nextElementLink = _lastElementLink;

			pendingElements = _pendingCount.getAndIncrement();
		}
		finally {
			_putLock.unlock();
		}

		if (pendingElements == 0) {
			_takeLock.lock();

			try {
				_notEmptyCondition.signal();
			}
			finally {
				_takeLock.unlock();
			}
		}
	}

	public E take() throws InterruptedException {
		E element = null;

		_takeLock.lockInterruptibly();

		try {
			while (_pendingCount.get() == 0) {
				_notEmptyCondition.await();
			}

			ElementLink<E> garbageElementLink = _headElementLink;

			_headElementLink = _headElementLink._nextElementLink;

			garbageElementLink._nextElementLink = null;

			element = _headElementLink._element;

			_headElementLink._element = null;

			int pendingElements = _pendingCount.getAndDecrement();

			if (pendingElements > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	public Object[] takeSnapshot() {
		_putLock.lock();
		_takeLock.lock();

		try {
			Object[] pendingElements = new Object[_pendingCount.get()];

			ElementLink<E> currentElementLink =
				_headElementLink._nextElementLink;

			for (int i = 0; currentElementLink != null; i++) {
				pendingElements[i++] = currentElementLink._element;

				currentElementLink = currentElementLink._nextElementLink;
			}

			return pendingElements;
		}
		finally {
			_putLock.unlock();
			_takeLock.unlock();
		}
	}

	private boolean _coalesceElement(E e) {
		try {
			_takeLock.lockInterruptibly();

			try {
				ElementLink<E> current = _headElementLink._nextElementLink;

				if (_comparator != null) {
					while (current != null) {
						if (_comparator.compare(current._element, e) == 0) {
							_coalescedCount.incrementAndGet();

							return true;
						}
						else {
							current = current._nextElementLink;
						}
					}
				}
				else {
					while (current != null) {
						if (current._element.equals(e)) {
							_coalescedCount.incrementAndGet();

							return true;
						}
						else {
							current = current._nextElementLink;
						}
					}
				}
			}
			finally {
				_takeLock.unlock();
			}
		}
		catch (InterruptedException ie) {
		}

		return false;
	}

	private final Comparator<E> _comparator;
	private final AtomicLong _coalescedCount = new AtomicLong(0);
	private ElementLink<E> _headElementLink;
	private ElementLink<E> _lastElementLink;
	private final Condition _notEmptyCondition;
	private final AtomicInteger _pendingCount = new AtomicInteger(0);
	private final ReentrantLock _putLock = new ReentrantLock();
	private final ReentrantLock _takeLock = new ReentrantLock();

	private static class ElementLink<E> {

		private ElementLink(E element) {
			this._element = element;
		}

		private E _element;
		private ElementLink<E> _nextElementLink;

	}

}