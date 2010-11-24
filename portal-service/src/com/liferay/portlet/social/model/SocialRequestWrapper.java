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

	/**
	* Gets the primary key of this social request.
	*
	* @return the primary key of this social request
	*/
	public long getPrimaryKey() {
		return _socialRequest.getPrimaryKey();
	}

	/**
	* Sets the primary key of this social request
	*
	* @param pk the primary key of this social request
	*/
	public void setPrimaryKey(long pk) {
		_socialRequest.setPrimaryKey(pk);
	}

	/**
	* Gets the uuid of this social request.
	*
	* @return the uuid of this social request
	*/
	public java.lang.String getUuid() {
		return _socialRequest.getUuid();
	}

	/**
	* Sets the uuid of this social request.
	*
	* @param uuid the uuid of this social request
	*/
	public void setUuid(java.lang.String uuid) {
		_socialRequest.setUuid(uuid);
	}

	/**
	* Gets the request id of this social request.
	*
	* @return the request id of this social request
	*/
	public long getRequestId() {
		return _socialRequest.getRequestId();
	}

	/**
	* Sets the request id of this social request.
	*
	* @param requestId the request id of this social request
	*/
	public void setRequestId(long requestId) {
		_socialRequest.setRequestId(requestId);
	}

	/**
	* Gets the group id of this social request.
	*
	* @return the group id of this social request
	*/
	public long getGroupId() {
		return _socialRequest.getGroupId();
	}

	/**
	* Sets the group id of this social request.
	*
	* @param groupId the group id of this social request
	*/
	public void setGroupId(long groupId) {
		_socialRequest.setGroupId(groupId);
	}

	/**
	* Gets the company id of this social request.
	*
	* @return the company id of this social request
	*/
	public long getCompanyId() {
		return _socialRequest.getCompanyId();
	}

	/**
	* Sets the company id of this social request.
	*
	* @param companyId the company id of this social request
	*/
	public void setCompanyId(long companyId) {
		_socialRequest.setCompanyId(companyId);
	}

	/**
	* Gets the user id of this social request.
	*
	* @return the user id of this social request
	*/
	public long getUserId() {
		return _socialRequest.getUserId();
	}

	/**
	* Sets the user id of this social request.
	*
	* @param userId the user id of this social request
	*/
	public void setUserId(long userId) {
		_socialRequest.setUserId(userId);
	}

	/**
	* Gets the user uuid of this social request.
	*
	* @return the user uuid of this social request
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequest.getUserUuid();
	}

	/**
	* Sets the user uuid of this social request.
	*
	* @param userUuid the user uuid of this social request
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_socialRequest.setUserUuid(userUuid);
	}

	/**
	* Gets the create date of this social request.
	*
	* @return the create date of this social request
	*/
	public long getCreateDate() {
		return _socialRequest.getCreateDate();
	}

	/**
	* Sets the create date of this social request.
	*
	* @param createDate the create date of this social request
	*/
	public void setCreateDate(long createDate) {
		_socialRequest.setCreateDate(createDate);
	}

	/**
	* Gets the modified date of this social request.
	*
	* @return the modified date of this social request
	*/
	public long getModifiedDate() {
		return _socialRequest.getModifiedDate();
	}

	/**
	* Sets the modified date of this social request.
	*
	* @param modifiedDate the modified date of this social request
	*/
	public void setModifiedDate(long modifiedDate) {
		_socialRequest.setModifiedDate(modifiedDate);
	}

	/**
	* Gets the class name of the model instance this social request is polymorphically associated with.
	*
	* @return the class name of the model instance this social request is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _socialRequest.getClassName();
	}

	/**
	* Gets the class name id of this social request.
	*
	* @return the class name id of this social request
	*/
	public long getClassNameId() {
		return _socialRequest.getClassNameId();
	}

	/**
	* Sets the class name id of this social request.
	*
	* @param classNameId the class name id of this social request
	*/
	public void setClassNameId(long classNameId) {
		_socialRequest.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this social request.
	*
	* @return the class p k of this social request
	*/
	public long getClassPK() {
		return _socialRequest.getClassPK();
	}

	/**
	* Sets the class p k of this social request.
	*
	* @param classPK the class p k of this social request
	*/
	public void setClassPK(long classPK) {
		_socialRequest.setClassPK(classPK);
	}

	/**
	* Gets the type of this social request.
	*
	* @return the type of this social request
	*/
	public int getType() {
		return _socialRequest.getType();
	}

	/**
	* Sets the type of this social request.
	*
	* @param type the type of this social request
	*/
	public void setType(int type) {
		_socialRequest.setType(type);
	}

	/**
	* Gets the extra data of this social request.
	*
	* @return the extra data of this social request
	*/
	public java.lang.String getExtraData() {
		return _socialRequest.getExtraData();
	}

	/**
	* Sets the extra data of this social request.
	*
	* @param extraData the extra data of this social request
	*/
	public void setExtraData(java.lang.String extraData) {
		_socialRequest.setExtraData(extraData);
	}

	/**
	* Gets the receiver user id of this social request.
	*
	* @return the receiver user id of this social request
	*/
	public long getReceiverUserId() {
		return _socialRequest.getReceiverUserId();
	}

	/**
	* Sets the receiver user id of this social request.
	*
	* @param receiverUserId the receiver user id of this social request
	*/
	public void setReceiverUserId(long receiverUserId) {
		_socialRequest.setReceiverUserId(receiverUserId);
	}

	/**
	* Gets the receiver user uuid of this social request.
	*
	* @return the receiver user uuid of this social request
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getReceiverUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequest.getReceiverUserUuid();
	}

	/**
	* Sets the receiver user uuid of this social request.
	*
	* @param receiverUserUuid the receiver user uuid of this social request
	*/
	public void setReceiverUserUuid(java.lang.String receiverUserUuid) {
		_socialRequest.setReceiverUserUuid(receiverUserUuid);
	}

	/**
	* Gets the status of this social request.
	*
	* @return the status of this social request
	*/
	public int getStatus() {
		return _socialRequest.getStatus();
	}

	/**
	* Sets the status of this social request.
	*
	* @param status the status of this social request
	*/
	public void setStatus(int status) {
		_socialRequest.setStatus(status);
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
		return new SocialRequestWrapper((SocialRequest)_socialRequest.clone());
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialRequest socialRequest) {
		return _socialRequest.compareTo(socialRequest);
	}

	public int hashCode() {
		return _socialRequest.hashCode();
	}

	public com.liferay.portlet.social.model.SocialRequest toEscapedModel() {
		return new SocialRequestWrapper(_socialRequest.toEscapedModel());
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