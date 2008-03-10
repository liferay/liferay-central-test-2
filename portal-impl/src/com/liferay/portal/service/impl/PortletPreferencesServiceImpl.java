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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.base.PortletPreferencesServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;

import java.util.Iterator;

import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * <a href="PortletPreferencesServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class PortletPreferencesServiceImpl
	extends PortletPreferencesServiceBaseImpl {

	public void deleteSavedPreferences(long portletItemId)
		throws PortalException, SystemException {

		long ownerId = portletItemId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_SAVED;
		long plid = 0;

		PortletItem portletItem =
			portletItemLocalService.getPortletItem(portletItemId);

		GroupPermissionUtil.check(
			getPermissionChecker(), portletItem.getGroupId(),
			ActionKeys.MANAGE_SAVED_SETUPS);

		portletPreferencesLocalService.deletePortletPreferences(
			ownerId, ownerType, plid, portletItem.getPortletId());

		portletItemLocalService.deletePortletItem(portletItemId);
	}

	public void restoreSavedPreferences(
			long groupId, String portletId, String name,
			javax.portlet.PortletPreferences setup)
		throws PortalException, SystemException {

		PortletItem portletItem = portletItemLocalService.getPortletItem(
			groupId, portletId, PortletPreferences.class.getName(), name);

		long ownerId = portletItem.getPortletItemId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_SAVED;
		long plid = 0;

		javax.portlet.PortletPreferences savedSetup =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletItem.getCompanyId(), ownerId, ownerType, plid,
				portletItem.getPortletId(), StringPool.BLANK);

		copyPreferences(savedSetup, setup);
	}

	public void savePreferences(
			long userId, long groupId, String portletId, String name,
			javax.portlet.PortletPreferences setup)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_SAVED_SETUPS);

		PortletItem portletItem = portletItemLocalService.updatePortletItem(
			userId, groupId, portletId, PortletPreferences.class.getName(),
			name);

		long ownerId = portletItem.getPortletItemId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_SAVED;
		long plid = 0;

		javax.portlet.PortletPreferences savedSetup =
			PortletPreferencesLocalServiceUtil.getPreferences(
			portletItem.getCompanyId(), ownerId, ownerType, plid,
			portletId, StringPool.BLANK);

		copyPreferences(setup, savedSetup);
	}

	protected void copyPreferences(
			javax.portlet.PortletPreferences sourcePreferences,
			javax.portlet.PortletPreferences targetPreferences)
		throws SystemException {

		try {
			// Clear previous setup

			Iterator itr = targetPreferences.getMap().keySet().iterator();

			while (itr.hasNext()) {
				String key = (String) itr.next();

				targetPreferences.reset(key);
			}

			// Copy current setup

			itr = sourcePreferences.getMap().keySet().iterator();

			while (itr.hasNext()) {
				String key = (String) itr.next();

				targetPreferences.setValues(
					key, sourcePreferences.getValues(key, null));
			}

			targetPreferences.store();
		}
		catch (ReadOnlyException roe) {
		}
		catch (ValidatorException ve) {
			throw new SystemException(ve);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}