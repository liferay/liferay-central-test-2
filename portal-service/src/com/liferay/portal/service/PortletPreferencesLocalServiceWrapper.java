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


/**
 * <a href="PortletPreferencesLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletPreferencesLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesLocalService
 * @generated
 */
public class PortletPreferencesLocalServiceWrapper
	implements PortletPreferencesLocalService {
	public PortletPreferencesLocalServiceWrapper(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		_portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public com.liferay.portal.model.PortletPreferences addPortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.addPortletPreferences(portletPreferences);
	}

	public com.liferay.portal.model.PortletPreferences createPortletPreferences(
		long portletPreferencesId) {
		return _portletPreferencesLocalService.createPortletPreferences(portletPreferencesId);
	}

	public void deletePortletPreferences(long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_portletPreferencesLocalService.deletePortletPreferences(portletPreferencesId);
	}

	public void deletePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		_portletPreferencesLocalService.deletePortletPreferences(portletPreferences);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long portletPreferencesId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferences(portletPreferencesId);
	}

	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferenceses(
		int start, int end) throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferenceses(start,
			end);
	}

	public int getPortletPreferencesesCount()
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferencesesCount();
	}

	public com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.updatePortletPreferences(portletPreferences);
	}

	public com.liferay.portal.model.PortletPreferences updatePortletPreferences(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge) throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.updatePortletPreferences(portletPreferences,
			merge);
	}

	public com.liferay.portal.model.PortletPreferences addPortletPreferences(
		long companyId, long ownerId, int ownerType, long plid,
		java.lang.String portletId, com.liferay.portal.model.Portlet portlet,
		java.lang.String defaultPreferences)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.addPortletPreferences(companyId,
			ownerId, ownerType, plid, portletId, portlet, defaultPreferences);
	}

	public void deletePortletPreferences(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.SystemException {
		_portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid);
	}

	public void deletePortletPreferences(long ownerId, int ownerType,
		long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_portletPreferencesLocalService.deletePortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public javax.portlet.PortletPreferences getDefaultPreferences(
		long companyId, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getDefaultPreferences(companyId,
			portletId);
	}

	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences()
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferences();
	}

	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferences(plid,
			portletId);
	}

	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferences(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid);
	}

	public com.liferay.portal.model.PortletPreferences getPortletPreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferences(ownerId,
			ownerType, plid, portletId);
	}

	public java.util.List<com.liferay.portal.model.PortletPreferences> getPortletPreferencesByPlid(
		long plid) throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPortletPreferencesByPlid(plid);
	}

	public javax.portlet.PortletPreferences getPreferences(
		com.liferay.portal.model.PortletPreferencesIds portletPreferencesIds)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPreferences(portletPreferencesIds);
	}

	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId);
	}

	public javax.portlet.PortletPreferences getPreferences(long companyId,
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String defaultPreferences)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.getPreferences(companyId,
			ownerId, ownerType, plid, portletId, defaultPreferences);
	}

	public com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.updatePreferences(ownerId,
			ownerType, plid, portletId, preferences);
	}

	public com.liferay.portal.model.PortletPreferences updatePreferences(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		java.lang.String xml) throws com.liferay.portal.SystemException {
		return _portletPreferencesLocalService.updatePreferences(ownerId,
			ownerType, plid, portletId, xml);
	}

	public PortletPreferencesLocalService getWrappedPortletPreferencesLocalService() {
		return _portletPreferencesLocalService;
	}

	private PortletPreferencesLocalService _portletPreferencesLocalService;
}