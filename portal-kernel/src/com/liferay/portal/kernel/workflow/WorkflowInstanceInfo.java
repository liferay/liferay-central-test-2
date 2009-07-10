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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstanceInfo.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This interface represents the information for a single workflow instance
 * created and loaded through the {@link WorkflowInstanceManager} where you can
 * find further descriptions about how to start and work with workflow
 * instances.
 * </p>
 *
 * <p>
 * A workflow instance is always created in connection with a workflow
 * definition and optionally related to a domain object instance. The workflow
 * instance is the element used to track the current state of a workflow and its
 * history and any other related entities like tasks, timers and jobs.
 * </p>
 *
 * <p>
 * Usually this interface is not implemented by the native process instance
 * depending on the underlying workflow engine but is rather returned as a proxy
 * information object just holding the necessary information to represent the
 * native workflow instance.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public interface WorkflowInstanceInfo {

	/**
	 * If this is a parent workflow instance, its children are being returned
	 * using this method. A child workflow instance is usually being created
	 * automatically by the engine to split the process flow into parallel
	 * processing (like a fork), so every child instance has its own track of
	 * activities and information related to it.
	 *
	 * @return the list of children, if any, an empty list otherwise (never
	 * <code>null</code>)
	 */
	public List<WorkflowInstanceInfo> getChildren();

	/**
	 * <p>
	 * Returns the map with context information as being provided while creating
	 * this workflow instance. Depending on the underlying engine, this map is
	 * usually being persisted along with the workflow instance for later
	 * retrieval and referencing within the workflow definition.
	 * </p>
	 *
	 * <p>
	 * If the engine also supports EL functionality within the workflow
	 * definition, this map could even be extended during the execution of the
	 * workflow as new, process scoped beans could be created and stored within
	 * this context map.
	 * </p>
	 *
	 * <p>
	 * The context map is a good place to store information needed during the
	 * execution plan of the workflow, usually used to trigger actions or to
	 * relate to for conditions of decisions and so on. If the context
	 * information is persisted (which is usually the case), make sure that all
	 * objects stored within the map are serializable.
	 * </p>
	 *
	 * <p>
	 * <b>NEVER</b> use the map returned by this information object to
	 * additionally store information in it as it is read only and would not
	 * affect the native map used by the engine. To add any additional
	 * information to the context, use
	 * {@link WorkflowInstanceManager#addContextInformation(long, Map)} instead.
	 * </p>
	 *
	 * @return the map with context information for this workflow instance
	 */
	public Map<String, Object> getContext();

	/**
	 * <p>
	 * Returns the name of the current node this process instance is waiting in
	 * which is most likely a state, a task or a fork preventing the engine from
	 * proceeding with the workflow graph execution.
	 * </p>
	 *
	 * <p>
	 * If the name returned is a fork, every branch of the fork is represented
	 * as own process instances related to this one as children. If the current
	 * node is a task, there must be a workflow task to be completed (fulfilled)
	 * in order to advance the workflow.
	 * </p>
	 *
	 * @return the name of the current node this process instance is waiting in
	 */
	public String getCurrentNodeName();

	/**
	 * Returns the end date of this workflow instance which is the same as the
	 * last modification date when the instance has been finished.
	 *
	 * @return the end date, if ended already, <code>null</code> otherwise
	 */
	public Date getEndDate();

	/**
	 * If this is a child workflow instance, its parent instance is being
	 * returned by this method. See {@link #getChildren()} for more details
	 * about parent - child relationship.
	 *
	 * @return the parent instance of this child instance or <code>null</code>
	 * if this is the root instance
	 */
	public WorkflowInstanceInfo getParent();

	/**
	 * <p>
	 * If this workflow instance was created in relation of a domain object
	 * instance, its identifier is returned by this method.
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @return the domain object identifier, if related to one,
	 * <code>null</code> otherwise
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfo(String, long) for
	 * more details about related domain objects
	 */
	public long getRelationId();

	/**
	 * <p>
	 * If this workflow instance was created in relation of a domain object
	 * instance, its type identifier is returned by this method.
	 * </p>
	 *
	 * <p>
	 * <b><i>Note</i></b> The support of a related domain object however is
	 * optional and might not be supported by the underlying engine.
	 * </p>
	 *
	 * @return the domain object type identifier, if related to one,
	 * <code>null</code> otherwise
	 * @see WorkflowInstanceManager#getWorkflowInstanceInfo(String, long) for
	 * more details about related domain objects
	 */
	public String getRelationType();

	/**
	 * Returns the date and time of the creation time of this workflow instance.
	 *
	 * @return the start date
	 */
	public Date getStartDate();

	/**
	 * Returns the workflow definition name this instance was created in
	 * relation with. Must never be <code>null</code>.
	 *
	 * @return the workflow definition name this instance is running for
	 */
	public String getWorkflowDefinitionName();

	/**
	 * Returns the version of the workflow definition this instance was created
	 * in relation with. If the underlying engine does not support definition
	 * versioning, this might be ignored. A workflow instance created with a
	 * certain version of a definition must not be changed to another one as
	 * this could compromise its current state, history and related objects like
	 * tasks, jobs and timers.
	 *
	 * @return the version of the definition this instance was created for
	 */
	public int getWorkflowDefinitionVersion();

	/**
	 * Returns the unique identifier of the workflow instance object which is
	 * usually the primary key of the persisted object instance.
	 *
	 * @return the identifier of the workflow instance
	 */
	public long getWorkflowInstanceId();

}