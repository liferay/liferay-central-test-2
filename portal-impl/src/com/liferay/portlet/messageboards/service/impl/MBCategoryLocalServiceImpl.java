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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.Indexer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBCategoryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBCategoryLocalServiceImpl extends MBCategoryLocalServiceBaseImpl {

	public MBCategory addCategory(
			long userId, long plid, long parentCategoryId, String name,
			String description, String emailAddress, String inProtocol,
			String inServerName, boolean inUseSSL, int inServerPort,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			boolean outUseSSL, int outServerPort, String outUserName,
			String outPassword, boolean mailingListActive,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			null, userId, plid, parentCategoryId, name, description,
			emailAddress, inProtocol, inServerName, inUseSSL,
			inServerPort, inUserName, inPassword, inReadInterval,
			outEmailAddress, outCustom, outServerName,
			outUseSSL, outServerPort, outUserName, outPassword,
			mailingListActive, null, null, communityPermissions,
			guestPermissions);
	}
	public MBCategory addCategory(
			long userId, long plid, long parentCategoryId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			null, userId, plid, parentCategoryId, name, description, null,
			null, null, false, 0, null, null, 0, null, false, null, false, 0,
			null, null, false, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public MBCategory addCategory(
			String uuid, long userId, long plid, long parentCategoryId,
			String name, String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			uuid, userId, plid, parentCategoryId, name, description, null,
			null, null, false, 0, null, null, 0, null, false, null, false, 0,
			null, null, false, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public MBCategory addCategory(
			long userId, long plid, long parentCategoryId, String name,
			String description, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addCategory(
			null, userId, plid, parentCategoryId, name, description, null,
			null, null, false, 0, null, null, 0, null, false, null, false, 0,
			null, null, false, null, null, communityPermissions,
			guestPermissions);
	}

	public MBCategory addCategory(
			String uuid, long userId, long plid, long parentCategoryId,
			String name, String description, String emailAddress,
			String inProtocol, String inServerName, boolean inUseSSL,
			int inServerPort, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, boolean outUseSSL, int outServerPort,
			String outUserName, String outPassword, boolean mailingListActive,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		long categoryId = counterLocalService.increment();

		MBCategory category = mbCategoryPersistence.create(categoryId);

		category.setUuid(uuid);
		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		mbCategoryPersistence.update(category, false);

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

		// Mailing

		mbMailingListLocalService.addMailingList(
			null, userId, category.getCategoryId(), emailAddress, inProtocol,
			inServerName, inServerPort, inUseSSL, inUserName, inPassword,
			inReadInterval, outEmailAddress, outCustom, outServerName,
			outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive);

		return category;
	}

	public void addCategoryResources(
			long categoryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
			MBCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
			long categoryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(category, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
			MBCategory category, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), MBCategory.class.getName(),
			category.getCategoryId(), communityPermissions, guestPermissions);
	}

	public void deleteCategories(long groupId)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			groupId, MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

		for (MBCategory category : categories) {
			deleteCategory(category);
		}
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		deleteCategory(category);
	}

	public void deleteCategory(MBCategory category)
		throws PortalException, SystemException {

		// Categories

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			category.getGroupId(), category.getCategoryId());

		for (MBCategory curCategory : categories) {
			deleteCategory(curCategory);
		}

		// Lucene

		try {
			Indexer.deleteMessages(
				category.getCompanyId(), category.getCategoryId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + category.getCategoryId(), se);
		}

		// Threads

		mbThreadLocalService.deleteThreads(category.getCategoryId());

		// Mailing list

		try {
			mbMailingListLocalService.deleteCategoryMailingList(
				category.getCategoryId());
		}
		catch (NoSuchMailingListException nsmle) {
		}

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Category

		mbCategoryPersistence.remove(category.getCategoryId());
	}

	public List<MBCategory> getCategories(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(groupId, parentCategoryId);
	}

	public List<MBCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	public int getCategoriesCount(long groupId) throws SystemException {
		return mbCategoryPersistence.countByGroupId(groupId);
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return mbCategoryPersistence.countByG_P(groupId, parentCategoryId);
	}

	public MBCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		return mbCategoryPersistence.findByPrimaryKey(categoryId);
	}

	public void getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			groupId, categoryId);

		for (MBCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}
	}

	public List<MBCategory> getSubscribedCategories(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return mbCategoryFinder.findByS_G_U(groupId, userId, start, end);
	}

	public int getSubscribedCategoriesCount(long groupId, long userId)
		throws SystemException {

		return mbCategoryFinder.countByS_G_U(groupId, userId);
	}

	public MBCategory getSystemCategory() throws SystemException {
		long categoryId = CompanyConstants.SYSTEM;

		MBCategory category = mbCategoryPersistence.fetchByPrimaryKey(
			categoryId);

		if (category == null) {
			category = mbCategoryPersistence.create(categoryId);

			category.setCompanyId(CompanyConstants.SYSTEM);
			category.setUserId(CompanyConstants.SYSTEM);

			mbCategoryPersistence.update(category, false);
		}

		return category;
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			List<MBCategory> categories = mbCategoryPersistence.findByCompanyId(
				companyId);

			for (MBCategory category : categories) {
				long categoryId = category.getCategoryId();

				List<MBMessage> messages =
					mbMessagePersistence.findByCategoryId(categoryId);

				for (MBMessage message : messages) {
					long groupId = category.getGroupId();
					String userName = message.getUserName();
					long threadId = message.getThreadId();
					long messageId = message.getMessageId();
					String title = message.getSubject();
					String content = message.getBody();

					String[] tagsEntries = tagsEntryLocalService.getEntryNames(
						MBMessage.class.getName(), messageId);

					try {
						Document doc = Indexer.getMessageDocument(
							companyId, groupId, userName, categoryId, threadId,
							messageId, title, content, tagsEntries);

						SearchEngineUtil.addDocument(companyId, doc);
					}
					catch (Exception e1) {
						_log.error("Reindexing " + messageId, e1);
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
			long companyId, long groupId, long[] categoryIds, long threadId,
			String keywords, int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				BooleanQuery categoryIdsQuery =
					BooleanQueryFactoryUtil.create();

				for (long categoryId : categoryIds) {
					TermQuery termQuery = TermQueryFactoryUtil.create(
						"categoryId", categoryId);

					categoryIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(categoryIdsQuery, BooleanClauseOccur.MUST);
			}

			if (threadId > 0) {
				contextQuery.addTerm("threadId", threadId);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.USER_NAME, keywords);
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(companyId, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		return updateCategory(
			categoryId, parentCategoryId, name, description,
			mergeWithParentCategory, 0, null, null, null, 0, false, null, null,
			0, null, false, null, 0, false, null, null, false);
	}

	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory,
			long mailingListId, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive)
		throws PortalException, SystemException {

		// Category

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		mbCategoryPersistence.update(category, false);

		// Merge categories

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId != MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);
		}

		// Mailing

		mbMailingListLocalService.updateMailingList(mailingListId,
			emailAddress, inProtocol, inServerName, inServerPort, inUseSSL,
			inUserName, inPassword, inReadInterval, outEmailAddress,
			outCustom, outServerName, outServerPort, outUseSSL, outUserName,
			outPassword, mailingListActive);

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId != MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId = MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
			MBCategory category, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId == MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}
		else {
			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(category.getGroupId() != parentCategory.getGroupId())) {

				return category.getParentCategoryId();
			}

			List<Long> subcategoryIds = new ArrayList<Long>();

			getSubcategoryIds(
				subcategoryIds, category.getGroupId(),
				category.getCategoryId());

			if (subcategoryIds.contains(parentCategoryId)) {
				return category.getParentCategoryId();
			}

			return parentCategoryId;
		}
	}

	protected void mergeCategories(MBCategory fromCategory, long toCategoryId)
		throws PortalException, SystemException {

		List<MBCategory> categories = mbCategoryPersistence.findByG_P(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (MBCategory category : categories) {
			mergeCategories(category, toCategoryId);
		}

		List<MBThread> threads = mbThreadPersistence.findByCategoryId(
			fromCategory.getCategoryId());

		for (MBThread thread : threads) {

			// Thread

			thread.setCategoryId(toCategoryId);

			mbThreadPersistence.update(thread, false);

			List<MBMessage> messages = mbMessagePersistence.findByThreadId(
				thread.getThreadId());

			for (MBMessage message : messages) {

				// Message

				message.setCategoryId(toCategoryId);

				mbMessagePersistence.update(message, false);

				// Lucene

				try {
					if (!fromCategory.isDiscussion()) {
						String[] tagsEntries =
							tagsEntryLocalService.getEntryNames(
								MBMessage.class.getName(),
								message.getMessageId());

						Indexer.updateMessage(
							message.getCompanyId(), fromCategory.getGroupId(),
							message.getUserName(), toCategoryId,
							message.getThreadId(), message.getMessageId(),
							message.getSubject(), message.getBody(),
							tagsEntries);
					}
				}
				catch (SearchException se) {
					_log.error("Indexing " + message.getMessageId(), se);
				}
			}
		}

		mbCategoryPersistence.remove(fromCategory.getCategoryId());
	}

	public void subscribeCategory(long userId, long categoryId)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	public void unsubscribeCategory(long userId, long categoryId)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(
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