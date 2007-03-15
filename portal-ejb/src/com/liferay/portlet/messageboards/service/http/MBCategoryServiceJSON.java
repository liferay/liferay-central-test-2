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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;

import org.json.JSONObject;

/**
 * <a href="MBCategoryServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the <code>com.liferay.portlet.messageboards.service.MBCategoryServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is difficult
 * for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>, that
 * is translated to a <code>org.json.JSONArray</code>. If the method in the service
 * utility returns a <code>com.liferay.portlet.messageboards.model.MBCategory</code>,
 * that is translated to a <code>org.json.JSONObject</code>. Methods that JSON cannot
 * safely use are skipped. The logic for the translation is encapsulated in <code>com.liferay.portlet.messageboards.service.http.MBCategoryJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript. See
 * <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a reference
 * of how that portlet uses the generated JavaScript in <code>portal-web/docroot/html/js/service.js</code>
 * to call the backend services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBCategoryServiceUtil
 * @see com.liferay.portlet.messageboards.service.http.MBCategoryJSONSerializer
 *
 */
public class MBCategoryServiceJSON {
	public static JSONObject addCategory(java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.addCategory(plid,
				parentCategoryId, name, description, addCommunityPermissions,
				addGuestPermissions);

		return MBCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addCategory(java.lang.String plid,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.addCategory(plid,
				parentCategoryId, name, description, communityPermissions,
				guestPermissions);

		return MBCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		MBCategoryServiceUtil.deleteCategory(categoryId);
	}

	public static JSONObject getCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.getCategory(categoryId);

		return MBCategoryJSONSerializer.toJSONObject(returnValue);
	}

	public static void subscribeCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		MBCategoryServiceUtil.subscribeCategory(categoryId);
	}

	public static void unsubscribeCategory(java.lang.String categoryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		MBCategoryServiceUtil.unsubscribeCategory(categoryId);
	}

	public static JSONObject updateCategory(java.lang.String categoryId,
		java.lang.String parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.messageboards.model.MBCategory returnValue = MBCategoryServiceUtil.updateCategory(categoryId,
				parentCategoryId, name, description, mergeWithParentCategory);

		return MBCategoryJSONSerializer.toJSONObject(returnValue);
	}
}