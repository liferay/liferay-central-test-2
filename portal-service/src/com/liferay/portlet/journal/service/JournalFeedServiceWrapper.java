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
 * <a href="JournalFeedServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalFeedService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedService
 * @generated
 */
public class JournalFeedServiceWrapper implements JournalFeedService {
	public JournalFeedServiceWrapper(JournalFeedService journalFeedService) {
		_journalFeedService = journalFeedService;
	}

	public com.liferay.portlet.journal.model.JournalFeed addFeed(long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalFeedService.addFeed(groupId, feedId, autoFeedId, name,
			description, type, structureId, templateId, rendererTemplateId,
			delta, orderByCol, orderByType, targetLayoutFriendlyUrl,
			targetPortletId, contentField, feedType, feedVersion, serviceContext);
	}

	public void deleteFeed(long groupId, long feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalFeedService.deleteFeed(groupId, feedId);
	}

	public void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_journalFeedService.deleteFeed(groupId, feedId);
	}

	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		long feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalFeedService.getFeed(groupId, feedId);
	}

	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalFeedService.getFeed(groupId, feedId);
	}

	public com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _journalFeedService.updateFeed(groupId, feedId, name,
			description, type, structureId, templateId, rendererTemplateId,
			delta, orderByCol, orderByType, targetLayoutFriendlyUrl,
			targetPortletId, contentField, feedType, feedVersion, serviceContext);
	}

	public JournalFeedService getWrappedJournalFeedService() {
		return _journalFeedService;
	}

	private JournalFeedService _journalFeedService;
}