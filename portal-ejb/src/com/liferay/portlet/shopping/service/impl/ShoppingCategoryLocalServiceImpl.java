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

package com.liferay.portlet.shopping.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.CategoryNameException;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemUtil;
import com.liferay.portlet.shopping.service.spring.ShoppingCategoryLocalService;
import com.liferay.portlet.shopping.service.spring.ShoppingItemLocalServiceUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ShoppingCategoryLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryLocalServiceImpl
	implements ShoppingCategoryLocalService {

	public ShoppingCategory addCategory(
			String userId, String plid, String parentCategoryId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Category

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		parentCategoryId = getParentCategoryId(groupId, parentCategoryId);
		Date now = new Date();

		validate(name);

		String categoryId = Long.toString(CounterLocalServiceUtil.increment(
			ShoppingCategory.class.getName()));

		ShoppingCategory category = ShoppingCategoryUtil.create(categoryId);

		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		ShoppingCategoryUtil.update(category);

		// Resources

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);

		return category;
	}

	public void addCategoryResources(
			String categoryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ShoppingCategory category =
			ShoppingCategoryUtil.findByPrimaryKey(categoryId);

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
			ShoppingCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), ShoppingCategory.class.getName(),
			category.getPrimaryKey().toString(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteCategory(String categoryId)
		throws PortalException, SystemException {

		ShoppingCategory category =
			ShoppingCategoryUtil.findByPrimaryKey(categoryId);

		deleteCategory(category);
	}

	public void deleteCategory(ShoppingCategory category)
		throws PortalException, SystemException {

		// Categories

		Iterator itr = ShoppingCategoryUtil.findByG_P(
			category.getGroupId(), category.getCategoryId()).iterator();

		while (itr.hasNext()) {
			ShoppingCategory curCategory = (ShoppingCategory)itr.next();

			deleteCategory(curCategory);
		}

		// Items

		ShoppingItemLocalServiceUtil.deleteItems(category.getCategoryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			category.getCompanyId(), ShoppingCategory.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			category.getPrimaryKey().toString());

		// Category

		ShoppingCategoryUtil.remove(category.getCategoryId());
	}

	public List getCategories(String groupId) throws SystemException {
		return ShoppingCategoryUtil.findByGroupId(groupId);
	}

	public List getCategories(
			String groupId, String parentCategoryId, int begin, int end)
		throws SystemException {

		return ShoppingCategoryUtil.findByG_P(
			groupId, parentCategoryId, begin, end);
	}

	public int getCategoriesCount(String groupId, String parentCategoryId)
		throws SystemException {

		return ShoppingCategoryUtil.countByG_P(groupId, parentCategoryId);
	}

	public ShoppingCategory getCategory(String categoryId)
		throws PortalException, SystemException {

		return ShoppingCategoryUtil.findByPrimaryKey(categoryId);
	}

	public ShoppingCategory getParentCategory(ShoppingCategory category)
		throws PortalException, SystemException {

		ShoppingCategory parentCategory = ShoppingCategoryUtil.findByPrimaryKey(
			category.getParentCategoryId());

		return parentCategory;
	}

	public List getParentCategories(String categoryId)
		throws PortalException, SystemException {

		return getParentCategories(
			ShoppingCategoryUtil.findByPrimaryKey(categoryId));
	}

	public List getParentCategories(ShoppingCategory category)
		throws PortalException, SystemException {

		List parentCategories = new ArrayList();

		ShoppingCategory tempCategory = category;

		for (;;) {
			parentCategories.add(tempCategory);

			if (tempCategory.getParentCategoryId().equals(
					ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID)) {

				break;
			}

			tempCategory = ShoppingCategoryUtil.findByPrimaryKey(
				tempCategory.getParentCategoryId());
		}

		Collections.reverse(parentCategories);

		return parentCategories;
	}

	public void getSubcategoryIds(
			List categoryIds, String groupId, String categoryId)
		throws SystemException {

		Iterator itr = ShoppingCategoryUtil.findByG_P(
			groupId, categoryId).iterator();

		while (itr.hasNext()) {
			ShoppingCategory category = (ShoppingCategory)itr.next();

			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());
		}
	}

	public ShoppingCategory updateCategory(
			String categoryId, String parentCategoryId, String name,
			String description, boolean mergeWithParentCategory)
		throws PortalException, SystemException {

		// Category

		ShoppingCategory category =
			ShoppingCategoryUtil.findByPrimaryKey(categoryId);

		String oldCategoryId = category.getParentCategoryId();
		parentCategoryId = getParentCategoryId(category, parentCategoryId);

		validate(name);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		ShoppingCategoryUtil.update(category);

		// Merge categories

		if (mergeWithParentCategory &&
			!oldCategoryId.equals(parentCategoryId) &&
			!parentCategoryId.equals(
				ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			mergeCategories(category, parentCategoryId);
		}

		return category;
	}

	protected String getParentCategoryId(
			String groupId, String parentCategoryId)
		throws SystemException {

		if (!parentCategoryId.equals(
				ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			ShoppingCategory parentCategory =
				ShoppingCategoryUtil.fetchByPrimaryKey(parentCategoryId);

			if ((parentCategory == null) ||
				(!groupId.equals(parentCategory.getGroupId()))) {

				parentCategoryId = ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return parentCategoryId;
	}

	protected String getParentCategoryId(
			ShoppingCategory category, String parentCategoryId)
		throws SystemException {

		if (parentCategoryId.equals(
				ShoppingCategory.DEFAULT_PARENT_CATEGORY_ID)) {

			return parentCategoryId;
		}

		if (category.getCategoryId().equals(parentCategoryId)) {
			return category.getParentCategoryId();
		}
		else {
			ShoppingCategory parentCategory =
				ShoppingCategoryUtil.fetchByPrimaryKey(parentCategoryId);

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

	protected void mergeCategories(
			ShoppingCategory fromCategory, String toCategoryId)
		throws PortalException, SystemException {

		Iterator itr = ShoppingCategoryUtil.findByG_P(
			fromCategory.getGroupId(), fromCategory.getCategoryId()).iterator();

		while (itr.hasNext()) {
			ShoppingCategory category = (ShoppingCategory)itr.next();

			mergeCategories(category, toCategoryId);
		}

		itr = ShoppingItemUtil.findByCategoryId(
			fromCategory.getCategoryId()).iterator();

		while (itr.hasNext()) {

			// Item

			ShoppingItem item = (ShoppingItem)itr.next();

			item.setCategoryId(toCategoryId);

			ShoppingItemUtil.update(item);
		}

		ShoppingCategoryUtil.remove(fromCategory.getCategoryId());
	}

	protected void validate(String name) throws PortalException {
		if ((Validator.isNull(name)) || (name.indexOf("\\\\") != -1) ||
			(name.indexOf("//") != -1)) {

			throw new CategoryNameException();
		}
	}

}