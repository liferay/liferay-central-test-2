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

package com.liferay.portlet.softwarecatalog.model;


/**
 * <a href="SCFrameworkVersionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCFrameworkVersion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersion
 * @generated
 */
public class SCFrameworkVersionWrapper implements SCFrameworkVersion {
	public SCFrameworkVersionWrapper(SCFrameworkVersion scFrameworkVersion) {
		_scFrameworkVersion = scFrameworkVersion;
	}

	public long getPrimaryKey() {
		return _scFrameworkVersion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scFrameworkVersion.setPrimaryKey(pk);
	}

	public long getFrameworkVersionId() {
		return _scFrameworkVersion.getFrameworkVersionId();
	}

	public void setFrameworkVersionId(long frameworkVersionId) {
		_scFrameworkVersion.setFrameworkVersionId(frameworkVersionId);
	}

	public long getGroupId() {
		return _scFrameworkVersion.getGroupId();
	}

	public void setGroupId(long groupId) {
		_scFrameworkVersion.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _scFrameworkVersion.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_scFrameworkVersion.setCompanyId(companyId);
	}

	public long getUserId() {
		return _scFrameworkVersion.getUserId();
	}

	public void setUserId(long userId) {
		_scFrameworkVersion.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersion.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_scFrameworkVersion.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _scFrameworkVersion.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_scFrameworkVersion.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _scFrameworkVersion.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_scFrameworkVersion.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _scFrameworkVersion.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_scFrameworkVersion.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _scFrameworkVersion.getName();
	}

	public void setName(java.lang.String name) {
		_scFrameworkVersion.setName(name);
	}

	public java.lang.String getUrl() {
		return _scFrameworkVersion.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_scFrameworkVersion.setUrl(url);
	}

	public boolean getActive() {
		return _scFrameworkVersion.getActive();
	}

	public boolean isActive() {
		return _scFrameworkVersion.isActive();
	}

	public void setActive(boolean active) {
		_scFrameworkVersion.setActive(active);
	}

	public int getPriority() {
		return _scFrameworkVersion.getPriority();
	}

	public void setPriority(int priority) {
		_scFrameworkVersion.setPriority(priority);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion toEscapedModel() {
		return _scFrameworkVersion.toEscapedModel();
	}

	public boolean isNew() {
		return _scFrameworkVersion.isNew();
	}

	public boolean setNew(boolean n) {
		return _scFrameworkVersion.setNew(n);
	}

	public boolean isCachedModel() {
		return _scFrameworkVersion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scFrameworkVersion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scFrameworkVersion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scFrameworkVersion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scFrameworkVersion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scFrameworkVersion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scFrameworkVersion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scFrameworkVersion.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		return _scFrameworkVersion.compareTo(scFrameworkVersion);
	}

	public int hashCode() {
		return _scFrameworkVersion.hashCode();
	}

	public java.lang.String toString() {
		return _scFrameworkVersion.toString();
	}

	public java.lang.String toXmlString() {
		return _scFrameworkVersion.toXmlString();
	}

	public SCFrameworkVersion getWrappedSCFrameworkVersion() {
		return _scFrameworkVersion;
	}

	private SCFrameworkVersion _scFrameworkVersion;
}