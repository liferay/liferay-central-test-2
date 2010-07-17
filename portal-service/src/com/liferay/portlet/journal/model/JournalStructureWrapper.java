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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalStructure}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructure
 * @generated
 */
public class JournalStructureWrapper implements JournalStructure {
	public JournalStructureWrapper(JournalStructure journalStructure) {
		_journalStructure = journalStructure;
	}

	public long getPrimaryKey() {
		return _journalStructure.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalStructure.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _journalStructure.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_journalStructure.setUuid(uuid);
	}

	public long getId() {
		return _journalStructure.getId();
	}

	public void setId(long id) {
		_journalStructure.setId(id);
	}

	public long getGroupId() {
		return _journalStructure.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalStructure.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _journalStructure.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_journalStructure.setCompanyId(companyId);
	}

	public long getUserId() {
		return _journalStructure.getUserId();
	}

	public void setUserId(long userId) {
		_journalStructure.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructure.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_journalStructure.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _journalStructure.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_journalStructure.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _journalStructure.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_journalStructure.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _journalStructure.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalStructure.setModifiedDate(modifiedDate);
	}

	public java.lang.String getStructureId() {
		return _journalStructure.getStructureId();
	}

	public void setStructureId(java.lang.String structureId) {
		_journalStructure.setStructureId(structureId);
	}

	public java.lang.String getParentStructureId() {
		return _journalStructure.getParentStructureId();
	}

	public void setParentStructureId(java.lang.String parentStructureId) {
		_journalStructure.setParentStructureId(parentStructureId);
	}

	public java.lang.String getName() {
		return _journalStructure.getName();
	}

	public void setName(java.lang.String name) {
		_journalStructure.setName(name);
	}

	public java.lang.String getDescription() {
		return _journalStructure.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_journalStructure.setDescription(description);
	}

	public java.lang.String getXsd() {
		return _journalStructure.getXsd();
	}

	public void setXsd(java.lang.String xsd) {
		_journalStructure.setXsd(xsd);
	}

	public com.liferay.portlet.journal.model.JournalStructure toEscapedModel() {
		return _journalStructure.toEscapedModel();
	}

	public boolean isNew() {
		return _journalStructure.isNew();
	}

	public void setNew(boolean n) {
		_journalStructure.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalStructure.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalStructure.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalStructure.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalStructure.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalStructure.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalStructure.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalStructure.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalStructure.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalStructure journalStructure) {
		return _journalStructure.compareTo(journalStructure);
	}

	public int hashCode() {
		return _journalStructure.hashCode();
	}

	public java.lang.String toString() {
		return _journalStructure.toString();
	}

	public java.lang.String toXmlString() {
		return _journalStructure.toXmlString();
	}

	public java.lang.String getMergedXsd() {
		return _journalStructure.getMergedXsd();
	}

	public JournalStructure getWrappedJournalStructure() {
		return _journalStructure;
	}

	private JournalStructure _journalStructure;
}