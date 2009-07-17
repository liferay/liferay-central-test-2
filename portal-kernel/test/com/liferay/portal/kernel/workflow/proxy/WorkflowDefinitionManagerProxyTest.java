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

import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTestBase;
import com.liferay.portal.kernel.workflow.request.WorkflowDefinitionRequest;
import com.liferay.portal.log.Log4jLogImpl;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * <a href="WorkflowDefinitionManagerProxyTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 *
 */
@RunWith(JMock.class)
public class WorkflowDefinitionManagerProxyTest extends WorkflowTestBase{

	@BeforeClass
	public static void setUpClass() {
		turnOffLogging(WorkflowDefinitionManagerProxy.class);
	}

	@Test
	public void deployWorkflowDefinition() throws Exception {
		//Correct
		long callingUserId=1;
		WorkflowDefinition definition = context.mock(WorkflowDefinition.class);
		ignored(4, callingUserId);
		final WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createDeployWorkflowDefinitionRequest(
				definition, callingUserId);
		final WorkflowResultContainer<Object> correctResult =
			new WorkflowResultContainer<Object>(true);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		workflowDefinitionManagerProxy.deployWorkflowDefinition(
			definition, callingUserId);

		//Engine error
		final WorkflowResultContainer<Boolean> errorResult =
			new WorkflowResultContainer<Boolean>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		boolean errorFlag = false;
		try {
			workflowDefinitionManagerProxy.deployWorkflowDefinition(
				definition, callingUserId);
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
			workflowDefinitionManagerProxy.deployWorkflowDefinition(
				definition, callingUserId);
		}
		catch (WorkflowException ex) {
			errorFlag = true;
		}
		assertTrue(errorFlag);
	}

	@Test
	public void getWorkflowDefinitions() throws Exception {
		//Correct
		WorkflowDefinition definition1 =
			context.mock(WorkflowDefinition.class, "definition1");
		WorkflowDefinition definition2 =
			context.mock(WorkflowDefinition.class, "definition2");
		List<WorkflowDefinition> definitionList =
			new ArrayList<WorkflowDefinition>();
		definitionList.add(definition1);
		definitionList.add(definition2);

		ignored(4, 0);
		final WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createGetWorkflowDefinitionsRequest();
		final WorkflowResultContainer<List<WorkflowDefinition>> correctResult =
			new WorkflowResultContainer<List<WorkflowDefinition>>(
				definitionList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		List<WorkflowDefinition> definitions =
			workflowDefinitionManagerProxy.getWorkflowDefinitions();
		assertEquals(2, definitions.size());
		assertEquals(definition1, definitions.get(0));
		assertEquals(definition2, definitions.get(1));

		//Engine error
		final WorkflowResultContainer<List<WorkflowDefinition>> errorResult =
			new WorkflowResultContainer<List<WorkflowDefinition>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		assertEquals(
			0, workflowDefinitionManagerProxy.getWorkflowDefinitions().size());

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		assertEquals(
			0, workflowDefinitionManagerProxy.getWorkflowDefinitions().size());

	}

	@Test
	public void getWorkflowDefinitions_String() throws Exception {

		//Correct
		WorkflowDefinition definition1 =
			context.mock(WorkflowDefinition.class, "definition1");
		WorkflowDefinition definition2 =
			context.mock(WorkflowDefinition.class, "definition2");
		List<WorkflowDefinition> definitionList =
			new ArrayList<WorkflowDefinition>();
		definitionList.add(definition1);
		definitionList.add(definition2);
		String name = "name";

		ignored(4, 0);
		final WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createGetWorkflowDefinitionsRequest(name);
		final WorkflowResultContainer<List<WorkflowDefinition>> correctResult =
			new WorkflowResultContainer<List<WorkflowDefinition>>(
				definitionList);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		List<WorkflowDefinition> definitions =
			workflowDefinitionManagerProxy.getWorkflowDefinitions(name);
		assertEquals(2, definitions.size());
		assertEquals(definition1, definitions.get(0));
		assertEquals(definition2, definitions.get(1));

		//Engine error
		final WorkflowResultContainer<List<WorkflowDefinition>> errorResult =
			new WorkflowResultContainer<List<WorkflowDefinition>>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		assertEquals(
			0,
			workflowDefinitionManagerProxy.getWorkflowDefinitions(name).size());

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});
		assertEquals(
			0,
			workflowDefinitionManagerProxy.getWorkflowDefinitions(name).size());

	}

	@Test
	public void isSupportsVersioning() throws Exception {
		ignored(4, 0);

		//Correct
		final WorkflowDefinitionRequest request =
			WorkflowDefinitionRequest.createIsSupportsVersioningRequest();
		final WorkflowResultContainer<Boolean> correctResult =
			new WorkflowResultContainer<Boolean>(true);
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(correctResult));
			}

		});
		assertTrue(workflowDefinitionManagerProxy.isSupportsVersioning());

		//Engine error
		final WorkflowResultContainer<Boolean> errorResult =
			new WorkflowResultContainer<Boolean>();
		errorResult.setException(new WorkflowException());
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(returnValue(errorResult));
			}

		});
		assertFalse(workflowDefinitionManagerProxy.isSupportsVersioning());

		//MessageBus error
		final MessageBusException exception = new MessageBusException();
		context.checking(new Expectations() {

			{
				oneOf(synchronousMessageSender).send(with(equal(request)));
				will(throwException(exception));
			}

		});

		assertFalse(workflowDefinitionManagerProxy.isSupportsVersioning());
	}

	private static void turnOffLogging(Class clazz) {
		turnOffLogging(LogFactoryUtil.getLog(clazz));
	}

	private static void turnOffLogging(Log log) {
		try {
			if (log instanceof Log4jLogImpl) {
				Log4jLogImpl log4jLogImpl = (Log4jLogImpl) log;
				Field field = Log4jLogImpl.class.getDeclaredField("_logger");
				field.setAccessible(true);
				Logger logger = (Logger) field.get(log4jLogImpl);
				logger.setLevel(Level.OFF);
			}
			else if (log instanceof Jdk14LogImpl) {
				Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl) log;
				Field field = Jdk14LogImpl.class.getDeclaredField("_log");
				field.setAccessible(true);
				java.util.logging.Logger logger =
					(java.util.logging.Logger) field.get(jdk14LogImpl);
				logger.setLevel(java.util.logging.Level.OFF);
			}
			else if (log instanceof LogWrapper) {
				LogWrapper logWrapper = (LogWrapper) log;
				Field field = LogWrapper.class.getDeclaredField("_log");
				field.setAccessible(true);
				Log realLog = (Log) field.get(logWrapper);
				turnOffLogging(realLog);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}