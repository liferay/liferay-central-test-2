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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Subscription}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Subscription
 * @generated
 */
public class SubscriptionWrapper implements Subscription {
	public SubscriptionWrapper(Subscription subscription) {
		_subscription = subscription;
	}

	public long getPrimaryKey() {
		return _subscription.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_subscription.setPrimaryKey(pk);
	}

	public long getSubscriptionId() {
		return _subscription.getSubscriptionId();
	}

	public void setSubscriptionId(long subscriptionId) {
		_subscription.setSubscriptionId(subscriptionId);
	}

	public long getCompanyId() {
		return _subscription.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_subscription.setCompanyId(companyId);
	}

	public long getUserId() {
		return _subscription.getUserId();
	}

	public void setUserId(long userId) {
		_subscription.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _subscription.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_subscription.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _subscription.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_subscription.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _subscription.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_subscription.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _subscription.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_subscription.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _subscription.getClassName();
	}

	public long getClassNameId() {
		return _subscription.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_subscription.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _subscription.getClassPK();
	}

	public void setClassPK(long classPK) {
		_subscription.setClassPK(classPK);
	}

	public java.lang.String getFrequency() {
		return _subscription.getFrequency();
	}

	public void setFrequency(java.lang.String frequency) {
		_subscription.setFrequency(frequency);
	}

	public com.liferay.portal.model.Subscription toEscapedModel() {
		return _subscription.toEscapedModel();
	}

	public boolean isNew() {
		return _subscription.isNew();
	}

	public void setNew(boolean n) {
		_subscription.setNew(n);
	}

	public boolean isCachedModel() {
		return _subscription.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_subscription.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _subscription.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_subscription.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _subscription.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _subscription.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_subscription.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _subscription.clone();
	}

	public int compareTo(com.liferay.portal.model.Subscription subscription) {
		return _subscription.compareTo(subscription);
	}

	public int hashCode() {
		return _subscription.hashCode();
	}

	public java.lang.String toString() {
		return _subscription.toString();
	}

	public java.lang.String toXmlString() {
		return _subscription.toXmlString();
	}

	public Subscription getWrappedSubscription() {
		return _subscription;
	}

	private Subscription _subscription;
}