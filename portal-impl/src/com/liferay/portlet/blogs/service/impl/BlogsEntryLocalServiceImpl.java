/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl;
import com.liferay.portlet.blogs.social.BlogsActivityKeys;
import com.liferay.portlet.blogs.util.BlogsUtil;
import com.liferay.portlet.blogs.util.Indexer;
import com.liferay.portlet.blogs.util.comparator.EntryDisplayDateComparator;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.StringReader;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * <a href="BlogsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Raymond Augé
 */
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	public BlogsEntry addEntry(
			long userId, String title, String content, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean allowTrackbacks, String[] trackbacks,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addEntry(
			null, userId, title, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			allowTrackbacks, trackbacks, serviceContext);
	}

	public BlogsEntry addEntry(
			String uuid, long userId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		Date now = new Date();

		validate(title, content);

		long entryId = counterLocalService.increment();

		BlogsEntry entry = blogsEntryPersistence.create(entryId);

		entry.setUuid(uuid);
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setTitle(title);
		entry.setUrlTitle(getUniqueUrlTitle(entryId, groupId, title));
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowTrackbacks(allowTrackbacks);
		entry.setStatus(serviceContext.getStatus());
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(now);

		blogsEntryPersistence.update(entry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Statistics

		if (serviceContext.getStatus() == StatusConstants.APPROVED) {
			blogsStatsUserLocalService.updateStatsUser(groupId, userId, now);
		}

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Message boards

		if (PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, entry.getUserName(), BlogsEntry.class.getName(),
				entryId, StatusConstants.APPROVED);
		}

		// Social

		if (serviceContext.getStatus() == StatusConstants.APPROVED) {
			socialActivityLocalService.addActivity(
				userId, groupId, BlogsEntry.class.getName(), entryId,
				BlogsActivityKeys.ADD_ENTRY, StringPool.BLANK, 0);
		}

		// Indexer

		reIndex(entry, false);

		// Ping

		if (serviceContext.getStatus() == StatusConstants.APPROVED) {
			pingGoogle(entry, serviceContext);

			if (allowTrackbacks) {
				pingTrackbacks(entry, trackbacks, false, serviceContext);
			}
		}

		return entry;
	}

	public void addEntryResources(
			BlogsEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (BlogsEntry entry : blogsEntryPersistence.findByGroupId(groupId)) {
			deleteEntry(entry);
		}
	}

	public void deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Indexer

		try {
			Indexer.deleteEntry(entry.getCompanyId(), entry.getEntryId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + entry.getEntryId(), se);
		}

		// Social

		socialActivityLocalService.deleteActivities(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Statistics

		blogsStatsUserLocalService.updateStatsUser(
			entry.getGroupId(), entry.getUserId());

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		blogsEntryPersistence.remove(entry);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int status, int start, int end)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByC_D(
				companyId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByC_D_S(
				companyId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getCompanyEntries(
			long companyId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByC_D(
				companyId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByC_D_S(
				companyId, new Date(), status, start, end, obc);
		}
	}

	public int getCompanyEntriesCount(long companyId, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.countByC_D(
				companyId, new Date());
		}
		else {
			return blogsEntryPersistence.countByC_D_S(
				companyId, new Date(), status);
		}
	}

	public BlogsEntry[] getEntriesPrevAndNext(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		return blogsEntryPersistence.findByGroupId_PrevAndNext(
			entry.getEntryId(), entry.getGroupId(),
			new EntryDisplayDateComparator(true));
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	public BlogsEntry getEntry(long groupId, String urlTitle)
		throws PortalException, SystemException {

		return blogsEntryPersistence.findByG_UT(groupId, urlTitle);
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByG_D(
				groupId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByG_D_S(
				groupId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getGroupEntries(
			long groupId, int status, int start, int end, OrderByComparator obc)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByG_D(
				groupId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByG_D_S(
				groupId, new Date(), status, start, end, obc);
		}
	}

	public int getGroupEntriesCount(long groupId, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.countByG_D(groupId, new Date());
		}
		else {
			return blogsEntryPersistence.countByG_D_S(
				groupId, new Date(), status);
		}
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int status, int start, int end)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByG_U_D(
				groupId, userId, new Date(), start, end);
		}
		else {
			return blogsEntryPersistence.findByG_U_D_S(
				groupId, userId, new Date(), status, start, end);
		}
	}

	public List<BlogsEntry> getGroupUserEntries(
			long groupId, long userId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.findByG_U_D(
				groupId, userId, new Date(), start, end, obc);
		}
		else {
			return blogsEntryPersistence.findByG_U_D_S(
				groupId, userId, new Date(), status, start, end, obc);
		}
	}

	public int getGroupUserEntriesCount(long groupId, long userId, int status)
		throws SystemException {

		if (status == StatusConstants.ANY) {
			return blogsEntryPersistence.countByG_U_D(
				groupId, userId, new Date());
		}
		else {
			return blogsEntryPersistence.countByG_U_D_S(
				groupId, userId, new Date(), status);
		}
	}

	public List<BlogsEntry> getNoAssetEntries() throws SystemException {
		return blogsEntryFinder.findByNoAssets();
	}

	public List<BlogsEntry> getOrganizationEntries(
			long organizationId, int status, int start, int end)
		throws SystemException {

		return blogsEntryFinder.findByOrganizationId(
			organizationId, new Date(), status, start, end);
	}

	public int getOrganizationEntriesCount(long organizationId, int status)
		throws SystemException {

		return blogsEntryFinder.countByOrganizationId(
			organizationId, new Date(), status);
	}

	public void reIndex(BlogsEntry entry, boolean update)
		throws SystemException {

		if (entry.getStatus() != StatusConstants.APPROVED) {
			return;
		}

		long companyId = entry.getCompanyId();
		long groupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = entry.getUserName();
		long entryId = entry.getEntryId();
		String title = entry.getTitle();
		String content = entry.getContent();
		Date displayDate = entry.getDisplayDate();

		String[] assetTagNames = assetTagLocalService.getTagNames(
			BlogsEntry.class.getName(), entryId);

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		try {
			if (update) {
				Indexer.updateEntry(
					companyId, groupId, userId, userName, entryId, title,
					content, displayDate, assetTagNames, expandoBridge);
			}
			else {
				Indexer.addEntry(
					companyId, groupId, userId, userName, entryId, title,
					content, displayDate, assetTagNames, expandoBridge);
			}
		}
		catch (SearchException se) {
			_log.error("Reindexing " + entryId, se);
		}
	}

	public void reIndex(long entryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		BlogsEntry entry = blogsEntryPersistence.fetchByPrimaryKey(entryId);

		if (entry == null) {
			return;
		}

		reIndex(entry, true);
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			reIndexEntries(companyId);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Hits search(
			long companyId, long groupId, long userId, long ownerUserId,
			String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if (ownerUserId > 0) {
				contextQuery.addRequiredTerm(Field.USER_ID, ownerUserId);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.USER_NAME, keywords);
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, BlogsEntry.class.getName(),
				fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void updateAsset(
			long userId, BlogsEntry entry, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException, SystemException {

		boolean visible = false;

		if (entry.getStatus() == StatusConstants.APPROVED) {
			visible = true;
		}

		assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), assetCategoryIds, assetTagNames, visible, null,
			null, entry.getDisplayDate(), null, ContentTypes.TEXT_HTML,
			entry.getTitle(), null, null, null, 0, 0, null, false);
	}

	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String content,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, boolean allowTrackbacks,
			String[] trackbacks, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		BlogsEntry entry = blogsEntryPersistence.findByPrimaryKey(entryId);

		String oldUrlTitle = entry.getUrlTitle();
		int oldStatus = entry.getStatus();

		entry.setModifiedDate(new Date());
		entry.setTitle(title);
		entry.setUrlTitle(
			getUniqueUrlTitle(entryId, entry.getGroupId(), title));
		entry.setContent(content);
		entry.setDisplayDate(displayDate);
		entry.setAllowTrackbacks(allowTrackbacks);

		blogsEntryPersistence.update(entry, false);

		// Resources

		if ((serviceContext.getCommunityPermissions() != null) ||
			(serviceContext.getGuestPermissions() != null)) {

			updateEntryResources(
				entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Status

		if (oldStatus != serviceContext.getStatus()) {
			boolean pingOldTrackbacks = false;

			if (oldUrlTitle != entry.getUrlTitle()) {
				pingOldTrackbacks = true;
			}

			entry = updateStatus(
				userId, entry, pingOldTrackbacks, trackbacks, serviceContext,
				false);
		}

		// Statistics

		if (serviceContext.getStatus() == StatusConstants.APPROVED) {
			blogsStatsUserLocalService.updateStatsUser(
				entry.getGroupId(), entry.getUserId(), displayDate);
		}

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Expando

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		// Indexer

		reIndex(entry, true);

		return entry;
	}

	public void updateEntryResources(
			BlogsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.updateResources(
			entry.getCompanyId(), entry.getGroupId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public BlogsEntry updateStatus(
			long userId, BlogsEntry entry, boolean pingOldTrackbaks,
			String[] trackbacks, ServiceContext serviceContext, boolean reIndex)
		throws PortalException, SystemException {

		int oldStatus = entry.getStatus();

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		entry.setStatus(serviceContext.getStatus());
		entry.setStatusByUserId(user.getUserId());
		entry.setStatusByUserName(user.getFullName());
		entry.setStatusDate(now);

		blogsEntryPersistence.update(entry, false);

		// Asset

		if ((serviceContext.getStatus() == StatusConstants.APPROVED) &&
			(oldStatus != StatusConstants.APPROVED)) {

			assetEntryLocalService.updateVisible(
				BlogsEntry.class.getName(), entry.getEntryId(), true);

			if (reIndex) {
				reIndex(entry, true);
			}
		}

		if ((serviceContext.getStatus() != StatusConstants.APPROVED) &&
			(oldStatus == StatusConstants.APPROVED)) {

			assetEntryLocalService.updateVisible(
				BlogsEntry.class.getName(), entry.getEntryId(), false);
		}

		// Social

		if ((serviceContext.getStatus() == StatusConstants.APPROVED) &&
			(oldStatus != StatusConstants.APPROVED)) {

			socialActivityLocalService.addActivity(
				userId, entry.getGroupId(), BlogsEntry.class.getName(),
				entry.getEntryId(), BlogsActivityKeys.ADD_ENTRY,
				StringPool.BLANK, 0);
		}

		// Ping

		if (serviceContext.getStatus() == StatusConstants.APPROVED) {
			pingGoogle(entry, serviceContext);

			if (entry.getAllowTrackbacks()) {
				pingTrackbacks(
					entry, trackbacks, pingOldTrackbaks, serviceContext);
			}
		}

		return entry;
	}

	public BlogsEntry updateStatus(
			long userId, long entryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BlogsEntry entry = getEntry(entryId);

		return updateStatus(
			userId, entry, false, null, serviceContext, true);
	}

	protected String getUniqueUrlTitle(
			long entryId, long groupId, String title)
		throws SystemException {

		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		String newUrlTitle = urlTitle;

		for (int i = 1;; i++) {
			BlogsEntry entry = blogsEntryPersistence.fetchByG_UT(
				groupId, newUrlTitle);

			if ((entry == null) || (entry.getEntryId() == entryId)) {
				break;
			}
			else {
				newUrlTitle = urlTitle + StringPool.DASH + i;
			}
		}

		return newUrlTitle;
	}

	protected void pingGoogle(BlogsEntry entry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!PropsValues.BLOGS_PING_GOOGLE_ENABLED) {
			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		Group group = groupPersistence.findByPrimaryKey(entry.getGroupId());

		StringBuilder sb = new StringBuilder();

		String name = group.getDescriptiveName();
		String url = layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs";
		String changesURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs/rss";

		sb.append("http://blogsearch.google.com/ping?name=");
		sb.append(HttpUtil.encodeURL(name));
		sb.append("&url=");
		sb.append(HttpUtil.encodeURL(url));
		sb.append("&changesURL=");
		sb.append(HttpUtil.encodeURL(changesURL));

		String location = sb.toString();

		if (_log.isInfoEnabled()) {
			_log.info("Pinging Google at " + location);
		}

		try {
			String response = HttpUtil.URLtoString(sb.toString());

			if (_log.isInfoEnabled()) {
				_log.info("Google ping response: " + response);
			}
		}
		catch (IOException ioe) {
			_log.error("Unable to ping Google at " + location, ioe);
		}
	}

	protected boolean pingTrackback(String trackback, Map<String, String> parts)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Pinging trackback " + trackback);
		}

		Http.Options options = new Http.Options();

		options.setLocation(trackback);
		options.setParts(parts);
		options.setPost(true);

		String xml = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug(xml);
		}

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		XMLStreamReader reader = inputFactory.createXMLStreamReader(
			new StringReader(xml));

		String error = xml;

		try {
			reader.nextTag();
			reader.nextTag();

			String name = reader.getLocalName();

			if (name.equals("error")) {
				int status = GetterUtil.getInteger(reader.getElementText(), 1);

				if (status == 0) {
					return true;
				}

				reader.nextTag();

				name = reader.getLocalName();

				if (name.equals("message")) {
					error = reader.getElementText();
				}
			}
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (Exception e) {
				}
			}
		}

		_log.error(
			"Error while pinging trackback at " + trackback + ": " + error);

		return false;
	}

	protected void pingTrackbacks(
			BlogsEntry entry, String[] trackbacks, boolean pingOldTrackbacks,
			ServiceContext serviceContext)
		throws SystemException {

		if (!PropsValues.BLOGS_TRACKBACK_ENABLED) {
			return;
		}

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (Validator.isNull(layoutFullURL)) {
			return;
		}

		Map<String, String> parts = new HashMap<String, String>();

		String excerpt = StringUtil.shorten(
			HtmlUtil.extractText(entry.getContent()),
			PropsValues.BLOGS_TRACKBACK_EXCERPT_LENGTH);
		String url =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "blogs/" +
				entry.getUrlTitle();

		parts.put("title", entry.getTitle());
		parts.put("excerpt", excerpt);
		parts.put("url", url);
		parts.put("blog_name", entry.getUserName());

		Set<String> trackbacksSet = null;

		if (Validator.isNotNull(trackbacks)) {
			trackbacksSet = SetUtil.fromArray(trackbacks);
		}
		else {
			trackbacksSet = new HashSet<String>();
		}

		if (pingOldTrackbacks) {
			trackbacksSet.addAll(
				SetUtil.fromArray(StringUtil.split(entry.getTrackbacks())));

			entry.setTrackbacks(StringPool.BLANK);

			blogsEntryPersistence.update(entry, false);
		}

		Set<String> oldTrackbacks = SetUtil.fromArray(
			StringUtil.split(entry.getTrackbacks()));

		Set<String> validTrackbacks = new HashSet<String>();

		for (String trackback : trackbacksSet) {
			if (oldTrackbacks.contains(trackback)) {
				continue;
			}

			try {
				if (pingTrackback(trackback, parts)) {
					validTrackbacks.add(trackback);
				}
			}
			catch (Exception e) {
				_log.error("Error while pinging trackback at " + trackback, e);
			}
		}

		if (!validTrackbacks.isEmpty()) {
			String newTrackbacks = StringUtil.merge(validTrackbacks);

			if (Validator.isNotNull(entry.getTrackbacks())) {
				newTrackbacks += StringPool.COMMA + entry.getTrackbacks();
			}

			entry.setTrackbacks(newTrackbacks);

			blogsEntryPersistence.update(entry, false);
		}
	}

	protected void reIndexEntries(long companyId) throws SystemException {
		try {
			Indexer.deleteEntries(companyId);
		}
		catch (SearchException se) {
			_log.error("Deleting indexes " + companyId, se);
		}

		int count = blogsEntryPersistence.countByCompanyId(companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reIndexEntries(companyId, start, end);
		}
	}

	protected void reIndexEntries(long companyId, int start, int end)
		throws SystemException {

		List<BlogsEntry> entries = blogsEntryPersistence.findByCompanyId(
			companyId, start, end);

		for (BlogsEntry entry : entries) {
			reIndex(entry, false);
		}
	}

	protected void validate(String title, String content)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new EntryContentException();
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(BlogsEntryLocalServiceImpl.class);

}