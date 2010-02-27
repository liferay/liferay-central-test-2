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

package com.liferay.portlet.shopping.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.shopping.model.ShoppingItemPrice;
import com.liferay.portlet.shopping.service.ShoppingCartLocalService;
import com.liferay.portlet.shopping.service.ShoppingCategoryLocalService;
import com.liferay.portlet.shopping.service.ShoppingCategoryService;
import com.liferay.portlet.shopping.service.ShoppingCouponLocalService;
import com.liferay.portlet.shopping.service.ShoppingCouponService;
import com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService;
import com.liferay.portlet.shopping.service.ShoppingItemLocalService;
import com.liferay.portlet.shopping.service.ShoppingItemPriceLocalService;
import com.liferay.portlet.shopping.service.ShoppingItemService;
import com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalService;
import com.liferay.portlet.shopping.service.ShoppingOrderService;
import com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderFinder;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence;

import java.util.List;

/**
 * <a href="ShoppingItemPriceLocalServiceBaseImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class ShoppingItemPriceLocalServiceBaseImpl
	implements ShoppingItemPriceLocalService {
	public ShoppingItemPrice addShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) throws SystemException {
		shoppingItemPrice.setNew(true);

		return shoppingItemPricePersistence.update(shoppingItemPrice, false);
	}

	public ShoppingItemPrice createShoppingItemPrice(long itemPriceId) {
		return shoppingItemPricePersistence.create(itemPriceId);
	}

	public void deleteShoppingItemPrice(long itemPriceId)
		throws PortalException, SystemException {
		shoppingItemPricePersistence.remove(itemPriceId);
	}

	public void deleteShoppingItemPrice(ShoppingItemPrice shoppingItemPrice)
		throws SystemException {
		shoppingItemPricePersistence.remove(shoppingItemPrice);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return shoppingItemPricePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return shoppingItemPricePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public ShoppingItemPrice getShoppingItemPrice(long itemPriceId)
		throws PortalException, SystemException {
		return shoppingItemPricePersistence.findByPrimaryKey(itemPriceId);
	}

	public List<ShoppingItemPrice> getShoppingItemPrices(int start, int end)
		throws SystemException {
		return shoppingItemPricePersistence.findAll(start, end);
	}

	public int getShoppingItemPricesCount() throws SystemException {
		return shoppingItemPricePersistence.countAll();
	}

	public ShoppingItemPrice updateShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) throws SystemException {
		shoppingItemPrice.setNew(false);

		return shoppingItemPricePersistence.update(shoppingItemPrice, true);
	}

	public ShoppingItemPrice updateShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice, boolean merge)
		throws SystemException {
		shoppingItemPrice.setNew(false);

		return shoppingItemPricePersistence.update(shoppingItemPrice, merge);
	}

	public ShoppingCartLocalService getShoppingCartLocalService() {
		return shoppingCartLocalService;
	}

	public void setShoppingCartLocalService(
		ShoppingCartLocalService shoppingCartLocalService) {
		this.shoppingCartLocalService = shoppingCartLocalService;
	}

	public ShoppingCartPersistence getShoppingCartPersistence() {
		return shoppingCartPersistence;
	}

	public void setShoppingCartPersistence(
		ShoppingCartPersistence shoppingCartPersistence) {
		this.shoppingCartPersistence = shoppingCartPersistence;
	}

	public ShoppingCategoryLocalService getShoppingCategoryLocalService() {
		return shoppingCategoryLocalService;
	}

	public void setShoppingCategoryLocalService(
		ShoppingCategoryLocalService shoppingCategoryLocalService) {
		this.shoppingCategoryLocalService = shoppingCategoryLocalService;
	}

	public ShoppingCategoryService getShoppingCategoryService() {
		return shoppingCategoryService;
	}

	public void setShoppingCategoryService(
		ShoppingCategoryService shoppingCategoryService) {
		this.shoppingCategoryService = shoppingCategoryService;
	}

	public ShoppingCategoryPersistence getShoppingCategoryPersistence() {
		return shoppingCategoryPersistence;
	}

	public void setShoppingCategoryPersistence(
		ShoppingCategoryPersistence shoppingCategoryPersistence) {
		this.shoppingCategoryPersistence = shoppingCategoryPersistence;
	}

	public ShoppingCouponLocalService getShoppingCouponLocalService() {
		return shoppingCouponLocalService;
	}

	public void setShoppingCouponLocalService(
		ShoppingCouponLocalService shoppingCouponLocalService) {
		this.shoppingCouponLocalService = shoppingCouponLocalService;
	}

	public ShoppingCouponService getShoppingCouponService() {
		return shoppingCouponService;
	}

	public void setShoppingCouponService(
		ShoppingCouponService shoppingCouponService) {
		this.shoppingCouponService = shoppingCouponService;
	}

	public ShoppingCouponPersistence getShoppingCouponPersistence() {
		return shoppingCouponPersistence;
	}

	public void setShoppingCouponPersistence(
		ShoppingCouponPersistence shoppingCouponPersistence) {
		this.shoppingCouponPersistence = shoppingCouponPersistence;
	}

	public ShoppingCouponFinder getShoppingCouponFinder() {
		return shoppingCouponFinder;
	}

	public void setShoppingCouponFinder(
		ShoppingCouponFinder shoppingCouponFinder) {
		this.shoppingCouponFinder = shoppingCouponFinder;
	}

	public ShoppingItemLocalService getShoppingItemLocalService() {
		return shoppingItemLocalService;
	}

	public void setShoppingItemLocalService(
		ShoppingItemLocalService shoppingItemLocalService) {
		this.shoppingItemLocalService = shoppingItemLocalService;
	}

	public ShoppingItemService getShoppingItemService() {
		return shoppingItemService;
	}

	public void setShoppingItemService(ShoppingItemService shoppingItemService) {
		this.shoppingItemService = shoppingItemService;
	}

	public ShoppingItemPersistence getShoppingItemPersistence() {
		return shoppingItemPersistence;
	}

	public void setShoppingItemPersistence(
		ShoppingItemPersistence shoppingItemPersistence) {
		this.shoppingItemPersistence = shoppingItemPersistence;
	}

	public ShoppingItemFinder getShoppingItemFinder() {
		return shoppingItemFinder;
	}

	public void setShoppingItemFinder(ShoppingItemFinder shoppingItemFinder) {
		this.shoppingItemFinder = shoppingItemFinder;
	}

	public ShoppingItemFieldLocalService getShoppingItemFieldLocalService() {
		return shoppingItemFieldLocalService;
	}

	public void setShoppingItemFieldLocalService(
		ShoppingItemFieldLocalService shoppingItemFieldLocalService) {
		this.shoppingItemFieldLocalService = shoppingItemFieldLocalService;
	}

	public ShoppingItemFieldPersistence getShoppingItemFieldPersistence() {
		return shoppingItemFieldPersistence;
	}

	public void setShoppingItemFieldPersistence(
		ShoppingItemFieldPersistence shoppingItemFieldPersistence) {
		this.shoppingItemFieldPersistence = shoppingItemFieldPersistence;
	}

	public ShoppingItemPriceLocalService getShoppingItemPriceLocalService() {
		return shoppingItemPriceLocalService;
	}

	public void setShoppingItemPriceLocalService(
		ShoppingItemPriceLocalService shoppingItemPriceLocalService) {
		this.shoppingItemPriceLocalService = shoppingItemPriceLocalService;
	}

	public ShoppingItemPricePersistence getShoppingItemPricePersistence() {
		return shoppingItemPricePersistence;
	}

	public void setShoppingItemPricePersistence(
		ShoppingItemPricePersistence shoppingItemPricePersistence) {
		this.shoppingItemPricePersistence = shoppingItemPricePersistence;
	}

	public ShoppingOrderLocalService getShoppingOrderLocalService() {
		return shoppingOrderLocalService;
	}

	public void setShoppingOrderLocalService(
		ShoppingOrderLocalService shoppingOrderLocalService) {
		this.shoppingOrderLocalService = shoppingOrderLocalService;
	}

	public ShoppingOrderService getShoppingOrderService() {
		return shoppingOrderService;
	}

	public void setShoppingOrderService(
		ShoppingOrderService shoppingOrderService) {
		this.shoppingOrderService = shoppingOrderService;
	}

	public ShoppingOrderPersistence getShoppingOrderPersistence() {
		return shoppingOrderPersistence;
	}

	public void setShoppingOrderPersistence(
		ShoppingOrderPersistence shoppingOrderPersistence) {
		this.shoppingOrderPersistence = shoppingOrderPersistence;
	}

	public ShoppingOrderFinder getShoppingOrderFinder() {
		return shoppingOrderFinder;
	}

	public void setShoppingOrderFinder(ShoppingOrderFinder shoppingOrderFinder) {
		this.shoppingOrderFinder = shoppingOrderFinder;
	}

	public ShoppingOrderItemLocalService getShoppingOrderItemLocalService() {
		return shoppingOrderItemLocalService;
	}

	public void setShoppingOrderItemLocalService(
		ShoppingOrderItemLocalService shoppingOrderItemLocalService) {
		this.shoppingOrderItemLocalService = shoppingOrderItemLocalService;
	}

	public ShoppingOrderItemPersistence getShoppingOrderItemPersistence() {
		return shoppingOrderItemPersistence;
	}

	public void setShoppingOrderItemPersistence(
		ShoppingOrderItemPersistence shoppingOrderItemPersistence) {
		this.shoppingOrderItemPersistence = shoppingOrderItemPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingCartLocalService")
	protected ShoppingCartLocalService shoppingCartLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCartPersistence")
	protected ShoppingCartPersistence shoppingCartPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingCategoryLocalService")
	protected ShoppingCategoryLocalService shoppingCategoryLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingCategoryService")
	protected ShoppingCategoryService shoppingCategoryService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCategoryPersistence")
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingCouponLocalService")
	protected ShoppingCouponLocalService shoppingCouponLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingCouponService")
	protected ShoppingCouponService shoppingCouponService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponPersistence")
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingCouponFinder")
	protected ShoppingCouponFinder shoppingCouponFinder;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingItemLocalService")
	protected ShoppingItemLocalService shoppingItemLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingItemService")
	protected ShoppingItemService shoppingItemService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPersistence")
	protected ShoppingItemPersistence shoppingItemPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFinder")
	protected ShoppingItemFinder shoppingItemFinder;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingItemFieldLocalService")
	protected ShoppingItemFieldLocalService shoppingItemFieldLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemFieldPersistence")
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingItemPriceLocalService")
	protected ShoppingItemPriceLocalService shoppingItemPriceLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingItemPricePersistence")
	protected ShoppingItemPricePersistence shoppingItemPricePersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingOrderLocalService")
	protected ShoppingOrderLocalService shoppingOrderLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingOrderService")
	protected ShoppingOrderService shoppingOrderService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderPersistence")
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderFinder")
	protected ShoppingOrderFinder shoppingOrderFinder;
	@BeanReference(name = "com.liferay.portlet.shopping.service.ShoppingOrderItemLocalService")
	protected ShoppingOrderItemLocalService shoppingOrderItemLocalService;
	@BeanReference(name = "com.liferay.portlet.shopping.service.persistence.ShoppingOrderItemPersistence")
	protected ShoppingOrderItemPersistence shoppingOrderItemPersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
}