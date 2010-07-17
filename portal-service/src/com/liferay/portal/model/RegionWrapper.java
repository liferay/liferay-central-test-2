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
 * This class is a wrapper for {@link Region}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Region
 * @generated
 */
public class RegionWrapper implements Region {
	public RegionWrapper(Region region) {
		_region = region;
	}

	public long getPrimaryKey() {
		return _region.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_region.setPrimaryKey(pk);
	}

	public long getRegionId() {
		return _region.getRegionId();
	}

	public void setRegionId(long regionId) {
		_region.setRegionId(regionId);
	}

	public long getCountryId() {
		return _region.getCountryId();
	}

	public void setCountryId(long countryId) {
		_region.setCountryId(countryId);
	}

	public java.lang.String getRegionCode() {
		return _region.getRegionCode();
	}

	public void setRegionCode(java.lang.String regionCode) {
		_region.setRegionCode(regionCode);
	}

	public java.lang.String getName() {
		return _region.getName();
	}

	public void setName(java.lang.String name) {
		_region.setName(name);
	}

	public boolean getActive() {
		return _region.getActive();
	}

	public boolean isActive() {
		return _region.isActive();
	}

	public void setActive(boolean active) {
		_region.setActive(active);
	}

	public com.liferay.portal.model.Region toEscapedModel() {
		return _region.toEscapedModel();
	}

	public boolean isNew() {
		return _region.isNew();
	}

	public void setNew(boolean n) {
		_region.setNew(n);
	}

	public boolean isCachedModel() {
		return _region.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_region.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _region.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_region.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _region.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _region.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_region.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _region.clone();
	}

	public int compareTo(com.liferay.portal.model.Region region) {
		return _region.compareTo(region);
	}

	public int hashCode() {
		return _region.hashCode();
	}

	public java.lang.String toString() {
		return _region.toString();
	}

	public java.lang.String toXmlString() {
		return _region.toXmlString();
	}

	public Region getWrappedRegion() {
		return _region;
	}

	private Region _region;
}