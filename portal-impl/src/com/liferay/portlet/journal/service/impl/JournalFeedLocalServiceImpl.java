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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.mirage.model.JournalFeedCriteria;
import com.liferay.portal.mirage.model.MirageJournalFeed;
import com.liferay.portal.mirage.service.MirageServiceFactory;
import com.liferay.portal.mirage.util.SmartCriteria;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.service.base.JournalFeedLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.ContentFeed;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalFeedLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Aug�
 * @author Karthik Sudarshan
 *
 */
public class JournalFeedLocalServiceImpl
	extends JournalFeedLocalServiceBaseImpl {

	public JournalFeed addFeed(
			long userId, long plid, String feedId, boolean autoFeedId,
			String name, String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFeed(
			null, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalFeed addFeed(
			long userId, long plid, String feedId, boolean autoFeedId,
			String name, String description, String type, String structureId,
			String templateId, String rendererTemplateId, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addFeed(
			null, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, null, null, communityPermissions,
			guestPermissions);
	}

	public JournalFeed addFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFeed(
			uuid, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalFeed addFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addFeed(
			uuid, userId, plid, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType,targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, null, null, communityPermissions,
			guestPermissions);
	}

	public JournalFeed addFeed(
			String uuid, long userId, long plid, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addFeedToGroup(
			uuid, userId, groupId, feedId, autoFeedId, name, description, type,
			structureId, templateId, rendererTemplateId, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public JournalFeed addFeedToGroup(
			String uuid, long userId, long groupId, String feedId,
			boolean autoFeedId, String name, String description, String type,
			String structureId, String templateId, String rendererTemplateId,
			int delta, String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedType, double feedVersion,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Feed

		JournalFeed feed = new JournalFeedImpl();

		feed.setUuid(uuid);
		feed.setUserId(userId);
		feed.setGroupId(groupId);
		feed.setFeedId(feedId);
		feed.setName(name);
		feed.setDescription(description);
		feed.setType(type);
		feed.setStructureId(structureId);
		feed.setTemplateId(templateId);
		feed.setRendererTemplateId(rendererTemplateId);
		feed.setDelta(delta);
		feed.setOrderByCol(orderByCol);
		feed.setOrderByType(orderByType);
		feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		feed.setTargetPortletId(targetPortletId);
		feed.setContentField(contentField);
		feed.setFeedType(feedType);
		feed.setFeedVersion(feedVersion);

		MirageJournalFeed mirageJournalFeed = new MirageJournalFeed(feed);

		mirageJournalFeed.setAutoFeedId(autoFeedId);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.createContentFeed(mirageJournalFeed);
		}
		catch (CMSException cmse) {
			processException(cmse);
		}

		feed = mirageJournalFeed.getFeed();

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFeedResources(
				feed, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFeedResources(feed, communityPermissions, guestPermissions);
		}

		return feed;
	}

	public void addFeedResources(
			long feedId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalFeed feed = getFeed(feedId);

		addFeedResources(feed, addCommunityPermissions, addGuestPermissions);
	}

	public void addFeedResources(
			JournalFeed feed, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFeedResources(
			long feedId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalFeed feed = getFeed(feedId);

		addFeedResources(feed, communityPermissions, guestPermissions);
	}

	public void addFeedResources(
			JournalFeed feed, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			feed.getCompanyId(), feed.getGroupId(), feed.getUserId(),
			JournalFeed.class.getName(), feed.getId(), communityPermissions,
			guestPermissions);
	}

	public void deleteFeed(long feedId)
		throws PortalException, SystemException {

		JournalFeed feed = getFeed(feedId);

		deleteFeed(feed);
	}

	public void deleteFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalFeed feed = getFeed(groupId, feedId);

		deleteFeed(feed);
	}

	public void deleteFeed(JournalFeed feed)
		throws PortalException, SystemException {

		// Resources

		resourceLocalService.deleteResource(
			feed.getCompanyId(), JournalFeed.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, feed.getId());

		// Feed

		MirageJournalFeed mirageJournalFeed = new MirageJournalFeed(feed);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.deleteContentFeed(mirageJournalFeed);
		}
		catch (CMSException cmse) {
			processException(cmse);
		}
	}

	public JournalFeed getFeed(long id)
		throws PortalException, SystemException {

		JournalFeed feed = new JournalFeedImpl();

		feed.setId(id);

		MirageJournalFeed mirageJournalFeed = new MirageJournalFeed(feed);

		JournalFeedCriteria criteria = new JournalFeedCriteria(
			JournalFeedCriteria.FIND_BY_PRIMARY_KEY);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			mirageJournalFeed =
				(MirageJournalFeed)contentFeedService.getContentFeed(
					mirageJournalFeed, criteria);
		}
		catch (CMSException cmse) {
			processException(cmse);
		}

		return mirageJournalFeed.getFeed();
	}

	public JournalFeed getFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalFeed feed = new JournalFeedImpl();

		feed.setGroupId(groupId);
		feed.setFeedId(feedId);

		MirageJournalFeed mirageJournalFeed = new MirageJournalFeed(feed);

		JournalFeedCriteria criteria = new JournalFeedCriteria(
			JournalFeedCriteria.FIND_BY_G_F);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			mirageJournalFeed =
				(MirageJournalFeed)contentFeedService.getContentFeed(
					mirageJournalFeed, criteria);
		}
		catch (CMSException cmse) {
			processException(cmse);
		}

		return mirageJournalFeed.getFeed();
	}

	public List<JournalFeed> getFeeds() throws SystemException {
		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.FIND_ALL);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria.getCriteria());

			return getFeeds(contentFeeds);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public List<JournalFeed> getFeeds(long groupId) throws SystemException {
		return getFeeds(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<JournalFeed> getFeeds(long groupId, int start, int end)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.FIND_BY_GROUP_ID);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);
		criteria.add(JournalFeedCriteria.START, start);
		criteria.add(JournalFeedCriteria.END, end);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria.getCriteria());

			return getFeeds(contentFeeds);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public int getFeedsCount(long groupId) throws SystemException {
		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.COUNT_BY_GROUP_ID);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public List<JournalFeed> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.FIND_BY_KEYWORDS);
		criteria.add(JournalFeedCriteria.COMPANY_ID, companyId);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);
		criteria.add(JournalFeedCriteria.KEYWORDS, keywords);
		criteria.add(JournalFeedCriteria.START, start);
		criteria.add(JournalFeedCriteria.END, end);

		criteria.setOrderByComparator(obc);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria.getCriteria());

			return getFeeds(contentFeeds);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public List<JournalFeed> search(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.FIND_BY_C_G_F_N_D);
		criteria.add(JournalFeedCriteria.COMPANY_ID, companyId);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);
		criteria.add(JournalFeedCriteria.FEED_ID, feedId);
		criteria.add(JournalFeedCriteria.NAME, name);
		criteria.add(JournalFeedCriteria.DESCRIPTION, description);
		criteria.add(JournalFeedCriteria.START, start);
		criteria.add(JournalFeedCriteria.END, end);

		criteria.setAndOperator(andOperator);
		criteria.setOrderByComparator(obc);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria.getCriteria());

			return getFeeds(contentFeeds);
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.COUNT_BY_KEYWORDS);
		criteria.add(JournalFeedCriteria.COMPANY_ID, companyId);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);
		criteria.add(JournalFeedCriteria.KEYWORDS, keywords);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public int searchCount(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator)
		throws SystemException {

		SmartCriteria criteria = new SmartCriteria();

		criteria.add(
			JournalFeedCriteria.QUERY, JournalFeedCriteria.COUNT_BY_C_G_F_N_D);
		criteria.add(JournalFeedCriteria.COMPANY_ID, companyId);
		criteria.add(JournalFeedCriteria.GROUP_ID, groupId);
		criteria.add(JournalFeedCriteria.FEED_ID, feedId);
		criteria.add(JournalFeedCriteria.NAME, name);
		criteria.add(JournalFeedCriteria.DESCRIPTION, description);

		criteria.setAndOperator(andOperator);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(
				criteria.getCriteria());
		}
		catch (CMSException cmse) {
			throw (SystemException)cmse.getCause();
		}
	}

	public JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			String type, String structureId, String templateId,
			String rendererTemplateId, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion)
		throws PortalException, SystemException{

		// Feed

		JournalFeed feed = new JournalFeedImpl();

		feed.setGroupId(groupId);
		feed.setFeedId(feedId);
		feed.setName(name);
		feed.setDescription(description);
		feed.setType(type);
		feed.setStructureId(structureId);
		feed.setTemplateId(templateId);
		feed.setRendererTemplateId(rendererTemplateId);
		feed.setDelta(delta);
		feed.setOrderByCol(orderByCol);
		feed.setOrderByType(orderByType);
		feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		feed.setTargetPortletId(targetPortletId);
		feed.setContentField(contentField);
		feed.setFeedType(feedType);
		feed.setFeedVersion(feedVersion);

		MirageJournalFeed mirageJournalFeed = new MirageJournalFeed(feed);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.updateContentFeed(mirageJournalFeed, null);
		}
		catch (CMSException cmse) {
			processException(cmse);
		}

		feed = mirageJournalFeed.getFeed();

		return feed;
	}

	protected List<JournalFeed> getFeeds(List<ContentFeed> contentFeeds) {
		List<JournalFeed> feeds = new ArrayList<JournalFeed>(
			contentFeeds.size());

		for (ContentFeed contentFeed : contentFeeds) {
			MirageJournalFeed mirageJournalFeed =
				(MirageJournalFeed)contentFeed;

			feeds.add(mirageJournalFeed.getFeed());
		}

		return feeds;
	}

	protected void processException(CMSException cmse)
		throws PortalException, SystemException {

		Throwable cause = cmse.getCause();

		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException)cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException)cause;
			}
		}
	}

}