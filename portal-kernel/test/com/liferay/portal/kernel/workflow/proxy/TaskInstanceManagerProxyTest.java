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
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.TaskInstanceRequest;
import com.liferay.portal.kernel.workflow.TaskInstanceRequestType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <a href="TaskInstanceManagerProxyTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceManagerProxyTest extends WorkflowManagerProxyTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_proxy = new TaskInstanceManagerProxy(taskSynchronousMessageSender);
	}

	public void testAssign() throws Exception {

		final long testTaskInstanceId = 101;
		final long testUserId = 102;
		final Date testCreateDate = new Date();
		final String testDescription = "testDescription";
		final Map<String, Object> contextInfo = new HashMap<String, Object>();
		final String testKey = "testKey";
		final String testValue = "testValue";
		contextInfo.put(testKey, testValue);

		MessageListener assignListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.ASSIGN, request.getType());
				assertEquals(testTaskInstanceId, request.getTaskInstanceId());
				assertEquals(testUserId, request.getUserId());
				assertEquals(testDescription, request.getDescription());
				assertEquals(testValue, request.getContextInfo().get(testKey));
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					TaskInstanceInfo instance =
						new TaskInstanceInfo(
							testTaskInstanceId, testUserId, testCreateDate);
					instance.setDescription(testDescription);
					instance.setContextInfo(contextInfo);
					message.setPayload(instance);
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};

		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, assignListener);
		TaskInstanceInfo instance =
			_proxy.assignTaskInstance(
				testTaskInstanceId, testUserId, testDescription, contextInfo);
		assertNotNull(instance);
		assertEquals(testTaskInstanceId, instance.getTaskInstanceId());
		assertEquals(testUserId, instance.getUserId());
		assertEquals(testDescription, instance.getDescription());
		assertEquals(testValue, instance.getContextInfo().get(testKey));
	}

	public void testFulfil() throws Exception {

		final long testTaskInstanceId = 101;
		final long testUserId = 102;
		final Date testCreateDate = new Date();
		final String testDescription = "testDescription";
		final Map<String, Object> contextInfo = new HashMap<String, Object>();
		final String testKey = "testKey";
		final String testValue = "testValue";
		contextInfo.put(testKey, testValue);

		MessageListener fulfilListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.FULFIL, request.getType());
				assertEquals(testTaskInstanceId, request.getTaskInstanceId());
				assertEquals(testUserId, request.getUserId());
				assertEquals(testDescription, request.getDescription());
				assertEquals(testValue, request.getContextInfo().get(testKey));
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					TaskInstanceInfo instance =
						new TaskInstanceInfo(
							testTaskInstanceId, testUserId, testCreateDate);
					instance.setDescription(testDescription);
					instance.setContextInfo(contextInfo);
					message.setPayload(instance);
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};

		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, fulfilListener);
		TaskInstanceInfo instance =
			_proxy.fulfillTaskInstance(
			testTaskInstanceId, testUserId, testDescription, contextInfo);
		assertNotNull(instance);
		assertEquals(testTaskInstanceId, instance.getTaskInstanceId());
		assertEquals(testUserId, instance.getUserId());
		assertEquals(testDescription, instance.getDescription());
		assertEquals(testValue, instance.getContextInfo().get(testKey));
	}

	public void testGetByProcessInstanceId1() throws Exception {
		final long testProcessInstanceId = 101;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				assertTrue(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByProcessInstanceId(
				testProcessInstanceId);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByProcessInstanceId2() throws Exception {
		final long testProcessInstanceId = 101;
		final boolean finished = true;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(
					testProcessInstanceId, request.getProcessInstanceId());
				assertEquals(finished, request.isFinished());
				assertFalse(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByProcessInstanceId(
				testProcessInstanceId, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByRole1() throws Exception {
		final long testRoleId = 101;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testRoleId, request.getRoleId());
				assertTrue(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByRole(testRoleId);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByRole2() throws Exception {
		final long testRoleId = 101;
		final boolean finished = true;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testRoleId, request.getRoleId());
				assertEquals(finished, request.isFinished());
				assertFalse(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByRole(testRoleId, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByRoles1() throws Exception {
		final Set<Long> testRoleIds = new HashSet<Long>();
		final long testRoleId1 = 101;
		final long testRoleId2 = 102;
		testRoleIds.add(testRoleId1);
		testRoleIds.add(testRoleId2);
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				Set<Long> roleIds = request.getRoleIds();
				assertEquals(2, roleIds.size());
				assertTrue(roleIds.contains(testRoleId1));
				assertTrue(roleIds.contains(testRoleId2));
				assertTrue(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByRoles(testRoleIds);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByRoles2() throws Exception {
		final Set<Long> testRoleIds = new HashSet<Long>();
		final long testRoleId1 = 101;
		final long testRoleId2 = 102;
		final boolean finished = true;
		testRoleIds.add(testRoleId1);
		testRoleIds.add(testRoleId2);
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				Set<Long> roleIds = request.getRoleIds();
				assertEquals(2, roleIds.size());
				assertTrue(roleIds.contains(testRoleId1));
				assertTrue(roleIds.contains(testRoleId2));
				assertEquals(finished, request.isFinished());
				assertFalse(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByRoles(testRoleIds, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByTokenId1() throws Exception {
		final long testTokenId = 101;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testTokenId, request.getTokenId());
				assertTrue(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByTokenId(testTokenId);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByTokenId2() throws Exception {
		final long testTokenId = 101;
		final boolean finished = true;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testTokenId, request.getTokenId());
				assertEquals(finished, request.isFinished());
				assertFalse(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByTokenId(testTokenId, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByUserId1() throws Exception {
		final long testUserId = 101;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testUserId, request.getUserId());
				assertTrue(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByUserId(testUserId);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	public void testGetByUserId2() throws Exception {
		final long testUserId = 101;
		final boolean finished = true;
		MessageListener getListener = new MessageListener() {

			public void receive(Message message) {
				TaskInstanceRequest request =
					(TaskInstanceRequest) message.getPayload();
				assertNotNull(request);
				assertEquals(TaskInstanceRequestType.GET, request.getType());
				assertEquals(testUserId, request.getUserId());
				assertEquals(finished, request.isFinished());
				assertFalse(request.isGetAll());
				String responseDestination = message.getResponseDestination();
				if (responseDestination != null) {
					message.setPayload(new ArrayList<TaskInstanceInfo>());
					messageBus.sendMessage(responseDestination, message);
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_TASK, getListener);
		List<TaskInstanceInfo> instances =
			_proxy.getTaskInstanceInfosByUserId(testUserId, finished);
		assertNotNull(instances);
		assertEquals(0, instances.size());
	}

	private TaskInstanceManagerProxy _proxy;

}