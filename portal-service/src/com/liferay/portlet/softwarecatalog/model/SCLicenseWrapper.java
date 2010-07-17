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

package com.liferay.portlet.softwarecatalog.model;

/**
 * <p>
 * This class is a wrapper for {@link SCLicense}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicense
 * @generated
 */
public class SCLicenseWrapper implements SCLicense {
	public SCLicenseWrapper(SCLicense scLicense) {
		_scLicense = scLicense;
	}

	public long getPrimaryKey() {
		return _scLicense.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scLicense.setPrimaryKey(pk);
	}

	public long getLicenseId() {
		return _scLicense.getLicenseId();
	}

	public void setLicenseId(long licenseId) {
		_scLicense.setLicenseId(licenseId);
	}

	public java.lang.String getName() {
		return _scLicense.getName();
	}

	public void setName(java.lang.String name) {
		_scLicense.setName(name);
	}

	public java.lang.String getUrl() {
		return _scLicense.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_scLicense.setUrl(url);
	}

	public boolean getOpenSource() {
		return _scLicense.getOpenSource();
	}

	public boolean isOpenSource() {
		return _scLicense.isOpenSource();
	}

	public void setOpenSource(boolean openSource) {
		_scLicense.setOpenSource(openSource);
	}

	public boolean getActive() {
		return _scLicense.getActive();
	}

	public boolean isActive() {
		return _scLicense.isActive();
	}

	public void setActive(boolean active) {
		_scLicense.setActive(active);
	}

	public boolean getRecommended() {
		return _scLicense.getRecommended();
	}

	public boolean isRecommended() {
		return _scLicense.isRecommended();
	}

	public void setRecommended(boolean recommended) {
		_scLicense.setRecommended(recommended);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense toEscapedModel() {
		return _scLicense.toEscapedModel();
	}

	public boolean isNew() {
		return _scLicense.isNew();
	}

	public void setNew(boolean n) {
		_scLicense.setNew(n);
	}

	public boolean isCachedModel() {
		return _scLicense.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scLicense.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scLicense.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scLicense.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scLicense.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scLicense.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scLicense.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scLicense.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicense.compareTo(scLicense);
	}

	public int hashCode() {
		return _scLicense.hashCode();
	}

	public java.lang.String toString() {
		return _scLicense.toString();
	}

	public java.lang.String toXmlString() {
		return _scLicense.toXmlString();
	}

	public SCLicense getWrappedSCLicense() {
		return _scLicense;
	}

	private SCLicense _scLicense;
}