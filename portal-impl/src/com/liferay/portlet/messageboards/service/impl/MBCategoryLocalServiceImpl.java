/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.base.MBCategoryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBCategoryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class MBCategoryLocalServiceImpl extends MBCategoryLocalServiceBaseImpl {

	public MBCategory addCategory(
			long userId, long parentCategoryId, String name, String description,
			String emailAddress, String inProtocol, String inServerName,
			int inServerPort, boolean inUseSSL, String inUserName,
			String inPassword, int inReadInterval, String outEmailAddress,
			boolean outCustom, String outServerName, int outServerPort,
			boolean outUseSSL, String outUserName, String outPassword,
			boolean mailingListActive, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addCategory(
			null, userId, parentCategoryId, name, description, emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive, serviceContext);
	}

	public MBCategory addCategory(
			String uuid, long userId, long parentCategoryId,
			String name, String description, String emailAddress,
			String inProtocol, String inServerName, int inServerPort,
			boolean inUseSSL, String inUserName, String inPassword,
			int inReadInterval, String outEmailAddress, boolean outCustom,
			String outServerName, int outServerPort, boolean outUseSSL,
			String outUserName, String outPassword, boolean mailingListActive,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
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

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Mailing list

		mbMailingListLocalService.addMailingList(
			null, userId, groupId, category.getCategoryId(), emailAddress,
			inProtocol, inServerName, inServerPort, inUseSSL, inUserName,
			inPassword, inReadInterval, outEmailAddress, outCustom,
			outServerName, outServerPort, outUseSSL, outUserName, outPassword,
			mailingListActive);

		// Expando

		ExpandoBridge expandoBridge = category.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

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
			long categoryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		addCategoryResources(category, communityPermissions, guestPermissions);
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
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

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

		// Indexer

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		indexer.delete(category);

		// Threads

		mbThreadLocalService.deleteThreads(
			category.getGroupId(), category.getCategoryId());

		// Mailing list

		try {
			mbMailingListLocalService.deleteCategoryMailingList(
				category.getGroupId(), category.getCategoryId());
		}
		catch (NoSuchMailingListException nsmle) {
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			category.getCompanyId(), MBCategory.class.getName(),
			category.getCategoryId());

		// Expando

		expandoValueLocalService.deleteValues(
			MBCategory.class.getName(), category.getCategoryId());

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Category

		mbCategoryPersistence.remove(category);
	}

	public List<MBCategory> getCategories(long groupId) throws SystemException {
		return mbCategoryPersistence.findByGroupId(groupId);
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

	public List<MBCategory> getCompanyCategories(
			long companyId, int start, int end)
		throws SystemException {

		return mbCategoryPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyCategoriesCount(long companyId)
		throws SystemException {

		return mbCategoryPersistence.countByCompanyId(companyId);
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

	public void subscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	public void unsubscribeCategory(long userId, long groupId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			categoryId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, MBCategory.class.getName(), categoryId);
	}

	public MBCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, String emailAddress, String inProtocol,
			String inServerName, int inServerPort, boolean inUseSSL,
			String inUserName, String inPassword, int inReadInterval,
			String outEmailAddress, boolean outCustom, String outServerName,
			int outServerPort, boolean outUseSSL, String outUserName,
			String outPassword, boolean mailingListActive,
			boolean mergeWithParentCategory, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Merge categories

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		mbCategoryPersistence.update(category, false);

		// Mailing list

		MBMailingList mailingList = mbMailingListPersistence.fetchByG_C(
			category.getGroupId(), category.getCategoryId());

		if (mailingList != null) {
			mbMailingListLocalService.updateMailingList(
				mailingList.getMailingListId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				mailingListActive);
		}
		else {
			mbMailingListLocalService.addMailingList(
				null, category.getUserId(), category.getGroupId(),
				category.getCategoryId(), emailAddress, inProtocol,
				inServerName, inServerPort, inUseSSL, inUserName, inPassword,
				inReadInterval, outEmailAddress, outCustom, outServerName,
				outServerPort, outUseSSL, outUserName, outPassword,
				mailingListActive);
		}

		// Expando

		ExpandoBridge expandoBridge = category.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			MBCategory parentCategory = mbCategoryPersistence.fetchByPrimaryKey(
				parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId =
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
			MBCategory category, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

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

		List<MBThread> threads = mbThreadPersistence.findByG_C(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

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

				// Indexer

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					MBMessage.class);

				indexer.reindex(message);
			}
		}

		deleteCategory(fromCategory);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException();
		}
	}

}