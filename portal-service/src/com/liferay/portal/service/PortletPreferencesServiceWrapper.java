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
 * <a href="PortletPreferencesServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletPreferencesService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesService
 * @generated
 */
public class PortletPreferencesServiceWrapper
	implements PortletPreferencesService {
	public PortletPreferencesServiceWrapper(
		PortletPreferencesService portletPreferencesService) {
		_portletPreferencesService = portletPreferencesService;
	}

	public void deleteArchivedPreferences(long portletItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletPreferencesService.deleteArchivedPreferences(portletItemId);
	}

	public void restoreArchivedPreferences(long groupId, java.lang.String name,
		java.lang.String portletId, javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletPreferencesService.restoreArchivedPreferences(groupId, name,
			portletId, preferences);
	}

	public void updateArchivePreferences(long userId, long groupId,
		java.lang.String name, java.lang.String portletId,
		javax.portlet.PortletPreferences preferences)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portletPreferencesService.updateArchivePreferences(userId, groupId,
			name, portletId, preferences);
	}

	public PortletPreferencesService getWrappedPortletPreferencesService() {
		return _portletPreferencesService;
	}

	private PortletPreferencesService _portletPreferencesService;
}