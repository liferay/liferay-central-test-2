/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.model;

/**
 * <p>
 * This class is a wrapper for {@link MBDiscussion}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBDiscussion
 * @generated
 */
public class MBDiscussionWrapper implements MBDiscussion {
	public MBDiscussionWrapper(MBDiscussion mbDiscussion) {
		_mbDiscussion = mbDiscussion;
	}

	public long getPrimaryKey() {
		return _mbDiscussion.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_mbDiscussion.setPrimaryKey(pk);
	}

	public long getDiscussionId() {
		return _mbDiscussion.getDiscussionId();
	}

	public void setDiscussionId(long discussionId) {
		_mbDiscussion.setDiscussionId(discussionId);
	}

	public java.lang.String getClassName() {
		return _mbDiscussion.getClassName();
	}

	public long getClassNameId() {
		return _mbDiscussion.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_mbDiscussion.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _mbDiscussion.getClassPK();
	}

	public void setClassPK(long classPK) {
		_mbDiscussion.setClassPK(classPK);
	}

	public long getThreadId() {
		return _mbDiscussion.getThreadId();
	}

	public void setThreadId(long threadId) {
		_mbDiscussion.setThreadId(threadId);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion toEscapedModel() {
		return _mbDiscussion.toEscapedModel();
	}

	public boolean isNew() {
		return _mbDiscussion.isNew();
	}

	public void setNew(boolean n) {
		_mbDiscussion.setNew(n);
	}

	public boolean isCachedModel() {
		return _mbDiscussion.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_mbDiscussion.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _mbDiscussion.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_mbDiscussion.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _mbDiscussion.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _mbDiscussion.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_mbDiscussion.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _mbDiscussion.clone();
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion) {
		return _mbDiscussion.compareTo(mbDiscussion);
	}

	public int hashCode() {
		return _mbDiscussion.hashCode();
	}

	public java.lang.String toString() {
		return _mbDiscussion.toString();
	}

	public java.lang.String toXmlString() {
		return _mbDiscussion.toXmlString();
	}

	public MBDiscussion getWrappedMBDiscussion() {
		return _mbDiscussion;
	}

	private MBDiscussion _mbDiscussion;
}