/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.concurrent;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Shuyang Zhou
 */
public class TaskQueue<E> {

	public TaskQueue() {
		this(Integer.MAX_VALUE);
	}

	public TaskQueue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}
		_capacity = capacity;
		_head = _tail = new Node<E>(null);

	}

	public int drainTo(Collection<E> collection) {
		if (collection == null) {
			throw new NullPointerException();
		}
		_takeLock.lock();
		try {
			int size = _count.get();
			Node<E> head = _head;
			int count = 0;
			try {
				while (count < size) {
					Node<E> current = head._next;
					collection.add(current._element);
					current._element = null;
					head._next = null;
					head = current;
					++count;
				}
				return count;
			}
			finally {
				if (count > 0) {
					_head = head;
					_count.getAndAdd(-count);
				}
			}
		}
		finally {
			_takeLock.unlock();
		}
	}

	public boolean isEmpty() {
		return _count.get() == 0;
	}

	public boolean offer(E element, boolean[] hasWaiterMarker) {
		if (element == null || hasWaiterMarker == null) {
			throw new NullPointerException();
		}
		if (hasWaiterMarker.length == 0) {
			throw new IllegalArgumentException();
		}

		if (_count.get() == _capacity) {
			return false;
		}

		int count = -1;
		_putLock.lock();
		try {
			if (_count.get() < _capacity) {
				_enqueue(element);
				count = _count.getAndIncrement();
				_takeLock.lock();
				try {
					// Set marker if there are spare taker threads.
					hasWaiterMarker[0] =
						_takeLock.hasWaiters(_notEmptyCondition);
					// Set marker if there is no spare take thread, but a take
					// has happened after enqueue and before lock up _takeLock.
					if (!hasWaiterMarker[0] && count >= _count.get()) {
						hasWaiterMarker[0] = true;
					}
				}
				finally {
					_takeLock.unlock();
				}
			}
		}
		finally {
			_putLock.unlock();
		}

		if (count == 0) {
			_takeLock.lock();
			try {
				_notEmptyCondition.signal();
			}
			finally {
				_takeLock.unlock();
			}
		}
		return count >= 0;
	}

	public E poll() {
		if (_count.get() == 0) {
			return null;
		}

		E element = null;
		_takeLock.lock();
		try {
			if (_count.get() > 0) {
				element = _dequeue();
				if (_count.getAndDecrement() > 1) {
					_notEmptyCondition.signal();
				}
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	public E poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
		E element = null;
		long nanos = timeUnit.toNanos(timeout);
		_takeLock.lockInterruptibly();
		try {
			while (_count.get() == 0) {
				if (nanos <= 0) {
					return null;
				}
				nanos = _notEmptyCondition.awaitNanos(nanos);
			}
			element = _dequeue();
			if (_count.getAndDecrement() > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}
		return element;
	}

	public int remainingCapacity() {
		return _capacity - _count.get();
	}

	public boolean remove(E element) {
		if (element == null) {
			return false;
		}
		_fullyLock();
		try {
			for (Node<E> previous = _head, current = previous._next;
				current != null;
				previous = current, current = current._next) {
				if (element.equals(current._element)) {
					_unlink(current, previous);
					return true;
				}
			}
			return false;
		}
		finally {
			_fullyUnlock();
		}
	}

	public int size() {
		return _count.get();
	}

	public E take() throws InterruptedException {
		E element = null;
		_takeLock.lockInterruptibly();
		try {
			while (_count.get() == 0) {
				_notEmptyCondition.await();
			}
			element = _dequeue();
			if (_count.getAndDecrement() > 1) {
				_notEmptyCondition.signal();
			}
		}
		finally {
			_takeLock.unlock();
		}

		return element;
	}

	/**
	 * Unit test probe, should not be used other than unit test purpose.
	 * Abusing this method could easily cause deadlock.
	 */
	protected ReentrantLock getPutLock() {
		return _putLock;
	}

	/**
	 * Unit test probe, should not be used other than unit test purpose.
	 * Abusing this method could easily cause deadlock.
	 */
	protected ReentrantLock getTakeLock() {
		return _takeLock;
	}

	private E _dequeue() {
		Node<E> head = _head;
		Node<E> first = head._next;
		head._next = null;
		_head = first;
		E element = first._element;
		first._element = null;
		return element;

	}

	private void _enqueue(E element) {
		_tail = _tail._next = new Node<E>(element);
	}

	private void _fullyLock() {
		_putLock.lock();
		_takeLock.lock();
	}

	private void _fullyUnlock() {
		_takeLock.unlock();
		_putLock.unlock();
	}

	private void _unlink(Node<E> current, Node<E> previous) {
		current._element = null;
		previous._next = current._next;
		if (_tail == current) {
			_tail = previous;
		}
		_count.getAndDecrement();
	}

	private final int _capacity;
	private final AtomicInteger _count = new AtomicInteger();
	private Node<E> _head;
	private Node<E> _tail;
	private final ReentrantLock _putLock = new ReentrantLock();
	private final ReentrantLock _takeLock = new ReentrantLock();
	private final Condition _notEmptyCondition = _takeLock.newCondition();

	private static class Node<E> {

		Node(E element) {
			_element = element;
		}

		E _element;
		Node<E> _next;
	}

}