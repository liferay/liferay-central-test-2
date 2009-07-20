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

import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import java.rmi.RemoteException;

public class AssetTagServiceSoap {
	public static com.liferay.portlet.asset.model.AssetTagSoap addTag(
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.addTag(name,
					tagProperties, serviceContext);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTag(long tagId) throws RemoteException {
		try {
			AssetTagServiceUtil.deleteTag(tagId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getGroupTags(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getGroupTags(groupId);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap getTag(
		long tagId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.getTag(tagId);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		long groupId, long classNameId, java.lang.String name)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(groupId, classNameId, name);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap[] getTags(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTag> returnValue =
				AssetTagServiceUtil.getTags(className, classPK);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void mergeTags(long fromTagId, long toTagId)
		throws RemoteException {
		try {
			AssetTagServiceUtil.mergeTags(fromTagId, toTagId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) throws RemoteException {
		try {
			com.liferay.portal.kernel.json.JSONArray returnValue = AssetTagServiceUtil.search(groupId,
					name, tagProperties, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagSoap updateTag(
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTag returnValue = AssetTagServiceUtil.updateTag(tagId,
					name, tagProperties, serviceContext);

			return com.liferay.portlet.asset.model.AssetTagSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagServiceSoap.class);
}