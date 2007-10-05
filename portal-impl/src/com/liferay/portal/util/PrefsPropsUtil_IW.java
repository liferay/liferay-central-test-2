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

/**
 * <a href="PrefsPropsUtil_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PrefsPropsUtil_IW {
	public static PrefsPropsUtil_IW getInstance() {
		return _instance;
	}

	public javax.portlet.PortletPreferences getPreferences()
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getPreferences();
	}

	public javax.portlet.PortletPreferences getPreferences(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getPreferences(companyId);
	}

	public boolean getBoolean(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getBoolean(name);
	}

	public boolean getBoolean(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getBoolean(companyId, name);
	}

	public boolean getBoolean(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getBoolean(prefs, companyId, name);
	}

	public java.lang.String getContent(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getContent(name);
	}

	public java.lang.String getContent(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getContent(companyId, name);
	}

	public java.lang.String getContent(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getContent(prefs, companyId, name);
	}

	public double getDouble(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getDouble(name);
	}

	public double getDouble(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getDouble(companyId, name);
	}

	public double getDouble(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getDouble(prefs, companyId, name);
	}

	public int getInteger(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getInteger(name);
	}

	public int getInteger(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getInteger(companyId, name);
	}

	public int getInteger(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getInteger(prefs, companyId, name);
	}

	public long getLong(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getLong(name);
	}

	public long getLong(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getLong(companyId, name);
	}

	public long getLong(javax.portlet.PortletPreferences prefs, long companyId,
		java.lang.String name) {
		return PrefsPropsUtil.getLong(prefs, companyId, name);
	}

	public short getShort(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getShort(name);
	}

	public short getShort(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getShort(companyId, name);
	}

	public short getShort(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getShort(prefs, companyId, name);
	}

	public java.lang.String getString(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getString(name);
	}

	public java.lang.String getString(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getString(companyId, name);
	}

	public java.lang.String getString(javax.portlet.PortletPreferences prefs,
		long companyId, java.lang.String name) {
		return PrefsPropsUtil.getString(prefs, companyId, name);
	}

	public java.lang.String[] getStringArray(java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getStringArray(name);
	}

	public java.lang.String[] getStringArray(long companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getStringArray(companyId, name);
	}

	public java.lang.String[] getStringArray(
		javax.portlet.PortletPreferences prefs, long companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		return PrefsPropsUtil.getStringArray(prefs, companyId, name);
	}

	private PrefsPropsUtil_IW() {
	}

	private static PrefsPropsUtil_IW _instance = new PrefsPropsUtil_IW();
}