/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PortletPreferencesLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PortletPreferencesLocalService
 *
 */
public class PortletPreferencesLocalServiceUtil {
	public static com.liferay.portal.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		return _service.addPortletPreferences(portletPreferences);
	}

	public static void deletePortletPreferences(long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deletePortletPreferences(portletPreferencesId);
	}

	public static void deletePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		_service.deletePortletPreferences(portletPreferences);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPortletPreferences(portletPreferencesId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencess(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getPortletPreferencess(start, end);
	}

	public static int getPortletPreferencessCount()
		throws com.liferay.portal.SystemException {
		return _service.getPortletPreferencessCount();
	}

	public static com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		return _service.updatePortletPreferences(portletPreferences);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid) throws com.liferay.portal.SystemException {
		_service.deletePortletPreferences(ownerId, ownerType, plid);
	}

	public static void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deletePortletPreferences(ownerId, ownerType, plid, portletId);
	}

	public static javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getDefaultPreferences(companyId, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences()
		throws com.liferay.portal.SystemException {
		return _service.getPortletPreferences();
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return _service.getPortletPreferences(plid, portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.SystemException {
		return _service.getPortletPreferences(ownerId, ownerType, plid);
	}

	public static com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPortletPreferences(ownerId, ownerType, plid,
			portletId);
	}

	public static java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) throws com.liferay.portal.SystemException {
		return _service.getPortletPreferencesByPlid(plid);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPreferences(portletPreferencesIds);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPreferences(companyId, ownerId, ownerType, plid,
			portletId);
	}

	public static javax.portlet.PortletPreferences getPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, java.lang.String defaultPreferences)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getPreferences(companyId, ownerId, ownerType, plid,
			portletId, defaultPreferences);
	}

	public static com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences prefs)
		throws com.liferay.portal.SystemException {
		return _service.updatePreferences(ownerId, ownerType, plid, portletId,
			prefs);
	}

	public static PortletPreferencesLocalService getService() {
		return _service;
	}

	public void setService(PortletPreferencesLocalService service) {
		_service = service;
	}

	private static PortletPreferencesLocalService _service;
}