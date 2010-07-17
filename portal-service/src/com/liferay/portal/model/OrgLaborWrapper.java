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
 * This class is a wrapper for {@link OrgLabor}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLabor
 * @generated
 */
public class OrgLaborWrapper implements OrgLabor {
	public OrgLaborWrapper(OrgLabor orgLabor) {
		_orgLabor = orgLabor;
	}

	public long getPrimaryKey() {
		return _orgLabor.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_orgLabor.setPrimaryKey(pk);
	}

	public long getOrgLaborId() {
		return _orgLabor.getOrgLaborId();
	}

	public void setOrgLaborId(long orgLaborId) {
		_orgLabor.setOrgLaborId(orgLaborId);
	}

	public long getOrganizationId() {
		return _orgLabor.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		_orgLabor.setOrganizationId(organizationId);
	}

	public int getTypeId() {
		return _orgLabor.getTypeId();
	}

	public void setTypeId(int typeId) {
		_orgLabor.setTypeId(typeId);
	}

	public int getSunOpen() {
		return _orgLabor.getSunOpen();
	}

	public void setSunOpen(int sunOpen) {
		_orgLabor.setSunOpen(sunOpen);
	}

	public int getSunClose() {
		return _orgLabor.getSunClose();
	}

	public void setSunClose(int sunClose) {
		_orgLabor.setSunClose(sunClose);
	}

	public int getMonOpen() {
		return _orgLabor.getMonOpen();
	}

	public void setMonOpen(int monOpen) {
		_orgLabor.setMonOpen(monOpen);
	}

	public int getMonClose() {
		return _orgLabor.getMonClose();
	}

	public void setMonClose(int monClose) {
		_orgLabor.setMonClose(monClose);
	}

	public int getTueOpen() {
		return _orgLabor.getTueOpen();
	}

	public void setTueOpen(int tueOpen) {
		_orgLabor.setTueOpen(tueOpen);
	}

	public int getTueClose() {
		return _orgLabor.getTueClose();
	}

	public void setTueClose(int tueClose) {
		_orgLabor.setTueClose(tueClose);
	}

	public int getWedOpen() {
		return _orgLabor.getWedOpen();
	}

	public void setWedOpen(int wedOpen) {
		_orgLabor.setWedOpen(wedOpen);
	}

	public int getWedClose() {
		return _orgLabor.getWedClose();
	}

	public void setWedClose(int wedClose) {
		_orgLabor.setWedClose(wedClose);
	}

	public int getThuOpen() {
		return _orgLabor.getThuOpen();
	}

	public void setThuOpen(int thuOpen) {
		_orgLabor.setThuOpen(thuOpen);
	}

	public int getThuClose() {
		return _orgLabor.getThuClose();
	}

	public void setThuClose(int thuClose) {
		_orgLabor.setThuClose(thuClose);
	}

	public int getFriOpen() {
		return _orgLabor.getFriOpen();
	}

	public void setFriOpen(int friOpen) {
		_orgLabor.setFriOpen(friOpen);
	}

	public int getFriClose() {
		return _orgLabor.getFriClose();
	}

	public void setFriClose(int friClose) {
		_orgLabor.setFriClose(friClose);
	}

	public int getSatOpen() {
		return _orgLabor.getSatOpen();
	}

	public void setSatOpen(int satOpen) {
		_orgLabor.setSatOpen(satOpen);
	}

	public int getSatClose() {
		return _orgLabor.getSatClose();
	}

	public void setSatClose(int satClose) {
		_orgLabor.setSatClose(satClose);
	}

	public com.liferay.portal.model.OrgLabor toEscapedModel() {
		return _orgLabor.toEscapedModel();
	}

	public boolean isNew() {
		return _orgLabor.isNew();
	}

	public void setNew(boolean n) {
		_orgLabor.setNew(n);
	}

	public boolean isCachedModel() {
		return _orgLabor.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_orgLabor.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _orgLabor.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_orgLabor.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _orgLabor.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _orgLabor.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_orgLabor.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _orgLabor.clone();
	}

	public int compareTo(com.liferay.portal.model.OrgLabor orgLabor) {
		return _orgLabor.compareTo(orgLabor);
	}

	public int hashCode() {
		return _orgLabor.hashCode();
	}

	public java.lang.String toString() {
		return _orgLabor.toString();
	}

	public java.lang.String toXmlString() {
		return _orgLabor.toXmlString();
	}

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _orgLabor.getType();
	}

	public OrgLabor getWrappedOrgLabor() {
		return _orgLabor;
	}

	private OrgLabor _orgLabor;
}