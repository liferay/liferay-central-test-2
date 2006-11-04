/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermission;
import com.liferay.portal.service.permission.LayoutPermission;
import com.liferay.portal.service.spring.LayoutLocalServiceUtil;
import com.liferay.portal.service.spring.LayoutService;

import java.util.List;

/**
 * <a href="LayoutServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutServiceImpl extends PrincipalBean implements LayoutService {

	public Layout addLayout(
			String groupId, boolean privateLayout, String parentLayoutId,
			String name, String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return LayoutLocalServiceUtil.addLayout(
			groupId, getUserId(), privateLayout, parentLayoutId, name, type,
			hidden, friendlyURL);
	}

	public void deleteLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		String groupId = Layout.getGroupId(ownerId);

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

		List list = LayoutLocalServiceUtil.getLayouts(
			companyId, portletId, prefsKey, prefsValue);

		return (LayoutReference[])list.toArray(new LayoutReference[0]);
	}

	public void setLayouts(
			String ownerId, String parentLayoutId, String[] layoutIds)
		throws PortalException, SystemException {

		String groupId = Layout.getGroupId(ownerId);

		GroupPermission.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		LayoutLocalServiceUtil.setLayouts(ownerId, parentLayoutId, layoutIds);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String languageId, String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLayout(
			layoutId, ownerId, parentLayoutId, name, languageId, type, hidden,
			friendlyURL);
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
			String colorSchemeId)
		throws PortalException, SystemException {

		LayoutPermission.check(
			getPermissionChecker(), layoutId, ownerId, ActionKeys.UPDATE);

		return LayoutLocalServiceUtil.updateLookAndFeel(
			layoutId, ownerId, themeId, colorSchemeId);
	}

}