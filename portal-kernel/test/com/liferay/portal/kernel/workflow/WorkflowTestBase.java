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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationSynchronousMessageSender;
import com.liferay.portal.kernel.workflow.proxy.TaskInstanceManagerProxy;
import com.liferay.portal.kernel.workflow.proxy.WorkflowDefinitionManagerProxy;
import com.liferay.portal.kernel.workflow.proxy.WorkflowInstanceManagerProxy;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.junit.Before;

/**
 * <a href="WorkflowTestBase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public abstract class WorkflowTestBase {

	@Before
	public void setUp() {
		synchronousMessageSender =
			context.mock(SingleDestinationSynchronousMessageSender.class);
		workflowDefinitionManagerProxy =
			new WorkflowDefinitionManagerProxy(synchronousMessageSender);
		workflowInstanceManagerProxy =
			new WorkflowInstanceManagerProxy(synchronousMessageSender);
		taskInstanceManagerProxy =
			new TaskInstanceManagerProxy(synchronousMessageSender);
		factory = context.mock(UserCredentialFactory.class);
		new WorkflowUtil(
			workflowDefinitionManagerProxy, workflowInstanceManagerProxy,
			taskInstanceManagerProxy, null, factory);
	}

	protected void ignored(final int times, final long callingUserId)
		throws WorkflowException {
		context.checking(new Expectations() {

			{
				exactly(times).of(factory).createCredential(callingUserId);
			}
		});
	}
	protected WorkflowDefinitionManagerProxy workflowDefinitionManagerProxy;
	protected WorkflowInstanceManagerProxy workflowInstanceManagerProxy;
	protected TaskInstanceManagerProxy taskInstanceManagerProxy;
	protected
		SingleDestinationSynchronousMessageSender synchronousMessageSender;
	protected Mockery context = new JUnit4Mockery();
	protected UserCredentialFactory factory;

}