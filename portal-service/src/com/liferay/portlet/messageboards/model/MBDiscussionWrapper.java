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
 * <a href="MBDiscussionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public boolean setNew(boolean n) {
		return _mbDiscussion.setNew(n);
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