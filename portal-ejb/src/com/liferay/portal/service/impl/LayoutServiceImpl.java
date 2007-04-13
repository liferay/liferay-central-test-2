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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;

import java.io.File;

/**
 * <a href="LayoutServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutServiceImpl extends PrincipalBean implements LayoutService {

	public Layout addLayout(
			long groupId, boolean privateLayout, String parentLayoutId,
			String name, String title, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return LayoutLocalServiceUtil.addLayout(
			groupId, getUserId(), privateLayout, parentLayoutId, name, title,
			type, hidden, friendlyURL);
	}

	public void deleteLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.deleteLayout(layoutId, ownerId);
	}

	public String getLayoutName(
			String layoutId, String ownerId, String languageId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(layoutId, ownerId);

		return layout.getName(languageId);
	}

	public LayoutReference[] getLayoutReferences(
			String companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		return LayoutLocalServiceUtil.getLayouts(
			companyId, portletId, prefsKey, prefsValue);
	}

	public byte[] exportLayouts(
			String ownerId, boolean exportPortletPreferences,
			boolean exportPortletData, boolean exportPermissions,
			boolean exportTheme)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return LayoutLocalServiceUtil.exportLayouts(
			ownerId, exportPortletPreferences, exportPortletData,
			exportPermissions, exportTheme);
	}

	public void importLayouts(
			String ownerId, boolean importPortletPreferences,
			boolean importPortletData, boolean importPermissions,
			boolean importTheme, File file)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.importLayouts(
			getUserId(), ownerId, importPortletPreferences, importPortletData,
			importPermissions, importTheme, file);
	}

	public void setLayouts(
			String ownerId, String parentLayoutId, String[] layoutIds)
		throws PortalException, SystemException {

		long groupId = LayoutImpl.getGroupId(ownerId);

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String title, String languageId, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			layoutId, ownerId, parentLayoutId, name, title, languageId, type,
			hidden, friendlyURL);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String title, String languageId, String type, boolean hidden,
			String friendlyURL, Boolean iconImage, byte[] iconBytes)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			layoutId, ownerId, parentLayoutId, name, title, languageId, type,
			hidden, friendlyURL, iconImage, iconBytes);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String typeSettings)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			layoutId, ownerId, typeSettings);
	}

	public Layout updateLookAndFeel(
			String layoutId, String ownerId, String themeId,
			String colorSchemeId, String css)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		PluginSettingLocalServiceUtil.checkPermission(
			getUserId(), themeId, ThemeImpl.PLUGIN_TYPE);

		return LayoutLocalServiceUtil.updateLookAndFeel(
			layoutId, ownerId, themeId, colorSchemeId, css);
	}

	public Layout updateName(
			String layoutId, String ownerId, String name, String languageId)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateName(
			layoutId, ownerId, name, languageId);
	}

	public Layout updateParentLayoutId(
			String layoutId, String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateParentLayoutId(
			layoutId, ownerId, parentLayoutId);
	}

	public Layout updatePriority(String layoutId, String ownerId, int priority)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updatePriority(
			layoutId, ownerId, priority);
	}

}