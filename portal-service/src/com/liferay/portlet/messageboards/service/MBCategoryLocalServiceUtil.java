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

package com.liferay.portlet.messageboards.service;

/**
 * <a href="MBCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBCategoryLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, communityPermissions,
			guestPermissions);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		java.lang.String userId, java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.addCategory(userId, plid,
			parentCategoryId, name, description, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(java.lang.String categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryResources(java.lang.String categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.deleteCategories(groupId);
	}

	public static void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.messageboards.model.MBCategory category)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.deleteCategory(category);
	}

	public static java.util.List getCategories(long groupId,
		java.lang.String parentCategoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategories(groupId, parentCategoryId,
			begin, end);
	}

	public static int getCategoriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategoriesCount(groupId);
	}

	public static int getCategoriesCount(long groupId,
		java.lang.String parentCategoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getCategory(categoryId);
	}

	public static void getSubcategoryIds(java.util.List categoryIds,
		long groupId, java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory getSystemCategory()
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.getSystemCategory();
	}

	public static void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.reIndex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		java.lang.String companyId, long groupId,
		java.lang.String[] categoryIds, java.lang.String threadId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.search(companyId, groupId, categoryIds,
			threadId, keywords);
	}

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();

		return mbCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory);
	}

	public static void subscribeCategory(java.lang.String userId,
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.subscribeCategory(userId, categoryId);
	}

	public static void unsubscribeCategory(java.lang.String userId,
		java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBCategoryLocalService mbCategoryLocalService = MBCategoryLocalServiceFactory.getService();
		mbCategoryLocalService.unsubscribeCategory(userId, categoryId);
	}
}