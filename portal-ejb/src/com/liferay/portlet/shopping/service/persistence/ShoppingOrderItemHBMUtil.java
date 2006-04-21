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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.util.dao.hibernate.Transformer;

/**
 * <a href="ShoppingOrderItemHBMUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderItemHBMUtil implements Transformer {
	public static com.liferay.portlet.shopping.model.ShoppingOrderItem model(
		ShoppingOrderItemHBM shoppingOrderItemHBM) {
		return model(shoppingOrderItemHBM, true);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderItem model(
		ShoppingOrderItemHBM shoppingOrderItemHBM, boolean checkPool) {
		com.liferay.portlet.shopping.model.ShoppingOrderItem shoppingOrderItem = null;

		if (checkPool) {
			shoppingOrderItem = ShoppingOrderItemPool.get(shoppingOrderItemHBM.getPrimaryKey());
		}

		if (shoppingOrderItem == null) {
			shoppingOrderItem = new com.liferay.portlet.shopping.model.ShoppingOrderItem(shoppingOrderItemHBM.getOrderId(),
					shoppingOrderItemHBM.getItemId(),
					shoppingOrderItemHBM.getSku(),
					shoppingOrderItemHBM.getName(),
					shoppingOrderItemHBM.getDescription(),
					shoppingOrderItemHBM.getProperties(),
					shoppingOrderItemHBM.getSupplierUserId(),
					shoppingOrderItemHBM.getPrice(),
					shoppingOrderItemHBM.getQuantity(),
					shoppingOrderItemHBM.getShippedDate());
			ShoppingOrderItemPool.put(shoppingOrderItem.getPrimaryKey(),
				shoppingOrderItem);
		}

		return shoppingOrderItem;
	}

	public static ShoppingOrderItemHBMUtil getInstance() {
		return _instance;
	}

	public Comparable transform(Object obj) {
		return model((ShoppingOrderItemHBM)obj);
	}

	private static ShoppingOrderItemHBMUtil _instance = new ShoppingOrderItemHBMUtil();
}