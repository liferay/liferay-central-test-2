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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.bi.rules.basicrulesengine.messaging;

import com.liferay.portal.kernel.bi.rules.ExecutionRequestMessage;
import com.liferay.portal.kernel.bi.rules.RulesEngine;
import com.liferay.portal.kernel.bi.rules.RulesEngineException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.sender.MessageSender;

import java.util.List;

/**
 * <a href="ExecutionRequestMessageListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Prashant Dighe
 * @author Michael C. Han
 */
public class ExecutionRequestMessageListener implements MessageListener {

	public ExecutionRequestMessageListener(
		ExecutionRequestMessageAcceptor executionRequestMessageAcceptor,
		MessageSender messageSender, RulesEngine engine){

		_rulesEngine = engine;
		_executionRequestMessageAcceptor = executionRequestMessageAcceptor;
		_messageSender = messageSender;
	}

	public void receive(Message requestMessage) {
		ExecutionRequestMessage executionRequestMessage =
			(ExecutionRequestMessage) requestMessage.getPayload();

		if (!_executionRequestMessageAcceptor.accept(executionRequestMessage)) {
			return;
		}

		try {
			doReceive(requestMessage, executionRequestMessage);
		}
		catch (Exception e) {
			_log.error(
				"Unable to process rule engine execution request", e);
		}
	}

	protected void doReceive(
		Message requestMessage, ExecutionRequestMessage ruleExecutionRequest)
		throws Exception {

		try {
			boolean synchronousRequest =
				(ruleExecutionRequest.getQuery() != null);

			Message replyMessage = null;

			if (synchronousRequest) {
				replyMessage =
					MessageBusUtil.createResponseMessage(requestMessage);
			}

			List results = _rulesEngine.execute(
				ruleExecutionRequest.getDomainName(),
				ruleExecutionRequest.getFacts(),
				ruleExecutionRequest.getQuery());

			if (synchronousRequest) {
				replyMessage.setPayload(results);

				_messageSender.send(
					replyMessage.getDestinationName(), replyMessage);
			}
		}
		catch (RulesEngineException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to execute rule", e);
			}
		}
	}

	private ExecutionRequestMessageAcceptor _executionRequestMessageAcceptor;

	private static final Log _log =
		LogFactoryUtil.getLog(ExecutionRequestMessageListener.class);

	private MessageSender _messageSender;

	private RulesEngine _rulesEngine;

}