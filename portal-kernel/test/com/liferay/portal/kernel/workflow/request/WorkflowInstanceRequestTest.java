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

package com.liferay.portal.kernel.workflow.request;

import com.liferay.portal.kernel.workflow.UserCredentialFactory;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowUtil;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * <a href="WorkflowInstanceRequestTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class WorkflowInstanceRequestTest {

	@Before
	public void setUp() {
		_manager = _context.mock(WorkflowInstanceManager.class);
		_factory = _context.mock(UserCredentialFactory.class);
		new WorkflowUtil(null, _manager, null, null, _factory);
	}

	@Test
	public void createAddContextInformationRequest() throws WorkflowException {
		final long workflowInstanceId = 1;
		final Map<String, Object> variables = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					addContextInformation(workflowInstanceId, variables);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createAddContextInformationRequest(
			workflowInstanceId, variables);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceHistoryRequest() throws
		WorkflowException {
		final long workflowInstanceId = 1;
		final boolean includeChildren = false;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceHistory(
					workflowInstanceId, includeChildren);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceHistoryRequest(
			workflowInstanceId, includeChildren);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceInfoRequest()
		throws WorkflowException {
		final long workflowInstanceId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceInfo(workflowInstanceId, false);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfoRequest(
			workflowInstanceId, false);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceInfoRequest2()
		throws WorkflowException {
		final String relationType = "";
		final long relationId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceInfo(relationType, relationId, false);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfoRequest(
			relationType, relationId, false);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceInfosRequest() throws
		WorkflowException {

		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceInfos(
					workflowDefinitionName, workflowDefinitionVersion,
					false);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			workflowDefinitionName, workflowDefinitionVersion, false);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceInfosRequest2() throws
		WorkflowException {
		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;
		final boolean finished = false;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceInfos(
					workflowDefinitionName, workflowDefinitionVersion,
					finished, false);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			workflowDefinitionName, workflowDefinitionVersion, finished,
			false);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowInstanceInfosRequest3()
		throws WorkflowException {
		final String relationType = "";
		final long relationId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getWorkflowInstanceInfos(relationType, relationId, false);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createGetWorkflowInstanceInfosRequest(
			relationType, relationId, false);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createRemoveWorkflowInstanceRequest() throws WorkflowException {
		final long workflowInstanceId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).removeWorkflowInstance(workflowInstanceId);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createRemoveWorkflowInstanceRequest(
			workflowInstanceId);
		assertFalse(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createSignalWorkflowInstanceRequest() throws WorkflowException {
		final long workflowInstanceId = 1;
		final long callingUserId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					signalWorkflowInstance(
					workflowInstanceId, attributes, callingUserId);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createSignalWorkflowInstanceRequest(
			workflowInstanceId, attributes, callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createSignalWorkflowInstanceRequest2()
		throws WorkflowException {

		final long workflowInstanceId = 1;
		final String activityName = "";
		final long callingUserId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					signalWorkflowInstance(
					workflowInstanceId, activityName, attributes,
					callingUserId);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createSignalWorkflowInstanceRequest(
			workflowInstanceId, activityName, attributes, callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createStartWorkflowInstanceRequest() throws WorkflowException {

		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();
		final long callingUserId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					startWorkflowInstance(
					workflowDefinitionName, workflowDefinitionVersion,
					attributes, callingUserId);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, attributes,
			callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createStartWorkflowInstanceRequest2() throws WorkflowException {

		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();
		final long callingUserId = 1;
		final String activityName = "";

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					startWorkflowInstance(
					workflowDefinitionName, workflowDefinitionVersion,
					attributes, callingUserId, activityName);

			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, attributes,
			callingUserId, activityName);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createStartWorkflowInstanceRequest3() throws WorkflowException {

		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;
		final String relationType = "";
		final long relationId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();
		final long callingUserId = 1;
		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					startWorkflowInstance(
					workflowDefinitionName, workflowDefinitionVersion,
					relationType, relationId, attributes, callingUserId);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, attributes, callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createStartWorkflowInstanceRequest4() throws WorkflowException {

		final String workflowDefinitionName = "";
		final Integer workflowDefinitionVersion = 1;
		final String relationType = "";
		final long relationId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();
		final long callingUserId = 1;
		final String activityName = "";
		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					startWorkflowInstance(
					workflowDefinitionName, workflowDefinitionVersion,
					relationType, relationId, attributes, callingUserId,
					activityName);
			}

		});
		WorkflowInstanceRequest request =
			WorkflowInstanceRequest.createStartWorkflowInstanceRequest(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, attributes, callingUserId, activityName);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	private Mockery _context = new JUnit4Mockery();
	private WorkflowInstanceManager _manager;
	private UserCredentialFactory _factory;

}