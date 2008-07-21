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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.mirage.custom.MirageServiceFactory;
import com.liferay.portal.mirage.model.JournalContentFeed;
import com.liferay.portal.mirage.model.OptionalJournalFeedCriteria;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.service.base.JournalFeedLocalServiceBaseImpl;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.ContentFeed;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

		// Create and populate the JournalFeed object

		JournalFeed feed = new JournalFeedImpl();

		feed.setUserId(userId);
		feed.setFeedId(feedId);
		feed.setGroupId(groupId);
		feed.setName(name);
		feed.setDescription(description);
		feed.setStructureId(structureId);
		feed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		feed.setContentField(contentField);
		feed.setUuid(uuid);
		feed.setType(type);
		feed.setTemplateId(templateId);
		feed.setRendererTemplateId(rendererTemplateId);
		feed.setDelta(delta);
		feed.setOrderByCol(orderByCol);
		feed.setOrderByType(orderByType);
		feed.setTargetPortletId(targetPortletId);
		feed.setFeedType(feedType);
		feed.setFeedVersion(feedVersion);

		// Create the JournalContentFeed object

		JournalContentFeed journalContentFeed = new JournalContentFeed(feed);

		// Set the CreationAttributes

		JournalContentFeed.CreationAttributes creationAttributes =
			journalContentFeed.new CreationAttributes(autoFeedId);

		journalContentFeed.setCreationAttributes(creationAttributes);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.createContentFeed(journalContentFeed);
		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}

		feed = journalContentFeed.getFeed();

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

		JournalContentFeed journalContentFeed = new JournalContentFeed(feed);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.deleteContentFeed(journalContentFeed);
		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}

	}

	public JournalFeed getFeed(long feedId)
		throws PortalException, SystemException {

		JournalFeed feed = new JournalFeedImpl();

		feed.setId(feedId);

		JournalContentFeed journalContentFeed = new JournalContentFeed(feed);

		OptionalJournalFeedCriteria criteria =
			new OptionalJournalFeedCriteria(
				OptionalJournalFeedCriteria.FIND_BY_PRIMARY_KEY);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			journalContentFeed =
				(JournalContentFeed) contentFeedService.getContentFeed(
					journalContentFeed, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return journalContentFeed.getFeed();
	}

	public JournalFeed getFeed(long groupId, String feedId)
		throws PortalException, SystemException {

		JournalFeed feed = new JournalFeedImpl();

		feed.setGroupId(groupId);
		feed.setFeedId(feedId);

		JournalContentFeed journalContentFeed = new JournalContentFeed(feed);

		OptionalJournalFeedCriteria criteria =
			new OptionalJournalFeedCriteria(
				OptionalJournalFeedCriteria.FIND_BY_G_F);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			journalContentFeed =
				(JournalContentFeed) contentFeedService.getContentFeed(
					journalContentFeed, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return journalContentFeed.getFeed();
	}

	public List<JournalFeed> getFeeds() throws SystemException {
		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_ALL);

		criteria.setSearchFieldValues(searchFields);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria);

			return _getFeedsFromContentFeeds(contentFeeds);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalFeed> getFeeds(long groupId) throws SystemException {
		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_BY_GROUP);
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria);

			return _getFeedsFromContentFeeds(contentFeeds);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalFeed> getFeeds(long groupId, int start, int end)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_BY_GROUP_WITH_LIMIT);
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_START, Long.toString(start));
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_END, Long.toString(end));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria);

			return _getFeedsFromContentFeeds(contentFeeds);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int getFeedsCount(long groupId) throws SystemException {
		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_BY_GROUP);
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalFeed> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.SEARCH_BY_GROUP_WITH_LIMIT);
		nameValues.put(
			OptionalJournalFeedCriteria.COMPANY_ID, Long.toString(companyId));
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalFeedCriteria.KEYWORDS, keywords);
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_START, Long.toString(start));
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_END, Long.toString(end));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria);

			return _getFeedsFromContentFeeds(contentFeeds);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public List<JournalFeed> search(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria
					.SEARCH_BY_GROUP_AND_FEED_WITH_LIMIT);
		nameValues.put(
			OptionalJournalFeedCriteria.COMPANY_ID, Long.toString(companyId));
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(OptionalJournalFeedCriteria.FEED_ID, feedId);
		nameValues.put(OptionalJournalFeedCriteria.NAME, name);
		nameValues.put(OptionalJournalFeedCriteria.DESCRIPTION, description);
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_START, Long.toString(start));
		nameValues.put(
			OptionalJournalFeedCriteria.RANGE_END, Long.toString(end));

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);
		criteria.setMatchAnyOneField(andOperator);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			List<ContentFeed> contentFeeds =
				contentFeedService.searchContentFeeds(criteria);
			return _getFeedsFromContentFeeds(contentFeeds);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_BY_GROUP_AND_KEYWORDS);
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(
			OptionalJournalFeedCriteria.COMPANY_ID, Long.toString(companyId));
		nameValues.put(OptionalJournalFeedCriteria.KEYWORDS, keywords);

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	public int searchCount(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();

		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> nameValues = new HashMap<String, String>();

		nameValues.put(
			OptionalJournalFeedCriteria.FINDER,
				OptionalJournalFeedCriteria.FIND_BY_GROUP_AND_FEED);
		nameValues.put(
			OptionalJournalFeedCriteria.GROUP_ID, Long.toString(groupId));
		nameValues.put(
			OptionalJournalFeedCriteria.COMPANY_ID, Long.toString(companyId));
		nameValues.put(OptionalJournalFeedCriteria.FEED_ID, feedId);
		nameValues.put(OptionalJournalFeedCriteria.NAME, name);
		nameValues.put(OptionalJournalFeedCriteria.DESCRIPTION, description);

		_addSearchFields(searchFields, nameValues);

		criteria.setSearchFieldValues(searchFields);
		criteria.setMatchAnyOneField(andOperator);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			return contentFeedService.getContentFeedSearchCount(criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
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

		JournalContentFeed journalContentFeed = new JournalContentFeed(feed);

		ContentFeedService contentFeedService =
			MirageServiceFactory.getContentFeedService();

		try {
			contentFeedService.updateContentFeed(journalContentFeed, null);
		}
		catch (CMSException cmse) {
			_throwException(cmse);
		}

		feed = journalContentFeed.getFeed();
		return feed;
	}

	private void _addSearchField(
		List<SearchFieldValue> fieldList, String fieldName, String fieldValue) {

		SearchFieldValue searchField = new SearchFieldValue();
		searchField.setFieldName(fieldName);
		searchField.setFieldValues(new String[] {fieldValue});

		fieldList.add(searchField);
	}

	private void _addSearchFields(
		List<SearchFieldValue> fieldList, Map<String, String> nameValues) {

		Iterator<String> iter = nameValues.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			String value = nameValues.get(name);

			_addSearchField(fieldList, name, value);
		}
	}

	private List<JournalFeed> _getFeedsFromContentFeeds(
		List<ContentFeed> contentFeeds) {

		List<JournalFeed> feeds =
			new ArrayList<JournalFeed>(contentFeeds.size());

		for (ContentFeed contentFeed : contentFeeds) {
			feeds.add(((JournalContentFeed) contentFeed).getFeed());
		}
		return feeds;
	}

	private void _throwException(CMSException ex)
		throws PortalException, SystemException {

		Throwable cause = ex.getCause();

		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException) cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException) cause;
			}
		}
	}

}