/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portlet.shopping.service.ShoppingCategoryServiceUtil;

import org.json.JSONObject;

/**
 * <a href="ShoppingCategoryServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryServiceJSON {
	public static JSONObject addCategory(java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.addCategory(plid,
				parentCategoryId, name, description, addCommunityPermissions,
				addGuestPermissions);

		return ShoppingCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addCategory(java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.addCategory(plid,
				parentCategoryId, name, description, communityPermissions,
				guestPermissions);

		return ShoppingCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		ShoppingCategoryServiceUtil.deleteCategory(categoryId);
	}

	public static JSONObject getCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.getCategory(categoryId);

		return ShoppingCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateCategory(java.lang.String categoryId,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.updateCategory(categoryId,
				parentCategoryId, name, description, mergeWithParentCategory);

		return ShoppingCategoryJSONSerializer.toJSONObject(returnValue);
	}
}