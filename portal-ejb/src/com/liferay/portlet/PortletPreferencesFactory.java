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

package com.liferay.portlet;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.PortletContextPool;
import com.liferay.portal.servlet.PortletContextWrapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.portlet.RenderRequestWrapper;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="PortletPreferencesFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesFactory {

	public static PortalPreferences getPortalPreferences(HttpServletRequest req)
		throws PortalException, SystemException {

		PortalPreferences portalPrefs = null;

		String portletId = PortletKeys.LIFERAY_PORTAL;
		String layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		String ownerId = String.valueOf(themeDisplay.getUserId());

		if (themeDisplay.isSignedIn()) {
			PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)
				PortletPreferencesLocalServiceUtil.getPreferences(
					themeDisplay.getCompanyId(), ownerId, layoutId, portletId);

			portalPrefs = new PortalPreferences(
				prefsImpl, themeDisplay.isSignedIn());
		}
		else {
			HttpSession ses = req.getSession();

			portalPrefs =
				(PortalPreferences)ses.getAttribute(WebKeys.PORTAL_PREFERENCES);

			if (portalPrefs == null) {
				PortletPreferencesImpl prefsImpl = (PortletPreferencesImpl)
					PortletPreferencesLocalServiceUtil.getPreferences(
						themeDisplay.getCompanyId(), ownerId, layoutId,
						portletId);

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

		long companyId = PortalUtil.getCompanyId(req);

		String[] portletPreferencesIds = getPortletPreferencesIds(
			req, portletId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, portletPreferencesIds[0], portletPreferencesIds[1],
			portletPreferencesIds[2]);
	}

	public static String[] getPortletPreferencesIds(
			HttpServletRequest req, String portletId)
		throws PortalException, SystemException {

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		return getPortletPreferencesIds(req, layout.getPlid(), portletId);
	}

	public static String[] getPortletPreferencesIds(
			HttpServletRequest req, String selPlid, String portletId)
		throws PortalException, SystemException {

		// Below is a list of  the possible combinations, where we specify the
		// the owner id, the layout id, portlet id, and the function.

		// liferay.com.1, SHARED, PORTAL, preference is scoped per user across
		// the entire portal

		// COMPANY.liferay.com, SHARED, 56_INSTANCE_abcd, preference is scoped
		// per portlet and company and is shared across all layouts

		// GROUP.10, SHARED, 56_INSTANCE_abcd, preference is scoped per portlet
		// and group and is shared across all layouts

		// USER.liferay.com.1, SHARED, 56_INSTANCE_abcd, preference is scoped
		// per portlet and user and is shared across all layouts

		// PUB.10, 3, 56_INSTANCE_abcd, preference is scoped per portlet, group,
		// and layout

		// PUB.10.USER.liferay.com.1, 3, 56_INSTANCE_abcd, preference is scoped
		// per portlet, user, and layout

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		String ownerId = null;
		String layoutId = null;

		boolean modeEditGuest = false;

		String portletMode = ParamUtil.getString(req, "p_p_mode");

		if (portletMode.equals(LiferayPortletMode.EDIT_GUEST.toString()) ||
			layoutTypePortlet.hasModeEditGuestPortletId(portletId)) {

			modeEditGuest = true;
		}

		if (modeEditGuest) {
			if (!layout.isPrivateLayout() &&
				themeDisplay.isShowAddContentIcon()) {

			}
			else {

				// Only users with the correct permissions can update guest
				// preferences

				throw new PrincipalException();
			}
		}

		if (portlet.isPreferencesCompanyWide()) {
			ownerId =
				PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD +
					themeDisplay.getCompanyId();
			layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				ownerId = LayoutImpl.getOwnerId(selPlid);
				layoutId = LayoutImpl.getLayoutId(selPlid);

				if (portlet.isPreferencesOwnedByGroup()) {
				}
				else {
					long userId = PortalUtil.getUserId(req);

					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							themeDisplay.getCompanyId());
					}

					ownerId +=
						StringPool.PERIOD + PortletKeys.PREFS_OWNER_ID_USER +
							StringPool.PERIOD + userId;
				}
			}
			else {
				ownerId = LayoutImpl.getOwnerId(selPlid);
				layoutId = PortletKeys.PREFS_LAYOUT_ID_SHARED;

				if (portlet.isPreferencesOwnedByGroup()) {
					ownerId =
						PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
							LayoutImpl.getGroupId(ownerId);
				}
				else {
					long userId = PortalUtil.getUserId(req);

					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							themeDisplay.getCompanyId());
					}

					ownerId =
						PortletKeys.PREFS_OWNER_ID_USER + StringPool.PERIOD +
							userId;
				}
			}
		}

		return new String[] {ownerId, layoutId, portletId};
	}

	public static PortletPreferences getPortletSetup(
			String portletId, String layoutId, String ownerId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, layoutId, portletId);
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
						LayoutImpl.getGroupId(ownerId);
			}
			else {
				ownerId =
					PortletKeys.PREFS_OWNER_ID_COMPANY + StringPool.PERIOD +
						layout.getCompanyId();
			}
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, layoutId, portletId);
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

}