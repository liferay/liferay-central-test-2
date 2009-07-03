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

package com.liferay.portal.kernel.bi.rules.proxy;

import com.liferay.portal.kernel.bi.rules.AdminRequestMessage;
import com.liferay.portal.kernel.bi.rules.AdminRequestType;
import com.liferay.portal.kernel.bi.rules.ExecutionRequestMessage;
import com.liferay.portal.kernel.bi.rules.Query;
import com.liferay.portal.kernel.bi.rules.RuleRetriever;
import com.liferay.portal.kernel.bi.rules.RulesEngine;
import com.liferay.portal.kernel.bi.rules.RulesEngineException;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;

import java.util.List;

/**
 * <a href="RulesEngineProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class RulesEngineProxy implements RulesEngine {

	public RulesEngineProxy(
		SingleDestinationMessageSender ruleEngineAdminMessageSender,
		SingleDestinationMessageSender asyncExecutionSender,
		SingleDestinationSynchronousMessageSender ruleEngineExecutionSender) {

		_adminRequestMessageSender = ruleEngineAdminMessageSender;
		_executionRequestMessageSender = asyncExecutionSender;
		_synchronousExecutionRequestMessageSender = ruleEngineExecutionSender;
	}

	public void add(String domainName, RuleRetriever ruleRetriever) {
		_sendAdminMessage(AdminRequestType.ADD, domainName, ruleRetriever);
	}

	public void execute(RuleRetriever ruleRetriever, List<?> facts) {
		ExecutionRequestMessage executionRequestMessage =
			new ExecutionRequestMessage(ruleRetriever);

		executionRequestMessage.addFact(facts);

		_executionRequestMessageSender.send(executionRequestMessage);
	}

	public List<?> execute(
			RuleRetriever ruleRetriever, List<?> facts, Query query)
		throws RulesEngineException {

		ExecutionRequestMessage executionRequestMessage =
			new ExecutionRequestMessage(ruleRetriever);

		return _sendExecutionMessage(executionRequestMessage, facts, query);
	}

	public void execute(String domainName, List<?> facts) {
		ExecutionRequestMessage executionRequestMessage =
			new ExecutionRequestMessage(domainName);

		executionRequestMessage.addFact(facts);

		_executionRequestMessageSender.send(executionRequestMessage);
	}

	public List<?> execute(String domainName, List<?> facts, Query query)
		throws RulesEngineException {

		ExecutionRequestMessage executionRequestMessage =
			new ExecutionRequestMessage(domainName);

		return _sendExecutionMessage(executionRequestMessage, facts, query);
	}

	public void remove(String domainName) {
		_sendAdminMessage(AdminRequestType.REMOVE, domainName, null);
	}

	public void update(String domainName, RuleRetriever ruleRetriever) {
		_sendAdminMessage(AdminRequestType.UPDATE, domainName, ruleRetriever);
	}

	private void _sendAdminMessage(
		AdminRequestType adminRequestType, String domainName,
		RuleRetriever ruleRetriever) {

		AdminRequestMessage adminRequestMessage = null;

		if (AdminRequestType.ADD.equals(adminRequestType)) {
			adminRequestMessage = AdminRequestMessage.add(
				domainName, ruleRetriever);
		}
		else if (AdminRequestType.REMOVE.equals(adminRequestType)) {
			adminRequestMessage = AdminRequestMessage.remove(domainName);
		}
		else if (AdminRequestType.UPDATE.equals(adminRequestType)) {
			adminRequestMessage = AdminRequestMessage.update(
				domainName, ruleRetriever);
		}

		_adminRequestMessageSender.send(adminRequestMessage);
	}

	private List<?> _sendExecutionMessage(
			ExecutionRequestMessage executionRequestMessage,
			List<?> facts, Query query)
		throws RulesEngineException {

		executionRequestMessage.addFact(facts);
		executionRequestMessage.setQuery(query);

		try {
			return (List<?>)_synchronousExecutionRequestMessageSender.send(
				executionRequestMessage);
		}
		catch (MessageBusException mbe) {
			throw new RulesEngineException("Unable to execute rules", mbe);
		}
	}

	private SingleDestinationMessageSender _adminRequestMessageSender;
	private SingleDestinationMessageSender _executionRequestMessageSender;
	private SingleDestinationSynchronousMessageSender
		_synchronousExecutionRequestMessageSender;

}