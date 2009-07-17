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

import com.liferay.portal.kernel.workflow.TaskInstanceManager;
import com.liferay.portal.kernel.workflow.UserCredential;
import com.liferay.portal.kernel.workflow.UserCredentialFactory;
import com.liferay.portal.kernel.workflow.WorkflowException;
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
 * <a href="TaskInstanceRequestTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class TaskInstanceRequestTest {

	@Before
	public void setUp() {
		_manager = _context.mock(TaskInstanceManager.class);
		_factory = _context.mock(UserCredentialFactory.class);
		new WorkflowUtil(null, null, _manager, null, _factory);
	}

	@Test
	public void createAssignTaskInstanceToRoleRequest()
		throws WorkflowException {

		final long taskInstanceId = 1;
		final long roleId = 1;
		final String comment = "";
		final long callingUserId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					assignTaskInstanceToRole(
					taskInstanceId, roleId, comment, attributes, callingUserId);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createAssignTaskInstanceToRoleRequest(
			taskInstanceId, roleId, comment, attributes, callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createAssignTaskInstanceToUserRequest()
		throws WorkflowException {
		final long taskInstanceId = 1;
		final UserCredential userCredential = new UserCredential();
		final String comment = "";
		final long callingUserId = 1;
		final Map<String, Object> attributes = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					assignTaskInstanceToUser(
						taskInstanceId, userCredential ,comment, attributes,
						callingUserId);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createAssignTaskInstanceToUserRequest(
			taskInstanceId, userCredential, comment, attributes, callingUserId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createCompleteTaskInstanceRequest() throws WorkflowException {
		final long taskInstanceId = 1;
		final long callingUserId = 1;
		final String comment = "";
		final Map<String, Object> attributes = new HashMap<String, Object>();

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					completeTaskInstance(
					taskInstanceId, callingUserId, comment, attributes);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createCompleteTaskInstanceRequest(
			taskInstanceId, callingUserId, comment, attributes);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByRoleRequest() throws
		WorkflowException {
		final long roleId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getTaskInstanceInfosByRole(roleId);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByRoleRequest(roleId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByRoleRequest2()
		throws WorkflowException {
		final long roleId = 1;
		final boolean completed = false;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getTaskInstanceInfosByRole(roleId, completed);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByRoleRequest(
			roleId, completed);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByUserRequest()
		throws WorkflowException {
		final long userId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getTaskInstanceInfosByUser(userId);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByUserRequest(userId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByUserRequest2()
		throws WorkflowException {
		final long userId = 1;
		final boolean completed = false;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getTaskInstanceInfosByUser(userId, completed);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.createGetTaskInstanceInfosByUserRequest(
			userId, completed);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByWorkflowInstanceRequest()
		throws WorkflowException {
		final long workflowInstanceId = 1;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getTaskInstanceInfosByWorkflowInstance(workflowInstanceId);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.
				createGetTaskInstanceInfosByWorkflowInstanceRequest(
					workflowInstanceId);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetTaskInstanceInfosByWorkflowInstanceRequest2()
		throws WorkflowException {
		final long workflowInstanceId = 1;
		final boolean completed = false;

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).
					getTaskInstanceInfosByWorkflowInstance(
					workflowInstanceId, completed);
			}

		});
		TaskInstanceRequest request =
			TaskInstanceRequest.
				createGetTaskInstanceInfosByWorkflowInstanceRequest(
					workflowInstanceId, completed);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	private Mockery _context = new JUnit4Mockery();
	private TaskInstanceManager _manager;
	private UserCredentialFactory _factory;

}