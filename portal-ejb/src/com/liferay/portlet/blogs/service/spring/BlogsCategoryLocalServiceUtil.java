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

package com.liferay.portlet.blogs.service.spring;

/**
 * <a href="BlogsCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsCategoryLocalServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		java.lang.String userId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

			return blogsCategoryLocalService.addCategory(userId,
				parentCategoryId, name, description, addCommunityPermissions,
				addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();
			blogsCategoryLocalService.addCategoryResources(categoryId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();
			blogsCategoryLocalService.addCategoryResources(category,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();
			blogsCategoryLocalService.deleteCategory(categoryId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteCategory(
		com.liferay.portlet.blogs.model.BlogsCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();
			blogsCategoryLocalService.deleteCategory(category);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getCategories(
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

			return blogsCategoryLocalService.getCategories(parentCategoryId,
				begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getCategoriesCount(java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

			return blogsCategoryLocalService.getCategoriesCount(parentCategoryId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

			return blogsCategoryLocalService.getCategory(categoryId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void getSubcategoryIds(java.util.List categoryIds,
		java.lang.String categoryId) throws com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();
			blogsCategoryLocalService.getSubcategoryIds(categoryIds, categoryId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

			return blogsCategoryLocalService.updateCategory(categoryId,
				parentCategoryId, name, description);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}