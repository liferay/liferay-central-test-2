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
 * This class is a wrapper for {@link JournalArticleImage}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleImage
 * @generated
 */
public class JournalArticleImageWrapper implements JournalArticleImage {
	public JournalArticleImageWrapper(JournalArticleImage journalArticleImage) {
		_journalArticleImage = journalArticleImage;
	}

	public long getPrimaryKey() {
		return _journalArticleImage.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalArticleImage.setPrimaryKey(pk);
	}

	public long getArticleImageId() {
		return _journalArticleImage.getArticleImageId();
	}

	public void setArticleImageId(long articleImageId) {
		_journalArticleImage.setArticleImageId(articleImageId);
	}

	public long getGroupId() {
		return _journalArticleImage.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalArticleImage.setGroupId(groupId);
	}

	public java.lang.String getArticleId() {
		return _journalArticleImage.getArticleId();
	}

	public void setArticleId(java.lang.String articleId) {
		_journalArticleImage.setArticleId(articleId);
	}

	public double getVersion() {
		return _journalArticleImage.getVersion();
	}

	public void setVersion(double version) {
		_journalArticleImage.setVersion(version);
	}

	public java.lang.String getElInstanceId() {
		return _journalArticleImage.getElInstanceId();
	}

	public void setElInstanceId(java.lang.String elInstanceId) {
		_journalArticleImage.setElInstanceId(elInstanceId);
	}

	public java.lang.String getElName() {
		return _journalArticleImage.getElName();
	}

	public void setElName(java.lang.String elName) {
		_journalArticleImage.setElName(elName);
	}

	public java.lang.String getLanguageId() {
		return _journalArticleImage.getLanguageId();
	}

	public void setLanguageId(java.lang.String languageId) {
		_journalArticleImage.setLanguageId(languageId);
	}

	public boolean getTempImage() {
		return _journalArticleImage.getTempImage();
	}

	public boolean isTempImage() {
		return _journalArticleImage.isTempImage();
	}

	public void setTempImage(boolean tempImage) {
		_journalArticleImage.setTempImage(tempImage);
	}

	public com.liferay.portlet.journal.model.JournalArticleImage toEscapedModel() {
		return _journalArticleImage.toEscapedModel();
	}

	public boolean isNew() {
		return _journalArticleImage.isNew();
	}

	public void setNew(boolean n) {
		_journalArticleImage.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalArticleImage.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalArticleImage.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalArticleImage.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalArticleImage.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticleImage.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticleImage.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticleImage.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalArticleImage.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage) {
		return _journalArticleImage.compareTo(journalArticleImage);
	}

	public int hashCode() {
		return _journalArticleImage.hashCode();
	}

	public java.lang.String toString() {
		return _journalArticleImage.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticleImage.toXmlString();
	}

	public JournalArticleImage getWrappedJournalArticleImage() {
		return _journalArticleImage;
	}

	private JournalArticleImage _journalArticleImage;
}