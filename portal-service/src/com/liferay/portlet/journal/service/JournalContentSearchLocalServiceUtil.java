/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

/**
 * <a href="JournalContentSearchLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.journal.service.JournalContentSearchLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalContentSearchLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalContentSearchLocalService
 * @see com.liferay.portlet.journal.service.JournalContentSearchLocalServiceFactory
 *
 */
public class JournalContentSearchLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.dynamicQuery(queryInitializer,
			begin, end);
	}

	public static void checkContentSearches(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.checkContentSearches(companyId);
	}

	public static void deleteArticleContentSearch(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteArticleContentSearch(groupId,
			privateLayout, layoutId, portletId, articleId);
	}

	public static void deleteArticleContentSearches(long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteArticleContentSearches(groupId,
			articleId);
	}

	public static void deleteLayoutContentSearches(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteLayoutContentSearches(groupId,
			privateLayout, layoutId);
	}

	public static void deleteOwnerContentSearches(long groupId,
		boolean privateLayout) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteOwnerContentSearches(groupId,
			privateLayout);
	}

	public static java.util.List getArticleContentSearches()
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getArticleContentSearches();
	}

	public static java.util.List getArticleContentSearches(long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getArticleContentSearches(groupId,
			articleId);
	}

	public static java.util.List getLayoutIds(long groupId,
		boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIds(groupId,
			privateLayout, articleId);
	}

	public static int getLayoutIdsCount(long groupId, boolean privateLayout,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIdsCount(groupId,
			privateLayout, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(groupId,
			privateLayout, layoutId, portletId, articleId);
	}

	public static java.util.List updateContentSearch(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String[] articleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(groupId,
			privateLayout, layoutId, portletId, articleIds);
	}
}