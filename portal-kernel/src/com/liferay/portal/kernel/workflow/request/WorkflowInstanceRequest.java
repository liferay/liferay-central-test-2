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

import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowUtil;

import java.lang.reflect.Method;

import java.util.Map;

public class WorkflowInstanceRequest extends BaseRequest {

	public static WorkflowInstanceRequest createAddContextInformationRequest(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			addContextInformation_long_Map, 0, workflowInstanceId, context);
	}

	public static WorkflowInstanceRequest
		createGetWorkflowInstanceHistoryRequest(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceHistory_long_boolean, 0, workflowInstanceId,
			includeChildren);
	}

	public static WorkflowInstanceRequest createGetWorkflowInstanceInfoRequest(
			long workflowInstanceId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceInfo_long_boolean, 0, workflowInstanceId,
			retrieveChildrenInfo);
	}

	public static WorkflowInstanceRequest createGetWorkflowInstanceInfoRequest(
			String relationType, long relationId,
			boolean retrieveChildrenInfo)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceInfo_String_long_boolean, 0, relationType,
			relationId, retrieveChildrenInfo);
	}

	public static WorkflowInstanceRequest createGetWorkflowInstanceInfosRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean retrieveChildrenInfo)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceInfos_String_Integer_boolean, 0,
			workflowDefinitionName, workflowDefinitionVersion,
			retrieveChildrenInfo);
	}

	public static WorkflowInstanceRequest createGetWorkflowInstanceInfosRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceInfos_String_Integer_boolean_boolean, 0,
			workflowDefinitionName, workflowDefinitionVersion, finished,
			retrieveChildrenInfo);
	}

	public static WorkflowInstanceRequest createGetWorkflowInstanceInfosRequest(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			getWorkflowInstanceInfos_String_long_boolean, 0, relationType,
			relationId, retrieveChildrenInfo);
	}

	public static WorkflowInstanceRequest createRemoveWorkflowInstanceRequest(
			long workflowInstanceId)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			removeWorkflowInstance_long, 0, workflowInstanceId);
	}

	public static WorkflowInstanceRequest createSignalWorkflowInstanceRequest(
			long workflowInstanceId, Map<String, Object> attributes,
		long callingUserId)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			signalWorkflowInstance_long_Map_long, callingUserId,
			workflowInstanceId,
			attributes, callingUserId);
	}

	public static WorkflowInstanceRequest createSignalWorkflowInstanceRequest(
			long workflowInstanceId, String activityName,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			signalWorkflowInstance_long_String_Map_long, callingUserId,
			workflowInstanceId,
			activityName, attributes, callingUserId);
	}

	public static WorkflowInstanceRequest createStartWorkflowInstanceRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			startWorkflowInstance_String_Integer_Map_long, callingUserId,
			workflowDefinitionName, workflowDefinitionVersion, context,
			callingUserId);
	}

	public static WorkflowInstanceRequest createStartWorkflowInstanceRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId,
			String activityName)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			startWorkflowInstance_String_Integer_Map_long_String,
			callingUserId,
			workflowDefinitionName, workflowDefinitionVersion, context,
			callingUserId, activityName);
	}

	public static WorkflowInstanceRequest createStartWorkflowInstanceRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			startWorkflowInstance_String_Integer_String_long_Map_long,
			callingUserId,
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, context, callingUserId);
	}

	public static WorkflowInstanceRequest createStartWorkflowInstanceRequest(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId, String activityName)
		throws WorkflowException {
		return new WorkflowInstanceRequest(
			startWorkflowInstance_String_Integer_String_long_Map_long_String,
			callingUserId,
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, context, callingUserId, activityName);
	}

	private WorkflowInstanceRequest(
		Method method, long callingUserId, Object... args)
		throws WorkflowException {
		super(method, WorkflowUtil.createUserCredential(callingUserId), args);
	}

	private static Method addContextInformation_long_Map;
	private static Method getWorkflowInstanceHistory_long_boolean;
	private static Method getWorkflowInstanceInfo_long_boolean;
	private static Method getWorkflowInstanceInfo_String_long_boolean;
	private static Method getWorkflowInstanceInfos_String_Integer_boolean;
	private static
		Method getWorkflowInstanceInfos_String_Integer_boolean_boolean;
	private static Method getWorkflowInstanceInfos_String_long_boolean;
	private static Method removeWorkflowInstance_long;
	private static Method signalWorkflowInstance_long_Map_long;
	private static Method signalWorkflowInstance_long_String_Map_long;
	private static Method startWorkflowInstance_String_Integer_Map_long;
	private static Method startWorkflowInstance_String_Integer_Map_long_String;
	private static
		Method startWorkflowInstance_String_Integer_String_long_Map_long;
	private static
		Method startWorkflowInstance_String_Integer_String_long_Map_long_String;

	static {
		Class clazz = WorkflowInstanceManager.class;
		try {
			addContextInformation_long_Map =
				clazz.getMethod(
					"addContextInformation", long.class, Map.class);
			getWorkflowInstanceHistory_long_boolean =
				clazz.getMethod(
					"getWorkflowInstanceHistory", long.class, boolean.class);
			getWorkflowInstanceInfo_long_boolean =
				clazz.getMethod(
					"getWorkflowInstanceInfo", long.class,boolean.class);
			getWorkflowInstanceInfo_String_long_boolean =
				clazz.getMethod(
					"getWorkflowInstanceInfo", String.class, long.class,
					boolean.class);
			getWorkflowInstanceInfos_String_Integer_boolean =
				clazz.getMethod(
					"getWorkflowInstanceInfos", String.class, Integer.class,
					boolean.class);
			getWorkflowInstanceInfos_String_Integer_boolean_boolean =
				clazz.getMethod(
					"getWorkflowInstanceInfos", String.class, Integer.class,
					boolean.class,boolean.class);
			getWorkflowInstanceInfos_String_long_boolean =
				clazz.getMethod(
					"getWorkflowInstanceInfos", String.class, long.class,
					boolean.class);
			removeWorkflowInstance_long =
				clazz.getMethod("removeWorkflowInstance", long.class);
			signalWorkflowInstance_long_Map_long =
				clazz.getMethod(
					"signalWorkflowInstance", long.class, Map.class,
					long.class);
			signalWorkflowInstance_long_String_Map_long =
				clazz.getMethod(
					"signalWorkflowInstance", long.class, String.class,
					Map.class, long.class);
			startWorkflowInstance_String_Integer_Map_long =
				clazz.getMethod(
					"startWorkflowInstance", String.class, Integer.class,
					Map.class, long.class);
			startWorkflowInstance_String_Integer_Map_long_String =
				clazz.getMethod(
					"startWorkflowInstance", String.class, Integer.class,
					Map.class, long.class, String.class);
			startWorkflowInstance_String_Integer_String_long_Map_long =
				clazz.getMethod(
					"startWorkflowInstance", String.class, Integer.class,
					String.class, long.class, Map.class, long.class);
			startWorkflowInstance_String_Integer_String_long_Map_long_String =
				clazz.getMethod(
					"startWorkflowInstance", String.class, Integer.class,
					String.class, long.class, Map.class, long.class,
					String.class);
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

}