/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.SystemException;

import javax.portlet.PortletPreferences;

/**
 * @author     Brian Wing Shun Chan
 * @author     Jon Steer
 * @author     Zongliang Li
 * @author     Shuyang Zhou
 * @deprecated {@link PortletPreferencesFactoryUtil}
 */
public class PortletPreferencesSerializer {

	public static PortletPreferences fromDefaultXML(String xml)
		throws SystemException {

		return PortletPreferencesFactoryUtil.fromDefaultXML(xml);
	}

	public static PortletPreferencesImpl fromXML(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws SystemException {

		return (PortletPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
			companyId, ownerId, ownerType, plid, portletId, xml);
	}

	public static String toXML(PortletPreferencesImpl portletPreferencesImpl) {
		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

}