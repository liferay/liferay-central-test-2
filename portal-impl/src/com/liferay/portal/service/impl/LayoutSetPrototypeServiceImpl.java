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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.LayoutSetPrototypeServiceBaseImpl;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="LayoutSetPrototypeServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypeServiceImpl
	extends LayoutSetPrototypeServiceBaseImpl {

	public LayoutSetPrototype addLayoutSetPrototype(
			Map<Locale, String> nameMap, String description,
			boolean active)
		throws PortalException, SystemException {

		User user = getUser();

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_LAYOUT_PROTOTYPE);

		return layoutSetPrototypeLocalService.addLayoutSetPrototype(
			user.getUserId(), user.getCompanyId(), nameMap, description,
			active);
	}

	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException, SystemException {

		LayoutSetPrototypePermissionUtil.check(
			getPermissionChecker(), layoutSetPrototypeId, ActionKeys.DELETE);

		layoutSetPrototypeLocalService.deleteLayoutSetPrototype(
			layoutSetPrototypeId);
	}

	public LayoutSetPrototype getLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException, SystemException {

		LayoutSetPrototypePermissionUtil.check(
			getPermissionChecker(), layoutSetPrototypeId, ActionKeys.VIEW);

		return layoutSetPrototypeLocalService.getLayoutSetPrototype(
			layoutSetPrototypeId);
	}

	public List<LayoutSetPrototype> search(
			long companyId, Boolean active, OrderByComparator obc)
		throws PortalException, SystemException {

		List<LayoutSetPrototype> filteredLayoutSetPrototypes =
			new ArrayList<LayoutSetPrototype>();

		List<LayoutSetPrototype> layoutSetPrototypes =
			layoutSetPrototypeLocalService.search(
				companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			if (LayoutSetPrototypePermissionUtil.contains(
					getPermissionChecker(),
					layoutSetPrototype.getLayoutSetPrototypeId(),
					ActionKeys.VIEW)) {

				filteredLayoutSetPrototypes.add(layoutSetPrototype);
			}
		}

		return filteredLayoutSetPrototypes;
	}

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException, SystemException {

		LayoutSetPrototypePermissionUtil.check(
			getPermissionChecker(), layoutSetPrototypeId, ActionKeys.UPDATE);

		return layoutSetPrototypeLocalService.updateLayoutSetPrototype(
			layoutSetPrototypeId, nameMap, description, active);
	}

}