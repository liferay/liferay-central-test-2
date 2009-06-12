/*
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
import com.liferay.portal.kernel.bi.rules.ExecutionRequestType;
import com.liferay.portal.kernel.bi.rules.Query;
import com.liferay.portal.kernel.bi.rules.RuleEngine;
import com.liferay.portal.kernel.bi.rules.RuleEngineException;
import com.liferay.portal.kernel.bi.rules.RuleRetriever;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;

import java.util.List;

/**
 * <a href="RuleEngineProxy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RuleEngineProxy implements RuleEngine {

	public RuleEngineProxy(
		SingleDestinationMessageSender ruleEngineAdminMessageSender,
		SingleDestinationMessageSender asyncExecutionSender,
		SingleDestinationSynchronousMessageSender ruleEngineExecutionSender) {

		_ruleEngineAdminMessageSender = ruleEngineAdminMessageSender;
		_asyncExecutionSender = asyncExecutionSender;
		_ruleEngineExecutionSender = ruleEngineExecutionSender;
	}

	public void add(String domainName, RuleRetriever ruleRtriever)
		throws RuleEngineException {

		_sendAdminMessage(AdminRequestType.ADD, domainName, ruleRtriever);
	}

	public void execute(RuleRetriever ruleRtriever, List<?> facts)
		throws RuleEngineException {

		ExecutionRequestMessage requestMessage =
			new ExecutionRequestMessage(
				ExecutionRequestType.EXECUTE, ruleRtriever);

		requestMessage.addFact(facts);

		Message message = new Message();
		message.setPayload(requestMessage);
		_asyncExecutionSender.send(message);
	}

	public List<?> execute(
		RuleRetriever ruleRtriever, List<?> facts, String query)
		throws RuleEngineException {

		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE, ruleRtriever, facts, query);
	}

	public List<?> execute(
		RuleRetriever ruleRtriever, List<?> facts, String query,
		Object[] queryArguments)
		throws RuleEngineException {

		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE, ruleRtriever, facts,
			query, queryArguments);
	}

	public void execute(String domainName, List<?> facts)
		throws RuleEngineException {

		ExecutionRequestMessage requestMessage =
			new ExecutionRequestMessage(
				ExecutionRequestType.EXECUTE, domainName);

		requestMessage.addFact(facts);

		Message message = new Message();
		message.setPayload(requestMessage);
		_asyncExecutionSender.send(message);
	}

	public List<?> execute(String domainName, List<?> facts, String query)
		throws RuleEngineException {
		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE, domainName, facts, query);
	}

	public List<?> execute(
		String domainName, List<?> facts, String query, Object[] queryArguments)
		throws RuleEngineException {

		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE, domainName, facts,
			query, queryArguments);
	}

	public List<?> executeWithResults(RuleRetriever ruleRtriever, List<?> facts)
		throws RuleEngineException {

		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE_WITH_RESULTS, ruleRtriever, facts);
	}

	public List<?> executeWithResults(String domainName, List<?> facts)
		throws RuleEngineException {
		
		return _sendExecutionMessage(
			ExecutionRequestType.EXECUTE_WITH_RESULTS, domainName, facts);
	}

	public void remove(String domainName) throws RuleEngineException {
		_sendAdminMessage(AdminRequestType.REMOVE, domainName, null);
	}

	public void update(String domainName, RuleRetriever ruleRetriever)
		throws RuleEngineException {
		_sendAdminMessage(AdminRequestType.UPDATE, domainName, ruleRetriever);
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType,
		RuleRetriever ruleRtriever, List<?> facts)
		throws RuleEngineException {

		return _sendExecutionMessage(
			requestType, ruleRtriever, facts, null, null);
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType,
		RuleRetriever ruleRtriever, List<?> facts, String query)
		throws RuleEngineException {

		return _sendExecutionMessage(
			requestType, ruleRtriever, facts, query, null);
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType, RuleRetriever ruleRtriever,
		List<?> facts, String query, Object[] queryArguments)
		throws RuleEngineException {

		ExecutionRequestMessage requestMessage =
			new ExecutionRequestMessage(requestType, ruleRtriever);

		requestMessage.addFact(facts);

		if (query != null) {
			Query rulesQuery = new Query();
			rulesQuery.setQueryString(query);
			rulesQuery.addArguments(queryArguments);
		}

		Message message = new Message();
		message.setPayload(requestMessage);
		try {
			return (List<?>) _ruleEngineExecutionSender.sendMessage(message);
		}
		catch (MessageBusException e) {
			throw new RuleEngineException("Unable to execute rules", e);
		}
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType,
		String ruleDomain, List<?> facts)
		throws RuleEngineException {

		return _sendExecutionMessage(
			requestType, ruleDomain, facts, null, null);
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType,
		String ruleDomain, List<?> facts, String query)
		throws RuleEngineException {

		return _sendExecutionMessage(
			requestType, ruleDomain, facts, query, null);
	}

	private List<?> _sendExecutionMessage(
		ExecutionRequestType requestType, String ruleDomain, List<?> facts,
		String query, Object[] queryArguments)
		throws RuleEngineException {

		ExecutionRequestMessage requestMessage =
			new ExecutionRequestMessage(requestType, ruleDomain);
		requestMessage.addFact(facts);

		if (query != null) {
			Query rulesQuery = new Query();
			rulesQuery.setQueryString(query);
			rulesQuery.addArguments(queryArguments);
		}

		Message message = new Message();
		message.setPayload(requestMessage);
		try {
			return (List<?>) _ruleEngineExecutionSender.sendMessage(message);
		}
		catch (MessageBusException e) {
			throw new RuleEngineException("Unable to execute rules", e);
		}
	}

	private void _sendAdminMessage(
		AdminRequestType requestType, String domainName,
		RuleRetriever ruleRtriever) {

		AdminRequestMessage adminRequestMessage = null;
		if (AdminRequestType.ADD.equals(requestType)) {
			adminRequestMessage =
				AdminRequestMessage.add(domainName, ruleRtriever);
		}
		else if (AdminRequestType.REMOVE.equals(requestType)) {
			adminRequestMessage =
				AdminRequestMessage.remove(domainName);
		}
		else if (AdminRequestType.UPDATE.equals(requestType)) {
			adminRequestMessage =
				AdminRequestMessage.update(domainName, ruleRtriever);
		}

		Message message = new Message();
		message.setPayload(adminRequestMessage);

		_ruleEngineAdminMessageSender.send(message);
	}

	private SingleDestinationMessageSender _ruleEngineAdminMessageSender;
	private SingleDestinationSynchronousMessageSender
		_ruleEngineExecutionSender;
	private SingleDestinationMessageSender _asyncExecutionSender;
}
