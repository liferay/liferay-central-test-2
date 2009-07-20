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

public class CompeteLatch {

	public void await() {
		_sync.acquireShared(1);
	}

	public boolean compete() {
		return _sync._tryInitAcquireShared();
	}

	public boolean done() {
		return _sync.releaseShared(1);
	}

	public boolean isLocked(){
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

		private final boolean _isLocked(){
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