/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.io.InputStream;

import java.util.List;

/**
 * <a href="WorkflowDefinitionManagerProxyBean.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionManagerProxyBean
	extends BaseProxyBean implements WorkflowDefinitionManager {

	public WorkflowDefinition deployWorkflowDefinition(
		long userId, String name, InputStream inputStream) {

		throw new UnsupportedOperationException();
	}

	public WorkflowDefinition getWorkflowDefinition(String name, int version) {
		throw new UnsupportedOperationException();
	}

	public int getWorkflowDefinitionCount() {
		throw new UnsupportedOperationException();
	}

	public int getWorkflowDefinitionCount(String name) {
		throw new UnsupportedOperationException();
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
		int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
		String name, int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	public void undeployWorkflowDefinition(
		long userId, String name, int version) {

		throw new UnsupportedOperationException();
	}

}