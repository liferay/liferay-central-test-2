/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;

import java.rmi.RemoteException;

public class MBCategoryServiceSoap {
	public static com.liferay.portlet.messageboards.model.MBCategorySoap addCategory(
		long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.addCategory(parentCategoryId,
					name, description, emailAddress, inProtocol, inServerName,
					inServerPort, inUseSSL, inUserName, inPassword,
					inReadInterval, outEmailAddress, outCustom, outServerName,
					outServerPort, outUseSSL, outUserName, outPassword,
					mailingListActive, serviceContext);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.deleteCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap getCategory(
		long categoryId) throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap[] getCategories(
		long groupId, long parentCategoryId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.messageboards.model.MBCategory> returnValue =
				MBCategoryServiceUtil.getCategories(groupId, parentCategoryId,
					start, end);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCategoriesCount(long groupId, long parentCategoryId)
		throws RemoteException {
		try {
			int returnValue = MBCategoryServiceUtil.getCategoriesCount(groupId,
					parentCategoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeCategory(long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.subscribeCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeCategory(long categoryId)
		throws RemoteException {
		try {
			MBCategoryServiceUtil.unsubscribeCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategorySoap updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String emailAddress,
		java.lang.String inProtocol, java.lang.String inServerName,
		int inServerPort, boolean inUseSSL, java.lang.String inUserName,
		java.lang.String inPassword, int inReadInterval,
		java.lang.String outEmailAddress, boolean outCustom,
		java.lang.String outServerName, int outServerPort, boolean outUseSSL,
		java.lang.String outUserName, java.lang.String outPassword,
		boolean mailingListActive, boolean mergeWithParentCategory)
		throws RemoteException {
		try {
			com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.updateCategory(categoryId,
					parentCategoryId, name, description, emailAddress,
					inProtocol, inServerName, inServerPort, inUseSSL,
					inUserName, inPassword, inReadInterval, outEmailAddress,
					outCustom, outServerName, outServerPort, outUseSSL,
					outUserName, outPassword, mailingListActive,
					mergeWithParentCategory);

			return com.liferay.portlet.messageboards.model.MBCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBCategoryServiceSoap.class);
}