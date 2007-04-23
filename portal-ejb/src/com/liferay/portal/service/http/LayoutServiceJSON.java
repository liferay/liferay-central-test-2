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

import com.liferay.portal.service.LayoutServiceUtil;

import org.json.JSONObject;

/**
 * <a href="LayoutServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the <code>com.liferay.portal.service.LayoutServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is difficult
 * for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>, that
 * is translated to a <code>org.json.JSONArray</code>. If the method in the service
 * utility returns a <code>com.liferay.portal.model.Layout</code>, that is translated
 * to a <code>org.json.JSONObject</code>. Methods that JSON cannot safely use are
 * skipped. The logic for the translation is encapsulated in <code>com.liferay.portal.service.http.LayoutJSONSerializer</code>.
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
 * @see com.liferay.portal.service.LayoutServiceUtil
 * @see com.liferay.portal.service.http.LayoutJSONSerializer
 *
 */
public class LayoutServiceJSON {
	public static JSONObject addLayout(long groupId, boolean privateLayout,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.addLayout(groupId,
				privateLayout, parentLayoutId, name, title, type, hidden,
				friendlyURL);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteLayout(java.lang.String layoutId,
		java.lang.String ownerId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		LayoutServiceUtil.deleteLayout(layoutId, ownerId);
	}

	public static java.lang.String getLayoutName(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		java.lang.String returnValue = LayoutServiceUtil.getLayoutName(layoutId,
				ownerId, languageId);

		return returnValue;
	}

	public static com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		java.lang.String companyId, java.lang.String portletId,
		java.lang.String prefsKey, java.lang.String prefsValue)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		com.liferay.portal.model.LayoutReference[] returnValue = LayoutServiceUtil.getLayoutReferences(companyId,
				portletId, prefsKey, prefsValue);

		return returnValue;
	}

	public static void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		LayoutServiceUtil.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	public static JSONObject updateLayout(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(layoutId,
				ownerId, parentLayoutId, name, title, languageId, type, hidden,
				friendlyURL);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateLayout(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String parentLayoutId,
		java.lang.String name, java.lang.String title,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(layoutId,
				ownerId, parentLayoutId, name, title, languageId, type, hidden,
				friendlyURL, iconImage, iconBytes);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateLayout(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String typeSettings)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(layoutId,
				ownerId, typeSettings);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateLookAndFeel(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLookAndFeel(layoutId,
				ownerId, themeId, colorSchemeId, css);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateName(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateName(layoutId,
				ownerId, name, languageId);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updateParentLayoutId(java.lang.String layoutId,
		java.lang.String ownerId, java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateParentLayoutId(layoutId,
				ownerId, parentLayoutId);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject updatePriority(java.lang.String layoutId,
		java.lang.String ownerId, int priority)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updatePriority(layoutId,
				ownerId, priority);

		return LayoutJSONSerializer.toJSONObject(returnValue);
	}
}