/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.OrganizationServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="OrganizationServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationServiceJSON {
	public static void addGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		OrganizationServiceUtil.addGroupOrganizations(groupId, organizationIds);
	}

	public static JSONObject addOrganization(
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId, int statusId,
		boolean location)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.addOrganization(parentOrganizationId,
				name, regionId, countryId, statusId, location);

		return OrganizationJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteOrganization(java.lang.String organizationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		OrganizationServiceUtil.deleteOrganization(organizationId);
	}

	public static JSONObject getOrganization(java.lang.String organizationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.getOrganization(organizationId);

		return OrganizationJSONSerializer.toJSONObject(returnValue);
	}

	public static java.lang.String getOrganizationId(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.lang.String returnValue = OrganizationServiceUtil.getOrganizationId(companyId,
				name);

		return returnValue;
	}

	public static JSONArray getUserOrganizations(java.lang.String userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.util.List returnValue = OrganizationServiceUtil.getUserOrganizations(userId);

		return OrganizationJSONSerializer.toJSONArray(returnValue);
	}

	public static void setGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		OrganizationServiceUtil.setGroupOrganizations(groupId, organizationIds);
	}

	public static void unsetGroupOrganizations(long groupId,
		java.lang.String[] organizationIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		OrganizationServiceUtil.unsetGroupOrganizations(groupId, organizationIds);
	}

	public static JSONObject updateOrganization(
		java.lang.String organizationId, java.lang.String parentOrganizationId,
		java.lang.String name, java.lang.String regionId,
		java.lang.String countryId, int statusId, boolean location)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
				parentOrganizationId, name, regionId, countryId, statusId,
				location);

		return OrganizationJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateOrganization(
		java.lang.String organizationId, java.lang.String comments)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Organization returnValue = OrganizationServiceUtil.updateOrganization(organizationId,
				comments);

		return OrganizationJSONSerializer.toJSONObject(returnValue);
	}
}