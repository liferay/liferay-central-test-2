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

import com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil;

import java.rmi.RemoteException;

public class AssetTagPropertyServiceSoap {
	public static com.liferay.portlet.asset.model.AssetTagPropertySoap addTagProperty(
		long tagId, java.lang.String key, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTagProperty returnValue = AssetTagPropertyServiceUtil.addTagProperty(tagId,
					key, value);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteTagProperty(long tagPropertyId)
		throws RemoteException {
		try {
			AssetTagPropertyServiceUtil.deleteTagProperty(tagPropertyId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagPropertySoap[] getTagProperties(
		long tagId) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> returnValue =
				AssetTagPropertyServiceUtil.getTagProperties(tagId);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagPropertySoap[] getTagPropertyValues(
		long companyId, java.lang.String key) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> returnValue =
				AssetTagPropertyServiceUtil.getTagPropertyValues(companyId, key);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetTagPropertySoap updateTagProperty(
		long tagPropertyId, java.lang.String key, java.lang.String value)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetTagProperty returnValue = AssetTagPropertyServiceUtil.updateTagProperty(tagPropertyId,
					key, value);

			return com.liferay.portlet.asset.model.AssetTagPropertySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetTagPropertyServiceSoap.class);
}