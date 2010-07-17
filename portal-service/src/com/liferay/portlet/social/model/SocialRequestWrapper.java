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

package com.liferay.portlet.social.model;

/**
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
		throws com.liferay.portal.kernel.exception.SystemException {
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
		throws com.liferay.portal.kernel.exception.SystemException {
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

	public void setNew(boolean n) {
		_socialRequest.setNew(n);
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