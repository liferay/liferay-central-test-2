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
		throws com.liferay.portal.SystemException {
		return getService().addJournalArticleResource(journalArticleResource);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return getService().createJournalArticleResource(resourcePrimKey);
	}

	public static void deleteJournalArticleResource(long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteJournalArticleResource(resourcePrimKey);
	}

	public static void deleteJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.SystemException {
		getService().deleteJournalArticleResource(journalArticleResource);
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

	public static com.liferay.portlet.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getJournalArticleResource(resourcePrimKey);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getJournalArticleResources(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getJournalArticleResources(start, end);
	}

	public static int getJournalArticleResourcesCount()
		throws com.liferay.portal.SystemException {
		return getService().getJournalArticleResourcesCount();
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource)
		throws com.liferay.portal.SystemException {
		return getService().updateJournalArticleResource(journalArticleResource);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateJournalArticleResource(journalArticleResource, merge);
	}

	public static void deleteArticleResource(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteArticleResource(groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getArticleResource(articleResourcePrimKey);
	}

	public static long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getService().getArticleResourcePrimKey(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticleResource> getArticleResources(
		long groupId) throws com.liferay.portal.SystemException {
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