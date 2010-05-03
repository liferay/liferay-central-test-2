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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalArticleImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalArticleImageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleImageLocalService
 * @generated
 */
public class JournalArticleImageLocalServiceWrapper
	implements JournalArticleImageLocalService {
	public JournalArticleImageLocalServiceWrapper(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		_journalArticleImageLocalService = journalArticleImageLocalService;
	}

	public com.liferay.portlet.journal.model.JournalArticleImage addJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.addJournalArticleImage(journalArticleImage);
	}

	public com.liferay.portlet.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId) {
		return _journalArticleImageLocalService.createJournalArticleImage(articleImageId);
	}

	public void deleteJournalArticleImage(long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteJournalArticleImage(articleImageId);
	}

	public void deleteJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteJournalArticleImage(journalArticleImage);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getJournalArticleImage(articleImageId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getJournalArticleImages(start,
			end);
	}

	public int getJournalArticleImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getJournalArticleImagesCount();
	}

	public com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.updateJournalArticleImage(journalArticleImage);
	}

	public com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.updateJournalArticleImage(journalArticleImage,
			merge);
	}

	public void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.addArticleImageId(articleImageId,
			groupId, articleId, version, elInstanceId, elName, languageId);
	}

	public void deleteArticleImage(long articleImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteArticleImage(articleImageId);
	}

	public void deleteArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage articleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteArticleImage(articleImage);
	}

	public void deleteArticleImage(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteArticleImage(groupId, articleId,
			version, elInstanceId, elName, languageId);
	}

	public void deleteImages(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleImageLocalService.deleteImages(groupId, articleId,
			version);
	}

	public com.liferay.portlet.journal.model.JournalArticleImage getArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getArticleImage(articleImageId);
	}

	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getArticleImageId(groupId,
			articleId, version, elInstanceId, elName, languageId);
	}

	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getArticleImageId(groupId,
			articleId, version, elInstanceId, elName, languageId, tempImage);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getArticleImages(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleImageLocalService.getArticleImages(groupId);
	}

	public JournalArticleImageLocalService getWrappedJournalArticleImageLocalService() {
		return _journalArticleImageLocalService;
	}

	private JournalArticleImageLocalService _journalArticleImageLocalService;
}