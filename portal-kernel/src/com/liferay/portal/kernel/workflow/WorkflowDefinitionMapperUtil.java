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

/**
 * <a href="WorkflowDefinitionMapperUtil.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * The utility class supporting static access to all methods for the {@link
 * WorkflowDefinitionMapper} interface. The target manager object is injected
 * using the {@link #setWorkflowDefinitionMapper(WorkflowDefinitionMapper)}
 * method. Besides the static method access, it is also available through {@link
 * #getWorkflowDefinitionMapper()}.
 * </p>
 *
 * @author Micha Kiener
 */
public class WorkflowDefinitionMapperUtil {

	public static WorkflowDefinitionMapper getWorkflowDefinitionMapper() {
		return _workflowDefinitionMapper;
	}

	/**
	 * @see WorkflowDefinitionMapper#getWorkflowDefinitionName(Class)
	 */
	public static String getWorkflowDefinitionName(Class<?> domainClass) {
		return _workflowDefinitionMapper.getWorkflowDefinitionName(domainClass);
	}

	/**
	 * @see WorkflowDefinitionMapper#hasWorkflowDefinitionMapping(Class)
	 */
	public static boolean hasWorkflowDefinitionMapping(Class<?> domainClass) {
		return _workflowDefinitionMapper.hasWorkflowDefinitionMapping(
			domainClass);
	}

	/**
	 * @see WorkflowDefinitionMapper#setWorkflowDefinitionMapping(Class, String)
	 */
	public static String setWorkflowDefinitionMapping(
		Class<?> domainClass, String workflowDefinitionName) {

		return _workflowDefinitionMapper.setWorkflowDefinitionMapping(
			domainClass, workflowDefinitionName);
	}

	public void setWorkflowDefinitionMapper(
		WorkflowDefinitionMapper workflowDefinitionMapper) {

		_workflowDefinitionMapper = workflowDefinitionMapper;
	}

	private static WorkflowDefinitionMapper _workflowDefinitionMapper;

}