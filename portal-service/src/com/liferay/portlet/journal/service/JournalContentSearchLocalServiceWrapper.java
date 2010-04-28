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
 * <a href="JournalContentSearchLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalContentSearchLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchLocalService
 * @generated
 */
public class JournalContentSearchLocalServiceWrapper
	implements JournalContentSearchLocalService {
	public JournalContentSearchLocalServiceWrapper(
		JournalContentSearchLocalService journalContentSearchLocalService) {
		_journalContentSearchLocalService = journalContentSearchLocalService;
	}

	public com.liferay.portlet.journal.model.JournalContentSearch addJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.addJournalContentSearch(journalContentSearch);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch createJournalContentSearch(
		long contentSearchId) {
		return _journalContentSearchLocalService.createJournalContentSearch(contentSearchId);
	}

	public void deleteJournalContentSearch(long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteJournalContentSearch(contentSearchId);
	}

	public void deleteJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteJournalContentSearch(journalContentSearch);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch getJournalContentSearch(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getJournalContentSearch(contentSearchId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getJournalContentSearchs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getJournalContentSearchs(start,
			end);
	}

	public int getJournalContentSearchsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getJournalContentSearchsCount();
	}

	public com.liferay.portlet.journal.model.JournalContentSearch updateJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.updateJournalContentSearch(journalContentSearch);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch updateJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.updateJournalContentSearch(journalContentSearch,
			merge);
	}

	public void checkContentSearches(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.checkContentSearches(companyId);
	}

	public void deleteArticleContentSearch(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteArticleContentSearch(groupId,
			privateLayout, layoutId, portletId, articleId);
	}

	public void deleteArticleContentSearches(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteArticleContentSearches(groupId,
			articleId);
	}

	public void deleteLayoutContentSearches(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteLayoutContentSearches(groupId,
			privateLayout, layoutId);
	}

	public void deleteOwnerContentSearches(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearchLocalService.deleteOwnerContentSearches(groupId,
			privateLayout);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getArticleContentSearches();
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getArticleContentSearches(groupId,
			articleId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getArticleContentSearches(articleId);
	}

	public java.util.List<Long> getLayoutIds(long groupId,
		boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getLayoutIds(groupId,
			privateLayout, articleId);
	}

	public int getLayoutIdsCount(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getLayoutIdsCount(groupId,
			privateLayout, articleId);
	}

	public int getLayoutIdsCount(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.getLayoutIdsCount(articleId);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.updateContentSearch(groupId,
			privateLayout, layoutId, portletId, articleId);
	}

	public com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.updateContentSearch(groupId,
			privateLayout, layoutId, portletId, articleId, purge);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String[] articleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalContentSearchLocalService.updateContentSearch(groupId,
			privateLayout, layoutId, portletId, articleIds);
	}

	public JournalContentSearchLocalService getWrappedJournalContentSearchLocalService() {
		return _journalContentSearchLocalService;
	}

	private JournalContentSearchLocalService _journalContentSearchLocalService;
}