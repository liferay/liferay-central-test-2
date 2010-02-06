/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service;


/**
 * <a href="ShoppingCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCategoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryLocalService
 * @generated
 */
public class ShoppingCategoryLocalServiceWrapper
	implements ShoppingCategoryLocalService {
	public ShoppingCategoryLocalServiceWrapper(
		ShoppingCategoryLocalService shoppingCategoryLocalService) {
		_shoppingCategoryLocalService = shoppingCategoryLocalService;
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.addShoppingCategory(shoppingCategory);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory createShoppingCategory(
		long categoryId) {
		return _shoppingCategoryLocalService.createShoppingCategory(categoryId);
	}

	public void deleteShoppingCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.deleteShoppingCategory(categoryId);
	}

	public void deleteShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.deleteShoppingCategory(shoppingCategory);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getShoppingCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getShoppingCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getShoppingCategories(
		int start, int end) throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getShoppingCategories(start, end);
	}

	public int getShoppingCategoriesCount()
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getShoppingCategoriesCount();
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.updateShoppingCategory(shoppingCategory);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.updateShoppingCategory(shoppingCategory,
			merge);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.addCategory(userId,
			parentCategoryId, name, description, serviceContext);
	}

	public void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.addCategoryResources(categoryId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.addCategoryResources(categoryId,
			communityPermissions, guestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategories(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.deleteCategories(groupId);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.deleteCategory(categoryId);
	}

	public void deleteCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.deleteCategory(category);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId) throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getCategories(groupId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getCategories(groupId,
			parentCategoryId, start, end);
	}

	public int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getCategoriesCount(groupId,
			parentCategoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getParentCategories(categoryId);
	}

	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getParentCategories(category);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory getParentCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.getParentCategory(category);
	}

	public void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.SystemException {
		_shoppingCategoryLocalService.getSubcategoryIds(categoryIds, groupId,
			categoryId);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _shoppingCategoryLocalService.updateCategory(categoryId,
			parentCategoryId, name, description, mergeWithParentCategory,
			serviceContext);
	}

	public ShoppingCategoryLocalService getWrappedShoppingCategoryLocalService() {
		return _shoppingCategoryLocalService;
	}

	private ShoppingCategoryLocalService _shoppingCategoryLocalService;
}