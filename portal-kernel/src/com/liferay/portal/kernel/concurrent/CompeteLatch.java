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

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

 /**
  * <a href="CompeteLatch.java.html"><b><i>View Source</i></b></a>
  *
  * See LPS-3744 for a demo usage.
  *
  * A synchronizer base on JDK5+'s AQS framework to simulate a single winner
  * competition.<br>
  * This synchronizer supports cyclic competition.(Usually for the situation
  * like losers want to try again)<br>
  * The single winner thread will lock the latch, other threads will block on
  * the latch by calling await.<br>
  * After the winner thread finishs its job, it should call done which will open
  * the latch, all blocking loser threads can pass the latch at the same
  * time.<br>
  * 
  * @author Shuyang Zhou
  *
  */
public class CompeteLatch {

	private final Sync _sync = new Sync();

	private static final class Sync extends AbstractQueuedSynchronizer {

		// 0 for free, 1 for taken

		final boolean tryInitAcquireShared() {
			return compareAndSetState(0, 1);
		}

		@Override
		protected int tryAcquireShared(int arg) {
			return getState() == 0 ? 1 : -1;
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			return compareAndSetState(1, 0);
		}

		final boolean isLocked(){
			return getState()==1;
		}
	}

	/**
	 * Current thread join the competition. Return immediately no matter current
	 * thread is the winner or loser. No matter how many threads join this
	 * competition, only one thread can become the winner.
	 *
	 * @return true if the current thread is the winner, otherwise false for
	 * loser.
	 */
	public boolean compete() {
		return _sync.tryInitAcquireShared();
	}

	/**
	 * <b>Note:</b><i>This method should only be called by loser thread. <i><br>
	 * If the latch has been locked which means the winner is doing its private
	 * stuff, all loser threads call this method will be blocked.<br>
	 * If the latch has been opened which means the winner has finished its job,
	 * the loser threads calling this method will all return immediately.<br>
	 * If the winner thread calls this method before he calls done, he will be
	 * blocked for ever and all losers who call this method will also
	 * be blocked, oops deadlock!
	 */
	public void await() {
		_sync.acquireShared(1);
	}

	/**
	 * <b>Note:</b><i>This method should only be called by winner thread.<i><br>
	 * Winner thread calls this method to indicate it has finished its job, and
	 * open the latch to allow all loser threads return from the await
	 * method.<br>
	 * If a non-winner thread does call this method when a winner thread has
	 * locked the latch, the latch will break, the winner thread may be put
	 * into a non-threadsafe state, usually you should never do this, but this
	 * can be used for deadlock protection.<br>
	 * If no one has locked the latch, calling this method takes no effect.<br>
	 * This method will return immediately.
	 *
	 * @return true if this call do open the latch, false if the latch is
	 * already open.
	 */
	public boolean done() {
		return _sync.releaseShared(1);
	}

	/**
	 * Indicate the latch is open or locked. This method should not be used to
	 * test latch before join competition which is not threadsafe. The only
	 * purpose for this method is giving external system a way to monitor the
	 * latch which usually be used for deadlock detection.
	 * @return
	 */
	public boolean isLocked(){
		return _sync.isLocked();
	}
}