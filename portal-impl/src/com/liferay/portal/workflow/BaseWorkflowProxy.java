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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;

/**
 * <a href="BaseWorkflowProxy.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The abstract base class for a workflow proxy implementing one of the manager
 * interfaces and using the event bus to serialize the invocation to the target
 * implementation. It will use Spring to instrument the proxy class by weaving
 * the {@link ManagerProxyAdvice} to all methods being specified by the manager
 * interface.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public abstract class BaseWorkflowProxy {

	public BaseWorkflowProxy(
		SingleDestinationSynchronousMessageSender synchronousMessageSender) {
		_synchronousMessageSender = synchronousMessageSender;
	}
	
	/**
	 * Declared final to not let Spring intercept this method.
	 * 
	 * @return the message sender used within the proxy
	 */
	public final SingleDestinationSynchronousMessageSender getMessageSender() {
		return _synchronousMessageSender;
	}

	private final SingleDestinationSynchronousMessageSender _synchronousMessageSender;
}
