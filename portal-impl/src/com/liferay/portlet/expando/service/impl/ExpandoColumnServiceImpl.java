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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.base.ExpandoColumnServiceBaseImpl;
import com.liferay.portlet.expando.service.permission.ExpandoColumnPermission;

/**
 * <a href="ExpandoColumnServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnServiceImpl extends ExpandoColumnServiceBaseImpl {

	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public ExpandoColumn addColumn(
			long tableId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.EXPANDO,
			ActionKeys.ADD_EXPANDO);

		return expandoColumnLocalService.addColumn(
			tableId, name, type, defaultData);
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.DELETE);

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(columnId, name, type);
	}

	public ExpandoColumn updateColumn(
			long columnId, String name, int type, Object defaultData)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateColumn(
			columnId, name, type, defaultData);
	}

	public ExpandoColumn updateTypeSettings(long columnId, String typeSettings)
		throws PortalException, SystemException {

		ExpandoColumnPermission.check(
			getPermissionChecker(), columnId, ActionKeys.UPDATE);

		return expandoColumnLocalService.updateTypeSettings(
			columnId, typeSettings);
	}

}