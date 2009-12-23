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

package com.liferay.portlet.messageboards.model;


/**
 * <a href="MBThreadSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBThread}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThread
 * @generated
 */
public class MBThreadWrapper implements MBThread {
	public MBThreadWrapper(MBThread mbThread) {
		_mbThread = mbThread;
	}

	public long getPrimaryKey() {
		return _mbThread.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbThread.setPrimaryKey(pk);
	}

	public long getThreadId() {
		return _mbThread.getThreadId();
	}

	public void setThreadId(long threadId) {
		_mbThread.setThreadId(threadId);
	}

	public long getGroupId() {
		return _mbThread.getGroupId();
	}

	public void setGroupId(long groupId) {
		_mbThread.setGroupId(groupId);
	}

	public long getCategoryId() {
		return _mbThread.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_mbThread.setCategoryId(categoryId);
	}

	public long getRootMessageId() {
		return _mbThread.getRootMessageId();
	}

	public void setRootMessageId(long rootMessageId) {
		_mbThread.setRootMessageId(rootMessageId);
	}

	public int getMessageCount() {
		return _mbThread.getMessageCount();
	}

	public void setMessageCount(int messageCount) {
		_mbThread.setMessageCount(messageCount);
	}

	public int getViewCount() {
		return _mbThread.getViewCount();
	}

	public void setViewCount(int viewCount) {
		_mbThread.setViewCount(viewCount);
	}

	public long getLastPostByUserId() {
		return _mbThread.getLastPostByUserId();
	}

	public void setLastPostByUserId(long lastPostByUserId) {
		_mbThread.setLastPostByUserId(lastPostByUserId);
	}

	public java.lang.String getLastPostByUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbThread.getLastPostByUserUuid();
	}

	public void setLastPostByUserUuid(java.lang.String lastPostByUserUuid) {
		_mbThread.setLastPostByUserUuid(lastPostByUserUuid);
	}

	public java.util.Date getLastPostDate() {
		return _mbThread.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_mbThread.setLastPostDate(lastPostDate);
	}

	public double getPriority() {
		return _mbThread.getPriority();
	}

	public void setPriority(double priority) {
		_mbThread.setPriority(priority);
	}

	public int getStatus() {
		return _mbThread.getStatus();
	}

	public void setStatus(int status) {
		_mbThread.setStatus(status);
	}

	public long getStatusByUserId() {
		return _mbThread.getStatusByUserId();
	}

	public void setStatusByUserId(long statusByUserId) {
		_mbThread.setStatusByUserId(statusByUserId);
	}

	public java.lang.String getStatusByUserUuid()
		throws com.liferay.portal.SystemException {
		return _mbThread.getStatusByUserUuid();
	}

	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_mbThread.setStatusByUserUuid(statusByUserUuid);
	}

	public java.lang.String getStatusByUserName() {
		return _mbThread.getStatusByUserName();
	}

	public void setStatusByUserName(java.lang.String statusByUserName) {
		_mbThread.setStatusByUserName(statusByUserName);
	}

	public java.util.Date getStatusDate() {
		return _mbThread.getStatusDate();
	}

	public void setStatusDate(java.util.Date statusDate) {
		_mbThread.setStatusDate(statusDate);
	}

	public com.liferay.portlet.messageboards.model.MBThread toEscapedModel() {
		return _mbThread.toEscapedModel();
	}

	public boolean isNew() {
		return _mbThread.isNew();
	}

	public boolean setNew(boolean n) {
		return _mbThread.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbThread.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbThread.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbThread.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbThread.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbThread.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbThread.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbThread.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbThread.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBThread mbThread) {
		return _mbThread.compareTo(mbThread);
	}

	public int hashCode() {
		return _mbThread.hashCode();
	}

	public java.lang.String toString() {
		return _mbThread.toString();
	}

	public java.lang.String toXmlString() {
		return _mbThread.toXmlString();
	}

	public java.lang.String getAttachmentsDir() {
		return _mbThread.getAttachmentsDir();
	}

	public com.liferay.portal.model.Lock getLock() {
		return _mbThread.getLock();
	}

	public boolean hasLock(long userId) {
		return _mbThread.hasLock(userId);
	}

	public boolean isLocked() {
		return _mbThread.isLocked();
	}

	public MBThread getWrappedMBThread() {
		return _mbThread;
	}

	private MBThread _mbThread;
}