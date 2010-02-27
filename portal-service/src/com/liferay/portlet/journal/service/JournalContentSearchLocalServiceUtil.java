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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="JournalContentSearchLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link JournalContentSearchLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchLocalService
 * @generated
 */
public class JournalContentSearchLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalContentSearch addJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addJournalContentSearch(journalContentSearch);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch createJournalContentSearch(
		long contentSearchId) {
		return getService().createJournalContentSearch(contentSearchId);
	}

	public static void deleteJournalContentSearch(long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalContentSearch(contentSearchId);
	}

	public static void deleteJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalContentSearch(journalContentSearch);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch getJournalContentSearch(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalContentSearch(contentSearchId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getJournalContentSearchs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalContentSearchs(start, end);
	}

	public static int getJournalContentSearchsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalContentSearchsCount();
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateJournalContentSearch(journalContentSearch);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateJournalContentSearch(journalContentSearch, merge);
	}

	public static void checkContentSearches(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkContentSearches(companyId);
	}

	public static void deleteArticleContentSearch(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteArticleContentSearch(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	public static void deleteArticleContentSearches(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticleContentSearches(groupId, articleId);
	}

	public static void deleteLayoutContentSearches(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteLayoutContentSearches(groupId, privateLayout, layoutId);
	}

	public static void deleteOwnerContentSearches(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteOwnerContentSearches(groupId, privateLayout);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleContentSearches();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleContentSearches(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleContentSearches(articleId);
	}

	public static java.util.List<Long> getLayoutIds(long groupId,
		boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutIds(groupId, privateLayout, articleId);
	}

	public static int getLayoutIdsCount(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutIdsCount(groupId, privateLayout, articleId);
	}

	public static int getLayoutIdsCount(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutIdsCount(articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateContentSearch(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateContentSearch(groupId, privateLayout, layoutId,
			portletId, articleId, purge);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String[] articleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateContentSearch(groupId, privateLayout, layoutId,
			portletId, articleIds);
	}

	public static JournalContentSearchLocalService getService() {
		if (_service == null) {
			_service = (JournalContentSearchLocalService)PortalBeanLocatorUtil.locate(JournalContentSearchLocalService.class.getName());
		}

		return _service;
	}

	public void setService(JournalContentSearchLocalService service) {
		_service = service;
	}

	private static JournalContentSearchLocalService _service;
}