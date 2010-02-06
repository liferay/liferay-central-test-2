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

package com.liferay.portlet.social.model;


/**
 * <a href="SocialRequestSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialRequest}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequest
 * @generated
 */
public class SocialRequestWrapper implements SocialRequest {
	public SocialRequestWrapper(SocialRequest socialRequest) {
		_socialRequest = socialRequest;
	}

	public long getPrimaryKey() {
		return _socialRequest.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialRequest.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _socialRequest.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_socialRequest.setUuid(uuid);
	}

	public long getRequestId() {
		return _socialRequest.getRequestId();
	}

	public void setRequestId(long requestId) {
		_socialRequest.setRequestId(requestId);
	}

	public long getGroupId() {
		return _socialRequest.getGroupId();
	}

	public void setGroupId(long groupId) {
		_socialRequest.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _socialRequest.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialRequest.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialRequest.getUserId();
	}

	public void setUserId(long userId) {
		_socialRequest.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _socialRequest.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialRequest.setUserUuid(userUuid);
	}

	public long getCreateDate() {
		return _socialRequest.getCreateDate();
	}

	public void setCreateDate(long createDate) {
		_socialRequest.setCreateDate(createDate);
	}

	public long getModifiedDate() {
		return _socialRequest.getModifiedDate();
	}

	public void setModifiedDate(long modifiedDate) {
		_socialRequest.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _socialRequest.getClassName();
	}

	public long getClassNameId() {
		return _socialRequest.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_socialRequest.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _socialRequest.getClassPK();
	}

	public void setClassPK(long classPK) {
		_socialRequest.setClassPK(classPK);
	}

	public int getType() {
		return _socialRequest.getType();
	}

	public void setType(int type) {
		_socialRequest.setType(type);
	}

	public java.lang.String getExtraData() {
		return _socialRequest.getExtraData();
	}

	public void setExtraData(java.lang.String extraData) {
		_socialRequest.setExtraData(extraData);
	}

	public long getReceiverUserId() {
		return _socialRequest.getReceiverUserId();
	}

	public void setReceiverUserId(long receiverUserId) {
		_socialRequest.setReceiverUserId(receiverUserId);
	}

	public java.lang.String getReceiverUserUuid()
		throws com.liferay.portal.SystemException {
		return _socialRequest.getReceiverUserUuid();
	}

	public void setReceiverUserUuid(java.lang.String receiverUserUuid) {
		_socialRequest.setReceiverUserUuid(receiverUserUuid);
	}

	public int getStatus() {
		return _socialRequest.getStatus();
	}

	public void setStatus(int status) {
		_socialRequest.setStatus(status);
	}

	public com.liferay.portlet.social.model.SocialRequest toEscapedModel() {
		return _socialRequest.toEscapedModel();
	}

	public boolean isNew() {
		return _socialRequest.isNew();
	}

	public boolean setNew(boolean n) {
		return _socialRequest.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialRequest.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialRequest.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialRequest.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialRequest.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialRequest.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialRequest.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialRequest.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialRequest.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialRequest socialRequest) {
		return _socialRequest.compareTo(socialRequest);
	}

	public int hashCode() {
		return _socialRequest.hashCode();
	}

	public java.lang.String toString() {
		return _socialRequest.toString();
	}

	public java.lang.String toXmlString() {
		return _socialRequest.toXmlString();
	}

	public SocialRequest getWrappedSocialRequest() {
		return _socialRequest;
	}

	private SocialRequest _socialRequest;
}