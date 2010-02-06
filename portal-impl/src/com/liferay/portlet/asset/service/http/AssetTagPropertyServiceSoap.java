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

import com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="AssetTagPropertyServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.asset.model.AssetTagPropertySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.asset.model.AssetTagProperty}, that is translated to a
 * {@link com.liferay.portlet.asset.model.AssetTagPropertySoap}. Methods that SOAP cannot
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
 * @see       AssetTagPropertyServiceHttp
 * @see       com.liferay.portlet.asset.model.AssetTagPropertySoap
 * @see       com.liferay.portlet.asset.service.AssetTagPropertyServiceUtil
 * @generated
 */
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