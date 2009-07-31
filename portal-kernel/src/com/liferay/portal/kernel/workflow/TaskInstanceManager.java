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

/**
 * <a href="TaskInstanceManager.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The task instance manager handles all tasks related to a workflow system. A
 * task might be part of the workflow definition in order to stop the execution
 * plan as any user needs to complete the task before the execution continues.
 * </p>
 *
 * <p>
 * The next step in a workflow definition is a decision to be made by a user, so
 * this could be modeled by adding a task activity to the workflow definition,
 * stopping the workflow execution and creating a task instance. The task would
 * ask the user for a decision and adds it to the context information available
 * trough the workflow instance and hence any node would have access to it.
 * After the task is completed, the workflow execution automatically continues
 * by executing the decision element using the information entered while
 * completing the task and taking that decision to continue with the execution
 * of the workflow.
 * </p>
 *
 * <p>
 * This is just one scenario where tasks come into play, but basically, a task
 * is just an element preventing the execution of the workflow by delegating it
 * to a user through a task instance.
 * </p>
 *
 * @author Micha Kiener
 */
public interface TaskInstanceManager {

	/**
	 * Assign the task instance with the given id to the specified role with an
	 * optional comment. The task instance information being returned will
	 * reflect those changes made to the task instance.
	 *
	 * @param  taskInstanceId the id of the task instance to be assigned
	 * @param  roleId the role id to assign the task to
	 * @param  comment the optional comment for the assignment
	 * @param  attributes the optional attributes to be passed on to the context
	 *		   information of the workflow instance (they can be empty or even
	 *		   <code>null</code>)
	 * @param  callingUserId the id of the calling user (see {@link
	 *		   WorkflowUtil#createUserCredential(long)} for more information)
	 * @return the task information reflecting the changes made to it
	 * @throws WorkflowException is thrown, if the user could not be assigned
	 */
	public TaskInstanceInfo assignTaskInstanceToRole(
			long taskInstanceId, long roleId, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException;

	/**
	 * Assign the task instance with the given id to the specified user with an
	 * optional comment. The task instance information being returned will
	 * reflect those changes made to the task instance.
	 *
	 * @param  taskInstanceId the id of the task instance to be assigned
	 * @param  userCredential the credential of the user to assign the task to,
	 *		   representing the user's attributes and its role set, a credential
	 *		   can be created using the id of the user through {@link
	 *		   WorkflowUtil#createUserCredential(long)}
	 * @param  comment the optional comment for the user being the new assignee
	 * @param  attributes the optional attributes to be passed on to the context
	 *		   information of the workflow instance (they can be empty or even
	 *		   <code>null</code>)
	 * @param  callingUserId the id of the calling user (see {@link
	 *		   WorkflowUtil#createUserCredential(long)} for more information)
	 * @return the task information reflecting the changes made to it
	 * @throws WorkflowException is thrown, if the user could not be assigned
	 */
	public TaskInstanceInfo assignTaskInstanceToUser(
			long taskInstanceId, UserCredential userCredential, String comment,
			Map<String, Object> attributes, long callingUserId)
		throws WorkflowException;

	/**
	 * <p>
	 * Completes the task instance with the specified id by the given user id
	 * (which should usually be the same as the current assignee, but it's not a
	 * must). Optionally there might be a comment to be left for the completion
	 * of the task and a map with attributes to be passed on to the workflow
	 * instance this task belongs to. If any attributes are provided, they will
	 * be made part of the context information of the workflow instance this
	 * task belongs to and hence available by the workflow actions later through
	 * the context information map.
	 * </p>
	 *
	 * <p>
	 * If the workflow engine supports EL expressions and a special process
	 * scope and Spring is used as the IoC container, the attributes are not
	 * necessary but instead, the information to be provided by task completion
	 * could directly be made to beans having process scope as they would be
	 * stored within the context information of the process instance anyway. As
	 * an alternative, the attribute map might be used for the same effect.
	 * </p>
	 *
	 * <p>
	 * Make sure that all attribute objects are serializable as they will be
	 * persisted along with the process instance.
	 * </p>
	 *
	 * <p>
	 * After the task has been completed, the workflow engine will continue with
	 * the next default activity, if it was not an asynchronous task.
	 * </p>
	 *
	 * @param  taskInstanceId the id of the task instance to be completed
	 * @param  userId the id of the user completing the task
	 * @param  comment an optional comment made by completing the task (just as
	 *		   a comment, not a structured information)
	 * @param  attributes the optional attributes to be passed on to the context
	 *		   information of the workflow instance (they can be empty or even
	 *		   <code>null</code>)
	 * @return the task information reflecting the changes
	 * @throws WorkflowException is thrown, if completing the task failed or the
	 *		   workflow could not be continued
	 */
	public TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String comment,
			Map<String, Object> attributes)
		throws WorkflowException;

	/**
	 * <p>
	 * In addition to the method {@link #completeTaskInstance(long, long,
	 * String, Map)}, this one accepts the name of the activity to be signaled
	 * by completing the given task. The possible next activity names can be
	 * requested through {@link #getPossibleNextActivityNames(long, long)}.
	 * </p>
	 *
	 * <p>
	 * There are two possible strategies to model around tasks: <li>Completing a
	 * task always moves on to the next node which could be a decision and hence
	 * the information needed for the engine to execute the decision would have
	 * been made by the user completing the task, adding those information to
	 * the context of the workflow instance or to the domain object related to
	 * it.</li> <li>Completing the task could list possible next activities to
	 * the user he has to choose one from, so completing the task would be done
	 * using this method by providing the name of the next activity to continue
	 * with, so no additional information has to be stored within the context to
	 * achieve the same goal, however, the documentation of the decision is only
	 * available through the history not through structured information within
	 * the workflow instance or its domain object instance.</li>
	 * </p>
	 *
	 * @param  taskInstanceId the id of the task instance to be completed
	 * @param  userId the id of the user completing the task
	 * @param  activityName the name of the next activity to continue the
	 *		   workflow by completing this task
	 * @param  comment an optional comment made by completing the task (just as
	 *		   a comment, not a structured information)
	 * @param  attributes the optional attributes to be passed on to the context
	 *		   information of the workflow instance (they can be empty or even
	 *		   <code>null</code>)
	 * @return the task information reflecting the changes
	 * @throws WorkflowException is thrown, if completing the task failed or the
	 *		   workflow could not be continued
	 */
	public TaskInstanceInfo completeTaskInstance(
			long taskInstanceId, long userId, String activityName, String comment,
			Map<String, Object> attributes)
		throws WorkflowException;

	/**
	 * <p>
	 * Returns a list of activity names to be possible next activities for the
	 * given task to be passed on through the method {@link
	 * #completeTaskInstance(long, long, String, String, Map)}. The method
	 * should return the same as if invoking {@link
	 * WorkflowInstanceManager#getPossibleNextActivityNames(long, long)} using
	 * the workflow instance id the task is attached to.
	 * </p>
	 *
	 * <p>
	 * The list of possible next activities is checked with the role set of the
	 * specified user to only return activity names the user is allowed to
	 * execute.
	 * </p>
	 *
	 * @param  taskInstanceId the id of the task instance to return a list of
	 *		   possible next activities for
	 * @param  userId the id of the user requesting the list to check the
	 *		   activities being executable by the user
	 * @return the list of activity names possible to follow on by completing
	 *		   the given task
	 * @throws WorkflowException is thrown, if requesting the list failed
	 */
	public List<String> getPossibleNextActivityNames(
			long taskInstanceId, long userId)
		throws WorkflowException;

	/**
	 * Returns the total count of open tasks currently assigned to the user
	 * represented by the given credential or indirectly assigned to one of its
	 * roles. The implementation of this method should be fast.
	 *
	 * @param  userCredential the credential representing the user and its role
	 *		   set to return the count of tasks assigned to either the user or
	 *		   one of its roles
	 * @return the total count of open tasks of the user represented by the
	 *		   credential
	 * @throws WorkflowException is thrown if the querying failed
	 */
	public int getTaskInstanceCountForCredential(UserCredential userCredential)
		throws WorkflowException;

	/**
	 * Returns the total count of open tasks currently assigned to the specified
	 * role. The implementation of this method should be fast.
	 *
	 * @param  roleId the id of the role to count the assigned, open tasks for
	 * @return the total count of open tasks of the specified role
	 * @throws WorkflowException is thrown if the querying failed
	 */
	public int getTaskInstanceCountForRole(long roleId)
		throws WorkflowException;

	/**
	 * Returns the total count of open tasks currently assigned to the specified
	 * user and only to ones directly assigned to the user, not the ones
	 * assigned to one of its roles. The implementation of this method should be
	 * fast.
	 *
	 * @param  userId the id of the user to count the directly assigned, open
	 *		   tasks for
	 * @return the total count of open, directly assigned tasks of the specified
	 *		   user
	 * @throws WorkflowException is thrown if the querying failed
	 */
	public int getTaskInstanceCountForUser(long userId)
		throws WorkflowException;

	/**
	 * Queries for all tasks currently assigned to the user represented by the
	 * given credential or indirectly assigned to one of the roles the user is
	 * assigned to. So a task being returned could either be directly assigned
	 * to the user or assigned to a role the user is a member of. If only open
	 * tasks should be returned, use {@link
	 * #getTaskInstanceInfosByCredential(UserCredential, boolean)} instead.
	 *
	 * @param  userCredential the credential representing the user to return
	 *		   tasks for
	 * @return all tasks either be assigned to the user directly or indirectly
	 *		   assigned to one of its roles
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential)
		throws WorkflowException;

	/**
	 * Queries for all tasks currently assigned to the user represented by the
	 * given credential or indirectly assigned to one of the roles the user is
	 * assigned to. So a task being returned could either be directly assigned
	 * to the user or assigned to a role the user is a member of.
	 *
	 * @param  userCredential the credential representing the user to return
	 *		   tasks for
	 * @param  completed <code>true</code>, if only completed tasks should be
	 *		   returned, <code>false</code> for all open tasks
	 * @return all tasks either be assigned to the user directly or indirectly
	 *		   assigned to one of its roles
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByCredential(
			UserCredential userCredential, boolean completed)
		throws WorkflowException;

	/**
	 * Queries for all tasks currently assigned to the given role. If only open
	 * tasks should be returned, use {@link #getTaskInstanceInfosByRole(long,
	 * boolean)} instead.
	 *
	 * @param  roleId the id of the role to return tasks for
	 * @return all tasks assigned to the given role
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(long roleId)
		throws WorkflowException;

	/**
	 * Queries for open or completed tasks currently assigned to the given role.
	 *
	 * @param  roleId the id of the role to return tasks for
	 * @param  completed <code>true</code>, if only completed tasks should be
	 *		   returned, <code>false</code> for all open tasks
	 * @return all open or completed tasks assigned to the given role
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByRole(
			long roleId, boolean completed)
		throws WorkflowException;

	/**
	 * Queries for all tasks currently assigned to the given user. If only open
	 * tasks should be returned, use {@link #getTaskInstanceInfosByUser(long,
	 * boolean)} instead.
	 *
	 * @param  userId the id of the user to return tasks for
	 * @return all tasks assigned to the given user
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(long userId)
		throws WorkflowException;

	/**
	 * Queries for open or completed tasks currently assigned to the given user.
	 *
	 * @param  userId the id of the user to return tasks for
	 * @param  completed <code>true</code>, if only completed tasks should be
	 *		   returned, <code>false</code> for all open tasks
	 * @return all open or completed tasks assigned to the given user
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByUser(
			long userId, boolean completed)
		throws WorkflowException;

	/**
	 * Queries for all tasks for the specified workflow instance. If only open
	 * tasks should be returned, use {@link
	 * #getTaskInstanceInfosByWorkflowInstance(long, boolean)} instead.
	 *
	 * @param  workflowInstanceId the id of the workflow instance to return
	 *		   tasks for
	 * @return all tasks related to the specified workflow instance
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId)
		throws WorkflowException;

	/**
	 * Queries for open or completed tasks for the specified workflow instance.
	 *
	 * @param  workflowInstanceId the id of the workflow instance to return
	 *		   tasks for
	 * @param  completed <code>true</code>, if only completed tasks should be
	 *		   returned, <code>false</code> for all open tasks
	 * @return all completed or open tasks related to the specified workflow
	 *		   instance
	 * @throws WorkflowException is thrown if querying failed
	 */
	public List<TaskInstanceInfo> getTaskInstanceInfosByWorkflowInstance(
			long workflowInstanceId, boolean completed)
		throws WorkflowException;

}