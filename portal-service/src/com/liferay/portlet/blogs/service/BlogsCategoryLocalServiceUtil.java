/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service;


/**
 * <a href="BlogsCategoryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.blogs.BlogsCategoryService</code> bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.blogs.BlogsCategoryServiceFactory</code> is responsible for
 * the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.blogs.service.BlogsCategoryService
 * @see com.liferay.portlet.blogs.service.BlogsCategoryServiceFactory
 *
 */
public class BlogsCategoryLocalServiceUtil {
	public static com.liferay.portlet.blogs.model.BlogsCategory addBlogsCategory(
		com.liferay.portlet.blogs.model.BlogsCategory model)
		throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.addBlogsCategory(model);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory updateBlogsCategory(
		com.liferay.portlet.blogs.model.BlogsCategory model)
		throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.updateBlogsCategory(model);
	}

	public static com.liferay.portlet.blogs.service.persistence.BlogsCategoryPersistence getBlogsCategoryPersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getBlogsCategoryPersistence();
	}

	public static void setBlogsCategoryPersistence(
		com.liferay.portlet.blogs.service.persistence.BlogsCategoryPersistence blogsCategoryPersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setBlogsCategoryPersistence(blogsCategoryPersistence);
	}

	public static com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence getBlogsEntryPersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getBlogsEntryPersistence();
	}

	public static void setBlogsEntryPersistence(
		com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setBlogsEntryPersistence(blogsEntryPersistence);
	}

	public static com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder getBlogsEntryFinder() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getBlogsEntryFinder();
	}

	public static void setBlogsEntryFinder(
		com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder blogsEntryFinder) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setBlogsEntryFinder(blogsEntryFinder);
	}

	public static com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence getBlogsStatsUserPersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getBlogsStatsUserPersistence();
	}

	public static void setBlogsStatsUserPersistence(
		com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setBlogsStatsUserPersistence(blogsStatsUserPersistence);
	}

	public static com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder getBlogsStatsUserFinder() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getBlogsStatsUserFinder();
	}

	public static void setBlogsStatsUserFinder(
		com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder blogsStatsUserFinder) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setBlogsStatsUserFinder(blogsStatsUserFinder);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsEntryPersistence getTagsEntryPersistence() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getTagsEntryPersistence();
	}

	public static void setTagsEntryPersistence(
		com.liferay.portlet.tags.service.persistence.TagsEntryPersistence tagsEntryPersistence) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setTagsEntryPersistence(tagsEntryPersistence);
	}

	public static com.liferay.portlet.tags.service.persistence.TagsEntryFinder getTagsEntryFinder() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getTagsEntryFinder();
	}

	public static void setTagsEntryFinder(
		com.liferay.portlet.tags.service.persistence.TagsEntryFinder tagsEntryFinder) {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.setTagsEntryFinder(tagsEntryFinder);
	}

	public static void afterPropertiesSet() {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.addCategory(userId, parentCategoryId,
			name, description, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.blogs.model.BlogsCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.blogs.model.BlogsCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.deleteCategory(category);
	}

	public static java.util.List getCategories(long parentCategoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getCategories(parentCategoryId, begin,
			end);
	}

	public static int getCategoriesCount(long parentCategoryId)
		throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getCategoriesCount(parentCategoryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.getCategory(categoryId);
	}

	public static void getSubcategoryIds(java.util.List categoryIds,
		long categoryId) throws com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		blogsCategoryLocalService.getSubcategoryIds(categoryIds, categoryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		BlogsCategoryLocalService blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getService();

		return blogsCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description);
	}
}