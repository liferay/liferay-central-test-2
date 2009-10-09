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

import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * <a href="WorkflowInstanceManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The utility class supporting static access to all methods for the {@link
 * WorkflowInstanceManager} interface. The target manager object is injected
 * using the {@link #setWorkflowInstanceManager(WorkflowInstanceManager)}
 * method. Besides the static method access, it is also available through {@link
 * #getWorkflowInstanceManager()}.
 * </p>
 *
 * @author Micha Kiener
 */
public class WorkflowInstanceManagerUtil {

	/**
	 * @see WorkflowInstanceManager#addContextInformation(long, Map)
	 */
	public static WorkflowInstanceInfo addContextInformation(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException {

		return _workflowInstanceManager.addContextInformation(
			workflowInstanceId, context);
	}

	/**
	 * @see WorkflowInstanceManager#getPossibleNextActivityNames(long, long,
	 *      Map)
	 */
	public static List<String> getPossibleNextActivityNames(
			long workflowInstanceId, long userId, Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.getPossibleNextActivityNames(
			workflowInstanceId, userId, parameters);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceHistory(long, boolean,
	 *		start, end, OrderByComparator)
	 */
	public static List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren, int start,
			int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceHistory(
			workflowInstanceId, includeChildren, start, end, orderByComparator);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceHistoryCount(long,
	 *		boolean)
	 */
	public static int getWorkflowInstanceHistoryCount(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceHistoryCount(
			workflowInstanceId, includeChildren);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfo(long, boolean)
	 */
	public static WorkflowInstanceInfo getWorkflowInstanceInfo(
			long workflowInstanceId, boolean retrieveChildrenInfo)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfo(
			workflowInstanceId, retrieveChildrenInfo);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfo(String, long,
	 *		boolean)
	 */
	public static WorkflowInstanceInfo getWorkflowInstanceInfo(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfo(
			relationType, relationId, retrieveChildrenInfo);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfoCount(String,
	 *		Integer, boolean)
	 */
	public static int getWorkflowInstanceInfoCount(
			String workflowDefinitionName, Integer workflowDefinitionVersion)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfoCount(
			workflowDefinitionName, workflowDefinitionVersion);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfoCount(String,
	 *		Integer, boolean)
	 */
	public static int getWorkflowInstanceInfoCount(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfoCount(
			workflowDefinitionName, workflowDefinitionVersion, finished);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfoCount(String, long)
	 */
	public static int getWorkflowInstanceInfoCount(
			String relationType, long relationId)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfoCount(
			relationType, relationId);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfos(String, Integer,
	 *		boolean, boolean, int, int, OrderByComparator)
	 */
	public static List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfos(
			workflowDefinitionName, workflowDefinitionVersion, finished,
			retrieveChildrenInfo, start, end, orderByComparator);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfos(String, Integer,
	 *		boolean, int, int, OrderByComparator)
	 */
	public static List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean retrieveChildrenInfo, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfos(
			workflowDefinitionName, workflowDefinitionVersion,
			retrieveChildrenInfo, start, end, orderByComparator);
	}

	/**
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfos(String, long,
	 *		boolean, int, int, OrderByComparator)
	 */
	public static List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String relationType, long relationId, boolean retrieveChildrenInfo,
			int start, int end, OrderByComparator orderByComparator)
		throws WorkflowException {

		return _workflowInstanceManager.getWorkflowInstanceInfos(
			relationType, relationId, retrieveChildrenInfo, start, end,
			orderByComparator);
	}

	public static WorkflowInstanceManager getWorkflowInstanceManager() {
		return _workflowInstanceManager;
	}

	/**
	 * @see WorkflowInstanceManager#removeWorkflowInstance(long)
	 */
	public static void removeWorkflowInstance(long workflowInstanceId)
		throws WorkflowException {

		_workflowInstanceManager.removeWorkflowInstance(workflowInstanceId);
	}

	/**
	 * @see WorkflowInstanceManager#signalWorkflowInstance(long, Map, long, Map)
	 */
	public static WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, Map<String, Object> attributes,
			long callingUserId, Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.signalWorkflowInstance(
			workflowInstanceId, attributes, callingUserId, parameters);
	}

	/**
	 * @see WorkflowInstanceManager#signalWorkflowInstance(long, String, Map,
	 *      long, Map)
	 */
	public static WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			Map<String, Object> attributes, long callingUserId,
		Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.signalWorkflowInstance(
			workflowInstanceId, activityName, attributes, callingUserId,
			parameters);
	}

	/**
	 * @see WorkflowInstanceManager#startWorkflowInstance(String, Integer, Map,
	 *      long, Map)
	 */
	public static WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId,
		Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, context,
			callingUserId, parameters);
	}

	/**
	 * @see WorkflowInstanceManager#startWorkflowInstance(String, Integer, Map,
	 *      long, String, Map)
	 */
	public static WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, long callingUserId,
			String activityName,
		Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, context,
			callingUserId, activityName, parameters);
	}

	/**
	 * @see WorkflowInstanceManager#startWorkflowInstance(String, Integer,
	 *      String, long, Map, long, Map)
	 */
	public static WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId, Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, context, callingUserId, parameters);
	}

	/**
	 * @see WorkflowInstanceManager#startWorkflowInstance(String, Integer,
	 *      String, long, Map, long, String, Map)
	 */
	public static WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			long callingUserId, String activityName, Map<String, Object> parameters)
		throws WorkflowException {

		return _workflowInstanceManager.startWorkflowInstance(
			workflowDefinitionName, workflowDefinitionVersion, relationType,
			relationId, context, callingUserId, activityName, parameters);
	}

	public void setWorkflowInstanceManager(
		WorkflowInstanceManager workflowInstanceManager) {

		_workflowInstanceManager = workflowInstanceManager;
	}

	private static WorkflowInstanceManager _workflowInstanceManager;

}