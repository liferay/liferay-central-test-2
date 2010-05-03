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
 * <a href="JournalArticleResourceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link JournalArticleResourceLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourceLocalService
 * @generated
 */
public class JournalArticleResourceLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticleResource addJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addJournalArticleResource(journalArticleResource);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return getService().createJournalArticleResource(resourcePrimKey);
	}

	public static void deleteJournalArticleResource(long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalArticleResource(resourcePrimKey);
	}

	public static void deleteJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalArticleResource(journalArticleResource);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleResource(resourcePrimKey);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getJournalArticleResources(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleResources(start, end);
	}

	public static int getJournalArticleResourcesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleResourcesCount();
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateJournalArticleResource(journalArticleResource);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateJournalArticleResource(journalArticleResource, merge);
	}

	public static void deleteArticleResource(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticleResource(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleResource(articleResourcePrimKey);
	}

	public static long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleResourcePrimKey(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getArticleResources(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleResources(groupId);
	}

	public static JournalArticleResourceLocalService getService() {
		if (_service == null) {
			_service = (JournalArticleResourceLocalService)PortalBeanLocatorUtil.locate(JournalArticleResourceLocalService.class.getName());
		}

		return _service;
	}

	public void setService(JournalArticleResourceLocalService service) {
		_service = service;
	}

	private static JournalArticleResourceLocalService _service;
}