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
import com.liferay.portal.kernel.resource.ResourceRetriever;
import com.liferay.portal.kernel.resource.StringResourceRetriever;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionInfo;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionRequest;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionRequestType;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <a href="WorkflowDefinitionManagerProxyTest.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowDefinitionManagerProxyTest
		extends WorkflowManagerProxyTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		_proxy = new WorkflowDefinitionManagerProxy(definitionMessageSender);
	}

	public void testAdd() {

		final String testName = "testName";
		final String testContent = "testContent";

		WorkflowDefinitionInfo definitionInfo =
			new WorkflowDefinitionInfo(testName);
		definitionInfo.setResourceRetriever(
			new StringResourceRetriever(testContent));

		MessageListener addListener = new MessageListener() {

			public void receive(Message message) {
				WorkflowDefinitionRequest request =
					(WorkflowDefinitionRequest) message.getPayload();
				assertEquals(
					WorkflowDefinitionRequestType.ADD, request.getType());
				WorkflowDefinitionInfo definitionInfo =
					request.getWorkflowDefinitionInfo();
				assertNotNull(definitionInfo);
				assertEquals(
					testName, definitionInfo.getWorkflowDefinitionName());
				ResourceRetriever retriever =
					definitionInfo.getResourceRetriever();

				BufferedReader br =
					new BufferedReader(
						new InputStreamReader(retriever.getInputStream()));
				try {
					assertEquals(testContent, br.readLine());
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_DEFINITION, addListener);

		_proxy.addWorkflowDefinition(definitionInfo);
	}

	public void testRemove() throws Exception {
		final String definitionName = "definitionName";
		MessageListener removeListener = new MessageListener() {

			public void receive(Message message) {
				WorkflowDefinitionRequest request =
					(WorkflowDefinitionRequest) message.getPayload();
				assertEquals(
					WorkflowDefinitionRequestType.REMOVE,
					request.getType());
				assertEquals(
					definitionName,
					request.getWorkflowDefinitionInfo().
					getWorkflowDefinitionName());
			}
		};
		messageBus.registerMessageListener(
			DestinationNames.WORKFLOW_DEFINITION, removeListener);
		_proxy.removeWorkflowDefinition(definitionName);
	}

	public void testUpdate() throws Exception {
		//Same to add
		testAdd();
	}

	private WorkflowDefinitionManagerProxy _proxy;

}