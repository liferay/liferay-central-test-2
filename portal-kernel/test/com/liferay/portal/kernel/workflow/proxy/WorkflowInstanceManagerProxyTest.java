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
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowTestBase;
import com.liferay.portal.kernel.workflow.request.WorkflowInstanceRequest;

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
 * <a href="WorkflowInstanceManagerProxyTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class WorkflowInstanceManagerProxyTest extends WorkflowTestBase{

	@Test
	public void addContextInformation() throws Exception {
		//Correct
		long workflowInstanceId = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		WorkflowInstanceInfo workflowInstanceInfo =
			context.mock(WorkflowInstanceInfo.class);
		ignored(4,0);

		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createAddContextInformationRequest(
			workflowInstanceId, variables);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(
			workflowInstanceInfo);

		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			workflowInstanceInfo,
			workflowInstanceManagerProxy.addContextInformation(
				workflowInstanceId, variables));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.addContextInformation(
				workflowInstanceId, variables);
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
			workflowInstanceManagerProxy.addContextInformation(
				workflowInstanceId, variables);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowInstanceInfo_long_boolean() throws Exception {
		//Correct
		long workflowInstanceId = 1;
		WorkflowInstanceInfo workflowInstanceInfo =
			context.mock(WorkflowInstanceInfo.class);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfoRequest(
			workflowInstanceId, false);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(
			workflowInstanceInfo);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}
		});
		assertSame(
			workflowInstanceInfo,
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				workflowInstanceId, false));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				workflowInstanceId, false);
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
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				workflowInstanceId, false);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowInstanceInfo_String_long_boolean() throws Exception {
		//Correct
		String relationType = "relationType";
		long relationId = 1;
		WorkflowInstanceInfo workflowInstanceInfo =
			context.mock(WorkflowInstanceInfo.class);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfoRequest(
			relationType, relationId, false);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(
			workflowInstanceInfo);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			workflowInstanceInfo,
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				relationType, relationId, false));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				relationType, relationId, false);
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
			workflowInstanceManagerProxy.getWorkflowInstanceInfo(
				relationType, relationId, false);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}
	@Test
	public void getWorkflowInstanceHistory() throws Exception {
		//Correct
		long workflowInstanceId = 1;
		boolean includeChildren = false;
		WorkflowInstanceHistory history1 =
			context.mock(WorkflowInstanceHistory.class, "history1");
		WorkflowInstanceHistory history2 =
			context.mock(WorkflowInstanceHistory.class, "history2");
		List<WorkflowInstanceHistory> histories =
			new ArrayList<WorkflowInstanceHistory>();
		histories.add(history1);
		histories.add(history2);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceHistoryRequest(
			workflowInstanceId, includeChildren);
		final WorkflowResultContainer<List<WorkflowInstanceHistory>>
			correctResult =
				new WorkflowResultContainer<List<WorkflowInstanceHistory>>(
					histories);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			histories,
			workflowInstanceManagerProxy.getWorkflowInstanceHistory(
			workflowInstanceId, includeChildren));

		//Engine error
		final WorkflowResultContainer<List<WorkflowInstanceHistory>>
			errorResult =
				new WorkflowResultContainer<List<WorkflowInstanceHistory>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceHistory(
				workflowInstanceId, includeChildren);
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
			workflowInstanceManagerProxy.getWorkflowInstanceHistory(
				workflowInstanceId, includeChildren);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowInstanceInfos_String_Integer_boolean()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		WorkflowInstanceInfo info1 =
			context.mock(WorkflowInstanceInfo.class, "info1");
		WorkflowInstanceInfo info2 =
			context.mock(WorkflowInstanceInfo.class, "info2");
		List<WorkflowInstanceInfo> infos =
			new ArrayList<WorkflowInstanceInfo>();
		infos.add(info1);
		infos.add(info2);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			workflowDefinitionName, workflowDefinitionVersion, false);
		final WorkflowResultContainer<List<WorkflowInstanceInfo>>
			correctResult =
				new WorkflowResultContainer<List<WorkflowInstanceInfo>>(infos);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infos,
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
			workflowDefinitionName, workflowDefinitionVersion, false));

		//Engine error
		final WorkflowResultContainer<List<WorkflowInstanceInfo>> errorResult =
			new WorkflowResultContainer<List<WorkflowInstanceInfo>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				workflowDefinitionName, workflowDefinitionVersion, false);
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
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				workflowDefinitionName, workflowDefinitionVersion, false);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowInstanceInfos_String_Integer_boolean_boolean()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		boolean finished = false;
		WorkflowInstanceInfo info1 =
			context.mock(WorkflowInstanceInfo.class, "info1");
		WorkflowInstanceInfo info2 =
			context.mock(WorkflowInstanceInfo.class, "info2");
		List<WorkflowInstanceInfo> infos =
			new ArrayList<WorkflowInstanceInfo>();
		infos.add(info1);
		infos.add(info2);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			workflowDefinitionName, workflowDefinitionVersion, finished,
			false);
		final WorkflowResultContainer<List<WorkflowInstanceInfo>>
			correctResult =
				new WorkflowResultContainer<List<WorkflowInstanceInfo>>(infos);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infos,
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
			workflowDefinitionName, workflowDefinitionVersion, finished,
			false));

		//Engine error
		final WorkflowResultContainer<List<WorkflowInstanceInfo>> errorResult =
			new WorkflowResultContainer<List<WorkflowInstanceInfo>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				workflowDefinitionName, workflowDefinitionVersion, finished,
				false);
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
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				workflowDefinitionName, workflowDefinitionVersion, finished,
				false);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowInstanceInfos_String_long_boolean() throws
		Exception {
		//Correct
		String relationType = "relationType";
		long relationId = 1;
		WorkflowInstanceInfo info1 =
			context.mock(WorkflowInstanceInfo.class, "info1");
		WorkflowInstanceInfo info2 =
			context.mock(WorkflowInstanceInfo.class, "info2");
		List<WorkflowInstanceInfo> infos =
			new ArrayList<WorkflowInstanceInfo>();
		infos.add(info1);
		infos.add(info2);

		ignored(4, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			relationType, relationId, false);
		final WorkflowResultContainer<List<WorkflowInstanceInfo>>
			correctResult =
				new WorkflowResultContainer<List<WorkflowInstanceInfo>>(infos);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			infos,
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				relationType, relationId, false));

		//Engine error
		final WorkflowResultContainer<List<WorkflowInstanceInfo>> errorResult =
			new WorkflowResultContainer<List<WorkflowInstanceInfo>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				relationType, relationId, false);
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
			workflowInstanceManagerProxy.getWorkflowInstanceInfos(
				relationType, relationId, false);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void removeWorkflowInstance() throws Exception {

		//Correct
		long workflowInstanceId = 1;
		WorkflowInstanceInfo info1 =
			context.mock(WorkflowInstanceInfo.class, "info1");
		WorkflowInstanceInfo info2 =
			context.mock(WorkflowInstanceInfo.class, "info2");
		List<WorkflowInstanceInfo> infos =
			new ArrayList<WorkflowInstanceInfo>();
		infos.add(info1);
		infos.add(info2);
		ignored(2, 0);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createRemoveWorkflowInstanceRequest(
			workflowInstanceId);
		final WorkflowResultContainer<Object> correctResult =
			new WorkflowResultContainer<Object>(true);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		workflowInstanceManagerProxy.removeWorkflowInstance(workflowInstanceId);
	}

	@Test
	public void signalWorkflowInstance_long_Map() throws Exception {
		//Correct
		long workflowInstanceId = 1;
		long callingUserId = 1;
		Map<String, Object> attributes = new HashMap<String, Object>();
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);
		ignored(4, callingUserId);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createSignalWorkflowInstanceRequest(
			workflowInstanceId, attributes, callingUserId);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.signalWorkflowInstance(
				workflowInstanceId, attributes, callingUserId));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.signalWorkflowInstance(
				workflowInstanceId, attributes, callingUserId);
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
			workflowInstanceManagerProxy.signalWorkflowInstance(
				workflowInstanceId, attributes, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void signalWorkflowInstance_String_Integer_Map_long() throws
		Exception {
		//Correct
		long workflowInstanceId = 1;
		long callingUserId = 1;
		String activityName = "activityName";
		Map<String, Object> attributes = new HashMap<String, Object>();
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);
		ignored(4, callingUserId);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createSignalWorkflowInstanceRequest(
			workflowInstanceId, activityName, attributes, callingUserId);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.signalWorkflowInstance(
			workflowInstanceId, activityName, attributes, callingUserId));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.signalWorkflowInstance(
				workflowInstanceId, activityName, attributes, callingUserId);
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
			workflowInstanceManagerProxy.signalWorkflowInstance(
				workflowInstanceId, activityName, attributes, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void startWorkflowInstance_String_Integer_Map_long()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		long callingUserId = 1;
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);
		ignored(4, callingUserId);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, variables,
			callingUserId);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, variables,
			callingUserId));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, variables,
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
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, variables,
				callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void startWorkflowInstance_String_Integer_Map_long_String()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		long callingUserId = 1;
		String activityName = "activityName";
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);
		ignored(4, callingUserId);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, variables,
			callingUserId, activityName);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, variables,
			callingUserId, activityName));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, variables,
				callingUserId, activityName);
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
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, variables,
				callingUserId, activityName);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void startWorkflowInstance_String_String_long_Map_long()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		String relationType = "relationType";
		long relationId = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		final long callingUserId = 1;
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);

		ignored(4, callingUserId);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, variables, callingUserId);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, variables, callingUserId));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, relationType,
				relationId, variables, callingUserId);
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
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, relationType,
				relationId, variables, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void startWorkflowInstance_String_String_long_Map_long_String()
		throws Exception {
		//Correct
		String workflowDefinitionName = "workflowDefinitionName";
		Integer workflowDefinitionVersion = 1;
		String relationType = "relationType";
		long relationId = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		final long callingUserId = 1;
		String activityName = "activityName";
		WorkflowInstanceInfo info = context.mock(WorkflowInstanceInfo.class);
		ignored(4, callingUserId);
		final WorkflowResultContainer<WorkflowInstanceInfo> correctResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>(info);
		final WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, variables, callingUserId, activityName);

		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertSame(
			info,
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, relationType,
				relationId, variables, callingUserId, activityName));

		//Engine error
		final WorkflowResultContainer<WorkflowInstanceInfo> errorResult =
			new WorkflowResultContainer<WorkflowInstanceInfo>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, relationType,
				relationId, variables, callingUserId, activityName);
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
			workflowInstanceManagerProxy.startWorkflowInstance(
				workflowDefinitionName, workflowDefinitionVersion, relationType,
				relationId, variables, callingUserId, activityName);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

}