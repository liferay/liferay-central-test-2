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

import com.liferay.portlet.softwarecatalog.service.SCProductEntryServiceUtil;

import org.json.JSONObject;

/**
 * <a href="SCProductEntryServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductEntryServiceJSON {
	public static JSONObject addProductEntry(java.lang.String plid,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCProductEntry returnValue = SCProductEntryServiceUtil.addProductEntry(plid,
				name, type, shortDescription, longDescription, pageURL,
				repoGroupId, repoArtifactId, licenseIds,
				addCommunityPermissions, addGuestPermissions);

		return SCProductEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addProductEntry(java.lang.String plid,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCProductEntry returnValue = SCProductEntryServiceUtil.addProductEntry(plid,
				name, type, shortDescription, longDescription, pageURL,
				repoGroupId, repoArtifactId, licenseIds, communityPermissions,
				guestPermissions);

		return SCProductEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		SCProductEntryServiceUtil.deleteProductEntry(productEntryId);
	}

	public static JSONObject getProductEntry(long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCProductEntry returnValue = SCProductEntryServiceUtil.getProductEntry(productEntryId);

		return SCProductEntryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateProductEntry(long productEntryId,
		java.lang.String name, java.lang.String type,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.softwarecatalog.model.SCProductEntry returnValue = SCProductEntryServiceUtil.updateProductEntry(productEntryId,
				name, type, shortDescription, longDescription, pageURL,
				repoGroupId, repoArtifactId, licenseIds);

		return SCProductEntryJSONSerializer.toJSONObject(returnValue);
	}
}