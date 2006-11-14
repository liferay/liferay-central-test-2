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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.SubscriptionLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.spring.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.portlet.messageboards.util.IndexerImpl;
import com.liferay.util.Validator;
import com.liferay.util.lucene.Hits;

import java.io.IOException;

import java.util.ArrayList;
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

/**
 * <a href="MBCategoryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBCategoryLocalServiceImpl implements MBCategoryLocalService {

	public MBCategory addCategory(
			String userId, String plid, String parentCategoryId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			userId, plid, parentCategoryId, name, description,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public MBCategory addCategory(
			String userId, String plid, String parentCategoryId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			userId, plid, parentCategoryId, name, description, null, null,
			communityPermissions, guestPermissions);
	}

	public MBCategory addCategory(
			String userId, String plid, String parentCategoryId, String name,
			String description, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Category

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		String categoryId = Long.toString(CounterLocalServiceUtil.increment(
			MBCategory.class.getName()));

		MBCategory category = MBCategoryUtil.create(categoryId);

		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		MBCategoryUtil.update(category);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addCategoryResources(
				category, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addCategoryResources(
				category, communityPermissions, guestPermissions);
		}

		return category;
	}

	public void addCategoryResources(
			String categoryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
			MBCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getPrimaryKey().toString(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
			String categoryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		addCategoryResources(category, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
			MBCategory category, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getPrimaryKey().toString(), communityPermissions,
			guestPermissions);
	}

	public void deleteCategories(String groupId)
		throws PortalException, SystemException {

		Iterator itr = MBCategoryUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			MBCategory category = (MBCategory)itr.next();

			deleteCategory(category);
		}
	}

	public void deleteCategory(String categoryId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		deleteCategory(category);
	}

	public void deleteCategory(MBCategory category)
		throws PortalException, SystemException {

		// Categories

		Iterator itr = MBCategoryUtil.findByG_P(
			category.getGroupId(), category.getCategoryId()).iterator();

		while (itr.hasNext()) {
			MBCategory curCategory = (MBCategory)itr.next();

			deleteCategory(curCategory);
		}

		// Lucene

		try {
			Indexer.deleteMessages(
				category.getCompanyId(), category.getCategoryId());
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
		catch (ParseException pe) {
			_log.error(pe.getMessage());
		}

		// Threads

		MBThreadLocalServiceUtil.deleteThreads(category.getCategoryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			category.getPrimaryKey().toString());

		// Category

		MBCategoryUtil.remove(category.getCategoryId());
	}

	public List getCategories(
			String groupId, String parentCategoryId, int begin, int end)
		throws SystemException {

		return MBCategoryUtil.findByG_P(groupId, parentCategoryId, begin, end);
	}

	public int getCategoriesCount(String groupId) throws SystemException {
		return MBCategoryUtil.countByGroupId(groupId);
	}

	public int getCategoriesCount(String groupId, String parentCategoryId)
		throws SystemException {

		return MBCategoryUtil.countByG_P(groupId, parentCategoryId);
	}

	public MBCategory getCategory(String categoryId)
		throws PortalException, SystemException {

		return MBCategoryUtil.findByPrimaryKey(categoryId);
	}

	public void getSubcategoryIds(
			List categoryIds, String groupId, String categoryId)
		throws SystemException {

		Iterator itr = MBCategoryUtil.findByG_P(groupId, categoryId).iterator();

		while (itr.hasNext()) {
			MBCategory category = (MBCategory)itr.next();

			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}
	}

	public MBCategory getSystemCategory()
		throws PortalException, SystemException {

		String categoryId = Company.SYSTEM;

		MBCategory category = MBCategoryUtil.fetchByPrimaryKey(categoryId);

		if (category == null) {
			category = MBCategoryUtil.create(categoryId);

			category.setCompanyId(categoryId);
			category.setUserId(categoryId);

			MBCategoryUtil.update(category);
		}

		return category;
	}

	public void reIndex(String[] ids) throws SystemException {
		try {
			String companyId = ids[0];

			Iterator itr1 = MBCategoryUtil.findByCompanyId(
				companyId).iterator();

			while (itr1.hasNext()) {
				MBCategory category = (MBCategory)itr1.next();

				String categoryId = category.getCategoryId();

				Iterator itr2 = MBMessageUtil.findByCategoryId(
					categoryId).iterator();

				while (itr2.hasNext()) {
					MBMessage message = (MBMessage)itr2.next();

					String groupId = category.getGroupId();
					String threadId = message.getThreadId();
					String messageId = message.getMessageId();
					String title = message.getSubject();
					String content = message.getBody();

					try {
						IndexerImpl.addMessage(
							companyId, groupId, categoryId, threadId, messageId,
							title, content);
					}
					catch (Exception e1) {

						// Continue indexing even if one message fails

						_log.error(e1.getMessage());
					}
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
   			String companyId, String groupId, String[] categoryIds,
			String threadId, String keywords)
		throws SystemException {

		try {
			Hits hits = new Hits();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, IndexerImpl.PORTLET_ID);
			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.GROUP_ID, groupId);

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				BooleanQuery categoryIdsQuery = new BooleanQuery();

				for (int i = 0; i < categoryIds.length; i++) {
					Term term = new Term("categoryId", categoryIds[i]);
					TermQuery termQuery = new TermQuery(term);

					categoryIdsQuery.add(termQuery, BooleanClause.Occur.SHOULD);
				}

				contextQuery.add(categoryIdsQuery, BooleanClause.Occur.MUST);
			}

			if (Validator.isNotNull(threadId)) {
				LuceneUtil.addTerm(contextQuery, "threadId", threadId);
			}

			BooleanQuery searchQuery = new BooleanQuery();

			LuceneUtil.addTerm(searchQuery, LuceneFields.TITLE, keywords);
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
			//throw new SystemException(pe);

			_log.error("Error parsing keywords " + keywords);

			return new Hits();
		}
	}

	public MBCategory updateCategory(
			String categoryId, String parentCategoryId, String name,
			String description, boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		// Category

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		String oldCategoryId = category.getParentCategoryId();
		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		MBCategoryUtil.update(category);

		// Merge categories

		if (mergeWithParentCategory &&
			!oldCategoryId.equals(parentCategoryId) &&
			!parentCategoryId.equals(MBCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);
		}

		return category;
	}

	protected String getParentCategoryId(
			String groupId, String parentCategoryId)
		throws SystemException {

		if (!parentCategoryId.equals(MBCategory.DEFAULT_PARENT_CATEGORY_ID)) {
			MBCategory parentCategory =
				MBCategoryUtil.fetchByPrimaryKey(parentCategoryId);

			if ((parentCategory == null) ||
				(!groupId.equals(parentCategory.getGroupId()))) {

				parentCategoryId = MBCategory.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected String getParentCategoryId(
			MBCategory category, String parentCategoryId)
		throws SystemException {

		if (parentCategoryId.equals(MBCategory.DEFAULT_PARENT_CATEGORY_ID)) {
			return parentCategoryId;
		}

		if (category.getCategoryId().equals(parentCategoryId)) {
			return category.getParentCategoryId();
		}
		else {
			MBCategory parentCategory =
				MBCategoryUtil.fetchByPrimaryKey(parentCategoryId);

			if ((parentCategory == null) ||
				(!category.getGroupId().equals(parentCategory.getGroupId()))) {

				return category.getParentCategoryId();
			}

			List subcategoryIds = new ArrayList();

			getSubcategoryIds(
				subcategoryIds, category.getGroupId(),
				category.getCategoryId());

			if (subcategoryIds.contains(parentCategoryId)) {
				return category.getParentCategoryId();
			}

			return parentCategoryId;
		}
	}

	protected void mergeCategories(MBCategory fromCategory, String toCategoryId)
		throws PortalException, SystemException {

		Iterator itr = MBCategoryUtil.findByG_P(
			fromCategory.getGroupId(), fromCategory.getCategoryId()).iterator();

		while (itr.hasNext()) {
			MBCategory category = (MBCategory)itr.next();

			mergeCategories(category, toCategoryId);
		}

		Iterator itr1 = MBThreadUtil.findByCategoryId(
			fromCategory.getCategoryId()).iterator();

		while (itr1.hasNext()) {

			// Thread

			MBThread thread = (MBThread)itr1.next();

			thread.setCategoryId(toCategoryId);

			MBThreadUtil.update(thread);

			Iterator itr2 = MBMessageUtil.findByThreadId(
				thread.getThreadId()).iterator();

			while (itr2.hasNext()) {

				// Message

				MBMessage message = (MBMessage)itr2.next();

				message.setCategoryId(toCategoryId);

				MBMessageUtil.update(message);

				// Lucene

				try {
					if (!fromCategory.isDiscussion()) {
						Indexer.updateMessage(
							message.getCompanyId(), fromCategory.getGroupId(),
							toCategoryId, message.getThreadId(),
							message.getMessageId(), message.getSubject(),
							message.getBody());
					}
				}
				catch (IOException ioe) {
					_log.error(ioe.getMessage());
				}
			}
		}

		MBCategoryUtil.remove(fromCategory.getCategoryId());
	}

	public void subscribeCategory(String userId, String categoryId)
		throws PortalException, SystemException {

		SubscriptionLocalServiceUtil.addSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	public void unsubscribeCategory(String userId, String categoryId)
		throws PortalException, SystemException {

		SubscriptionLocalServiceUtil.deleteSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException();
		}
	}

	private static Log _log =
		LogFactory.getLog(MBCategoryLocalServiceImpl.class);

}