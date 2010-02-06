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

package com.liferay.portlet.messageboards.model;


/**
 * <a href="MBCategorySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBCategory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBCategory
 * @generated
 */
public class MBCategoryWrapper implements MBCategory {
	public MBCategoryWrapper(MBCategory mbCategory) {
		_mbCategory = mbCategory;
	}

	public long getPrimaryKey() {
		return _mbCategory.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbCategory.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _mbCategory.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_mbCategory.setUuid(uuid);
	}

	public long getCategoryId() {
		return _mbCategory.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_mbCategory.setCategoryId(categoryId);
	}

	public long getGroupId() {
		return _mbCategory.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbCategory.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _mbCategory.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_mbCategory.setCompanyId(companyId);
	}

	public long getUserId() {
		return _mbCategory.getUserId();
	}

	public void setUserId(long userId) {
		_mbCategory.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbCategory.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbCategory.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _mbCategory.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_mbCategory.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _mbCategory.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_mbCategory.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _mbCategory.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbCategory.setModifiedDate(modifiedDate);
	}

	public long getParentCategoryId() {
		return _mbCategory.getParentCategoryId();
	}

	public void setParentCategoryId(long parentCategoryId) {
		_mbCategory.setParentCategoryId(parentCategoryId);
	}

	public java.lang.String getName() {
		return _mbCategory.getName();
	}

	public void setName(java.lang.String name) {
		_mbCategory.setName(name);
	}

	public java.lang.String getDescription() {
		return _mbCategory.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_mbCategory.setDescription(description);
	}

	public int getThreadCount() {
		return _mbCategory.getThreadCount();
	}

	public void setThreadCount(int threadCount) {
		_mbCategory.setThreadCount(threadCount);
	}

	public int getMessageCount() {
		return _mbCategory.getMessageCount();
	}

	public void setMessageCount(int messageCount) {
		_mbCategory.setMessageCount(messageCount);
	}

	public java.util.Date getLastPostDate() {
		return _mbCategory.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbCategory.setLastPostDate(lastPostDate);
	}

	public com.liferay.portlet.messageboards.model.MBCategory toEscapedModel() {
		return _mbCategory.toEscapedModel();
	}

	public boolean isNew() {
		return _mbCategory.isNew();
	}

	public boolean setNew(boolean n) {
		return _mbCategory.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbCategory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbCategory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbCategory.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbCategory.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbCategory.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbCategory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbCategory.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbCategory.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBCategory mbCategory) {
		return _mbCategory.compareTo(mbCategory);
	}

	public int hashCode() {
		return _mbCategory.hashCode();
	}

	public java.lang.String toString() {
		return _mbCategory.toString();
	}

	public java.lang.String toXmlString() {
		return _mbCategory.toXmlString();
	}

	public java.util.List<Long> getAncestorCategoryIds()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbCategory.getAncestorCategoryIds();
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getAncestors()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbCategory.getAncestors();
	}

	public boolean isRoot() {
		return _mbCategory.isRoot();
	}

	public MBCategory getWrappedMBCategory() {
		return _mbCategory;
	}

	private MBCategory _mbCategory;
}