/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <a href="BatchablePipe.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BatchablePipe<K, V> {

	public BatchablePipe() {
		_headEntry = new Entry<K, V>(null);
		_lastEntryRef = new AtomicReference<Entry<K, V>>(_headEntry);
	}

	/**
	 * Put an IncreasableEntry into pipe, do increase if possible, otherwise
	 * append new entry to the tail.
	 *
	 * @param entry The new entry
	 * @return true if append the new entry to the tail, false if do increase
	 * successfully
	 */
	public boolean put(IncreasableEntry<K, V> entry) {
		Entry<K, V> newEntry = new Entry<K, V>(entry);
		while (true) {
			if (doIncrease(entry)) {
				// Increase successfuly, no need to append new entry
				return false;
			}

			Entry<K, V> lastEntryLink = _lastEntryRef.get();
			Entry<K, V> nextEntryLink = lastEntryLink._nextEntry.getReference();
			if (nextEntryLink == null) {
				// Try to append to last, return on success, retry on fail
				if (lastEntryLink._nextEntry.compareAndSet(
					null, newEntry, false, false)) {
					_lastEntryRef.set(newEntry);
					return true;
				}
			}
			else {
				// _lastEntryRef is out of date, update it.
				// The only reason for this is some other thread just appended
				// a new entry, so it is worthy to try doBatch() again.
				_lastEntryRef.compareAndSet(lastEntryLink, nextEntryLink);
			}
		}

	}

	public IncreasableEntry<K, V> take() {
		boolean[] marked = {false};

		Retry:
		while (true) {
			// Start searching first takable entry from head
			Entry<K, V> predecessor = _headEntry;
			Entry<K, V> current = predecessor._nextEntry.getReference();
			// Do searching until reaching the tail
			while (current != null) {
				Entry<K, V> successor = current._nextEntry.get(marked);
				// Do physically clean up if current is marked as logicly
				// removed
				if (marked[0]) {
					// Try to physically clean up current
					if (predecessor._nextEntry.compareAndSet(
						current, successor, false, false) == false) {
						// Some other thread just clean it up, retry to avoid
						// corrupt links.
						continue Retry;
					}
					// clean up successfully, rediect the links
					current = predecessor._nextEntry.getReference();
					continue;
				}

				// Current entry is valid, try to logicly remove it.
				if (current._nextEntry.compareAndSet(
					successor, successor, false, true)) {
					return current._entry;
				}
				else {
					// Some other thread just removed
					// (either logicly or physically) current, retry
					continue Retry;
				}
			}
			// Nothing is takable, which means the pipe is empty currently
			return null;
		}
	}

	/**
	 * Do increase and physically clean up logicly removed entries.
	 * @param entry The entry trys to batch
	 * @return true on success batch, otherwise false
	 */
	private boolean doIncrease(IncreasableEntry<K, V> entry) {
		boolean[] marked = {false};

		Retry:
		while (true) {
			// Start searching from head
			Entry<K, V> predecessor = _headEntry;
			Entry<K, V> current = predecessor._nextEntry.getReference();
			// Do searching until reaching the tail
			while (current != null) {
				Entry<K, V> successor = current._nextEntry.get(marked);
				// Do physically clean up if current is marked as
				// logicly removed
				if (marked[0]) {
					// Try to physically clean up current
					if (predecessor._nextEntry.compareAndSet(
						current, successor, false, false) == false) {
						// Some other thread just clean it up, retry to avoid
						// corrupt links.
						continue Retry;
					}
					// clean up successfully, rediect the links
					current = predecessor._nextEntry.getReference();
					continue;
				}

				// Current entry is valid, try to batch it.
				if (current._entry.getKey().equals(entry.getKey())) {
					// Find a match entry, do increase. If race condition
					// happens here(at the mean time some other thread already
					// take this entry off from pipe), the increase will depends
					// on IncreasableEntry's synchronization logic to prevent
					// losing increase value.
					// In other word, even other thread take off this entry
					// already, before it calls get to retrieve the value, it is
					// still valid to do increase
					return current._entry.increase(entry.getValue());
				}
				// Move forward
				predecessor = current;
				current = successor;
			}
			// Already searched the whole list, nothing batchable
			_lastEntryRef.set(predecessor);
			return false;
		}
	}

	private static class Entry<K, V> {

		private Entry(IncreasableEntry<K, V> entry) {
			_entry = entry;
			_nextEntry = new AtomicMarkableReference<Entry<K, V>>(
				null, false);
		}

		private IncreasableEntry<K, V> _entry;
		private AtomicMarkableReference<Entry<K, V>> _nextEntry;

	}

	private final Entry<K, V> _headEntry;
	private final AtomicReference<Entry<K, V>> _lastEntryRef;

}