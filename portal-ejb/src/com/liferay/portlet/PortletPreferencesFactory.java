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

package com.liferay.portlet;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.PortletLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesServiceUtil;
import com.liferay.portal.servlet.PortletContextPool;
import com.liferay.portal.servlet.PortletContextWrapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.InstancePool;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;
import com.liferay.util.portlet.RenderRequestWrapper;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletPreferencesFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesFactory {

	public static PortalPreferences getPortalPreferences(HttpServletRequest req)
		throws PortalException, SystemException {

		PortalPreferences portalPrefs = null;

		String portletId = PortletKeys.PORTAL;
		String layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		PortletPreferencesPK pk = new PortletPreferencesPK(
			portletId, layoutId, themeDisplay.getUserId());

		if (themeDisplay.isSignedIn()) {
			PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)
				PortletPreferencesServiceUtil.getPreferences(
					themeDisplay.getCompanyId(), pk);

			portalPrefs = new PortalPreferences(
				prefsImpl, themeDisplay.isSignedIn());
		}
		else {
			HttpSession ses = req.getSession();

			portalPrefs =
				(PortalPreferences)ses.getAttribute(WebKeys.PORTAL_PREFERENCES);

			if (portalPrefs == null) {
				PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)
					PortletPreferencesServiceUtil.getPreferences(
						themeDisplay.getCompanyId(), pk);

				prefsImpl = (PortletPreferencesImpl)prefsImpl.clone();

				portalPrefs = new PortalPreferences(
					prefsImpl, themeDisplay.isSignedIn());

				ses.setAttribute(WebKeys.PORTAL_PREFERENCES, portalPrefs);
			}
		}

		return portalPrefs;
	}

	public static PortalPreferences getPortalPreferences(ActionRequest req)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getPortalPreferences(reqImpl.getHttpServletRequest());
	}

	public static PortalPreferences getPortalPreferences(RenderRequest req)
		throws PortalException, SystemException {

		// FIX ME, the logic for getting the HTTP servlet request should be
		// abstracted out

		RenderRequestImpl reqImpl = null;

		if (req instanceof RenderRequestWrapper) {
			RenderRequestWrapper reqWrapper = (RenderRequestWrapper)req;

			return getPortalPreferences(
				(RenderRequest)reqWrapper.getPortletRequest());
		}
		else {
			reqImpl = (RenderRequestImpl)req;
		}

		return getPortalPreferences(reqImpl.getHttpServletRequest());
	}

	public static PortletPreferences getPortletPreferences(
			HttpServletRequest req, String portletId)
		throws PortalException, SystemException {

		String companyId = PortalUtil.getCompanyId(req);

		return PortletPreferencesServiceUtil.getPreferences(
			companyId, getPortletPreferencesPK(req, portletId));
	}

	public static PortletPreferencesPK getPortletPreferencesPK(
			HttpServletRequest req, String portletId)
		throws SystemException {

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		return getPortletPreferencesPK(req, portletId, layout.getPlid());
	}

	public static PortletPreferencesPK getPortletPreferencesPK(
			HttpServletRequest req, String portletId, String selPlid)
		throws SystemException {

		// Below is a list of  the possible combinations, where we specify the
		// portlet id, the layout id, the owner id, and the function.

		// PORTAL, SHARED, liferay.com.1, preference is scoped per user across
		// the entire portal

		// 56_INSTANCE_abcd, SHARED, COMPANY.liferay.com, preference is scoped
		// per portlet and company and is shared across all layouts

		// 56_INSTANCE_abcd, SHARED, GROUP.10, preference is scoped per portlet
		// and group and is shared across all layouts

		// 56_INSTANCE_abcd, SHARED, USER.liferay.com.1, preference is scoped
		// per portlet and user and is shared across all layouts

		// 56_INSTANCE_abcd, 3, PUB.10, preference is scoped per portlet, group,
		// and layout

		// 56_INSTANCE_abcd, 3, PUB.10.USER.liferay.com.1, preference is scoped
		// per portlet, user, and layout

		String companyId = PortalUtil.getCompanyId(req);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		String layoutId = null;
		String ownerId = null;

		if (portlet.isPreferencesCompanyWide()) {
			layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;
			ownerId =
				PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD +
					companyId;
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				layoutId = Layout.getLayoutId(selPlid);
				ownerId = Layout.getOwnerId(selPlid);

				if (portlet.isPreferencesOwnedByGroup()) {
				}
				else {
					String userId = PortalUtil.getUserId(req);

					if (userId == null) {
						userId = User.getDefaultUserId(companyId);
					}

					ownerId +=
						StringPool.PERIOD + PortletKeys.PREFS_OWNER_ID_USER +
							StringPool.PERIOD + userId;
				}
			}
			else {
				layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;
				ownerId = Layout.getOwnerId(selPlid);

				if (portlet.isPreferencesOwnedByGroup()) {
					ownerId =
						PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
							Layout.getGroupId(ownerId);
				}
				else {
					String userId = PortalUtil.getUserId(req);

					if (userId == null) {
						userId = User.getDefaultUserId(companyId);
					}

					ownerId =
						PortletKeys.PREFS_OWNER_ID_USER + StringPool.PERIOD +
							userId;
				}
			}
		}

		return new PortletPreferencesPK(portletId, layoutId, ownerId);
	}

	public static PortletPreferences getPortletSetup(
			HttpServletRequest req, String portletId, boolean uniquePerLayout,
			boolean uniquePerGroup)
		throws PortalException, SystemException {

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String layoutId = layout.getLayoutId();
		String ownerId = layout.getOwnerId();

		if (!uniquePerLayout) {
			layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;

			if (uniquePerGroup) {
				ownerId =
					PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
						Layout.getGroupId(ownerId);
			}
			else {
				ownerId =
					PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD +
						layout.getCompanyId();
			}
		}

		PortletPreferencesPK pk = new PortletPreferencesPK(
			portletId, layoutId, ownerId);

		return PortletPreferencesServiceUtil.getPreferences(
			layout.getCompanyId(), pk);
	}

	public static PortletPreferences getPortletSetup(
			ActionRequest req, String portletId, boolean uniquePerLayout,
			boolean uniquePerGroup)
		throws PortalException, SystemException {

		ActionRequestImpl reqImpl = (ActionRequestImpl)req;

		return getPortletSetup(
			reqImpl.getHttpServletRequest(), portletId, uniquePerLayout,
			uniquePerGroup);
	}

	public static PortletPreferences getPortletSetup(
			RenderRequest req, String portletId, boolean uniquePerLayout,
			boolean uniquePerGroup)
		throws PortalException, SystemException {

		RenderRequestImpl reqImpl = (RenderRequestImpl)req;

		return getPortletSetup(
			reqImpl.getHttpServletRequest(), portletId, uniquePerLayout,
			uniquePerGroup);
	}

	public static PortletPreferences getPreferences(HttpServletRequest req) {
		RenderRequest renderRequest =
			(RenderRequest)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

		PortletPreferences prefs = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper prefsWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			prefs = prefsWrapper.getPreferencesImpl();
		}

		return prefs;
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		if (portlet.isWARFile()) {
			PortletContextWrapper pcw =
				PortletContextPool.get(portlet.getRootPortletId());

			return pcw.getPreferencesValidator();
		}
		else {
			PreferencesValidator prefsValidator = null;

			if (Validator.isNotNull(portlet.getPreferencesValidator())) {
				prefsValidator =
					(PreferencesValidator)InstancePool.get(
						portlet.getPreferencesValidator());
			}

			return prefsValidator;
		}
	}

	private static Log _log =
		LogFactory.getLog(PortletPreferencesFactory.class);

}