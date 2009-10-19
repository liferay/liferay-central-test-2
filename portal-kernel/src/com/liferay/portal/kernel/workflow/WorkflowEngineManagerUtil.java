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

import java.util.Map;

/**
 * <a href="WorkflowEngineManagerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * The utility class supporting static access to all methods for the {@link
 * WorkflowEngineManager} interface. The target manager object is injected using
 * the {@link #setWorkflowEngineManager(WorkflowEngineManager)} method. Besides
 * the static method access, it is also available through {@link
 * #getWorkflowEngineManager()}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class WorkflowEngineManagerUtil {

	/**
	 * @see WorkflowEngineManager#getAdditionalInformation()
	 */
	public static Map<String, Object> getAdditionalInformation() {
		return _workflowEngineManager.getAdditionalInformation();
	}

	/**
	 * @see WorkflowEngineManager#getDelegate()
	 */
	public static Object getDelegate() {
		return _workflowEngineManager.getDelegate();
	}

	/**
	 * @see WorkflowEngineManager#getVersion()
	 */
	public static String getVersion() {
		return _workflowEngineManager.getVersion();
	}

	public static WorkflowEngineManager getWorkflowEngineManager() {
		return _workflowEngineManager;
	}

	/**
	 * @see WorkflowEngineManager#getWorkflowEngineKey()
	 */
	public static String getWorkflowEngineKey() {
		return _workflowEngineManager.getWorkflowEngineKey();
	}

	/**
	 * @see WorkflowEngineManager#getWorkflowEngineName()
	 */
	public static String getWorkflowEngineName() {
		return _workflowEngineManager.getWorkflowEngineName();
	}

	/**
	 * @see WorkflowEngineManager#isSupportsGlobalActivities()
	 */
	public static boolean isSupportsGlobalActivities() {
		return _workflowEngineManager.isSupportsGlobalActivities();
	}

	/**
	 * @see WorkflowEngineManager#isSupportsWorkflowDefinitionVersioning()
	 */
	public static boolean isSupportsWorkflowDefinitionVersioning() {
		return _workflowEngineManager.isSupportsWorkflowDefinitionVersioning();
	}

	public void setWorkflowEngineManager(
		WorkflowEngineManager workflowEngineManager) {

		_workflowEngineManager = workflowEngineManager;
	}

	private static WorkflowEngineManager _workflowEngineManager;

}