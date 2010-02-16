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
 * <a href="WorkflowInstanceLinkSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowInstanceLink}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLink
 * @generated
 */
public class WorkflowInstanceLinkWrapper implements WorkflowInstanceLink {
	public WorkflowInstanceLinkWrapper(
		WorkflowInstanceLink workflowInstanceLink) {
		_workflowInstanceLink = workflowInstanceLink;
	}

	public long getPrimaryKey() {
		return _workflowInstanceLink.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_workflowInstanceLink.setPrimaryKey(pk);
	}

	public long getWorkflowInstanceLinkId() {
		return _workflowInstanceLink.getWorkflowInstanceLinkId();
	}

	public void setWorkflowInstanceLinkId(long workflowInstanceLinkId) {
		_workflowInstanceLink.setWorkflowInstanceLinkId(workflowInstanceLinkId);
	}

	public long getGroupId() {
		return _workflowInstanceLink.getGroupId();
	}

	public void setGroupId(long groupId) {
		_workflowInstanceLink.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _workflowInstanceLink.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_workflowInstanceLink.setCompanyId(companyId);
	}

	public long getUserId() {
		return _workflowInstanceLink.getUserId();
	}

	public void setUserId(long userId) {
		_workflowInstanceLink.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _workflowInstanceLink.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_workflowInstanceLink.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _workflowInstanceLink.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_workflowInstanceLink.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _workflowInstanceLink.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_workflowInstanceLink.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _workflowInstanceLink.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_workflowInstanceLink.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _workflowInstanceLink.getClassName();
	}

	public long getClassNameId() {
		return _workflowInstanceLink.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_workflowInstanceLink.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _workflowInstanceLink.getClassPK();
	}

	public void setClassPK(long classPK) {
		_workflowInstanceLink.setClassPK(classPK);
	}

	public long getWorkflowInstanceId() {
		return _workflowInstanceLink.getWorkflowInstanceId();
	}

	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);
	}

	public com.liferay.portal.model.WorkflowInstanceLink toEscapedModel() {
		return _workflowInstanceLink.toEscapedModel();
	}

	public boolean isNew() {
		return _workflowInstanceLink.isNew();
	}

	public boolean setNew(boolean n) {
		return _workflowInstanceLink.setNew(n);
	}

	public boolean isCachedModel() {
		return _workflowInstanceLink.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_workflowInstanceLink.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _workflowInstanceLink.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_workflowInstanceLink.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _workflowInstanceLink.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _workflowInstanceLink.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_workflowInstanceLink.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _workflowInstanceLink.clone();
	}

	public int compareTo(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		return _workflowInstanceLink.compareTo(workflowInstanceLink);
	}

	public int hashCode() {
		return _workflowInstanceLink.hashCode();
	}

	public java.lang.String toString() {
		return _workflowInstanceLink.toString();
	}

	public java.lang.String toXmlString() {
		return _workflowInstanceLink.toXmlString();
	}

	public WorkflowInstanceLink getWrappedWorkflowInstanceLink() {
		return _workflowInstanceLink;
	}

	private WorkflowInstanceLink _workflowInstanceLink;
}