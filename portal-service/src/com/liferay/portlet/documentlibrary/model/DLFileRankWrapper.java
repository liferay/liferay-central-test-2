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
 * <a href="DLFileRankSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link DLFileRank}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRank
 * @generated
 */
public class DLFileRankWrapper implements DLFileRank {
	public DLFileRankWrapper(DLFileRank dlFileRank) {
		_dlFileRank = dlFileRank;
	}

	public long getPrimaryKey() {
		return _dlFileRank.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_dlFileRank.setPrimaryKey(pk);
	}

	public long getFileRankId() {
		return _dlFileRank.getFileRankId();
	}

	public void setFileRankId(long fileRankId) {
		_dlFileRank.setFileRankId(fileRankId);
	}

	public long getGroupId() {
		return _dlFileRank.getGroupId();
	}

	public void setGroupId(long groupId) {
		_dlFileRank.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _dlFileRank.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_dlFileRank.setCompanyId(companyId);
	}

	public long getUserId() {
		return _dlFileRank.getUserId();
	}

	public void setUserId(long userId) {
		_dlFileRank.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileRank.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_dlFileRank.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _dlFileRank.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_dlFileRank.setCreateDate(createDate);
	}

	public long getFolderId() {
		return _dlFileRank.getFolderId();
	}

	public void setFolderId(long folderId) {
		_dlFileRank.setFolderId(folderId);
	}

	public java.lang.String getName() {
		return _dlFileRank.getName();
	}

	public void setName(java.lang.String name) {
		_dlFileRank.setName(name);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileRank toEscapedModel() {
		return _dlFileRank.toEscapedModel();
	}

	public boolean isNew() {
		return _dlFileRank.isNew();
	}

	public boolean setNew(boolean n) {
		return _dlFileRank.setNew(n);
	}

	public boolean isCachedModel() {
		return _dlFileRank.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_dlFileRank.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _dlFileRank.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_dlFileRank.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _dlFileRank.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _dlFileRank.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_dlFileRank.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _dlFileRank.clone();
	}

	public int compareTo(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank) {
		return _dlFileRank.compareTo(dlFileRank);
	}

	public int hashCode() {
		return _dlFileRank.hashCode();
	}

	public java.lang.String toString() {
		return _dlFileRank.toString();
	}

	public java.lang.String toXmlString() {
		return _dlFileRank.toXmlString();
	}

	public DLFileRank getWrappedDLFileRank() {
		return _dlFileRank;
	}

	private DLFileRank _dlFileRank;
}