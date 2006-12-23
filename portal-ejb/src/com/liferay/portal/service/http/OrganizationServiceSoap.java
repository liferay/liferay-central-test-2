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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="OrganizationServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationServiceSoap {
	public static void addGroupOrganizations(java.lang.String groupId,
		java.lang.String[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.addGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.OrganizationSoap addOrganization(
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId, int statusId,
		boolean location) throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
					name, regionId, countryId, statusId, location);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteOrganization(java.lang.String organizationId)
		throws RemoteException {
		try {
			OrganizationServiceUtil.deleteOrganization(organizationId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.OrganizationSoap getOrganization(
		java.lang.String organizationId) throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.getOrganization(organizationId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.OrganizationSoap[] getUserOrganizations(
		java.lang.String userId) throws RemoteException {
		try {
			java.util.List returnValue = OrganizationServiceUtil.getUserOrganizations(userId);

			return com.liferay.portal.model.OrganizationSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setGroupOrganizations(java.lang.String groupId,
		java.lang.String[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.setGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void unsetGroupOrganizations(java.lang.String groupId,
		java.lang.String[] organizationIds) throws RemoteException {
		try {
			OrganizationServiceUtil.unsetGroupOrganizations(groupId,
				organizationIds);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		java.lang.String organizationId, java.lang.String parentOrganizationId,
		java.lang.String name, java.lang.String regionId,
		java.lang.String countryId, int statusId, boolean location)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					parentOrganizationId, name, regionId, countryId, statusId,
					location);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.OrganizationSoap updateOrganization(
		java.lang.String organizationId, java.lang.String comments)
		throws RemoteException {
		try {
			com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
					comments);

			return com.liferay.portal.model.OrganizationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrganizationServiceSoap.class);
}