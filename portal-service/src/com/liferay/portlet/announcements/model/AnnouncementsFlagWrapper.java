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

package com.liferay.portlet.announcements.model;


/**
 * <a href="AnnouncementsFlagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AnnouncementsFlag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlag
 * @generated
 */
public class AnnouncementsFlagWrapper implements AnnouncementsFlag {
	public AnnouncementsFlagWrapper(AnnouncementsFlag announcementsFlag) {
		_announcementsFlag = announcementsFlag;
	}

	public long getPrimaryKey() {
		return _announcementsFlag.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_announcementsFlag.setPrimaryKey(pk);
	}

	public long getFlagId() {
		return _announcementsFlag.getFlagId();
	}

	public void setFlagId(long flagId) {
		_announcementsFlag.setFlagId(flagId);
	}

	public long getUserId() {
		return _announcementsFlag.getUserId();
	}

	public void setUserId(long userId) {
		_announcementsFlag.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _announcementsFlag.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_announcementsFlag.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _announcementsFlag.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_announcementsFlag.setCreateDate(createDate);
	}

	public long getEntryId() {
		return _announcementsFlag.getEntryId();
	}

	public void setEntryId(long entryId) {
		_announcementsFlag.setEntryId(entryId);
	}

	public int getValue() {
		return _announcementsFlag.getValue();
	}

	public void setValue(int value) {
		_announcementsFlag.setValue(value);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsFlag toEscapedModel() {
		return _announcementsFlag.toEscapedModel();
	}

	public boolean isNew() {
		return _announcementsFlag.isNew();
	}

	public boolean setNew(boolean n) {
		return _announcementsFlag.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsFlag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsFlag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsFlag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_announcementsFlag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsFlag.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsFlag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsFlag.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _announcementsFlag.clone();
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		return _announcementsFlag.compareTo(announcementsFlag);
	}

	public int hashCode() {
		return _announcementsFlag.hashCode();
	}

	public java.lang.String toString() {
		return _announcementsFlag.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsFlag.toXmlString();
	}

	public AnnouncementsFlag getWrappedAnnouncementsFlag() {
		return _announcementsFlag;
	}

	private AnnouncementsFlag _announcementsFlag;
}