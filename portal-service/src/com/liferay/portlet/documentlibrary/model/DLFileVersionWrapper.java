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

package com.liferay.portlet.documentlibrary.model;


/**
 * <a href="DLFileVersionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileVersion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileVersion
 * @generated
 */
public class DLFileVersionWrapper implements DLFileVersion {
	public DLFileVersionWrapper(DLFileVersion dlFileVersion) {
		_dlFileVersion = dlFileVersion;
	}

	public long getPrimaryKey() {
		return _dlFileVersion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileVersion.setPrimaryKey(pk);
	}

	public long getFileVersionId() {
		return _dlFileVersion.getFileVersionId();
	}

	public void setFileVersionId(long fileVersionId) {
		_dlFileVersion.setFileVersionId(fileVersionId);
	}

	public long getGroupId() {
		return _dlFileVersion.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileVersion.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileVersion.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileVersion.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileVersion.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileVersion.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileVersion.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _dlFileVersion.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_dlFileVersion.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _dlFileVersion.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileVersion.setCreateDate(createDate);
	}

	public long getFolderId() {
		return _dlFileVersion.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileVersion.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileVersion.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileVersion.setName(name);
	}

	public java.lang.String getDescription() {
		return _dlFileVersion.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_dlFileVersion.setDescription(description);
	}

	public double getVersion() {
		return _dlFileVersion.getVersion();
	}

	public void setVersion(double version) {
		_dlFileVersion.setVersion(version);
	}

	public int getSize() {
		return _dlFileVersion.getSize();
	}

	public void setSize(int size) {
		_dlFileVersion.setSize(size);
	}

	public int getStatus() {
		return _dlFileVersion.getStatus();
	}

	public void setStatus(int status) {
		_dlFileVersion.setStatus(status);
	}

	public long getStatusByUserId() {
		return _dlFileVersion.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_dlFileVersion.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileVersion.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_dlFileVersion.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _dlFileVersion.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_dlFileVersion.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _dlFileVersion.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_dlFileVersion.setStatusDate(statusDate);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileVersion toEscapedModel() {
		return _dlFileVersion.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileVersion.isNew();
	}

	public boolean setNew(boolean n) {
		return _dlFileVersion.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileVersion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileVersion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileVersion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileVersion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileVersion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileVersion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileVersion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileVersion.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion) {
		return _dlFileVersion.compareTo(dlFileVersion);
	}

	public int hashCode() {
		return _dlFileVersion.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileVersion.toXmlString();
	}

	public boolean isApproved() {
		return _dlFileVersion.isApproved();
	}

	public DLFileVersion getWrappedDLFileVersion() {
		return _dlFileVersion;
	}

	private DLFileVersion _dlFileVersion;
}