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

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * <a href="CompeteLatch.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * A synchronizer based on the JDK's AQS framework to simulate a single winner
 * competition. This synchronizer supports cyclical competition. In this
 * situation, loser threads should try again. The single winner thread will lock
 * the latch while other threads will block on the latch by calling
 * <code>await</code>. After the winner thread finishes its job, it should call
 * <code>done</code> which will open the latch. All blocking loser threads can
 * pass the latch at the same time.
 * </p>
 *
 * <p>
 * See LPS-3744 for a sample use case.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class CompeteLatch {

	/**
	 * This method should only be called by a loser thread. If the latch is
	 * locked, that means the winner is executing its job and all loser threads
	 * that call this method will be blocked. If the latch is not locked, that
	 * means the winner has finished its job and all the loser threads calling
	 * this method will return immediately. If the winner thread calls this
	 * method before his job completed, then all threads will deadlock.
	 */
	public void await() {
		_sync.acquireShared(1);
	}

	/**
	 * Tells the current thread to join the competition. Return immediately
	 * whether or not the current thread is the winner thread or a loser thread.
	 * No matter how many threads join this competition, only one thread can be
	 * the winner thread.
	 *
	 * @return true if the current thread is the winner thread
	 */
	public boolean compete() {
		return _sync._tryInitAcquireShared();
	}

	/**
	 * This method should only be called by the winner thread. The winner thread
	 * calls this method to indicate that it has finished its job and unlocks
	 * the latch to allow all loser threads return from the <code>await</code>
	 * method. If a loser thread does call this method when a winner thread has
	 * locked the latch, the latch will break and the winner thread may be put
	 * into a non thread safe state. You should never have to do this except to
	 * get out of a deadlock. If no one threads have locked the latch, then
	 * calling this method has no effect. This method will return immediately.
	 *
	 * @return true if this call opens the latch, false if the latch is already
	 *		   open
	 */
	public boolean done() {
		return _sync.releaseShared(1);
	}

	/**
	 * Returns true if the latch is locked. This method should not be used to
	 * test the latch before joining a competition because it is not thread
	 * safe. The only purpose for this method is to give external systems a way
	 * to monitor the latch which is usually be used for deadlock detection.
	 */
	public boolean isLocked() {
		return _sync._isLocked();
	}

	private Sync _sync = new Sync();

	private class Sync extends AbstractQueuedSynchronizer {

		protected int tryAcquireShared(int arg) {
			if (getState() == 0) {
				return 1;
			}
			else {
				return -1;
			}
		}

		protected boolean tryReleaseShared(int arg) {
			if (compareAndSetState(1, 0)) {
				return true;
			}
			else {
				return false;
			}
		}

		private final boolean _isLocked() {
			if (getState() == 1) {
				return true;
			}
			else {
				return false;
			}
		}

		private final boolean _tryInitAcquireShared() {
			if (compareAndSetState(0, 1)) {
				return true;
			}
			else {
				return false;
			}
		}

	}

}