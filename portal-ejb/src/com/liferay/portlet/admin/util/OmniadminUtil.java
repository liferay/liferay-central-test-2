/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.admin.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OmniadminUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OmniadminUtil {

	public static String getAutoDeployDeployDir()
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		String deployDir = PropsUtil.get(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR);

		return prefs.getValue(PropsUtil.AUTO_DEPLOY_DEPLOY_DIR, deployDir);
	}

	public static String getAutoDeployDestDir()
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		String destDir = PropsUtil.get(PropsUtil.AUTO_DEPLOY_DEST_DIR);

		return prefs.getValue(PropsUtil.AUTO_DEPLOY_DEST_DIR, destDir);
	}

	public static long getAutoDeployInterval()
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		String interval = PropsUtil.get(PropsUtil.AUTO_DEPLOY_INTERVAL);

		return GetterUtil.getLong(
			prefs.getValue(PropsUtil.AUTO_DEPLOY_INTERVAL, interval));
	}

	public static String getAutoDeployTomcatLibDir()
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		String tomcatLibDir = PropsUtil.get(
			PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR);

		return prefs.getValue(
			PropsUtil.AUTO_DEPLOY_TOMCAT_LIB_DIR, tomcatLibDir);
	}

	public static boolean getAutoDeployUnpackWar()
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		String unpackWar = PropsUtil.get(PropsUtil.AUTO_DEPLOY_UNPACK_WAR);

		return GetterUtil.getBoolean(
			prefs.getValue(PropsUtil.AUTO_DEPLOY_UNPACK_WAR, unpackWar));
	}

	public static PortletPreferences getPreferences()
		throws PortalException, SystemException {

		String companyId = PortletKeys.LIFERAY_PORTAL;

		PortletPreferencesPK prefsPK = new PortletPreferencesPK(
			PortletKeys.LIFERAY_PORTAL, PortletKeys.PREFS_LAYOUT_ID_SHARED,
			PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD + companyId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, prefsPK);
	}

	public static boolean isOmniadmin(String userId) {
		if (userId == null) {
			return false;
		}

		String[] omniAdminUsers = PropsUtil.getArray(PropsUtil.OMNIADMIN_USERS);

		if (omniAdminUsers.length > 0) {
			for (int i = 0; i < omniAdminUsers.length; i++) {
				if (omniAdminUsers[i].equals(userId)) {
					return true;
				}
			}

			return false;
		}
		else {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				return RoleLocalServiceUtil.hasUserRole(
					userId, user.getCompanyId(), Role.ADMINISTRATOR);
			}
			catch (Exception e) {
				_log.error(e);

				return false;
			}
		}
	}

	private static Log _log = LogFactory.getLog(OmniadminUtil.class);

}