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

package com.liferay.portal.kernel.workflow.proxy;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.workflow.ProcessInstanceHistory;
import com.liferay.portal.kernel.workflow.ProcessInstanceInfo;
import com.liferay.portal.kernel.workflow.ProcessInstanceRequest;
import com.liferay.portal.kernel.workflow.ProcessInstanceRequestType;
import com.liferay.portal.kernel.workflow.TokenInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="ProcessInstanceManagerProxyTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class ProcessInstanceManagerProxyTest
	extends WorkflowManagerProxyTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_proxy = new ProcessInstanceManagerProxy(
			instanceMessageSender, instanceSynchronousMessageSender);
	}

	public void testGet1() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(ProcessInstanceRequestType.GET, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		ProcessInstanceInfo instance =
			_proxy.getProcessInstanceInfo(testProcessInstanceId);
		assertNotNull(instance);
		assertEquals(testProcessInstanceId, instance.getProcessInstanceId());
	}

	public void testGet2() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final boolean retrieveTokenInfo = true;
		final long testTokenId = 102;
		final String testTokenName = "testTokenName";
		final String testNodeName = "testNodeName";
		final Date testStartDate = new Date();
		final Date testEndDate = new Date();

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(ProcessInstanceRequestType.GET, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				assertEquals(
					retrieveTokenInfo, request.isRetrieveTokenInfo());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					TokenInfo tokenInfo =
						new TokenInfo(
						testTokenId, testTokenName, testNodeName,
						testStartDate, testEndDate);
					instance.setTokenInfo(tokenInfo);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		ProcessInstanceInfo instance =
			_proxy.getProcessInstanceInfo(
			testProcessInstanceId, retrieveTokenInfo);
		assertNotNull(instance);
		assertEquals(testProcessInstanceId, instance.getProcessInstanceId());
		TokenInfo tokenInfo = instance.getTokenInfo();
		assertNotNull(tokenInfo);
		assertEquals(testTokenId, tokenInfo.getTokenId());
		assertEquals(testTokenName, tokenInfo.getTokenName());
		assertEquals(testNodeName, tokenInfo.getNodeName());
		assertEquals(testStartDate, tokenInfo.getStartDate());
	}

	public void testGet3() throws Exception {

		final String testName = "testName";

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(ProcessInstanceRequestType.GET, request.getType());
				assertEquals(testName, request.getWorkflowDefinitionName());
				assertTrue(request.isGetAll());
				assertFalse(request.isRetrieveTokenInfo());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					List<ProcessInstanceInfo> instances =
						new ArrayList<ProcessInstanceInfo>();
					message.setPayload(
						new WorkflowResultContainer<List<ProcessInstanceInfo>>(
						instances));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		List<ProcessInstanceInfo> instances =
			_proxy.getProcessInstanceInfos(testName);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGet4() throws Exception {

		final String testName = "testName";
		final boolean retrieveTokenInfo = true;

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.GET, request.getType());
				assertEquals(testName, request.getWorkflowDefinitionName());

				assertTrue(request.isGetAll());
				assertTrue(request.isRetrieveTokenInfo());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					List<ProcessInstanceInfo> instances =
						new ArrayList<ProcessInstanceInfo>();
					message.setPayload(
						new WorkflowResultContainer<List<ProcessInstanceInfo>>(
						instances));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		List<ProcessInstanceInfo> instances =
			_proxy.getProcessInstanceInfos(testName, retrieveTokenInfo);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGet5() throws Exception {

		final String testName = "testName";
		final boolean retrieveTokenInfo = true;
		final boolean finished = true;

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(ProcessInstanceRequestType.GET, request.getType());
				assertEquals(testName, request.getWorkflowDefinitionName());

				assertFalse(request.isGetAll());
				assertEquals(finished, request.isFinished());
				assertTrue(request.isRetrieveTokenInfo());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					List<ProcessInstanceInfo> instances =
						new ArrayList<ProcessInstanceInfo>();
					message.setPayload(
						new WorkflowResultContainer<List<ProcessInstanceInfo>>(
						instances));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		List<ProcessInstanceInfo> instances =
			_proxy.getProcessInstanceInfos(
			testName, retrieveTokenInfo, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetHistory() throws Exception {

		final long testProcessInstanceId = 101;

		MessageListener getHistoryListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.GET_HISTORY,
					request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceHistory history =
						new ProcessInstanceHistory(testProcessInstanceId);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceHistory>(
						history));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getHistoryListener);

		ProcessInstanceHistory history =
			_proxy.getProcessInstanceHistory(testProcessInstanceId);
		assertNotNull(history);
	}

	public void testRemove() throws Exception {
		final long testProcessInstanceId = 101;
		MessageListener removeListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.REMOVE, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, removeListener);

		_proxy.removeProcessInstance(testProcessInstanceId);
	}

	public void testSignalByPI1() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.SIGNAL, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		ProcessInstanceInfo instance =
			_proxy.signalProcessInstanceById(testProcessInstanceId, null);
		assertNotNull(instance);
		assertEquals(testProcessInstanceId, instance.getProcessInstanceId());
	}

	public void testSignalByPI2() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final String testActivityName = "testActivityName";

		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertTrue(request instanceof ProcessInstanceRequest);
				assertEquals(
					ProcessInstanceRequestType.SIGNAL, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				assertEquals(testActivityName, request.getActivityName());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, getListener);

		ProcessInstanceInfo instance =
			_proxy.signalProcessInstanceById(
			testProcessInstanceId, testActivityName, null);
		assertNotNull(instance);
		assertEquals(testProcessInstanceId, instance.getProcessInstanceId());
	}

	public void testSignalByTI1() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final long testTokenId = 101;
		final String testTokenName = "testTokenName";
		final String testNodeName = "testNodeName";
		final Date testStartDate = new Date();
		final Date testEndDate = new Date();

		MessageListener signalListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.SIGNAL, request.getType());
				assertEquals(testTokenId, request.getTokenId());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					TokenInfo tokenInfo =
						new TokenInfo(
						testTokenId, testTokenName, testNodeName,
						testStartDate, testEndDate);
					instance.setTokenInfo(tokenInfo);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, signalListener);

		ProcessInstanceInfo instance =
			_proxy.signalProcessInstanceByTokenId(testTokenId, null);
		assertNotNull(instance);
		TokenInfo tokenInfo = instance.getTokenInfo();
		assertNotNull(tokenInfo);
		assertEquals(testTokenId, tokenInfo.getTokenId());
	}

	public void testSignalByTI2() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final long testTokenId = 101;
		final String testTokenName = "testTokenName";
		final String testNodeName = "testNodeName";
		final Date testStartDate = new Date();
		final Date testEndDate = new Date();
		final String testActivityName = "testActivityName";

		MessageListener signalListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.SIGNAL, request.getType());
				assertEquals(testTokenId, request.getTokenId());
				assertEquals(testActivityName, request.getActivityName());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					TokenInfo tokenInfo =
						new TokenInfo(
						testTokenId, testTokenName, testNodeName,
						testStartDate, testEndDate);
					instance.setTokenInfo(tokenInfo);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, signalListener);

		ProcessInstanceInfo instance =
			_proxy.signalProcessInstanceByTokenId(
			testTokenId, testActivityName, null);
		assertNotNull(instance);
		TokenInfo tokenInfo = instance.getTokenInfo();
		assertNotNull(tokenInfo);
		assertEquals(testTokenId, tokenInfo.getTokenId());
	}

	public void testStart1() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final String testDefinitionName = "testDefinitionName";
		final Map<String, Object> contextInfo = new HashMap<String, Object>();
		final long testUserId = 101;
		final String testKey = "testKey";
		final String testValue = "testValue";
		contextInfo.put(testKey, testValue);

		MessageListener startListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.START, request.getType());
				assertEquals(
					testDefinitionName,
					request.getWorkflowDefinitionName());

				Map<String, Object> context = request.getContextInfo();
				assertNotNull(context);
				assertEquals(testValue, context.get(testKey));
				assertEquals(testUserId, request.getUserId());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};

		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, startListener);

		ProcessInstanceInfo instance =
			_proxy.startProcessInstance(
			testDefinitionName, contextInfo, testUserId);
		assertNotNull(instance);
	}

	public void testStart2() throws Exception {

		final long testProcessInstanceId = 101;
		final int testProcessInstanceVersion = 2;
		final String testDefinitionName = "testDefinitionName";
		final Map<String, Object> contextInfo = new HashMap<String, Object>();
		final long testUserId = 101;
		final String testKey = "testKey";
		final String testValue = "testValue";
		final String testActivityName = "testActivityName";
		contextInfo.put(testKey, testValue);

		MessageListener startListener = new MessageListener() {

			public void receive(Message message) {
				ProcessInstanceRequest request =
					(ProcessInstanceRequest) message.getPayload();
				assertEquals(
					ProcessInstanceRequestType.START, request.getType());
				assertEquals(
					testDefinitionName,
					request.getWorkflowDefinitionName());

				Map<String, Object> context = request.getContextInfo();
				assertNotNull(context);
				assertEquals(testValue, context.get(testKey));
				assertEquals(testUserId, request.getUserId());
				assertEquals(testActivityName, request.getActivityName());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					ProcessInstanceInfo instance =
						new ProcessInstanceInfo(
						testProcessInstanceId,
						testProcessInstanceVersion);
					message.setPayload(
						new WorkflowResultContainer<ProcessInstanceInfo>(
						instance));
					messageBus.sendMessage(responseDestination, message);
				}
			}

		};

		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_INSTANCE, startListener);

		ProcessInstanceInfo instance =
			_proxy.startProcessInstance(
			testDefinitionName, contextInfo, testUserId, testActivityName);
		assertNotNull(instance);
	}

	private ProcessInstanceManagerProxy _proxy;

}