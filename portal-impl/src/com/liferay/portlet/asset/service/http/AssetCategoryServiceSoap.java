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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;

import java.rmi.RemoteException;

public class AssetCategoryServiceSoap {
	public static com.liferay.portlet.asset.model.AssetCategorySoap addCategory(
		long parentCategoryId, java.lang.String name, long vocabularyId,
		java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetCategory returnValue = AssetCategoryServiceUtil.addCategory(parentCategoryId,
					name, vocabularyId, categoryProperties, serviceContext);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCategory(long categoryId)
		throws RemoteException {
		try {
			AssetCategoryServiceUtil.deleteCategory(categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap[] getCategories(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetCategory> returnValue =
				AssetCategoryServiceUtil.getCategories(className, classPK);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap getCategory(
		long categoryId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetCategory returnValue = AssetCategoryServiceUtil.getCategory(categoryId);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap[] getChildCategories(
		long parentCategoryId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetCategory> returnValue =
				AssetCategoryServiceUtil.getChildCategories(parentCategoryId);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap[] getVocabularyCategories(
		long vocabularyId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetCategory> returnValue =
				AssetCategoryServiceUtil.getVocabularyCategories(vocabularyId);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap[] getVocabularyRootCategories(
		long vocabularyId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetCategory> returnValue =
				AssetCategoryServiceUtil.getVocabularyRootCategories(vocabularyId);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name,
		java.lang.String[] categoryProperties, int start, int end)
		throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue = AssetCategoryServiceUtil.search(groupId,
					name, categoryProperties, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetCategorySoap updateCategory(
		long categoryId, long parentCategoryId, java.lang.String name,
		long vocabularyId, java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetCategory returnValue = AssetCategoryServiceUtil.updateCategory(categoryId,
					parentCategoryId, name, vocabularyId, categoryProperties,
					serviceContext);

			return com.liferay.portlet.asset.model.AssetCategorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetCategoryServiceSoap.class);
}