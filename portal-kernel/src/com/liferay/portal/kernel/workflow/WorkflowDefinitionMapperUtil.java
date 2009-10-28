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
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionMapperUtil {

	public static WorkflowDefinitionMapper getWorkflowDefinitionMapper() {
		return _workflowDefinitionMapper;
	}

	public static String getWorkflowDefinitionName(Class<?> domainClass) {
		return _workflowDefinitionMapper.getWorkflowDefinitionName(domainClass);
	}

	public static boolean hasWorkflowDefinitionMapping(Class<?> domainClass) {
		return _workflowDefinitionMapper.hasWorkflowDefinitionMapping(
			domainClass);
	}

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