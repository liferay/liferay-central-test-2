/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.PermissionLocalServiceUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.NoSuchCategoryException;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.persistence.BlogsCategoryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.spring.BlogsEntryLocalService;
import com.liferay.portlet.blogs.util.Indexer;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.util.Html;
import com.liferay.util.RSSUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.lucene.Hits;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.impl.basic.ChannelBuilder;

/**
 * <a href="BlogsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Wilson S. Man
 *
 */
public class BlogsEntryLocalServiceImpl implements BlogsEntryLocalService {

	public BlogsEntry addEntry(
			String userId, String plid, String categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		categoryId = getCategoryId(user.getCompanyId(), categoryId);
		Date now = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		String entryId = Long.toString(CounterLocalServiceUtil.increment(
			BlogsEntry.class.getName()));

		BlogsEntry entry = BlogsEntryUtil.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setCategoryId(categoryId);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);

		BlogsEntryUtil.update(entry);

		// Resources

		addEntryResources(entry, communityPermissions, guestPermissions);

		// Lucene

		try {
			Indexer.addEntry(
				entry.getCompanyId(), entry.getGroupId(), userId, categoryId,
				entryId, title, content);
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		return entry;
	}

	public void addEntryResources(
			String entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getPrimaryKey().toString(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(String groupId)
		throws PortalException, SystemException {

		Iterator itr = BlogsEntryUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(String entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteEntry(entry.getCompanyId(), entry.getEntryId());
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		// Message boards

		MBMessageLocalServiceUtil.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			entry.getPrimaryKey().toString());

		// Entry

		BlogsEntryUtil.remove(entry.getEntryId());
	}

	public int getCategoriesEntriesCount(List categoryIds)
		throws SystemException {

		return BlogsEntryFinder.countByCategoryIds(categoryIds);
	}

	public List getEntries(String categoryId, int begin, int end)
		throws SystemException {

		return BlogsEntryUtil.findByCategoryId(categoryId, begin, end);
	}

	public int getEntriesCount(String categoryId) throws SystemException {
		return BlogsEntryUtil.countByCategoryId(categoryId);
	}

	public BlogsEntry getEntry(String entryId)
		throws PortalException, SystemException {

		return BlogsEntryUtil.findByPrimaryKey(entryId);
	}

	public List getGroupEntries(String groupId, int begin, int end)
		throws SystemException {

		return BlogsEntryUtil.findByGroupId(groupId, begin, end);
	}

	public int getGroupEntriesCount(String groupId) throws SystemException {
		return BlogsEntryUtil.countByGroupId(groupId);
	}

	public String getGroupEntriesRSS(
			String groupId, int begin, int end, double version, String url)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		String name = group.getName();

		if (group.isUser()) {
			User user = UserUtil.findByPrimaryKey(group.getClassPK());

			name = user.getFullName();
		}

		ChannelBuilder builder = new ChannelBuilder();

		ChannelIF channel = builder.createChannel(name);

		channel.setDescription(name);

		Iterator itr = BlogsEntryUtil.findByGroupId(
			groupId, begin, end).iterator();

		while (itr.hasNext()) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			try {
				String firstLine =
					StringUtil.shorten(
						Html.stripHtml(entry.getContent()), 80,
						StringPool.BLANK);

				builder.createItem(
					channel, entry.getTitle(), firstLine,
					new URL(url + "&entryId=" + entry.getEntryId()));
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return RSSUtil.export(channel, version);
	}

	public void reIndex(String[] ids) throws SystemException {
		try {
			String companyId = ids[0];

			Iterator itr = BlogsEntryUtil.findByCompanyId(companyId).iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				String groupId = entry.getGroupId();
				String userId = entry.getUserId();
				String categoryId = entry.getCategoryId();
				String entryId = entry.getEntryId();
				String title = entry.getTitle();
				String content = entry.getContent();

				try {
					Indexer.addEntry(
						companyId, groupId, userId, categoryId, entryId, title,
						content);
				}
				catch (Exception e1) {

					// Continue indexing even if one entry fails

					_log.error(e1.getMessage());
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public Hits search(
			String companyId, String groupId, String userId,
			String[] categoryIds, String keywords)
		throws SystemException {

		try {
			Hits hits = new Hits();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, Indexer.PORTLET_ID);

			if (Validator.isNotNull(groupId)) {
				LuceneUtil.addRequiredTerm(
					contextQuery, LuceneFields.GROUP_ID, groupId);
			}

			if (Validator.isNotNull(userId)) {
				LuceneUtil.addRequiredTerm(
					contextQuery, LuceneFields.USER_ID, userId);
			}

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				BooleanQuery categoryIdsQuery = new BooleanQuery();

				for (int i = 0; i < categoryIds.length; i++) {
					Term term = new Term("categoryId", categoryIds[i]);
					TermQuery termQuery = new TermQuery(term);

					categoryIdsQuery.add(termQuery, BooleanClause.Occur.SHOULD);
				}

				contextQuery.add(categoryIdsQuery, BooleanClause.Occur.MUST);
			}

			BooleanQuery searchQuery = new BooleanQuery();

			LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);
			fullQuery.add(searchQuery, BooleanClause.Occur.MUST);

			Searcher searcher = LuceneUtil.getSearcher(companyId);

			hits.recordHits(searcher.search(fullQuery));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	public BlogsEntry updateEntry(
			String userId, String entryId, String categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		categoryId = getCategoryId(user.getCompanyId(), categoryId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		entry.setModifiedDate(new Date());
		entry.setCategoryId(categoryId);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);

		BlogsEntryUtil.update(entry);

		// Lucene

		try {
			Indexer.updateEntry(
				entry.getCompanyId(), entry.getGroupId(), userId, categoryId,
				entryId, title, content);
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		return entry;
	}

	protected String getCategoryId(String companyId, String categoryId)
		throws PortalException, SystemException {

		if (!categoryId.equals(BlogsCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			// Ensure category exists and belongs to the proper company

			try {
				BlogsCategory category =
					BlogsCategoryUtil.findByPrimaryKey(categoryId);

				if (!companyId.equals(category.getCompanyId())) {
					categoryId = BlogsCategory.DEFAULT_PARENT_CATEGORY_ID;
				}
			}
			catch (NoSuchCategoryException nsfe) {
				categoryId = BlogsCategory.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return categoryId;
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
		LogFactory.getLog(BlogsEntryLocalServiceImpl.class);

}