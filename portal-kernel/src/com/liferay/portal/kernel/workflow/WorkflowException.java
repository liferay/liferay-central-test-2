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

import com.liferay.portal.PortalException;

/**
 * <a href="WorkflowException.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The workflow exception is the main exception to be thrown by any of the
 * workflow system API methods.
 * </p>
 *
 * <p>
 * A workflow exception might contain a native exception being thrown by the
 * underlying workflow engine, if the transport layer allows it otherwise it
 * will just contain its message.
 * </p>
 *
 * @author Micha Kiener
 */
public class WorkflowException extends PortalException {

	/**
	 * Default, empty constructor for a new workflow exception without any
	 * further information.
	 */
	public WorkflowException() {
		super();
	}

	/**
	 * This constructor just takes a message as the exception descriptor and
	 * does not include an original cause.
	 *
	 * @param msg the message for the exception
	 */
	public WorkflowException(String msg) {
		super(msg);
	}

	/**
	 * This constructor, taking the original cause and an additional message,
	 * should only be used, if the engine lives in the same JVM as the client or
	 * if the class of the cause is serializable and available within the client
	 * system.
	 *
	 * @param msg the additional message for the exception
	 * @param cause the original cause for this exception
	 */
	public WorkflowException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * This constructor, taking the original cause, should only be used, if the
	 * engine lives in the same JVM as the client or if the class of the cause
	 * is serializable and available within the client system.
	 *
	 * @param cause the original cause for this exception
	 */
	public WorkflowException(Throwable cause) {
		super(cause);
	}

}