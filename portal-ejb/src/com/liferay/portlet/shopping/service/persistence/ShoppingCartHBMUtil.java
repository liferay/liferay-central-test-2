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
 * <a href="ShoppingCartHBMUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCartHBMUtil implements Transformer {
	public static com.liferay.portlet.shopping.model.ShoppingCart model(
		ShoppingCartHBM shoppingCartHBM) {
		return model(shoppingCartHBM, true);
	}

	public static com.liferay.portlet.shopping.model.ShoppingCart model(
		ShoppingCartHBM shoppingCartHBM, boolean checkPool) {
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart = null;

		if (checkPool) {
			shoppingCart = ShoppingCartPool.get(shoppingCartHBM.getPrimaryKey());
		}

		if (shoppingCart == null) {
			shoppingCart = new com.liferay.portlet.shopping.model.ShoppingCart(shoppingCartHBM.getCartId(),
					shoppingCartHBM.getCompanyId(),
					shoppingCartHBM.getUserId(),
					shoppingCartHBM.getCreateDate(),
					shoppingCartHBM.getModifiedDate(),
					shoppingCartHBM.getItemIds(),
					shoppingCartHBM.getCouponIds(),
					shoppingCartHBM.getAltShipping(),
					shoppingCartHBM.getInsure());
			ShoppingCartPool.put(shoppingCart.getPrimaryKey(), shoppingCart);
		}

		return shoppingCart;
	}

	public static ShoppingCartHBMUtil getInstance() {
		return _instance;
	}

	public Comparable transform(Object obj) {
		return model((ShoppingCartHBM)obj);
	}

	private static ShoppingCartHBMUtil _instance = new ShoppingCartHBMUtil();
}