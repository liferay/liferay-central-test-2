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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;

import java.rmi.RemoteException;

public class SCFrameworkVersionServiceSoap {
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.addFrameworkVersion(name, url,
					active, priority, serviceContext);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws RemoteException {
		try {
			SCFrameworkVersionServiceUtil.deleteFrameworkVersion(frameworkVersionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap getFrameworkVersion(
		long frameworkVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersion(frameworkVersionId);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap[] getFrameworkVersions(
		long groupId, boolean active) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
					active);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap[] getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> returnValue =
				SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
					active, start, end);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority) throws RemoteException {
		try {
			com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
				SCFrameworkVersionServiceUtil.updateFrameworkVersion(frameworkVersionId,
					name, url, active, priority);

			return com.liferay.portlet.softwarecatalog.model.SCFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionServiceSoap.class);
}