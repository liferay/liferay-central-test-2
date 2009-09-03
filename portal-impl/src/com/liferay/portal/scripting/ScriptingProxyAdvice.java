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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ScriptingProxyAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ScriptingProxyAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {

		ScriptingRequest request = new ScriptingRequest(invocation);
		Message message = new Message();
		message.setPayload(request);
		//Do not bridge scripting requests with return values
		try {
			if (request.hasReturnValue()) {
				ScriptingResultContainer response =
					(ScriptingResultContainer)
						MessageBusUtil.sendSynchronousMessage(
							DestinationNames.SCRIPTING, message);
				if (response.hasError()) {
					throw response.getException();
				}
				else {
					return response.getResult();
				}
			}
			else {
				MessageBusUtil.sendMessage(DestinationNames.SCRIPTING, message);
				return null;
			}
		}
		catch (MessageBusException ex) {
			throw new ScriptingException(
				"Unable to invoke scripting method", ex);
		}
	}

}