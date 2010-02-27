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

package com.liferay.portlet.shopping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ShoppingCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ShoppingCategoryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategoryLocalService
 * @generated
 */
public class ShoppingCategoryLocalServiceUtil {
	public static com.liferay.portlet.shopping.model.ShoppingCategory addShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShoppingCategory(shoppingCategory);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory createShoppingCategory(
		long categoryId) {
		return getService().createShoppingCategory(categoryId);
	}

	public static void deleteShoppingCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCategory(categoryId);
	}

	public static void deleteShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShoppingCategory(shoppingCategory);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getShoppingCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getShoppingCategories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCategories(start, end);
	}

	public static int getShoppingCategoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShoppingCategoriesCount();
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCategory(shoppingCategory);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateShoppingCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShoppingCategory(shoppingCategory, merge);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory addCategory(
		long userId, long parentCategoryId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addCategory(userId, parentCategoryId, name, description,
			serviceContext);
	}

	public static void addCategoryResources(long categoryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(long categoryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(categoryId, communityPermissions,
			guestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.shopping.model.ShoppingCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addCategoryResources(category, communityPermissions,
			guestPermissions);
	}

	public static void deleteCategories(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategories(groupId);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCategory(category);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategories(groupId, parentCategoryId, start, end);
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategoriesCount(groupId, parentCategoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getCategory(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getParentCategories(categoryId);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingCategory> getParentCategories(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getParentCategories(category);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory getParentCategory(
		com.liferay.portlet.shopping.model.ShoppingCategory category)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getParentCategory(category);
	}

	public static void getSubcategoryIds(java.util.List<Long> categoryIds,
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getSubcategoryIds(categoryIds, groupId, categoryId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategory updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateCategory(categoryId, parentCategoryId, name,
			description, mergeWithParentCategory, serviceContext);
	}

	public static ShoppingCategoryLocalService getService() {
		if (_service == null) {
			_service = (ShoppingCategoryLocalService)PortalBeanLocatorUtil.locate(ShoppingCategoryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ShoppingCategoryLocalService service) {
		_service = service;
	}

	private static ShoppingCategoryLocalService _service;
}