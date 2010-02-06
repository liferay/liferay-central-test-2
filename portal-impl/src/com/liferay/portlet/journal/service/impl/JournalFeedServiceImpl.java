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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.base.JournalFeedServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalFeedPermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;

/**
 * <a href="JournalFeedServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class JournalFeedServiceImpl extends JournalFeedServiceBaseImpl {

	public JournalFeed addFeed(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_FEED);

		return journalFeedLocalService.addFeed(
			getUserId(), groupId, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, serviceContext);
	}

	public void deleteFeed(long groupId, long feedId)
		throws PortalException, SystemException {

		JournalFeedPermission.check(
			getPermissionChecker(), feedId, ActionKeys.DELETE);

		journalFeedLocalService.deleteFeed(feedId);
	}

	public void deleteFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalFeedPermission.check(
			getPermissionChecker(), groupId, feedId, ActionKeys.DELETE);

		journalFeedLocalService.deleteFeed(groupId, feedId);
	}

	public JournalFeed getFeed(long groupId, long feedId)
		throws PortalException, SystemException {

		JournalFeedPermission.check(
			getPermissionChecker(), feedId, ActionKeys.VIEW);

		return journalFeedLocalService.getFeed(feedId);
	}

	public JournalFeed getFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalFeedPermission.check(
			getPermissionChecker(), groupId, feedId, ActionKeys.VIEW);

		return journalFeedLocalService.getFeed(groupId, feedId);
	}

	public JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			String type, String structureId, String templateId,
			String rendererTemplateId, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFeedPermission.check(
			getPermissionChecker(), groupId, feedId, ActionKeys.UPDATE);

		return journalFeedLocalService.updateFeed(
			groupId, feedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

}