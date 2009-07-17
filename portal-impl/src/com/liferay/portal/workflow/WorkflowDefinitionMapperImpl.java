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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.workflow.WorkflowDefinitionMapper;
import com.liferay.portal.util.PropsUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="WorkflowDefinitionMapperImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class WorkflowDefinitionMapperImpl implements WorkflowDefinitionMapper{

	public void init() {
		Properties props =
			PropsUtil.getProperties("workflow.definition.name.", true);
		if (props != null) {
			for (Object key : props.keySet()) {
				_workflowDefinitionNames.put(
					(String)key, props.getProperty((String)key));
			}
		}
	}

	public String getWorkflowDefinitionName(Class<?> domainClass) {
		return _workflowDefinitionNames.get(domainClass.getName());
	}

	public boolean hasWorkflowDefinitionMapping(Class<?> domainClass) {
		return _workflowDefinitionNames.containsKey(domainClass.getName());
	}

	public String setWorkflowDefinitionMapping(Class<?> domainClass,
		String workflowDefinitionName) {
		return _workflowDefinitionNames.put(
			domainClass.getName(), workflowDefinitionName);
	}

	private Map<String, String> _workflowDefinitionNames =
		new HashMap<String, String>();

}