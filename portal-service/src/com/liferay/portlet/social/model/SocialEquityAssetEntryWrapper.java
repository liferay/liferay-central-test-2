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
 * <a href="SocialEquityAssetEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquityAssetEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntry
 * @generated
 */
public class SocialEquityAssetEntryWrapper implements SocialEquityAssetEntry {
	public SocialEquityAssetEntryWrapper(
		SocialEquityAssetEntry socialEquityAssetEntry) {
		_socialEquityAssetEntry = socialEquityAssetEntry;
	}

	public long getPrimaryKey() {
		return _socialEquityAssetEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_socialEquityAssetEntry.setPrimaryKey(pk);
	}

	public long getEquityAssetEntryId() {
		return _socialEquityAssetEntry.getEquityAssetEntryId();
	}

	public void setEquityAssetEntryId(long equityAssetEntryId) {
		_socialEquityAssetEntry.setEquityAssetEntryId(equityAssetEntryId);
	}

	public long getCompanyId() {
		return _socialEquityAssetEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_socialEquityAssetEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _socialEquityAssetEntry.getUserId();
	}

	public void setUserId(long userId) {
		_socialEquityAssetEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityAssetEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_socialEquityAssetEntry.setUserUuid(userUuid);
	}

	public long getAssetEntryId() {
		return _socialEquityAssetEntry.getAssetEntryId();
	}

	public void setAssetEntryId(long assetEntryId) {
		_socialEquityAssetEntry.setAssetEntryId(assetEntryId);
	}

	public double getInformationK() {
		return _socialEquityAssetEntry.getInformationK();
	}

	public void setInformationK(double informationK) {
		_socialEquityAssetEntry.setInformationK(informationK);
	}

	public double getInformationB() {
		return _socialEquityAssetEntry.getInformationB();
	}

	public void setInformationB(double informationB) {
		_socialEquityAssetEntry.setInformationB(informationB);
	}

	public double getInformationEquity() {
		return _socialEquityAssetEntry.getInformationEquity();
	}

	public void setInformationEquity(double informationEquity) {
		_socialEquityAssetEntry.setInformationEquity(informationEquity);
	}

	public com.liferay.portlet.social.model.SocialEquityAssetEntry toEscapedModel() {
		return _socialEquityAssetEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _socialEquityAssetEntry.isNew();
	}

	public boolean setNew(boolean n) {
		return _socialEquityAssetEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _socialEquityAssetEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_socialEquityAssetEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _socialEquityAssetEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_socialEquityAssetEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _socialEquityAssetEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _socialEquityAssetEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_socialEquityAssetEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _socialEquityAssetEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry) {
		return _socialEquityAssetEntry.compareTo(socialEquityAssetEntry);
	}

	public int hashCode() {
		return _socialEquityAssetEntry.hashCode();
	}

	public java.lang.String toString() {
		return _socialEquityAssetEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _socialEquityAssetEntry.toXmlString();
	}

	public SocialEquityAssetEntry getWrappedSocialEquityAssetEntry() {
		return _socialEquityAssetEntry;
	}

	private SocialEquityAssetEntry _socialEquityAssetEntry;
}