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

import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowUtil;

import java.lang.reflect.Method;

/**
 * <a href="WorkflowDefinitionRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowDefinitionRequest extends BaseRequest {

	public static WorkflowDefinitionRequest
		createDeployWorkflowDefinitionRequest(
			WorkflowDefinition workflowDefinition, long callingUserId)
		throws WorkflowException {
		return new WorkflowDefinitionRequest(
			deployWorkflowDefinition, callingUserId, workflowDefinition,
			callingUserId);
	}

	public static WorkflowDefinitionRequest
		createGetWorkflowDefinitionsRequest()
		throws WorkflowException {
		return new WorkflowDefinitionRequest(getWorkflowDefinitions, 0);
	}

	public static WorkflowDefinitionRequest createGetWorkflowDefinitionsRequest(
			String workflowDefinitionName)
		throws WorkflowException {
		return new WorkflowDefinitionRequest(
			getWorkflowDefinitions_String, 0, workflowDefinitionName);
	}

	public static WorkflowDefinitionRequest
		createIsSupportsVersioningRequest()
		throws WorkflowException {
		return new WorkflowDefinitionRequest(isSupportsVersioning, 0);
	}

	private WorkflowDefinitionRequest(
		Method method, long callingUserId, Object... args)
		throws WorkflowException {
		super(method, WorkflowUtil.createUserCredential(callingUserId), args);
	}

	private static Method deployWorkflowDefinition;
	private static Method getWorkflowDefinitions;
	private static Method getWorkflowDefinitions_String;
	private static Method isSupportsVersioning;

	static {
		Class clazz = WorkflowDefinitionManager.class;
		try {
			deployWorkflowDefinition =
				clazz.getMethod(
				"deployWorkflowDefinition", WorkflowDefinition.class,
					long.class);
			getWorkflowDefinitions =
				clazz.getMethod("getWorkflowDefinitions");
			getWorkflowDefinitions_String =
				clazz.getMethod("getWorkflowDefinitions", String.class);
			isSupportsVersioning =
				clazz.getMethod("isSupportsVersioning");
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

}