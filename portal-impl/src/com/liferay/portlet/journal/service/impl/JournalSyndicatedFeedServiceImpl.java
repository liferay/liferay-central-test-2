/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalSyndicatedFeed;
import com.liferay.portlet.journal.service.base.JournalSyndicatedFeedServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalSyndicatedFeedPermission;

import java.util.Date;

/**
 * <a href="JournalSyndicatedFeedServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalSyndicatedFeedServiceImpl
	extends JournalSyndicatedFeedServiceBaseImpl {

	public JournalSyndicatedFeed addSyndicatedFeed(
			long plid, String feedId, boolean autoFeedId, String name,
			String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_SYNDICATED_FEED);

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(
			getUserId(), plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, addCommunityPermissions,
			addGuestPermissions);
	}

	public JournalSyndicatedFeed addSyndicatedFeed(
			long plid, String feedId, boolean autoFeedId, String name,
			String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_SYNDICATED_FEED);

		return journalSyndicatedFeedLocalService.addSyndicatedFeed(
			getUserId(), plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, communityPermissions, guestPermissions);
	}

	public void deleteSyndicatedFeed(long groupId, long synFeedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeedPermission.check(
				getPermissionChecker(), synFeedId, ActionKeys.DELETE);

		journalSyndicatedFeedLocalService.deleteSyndicatedFeed(synFeedId);
	}

	public void deleteSyndicatedFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeedPermission.check(
				getPermissionChecker(), groupId, feedId, ActionKeys.DELETE);

		journalSyndicatedFeedLocalService.deleteSyndicatedFeed(groupId, feedId);
	}

	public JournalSyndicatedFeed getSyndicatedFeed(long groupId, long synFeedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeedPermission.check(
			getPermissionChecker(), synFeedId, ActionKeys.VIEW);

		return journalSyndicatedFeedLocalService.getSyndicatedFeed(synFeedId);
	}

	public JournalSyndicatedFeed getSyndicatedFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalSyndicatedFeedPermission.check(
			getPermissionChecker(), groupId, feedId, ActionKeys.VIEW);

		return journalSyndicatedFeedLocalService.getSyndicatedFeed(
			groupId, feedId);
	}

	public JournalSyndicatedFeed updateSyndicatedFeed(
			long groupId, String feedId, String name, String description,
			String type, String structureId, String templateId,
			String rendererTemplateId, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion)
		throws PortalException, SystemException {

		JournalSyndicatedFeedPermission.check(
			getPermissionChecker(), groupId, feedId, ActionKeys.UPDATE);

		return journalSyndicatedFeedLocalService.updateSyndicatedFeed(
			groupId, feedId, name, description, type, structureId,
			templateId, rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion);
	}

}