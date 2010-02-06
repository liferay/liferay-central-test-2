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

package com.liferay.portal.service;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletPreferencesIds;

import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * <a href="ServiceContextUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class ServiceContextUtil {

	public static Object deserialize(JSONObject jsonObject) {
		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		serviceContext.setCompanyId(jsonObject.getLong("companyId"));
		serviceContext.setLayoutFullURL(jsonObject.getString("layoutFullURL"));
		serviceContext.setLayoutURL(jsonObject.getString("layoutURL"));
		serviceContext.setPathMain(jsonObject.getString("pathMain"));
		serviceContext.setPlid(jsonObject.getLong("plid"));
		serviceContext.setPortalURL(jsonObject.getString("portalURL"));
		serviceContext.setScopeGroupId(jsonObject.getLong("scopeGroupId"));
		serviceContext.setUserDisplayURL(
			jsonObject.getString("userDisplayURL"));

		// Permissions

		String[] communityPermissions = StringUtil.split(
			jsonObject.getString("communityPermissions"));
		String[] guestPermissions = StringUtil.split(
			jsonObject.getString("guestPermissions"));

		serviceContext.setAddCommunityPermissions(
			jsonObject.getBoolean("addCommunityPermissions"));
		serviceContext.setAddGuestPermissions(
			jsonObject.getBoolean("addGuestPermissions"));
		serviceContext.setCommunityPermissions(communityPermissions);
		serviceContext.setGuestPermissions(guestPermissions);

		// Asset

		long[] assetCategoryIds = StringUtil.split(
			jsonObject.getString("assetCategoryIds"), 0L);
		String[] assetTagNames = StringUtil.split(
			jsonObject.getString("assetTagNames"));

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		// Workflow

		serviceContext.setStatus(jsonObject.getInt("status"));

		return serviceContext;
	}

	public static Locale getLocale(ServiceContext serviceContext) {
		return LocaleUtil.fromLanguageId(serviceContext.getLanguageId());
	}

	public static PortletPreferences getPortletPreferences(
			ServiceContext serviceContext)
		throws SystemException {

		if (serviceContext == null) {
			return null;
		}

		PortletPreferencesIds portletPreferencesIds =
			serviceContext.getPortletPreferencesIds();

		if (portletPreferencesIds == null) {
			return null;
		}
		else {
			return PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds.getCompanyId(),
				portletPreferencesIds.getOwnerId(),
				portletPreferencesIds.getOwnerType(),
				portletPreferencesIds.getPlid(),
				portletPreferencesIds.getPortletId());
		}
	}

}