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

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="ShoppingCartLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.shopping.service.impl.ShoppingCartLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCartLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingCartLocalService {
	public com.liferay.portlet.shopping.model.ShoppingCart addShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCart createShoppingCart(
		long cartId);

	public void deleteShoppingCart(long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.shopping.model.ShoppingCart getShoppingCart(
		long cartId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCart> getShoppingCarts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getShoppingCartsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCart updateShoppingCart(
		com.liferay.portlet.shopping.model.ShoppingCart shoppingCart,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteGroupCarts(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteUserCarts(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.shopping.model.ShoppingCart getCart(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.Map<com.liferay.portlet.shopping.model.ShoppingCartItem, Integer> getItems(
		long groupId, java.lang.String itemIds)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.shopping.model.ShoppingCart updateCart(
		long userId, long groupId, java.lang.String itemIds,
		java.lang.String couponCodes, int altShipping, boolean insure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}