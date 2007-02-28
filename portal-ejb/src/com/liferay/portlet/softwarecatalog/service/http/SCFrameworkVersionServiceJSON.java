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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="SCFrameworkVersionServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCFrameworkVersionServiceJSON {
	public static JSONObject addFrameworkVersion(java.lang.String plid,
		java.lang.String name, java.lang.String url, boolean active,
		int priority, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
			SCFrameworkVersionServiceUtil.addFrameworkVersion(plid, name, url,
				active, priority, addCommunityPermissions, addGuestPermissions);

		return SCFrameworkVersionJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addFrameworkVersion(java.lang.String plid,
		java.lang.String name, java.lang.String url, boolean active,
		int priority, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
			SCFrameworkVersionServiceUtil.addFrameworkVersion(plid, name, url,
				active, priority, communityPermissions, guestPermissions);

		return SCFrameworkVersionJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		SCFrameworkVersionServiceUtil.deleteFrameworkVersion(frameworkVersionId);
	}

	public static JSONArray getFrameworkVersions(long groupId, boolean active)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
				active);

		return SCFrameworkVersionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getFrameworkVersions(long groupId, boolean active,
		int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		java.util.List returnValue = SCFrameworkVersionServiceUtil.getFrameworkVersions(groupId,
				active, begin, end);

		return SCFrameworkVersionJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject getFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
			SCFrameworkVersionServiceUtil.getFrameworkVersion(frameworkVersionId);

		return SCFrameworkVersionJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateFrameworkVersion(long frameworkVersionId,
		java.lang.String name, java.lang.String url, boolean active,
		int priority)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion returnValue =
			SCFrameworkVersionServiceUtil.updateFrameworkVersion(frameworkVersionId,
				name, url, active, priority);

		return SCFrameworkVersionJSONSerializer.toJSONObject(returnValue);
	}
}