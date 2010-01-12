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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.shopping.CategoryNameException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCategoryConstants;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.base.ShoppingCategoryLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingCategoryLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingCategoryLocalServiceImpl
	extends ShoppingCategoryLocalServiceBaseImpl {

	public ShoppingCategory addCategory(
			long userId, long parentCategoryId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		long categoryId = counterLocalService.increment();

		ShoppingCategory category = shoppingCategoryPersistence.create(
			categoryId);

		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		shoppingCategoryPersistence.update(category, false);

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

		return category;
	}

	public void addCategoryResources(
			long categoryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
			long categoryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		addCategoryResources(category, communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
			ShoppingCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), ShoppingCategory.class.getName(),
			category.getCategoryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
			ShoppingCategory category, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), ShoppingCategory.class.getName(),
			category.getCategoryId(), communityPermissions, guestPermissions);
	}

	public void deleteCategories(long groupId)
		throws PortalException, SystemException {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByGroupId(groupId);

		for (ShoppingCategory category : categories) {
			deleteCategory(category);
		}
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		deleteCategory(category);
	}

	public void deleteCategory(ShoppingCategory category)
		throws PortalException, SystemException {

		// Category

		shoppingCategoryPersistence.remove(category);

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), ShoppingCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Categories

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(
				category.getGroupId(), category.getCategoryId());

		for (ShoppingCategory curCategory : categories) {
			deleteCategory(curCategory);
		}

		// Items

		shoppingItemLocalService.deleteItems(
			category.getGroupId(), category.getCategoryId());
	}

	public List<ShoppingCategory> getCategories(long groupId)
		throws SystemException {

		return shoppingCategoryPersistence.findByGroupId(groupId);
	}

	public List<ShoppingCategory> getCategories(
			long groupId, long parentCategoryId, int start, int end)
		throws SystemException {

		return shoppingCategoryPersistence.findByG_P(
			groupId, parentCategoryId, start, end);
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws SystemException {

		return shoppingCategoryPersistence.countByG_P(
			groupId, parentCategoryId);
	}

	public ShoppingCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		return shoppingCategoryPersistence.findByPrimaryKey(categoryId);
	}

	public List<ShoppingCategory> getParentCategories(long categoryId)
		throws PortalException, SystemException {

		return getParentCategories(
			shoppingCategoryPersistence.findByPrimaryKey(categoryId));
	}

	public List<ShoppingCategory> getParentCategories(ShoppingCategory category)
		throws PortalException, SystemException {

		List<ShoppingCategory> parentCategories =
			new ArrayList<ShoppingCategory>();

		ShoppingCategory tempCategory = category;

		for (;;) {
			parentCategories.add(tempCategory);

			if (tempCategory.getParentCategoryId() ==
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				break;
			}

			tempCategory = shoppingCategoryPersistence.findByPrimaryKey(
				tempCategory.getParentCategoryId());
		}

		Collections.reverse(parentCategories);

		return parentCategories;
	}

	public ShoppingCategory getParentCategory(ShoppingCategory category)
		throws PortalException, SystemException {

		ShoppingCategory parentCategory =
			shoppingCategoryPersistence.findByPrimaryKey(
				category.getParentCategoryId());

		return parentCategory;
	}

	public void getSubcategoryIds(
			List<Long> categoryIds, long groupId, long categoryId)
		throws SystemException {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(groupId, categoryId);

		for (ShoppingCategory category : categories) {
			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}
	}

	public ShoppingCategory updateCategory(
			long categoryId, long parentCategoryId, String name,
			String description, boolean mergeWithParentCategory,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		ShoppingCategory category =
			shoppingCategoryPersistence.findByPrimaryKey(categoryId);

		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		// Merge categories

		if (mergeWithParentCategory &&
			(categoryId != parentCategoryId) &&
			(parentCategoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);

			return category;
		}

		// Category

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		shoppingCategoryPersistence.update(category, false);

		return category;
	}

	protected long getParentCategoryId(long groupId, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId !=
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			ShoppingCategory parentCategory =
				shoppingCategoryPersistence.fetchByPrimaryKey(parentCategoryId);

			if ((parentCategory == null) ||
				(groupId != parentCategory.getGroupId())) {

				parentCategoryId =
					ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected long getParentCategoryId(
			ShoppingCategory category, long parentCategoryId)
		throws SystemException {

		if (parentCategoryId ==
				ShoppingCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return parentCategoryId;
		}

		if (category.getCategoryId() == parentCategoryId) {
			return category.getParentCategoryId();
		}
		else {
			ShoppingCategory parentCategory =
				shoppingCategoryPersistence.fetchByPrimaryKey(parentCategoryId);

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

	protected void mergeCategories(
			ShoppingCategory fromCategory, long toCategoryId)
		throws PortalException, SystemException {

		List<ShoppingCategory> categories =
			shoppingCategoryPersistence.findByG_P(
				fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (ShoppingCategory category : categories) {
			mergeCategories(category, toCategoryId);
		}

		List<ShoppingItem> items = shoppingItemPersistence.findByG_C(
			fromCategory.getGroupId(), fromCategory.getCategoryId());

		for (ShoppingItem item : items) {

			// Item

			item.setCategoryId(toCategoryId);

			shoppingItemPersistence.update(item, false);
		}

		deleteCategory(fromCategory);
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new CategoryNameException();
		}
	}

}