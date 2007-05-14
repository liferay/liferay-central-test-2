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
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.PluginSettingLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;

import java.io.File;

import java.util.Map;

/**
 * <a href="LayoutServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutServiceImpl extends PrincipalBean implements LayoutService {

	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			String name, String title, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return LayoutLocalServiceUtil.addLayout(
			getUserId(), groupId, privateLayout, parentLayoutId, name, title,
			type, hidden, friendlyURL);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.deleteLayout(groupId, privateLayout, layoutId);
	}

	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			groupId, privateLayout, layoutId);

		return layout.getName(languageId);
	}

	public LayoutReference[] getLayoutReferences(
			long companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		return LayoutLocalServiceUtil.getLayouts(
			companyId, portletId, prefsKey, prefsValue);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, Map parameterMap)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return LayoutLocalServiceUtil.exportLayouts(
			groupId, privateLayout, parameterMap);
	}

	public void importLayouts(
			long groupId, boolean privateLayout, Map parameterMap, File file)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, file);
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, String name, String title, String languageId,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, name, title,
			languageId, type, hidden, friendlyURL);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, String name, String title, String languageId,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, name, title,
			languageId, type, hidden, friendlyURL, iconImage, iconBytes);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		PluginSettingLocalServiceUtil.checkPermission(
			getUserId(), themeId, ThemeImpl.PLUGIN_TYPE);

		return LayoutLocalServiceUtil.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
			wapTheme);
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateName(
			groupId, privateLayout, layoutId, name, languageId);
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

}