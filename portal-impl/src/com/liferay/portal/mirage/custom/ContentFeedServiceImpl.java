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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.custom;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.model.JournalContentFeed;
import com.liferay.portal.mirage.model.OptionalJournalFeedCriteria;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.DuplicateFeedIdException;
import com.liferay.portlet.journal.FeedContentFieldException;
import com.liferay.portlet.journal.FeedDescriptionException;
import com.liferay.portlet.journal.FeedIdException;
import com.liferay.portlet.journal.FeedNameException;
import com.liferay.portlet.journal.FeedTargetLayoutFriendlyUrlException;
import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.service.persistence.JournalFeedFinder;
import com.liferay.portlet.journal.service.persistence.JournalFeedFinderUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedPersistence;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructurePersistence;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.util.RSSUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.ContentFeed;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.UpdateCriteria;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * <a href="ContentFeedServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public class ContentFeedServiceImpl implements ContentFeedService {

	public void createContentFeed(ContentFeed contentFeed)
		throws CMSException {

		JournalContentFeed journalContentFeed =
			(JournalContentFeed) contentFeed;
		JournalFeed feed = journalContentFeed.getFeed();

		try {

			// Get the attributes

			long userId = feed.getUserId();
			String feedId = feed.getFeedId();
			long groupId = feed.getGroupId();
			String name = feed.getName();
			String description = feed.getDescription();
			String structureId = feed.getStructureId();
			String targetLayoutFriendlyUrl = feed.getTargetLayoutFriendlyUrl();
			String contentField = feed.getContentField();
			String uuid = feed.getUuid();
			String type = feed.getType();
			String templateId = feed.getTemplateId();
			String rendererTemplateId = feed.getRendererTemplateId();
			int delta = feed.getDelta();
			String orderByCol = feed.getOrderByCol();
			String orderByType = feed.getOrderByType();
			String targetPortletId = feed.getTargetPortletId();
			String feedType = feed.getFeedType();
			double feedVersion = feed.getFeedVersion();

			// Feed

			User user = userPersistence.findByPrimaryKey(userId);
			feedId = feedId.trim().toUpperCase();
			Date now = new Date();

			boolean autoFeedId =
				journalContentFeed.getCreationAttributes().isAutoCreateId();

			validate(
				user.getCompanyId(), groupId, feedId, autoFeedId,
					name, description, structureId, targetLayoutFriendlyUrl,
						contentField);

			if (autoFeedId) {
				feedId = String.valueOf(counterLocalService.increment());
			}

			long id = counterLocalService.increment();

			JournalFeed newFeed = journalFeedPersistence.create(id);

			newFeed.setUuid(uuid);
			newFeed.setGroupId(groupId);
			newFeed.setCompanyId(user.getCompanyId());
			newFeed.setUserId(user.getUserId());
			newFeed.setUserName(user.getFullName());
			newFeed.setCreateDate(now);
			newFeed.setModifiedDate(now);
			newFeed.setFeedId(feedId);
			newFeed.setName(name);
			newFeed.setDescription(description);
			newFeed.setType(type);
			newFeed.setStructureId(structureId);
			newFeed.setTemplateId(templateId);
			newFeed.setRendererTemplateId(rendererTemplateId);
			newFeed.setDelta(delta);
			newFeed.setOrderByCol(orderByCol);
			newFeed.setOrderByType(orderByType);
			newFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
			newFeed.setTargetPortletId(targetPortletId);
			newFeed.setContentField(contentField);

			if (Validator.isNull(feedType)) {
				newFeed.setFeedType(RSSUtil.DEFAULT_TYPE);
				newFeed.setFeedVersion(RSSUtil.DEFAULT_VERSION);
			}
			else {
				newFeed.setFeedType(feedType);
				newFeed.setFeedVersion(feedVersion);
			}

			journalFeedPersistence.update(newFeed, false);
			journalContentFeed.setFeed(newFeed);
		}

		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
			throw new CMSException(pe.getMessage(), pe);
		}		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteContentFeed(ContentFeed contentFeed)
		throws CMSException {

		JournalContentFeed journalContentFeed =
			(JournalContentFeed) contentFeed;
		JournalFeed feed = journalContentFeed.getFeed();
		try {
			journalFeedPersistence.remove(feed.getPrimaryKey());
		}

		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
			throw new CMSException(pe.getMessage(), pe);
		}		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
	}

	public ContentFeed getContentFeed(
			ContentFeed contentFeed, OptionalCriteria optionalCriteria)
		throws CMSException {

		JournalContentFeed journalContentFeed =
			(JournalContentFeed) contentFeed;

		JournalFeed feed = journalContentFeed.getFeed();

		OptionalJournalFeedCriteria criteria =
			(OptionalJournalFeedCriteria) optionalCriteria;

		String finder = criteria.getOptions().get(
							OptionalJournalFeedCriteria.FINDER);

		if (finder == null) {
			return null;
		}
		try {
			if (finder.equals(
					OptionalJournalFeedCriteria.FIND_BY_PRIMARY_KEY)) {

				long feedId = feed.getId();
				feed = _getJournalContentFeed(feedId);

			}
			else if (finder.equals(OptionalJournalFeedCriteria.FIND_BY_G_F)) {

				long groupId = feed.getGroupId();
				String feedId = feed.getFeedId();
				feed = _getJournalContentFeed(groupId, feedId);
			}
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		journalContentFeed.setFeed(feed);
		return journalContentFeed;
	}

	public int getContentFeedSearchCount(SearchCriteria searchCriteria)
		throws CMSException {

		int result = 0;
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		try {
			String finder =	_findFieldValue(
								searchFields,
									OptionalJournalFeedCriteria.FINDER);

			if (OptionalJournalFeedCriteria.FIND_BY_GROUP.equals(finder)) {

				long groupId =
					Long.parseLong(
						_findFieldValue(
							searchFields,
								OptionalJournalFeedCriteria.GROUP_ID));
				result = _getFeedsCount(groupId);

			}
			else if (OptionalJournalFeedCriteria.FIND_BY_GROUP_AND_KEYWORDS.
						equals(finder)) {
				long groupId =
					Long.parseLong(_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.GROUP_ID));
				long companyId =
					Long.parseLong(_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.COMPANY_ID));
				String keywords =
					_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.KEYWORDS);
				result = _getFeedsCount(companyId, groupId, keywords);
			}
			else if (OptionalJournalFeedCriteria.FIND_BY_GROUP_AND_FEED.
						equals(finder)) {
				long groupId =
					Long.parseLong(_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.GROUP_ID));
				long companyId =
					Long.parseLong(_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.COMPANY_ID));
				String feedId =
					_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.FEED_ID);
				String name =
					_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.NAME);
				String description =
					_findFieldValue(
						searchFields, OptionalJournalFeedCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				result = _getFeedsCount(
							companyId, groupId, feedId, name, description,
								andOperator);
			}
		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
		return result;
	}

	public List<ContentFeed> searchContentFeeds(SearchCriteria searchCriteria)
		throws CMSException {

		List<ContentFeed> contentFeeds = new ArrayList<ContentFeed>();

		try {
			List<JournalFeed> feeds = null;
			String finder = _findFieldValue(
								searchCriteria.getSearchFieldValues(),
									OptionalJournalFeedCriteria.FINDER);

			if (OptionalJournalFeedCriteria.FIND_ALL.equals(finder)) {
				feeds = _getJournalFeeds();
			}
			else if (OptionalJournalFeedCriteria.FIND_BY_GROUP.equals(finder)) {
				String groupIdStr =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdStr);
				feeds = _getJournalFeedsByGroupId(groupId);
			}
			else if (OptionalJournalFeedCriteria.FIND_BY_GROUP_WITH_LIMIT.
						equals(finder)) {
				long groupId =
					Long.parseLong(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.GROUP_ID));
				int start =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_START));
				int end =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_END));
				feeds = _getJournalFeedsByGroupIdWithinRange(
							groupId, start, end);
			}
			else if (OptionalJournalFeedCriteria.SEARCH_BY_GROUP_WITH_LIMIT.
						equals(finder)) {
				long companyId =
					Long.parseLong(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.COMPANY_ID));
				long groupId =
					Long.parseLong(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.GROUP_ID));
				String keywords =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.KEYWORDS);
				int start =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_START));
				int end =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_END));
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				feeds =
					_searchJournalFeedsByGroupIdWithinRange(
						companyId, groupId, keywords, start, end, obc);
			}
			else if (OptionalJournalFeedCriteria.SEARCH_BY_GROUP_WITH_LIMIT.
						equals(finder)) {
				long companyId =
					Long.parseLong(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.COMPANY_ID));
				long groupId =
					Long.parseLong(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.GROUP_ID));
				String feedId =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.FEED_ID);
				String name =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.NAME);
				String description =
					_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.DESCRIPTION);
				int start =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_START));
				int end =
					Integer.parseInt(_findFieldValue(
						searchCriteria.getSearchFieldValues(),
						OptionalJournalFeedCriteria.RANGE_END));
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				feeds =
					_searchJournalFeedsByGroupIdAndFeedIdWithinRange(
						companyId, groupId, feedId, name, description,
						andOperator, start, end, obc);
			}
			_populateContentFeeds(contentFeeds, feeds);
		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return contentFeeds;
	}

	public void updateContentFeed(
			ContentFeed contentFeed, UpdateCriteria updateCriteria)
		throws CMSException {

		JournalContentFeed journalContentFeed =
			(JournalContentFeed) contentFeed;

		JournalFeed journalFeed = journalContentFeed.getFeed();
		long groupId = journalFeed.getGroupId();
		String feedId = journalFeed.getFeedId();
		String name = journalFeed.getName();
		String description = journalFeed.getDescription();
		String structureId = journalFeed.getStructureId();
		String targetLayoutFriendlyUrl =
			journalFeed.getTargetLayoutFriendlyUrl();

		String contentField = journalFeed.getContentField();
		String type = journalFeed.getType();
		String templateId = journalFeed.getTemplateId();
		String rendererTemplateId = journalFeed.getRendererTemplateId();
		int delta = journalFeed.getDelta();
		String orderByCol = journalFeed.getOrderByCol();
		String orderByType = journalFeed.getOrderByType();
		String targetPortletId = journalFeed.getTargetPortletId();

		String feedType = journalFeed.getFeedType();
		double feedVersion = journalFeed.getFeedVersion();
		try {

			// Feed

			JournalFeed feed =
				journalFeedPersistence.findByG_F(groupId, feedId);

			validate(
				feed.getCompanyId(), groupId, name, description, structureId,
					targetLayoutFriendlyUrl, contentField);

			feed.setModifiedDate(new Date());
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

			if (Validator.isNull(feedType)) {
				feed.setFeedType(RSSUtil.DEFAULT_TYPE);
				feed.setFeedVersion(RSSUtil.DEFAULT_VERSION);
			}
			else {
				feed.setFeedType(feedType);
				feed.setFeedVersion(feedVersion);
			}

			journalFeedPersistence.update(feed, false);
			journalContentFeed.setFeed(feed);
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
	}

	protected boolean isValidStructureField(
		long groupId, String structureId, String contentField) {

		if (contentField.equals(JournalFeedImpl.ARTICLE_DESCRIPTION) ||
			contentField.equals(JournalFeedImpl.RENDERED_ARTICLE)) {

			return true;
		}
		else {
			try {
				JournalStructure structure =
					journalStructurePersistence.findByG_S(groupId, structureId);

				Document doc =
					DocumentUtil.readDocumentFromXML(structure.getXsd());

				XPath xpathSelector =
					DocumentHelper.createXPath("//dynamic-element[@name='" +
						contentField + "']");

				Element el = (Element) xpathSelector.selectSingleNode(doc);

				if (el != null) {
					return true;
				}
			}
			catch (Exception e) {
			}
		}
		return false;
	}

	protected void validate(
			long companyId, long groupId, String feedId, boolean autoFeedId,
			String name, String description, String structureId,
			String targetLayoutFriendlyUrl, String contentField)
		throws PortalException, SystemException {

		if (!autoFeedId) {
			if ((Validator.isNull(feedId)) || (Validator.isNumber(feedId)) ||
				(feedId.indexOf(StringPool.SPACE) != -1)) {

				throw new FeedIdException();
			}

			try {
				journalFeedPersistence.findByG_F(groupId, feedId);

				throw new DuplicateFeedIdException();
			}
			catch (NoSuchFeedException nsfe) {
			}
		}

		validate(
			companyId, groupId, name, description, structureId,
				targetLayoutFriendlyUrl, contentField);
	}

	protected void validate(
			long companyId, long groupId, String name, String description,
			String structureId, String targetLayoutFriendlyUrl,
			String contentField)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new FeedNameException();
		}

		if (Validator.isNull(description)) {
			throw new FeedDescriptionException();
		}

		long plid = PortalUtil.getPlidFromFriendlyURL(
						companyId, targetLayoutFriendlyUrl);

		if (plid <= 0) {
			throw new FeedTargetLayoutFriendlyUrlException();
		}

		if (!isValidStructureField(groupId, structureId, contentField)) {
			throw new FeedContentFieldException();
		}
	}

	private String _findFieldValue(
		List<SearchFieldValue> fieldValues, String fieldName) {

		String result = null;
		for (SearchFieldValue searchField : fieldValues) {
			if (searchField.getFieldName().equals(fieldName)) {
				result = searchField.getFieldValue();
				break;
			}
		}
		return result;
	}

	private int _getFeedsCount(long groupId)
		throws SystemException {

		return journalFeedPersistence.countByGroupId(groupId);
	}

	private int _getFeedsCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return journalFeedFinder.countByKeywords(companyId, groupId, keywords);
	}

	private int _getFeedsCount(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator)
		throws SystemException {

		return journalFeedFinder.countByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator);
	}

	private JournalFeed _getJournalContentFeed(long feedId)
		throws SystemException, NoSuchFeedException {

		return journalFeedPersistence.findByPrimaryKey(feedId);
	}

	private JournalFeed _getJournalContentFeed(long groupId, String feedId)
		throws SystemException, NoSuchFeedException {

		return journalFeedPersistence.findByG_F(groupId, feedId);
	}

	private List<JournalFeed> _getJournalFeeds()
		throws SystemException {

		return journalFeedPersistence.findAll();
	}

	private List<JournalFeed> _getJournalFeedsByGroupId(long groupId)
		throws SystemException {

		return journalFeedPersistence.findByGroupId(groupId);
	}

	private List<JournalFeed> _getJournalFeedsByGroupIdWithinRange(
			long groupId, int start, int end)
		throws SystemException {

		return journalFeedPersistence.findByGroupId(groupId, start, end);
	}

	private void _populateContentFeeds(
		List<ContentFeed> contentFeeds, List<JournalFeed> feeds) {

		if (feeds == null) {
			return;
		}

		for (JournalFeed feed : feeds) {
			contentFeeds.add(new JournalContentFeed(feed));
		}

	}

	private List<JournalFeed> _searchJournalFeedsByGroupIdAndFeedIdWithinRange(
			long companyId, long groupId, String feedId, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalFeedFinder.findByC_G_F_N_D(
			companyId, groupId, feedId, name, description, andOperator, start,
			end, obc);
	}

	private List<JournalFeed> _searchJournalFeedsByGroupIdWithinRange(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return journalFeedFinder.findByKeywords(
			companyId, groupId, keywords, start, end, obc);
	}

	protected CounterLocalService counterLocalService =
		CounterLocalServiceFactory.getImpl();
	protected JournalFeedFinder journalFeedFinder =
		JournalFeedFinderUtil.getFinder();
	protected JournalFeedPersistence journalFeedPersistence =
		JournalFeedUtil.getPersistence();
	protected JournalStructurePersistence journalStructurePersistence =
		JournalStructureUtil.getPersistence();
	protected UserPersistence userPersistence = UserUtil.getPersistence();

	private static final Log _log =
		LogFactory.getLog(ContentFeedServiceImpl.class);

}