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

/**
 * <a href="WorkflowDefinitionKey.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class WorkflowDefinitionKey {

	public WorkflowDefinitionKey(
		String workflowDefinitionName, int workflowDefinitionVersion) {
		_workflowDefinitionName = workflowDefinitionName;
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final WorkflowDefinitionKey other = (WorkflowDefinitionKey) obj;
		if ((this._workflowDefinitionName == null) ?
			(other._workflowDefinitionName != null) :
			!this._workflowDefinitionName.equals(other._workflowDefinitionName))
		{
			return false;
		}
		if (this._workflowDefinitionVersion != other._workflowDefinitionVersion)
		{
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 71 * hash +
			(this._workflowDefinitionName != null ?
				this._workflowDefinitionName.hashCode() : 0);
		hash = 71 * hash + this._workflowDefinitionVersion;
		return hash;
	}

	private final String _workflowDefinitionName;
	private final int _workflowDefinitionVersion;

}