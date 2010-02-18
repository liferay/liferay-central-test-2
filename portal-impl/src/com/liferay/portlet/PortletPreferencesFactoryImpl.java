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
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PreferencesValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="PortletPreferencesFactoryImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesFactoryImpl
	implements PortletPreferencesFactory {

	public PortletPreferences getLayoutPortletSetup(
			Layout layout, String portletId)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, ownerType, layout.getPlid(),
			portletId);
	}

	public PortalPreferences getPortalPreferences(HttpServletRequest request)
		throws SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ownerId = themeDisplay.getUserId();
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		long plid = PortletKeys.PREFS_PLID_SHARED;
		String portletId = PortletKeys.LIFERAY_PORTAL;

		PortalPreferences portalPrefs = null;

		if (themeDisplay.isSignedIn()) {
			PortletPreferencesImpl preferencesImpl = (PortletPreferencesImpl)
				PortletPreferencesLocalServiceUtil.getPreferences(
					themeDisplay.getCompanyId(), ownerId, ownerType, plid,
					portletId);

			portalPrefs = new PortalPreferencesImpl(
				preferencesImpl, themeDisplay.isSignedIn());
		}
		else {
			HttpSession session = request.getSession();

			portalPrefs = (PortalPreferences)session.getAttribute(
				WebKeys.PORTAL_PREFERENCES);

			if (portalPrefs == null) {
				PortletPreferencesImpl preferencesImpl =
					(PortletPreferencesImpl)
						PortletPreferencesLocalServiceUtil.getPreferences(
							themeDisplay.getCompanyId(), ownerId, ownerType,
							plid, portletId);

				preferencesImpl =
					(PortletPreferencesImpl)preferencesImpl.clone();

				portalPrefs = new PortalPreferencesImpl(
					preferencesImpl, themeDisplay.isSignedIn());

				session.setAttribute(WebKeys.PORTAL_PREFERENCES, portalPrefs);
			}
		}

		return portalPrefs;
	}

	public PortalPreferences getPortalPreferences(PortletRequest portletRequest)
		throws SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getPortalPreferences(request);
	}

	public PortletPreferences getPortletPreferences(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		PortletPreferencesIds portletPreferencesIds = getPortletPreferencesIds(
			request, portletId);

		return PortletPreferencesLocalServiceUtil.getPreferences(
			portletPreferencesIds);
	}

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, String portletId)
		throws PortalException, SystemException {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		return getPortletPreferencesIds(request, layout, portletId);
	}

	public PortletPreferencesIds getPortletPreferencesIds(
			HttpServletRequest request, Layout selLayout, String portletId)
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

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		long ownerId = 0;
		int ownerType = 0;
		long plid = 0;

		boolean modeEditGuest = false;

		String portletMode = ParamUtil.getString(request, "p_p_mode");

		if (portletMode.equals(LiferayPortletMode.EDIT_GUEST.toString()) ||
			((layoutTypePortlet != null) &&
			 (layoutTypePortlet.hasModeEditGuestPortletId(portletId)))) {

			modeEditGuest = true;
		}

		if (modeEditGuest) {
			boolean hasUpdateLayoutPermission = LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.UPDATE);

			if (!layout.isPrivateLayout() && hasUpdateLayoutPermission) {
			}
			else {

				// Only users with the correct permissions can update guest
				// preferences

				throw new PrincipalException();
			}
		}

		if (portlet.isPreferencesCompanyWide()) {
			ownerId = themeDisplay.getCompanyId();
			ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
			plid = PortletKeys.PREFS_PLID_SHARED;
			portletId = PortletConstants.getRootPortletId(portletId);
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
				ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
				plid = selLayout.getPlid();

				if (portlet.isPreferencesOwnedByGroup()) {
				}
				else {
					long userId = PortalUtil.getUserId(request);

					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							themeDisplay.getCompanyId());
					}

					ownerId = userId;
					ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				}
			}
			else {
				plid = PortletKeys.PREFS_PLID_SHARED;

				if (portlet.isPreferencesOwnedByGroup()) {
					ownerId = themeDisplay.getScopeGroupId();
					ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
					portletId = PortletConstants.getRootPortletId(portletId);
				}
				else {
					long userId = PortalUtil.getUserId(request);

					if ((userId <= 0) || modeEditGuest) {
						userId = UserLocalServiceUtil.getDefaultUserId(
							themeDisplay.getCompanyId());
					}

					ownerId = userId;
					ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
				}
			}
		}

		return new PortletPreferencesIds(
			themeDisplay.getCompanyId(), ownerId, ownerType, plid, portletId);
	}

	public PortletPreferences getPortletSetup(
			Layout layout, String portletId, String defaultPreferences)
		throws SystemException {

		return getPortletSetup(
			LayoutConstants.DEFAULT_PLID, layout, portletId,
			defaultPreferences);
	}

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId)
		throws SystemException {

		return getPortletSetup(request, portletId, null);
	}

	public PortletPreferences getPortletSetup(
			HttpServletRequest request, String portletId,
			String defaultPreferences)
		throws SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getPortletSetup(
			themeDisplay.getScopeGroupId(), themeDisplay.getLayout(), portletId,
			defaultPreferences);
	}

	public PortletPreferences getPortletSetup(PortletRequest portletRequest)
		throws SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		String portletId = PortalUtil.getPortletId(portletRequest);

		return getPortletSetup(request, portletId);
	}

	public PortletPreferences getPortletSetup(
			PortletRequest portletRequest, String portletId)
		throws SystemException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getPortletSetup(request, portletId);
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences preferences = null;

		if (portletRequest != null) {
			PortletPreferencesWrapper preferencesWrapper =
				(PortletPreferencesWrapper)portletRequest.getPreferences();

			preferences = preferencesWrapper.getPreferencesImpl();
		}

		return preferences;
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			return portletBag.getPreferencesValidatorInstance();
		}
		else {
			PreferencesValidator preferencesValidator = null;

			if (Validator.isNotNull(portlet.getPreferencesValidator())) {
				preferencesValidator =
					(PreferencesValidator)InstancePool.get(
						portlet.getPreferencesValidator());
			}

			return preferencesValidator;
		}
	}

	protected PortletPreferences getPortletSetup(
			long scopeGroupId, Layout layout, String portletId,
			String defaultPreferences)
		throws SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		boolean uniquePerLayout = false;
		boolean uniquePerGroup = false;

		if (portlet.isPreferencesCompanyWide()) {
			portletId = PortletConstants.getRootPortletId(portletId);
		}
		else {
			if (portlet.isPreferencesUniquePerLayout()) {
				uniquePerLayout = true;

				if (portlet.isPreferencesOwnedByGroup()) {
					uniquePerGroup = true;
				}
			}
			else {
				if (portlet.isPreferencesOwnedByGroup()) {
					uniquePerGroup = true;
					portletId = PortletConstants.getRootPortletId(portletId);
				}
			}
		}

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		long plid = layout.getPlid();

		if (!uniquePerLayout) {
			plid = PortletKeys.PREFS_PLID_SHARED;

			if (uniquePerGroup) {
				if (scopeGroupId > LayoutConstants.DEFAULT_PLID) {
					ownerId = scopeGroupId;
				}
				else {
					ownerId = layout.getGroupId();
				}

				ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
			}
			else {
				ownerId = layout.getCompanyId();
				ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
			}
		}

		return PortletPreferencesLocalServiceUtil.getPreferences(
			layout.getCompanyId(), ownerId, ownerType, plid, portletId,
			defaultPreferences);
	}

}