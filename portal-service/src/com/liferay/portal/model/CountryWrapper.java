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
 * This class is a wrapper for {@link Country}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Country
 * @generated
 */
public class CountryWrapper implements Country {
	public CountryWrapper(Country country) {
		_country = country;
	}

	public long getPrimaryKey() {
		return _country.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_country.setPrimaryKey(pk);
	}

	public long getCountryId() {
		return _country.getCountryId();
	}

	public void setCountryId(long countryId) {
		_country.setCountryId(countryId);
	}

	public java.lang.String getName() {
		return _country.getName();
	}

	public void setName(java.lang.String name) {
		_country.setName(name);
	}

	public java.lang.String getA2() {
		return _country.getA2();
	}

	public void setA2(java.lang.String a2) {
		_country.setA2(a2);
	}

	public java.lang.String getA3() {
		return _country.getA3();
	}

	public void setA3(java.lang.String a3) {
		_country.setA3(a3);
	}

	public java.lang.String getNumber() {
		return _country.getNumber();
	}

	public void setNumber(java.lang.String number) {
		_country.setNumber(number);
	}

	public java.lang.String getIdd() {
		return _country.getIdd();
	}

	public void setIdd(java.lang.String idd) {
		_country.setIdd(idd);
	}

	public boolean getActive() {
		return _country.getActive();
	}

	public boolean isActive() {
		return _country.isActive();
	}

	public void setActive(boolean active) {
		_country.setActive(active);
	}

	public com.liferay.portal.model.Country toEscapedModel() {
		return _country.toEscapedModel();
	}

	public boolean isNew() {
		return _country.isNew();
	}

	public void setNew(boolean n) {
		_country.setNew(n);
	}

	public boolean isCachedModel() {
		return _country.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_country.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _country.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_country.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _country.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _country.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_country.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _country.clone();
	}

	public int compareTo(com.liferay.portal.model.Country country) {
		return _country.compareTo(country);
	}

	public int hashCode() {
		return _country.hashCode();
	}

	public java.lang.String toString() {
		return _country.toString();
	}

	public java.lang.String toXmlString() {
		return _country.toXmlString();
	}

	public Country getWrappedCountry() {
		return _country;
	}

	private Country _country;
}