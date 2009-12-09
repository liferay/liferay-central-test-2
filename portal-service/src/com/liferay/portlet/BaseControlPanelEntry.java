/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletCategoryKeys;

/**
 * <a href="BaseControlPanelEntry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public abstract class BaseControlPanelEntry implements ControlPanelEntry {

	public boolean isVisible(
			Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		Group group = themeDisplay.getScopeGroup();

		long plid = LayoutConstants.DEFAULT_PLID;

		if (category.equals(PortletCategoryKeys.CONTENT)) {
			plid = group.getDefaultPublicPlid();

			if (plid == LayoutConstants.DEFAULT_PLID) {
				plid = group.getDefaultPrivatePlid();
			}
		}

		if (category.equals(PortletCategoryKeys.CONTENT) &&
			permissionChecker.isCommunityAdmin(group.getGroupId())) {

			return true;
		}

		if (PortletPermissionUtil.contains(
				permissionChecker, plid, portlet.getPortletId(),
				ActionKeys.ACCESS_IN_CONTROL_PANEL, true)) {

			return true;
		}

		return isVisible(themeDisplay.getPermissionChecker(), portlet);
	}

}