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
 * <a href="JournalFeedLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.journal.service.JournalFeedLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.journal.service.JournalFeedLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalFeedLocalService
 * @see com.liferay.portlet.journal.service.JournalFeedLocalServiceFactory
 *
 */
public class JournalFeedLocalServiceUtil {
	public static com.liferay.portlet.journal.model.JournalFeed addJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addJournalFeed(journalFeed);
	}

	public static void deleteJournalFeed(long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.deleteJournalFeed(id);
	}

	public static void deleteJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.deleteJournalFeed(journalFeed);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.journal.model.JournalFeed getJournalFeed(
		long id)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getJournalFeed(id);
	}

	public static com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.updateJournalFeed(journalFeed);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		long userId, long plid, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeed(userId, plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		long userId, long plid, java.lang.String feedId, boolean autoFeedId,
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
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeed(userId, plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeed(uuid, userId, plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeed(uuid, userId, plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		java.lang.String uuid, long userId, long plid, java.lang.String feedId,
		boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeed(uuid, userId, plid, feedId,
			autoFeedId, name, description, type, structureId, templateId,
			rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.journal.model.JournalFeed addFeedToGroup(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.addFeedToGroup(uuid, userId, groupId,
			feedId, autoFeedId, name, description, type, structureId,
			templateId, rendererTemplateId, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public static void addFeedResources(long feedId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.addFeedResources(feedId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.addFeedResources(feed, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addFeedResources(long feedId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.addFeedResources(feedId, communityPermissions,
			guestPermissions);
	}

	public static void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.addFeedResources(feed, communityPermissions,
			guestPermissions);
	}

	public static void deleteFeed(long feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.deleteFeed(feedId);
	}

	public static void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.deleteFeed(groupId, feedId);
	}

	public static void deleteFeed(
		com.liferay.portlet.journal.model.JournalFeed feed)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		journalFeedLocalService.deleteFeed(feed);
	}

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		long feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeed(feedId);
	}

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeed(groupId, feedId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds()
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeeds();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId) throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeeds(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeeds(groupId, start, end);
	}

	public static int getFeedsCount(long groupId)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.getFeedsCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.search(companyId, groupId, keywords,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.search(companyId, groupId, feedId, name,
			description, andOperator, start, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.searchCount(companyId, groupId, keywords);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.searchCount(companyId, groupId, feedId,
			name, description, andOperator);
	}

	public static com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		JournalFeedLocalService journalFeedLocalService = JournalFeedLocalServiceFactory.getService();

		return journalFeedLocalService.updateFeed(groupId, feedId, name,
			description, type, structureId, templateId, rendererTemplateId,
			delta, orderByCol, orderByType, targetLayoutFriendlyUrl,
			targetPortletId, contentField, feedType, feedVersion);
	}
}