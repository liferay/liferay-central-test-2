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

import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.workflow.TaskInstanceInfo;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTestBase;
import com.liferay.portal.kernel.workflow.request.TaskInstanceRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * <a href="TaskInstanceManagerProxyTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class TaskInstanceManagerProxyTest extends WorkflowTestBase{

	@Test
	public void assignTaskInstanceToRole() throws Exception {
		//Correct
		long taskInstanceId = 1;
		long roleId = 1;
		String comment = "comment";
		final long callingUserId = 1;
		ignored(4, callingUserId);
		Map<String, Object> attributes = new HashMap<String, Object>();
		TaskInstanceInfo info = context.mock(TaskInstanceInfo.class);
		final WorkflowResultContainer<TaskInstanceInfo> correctResult =
			new WorkflowResultContainer<TaskInstanceInfo>(info);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createAssignTaskInstanceToRoleRequest(
			taskInstanceId, roleId, comment, attributes, callingUserId);

		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			taskInstanceManagerProxy.assignTaskInstanceToRole(
			taskInstanceId, roleId, comment, attributes, callingUserId));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.assignTaskInstanceToRole(
				taskInstanceId, roleId, comment, attributes, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.assignTaskInstanceToRole(
				taskInstanceId, roleId, comment, attributes, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void assignTaskInstanceToUser() throws Exception {
		//Correct
		long taskInstanceId = 1;
		UserCredential userCredential=new UserCredential();
		String comment = "comment";
		Map<String, Object> attributes = new HashMap<String, Object>();
		long callingUserId = 1;
		ignored(4, callingUserId);
		TaskInstanceInfo info = context.mock(TaskInstanceInfo.class);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createAssignTaskInstanceToUserRequest(
			taskInstanceId, userCredential, comment, attributes, callingUserId);
		final WorkflowResultContainer<TaskInstanceInfo> correctResult =
			new WorkflowResultContainer<TaskInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			taskInstanceManagerProxy.assignTaskInstanceToUser(
			taskInstanceId,userCredential, comment, attributes, callingUserId));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.assignTaskInstanceToUser(
				taskInstanceId, userCredential,comment, attributes,
				callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.assignTaskInstanceToUser(
				taskInstanceId, userCredential,comment, attributes,
				callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void completeTaskInstance() throws Exception {
		//Correct
		long taskInstanceId = 1;
		long callingUserId = 1;
		ignored(4, callingUserId);
		String comment = "comment";
		Map<String, Object> attributes = new HashMap<String, Object>();
		TaskInstanceInfo info = context.mock(TaskInstanceInfo.class);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createCompleteTaskInstanceRequest(
			taskInstanceId, callingUserId, comment, attributes);
		final WorkflowResultContainer<TaskInstanceInfo> correctResult =
			new WorkflowResultContainer<TaskInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			taskInstanceManagerProxy.completeTaskInstance(
			taskInstanceId, callingUserId, comment, attributes));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.completeTaskInstance(
				taskInstanceId, callingUserId, comment, attributes);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.completeTaskInstance(
				taskInstanceId, callingUserId, comment, attributes);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByRole_long() throws Exception {
		//Correct
		long roleId = 1;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);
		ignored(4, 0);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByRoleRequest(roleId);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList,
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(roleId));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(roleId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(roleId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByRole_long_boolean() throws Exception {
		//Correct
		long roleId = 1;
		boolean completed = false;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);
		ignored(4, 0);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByRoleRequest(
			roleId, completed);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList,
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(
				roleId, completed));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(
				roleId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByRole(
				roleId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByUser_long() throws Exception {
		//Correct
		long userId = 1;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);

		ignored(4, 0);

		final TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByUserRequest(userId);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList, taskInstanceManagerProxy.getTaskInstanceInfosByUser(
				userId));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByUser(userId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByUser(userId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByUser_long_boolean() throws Exception {
		//Correct
		long userId = 1;
		boolean completed = false;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);
		ignored(4, 0);
		final TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByUserRequest(
			userId, completed);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList,
			taskInstanceManagerProxy.getTaskInstanceInfosByUser(
				userId, completed));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByUser(
				userId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByUser(
				userId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByWorkflowInstance_long() throws Exception {
		//Correct
		long workflowInstanceId = 1;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);
		ignored(4, 0);
		final TaskInstanceRequest request =
			TaskInstanceRequest.
			createGetTaskInstanceInfosByWorkflowInstanceRequest(
				workflowInstanceId);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList,
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
				workflowInstanceId));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
				workflowInstanceId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
				workflowInstanceId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getTaskInstanceInfosByWorkflowInstance_long_boolean() throws
		Exception {
		//Correct
		long workflowInstanceId = 1;
		boolean completed = false;
		TaskInstanceInfo info1 = context.mock(TaskInstanceInfo.class, "info1");
		TaskInstanceInfo info2 = context.mock(TaskInstanceInfo.class, "info2");
		List<TaskInstanceInfo> infoList = new ArrayList<TaskInstanceInfo>();
		infoList.add(info1);
		infoList.add(info2);
		ignored(4, 0);
		final TaskInstanceRequest request =
			TaskInstanceRequest.
			createGetTaskInstanceInfosByWorkflowInstanceRequest(
				workflowInstanceId, completed);
		final WorkflowResultContainer<List<TaskInstanceInfo>> correctResult =
			new WorkflowResultContainer<List<TaskInstanceInfo>>(infoList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infoList,
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
			workflowInstanceId, completed));

		//Engine error
		final WorkflowResultContainer<TaskInstanceInfo> errorResult =
			new WorkflowResultContainer<TaskInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
				workflowInstanceId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		errorFlag = false;
		try {
			taskInstanceManagerProxy.getTaskInstanceInfosByWorkflowInstance(
				workflowInstanceId, completed);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

}