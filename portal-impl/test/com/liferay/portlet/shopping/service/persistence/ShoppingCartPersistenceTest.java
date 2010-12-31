/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.shopping.NoSuchCartException;
import com.liferay.portlet.shopping.model.ShoppingCart;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingCartPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingCartPersistence)PortalBeanLocatorUtil.locate(ShoppingCartPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingCart shoppingCart = _persistence.create(pk);

		assertNotNull(shoppingCart);

		assertEquals(shoppingCart.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		_persistence.remove(newShoppingCart);

		ShoppingCart existingShoppingCart = _persistence.fetchByPrimaryKey(newShoppingCart.getPrimaryKey());

		assertNull(existingShoppingCart);
	}

	public void testUpdateNew() throws Exception {
		addShoppingCart();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingCart newShoppingCart = _persistence.create(pk);

		newShoppingCart.setGroupId(nextLong());
		newShoppingCart.setCompanyId(nextLong());
		newShoppingCart.setUserId(nextLong());
		newShoppingCart.setUserName(randomString());
		newShoppingCart.setCreateDate(nextDate());
		newShoppingCart.setModifiedDate(nextDate());
		newShoppingCart.setItemIds(randomString());
		newShoppingCart.setCouponCodes(randomString());
		newShoppingCart.setAltShipping(nextInt());
		newShoppingCart.setInsure(randomBoolean());

		_persistence.update(newShoppingCart, false);

		ShoppingCart existingShoppingCart = _persistence.findByPrimaryKey(newShoppingCart.getPrimaryKey());

		assertEquals(existingShoppingCart.getCartId(),
			newShoppingCart.getCartId());
		assertEquals(existingShoppingCart.getGroupId(),
			newShoppingCart.getGroupId());
		assertEquals(existingShoppingCart.getCompanyId(),
			newShoppingCart.getCompanyId());
		assertEquals(existingShoppingCart.getUserId(),
			newShoppingCart.getUserId());
		assertEquals(existingShoppingCart.getUserName(),
			newShoppingCart.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingShoppingCart.getCreateDate()),
			Time.getShortTimestamp(newShoppingCart.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingShoppingCart.getModifiedDate()),
			Time.getShortTimestamp(newShoppingCart.getModifiedDate()));
		assertEquals(existingShoppingCart.getItemIds(),
			newShoppingCart.getItemIds());
		assertEquals(existingShoppingCart.getCouponCodes(),
			newShoppingCart.getCouponCodes());
		assertEquals(existingShoppingCart.getAltShipping(),
			newShoppingCart.getAltShipping());
		assertEquals(existingShoppingCart.getInsure(),
			newShoppingCart.getInsure());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		ShoppingCart existingShoppingCart = _persistence.findByPrimaryKey(newShoppingCart.getPrimaryKey());

		assertEquals(existingShoppingCart, newShoppingCart);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCartException");
		}
		catch (NoSuchCartException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		ShoppingCart existingShoppingCart = _persistence.fetchByPrimaryKey(newShoppingCart.getPrimaryKey());

		assertEquals(existingShoppingCart, newShoppingCart);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingCart missingShoppingCart = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingCart);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingCart newShoppingCart = addShoppingCart();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("cartId",
				newShoppingCart.getCartId()));

		List<ShoppingCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingCart existingShoppingCart = result.get(0);

		assertEquals(existingShoppingCart, newShoppingCart);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingCart.class,
				ShoppingCart.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("cartId", nextLong()));

		List<ShoppingCart> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ShoppingCart addShoppingCart() throws Exception {
		long pk = nextLong();

		ShoppingCart shoppingCart = _persistence.create(pk);

		shoppingCart.setGroupId(nextLong());
		shoppingCart.setCompanyId(nextLong());
		shoppingCart.setUserId(nextLong());
		shoppingCart.setUserName(randomString());
		shoppingCart.setCreateDate(nextDate());
		shoppingCart.setModifiedDate(nextDate());
		shoppingCart.setItemIds(randomString());
		shoppingCart.setCouponCodes(randomString());
		shoppingCart.setAltShipping(nextInt());
		shoppingCart.setInsure(randomBoolean());

		_persistence.update(shoppingCart, false);

		return shoppingCart;
	}

	private ShoppingCartPersistence _persistence;
}