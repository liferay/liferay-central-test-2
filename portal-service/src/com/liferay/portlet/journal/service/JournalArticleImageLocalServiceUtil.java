/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
		throws com.liferay.portal.SystemException {
		return getService().addJournalArticleImage(journalArticleImage);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage createJournalArticleImage(
		long articleImageId) {
		return getService().createJournalArticleImage(articleImageId);
	}

	public static void deleteJournalArticleImage(long articleImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteJournalArticleImage(articleImageId);
	}

	public static void deleteJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.SystemException {
		getService().deleteJournalArticleImage(journalArticleImage);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage getJournalArticleImage(
		long articleImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getJournalArticleImage(articleImageId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getJournalArticleImages(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getJournalArticleImages(start, end);
	}

	public static int getJournalArticleImagesCount()
		throws com.liferay.portal.SystemException {
		return getService().getJournalArticleImagesCount();
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage)
		throws com.liferay.portal.SystemException {
		return getService().updateJournalArticleImage(journalArticleImage);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage updateJournalArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage journalArticleImage,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateJournalArticleImage(journalArticleImage, merge);
	}

	public static void addArticleImageId(long articleImageId, long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addArticleImageId(articleImageId, groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	public static void deleteArticleImage(long articleImageId)
		throws com.liferay.portal.SystemException {
		getService().deleteArticleImage(articleImageId);
	}

	public static void deleteArticleImage(
		com.liferay.portlet.journal.model.JournalArticleImage articleImage)
		throws com.liferay.portal.SystemException {
		getService().deleteArticleImage(articleImage);
	}

	public static void deleteArticleImage(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		getService()
			.deleteArticleImage(groupId, articleId, version, elInstanceId,
			elName, languageId);
	}

	public static void deleteImages(long groupId, java.lang.String articleId,
		double version) throws com.liferay.portal.SystemException {
		getService().deleteImages(groupId, articleId, version);
	}

	public static com.liferay.portlet.journal.model.JournalArticleImage getArticleImage(
		long articleImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getArticleImage(articleImageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId);
	}

	public static long getArticleImageId(long groupId,
		java.lang.String articleId, double version,
		java.lang.String elInstanceId, java.lang.String elName,
		java.lang.String languageId, boolean tempImage)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getArticleImageId(groupId, articleId, version,
			elInstanceId, elName, languageId, tempImage);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleImage> getArticleImages(
		long groupId) throws com.liferay.portal.SystemException {
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