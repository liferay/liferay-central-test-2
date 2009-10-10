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

package com.liferay.portal.kernel.workflow;

/**
 * <a href="UserCredentialThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The {@link ThreadLocal} to be used to attach the current {@link
 * UserCredential} to the thread before invoking a proxied method. If using it,
 * make sure the the credential being attached to the thread is going to be
 * removed after the method invocation in a finally block as it is dangerous, if
 * the credential would stick on the thread and could lead into unpredictable
 * behavior.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Micha Kiener
 */
public class UserCredentialThreadLocal {

	public static UserCredential getUserCredential() {
		return _threadLocal.get();
	}

	public static void setUserCredential(UserCredential userCredential) {
		_threadLocal.set(userCredential);
	}

	private static ThreadLocal<UserCredential> _threadLocal =
		new ThreadLocal<UserCredential>();

}