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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="PortletPreferencesFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface PortletPreferencesFactory {

	public PortletPreferences getLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException;

	public PortalPreferences getPortalPreferences(HttpServletRequest request)
		throws SystemException;

	public PortalPreferences getPortalPreferences(PortletRequest portletRequest)
		throws SystemException;

	public PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException;

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout selLayout, String portletId)
		throws PortalException, SystemException;

	public PortletPreferences getPortletSetup(
			Layout layout, String portletId, String defaultPreferences)
		throws SystemException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws SystemException;

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
			String defaultPreferences)
		throws SystemException;

	public PortletPreferences getPortletSetup(PortletRequest portletRequest)
		throws SystemException;

	public PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws SystemException;

	public PortletPreferences getPreferences(HttpServletRequest request);

	public PreferencesValidator getPreferencesValidator(Portlet portlet);

}