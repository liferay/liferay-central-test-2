/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchLocalServiceUtil {
	public static void checkContentSearches(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();
		journalContentSearchLocalService.checkContentSearches(companyId);
	}

	public static void deleteArticleContentSearches(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
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

	public static java.util.List getArticleContentSearches(
		java.lang.String companyId, java.lang.String groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getArticleContentSearches(companyId,
			groupId, articleId);
	}

	public static java.util.List getLayoutIds(java.lang.String ownerId,
		java.lang.String groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIds(ownerId, groupId,
			articleId);
	}

	public static int getLayoutIdsCount(java.lang.String ownerId,
		java.lang.String groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.getLayoutIdsCount(ownerId,
			groupId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		java.lang.String portletId, java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String companyId,
		java.lang.String groupId, java.lang.String articleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(portletId,
			layoutId, ownerId, companyId, groupId, articleId);
	}

	public static java.util.List updateContentSearch(
		java.lang.String portletId, java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String companyId,
		java.lang.String groupId, java.lang.String[] articleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		JournalContentSearchLocalService journalContentSearchLocalService = JournalContentSearchLocalServiceFactory.getService();

		return journalContentSearchLocalService.updateContentSearch(portletId,
			layoutId, ownerId, companyId, groupId, articleIds);
	}
}