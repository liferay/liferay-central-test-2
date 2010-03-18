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
 * <a href="JournalArticleResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalArticleResourceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourceLocalService
 * @generated
 */
public class JournalArticleResourceLocalServiceWrapper
	implements JournalArticleResourceLocalService {
	public JournalArticleResourceLocalServiceWrapper(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		_journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	public com.liferay.portlet.journal.model.JournalArticleResource addJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.addJournalArticleResource(journalArticleResource);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return _journalArticleResourceLocalService.createJournalArticleResource(resourcePrimKey);
	}

	public void deleteJournalArticleResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteJournalArticleResource(resourcePrimKey);
	}

	public void deleteJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteJournalArticleResource(journalArticleResource);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResource(resourcePrimKey);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getJournalArticleResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResources(start,
			end);
	}

	public int getJournalArticleResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getJournalArticleResourcesCount();
	}

	public com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.updateJournalArticleResource(journalArticleResource);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.updateJournalArticleResource(journalArticleResource,
			merge);
	}

	public void deleteArticleResource(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResourceLocalService.deleteArticleResource(groupId,
			articleId);
	}

	public com.liferay.portlet.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResource(articleResourcePrimKey);
	}

	public long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResourcePrimKey(groupId,
			articleId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getArticleResources(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalArticleResourceLocalService.getArticleResources(groupId);
	}

	public JournalArticleResourceLocalService getWrappedJournalArticleResourceLocalService() {
		return _journalArticleResourceLocalService;
	}

	private JournalArticleResourceLocalService _journalArticleResourceLocalService;
}