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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portlet.blogs.CategoryNameException;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.persistence.BlogsCategoryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.spring.BlogsCategoryLocalService;
import com.liferay.portlet.blogs.util.Indexer;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BlogsCategoryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsCategoryLocalServiceImpl
	implements BlogsCategoryLocalService {

	public BlogsCategory addCategory(
			String userId, String parentCategoryId, String name,
			String description, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Category

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		validate(name);

		String categoryId = Long.toString(CounterLocalServiceUtil.increment(
			BlogsCategory.class.getName()));

		BlogsCategory category = BlogsCategoryUtil.create(categoryId);

		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		BlogsCategoryUtil.update(category);

		// Resources

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);

		return category;
	}

	public void addCategoryResources(
			String categoryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsCategory category = BlogsCategoryUtil.findByPrimaryKey(categoryId);

		addCategoryResources(
			category, addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
			BlogsCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			category.getCompanyId(), null, category.getUserId(),
			BlogsCategory.class.getName(), category.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void deleteCategory(String categoryId)
		throws PortalException, SystemException {

		BlogsCategory category = BlogsCategoryUtil.findByPrimaryKey(categoryId);

		deleteCategory(category);
	}

	public void deleteCategory(BlogsCategory category)
		throws PortalException, SystemException {

		// Categories

		Iterator itr = BlogsCategoryUtil.findByParentCategoryId(
			category.getCategoryId()).iterator();

		while (itr.hasNext()) {
			BlogsCategory curCategory = (BlogsCategory)itr.next();

			deleteCategory(curCategory);
		}

		// Entries

		itr = BlogsEntryUtil.findByCategoryId(
			category.getCategoryId()).iterator();

		while (itr.hasNext()) {

			// Entry

			BlogsEntry entry = (BlogsEntry)itr.next();

			entry.setCategoryId(BlogsCategory.DEFAULT_PARENT_CATEGORY_ID);

			BlogsEntryUtil.update(entry);

			// Lucene

			try {
				Indexer.updateEntry(
					entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
					category.getCategoryId(), entry.getEntryId(),
					entry.getTitle(), entry.getContent());
			}
			catch (IOException ioe) {
				_log.error(ioe.getMessage());
			}
		}

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			category.getCompanyId(), BlogsCategory.class.getName(),
			Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
			category.getPrimaryKey().toString());

		// Category

		BlogsCategoryUtil.remove(category.getCategoryId());
	}

	public List getCategories(String parentCategoryId, int begin, int end)
		throws SystemException {

		return BlogsCategoryUtil.findByParentCategoryId(
			parentCategoryId, begin, end);
	}

	public int getCategoriesCount(String parentCategoryId)
		throws SystemException {

		return BlogsCategoryUtil.countByParentCategoryId(parentCategoryId);
	}

	public BlogsCategory getCategory(String categoryId)
		throws PortalException, SystemException {

		return BlogsCategoryUtil.findByPrimaryKey(categoryId);
	}

	public void getSubcategoryIds(List categoryIds, String categoryId)
		throws SystemException {

		Iterator itr = BlogsCategoryUtil.findByParentCategoryId(
			categoryId).iterator();

		while (itr.hasNext()) {
			BlogsCategory category = (BlogsCategory)itr.next();

			categoryIds.add(category.getCategoryId());

			getSubcategoryIds(categoryIds, category.getCategoryId());
		}
	}

	public BlogsCategory updateCategory(
			String categoryId, String parentCategoryId, String name,
			String description)
		throws PortalException, SystemException {

		validate(name);

		BlogsCategory category = BlogsCategoryUtil.findByPrimaryKey(categoryId);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setDescription(description);

		BlogsCategoryUtil.update(category);

		return category;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new CategoryNameException();
		}
	}

	private static Log _log =
		LogFactory.getLog(BlogsCategoryLocalServiceImpl.class);

}