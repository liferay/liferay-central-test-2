/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.mail.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * <a href="MailSessionLock.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class MailSessionLock {

	public static MailSessionLock getInstance() {
		return _lock;
	}

	public void lock(String sessionId) {
		ThreadLocal tl = null;

		for (;;) {
			synchronized (_sessionMap) {
				tl = (ThreadLocal)_sessionMap.get(sessionId);

				if (tl == null) {
					// Initialize reentrant counter.

					tl = new ThreadLocal();

					tl.set(new Long(0));

					_sessionMap.put(sessionId, tl);

					break;
				}
				else if (tl.get() != null) {
					// This thread instantiated the thread local.  Increment the
					// reentrant counter.

					Long count = (Long)tl.get();

					tl.set(new Long(count.longValue() + 1L));

					break;
				}
			}

			// Another thread instantiated the thread local.  Wait until that
			// thread is done.

			try {
				wait(100);
			}
			catch (Exception ex) {
			}
		}
	}

	public void unlock(String sessionId) {
		ThreadLocal tl = null;

		synchronized (_sessionMap) {
			tl = (ThreadLocal)_sessionMap.get(sessionId);

			// The variable can be null at this time if (1) a method called
			// unlock() twice or (2) cleanUp() was called.

			if (tl != null) {
				Long count = (Long)tl.get();

				if (count.longValue() == 0L) {
					// All reentrant calls have completed

					_sessionMap.remove(sessionId);
				}
				else {
					// Finished one of the reentrant calls

					count = new Long(count.longValue() - 1L);

					tl.set(count);
				}
			}
		}
	}

	public void cleanUp(HttpSession ses) {
		try {
			MailUtil.cleanUp(ses);
		}
		catch (Exception ex) {
		}

		synchronized (_sessionMap) {
			_sessionMap.remove(ses.getId());
		}
	}

	private MailSessionLock() {
	}

	private static MailSessionLock _lock = new MailSessionLock();

	private Map _sessionMap = new HashMap();

}