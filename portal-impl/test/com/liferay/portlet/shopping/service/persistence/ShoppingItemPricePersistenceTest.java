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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.shopping.NoSuchItemPriceException;
import com.liferay.portlet.shopping.model.ShoppingItemPrice;

import java.util.List;

/**
 * <a href="ShoppingItemPricePersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemPricePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShoppingItemPricePersistence)PortalBeanLocatorUtil.locate(ShoppingItemPricePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShoppingItemPrice shoppingItemPrice = _persistence.create(pk);

		assertNotNull(shoppingItemPrice);

		assertEquals(shoppingItemPrice.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		_persistence.remove(newShoppingItemPrice);

		ShoppingItemPrice existingShoppingItemPrice = _persistence.fetchByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		assertNull(existingShoppingItemPrice);
	}

	public void testUpdateNew() throws Exception {
		addShoppingItemPrice();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShoppingItemPrice newShoppingItemPrice = _persistence.create(pk);

		newShoppingItemPrice.setItemId(nextLong());
		newShoppingItemPrice.setMinQuantity(nextInt());
		newShoppingItemPrice.setMaxQuantity(nextInt());
		newShoppingItemPrice.setPrice(nextDouble());
		newShoppingItemPrice.setDiscount(nextDouble());
		newShoppingItemPrice.setTaxable(randomBoolean());
		newShoppingItemPrice.setShipping(nextDouble());
		newShoppingItemPrice.setUseShippingFormula(randomBoolean());
		newShoppingItemPrice.setStatus(nextInt());

		_persistence.update(newShoppingItemPrice, false);

		ShoppingItemPrice existingShoppingItemPrice = _persistence.findByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		assertEquals(existingShoppingItemPrice.getItemPriceId(),
			newShoppingItemPrice.getItemPriceId());
		assertEquals(existingShoppingItemPrice.getItemId(),
			newShoppingItemPrice.getItemId());
		assertEquals(existingShoppingItemPrice.getMinQuantity(),
			newShoppingItemPrice.getMinQuantity());
		assertEquals(existingShoppingItemPrice.getMaxQuantity(),
			newShoppingItemPrice.getMaxQuantity());
		assertEquals(existingShoppingItemPrice.getPrice(),
			newShoppingItemPrice.getPrice());
		assertEquals(existingShoppingItemPrice.getDiscount(),
			newShoppingItemPrice.getDiscount());
		assertEquals(existingShoppingItemPrice.getTaxable(),
			newShoppingItemPrice.getTaxable());
		assertEquals(existingShoppingItemPrice.getShipping(),
			newShoppingItemPrice.getShipping());
		assertEquals(existingShoppingItemPrice.getUseShippingFormula(),
			newShoppingItemPrice.getUseShippingFormula());
		assertEquals(existingShoppingItemPrice.getStatus(),
			newShoppingItemPrice.getStatus());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		ShoppingItemPrice existingShoppingItemPrice = _persistence.findByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchItemPriceException");
		}
		catch (NoSuchItemPriceException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		ShoppingItemPrice existingShoppingItemPrice = _persistence.fetchByPrimaryKey(newShoppingItemPrice.getPrimaryKey());

		assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShoppingItemPrice missingShoppingItemPrice = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShoppingItemPrice);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShoppingItemPrice newShoppingItemPrice = addShoppingItemPrice();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				ShoppingItemPrice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemPriceId",
				newShoppingItemPrice.getItemPriceId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShoppingItemPrice existingShoppingItemPrice = (ShoppingItemPrice)result.get(0);

		assertEquals(existingShoppingItemPrice, newShoppingItemPrice);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShoppingItemPrice.class,
				ShoppingItemPrice.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("itemPriceId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ShoppingItemPrice addShoppingItemPrice()
		throws Exception {
		long pk = nextLong();

		ShoppingItemPrice shoppingItemPrice = _persistence.create(pk);

		shoppingItemPrice.setItemId(nextLong());
		shoppingItemPrice.setMinQuantity(nextInt());
		shoppingItemPrice.setMaxQuantity(nextInt());
		shoppingItemPrice.setPrice(nextDouble());
		shoppingItemPrice.setDiscount(nextDouble());
		shoppingItemPrice.setTaxable(randomBoolean());
		shoppingItemPrice.setShipping(nextDouble());
		shoppingItemPrice.setUseShippingFormula(randomBoolean());
		shoppingItemPrice.setStatus(nextInt());

		_persistence.update(shoppingItemPrice, false);

		return shoppingItemPrice;
	}

	private ShoppingItemPricePersistence _persistence;
}