/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.base;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

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
 * <a href="ShoppingItemPriceLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class ShoppingItemPriceLocalServiceBaseImpl
	implements ShoppingItemPriceLocalService, InitializingBean {
	public ShoppingItemPrice addShoppingItemPrice(
		ShoppingItemPrice shoppingItemPrice) throws SystemException {
		shoppingItemPrice.setNew(true);

		return shoppingItemPricePersistence.update(shoppingItemPrice, false);
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

	public void afterPropertiesSet() {
		if (shoppingCartLocalService == null) {
			shoppingCartLocalService = (ShoppingCartLocalService)PortalBeanLocatorUtil.locate(ShoppingCartLocalService.class.getName() +
					".impl");
		}

		if (shoppingCartPersistence == null) {
			shoppingCartPersistence = (ShoppingCartPersistence)PortalBeanLocatorUtil.locate(ShoppingCartPersistence.class.getName() +
					".impl");
		}

		if (shoppingCategoryLocalService == null) {
			shoppingCategoryLocalService = (ShoppingCategoryLocalService)PortalBeanLocatorUtil.locate(ShoppingCategoryLocalService.class.getName() +
					".impl");
		}

		if (shoppingCategoryService == null) {
			shoppingCategoryService = (ShoppingCategoryService)PortalBeanLocatorUtil.locate(ShoppingCategoryService.class.getName() +
					".impl");
		}

		if (shoppingCategoryPersistence == null) {
			shoppingCategoryPersistence = (ShoppingCategoryPersistence)PortalBeanLocatorUtil.locate(ShoppingCategoryPersistence.class.getName() +
					".impl");
		}

		if (shoppingCouponLocalService == null) {
			shoppingCouponLocalService = (ShoppingCouponLocalService)PortalBeanLocatorUtil.locate(ShoppingCouponLocalService.class.getName() +
					".impl");
		}

		if (shoppingCouponService == null) {
			shoppingCouponService = (ShoppingCouponService)PortalBeanLocatorUtil.locate(ShoppingCouponService.class.getName() +
					".impl");
		}

		if (shoppingCouponPersistence == null) {
			shoppingCouponPersistence = (ShoppingCouponPersistence)PortalBeanLocatorUtil.locate(ShoppingCouponPersistence.class.getName() +
					".impl");
		}

		if (shoppingCouponFinder == null) {
			shoppingCouponFinder = (ShoppingCouponFinder)PortalBeanLocatorUtil.locate(ShoppingCouponFinder.class.getName() +
					".impl");
		}

		if (shoppingItemLocalService == null) {
			shoppingItemLocalService = (ShoppingItemLocalService)PortalBeanLocatorUtil.locate(ShoppingItemLocalService.class.getName() +
					".impl");
		}

		if (shoppingItemService == null) {
			shoppingItemService = (ShoppingItemService)PortalBeanLocatorUtil.locate(ShoppingItemService.class.getName() +
					".impl");
		}

		if (shoppingItemPersistence == null) {
			shoppingItemPersistence = (ShoppingItemPersistence)PortalBeanLocatorUtil.locate(ShoppingItemPersistence.class.getName() +
					".impl");
		}

		if (shoppingItemFinder == null) {
			shoppingItemFinder = (ShoppingItemFinder)PortalBeanLocatorUtil.locate(ShoppingItemFinder.class.getName() +
					".impl");
		}

		if (shoppingItemFieldLocalService == null) {
			shoppingItemFieldLocalService = (ShoppingItemFieldLocalService)PortalBeanLocatorUtil.locate(ShoppingItemFieldLocalService.class.getName() +
					".impl");
		}

		if (shoppingItemFieldPersistence == null) {
			shoppingItemFieldPersistence = (ShoppingItemFieldPersistence)PortalBeanLocatorUtil.locate(ShoppingItemFieldPersistence.class.getName() +
					".impl");
		}

		if (shoppingItemPricePersistence == null) {
			shoppingItemPricePersistence = (ShoppingItemPricePersistence)PortalBeanLocatorUtil.locate(ShoppingItemPricePersistence.class.getName() +
					".impl");
		}

		if (shoppingOrderLocalService == null) {
			shoppingOrderLocalService = (ShoppingOrderLocalService)PortalBeanLocatorUtil.locate(ShoppingOrderLocalService.class.getName() +
					".impl");
		}

		if (shoppingOrderService == null) {
			shoppingOrderService = (ShoppingOrderService)PortalBeanLocatorUtil.locate(ShoppingOrderService.class.getName() +
					".impl");
		}

		if (shoppingOrderPersistence == null) {
			shoppingOrderPersistence = (ShoppingOrderPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderPersistence.class.getName() +
					".impl");
		}

		if (shoppingOrderFinder == null) {
			shoppingOrderFinder = (ShoppingOrderFinder)PortalBeanLocatorUtil.locate(ShoppingOrderFinder.class.getName() +
					".impl");
		}

		if (shoppingOrderItemLocalService == null) {
			shoppingOrderItemLocalService = (ShoppingOrderItemLocalService)PortalBeanLocatorUtil.locate(ShoppingOrderItemLocalService.class.getName() +
					".impl");
		}

		if (shoppingOrderItemPersistence == null) {
			shoppingOrderItemPersistence = (ShoppingOrderItemPersistence)PortalBeanLocatorUtil.locate(ShoppingOrderItemPersistence.class.getName() +
					".impl");
		}
	}

	protected ShoppingCartLocalService shoppingCartLocalService;
	protected ShoppingCartPersistence shoppingCartPersistence;
	protected ShoppingCategoryLocalService shoppingCategoryLocalService;
	protected ShoppingCategoryService shoppingCategoryService;
	protected ShoppingCategoryPersistence shoppingCategoryPersistence;
	protected ShoppingCouponLocalService shoppingCouponLocalService;
	protected ShoppingCouponService shoppingCouponService;
	protected ShoppingCouponPersistence shoppingCouponPersistence;
	protected ShoppingCouponFinder shoppingCouponFinder;
	protected ShoppingItemLocalService shoppingItemLocalService;
	protected ShoppingItemService shoppingItemService;
	protected ShoppingItemPersistence shoppingItemPersistence;
	protected ShoppingItemFinder shoppingItemFinder;
	protected ShoppingItemFieldLocalService shoppingItemFieldLocalService;
	protected ShoppingItemFieldPersistence shoppingItemFieldPersistence;
	protected ShoppingItemPricePersistence shoppingItemPricePersistence;
	protected ShoppingOrderLocalService shoppingOrderLocalService;
	protected ShoppingOrderService shoppingOrderService;
	protected ShoppingOrderPersistence shoppingOrderPersistence;
	protected ShoppingOrderFinder shoppingOrderFinder;
	protected ShoppingOrderItemLocalService shoppingOrderItemLocalService;
	protected ShoppingOrderItemPersistence shoppingOrderItemPersistence;
}