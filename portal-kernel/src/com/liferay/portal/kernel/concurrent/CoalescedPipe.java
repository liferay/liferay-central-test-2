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

	public CoalescedPipe(Comparator<E> coalesceComparator) {
		_coalesceComparator = coalesceComparator;
		_last = _head = new Element<E>(null);
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
			_last = _last.next = new Element<E>(e);
			pendingElements = _pendingCount.getAndIncrement();
		}
		finally {
			_putLock.unlock();
		}
		if (pendingElements == 0) {
			_takeLock.lock();
			try {
				_notEmpty.signal();
			}
			finally {
				_takeLock.unlock();
			}
		}
	}

	public E take() throws InterruptedException {
		E item;
		_takeLock.lockInterruptibly();
		try {
			while (_pendingCount.get() == 0) {
				_notEmpty.await();
			}
			Element<E> garbageELement = _head;
			_head = _head.next;

			// Detach garbage element, help GC
			garbageELement.next = null;
			item = _head.item;

			// Detach reference, help GC
			_head.item = null;
			int pendingElements = _pendingCount.getAndDecrement();
			if (pendingElements > 1) {
				_notEmpty.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}
		return item;
	}

	public Object[] takeSnapshot() {
		_putLock.lock();
		_takeLock.lock();
		try {
			int pendingElements = _pendingCount.get();
			Object[] array = new Object[pendingElements];
			int index = 0;
			Element<E> current = _head.next;
			while (current != null) {
				array[index++] = current.item;
				current = current.next;
			}
			return array;
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
				Element<E> current = _head.next;

				if (_coalesceComparator != null) {
					// coalesce by comparator
					while (current != null) {
						if (_coalesceComparator.compare(current.item, e) == 0) {
							_coalescedCount.incrementAndGet();
							return true;
						}
						else {
							current = current.next;
						}
					}
				}
				else {
					// coalesce by equals
					while (current != null) {
						if (current.item.equals(e)) {
							_coalescedCount.incrementAndGet();
							return true;
						}
						else {
							current = current.next;
						}
					}
				}
			}
			finally {
				_takeLock.unlock();
			}
		}
		catch (InterruptedException ignore) {
			// If get an interrupt during coalescing, simply return false to
			// let the current element go into the pipe.
		}

		return false;
	}

	private final Comparator<E> _coalesceComparator;
	private final AtomicLong _coalescedCount = new AtomicLong(0);
	private final AtomicInteger _pendingCount = new AtomicInteger(0);
	private final ReentrantLock _putLock = new ReentrantLock();
	private final ReentrantLock _takeLock = new ReentrantLock();
	private final Condition _notEmpty = _takeLock.newCondition();

	private Element<E> _head;
	private Element<E> _last;

	static class Element<E> {

		E item;
		Element<E> next;

		Element(E x) {
			item = x;
		}

	}

}