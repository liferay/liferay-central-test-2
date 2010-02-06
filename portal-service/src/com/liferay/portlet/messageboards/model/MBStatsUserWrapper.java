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
 * <a href="MBStatsUserSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBStatsUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUser
 * @generated
 */
public class MBStatsUserWrapper implements MBStatsUser {
	public MBStatsUserWrapper(MBStatsUser mbStatsUser) {
		_mbStatsUser = mbStatsUser;
	}

	public long getPrimaryKey() {
		return _mbStatsUser.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbStatsUser.setPrimaryKey(pk);
	}

	public long getStatsUserId() {
		return _mbStatsUser.getStatsUserId();
	}

	public void setStatsUserId(long statsUserId) {
		_mbStatsUser.setStatsUserId(statsUserId);
	}

	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbStatsUser.getStatsUserUuid();
	}

	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_mbStatsUser.setStatsUserUuid(statsUserUuid);
	}

	public long getGroupId() {
		return _mbStatsUser.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbStatsUser.setGroupId(groupId);
	}

	public long getUserId() {
		return _mbStatsUser.getUserId();
	}

	public void setUserId(long userId) {
		_mbStatsUser.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbStatsUser.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_mbStatsUser.setUserUuid(userUuid);
	}

	public int getMessageCount() {
		return _mbStatsUser.getMessageCount();
	}

	public void setMessageCount(int messageCount) {
		_mbStatsUser.setMessageCount(messageCount);
	}

	public java.util.Date getLastPostDate() {
		return _mbStatsUser.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbStatsUser.setLastPostDate(lastPostDate);
	}

	public com.liferay.portlet.messageboards.model.MBStatsUser toEscapedModel() {
		return _mbStatsUser.toEscapedModel();
	}

	public boolean isNew() {
		return _mbStatsUser.isNew();
	}

	public boolean setNew(boolean n) {
		return _mbStatsUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbStatsUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbStatsUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbStatsUser.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbStatsUser.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbStatsUser.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbStatsUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbStatsUser.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		return _mbStatsUser.compareTo(mbStatsUser);
	}

	public int hashCode() {
		return _mbStatsUser.hashCode();
	}

	public java.lang.String toString() {
		return _mbStatsUser.toString();
	}

	public java.lang.String toXmlString() {
		return _mbStatsUser.toXmlString();
	}

	public MBStatsUser getWrappedMBStatsUser() {
		return _mbStatsUser;
	}

	private MBStatsUser _mbStatsUser;
}