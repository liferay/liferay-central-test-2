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