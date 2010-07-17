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
 * This class is a wrapper for {@link JournalContentSearch}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearch
 * @generated
 */
public class JournalContentSearchWrapper implements JournalContentSearch {
	public JournalContentSearchWrapper(
		JournalContentSearch journalContentSearch) {
		_journalContentSearch = journalContentSearch;
	}

	public long getPrimaryKey() {
		return _journalContentSearch.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalContentSearch.setPrimaryKey(pk);
	}

	public long getContentSearchId() {
		return _journalContentSearch.getContentSearchId();
	}

	public void setContentSearchId(long contentSearchId) {
		_journalContentSearch.setContentSearchId(contentSearchId);
	}

	public long getGroupId() {
		return _journalContentSearch.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalContentSearch.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _journalContentSearch.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_journalContentSearch.setCompanyId(companyId);
	}

	public boolean getPrivateLayout() {
		return _journalContentSearch.getPrivateLayout();
	}

	public boolean isPrivateLayout() {
		return _journalContentSearch.isPrivateLayout();
	}

	public void setPrivateLayout(boolean privateLayout) {
		_journalContentSearch.setPrivateLayout(privateLayout);
	}

	public long getLayoutId() {
		return _journalContentSearch.getLayoutId();
	}

	public void setLayoutId(long layoutId) {
		_journalContentSearch.setLayoutId(layoutId);
	}

	public java.lang.String getPortletId() {
		return _journalContentSearch.getPortletId();
	}

	public void setPortletId(java.lang.String portletId) {
		_journalContentSearch.setPortletId(portletId);
	}

	public java.lang.String getArticleId() {
		return _journalContentSearch.getArticleId();
	}

	public void setArticleId(java.lang.String articleId) {
		_journalContentSearch.setArticleId(articleId);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch toEscapedModel() {
		return _journalContentSearch.toEscapedModel();
	}

	public boolean isNew() {
		return _journalContentSearch.isNew();
	}

	public void setNew(boolean n) {
		_journalContentSearch.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalContentSearch.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalContentSearch.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalContentSearch.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalContentSearch.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalContentSearch.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalContentSearch.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalContentSearch.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalContentSearch.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return _journalContentSearch.compareTo(journalContentSearch);
	}

	public int hashCode() {
		return _journalContentSearch.hashCode();
	}

	public java.lang.String toString() {
		return _journalContentSearch.toString();
	}

	public java.lang.String toXmlString() {
		return _journalContentSearch.toXmlString();
	}

	public JournalContentSearch getWrappedJournalContentSearch() {
		return _journalContentSearch;
	}

	private JournalContentSearch _journalContentSearch;
}