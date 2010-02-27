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
 * <a href="JournalArticleImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link JournalArticleImageLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleImageLocalService
 * @generated
 */
public class JournalArticleImageLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalArticleImage addJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addJournalArticleImage(journalArticleImage);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId) {
		return getService().createJournalArticleImage(articleImageId);
	}

	public static void deleteJournalArticleImage(long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalArticleImage(articleImageId);
	}

	public static void deleteJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteJournalArticleImage(journalArticleImage);
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

	public static com.liferay.portlet.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleImage(articleImageId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleImages(start, end);
	}

	public static int getJournalArticleImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getJournalArticleImagesCount();
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateJournalArticleImage(journalArticleImage);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateJournalArticleImage(journalArticleImage, merge);
	}

	public static void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addArticleImageId(articleImageId, groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	public static void deleteArticleImage(long articleImageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticleImage(articleImageId);
	}

	public static void deleteArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage articleImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticleImage(articleImage);
	}

	public static void deleteArticleImage(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteArticleImage(groupId, articleId, version, elInstanceId,
			elName, languageId);
	}

	public static void deleteImages(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteImages(groupId, articleId, version);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage getArticleImage(
		long articleImageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleImage(articleImageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId, tempImage);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getArticleImages(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArticleImages(groupId);
	}

	public static JournalArticleImageLocalService getService() {
		if (_service == null) {
			_service = (JournalArticleImageLocalService)PortalBeanLocatorUtil.locate(JournalArticleImageLocalService.class.getName());
		}

		return _service;
	}

	public void setService(JournalArticleImageLocalService service) {
		_service = service;
	}

	private static JournalArticleImageLocalService _service;
}