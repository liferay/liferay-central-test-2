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
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowUtil;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * <a href="WorkflowDefinitionRequestTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class WorkflowDefinitionRequestTest {

	@Before
	public void setUp() {
		_manager = _context.mock(WorkflowDefinitionManager.class);
		_factory = _context.mock(UserCredentialFactory.class);
		new WorkflowUtil(_manager, null, null, null, _factory);
	}

	@Test
	public void createDeployWorkflowDefinitionRequest()
		throws WorkflowException {

		final WorkflowDefinition definition =
			_context.mock(WorkflowDefinition.class);
		final long callingUserId = 1;
		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(callingUserId);
				will(returnValue(null));
				oneOf(_manager).
					deployWorkflowDefinition(definition, callingUserId);
			}

		});
		WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createDeployWorkflowDefinitionRequest(
			definition, callingUserId);
		assertFalse(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowDefinitionsRequest() throws WorkflowException {

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getWorkflowDefinitions();
			}

		});
		WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createGetWorkflowDefinitionsRequest();
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createGetWorkflowDefinitionsRequest_String() throws
		WorkflowException {

		final String name = "testName";

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).getWorkflowDefinitions(name);
			}

		});
		WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createGetWorkflowDefinitionsRequest(name);
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	@Test
	public void createIsSupportsVersioningRequest() throws WorkflowException {

		_context.checking(new Expectations() {

			{
				oneOf(_factory).createCredential(0);
				will(returnValue(null));
				oneOf(_manager).isSupportsVersioning();
			}

		});
		WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createIsSupportsVersioningRequest();
		assertTrue(request.hasReturnValue());
		request.execute(_manager);
	}

	private Mockery _context = new JUnit4Mockery();
	private WorkflowDefinitionManager _manager;
	private UserCredentialFactory _factory;

}