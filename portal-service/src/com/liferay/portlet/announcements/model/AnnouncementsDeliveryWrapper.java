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

package com.liferay.portlet.announcements.model;

/**
 * <p>
 * This class is a wrapper for {@link AnnouncementsDelivery}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDelivery
 * @generated
 */
public class AnnouncementsDeliveryWrapper implements AnnouncementsDelivery {
	public AnnouncementsDeliveryWrapper(
		AnnouncementsDelivery announcementsDelivery) {
		_announcementsDelivery = announcementsDelivery;
	}

	public long getPrimaryKey() {
		return _announcementsDelivery.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_announcementsDelivery.setPrimaryKey(pk);
	}

	public long getDeliveryId() {
		return _announcementsDelivery.getDeliveryId();
	}

	public void setDeliveryId(long deliveryId) {
		_announcementsDelivery.setDeliveryId(deliveryId);
	}

	public long getCompanyId() {
		return _announcementsDelivery.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_announcementsDelivery.setCompanyId(companyId);
	}

	public long getUserId() {
		return _announcementsDelivery.getUserId();
	}

	public void setUserId(long userId) {
		_announcementsDelivery.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _announcementsDelivery.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_announcementsDelivery.setUserUuid(userUuid);
	}

	public java.lang.String getType() {
		return _announcementsDelivery.getType();
	}

	public void setType(java.lang.String type) {
		_announcementsDelivery.setType(type);
	}

	public boolean getEmail() {
		return _announcementsDelivery.getEmail();
	}

	public boolean isEmail() {
		return _announcementsDelivery.isEmail();
	}

	public void setEmail(boolean email) {
		_announcementsDelivery.setEmail(email);
	}

	public boolean getSms() {
		return _announcementsDelivery.getSms();
	}

	public boolean isSms() {
		return _announcementsDelivery.isSms();
	}

	public void setSms(boolean sms) {
		_announcementsDelivery.setSms(sms);
	}

	public boolean getWebsite() {
		return _announcementsDelivery.getWebsite();
	}

	public boolean isWebsite() {
		return _announcementsDelivery.isWebsite();
	}

	public void setWebsite(boolean website) {
		_announcementsDelivery.setWebsite(website);
	}

	public com.liferay.portlet.announcements.model.AnnouncementsDelivery toEscapedModel() {
		return _announcementsDelivery.toEscapedModel();
	}

	public boolean isNew() {
		return _announcementsDelivery.isNew();
	}

	public void setNew(boolean n) {
		_announcementsDelivery.setNew(n);
	}

	public boolean isCachedModel() {
		return _announcementsDelivery.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_announcementsDelivery.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _announcementsDelivery.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_announcementsDelivery.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _announcementsDelivery.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _announcementsDelivery.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_announcementsDelivery.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _announcementsDelivery.clone();
	}

	public int compareTo(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery) {
		return _announcementsDelivery.compareTo(announcementsDelivery);
	}

	public int hashCode() {
		return _announcementsDelivery.hashCode();
	}

	public java.lang.String toString() {
		return _announcementsDelivery.toString();
	}

	public java.lang.String toXmlString() {
		return _announcementsDelivery.toXmlString();
	}

	public AnnouncementsDelivery getWrappedAnnouncementsDelivery() {
		return _announcementsDelivery;
	}

	private AnnouncementsDelivery _announcementsDelivery;
}