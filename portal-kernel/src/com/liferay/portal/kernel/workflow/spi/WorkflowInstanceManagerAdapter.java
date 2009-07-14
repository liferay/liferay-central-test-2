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

package com.liferay.portal.kernel.workflow.spi;

import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstanceHistory;
import com.liferay.portal.kernel.workflow.WorkflowInstanceInfo;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

/**
 * <a href="WorkflowInstanceManagerAdapter.java.html"><b><i>View
 * Source</i></b></a>
 * 
 * <p>
 * This is the SPI for the {@link WorkflowInstanceManager}. Inspect it for the
 * details about the interface as they are not being documented here again.
 * </p>
 * 
 * <p>
 * The workflow integration within Liferay works as follows:
 * <li>One of the manager is invoked as part of the high level API (all found
 * within the <code>com.liferay.portal.kernel.workflow</code> package).</li>
 * <li>The invocation is turned into a request by the proxy implementation
 * behind the interface.</li>
 * <li>The message bus of Liferay is used to transport this request to the
 * appropriate SPI as the request could have been enriched with additional data
 * hidden by the API but added by the proxy to make things easier.</li>
 * <li>The adapter, implementing the SPI and having itself registered as the
 * listener for the workflow messages on the message bus, is dispatching the
 * request and invoking the workflow engine.</li>
 * <li>The result is brought back by the adapter through the message bus and
 * returned back by the proxy as the method's result originally invoked through
 * the API.</li>
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public interface WorkflowInstanceManagerAdapter {

	/**
	 * Merges the given additional context information into the existing context
	 * of the given workflow instance to make them available globally. Make sure
	 * that all objects are serializable as they are usually being persisted
	 * along with the workflow instance.
	 *
	 * @param workflowInstanceId the workflow instance to add context
	 *			information to
	 * @param context the map of additional context information
	 * @return the workflow instance information object reflecting those changes
	 *		 made
	 * @throws WorkflowException is thrown, if adding the context information
	 *			 failed
	 */
	public WorkflowInstanceInfo addContextInformation(
			long workflowInstanceId, Map<String, Object> context)
		throws WorkflowException;

	/**
	 * Returns the workflow instance information for the given identifier, if
	 * found, otherwise an exception will be thrown.
	 *
	 * @param workflowInstanceId the identifier of the workflow instance to be
	 *			returned
	 * @param retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *			children's information should be returned as well or if only
	 *			the specified workflow instance should be returned, without
	 *			the children
	 * @return the workflow instance information
	 * @throws WorkflowException is thrown, if the instance was not found or any
	 *			 other error occurred
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
	 * this is not the case, the method
	 * {@link #getWorkflowInstanceInfos(String, long, boolean)} should be used
	 * instead where a list of workflow instances will be returned rather than
	 * one instance only.
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
	 * @param relationType the unique type representing the domain object class
	 *			to return a workflow instance information for
	 * @param relationId the identifier of the domain object instance returning
	 *			a workflow instance information for
	 * @param retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *			children's information should be returned as well or if only
	 *			the root workflow instance should be returned, without the
	 *			children
	 * @return the workflow instance related to the specified domain object
	 *		 instance, if found, <code>null</code> otherwise
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public WorkflowInstanceInfo getWorkflowInstanceInfo(
			String relationType, long relationId, boolean retrieveChildrenInfo)
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
	 * @param workflowInstanceId the workflow instance identifier to return its
	 *			history
	 * @param includeChildren flag, indicating whether to return the history of
	 *			children too
	 * @return the list of history entries for the given instance and optionally
	 *		 its children
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceHistory> getWorkflowInstanceHistory(
			long workflowInstanceId, boolean includeChildren)
		throws WorkflowException;

	/**
	 * Returns a list of workflow instance information for a given workflow
	 * definition and optionally a certain version of it. By default, it only
	 * returns open workflow instances. If ended ones should be returned as well
	 * or even exclusively, the method
	 * {@link #getWorkflowInstanceInfos(String, Integer, boolean)} should be
	 * used instead.
	 *
	 * @param workflowDefinitionName the name of the workflow definition to
	 *			return instance information for
	 * @param workflowDefinitionVersion the optional version of the definition,
	 *			if querying for a particular version, otherwise
	 *			<code>null</code> has to be provided
	 * @param retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *			children's information should be returned as well or if only
	 *			the root workflow instance should be returned, without the
	 *			children
	 * @return a list of workflow instance information for the given definition,
	 *		 will return an empty list rather than <code>null</code> if no
	 *		 instances found
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean retrieveChildrenInfo)
		throws WorkflowException;

	/**
	 * Returns a list of workflow instance information for a given workflow
	 * definition and optionally a certain version of it. If the parameter
	 * <code>finished</code> is provided (not <code>null</code>), only
	 * appropriate instances are returned.
	 *
	 * @param workflowDefinitionName the name of the workflow definition to
	 *			return instance information for
	 * @param workflowDefinitionVersion the optional version of the definition,
	 *			if querying for a particular version, otherwise
	 *			<code>null</code> has to be provided
	 * @param finished defines, if finished or open workflow instances should be
	 *			returned or if <code>null</code>, all instances should be
	 *			returned
	 * @param retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *			children's information should be returned as well or if only
	 *			the root workflow instance should be returned, without the
	 *			children
	 * @return a list of workflow instance information for the given definition,
	 *		 will return an empty list rather than <code>null</code> if no
	 *		 instances found
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			boolean finished, boolean retrieveChildrenInfo)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns a list of workflow instance information related to the specified
	 * domain object instance. See the documentation on method
	 * {@link #getWorkflowInstanceInfo(String, long, boolean)} on how a domain
	 * object can be related to a workflow instance.
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
	 * @param relationType the unique type representing the domain object class
	 *			to return the workflow instance information for
	 * @param relationId the identifier of the domain object instance returning
	 *			workflow instance information for
	 * @param retrieveChildrenInfo flag, indicating whether the hierarchy of
	 *			children's information should be returned as well or if only
	 *			the root workflow instance should be returned, without the
	 *			children
	 * @return the list of workflow instance information found in relation to
	 *		 the specified domain object instance or an empty list, if nothing
	 *		 found, never <code>null</code>
	 * @throws WorkflowException is thrown, if querying failed
	 */
	public List<WorkflowInstanceInfo> getWorkflowInstanceInfos(
			String relationType, long relationId, boolean retrieveChildrenInfo)
		throws WorkflowException;

	/**
	 * Removes the given workflow instance and all its related entities like
	 * history, tasks, timers and anything related to it. Using this method
	 * means the same as if the instance was never started. It also removes the
	 * attached, persisted context information, but it does not rollback
	 * anything that happened during the execution of the workflow.
	 *
	 * @param workflowInstanceId the identifier of the workflow instance to be
	 *			removed
	 */
	public void removeWorkflowInstance(long workflowInstanceId);

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
	 * @param workflowInstanceId the id of the workflow instance being triggered
	 * @param userCredential the user credential having the user's id and role
	 *            set which invoked the signal of the workflow instance as some
	 *            engines needs the current user to signal the next activity
	 * @param attributes the optional context information to be passed on to the
	 *            engine in order to execute the next default activity, they
	 *            will be merged into the existing context information map
	 * @return the updated workflow instance information reflecting the current
	 *         node (state) of the process after the next default activity has
	 *         been executed
	 * @throws WorkflowException is thrown, if triggering the next activity
	 *             failed
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, UserCredential userCredential,
			Map<String, Object> attributes)
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
	 * @param workflowInstanceId the id of the workflow instance being triggered
	 * @param activityName the name of the activity to be triggered
	 * @param userCredential the user credential having the user's id and role
	 *            set which invoked the signal of the workflow instance as some
	 *            engines needs the current user to signal the next activity
	 * @param attributes the optional context information to be passed on to the
	 *            engine in order to execute the next default activity, they
	 *            will be merged into the existing context information map
	 * @return the updated workflow instance information reflecting the current
	 *         node (state) of the process after the activity has been executed
	 * @throws WorkflowException is thrown, if triggering the activity failed or
	 *             the activity was not found or is not executable due to the
	 *             current state of the instance
	 */
	public WorkflowInstanceInfo signalWorkflowInstance(
			long workflowInstanceId, String activityName,
			UserCredential userCredential, Map<String, Object> attributes)
		throws WorkflowException;

	/**
	 * Creates a new workflow instance and starts the workflow by triggering the
	 * first default activity and returns the information object for it. The
	 * instance is running after the specified workflow definition and is
	 * initialized with the given map of context information, if provided
	 * (optional).
	 * 
	 * @param workflowDefinitionName the name of the workflow definition to
	 *            create a new workflow instance for
	 * @param workflowDefinitionVersion the optional, specific version of the
	 *            workflow definition, if not provided (<code>null</code>), the
	 *            newest version is used automatically
	 * @param context the optional map of context information being attached to
	 *            the process instance, the objects contained in the map must be
	 *            serializable in order to be persisted along the workflow
	 *            instance
	 * @param userCredential the user and its role set creating this new
	 *            workflow instance
	 * @return the workflow instance information after being successfully
	 *         created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *             created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, UserCredential userCredential)
		throws WorkflowException;

	/**
	 * In addition to the
	 * {@link #startWorkflowInstance(String, Integer, Map, UserCredential)} this
	 * method also supports the starting activity name to be provided to tell
	 * the engine how to start the new workflow. This method depends on the
	 * workflow definition and engine actually, if a specific starting activity
	 * is provided, the activity name provided might be ignored, if the engine
	 * does not support it or if the workflow definition only has one starting
	 * point.
	 * 
	 * @param workflowDefinitionName the name of the workflow definition to
	 *            create a new workflow instance for
	 * @param workflowDefinitionVersion the optional, specific version of the
	 *            workflow definition, if not provided (<code>null</code>), the
	 *            newest version is used automatically
	 * @param context the optional map of context information being attached to
	 *            the process instance, the objects contained in the map must be
	 *            serializable in order to be persisted along the workflow
	 *            instance
	 * @param userCredential the user and its role set creating this new
	 *            workflow instance
	 * @param activityName the optional activity name to initialize the workflow
	 * @return the workflow instance information after being successfully
	 *         created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *             created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			Map<String, Object> context, UserCredential userCredential,
			String activityName)
		throws WorkflowException;

	/**
	 * <p>
	 * Creates a new workflow instance and starts the workflow by triggering the
	 * first default activity and returns the information object for it. The
	 * instance is running after the specified workflow definition and is
	 * initialized with the given map of context information, if provided
	 * (optional). The newly created instance is related to the specified domain
	 * object instance and hence can be retrieved later using the
	 * {@link #getWorkflowInstanceInfo(String, long, boolean)} (see its comment
	 * for more information about relations to domain objects).
	 * </p>
	 * 
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 * 
	 * @param workflowDefinitionName the name of the workflow definition to
	 *            create a new workflow instance for
	 * @param workflowDefinitionVersion the optional, specific version of the
	 *            workflow definition, if not provided (<code>null</code>), the
	 *            newest version is used automatically.
	 * @param relationType the unique type identifier of the domain object class
	 *            to create a new workflow instance in relation to
	 * @param relationId the identifier of the domain object instance to create
	 *            a new workflow instance in relation to
	 * @param context the optional map of context information being attached to
	 *            the process instance, the objects contained in the map must be
	 *            serializable in order to be persisted along the workflow
	 *            instance
	 * @param userCredential the user and its role set creating this new
	 *            workflow instance
	 * @return the workflow instance information after being successfully
	 *         created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *             created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			UserCredential userCredential)
		throws WorkflowException;

	/**
	 * <p>
	 * In addition to the
	 * {@link #startWorkflowInstance(String, Integer, String, long, Map, UserCredential)}
	 * this method also supports the starting activity name to be provided to
	 * tell the engine how to start the new workflow. This method depends on the
	 * workflow definition and engine actually, if a specific starting activity
	 * is provided, the activity name provided might be ignored, if the engine
	 * does not support it or if the workflow definition only has one starting
	 * point.
	 * </p>
	 * 
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 * 
	 * @param workflowDefinitionName the name of the workflow definition to
	 *            create a new workflow instance for
	 * @param workflowDefinitionVersion the optional, specific version of the
	 *            workflow definition, if not provided (<code>null</code>), the
	 *            newest version is used automatically
	 * @param relationType the unique type identifier of the domain object class
	 *            to create a new workflow instance in relation to
	 * @param relationId the identifier of the domain object instance to create
	 *            a new workflow instance in relation to
	 * @param context the optional map of context information being attached to
	 *            the process instance, the objects contained in the map must be
	 *            serializable in order to be persisted along the workflow
	 *            instance
	 * @param userCredential the user and its role set creating this new
	 *            workflow instance
	 * @param activityName the optional activity name to initialize the workflow
	 * @return the workflow instance information after being successfully
	 *         created
	 * @throws WorkflowException is thrown, if the new instance could not be
	 *             created or the workflow could not be started
	 */
	public WorkflowInstanceInfo startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String relationType, long relationId, Map<String, Object> context,
			UserCredential userCredential, String activityName)
		throws WorkflowException;

}