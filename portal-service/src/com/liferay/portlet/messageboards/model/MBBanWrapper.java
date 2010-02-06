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
 * <a href="MBBanSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBBan}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBan
 * @generated
 */
public class MBBanWrapper implements MBBan {
	public MBBanWrapper(MBBan mbBan) {
		_mbBan = mbBan;
	}

	public long getPrimaryKey() {
		return _mbBan.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbBan.setPrimaryKey(pk);
	}

	public long getBanId() {
		return _mbBan.getBanId();
	}

	public void setBanId(long banId) {
		_mbBan.setBanId(banId);
	}

	public long getGroupId() {
		return _mbBan.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbBan.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _mbBan.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_mbBan.setCompanyId(companyId);
	}

	public long getUserId() {
		return _mbBan.getUserId();
	}

	public void setUserId(long userId) {
		_mbBan.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbBan.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbBan.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _mbBan.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_mbBan.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _mbBan.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_mbBan.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _mbBan.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_mbBan.setModifiedDate(modifiedDate);
	}

	public long getBanUserId() {
		return _mbBan.getBanUserId();
	}

	public void setBanUserId(long banUserId) {
		_mbBan.setBanUserId(banUserId);
	}

	public java.lang.String getBanUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbBan.getBanUserUuid();
	}

	public void setBanUserUuid(java.lang.String banUserUuid) {
		_mbBan.setBanUserUuid(banUserUuid);
	}

	public com.liferay.portlet.messageboards.model.MBBan toEscapedModel() {
		return _mbBan.toEscapedModel();
	}

	public boolean isNew() {
		return _mbBan.isNew();
	}

	public boolean setNew(boolean n) {
		return _mbBan.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbBan.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbBan.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbBan.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbBan.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbBan.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbBan.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbBan.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbBan.clone();
	}

	public int compareTo(com.liferay.portlet.messageboards.model.MBBan mbBan) {
		return _mbBan.compareTo(mbBan);
	}

	public int hashCode() {
		return _mbBan.hashCode();
	}

	public java.lang.String toString() {
		return _mbBan.toString();
	}

	public java.lang.String toXmlString() {
		return _mbBan.toXmlString();
	}

	public MBBan getWrappedMBBan() {
		return _mbBan;
	}

	private MBBan _mbBan;
}