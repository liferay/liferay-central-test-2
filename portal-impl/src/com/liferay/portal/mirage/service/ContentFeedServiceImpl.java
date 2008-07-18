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

package com.liferay.portal.mirage.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.model.MirageFeed;
import com.liferay.portal.mirage.model.MirageFeedCriteria;
import com.liferay.portal.mirage.util.SmartFields;
import com.liferay.portal.model.User;
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
import com.liferay.portlet.journal.service.persistence.JournalFeedFinderUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.util.RSSUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.ContentFeed;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.UpdateCriteria;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * <a href="ContentFeedServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Brian Wing Shun Chan
 *
 */
public class ContentFeedServiceImpl implements ContentFeedService {

	public void createContentFeed(ContentFeed contentFeed) throws CMSException {
		try {
			MirageFeed mirageFeed = (MirageFeed)contentFeed;

			JournalFeed feed = mirageFeed.getFeed();

			String uuid = feed.getUuid();
			long userId = feed.getUserId();
			long groupId = feed.getGroupId();
			String feedId = feed.getFeedId();
			boolean autoFeedId = mirageFeed.isAutoFeedId();
			String name = feed.getName();
			String description = feed.getDescription();
			String type = feed.getType();
			String structureId = feed.getStructureId();
			String templateId = feed.getTemplateId();
			String rendererTemplateId = feed.getRendererTemplateId();
			int delta = feed.getDelta();
			String orderByCol = feed.getOrderByCol();
			String orderByType = feed.getOrderByType();
			String targetLayoutFriendlyUrl = feed.getTargetLayoutFriendlyUrl();
			String targetPortletId = feed.getTargetPortletId();
			String contentField = feed.getContentField();
			String feedType = feed.getFeedType();
			double feedVersion = feed.getFeedVersion();

			// Feed

			User user = UserUtil.findByPrimaryKey(userId);
			feedId = feedId.trim().toUpperCase();
			Date now = new Date();

			validate(
				user.getCompanyId(), groupId, feedId, autoFeedId, name,
				description, structureId, targetLayoutFriendlyUrl,
				contentField);

			if (autoFeedId) {
				feedId = String.valueOf(CounterLocalServiceUtil.increment());
			}

			long id = CounterLocalServiceUtil.increment();

			feed = JournalFeedUtil.create(id);

			feed.setUuid(uuid);
			feed.setGroupId(groupId);
			feed.setCompanyId(user.getCompanyId());
			feed.setUserId(user.getUserId());
			feed.setUserName(user.getFullName());
			feed.setCreateDate(now);
			feed.setModifiedDate(now);
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

			if (Validator.isNull(feedType)) {
				feed.setFeedType(RSSUtil.DEFAULT_TYPE);
				feed.setFeedVersion(RSSUtil.DEFAULT_VERSION);
			}
			else {
				feed.setFeedType(feedType);
				feed.setFeedVersion(feedVersion);
			}

			JournalFeedUtil.update(feed, false);

			mirageFeed.setFeed(feed);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void deleteContentFeed(ContentFeed contentFeed) throws CMSException {
		try {
			MirageFeed mirageFeed = (MirageFeed)contentFeed;

			JournalFeed feed = mirageFeed.getFeed();

			JournalFeedUtil.remove(feed.getPrimaryKey());
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public ContentFeed getContentFeed(
			ContentFeed contentFeed, OptionalCriteria optionalCriteria)
		throws CMSException {

		try {
			MirageFeed mirageFeed = (MirageFeed)contentFeed;

			JournalFeed feed = mirageFeed.getFeed();

			MirageFeedCriteria criteria = (MirageFeedCriteria)optionalCriteria;

			String query = criteria.getOptions().get(MirageFeedCriteria.QUERY);

			if (query.equals(MirageFeedCriteria.FIND_BY_PRIMARY_KEY)) {
				long feedId = feed.getId();

				feed = JournalFeedUtil.findByPrimaryKey(feedId);
			}
			else if (query.equals(MirageFeedCriteria.FIND_BY_G_F)) {
				long groupId = feed.getGroupId();
				String feedId = feed.getFeedId();

				feed = JournalFeedUtil.findByG_F(groupId, feedId);
			}

			mirageFeed.setFeed(feed);

			return mirageFeed;
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public int getContentFeedSearchCount(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			int result = 0;

			SmartFields smartFields = new SmartFields(
				searchCriteria.getSearchFieldValues());

			String query =	smartFields.getString(MirageFeedCriteria.QUERY);

			if (query.equals(MirageFeedCriteria.COUNT_BY_GROUP_ID)) {
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);

				result = JournalFeedUtil.countByGroupId(groupId);
			}
			else if (query.equals(MirageFeedCriteria.COUNT_BY_KEYWORDS)) {
				long companyId = smartFields.getLong(
					MirageFeedCriteria.COMPANY_ID);
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);
				String keywords = smartFields.getString(
					MirageFeedCriteria.KEYWORDS);

				result = JournalFeedFinderUtil.countByKeywords(
					companyId, groupId, keywords);
			}
			else if (query.equals(MirageFeedCriteria.COUNT_BY_C_G_F_N_D)) {
				long companyId = smartFields.getLong(
					MirageFeedCriteria.COMPANY_ID);
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);
				String feedId = smartFields.getString(
					MirageFeedCriteria.FEED_ID);
				String name = smartFields.getString(MirageFeedCriteria.NAME);
				String description = smartFields.getString(
					MirageFeedCriteria.DESCRIPTION);
				boolean andOperator = searchCriteria.isMatchAnyOneField();

				result = JournalFeedFinderUtil.countByC_G_F_N_D(
					companyId, groupId, feedId, name, description, andOperator);
			}

			return result;
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public List<ContentFeed> searchContentFeeds(SearchCriteria searchCriteria)
		throws CMSException {

		try {
			List<JournalFeed> feeds = null;

			SmartFields smartFields = new SmartFields(
				searchCriteria.getSearchFieldValues());

			String query =	smartFields.getString(MirageFeedCriteria.QUERY);

			if (query.equals(MirageFeedCriteria.FIND_ALL)) {
				feeds = JournalFeedUtil.findAll();
			}
			else if (query.equals(MirageFeedCriteria.FIND_BY_GROUP_ID)) {
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);
				int start = smartFields.getInteger(MirageFeedCriteria.START);
				int end = smartFields.getInteger(MirageFeedCriteria.END);

				feeds = JournalFeedUtil.findByGroupId(groupId, start, end);
			}
			else if (query.equals(MirageFeedCriteria.FIND_BY_KEYWORDS)) {
				long companyId = smartFields.getLong(
					MirageFeedCriteria.COMPANY_ID);
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);
				String keywords = smartFields.getString(
					MirageFeedCriteria.KEYWORDS);
				int start = smartFields.getInteger(MirageFeedCriteria.START);
				int end = smartFields.getInteger(MirageFeedCriteria.END);
				OrderByComparator obc =
					(OrderByComparator)searchCriteria.getOrderByComparator();

				feeds = JournalFeedFinderUtil.findByKeywords(
					companyId, groupId, keywords, start, end, obc);
			}
			else if (query.equals(MirageFeedCriteria.FIND_BY_C_G_F_N_D)) {
				long companyId = smartFields.getLong(
					MirageFeedCriteria.COMPANY_ID);
				long groupId = smartFields.getLong(MirageFeedCriteria.GROUP_ID);
				String feedId = smartFields.getString(
					MirageFeedCriteria.FEED_ID);
				String name = smartFields.getString(MirageFeedCriteria.NAME);
				String description = smartFields.getString(
					MirageFeedCriteria.DESCRIPTION);
				int start = smartFields.getInteger(MirageFeedCriteria.START);
				int end = smartFields.getInteger(MirageFeedCriteria.END);
				boolean andOperator = searchCriteria.isMatchAnyOneField();
				OrderByComparator obc =
					(OrderByComparator)searchCriteria.getOrderByComparator();

				feeds = JournalFeedFinderUtil.findByC_G_F_N_D(
					companyId, groupId, feedId, name, description, andOperator,
					start, end, obc);
			}

			if (feeds == null) {
				return new ArrayList<ContentFeed>();
			}

			List<ContentFeed> contentFeeds = new ArrayList<ContentFeed>(
				feeds.size());

			for (JournalFeed feed : feeds) {
				contentFeeds.add(new MirageFeed(feed));
			}

			return contentFeeds;
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateContentFeed(
			ContentFeed contentFeed, UpdateCriteria updateCriteria)
		throws CMSException {

		try {
			MirageFeed mirageFeed = (MirageFeed)contentFeed;

			JournalFeed feed = mirageFeed.getFeed();

			long groupId = feed.getGroupId();
			String feedId = feed.getFeedId();
			String name = feed.getName();
			String description = feed.getDescription();
			String type = feed.getType();
			String structureId = feed.getStructureId();
			String templateId = feed.getTemplateId();
			String rendererTemplateId = feed.getRendererTemplateId();
			int delta = feed.getDelta();
			String orderByCol = feed.getOrderByCol();
			String orderByType = feed.getOrderByType();
			String targetLayoutFriendlyUrl = feed.getTargetLayoutFriendlyUrl();
			String targetPortletId = feed.getTargetPortletId();
			String contentField = feed.getContentField();
			String feedType = feed.getFeedType();
			double feedVersion = feed.getFeedVersion();

			// Feed

			feed = JournalFeedUtil.findByG_F(groupId, feedId);

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

			JournalFeedUtil.update(feed, false);

			mirageFeed.setFeed(feed);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
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
				JournalStructure structure = JournalStructureUtil.findByG_S(
					groupId, structureId);

				Document doc = DocumentUtil.readDocumentFromXML(
					structure.getXsd());

				XPath xpathSelector = DocumentHelper.createXPath(
					"//dynamic-element[@name='"+ contentField + "']");

				Element el = (Element)xpathSelector.selectSingleNode(doc);

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
				JournalFeedUtil.findByG_F(groupId, feedId);

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

}