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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.security.permission.ActionKeys;
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
 */
public class PortletPreferencesServiceImpl
	extends PortletPreferencesServiceBaseImpl {

	public void deleteArchivedPreferences(long portletItemId)
		throws PortalException, SystemException {

		PortletItem portletItem = portletItemLocalService.getPortletItem(
			portletItemId);

		GroupPermissionUtil.check(
			getPermissionChecker(), portletItem.getGroupId(),
			ActionKeys.MANAGE_ARCHIVED_SETUPS);

		long ownerId = portletItemId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;
		String portletId = portletItem.getPortletId();

		portletPreferencesLocalService.deletePortletPreferences(
			ownerId, ownerType, plid, portletId);

		portletItemLocalService.deletePortletItem(portletItemId);
	}

	public void restoreArchivedPreferences(
			long groupId, String name, String portletId,
			javax.portlet.PortletPreferences preferences)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_ARCHIVED_SETUPS);

		PortletItem portletItem = portletItemLocalService.getPortletItem(
			groupId, name, portletId, PortletPreferences.class.getName());

		long ownerId = portletItem.getPortletItemId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;

		javax.portlet.PortletPreferences archivedPrefs =
			portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), ownerId, ownerType, plid,
				portletId);

		copyPreferences(archivedPrefs, preferences);
	}

	public void updateArchivePreferences(
			long userId, long groupId, String name, String portletId,
			javax.portlet.PortletPreferences preferences)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_ARCHIVED_SETUPS);

		PortletItem portletItem = portletItemLocalService.updatePortletItem(
			userId, groupId, name, portletId,
			PortletPreferences.class.getName());

		long ownerId = portletItem.getPortletItemId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;
		long plid = 0;

		javax.portlet.PortletPreferences archivedPrefs =
			portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), ownerId, ownerType, plid,
				portletId);

		copyPreferences(preferences, archivedPrefs);
	}

	protected void copyPreferences(
			javax.portlet.PortletPreferences sourcePreferences,
			javax.portlet.PortletPreferences targetPreferences)
		throws SystemException {

		try {
			Iterator<String> itr =
				targetPreferences.getMap().keySet().iterator();

			while (itr.hasNext()) {
				try {
					String key = itr.next();

					targetPreferences.reset(key);
				}
				catch (ReadOnlyException roe) {
				}
			}

			itr = sourcePreferences.getMap().keySet().iterator();

			while (itr.hasNext()) {
				try {
					String key = itr.next();

					targetPreferences.setValues(
						key, sourcePreferences.getValues(key, new String[0]));
				}
				catch (ReadOnlyException roe) {
				}
			}

			targetPreferences.store();
		}
		catch (IOException ioe) {
			_log.error(ioe);
		}
		catch (ValidatorException ve) {
			throw new SystemException(ve);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortletPreferencesServiceImpl.class);

}