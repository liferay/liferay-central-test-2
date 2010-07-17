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
 * This class is a wrapper for {@link Release}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Release
 * @generated
 */
public class ReleaseWrapper implements Release {
	public ReleaseWrapper(Release release) {
		_release = release;
	}

	public long getPrimaryKey() {
		return _release.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_release.setPrimaryKey(pk);
	}

	public long getReleaseId() {
		return _release.getReleaseId();
	}

	public void setReleaseId(long releaseId) {
		_release.setReleaseId(releaseId);
	}

	public java.util.Date getCreateDate() {
		return _release.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_release.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _release.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_release.setModifiedDate(modifiedDate);
	}

	public java.lang.String getServletContextName() {
		return _release.getServletContextName();
	}

	public void setServletContextName(java.lang.String servletContextName) {
		_release.setServletContextName(servletContextName);
	}

	public int getBuildNumber() {
		return _release.getBuildNumber();
	}

	public void setBuildNumber(int buildNumber) {
		_release.setBuildNumber(buildNumber);
	}

	public java.util.Date getBuildDate() {
		return _release.getBuildDate();
	}

	public void setBuildDate(java.util.Date buildDate) {
		_release.setBuildDate(buildDate);
	}

	public boolean getVerified() {
		return _release.getVerified();
	}

	public boolean isVerified() {
		return _release.isVerified();
	}

	public void setVerified(boolean verified) {
		_release.setVerified(verified);
	}

	public java.lang.String getTestString() {
		return _release.getTestString();
	}

	public void setTestString(java.lang.String testString) {
		_release.setTestString(testString);
	}

	public com.liferay.portal.model.Release toEscapedModel() {
		return _release.toEscapedModel();
	}

	public boolean isNew() {
		return _release.isNew();
	}

	public void setNew(boolean n) {
		_release.setNew(n);
	}

	public boolean isCachedModel() {
		return _release.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_release.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _release.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_release.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _release.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _release.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_release.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _release.clone();
	}

	public int compareTo(com.liferay.portal.model.Release release) {
		return _release.compareTo(release);
	}

	public int hashCode() {
		return _release.hashCode();
	}

	public java.lang.String toString() {
		return _release.toString();
	}

	public java.lang.String toXmlString() {
		return _release.toXmlString();
	}

	public Release getWrappedRelease() {
		return _release;
	}

	private Release _release;
}