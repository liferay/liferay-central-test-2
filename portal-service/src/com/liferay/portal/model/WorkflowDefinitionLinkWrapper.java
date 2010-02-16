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

package com.liferay.portal.model;


/**
 * <a href="WorkflowDefinitionLinkSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowDefinitionLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLink
 * @generated
 */
public class WorkflowDefinitionLinkWrapper implements WorkflowDefinitionLink {
	public WorkflowDefinitionLinkWrapper(
		WorkflowDefinitionLink workflowDefinitionLink) {
		_workflowDefinitionLink = workflowDefinitionLink;
	}

	public long getPrimaryKey() {
		return _workflowDefinitionLink.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_workflowDefinitionLink.setPrimaryKey(pk);
	}

	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLink.getWorkflowDefinitionLinkId();
	}

	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLink.setWorkflowDefinitionLinkId(workflowDefinitionLinkId);
	}

	public long getGroupId() {
		return _workflowDefinitionLink.getGroupId();
	}

	public void setGroupId(long groupId) {
		_workflowDefinitionLink.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _workflowDefinitionLink.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_workflowDefinitionLink.setCompanyId(companyId);
	}

	public long getUserId() {
		return _workflowDefinitionLink.getUserId();
	}

	public void setUserId(long userId) {
		_workflowDefinitionLink.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowDefinitionLink.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_workflowDefinitionLink.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _workflowDefinitionLink.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_workflowDefinitionLink.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _workflowDefinitionLink.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_workflowDefinitionLink.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _workflowDefinitionLink.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowDefinitionLink.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _workflowDefinitionLink.getClassName();
	}

	public long getClassNameId() {
		return _workflowDefinitionLink.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_workflowDefinitionLink.setClassNameId(classNameId);
	}

	public java.lang.String getWorkflowDefinitionName() {
		return _workflowDefinitionLink.getWorkflowDefinitionName();
	}

	public void setWorkflowDefinitionName(
		java.lang.String workflowDefinitionName) {
		_workflowDefinitionLink.setWorkflowDefinitionName(workflowDefinitionName);
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionLink.getWorkflowDefinitionVersion();
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionLink.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	public com.liferay.portal.model.WorkflowDefinitionLink toEscapedModel() {
		return _workflowDefinitionLink.toEscapedModel();
	}

	public boolean isNew() {
		return _workflowDefinitionLink.isNew();
	}

	public boolean setNew(boolean n) {
		return _workflowDefinitionLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _workflowDefinitionLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_workflowDefinitionLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _workflowDefinitionLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_workflowDefinitionLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowDefinitionLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowDefinitionLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowDefinitionLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _workflowDefinitionLink.clone();
	}

	public int compareTo(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink) {
		return _workflowDefinitionLink.compareTo(workflowDefinitionLink);
	}

	public int hashCode() {
		return _workflowDefinitionLink.hashCode();
	}

	public java.lang.String toString() {
		return _workflowDefinitionLink.toString();
	}

	public java.lang.String toXmlString() {
		return _workflowDefinitionLink.toXmlString();
	}

	public WorkflowDefinitionLink getWrappedWorkflowDefinitionLink() {
		return _workflowDefinitionLink;
	}

	private WorkflowDefinitionLink _workflowDefinitionLink;
}