/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTopic;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBTopicUtil;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalService;
import com.liferay.portlet.messageboards.service.spring.MBTopicLocalServiceUtil;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.util.Validator;
import com.liferay.util.lucene.Hits;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
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

		// Category

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		validate(name);

		String categoryId = Long.toString(CounterServiceUtil.increment(
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

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);

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

		// Topics

		MBTopicLocalServiceUtil.deleteTopics(category.getCategoryId());

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

	public void reIndex(String[] ids) throws SystemException {
		try {
			String companyId = ids[0];

			Iterator itr1 = MBTopicUtil.findByCompanyId(companyId).iterator();

			while (itr1.hasNext()) {
				MBTopic topic = (MBTopic)itr1.next();

				String topicId = topic.getTopicId();
				MBCategory category = topic.getCategory();

				Iterator itr2 = MBMessageUtil.findByTopicId(topicId).iterator();

				while (itr2.hasNext()) {
					MBMessage message = (MBMessage)itr2.next();

					String groupId = category.getGroupId();
					String categoryId = category.getCategoryId();
					String threadId = message.getThreadId();
					String messageId = message.getMessageId();
					String title = message.getSubject();
					String content = message.getBody();

					try {
						Indexer.addMessage(
							companyId, groupId, categoryId, topicId, threadId,
							messageId, title, content);
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
			String[] topicIds, String threadId, String keywords)
		throws SystemException {

		try {
			Hits hits = new Hits();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery booleanQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				booleanQuery, LuceneFields.PORTLET_ID, Indexer.PORTLET_ID);
			LuceneUtil.addRequiredTerm(
				booleanQuery, LuceneFields.GROUP_ID, groupId);

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				BooleanQuery categoryIdsQuery = new BooleanQuery();

				for (int i = 0; i < categoryIds.length; i++) {
					categoryIdsQuery.add(new TermQuery(new Term(
						"categoryId", categoryIds[i])), false, false);
				}

				booleanQuery.add(categoryIdsQuery, true, false);
			}

			if ((topicIds != null) && (topicIds.length > 0)) {
				BooleanQuery topicIdsQuery = new BooleanQuery();

				for (int i = 0; i < topicIds.length; i++) {
					topicIdsQuery.add(new TermQuery(new Term(
						"topicId", topicIds[i])), false, false);
				}

				booleanQuery.add(topicIdsQuery, true, false);
			}

			if (Validator.isNotNull(threadId)) {
				LuceneUtil.addTerm(booleanQuery, "threadId", threadId);
			}

			LuceneUtil.addTerm(booleanQuery, LuceneFields.TITLE, keywords);
			LuceneUtil.addTerm(booleanQuery, LuceneFields.CONTENT, keywords);

			Searcher searcher = LuceneUtil.getSearcher(companyId);

			Query query = QueryParser.parse(
				booleanQuery.toString(), LuceneFields.CONTENT,
				LuceneUtil.getAnalyzer());

			hits.recordHits(searcher.search(query));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	public MBCategory updateCategory(
			String categoryId, String parentCategoryId, String name,
			String description)
		throws PortalException, SystemException {

		validate(name);

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		MBCategoryUtil.update(category);

		return category;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException();
		}
	}

	private static Log _log =
		LogFactory.getLog(MBCategoryLocalServiceImpl.class);

}