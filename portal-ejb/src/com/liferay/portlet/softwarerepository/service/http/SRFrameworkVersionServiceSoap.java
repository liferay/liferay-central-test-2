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

package com.liferay.portlet.softwarerepository.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.softwarerepository.service.SRFrameworkVersionServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="SRFrameworkVersionServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionServiceSoap {
	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap addFrameworkVersion(
		java.lang.String userId, java.lang.String plid, java.lang.String name,
		boolean active, int priority, java.lang.String url)
		throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRFrameworkVersion returnValue =
				SRFrameworkVersionServiceUtil.addFrameworkVersion(userId, plid,
					name, active, priority, url);

			return com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws RemoteException {
		try {
			SRFrameworkVersionServiceUtil.deleteFrameworkVersion(frameworkVersionId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap getFrameworkVersion(
		long frameworkVersionId) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRFrameworkVersion returnValue =
				SRFrameworkVersionServiceUtil.getFrameworkVersion(frameworkVersionId);

			return com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap[] getFrameworkVersions(
		java.lang.String groupId) throws RemoteException {
		try {
			java.util.List returnValue = SRFrameworkVersionServiceUtil.getFrameworkVersions(groupId);

			return com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap[] getFrameworkVersions(
		java.lang.String groupId, boolean active) throws RemoteException {
		try {
			java.util.List returnValue = SRFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
					active);

			return com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getFrameworkVersionsCount(java.lang.String groupId)
		throws RemoteException {
		try {
			int returnValue = SRFrameworkVersionServiceUtil.getFrameworkVersionsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getFrameworkVersionsCount(java.lang.String groupId,
		boolean active) throws RemoteException {
		try {
			int returnValue = SRFrameworkVersionServiceUtil.getFrameworkVersionsCount(groupId,
					active);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, boolean active,
		int priority, java.lang.String url) throws RemoteException {
		try {
			com.liferay.portlet.softwarerepository.model.SRFrameworkVersion returnValue =
				SRFrameworkVersionServiceUtil.updateFrameworkVersion(frameworkVersionId,
					name, active, priority, url);

			return com.liferay.portlet.softwarerepository.model.SRFrameworkVersionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SRFrameworkVersionServiceSoap.class);
}