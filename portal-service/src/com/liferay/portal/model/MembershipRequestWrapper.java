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

package com.liferay.portal.model;


/**
 * <a href="MembershipRequestSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MembershipRequest}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequest
 * @generated
 */
public class MembershipRequestWrapper implements MembershipRequest {
	public MembershipRequestWrapper(MembershipRequest membershipRequest) {
		_membershipRequest = membershipRequest;
	}

	public long getPrimaryKey() {
		return _membershipRequest.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_membershipRequest.setPrimaryKey(pk);
	}

	public long getMembershipRequestId() {
		return _membershipRequest.getMembershipRequestId();
	}

	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequest.setMembershipRequestId(membershipRequestId);
	}

	public long getCompanyId() {
		return _membershipRequest.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_membershipRequest.setCompanyId(companyId);
	}

	public long getUserId() {
		return _membershipRequest.getUserId();
	}

	public void setUserId(long userId) {
		_membershipRequest.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_membershipRequest.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _membershipRequest.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_membershipRequest.setCreateDate(createDate);
	}

	public long getGroupId() {
		return _membershipRequest.getGroupId();
	}

	public void setGroupId(long groupId) {
		_membershipRequest.setGroupId(groupId);
	}

	public java.lang.String getComments() {
		return _membershipRequest.getComments();
	}

	public void setComments(java.lang.String comments) {
		_membershipRequest.setComments(comments);
	}

	public java.lang.String getReplyComments() {
		return _membershipRequest.getReplyComments();
	}

	public void setReplyComments(java.lang.String replyComments) {
		_membershipRequest.setReplyComments(replyComments);
	}

	public java.util.Date getReplyDate() {
		return _membershipRequest.getReplyDate();
	}

	public void setReplyDate(java.util.Date replyDate) {
		_membershipRequest.setReplyDate(replyDate);
	}

	public long getReplierUserId() {
		return _membershipRequest.getReplierUserId();
	}

	public void setReplierUserId(long replierUserId) {
		_membershipRequest.setReplierUserId(replierUserId);
	}

	public java.lang.String getReplierUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequest.getReplierUserUuid();
	}

	public void setReplierUserUuid(java.lang.String replierUserUuid) {
		_membershipRequest.setReplierUserUuid(replierUserUuid);
	}

	public int getStatusId() {
		return _membershipRequest.getStatusId();
	}

	public void setStatusId(int statusId) {
		_membershipRequest.setStatusId(statusId);
	}

	public com.liferay.portal.model.MembershipRequest toEscapedModel() {
		return _membershipRequest.toEscapedModel();
	}

	public boolean isNew() {
		return _membershipRequest.isNew();
	}

	public boolean setNew(boolean n) {
		return _membershipRequest.setNew(n);
	}

	public boolean isCachedModel() {
		return _membershipRequest.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_membershipRequest.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _membershipRequest.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_membershipRequest.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _membershipRequest.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _membershipRequest.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_membershipRequest.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _membershipRequest.clone();
	}

	public int compareTo(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		return _membershipRequest.compareTo(membershipRequest);
	}

	public int hashCode() {
		return _membershipRequest.hashCode();
	}

	public java.lang.String toString() {
		return _membershipRequest.toString();
	}

	public java.lang.String toXmlString() {
		return _membershipRequest.toXmlString();
	}

	public MembershipRequest getWrappedMembershipRequest() {
		return _membershipRequest;
	}

	private MembershipRequest _membershipRequest;
}