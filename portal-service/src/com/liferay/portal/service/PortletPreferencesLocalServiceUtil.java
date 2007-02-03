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

package com.liferay.portal.service;

/**
 * <a href="PortletPreferencesLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesLocalServiceUtil {
	public static void deletePortletPreferences(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();
		portletPreferencesLocalService.deletePortletPreferences(ownerId);
	}

	public static void deletePortletPreferences(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();
		portletPreferencesLocalService.deletePortletPreferences(layoutId,
			ownerId);
	}

	public static void deletePortletPreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();
		portletPreferencesLocalService.deletePortletPreferences(pk);
	}

	public static javax.portlet.PortletPreferences getDefaultPreferences(
		java.lang.String companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getDefaultPreferences(companyId,
			portletId);
	}

	public static java.util.List getPortletPreferences()
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences();
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferences(pk);
	}

	public static java.util.List getPortletPreferencesByLayout(
		java.lang.String layoutId, java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferencesByLayout(layoutId,
			ownerId);
	}

	public static java.util.List getPortletPreferencesByOwnerId(
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferencesByOwnerId(ownerId);
	}

	public static java.util.List getPortletPreferencesByPortletId(
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPortletPreferencesByPortletId(portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		java.lang.String companyId,
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.getPreferences(companyId, pk);
	}

	public static com.liferay.portal.model.PortletPreferences updatePreferences(
		com.liferay.portal.service.persistence.PortletPreferencesPK pk,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		PortletPreferencesLocalService portletPreferencesLocalService = PortletPreferencesLocalServiceFactory.getService();

		return portletPreferencesLocalService.updatePreferences(pk, prefs);
	}
}