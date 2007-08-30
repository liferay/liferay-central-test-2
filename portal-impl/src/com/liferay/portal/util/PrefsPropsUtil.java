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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * <a href="PrefsPropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PrefsPropsUtil {

	public static PortletPreferences getPreferences()
		throws PortalException, SystemException {

		return getPreferences(0);
	}

	public static PortletPreferences getPreferences(long companyId)
		throws PortalException, SystemException {

		long ownerId = companyId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		long plid = PortletKeys.PREFS_PLID_SHARED;
		String portletId = PortletKeys.LIFERAY_PORTAL;

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, ownerId, ownerType, plid, portletId);
	}

	public static boolean getBoolean(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getBoolean(prefs, 0, name);
	}

	public static boolean getBoolean(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getBoolean(prefs, companyId, name);
	}

	public static boolean getBoolean(
		PortletPreferences prefs, long companyId, String name) {

		return GetterUtil.getBoolean(getString(prefs, companyId, name));
	}

	public static String getContent(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getContent(prefs, 0, name);
	}

	public static String getContent(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getContent(prefs, companyId, name);
	}

	public static String getContent(
		PortletPreferences prefs, long companyId, String name) {

		String value = prefs.getValue(name, StringPool.BLANK);

		if (Validator.isNotNull(value)) {
			return value;
		}
		else {
			return ContentUtil.get(PropsUtil.get(name));
		}
	}

	public static double getDouble(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getDouble(prefs, 0, name);
	}

	public static double getDouble(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getDouble(prefs, companyId, name);
	}

	public static double getDouble(
		PortletPreferences prefs, long companyId, String name) {

		return GetterUtil.getDouble(getString(prefs, companyId, name));
	}

	public static int getInteger(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getInteger(prefs, 0, name);
	}

	public static int getInteger(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getInteger(prefs, companyId, name);
	}

	public static int getInteger(
		PortletPreferences prefs, long companyId, String name) {

		return GetterUtil.getInteger(getString(prefs, companyId, name));
	}

	public static long getLong(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getLong(prefs, 0, name);
	}

	public static long getLong(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getLong(prefs, companyId, name);
	}

	public static long getLong(
		PortletPreferences prefs, long companyId, String name) {

		return GetterUtil.getLong(getString(prefs, companyId, name));
	}

	public static short getShort(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getShort(prefs, 0, name);
	}

	public static short getShort(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getShort(prefs, companyId, name);
	}

	public static short getShort(
		PortletPreferences prefs, long companyId, String name) {

		return GetterUtil.getShort(getString(prefs, companyId, name));
	}

	public static String getString(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getString(prefs, 0, name);
	}

	public static String getString(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getString(prefs, companyId, name);
	}

	public static String getString(
		PortletPreferences prefs, long companyId, String name) {

		String value = PropsUtil.get(name);

		return prefs.getValue(name, value);
	}

	public static String[] getStringArray(String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences();

		return getStringArray(prefs, 0, name);
	}

	public static String[] getStringArray(long companyId, String name)
		throws PortalException, SystemException {

		PortletPreferences prefs = getPreferences(companyId);

		return getStringArray(prefs, companyId, name);
	}

	public static String[] getStringArray(
			PortletPreferences prefs, long companyId, String name)
		throws PortalException, SystemException {

		String value = PropsUtil.get(name);

		return StringUtil.split(
			prefs.getValue(name, value), StringPool.NEW_LINE);
	}

}