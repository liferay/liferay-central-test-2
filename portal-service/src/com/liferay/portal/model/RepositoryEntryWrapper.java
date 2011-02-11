/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link RepositoryEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RepositoryEntry
 * @generated
 */
public class RepositoryEntryWrapper implements RepositoryEntry {
	public RepositoryEntryWrapper(RepositoryEntry repositoryEntry) {
		_repositoryEntry = repositoryEntry;
	}

	/**
	* Gets the primary key of this repository entry.
	*
	* @return the primary key of this repository entry
	*/
	public long getPrimaryKey() {
		return _repositoryEntry.getPrimaryKey();
	}

	/**
	* Sets the primary key of this repository entry
	*
	* @param pk the primary key of this repository entry
	*/
	public void setPrimaryKey(long pk) {
		_repositoryEntry.setPrimaryKey(pk);
	}

	/**
	* Gets the repository entry ID of this repository entry.
	*
	* @return the repository entry ID of this repository entry
	*/
	public long getRepositoryEntryId() {
		return _repositoryEntry.getRepositoryEntryId();
	}

	/**
	* Sets the repository entry ID of this repository entry.
	*
	* @param repositoryEntryId the repository entry ID of this repository entry
	*/
	public void setRepositoryEntryId(long repositoryEntryId) {
		_repositoryEntry.setRepositoryEntryId(repositoryEntryId);
	}

	/**
	* Gets the repository ID of this repository entry.
	*
	* @return the repository ID of this repository entry
	*/
	public long getRepositoryId() {
		return _repositoryEntry.getRepositoryId();
	}

	/**
	* Sets the repository ID of this repository entry.
	*
	* @param repositoryId the repository ID of this repository entry
	*/
	public void setRepositoryId(long repositoryId) {
		_repositoryEntry.setRepositoryId(repositoryId);
	}

	/**
	* Gets the mapped ID of this repository entry.
	*
	* @return the mapped ID of this repository entry
	*/
	public java.lang.String getMappedId() {
		return _repositoryEntry.getMappedId();
	}

	/**
	* Sets the mapped ID of this repository entry.
	*
	* @param mappedId the mapped ID of this repository entry
	*/
	public void setMappedId(java.lang.String mappedId) {
		_repositoryEntry.setMappedId(mappedId);
	}

	public boolean isNew() {
		return _repositoryEntry.isNew();
	}

	public void setNew(boolean n) {
		_repositoryEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _repositoryEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_repositoryEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _repositoryEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_repositoryEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _repositoryEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _repositoryEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_repositoryEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new RepositoryEntryWrapper((RepositoryEntry)_repositoryEntry.clone());
	}

	public int compareTo(
		com.liferay.portal.model.RepositoryEntry repositoryEntry) {
		return _repositoryEntry.compareTo(repositoryEntry);
	}

	public int hashCode() {
		return _repositoryEntry.hashCode();
	}

	public com.liferay.portal.model.RepositoryEntry toEscapedModel() {
		return new RepositoryEntryWrapper(_repositoryEntry.toEscapedModel());
	}

	public java.lang.String toString() {
		return _repositoryEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _repositoryEntry.toXmlString();
	}

	public RepositoryEntry getWrappedRepositoryEntry() {
		return _repositoryEntry;
	}

	private RepositoryEntry _repositoryEntry;
}