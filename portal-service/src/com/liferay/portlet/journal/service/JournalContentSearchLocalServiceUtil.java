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

	public static void deleteArticleContentSearches(long companyId,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteArticleContentSearches(companyId,
			groupId, articleId);
	}

	public static void deleteLayoutContentSearches(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteLayoutContentSearches(layoutId,
			ownerId);
	}

	public static void deleteOwnerContentSearches(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.deleteOwnerContentSearches(ownerId);
	}

	public static java.util.List getArticleContentSearches()
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getArticleContentSearches();
	}

	public static java.util.List getArticleContentSearches(long companyId,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getArticleContentSearches(companyId,
			groupId, articleId);
	}

	public static java.util.List getLayoutIds(java.lang.String ownerId,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIds(ownerId, groupId,
			articleId);
	}

	public static int getLayoutIdsCount(java.lang.String ownerId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIdsCount(ownerId,
			groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		java.lang.String portletId, java.lang.String layoutId,
		java.lang.String ownerId, long companyId, long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(portletId,
			layoutId, ownerId, companyId, groupId, articleId);
	}

	public static java.util.List updateContentSearch(
		java.lang.String portletId, java.lang.String layoutId,
		java.lang.String ownerId, long companyId, long groupId,
		java.lang.String[] articleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(portletId,
			layoutId, ownerId, companyId, groupId, articleIds);
	}
}