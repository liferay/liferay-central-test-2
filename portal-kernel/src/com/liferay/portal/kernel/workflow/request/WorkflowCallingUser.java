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

package com.liferay.portal.kernel.workflow.request;

import com.liferay.portal.kernel.workflow.UserCredential;

/**
 * <a href="WorkflowCallingUser.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class represents the {@link UserCredential} attached to a {@link
 * ThreadLocal}, made available through {@link #getCallingUserCredential()}.
 * Most workflow API invocations provides the calling user however, some read
 * only methods do not, so the calling user might not be present for all
 * requests and thus the method {@link #getCallingUserCredential()} could return
 * <code>null</code>.
 * </p>
 *
 * @author Micha Kiener
 * @see    BaseRequest BaseRequest for more information about the transport
 *		   mechanism used for the credential
 */
public class WorkflowCallingUser {

	/**
	 * @return the calling user's credential, if available, <code>null</code>
	 *		   otherwise
	 */
	public static UserCredential getCallingUserCredential() {
		return _callingUser.get();
	}

	/**
	 * This method should only be used by the {@link BaseRequest} to set the
	 * calling user credential before invoking the method on the underlying
	 * workflow engine. <b>IMPORTANT</b> It has to make sure, to remove the
	 * credential in a finally after the method execution by invoking this
	 * method again by passing in <code>null</code>.
	 */
	public static void setCallingUserCredential(UserCredential userCredential) {

		_callingUser.set(userCredential);
	}

	/**
	 * The thread local holding the calling user's credential, if any.
	 */
	private static final ThreadLocal<UserCredential> _callingUser =
		new ThreadLocal<UserCredential>();

}