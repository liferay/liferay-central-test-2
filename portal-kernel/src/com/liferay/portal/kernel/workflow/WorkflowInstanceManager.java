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

import com.liferay.portal.kernel.messaging.proxy.MessagingProxy;
import com.liferay.portal.kernel.messaging.proxy.ProxyMode;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

@MessagingProxy(mode = ProxyMode.SYNC)
/**
 * <a href="WorkflowInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The workflow instance manager is the starting point into the workflow system
 * and provides methods to start new workflow instances as well as querying
 * existing ones.
 * </p>
 *
 * <p>
 * A workflow instance is actually a single instance of a workflow definition,
 * tracking its state, history and possible next activities. A workflow instance
 * is therefore always in relation to a single workflow definition and might
 * have tasks related to it.
 * </p>
 *
 * <p>
 * Optionally, a workflow instance can be started in relation to a specific
 * domain object instance, guiding it through the workflow. Assume there is a
 * specific new entry within the CMS created which has to run through an
 * approval process which is defined as a workflow definition. in this case, the
 * new CMS entry would act as the related domain object of a workflow instance.
 * <b><i>Note:</i></b> However, the relation domain object is an optional
 * feature and might not be supported by the underlying engine.
 * </p>
 *
 * <p>
 * Creating a new workflow instance automatically triggers the first default
 * activity, if any and stops on the next node (state) where there is no further
 * processing without any user or system interaction. Such an interaction is
 * modeled either through a task being created or a waiting state followed by a
 * decision or activity which has to be triggered through this API manually.
 * Completing a task usually runs forward the workflow by triggering its
 * following activity automatically.
 * </p>
 *
 * <p>
 * The different modeling possibilities and features is dependent on the
 * underlying workflow engine where this interface is just a high level API to
 * communicate with the engine in a generic way.
 * </p>
 *
 * @author Micha Kiener
 */
public interface WorkflowInstanceManager {

	/**
	 * Merges the given additional context information into the existing context
	 * of the given workflow instance to make them available globally. Make sure
	 * that all objects are serializable as they are usually being persisted
	 * along with the workflow instance.
	 *
	 * @param  workflowInstanceId the workflow instance to add context
	 *		   information to
	 * @param  context the map of additional context information
	 * @return the workflow instance information object reflecting those changes
	 *		   made
	 * @throws WorkflowException is thrown, if adding the context information
	 *		   failed
	 */
	public WorkflowInstanceInfo addContextInformation(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns a list of activity names to be possible next activities to signal
	 * the given workflow instance with.
	 * </p>
	 *
	 * <p>
	 * The list of possible next activities is checked with the role set of the
	 * specified user to only return activity names the user is allowed to
	 * execute.
	 * </p>
	 *
	 * @param  workflowInstanceId the id of the workflow instance to return a
	 *		   list of possible next activities for
	 * @param  userId the id of the user requesting the list to check the
	 *		   activities being executable by the user
	 * @return the list of activity names possible to be signaled
	 * @throws WorkflowException is thrown, if requesting the list failed
	 */
	public List<String> getPossibleNextActivityNames(
			long workflowInstanceId, @CallingUserId long userId)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns the history of the specified workflow instance. The history will
	 * contain the workflow log entries being made which are workflow engine
	 * specific in terms of what event will be part of the history.
	 * </p>
	 *
	 * <p>
	 * If the parameter <code>includeChildren</code> is set to <code>true</code>
	 * , a complete history is returned, also including the log entries from the
	 * children of the given instance, if any in chronological, descending
	 * order, the newest entry at first position.
	 *
	 * @param  workflowInstanceId the workflow instance identifier to return its
	 *		   history
	 * @param  includeChildren flag, indicating whether to return the history of
	 *		   children too
	 * @return the list of history entries for the given instance and optionally
	 *		   its children
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException;

	/**
	 * Returns the workflow instance information for the given identifier, if
	 * found, otherwise an exception will be thrown.
	 *
	 * @param  workflowInstanceId the identifier of the workflow instance to be
	 *		   returned
	 * @param  retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *		   children's information should be returned as well or if only the
	 *		   specified workflow instance should be returned, without the
	 *		   children
	 * @return the workflow instance information
	 * @throws WorkflowException is thrown, if the instance was not found or any
	 *		   other error occurred
	 */
	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			long workflowInstanceId, boolean retrieveChildrenInfo)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns the workflow instance information for a specific relation object
	 * instance. Starting a new workflow instance can be done in relation to a
	 * certain object instance by providing its type (usually the fully
	 * qualified class name or some unique type information) and its identifier.
	 * If done so, the instance might be queried using this method as it will
	 * return the instance related to the specified object instance. This is
	 * useful to run a workflow related to a domain object and request its
	 * current state of the workflow afterwards.
	 * </p>
	 *
	 * <p>
	 * Unlike the {@link #getWorkflowInstanceInfos(String, long, boolean)}, this
	 * method assumes there is only one workflow instance per domain object. If
	 * this is not the case, the method {@link #getWorkflowInstanceInfos(String,
	 * long, boolean)} should be used instead where a list of workflow instances
	 * will be returned rather than one instance only.
	 * </p>
	 *
	 * <p>
	 * This method returns a workflow instance regardless if it is still open or
	 * already finished, but it will return <code>null</code> rather than
	 * throwing an exception, if no such instance found in relation to the
	 * specified domain object instance.
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @param  relationType the unique type representing the domain object class
	 *		   to return a workflow instance information for
	 * @param  relationId the identifier of the domain object instance returning
	 *		   a workflow instance information for
	 * @param  retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *		   children's information should be returned as well or if only the
	 *		   root workflow instance should be returned, without the children
	 * @return the workflow instance related to the specified domain object
	 *		   instance, if found, <code>null</code> otherwise
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException;

	/**
	 * Returns a list of workflow instance information for a given workflow
	 * definition and optionally a certain version of it. By default, it only
	 * returns open workflow instances. If ended ones should be returned as well
	 * or even exclusively, the method {@link #getWorkflowInstanceInfos(String,
	 * Integer, boolean)} should be used instead.
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   return instance information for
	 * @param  workflowDefinitionVersion the optional version of the definition,
	 *		   if querying for a particular version, otherwise <code>null</code>
	 *		   has to be provided
	 * @param  retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *		   children's information should be returned as well or if only the
	 *		   root workflow instance should be returned, without the children
	 * @param  start start position for the result pagination(inculde element on
	 *		   start)
	 * @param  end end position for the result pagination(exclude element on
	 *		   end)
	 * @param  orderByComparator the comparator for sorting the result list.
	 * @return a list of workflow instance information for the given definition,
	 *		   will return an empty list rather than <code>null</code> if no
	 *		   instances found
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean retrieveChildrenInfo, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	/**
	 * Returns a list of workflow instance information for a given workflow
	 * definition and optionally a certain version of it. If the parameter
	 * <code>finished</code> is provided (not <code>null</code>), only
	 * appropriate instances are returned.
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   return instance information for
	 * @param  workflowDefinitionVersion the optional version of the definition,
	 *		   if querying for a particular version, otherwise <code>null</code>
	 *		   has to be provided
	 * @param  finished defines, if finished or open workflow instances should
	 *		   be returned or if <code>null</code>, all instances should be
	 *		   returned
	 * @param  retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *		   children's information should be returned as well or if only the
	 *		   root workflow instance should be returned, without the children
	 * @param  start start position for the result pagination(inculde element on
	 *		   start)
	 * @param  end end position for the result pagination(exclude element on
	 *		   end)
	 * @param  orderByComparator the comparator for sorting the result list.
	 * @return a list of workflow instance information for the given definition,
	 *		   will return an empty list rather than <code>null</code> if no
	 *		   instances found
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo, int start, int end,
			OrderByComparator orderByComparator)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns a list of workflow instance information related to the specified
	 * domain object instance. See the documentation on method {@link
	 * #getWorkflowInstanceInfo(String, long, boolean)} on how a domain object
	 * can be related to a workflow instance.
	 * </p>
	 *
	 * <p>
	 * Unlike the above mentioned method, this one returns a list of workflow
	 * instance information rather than just the first one found. If there might
	 * be more than one workflow instance in relation to the same domain object
	 * instance, this method must always be used.
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @param  relationType the unique type representing the domain object class
	 *		   to return the workflow instance information for
	 * @param  relationId the identifier of the domain object instance returning
	 *		   workflow instance information for
	 * @param  retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *		   children's information should be returned as well or if only the
	 *		   root workflow instance should be returned, without the children
	 * @param  start start position for the result pagination(inculde element on
	 *		   start)
	 * @param  end end position for the result pagination(exclude element on
	 *		   end)
	 * @param  orderByComparator the comparator for sorting the result list.
	 * @return the list of workflow instance information found in relation to
	 *		   the specified domain object instance or an empty list, if nothing
	 *		   found, never <code>null</code>
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String relationType, long relationId, boolean retrieveChildrenInfo,
			int start, int end, OrderByComparator orderByComparator)
		throws WorkflowException;

	/**
	 * Removes the given workflow instance and all its related entities like
	 * history, tasks, timers and anything related to it. Using this method
	 * means the same as if the instance was never started. It also removes the
	 * attached, persisted context information, but it does not rollback
	 * anything that happened during the execution of the workflow. If the given
	 * workflow instance id is a child instance, the root instance and all of
	 * its children is removed anyhow.
	 *
	 * @param  workflowInstanceId the identifier of the workflow instance to be
	 *		   removed
	 * @throws WorkflowException is thrown, if removing failed
	 */
	public void removeWorkflowInstance(long workflowInstanceId)
		throws WorkflowException;

	/**
	 * <p>
	 * Executes the next, default transition (activity) on the given workflow
	 * instance by signaling its execution plan. Optionally, there might be any
	 * context information being provided through a context info map. Some
	 * engines do not allow to attach context information to the instance itself
	 * but rather use them on a per method basis.
	 * </p>
	 *
	 * <p>
	 * Triggering the next activity could eventually execute some following
	 * activities as well, if they are to be executed automatically (depending
	 * on the workflow definition, actually), if so, the engine stops at the
	 * next node or state which is not executable without user interaction or
	 * any other signaling invocation.
	 * </p>
	 *
	 * @param  workflowInstanceId the id of the workflow instance being
	 *		   triggered
	 * @param  attributes the optional context information to be passed on to
	 *		   the engine in order to execute the next default activity, they
	 *		   will be merged into the existing context information map
	 * @param  callingUserId the id of the calling user (see {@link
	 *		   UserCredentialFactoryUtil#createCredential(long)} for more
	 *		   information)
	 * @return the updated workflow instance information reflecting the current
	 *		   node (state) of the process after the next default activity has
	 *		   been executed
	 * @throws WorkflowException is thrown, if triggering the next activity
	 *		   failed
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, Map<String, Object> attributes,
			@CallingUserId long callingUserId)
		throws WorkflowException;

	/**
	 * <p>
	 * Executes the specified activity on the given workflow instance by
	 * signaling its execution plan using the given activity name. Optionally,
	 * there might be any context information being provided through a context
	 * info map. Some engines do not allow to attach context information to the
	 * instance itself but rather use them on a per method basis.
	 * </p>
	 *
	 * <p>
	 * Triggering this activity could eventually execute some following
	 * activities as well, if they are to be executed automatically (depending
	 * on the workflow definition, actually), if so, the engine stops at the
	 * next node or state which is not executable without user interaction or
	 * any other signaling invocation.
	 * </p>
	 *
	 * <p>
	 * This method is usually used to execute a global activity which does not
	 * depend on the current state nor does the activity change it afterwards.
	 * </p>
	 *
	 * @param  workflowInstanceId the id of the workflow instance being
	 *		   triggered
	 * @param  activityName the name of the activity to be triggered
	 * @param  attributes the optional context information to be passed on to
	 *		   the engine in order to execute the next default activity, they
	 *		   will be merged into the existing context information map
	 * @param  callingUserId the id of the calling user (see {@link
	 *		   UserCredentialFactoryUtil#createCredential(long)} for more
	 *		   information)
	 * @return the updated workflow instance information reflecting the current
	 *		   node (state) of the process after the activity has been executed
	 * @throws WorkflowException is thrown, if triggering the activity failed or
	 *		   the activity was not found or is not executable due to the
	 *		   current state of the instance
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			Map<String, Object> attributes, @CallingUserId long callingUserId)
		throws WorkflowException;

	/**
	 * Creates a new workflow instance and starts the workflow by triggering the
	 * first default activity and returns the information object for it. The
	 * instance is running after the specified workflow definition and is
	 * initialized with the given map of context information, if provided
	 * (optional).
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   create a new workflow instance for
	 * @param  workflowDefinitionVersion the optional, specific version of the
	 *		   workflow definition, if not provided (<code>null</code>), the
	 *		   newest version is used automatically
	 * @param  context the optional map of context information being attached to
	 *		   the process instance, the objects contained in the map must be
	 *		   serializable in order to be persisted along the workflow instance
	 * @param  callingUserId the user creating this new workflow instance
	 * @return the workflow instance information after being successfully
	 *		   created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *		   created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, @CallingUserId long callingUserId)
		throws WorkflowException;

	/**
	 * In addition to the {@link #startWorkflowInstance(String, Integer, Map,
	 * long)} this method also supports the starting activity name to be
	 * provided to tell the engine how to start the new workflow. This method
	 * depends on the workflow definition and engine actually, if a specific
	 * starting activity is provided, the activity name provided might be
	 * ignored, if the engine does not support it or if the workflow definition
	 * only has one starting point.
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   create a new workflow instance for
	 * @param  workflowDefinitionVersion the optional, specific version of the
	 *		   workflow definition, if not provided (<code>null</code>), the
	 *		   newest version is used automatically
	 * @param  context the optional map of context information being attached to
	 *		   the process instance, the objects contained in the map must be
	 *		   serializable in order to be persisted along the workflow instance
	 * @param  callingUserId the user creating this new workflow instance
	 * @param  activityName the optional activity name to initialize the
	 *		   workflow
	 * @return the workflow instance information after being successfully
	 *		   created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *		   created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, @CallingUserId long callingUserId,
			String activityName)
		throws WorkflowException;

	/**
	 * <p>
	 * Creates a new workflow instance and starts the workflow by triggering the
	 * first default activity and returns the information object for it. The
	 * instance is running after the specified workflow definition and is
	 * initialized with the given map of context information, if provided
	 * (optional). The newly created instance is related to the specified domain
	 * object instance and hence can be retrieved later using the {@link
	 * #getWorkflowInstanceInfo(String, long, boolean)} (see its comment for
	 * more information about relations to domain objects).
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   create a new workflow instance for
	 * @param  workflowDefinitionVersion the optional, specific version of the
	 *		   workflow definition, if not provided (<code>null</code>), the
	 *		   newest version is used automatically.
	 * @param  relationType the unique type identifier of the domain object
	 *		   class to create a new workflow instance in relation to
	 * @param  relationId the identifier of the domain object instance to create
	 *		   a new workflow instance in relation to
	 * @param  context the optional map of context information being attached to
	 *		   the process instance, the objects contained in the map must be
	 *		   serializable in order to be persisted along the workflow instance
	 * @param  callingUserId the user creating this new workflow instance
	 * @return the workflow instance information after being successfully
	 *		   created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *		   created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			@CallingUserId long callingUserId)
		throws WorkflowException;

	/**
	 * <p>
	 * In addition to the {@link #startWorkflowInstance(String, Integer, String,
	 * long, Map, long)} this method also supports the starting activity name to
	 * be provided to tell the engine how to start the new workflow. This method
	 * depends on the workflow definition and engine actually, if a specific
	 * starting activity is provided, the activity name provided might be
	 * ignored, if the engine does not support it or if the workflow definition
	 * only has one starting point.
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @param  workflowDefinitionName the name of the workflow definition to
	 *		   create a new workflow instance for
	 * @param  workflowDefinitionVersion the optional, specific version of the
	 *		   workflow definition, if not provided (<code>null</code>), the
	 *		   newest version is used automatically
	 * @param  relationType the unique type identifier of the domain object
	 *		   class to create a new workflow instance in relation to
	 * @param  relationId the identifier of the domain object instance to create
	 *		   a new workflow instance in relation to
	 * @param  context the optional map of context information being attached to
	 *		   the process instance, the objects contained in the map must be
	 *		   serializable in order to be persisted along the workflow instance
	 * @param  callingUserId the user creating this new workflow instance
	 * @param  activityName the optional activity name to initialize the
	 *		   workflow
	 * @return the workflow instance information after being successfully
	 *		   created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *		   created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			@CallingUserId long callingUserId, String activityName)
		throws WorkflowException;

}