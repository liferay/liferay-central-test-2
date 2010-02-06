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

import java.util.concurrent.ThreadFactory;

/**
 * <a href="NamedThreadFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class NamedThreadFactory implements ThreadFactory {

	public NamedThreadFactory(String name, int priority) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			_group = securityManager.getThreadGroup();
		}
		else {
			Thread currentThread = Thread.currentThread();

			_group = currentThread.getThreadGroup();
		}

		_name = name;
		_priority = priority;
	}

	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(_group, runnable, _name);

		thread.setDaemon(true);
		thread.setPriority(_priority);

		return thread;
	}

	private ThreadGroup _group;
	private String _name;
	private int _priority;

}