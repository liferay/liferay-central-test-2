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

	/**
	* Gets the primary key of this message boards discussion.
	*
	* @return the primary key of this message boards discussion
	*/
	public long getPrimaryKey() {
		return _mbDiscussion.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message boards discussion
	*
	* @param pk the primary key of this message boards discussion
	*/
	public void setPrimaryKey(long pk) {
		_mbDiscussion.setPrimaryKey(pk);
	}

	/**
	* Gets the discussion id of this message boards discussion.
	*
	* @return the discussion id of this message boards discussion
	*/
	public long getDiscussionId() {
		return _mbDiscussion.getDiscussionId();
	}

	/**
	* Sets the discussion id of this message boards discussion.
	*
	* @param discussionId the discussion id of this message boards discussion
	*/
	public void setDiscussionId(long discussionId) {
		_mbDiscussion.setDiscussionId(discussionId);
	}

	/**
	* Gets the class name of the model instance this message boards discussion is polymorphically associated with.
	*
	* @return the class name of the model instance this message boards discussion is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _mbDiscussion.getClassName();
	}

	/**
	* Gets the class name id of this message boards discussion.
	*
	* @return the class name id of this message boards discussion
	*/
	public long getClassNameId() {
		return _mbDiscussion.getClassNameId();
	}

	/**
	* Sets the class name id of this message boards discussion.
	*
	* @param classNameId the class name id of this message boards discussion
	*/
	public void setClassNameId(long classNameId) {
		_mbDiscussion.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this message boards discussion.
	*
	* @return the class p k of this message boards discussion
	*/
	public long getClassPK() {
		return _mbDiscussion.getClassPK();
	}

	/**
	* Sets the class p k of this message boards discussion.
	*
	* @param classPK the class p k of this message boards discussion
	*/
	public void setClassPK(long classPK) {
		_mbDiscussion.setClassPK(classPK);
	}

	/**
	* Gets the thread id of this message boards discussion.
	*
	* @return the thread id of this message boards discussion
	*/
	public long getThreadId() {
		return _mbDiscussion.getThreadId();
	}

	/**
	* Sets the thread id of this message boards discussion.
	*
	* @param threadId the thread id of this message boards discussion
	*/
	public void setThreadId(long threadId) {
		_mbDiscussion.setThreadId(threadId);
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
		return new MBDiscussionWrapper((MBDiscussion)_mbDiscussion.clone());
	}

	public int compareTo(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion) {
		return _mbDiscussion.compareTo(mbDiscussion);
	}

	public int hashCode() {
		return _mbDiscussion.hashCode();
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion toEscapedModel() {
		return new MBDiscussionWrapper(_mbDiscussion.toEscapedModel());
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