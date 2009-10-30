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

package com.liferay.portal.model;


/**
 * <a href="WorkflowLinkSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLink
 * @generated
 */
public class WorkflowLinkWrapper implements WorkflowLink {
	public WorkflowLinkWrapper(WorkflowLink workflowLink) {
		_workflowLink = workflowLink;
	}

	public long getPrimaryKey() {
		return _workflowLink.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_workflowLink.setPrimaryKey(pk);
	}

	public long getWorkflowLinkId() {
		return _workflowLink.getWorkflowLinkId();
	}

	public void setWorkflowLinkId(long workflowLinkId) {
		_workflowLink.setWorkflowLinkId(workflowLinkId);
	}

	public long getGroupId() {
		return _workflowLink.getGroupId();
	}

	public void setGroupId(long groupId) {
		_workflowLink.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _workflowLink.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_workflowLink.setCompanyId(companyId);
	}

	public long getUserId() {
		return _workflowLink.getUserId();
	}

	public void setUserId(long userId) {
		_workflowLink.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _workflowLink.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_workflowLink.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _workflowLink.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_workflowLink.setUserName(userName);
	}

	public java.util.Date getModifiedDate() {
		return _workflowLink.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowLink.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _workflowLink.getClassName();
	}

	public long getClassNameId() {
		return _workflowLink.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_workflowLink.setClassNameId(classNameId);
	}

	public java.lang.String getWorkflowDefinitionName() {
		return _workflowLink.getWorkflowDefinitionName();
	}

	public void setWorkflowDefinitionName(
		java.lang.String workflowDefinitionName) {
		_workflowLink.setWorkflowDefinitionName(workflowDefinitionName);
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowLink.getWorkflowDefinitionVersion();
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowLink.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	public com.liferay.portal.model.WorkflowLink toEscapedModel() {
		return _workflowLink.toEscapedModel();
	}

	public boolean isNew() {
		return _workflowLink.isNew();
	}

	public boolean setNew(boolean n) {
		return _workflowLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _workflowLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_workflowLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _workflowLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_workflowLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _workflowLink.clone();
	}

	public int compareTo(com.liferay.portal.model.WorkflowLink workflowLink) {
		return _workflowLink.compareTo(workflowLink);
	}

	public int hashCode() {
		return _workflowLink.hashCode();
	}

	public java.lang.String toString() {
		return _workflowLink.toString();
	}

	public java.lang.String toXmlString() {
		return _workflowLink.toXmlString();
	}

	public WorkflowLink getWrappedWorkflowLink() {
		return _workflowLink;
	}

	private WorkflowLink _workflowLink;
}