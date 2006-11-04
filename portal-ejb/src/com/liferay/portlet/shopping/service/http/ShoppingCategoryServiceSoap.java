/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.shopping.service.spring.ShoppingCategoryServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="ShoppingCategoryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingCategoryServiceSoap {
	public static com.liferay.portlet.shopping.model.ShoppingCategoryModel addCategory(
		java.lang.String plid, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.addCategory(plid,
					parentCategoryId, name, description,
					addCommunityPermissions, addGuestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteCategory(java.lang.String categoryId)
		throws RemoteException {
		try {
			ShoppingCategoryServiceUtil.deleteCategory(categoryId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategoryModel getCategory(
		java.lang.String categoryId) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.getCategory(categoryId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingCategoryModel updateCategory(
		java.lang.String categoryId, java.lang.String parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingCategory returnValue = ShoppingCategoryServiceUtil.updateCategory(categoryId,
					parentCategoryId, name, description, mergeWithParentCategory);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(ShoppingCategoryServiceSoap.class);
}