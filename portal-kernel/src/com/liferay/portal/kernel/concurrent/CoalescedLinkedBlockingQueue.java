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

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <a href="CoalescedLinkedBlockingQueue.java.html"><b><i>View Source</i></b>
 * </a>
 * This class is a copy from java.util.concurrent.LinkedBlockingQueue.
 * But enhanced with the ability to coalesce elements.
 * Please do not refactor this class, otherwise may cause trouble for further
 * maintain.
 *
 * @author Doug Lea
 * @author Shuyang Zhou
 */
public class CoalescedLinkedBlockingQueue<E> extends AbstractQueue<E>
	implements BlockingQueue<E>, java.io.Serializable {

	static class Node<E> {

		E item;
		Node<E> next;

		Node(E x) {
			item = x;
		}

	}

	public CoalescedLinkedBlockingQueue() {
		this(Integer.MAX_VALUE, null);
	}

	public CoalescedLinkedBlockingQueue(Comparator<E> coalesceComparator) {
		this(Integer.MAX_VALUE, coalesceComparator);
	}

	public CoalescedLinkedBlockingQueue(int capacity,
		Comparator<E> coalesceComparator) {
		if (capacity <= 0) {
			throw new IllegalArgumentException();

		}
		this.capacity = capacity;
		this.coalesceComparator = coalesceComparator;
		last = head = new Node<E>(null);
	}

	public CoalescedLinkedBlockingQueue(Collection<? extends E> c) {
		this(c, null);
	}

	public CoalescedLinkedBlockingQueue(Collection<? extends E> c,
		Comparator<E> coalesceComparator) {
		this(Integer.MAX_VALUE, coalesceComparator);
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			int n = 0;
			for (E e : c) {
				if (e == null) {
					throw new NullPointerException();

				}
				if (n == capacity) {
					throw new IllegalStateException("Queue full");

				}
				enqueue(e);
				++n;
			}
			count.set(n);
		}
		finally {
			putLock.unlock();
		}
	}

	public void clear() {
		fullyLock();
		try {
			for (Node<E> p, h = head; (p = h.next) != null; h = p) {
				h.next = h;
				p.item = null;
			}
			head = last;
			// assert head.item == null && head.next == null;
			if (count.getAndSet(0) == capacity) {
				notFull.signal();
			}
		}
		finally {
			fullyUnlock();
		}
	}

	public long coalesceCount() {
		return coalescedCount.get();
	}

	public int drainTo(Collection<? super E> c) {
		return drainTo(c, Integer.MAX_VALUE);
	}

	public int drainTo(Collection<? super E> c, int maxElements) {
		if (c == null) {
			throw new NullPointerException();
		}
		if (c == this) {
			throw new IllegalArgumentException();
		}
		boolean signalNotFull = false;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			int n = Math.min(maxElements, count.get());
			// count.get provides visibility to first n Nodes
			Node<E> h = head;
			int i = 0;
			try {
				while (i < n) {
					Node<E> p = h.next;
					c.add(p.item);
					p.item = null;
					h.next = h;
					h = p;
					++i;
				}
				return n;
			}
			finally {
				// Restore invariants even if c.add() threw
				if (i > 0) {
					// assert h.item == null;
					head = h;
					signalNotFull = (count.getAndAdd(-i) == capacity);
				}
			}
		}
		finally {
			takeLock.unlock();
			if (signalNotFull) {
				signalNotFull();
			}
		}
	}

	public Iterator<E> iterator() {
		return new Itr();
	}

	public boolean offer(E e, long timeout, TimeUnit unit)
		throws InterruptedException {

		if (e == null) {
			throw new NullPointerException();
		}
		long nanos = unit.toNanos(timeout);
		int c = -1;
		final ReentrantLock putLock = this.putLock;
		final AtomicInteger count = this.count;
		putLock.lockInterruptibly();
		try {
			if (coalesceElement(e)) {
				return true;
			}
			while (count.get() == capacity) {
				if (nanos <= 0) {
					return false;
				}
				nanos = notFull.awaitNanos(nanos);
			}
			enqueue(e);
			c = count.getAndIncrement();
			if (c + 1 < capacity) {
				notFull.signal();
			}
		}
		finally {
			putLock.unlock();
		}
		if (c == 0) {
			signalNotEmpty();
		}
		return true;
	}

	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		final AtomicInteger count = this.count;
		if (count.get() == capacity) {
			return false;
		}
		int c = -1;
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			if (coalesceElement(e)) {
				return true;
			}
			if (count.get() < capacity) {
				enqueue(e);
				c = count.getAndIncrement();
				if (c + 1 < capacity) {
					notFull.signal();
				}
			}
		}
		finally {
			putLock.unlock();
		}
		if (c == 0) {
			signalNotEmpty();
		}
		return c >= 0;
	}

	public E peek() {
		if (count.get() == 0) {
			return null;
		}
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			Node<E> first = head.next;
			if (first == null) {
				return null;
			}
			else {
				return first.item;
			}
		}
		finally {
			takeLock.unlock();
		}
	}

	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E x = null;
		int c = -1;
		long nanos = unit.toNanos(timeout);
		final AtomicInteger count = this.count;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) {
				if (nanos <= 0) {
					return null;
				}
				nanos = notEmpty.awaitNanos(nanos);
			}
			x = dequeue();
			c = count.getAndDecrement();
			if (c > 1) {
				notEmpty.signal();
			}
		}
		finally {
			takeLock.unlock();
		}
		if (c == capacity) {
			signalNotFull();
		}
		return x;
	}

	public E poll() {
		final AtomicInteger count = this.count;
		if (count.get() == 0) {
			return null;
		}
		E x = null;
		int c = -1;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			if (count.get() > 0) {
				x = dequeue();
				c = count.getAndDecrement();
				if (c > 1) {
					notEmpty.signal();
				}
			}
		}
		finally {
			takeLock.unlock();
		}
		if (c == capacity) {
			signalNotFull();
		}
		return x;
	}

	public void put(E e) throws InterruptedException {
		if (e == null) {
			throw new NullPointerException();
		}
		int c = -1;
		final ReentrantLock putLock = this.putLock;
		final AtomicInteger count = this.count;
		putLock.lockInterruptibly();
		try {
			if (coalesceElement(e)) {
				return;
			}
			while (count.get() == capacity) {
				notFull.await();
			}
			enqueue(e);
			c = count.getAndIncrement();
			if (c + 1 < capacity) {
				notFull.signal();

			}
		}
		finally {
			putLock.unlock();
		}
		if (c == 0) {
			signalNotEmpty();

		}
	}

	public int remainingCapacity() {
		return capacity - count.get();
	}

	public boolean remove(Object o) {
		if (o == null) {
			return false;
		}
		fullyLock();
		try {
			for (Node<E> trail = head, p = trail.next;
				p != null;
				trail = p, p = p.next) {
				if (o.equals(p.item)) {
					unlink(p, trail);
					return true;
				}
			}
			return false;
		}
		finally {
			fullyUnlock();
		}
	}

	public int size() {
		return count.get();
	}

	public E take() throws InterruptedException {
		E x;
		int c = -1;
		final AtomicInteger count = this.count;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) {
				notEmpty.await();
			}
			x = dequeue();
			c = count.getAndDecrement();
			if (c > 1) {
				notEmpty.signal();
			}
		}
		finally {
			takeLock.unlock();
		}
		if (c == capacity) {
			signalNotFull();
		}
		return x;
	}

	public Object[] toArray() {
		fullyLock();
		try {
			int size = count.get();
			Object[] a = new Object[size];
			int k = 0;
			for (Node<E> p = head.next; p != null; p = p.next) {
				a[k++] = p.item;
			}
			return a;
		}
		finally {
			fullyUnlock();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		fullyLock();
		try {
			int size = count.get();
			if (a.length < size) {
				a = (T[]) java.lang.reflect.Array.newInstance(
					a.getClass().getComponentType(), size);
			}

			int k = 0;
			for (Node<E> p = head.next; p != null; p = p.next) {
				a[k++] = (T) p.item;
			}
			if (a.length > k) {
				a[k] = null;
			}
			return a;
		}
		finally {
			fullyUnlock();
		}
	}

	public String toString() {
		fullyLock();
		try {
			return super.toString();
		}
		finally {
			fullyUnlock();
		}
	}

	void fullyLock() {
		putLock.lock();
		takeLock.lock();
	}

	void fullyUnlock() {
		takeLock.unlock();
		putLock.unlock();
	}

	void unlink(Node<E> p, Node<E> trail) {
		p.item = null;
		trail.next = p.next;
		if (last == p) {
			last = trail;
		}
		if (count.getAndDecrement() == capacity) {
			notFull.signal();
		}
	}

	private boolean coalesceElement(E e) {
		final ReentrantLock takeLock = this.takeLock;
		try {
			takeLock.lockInterruptibly();
			try {
				Node<E> current = this.head.next;
				if (coalesceComparator != null) {
					while (current != null) {
						if (coalesceComparator.compare(current.item, e) == 0) {
							coalescedCount.incrementAndGet();
							return true;
						}
						else {
							current = current.next;
						}
					}
				}
				else {
					while (current != null) {
						if (current.item.equals(e)) {
							coalescedCount.incrementAndGet();
							return true;
						}
						else {
							current = current.next;
						}
					}
				}
			}
			finally {
				takeLock.unlock();
			}
		}
		catch (InterruptedException ignore) {
		}

		return false;
	}

	private E dequeue() {
		Node<E> h = head;
		Node<E> first = h.next;
		h.next = h;
		head = first;
		E x = first.item;
		first.item = null;
		return x;
	}

	private void enqueue(E x) {
		last = last.next = new Node<E>(x);
	}

	private void signalNotEmpty() {
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			notEmpty.signal();
		}
		finally {
			takeLock.unlock();
		}
	}

	private void signalNotFull() {
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			notFull.signal();
		}
		finally {
			putLock.unlock();
		}
	}

	private class Itr implements Iterator<E> {

		private Node<E> current;
		private Node<E> lastRet;
		private E currentElement;

		Itr() {
			fullyLock();
			try {
				current = head.next;
				if (current != null) {
					currentElement = current.item;
				}
			}
			finally {
				fullyUnlock();
			}
		}

		public boolean hasNext() {
			return current != null;
		}

		private Node<E> nextNode(Node<E> p) {
			for (;;) {
				Node<E> s = p.next;
				if (s == p) {
					return head.next;
				}
				if (s == null || s.item != null) {
					return s;
				}
				p = s;
			}
		}

		public E next() {
			fullyLock();
			try {
				if (current == null) {
					throw new NoSuchElementException();
				}
				E x = currentElement;
				lastRet = current;
				current = nextNode(current);
				currentElement = (current == null) ? null : current.item;
				return x;
			}
			finally {
				fullyUnlock();
			}
		}

		public void remove() {
			if (lastRet == null) {
				throw new IllegalStateException();
			}
			fullyLock();
			try {
				Node<E> node = lastRet;
				lastRet = null;
				for (Node<E> trail = head, p = trail.next;
					p != null;
					trail = p, p = p.next) {
					if (p == node) {
						unlink(p, trail);
						break;
					}
				}
			}
			finally {
				fullyUnlock();
			}
		}

	}

	private void writeObject(java.io.ObjectOutputStream s)
		throws java.io.IOException {

		fullyLock();
		try {
			// Write out any hidden stuff, plus capacity
			s.defaultWriteObject();

			// Write out all elements in the proper order.
			for (Node<E> p = head.next; p != null; p = p.next) {
				s.writeObject(p.item);
			}

			// Use trailing null as sentinel
			s.writeObject(null);
		}
		finally {
			fullyUnlock();
		}
	}

	private void readObject(java.io.ObjectInputStream s)
		throws java.io.IOException, ClassNotFoundException {
		// Read in capacity, and any hidden stuff
		s.defaultReadObject();

		count.set(0);
		last = head = new Node<E>(null);

		// Read in all elements and place in queue
		for (;;) {
			@SuppressWarnings("unchecked")
			E item = (E) s.readObject();
			if (item == null) {
				break;
			}
			add(item);
		}
	}

	private final int capacity;
	private final Comparator<E> coalesceComparator;
	private final AtomicLong coalescedCount = new AtomicLong(0);
	private final AtomicInteger count = new AtomicInteger(0);
	private transient Node<E> head;
	private transient Node<E> last;
	private final ReentrantLock putLock = new ReentrantLock();
	private final Condition notFull = putLock.newCondition();
	private final ReentrantLock takeLock = new ReentrantLock();
	private final Condition notEmpty = takeLock.newCondition();

}