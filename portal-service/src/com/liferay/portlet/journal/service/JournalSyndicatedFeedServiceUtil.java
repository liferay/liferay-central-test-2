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

package com.liferay.portlet.journal.service;


/**
 * <a href="JournalSyndicatedFeedServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalSyndicatedFeedService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalSyndicatedFeedServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalSyndicatedFeedService
 * @see com.liferay.portlet.journal.service.JournalSyndicatedFeedServiceFactory
 *
 */
public class JournalSyndicatedFeedServiceUtil {
	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		long plid, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		return journalSyndicatedFeedService.addSyndicatedFeed(plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed addSyndicatedFeed(
		long plid, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		return journalSyndicatedFeedService.addSyndicatedFeed(plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, communityPermissions, guestPermissions);
	}

	public static void deleteSyndicatedFeed(long groupId, long synFeedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		journalSyndicatedFeedService.deleteSyndicatedFeed(groupId, synFeedId);
	}

	public static void deleteSyndicatedFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		journalSyndicatedFeedService.deleteSyndicatedFeed(groupId, feedId);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed getSyndicatedFeed(
		long groupId, long synFeedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		return journalSyndicatedFeedService.getSyndicatedFeed(groupId, synFeedId);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed getSyndicatedFeed(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		return journalSyndicatedFeedService.getSyndicatedFeed(groupId, feedId);
	}

	public static com.liferay.portlet.journal.model.JournalSyndicatedFeed updateSyndicatedFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		JournalSyndicatedFeedService journalSyndicatedFeedService = JournalSyndicatedFeedServiceFactory.getService();

		return journalSyndicatedFeedService.updateSyndicatedFeed(groupId,
			feedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion);
	}
}