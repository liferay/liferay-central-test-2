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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="AssetTagServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.asset.service.AssetTagServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.asset.model.AssetTagSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.asset.model.AssetTag}, that is translated to a
 * {@link com.liferay.portlet.asset.model.AssetTagSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagServiceHttp
 * @see       com.liferay.portlet.asset.model.AssetTagSoap
 * @see       com.liferay.portlet.asset.service.AssetTagServiceUtil
 * @generated
 */
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