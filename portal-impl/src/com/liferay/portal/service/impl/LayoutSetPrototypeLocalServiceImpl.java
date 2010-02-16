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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutSetPrototypeLocalServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="LayoutSetPrototypeLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypeLocalServiceImpl
	extends LayoutSetPrototypeLocalServiceBaseImpl {

	public LayoutSetPrototype addLayoutSetPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException, SystemException {

		// Layout set prototype

		long layoutSetPrototypeId = counterLocalService.increment();

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.create(layoutSetPrototypeId);

		layoutSetPrototype.setCompanyId(companyId);
		layoutSetPrototype.setNameMap(nameMap);
		layoutSetPrototype.setDescription(description);
		layoutSetPrototype.setActive(active);

		layoutSetPrototypePersistence.update(layoutSetPrototype, false);

		// Resources

		if (userId > 0) {
			resourceLocalService.addResources(
				companyId, 0, userId, LayoutSetPrototype.class.getName(),
				layoutSetPrototype.getLayoutSetPrototypeId(), false, false,
				false);
		}

		// Group

		String friendlyURL =
			"/template-" + layoutSetPrototype.getLayoutSetPrototypeId();

		Group group = groupLocalService.addGroup(
			userId, LayoutSetPrototype.class.getName(),
			layoutSetPrototype.getLayoutSetPrototypeId(),
			layoutSetPrototype.getName(LocaleUtil.getDefault()), null, 0,
			friendlyURL, true, null);

		ServiceContext serviceContext = new ServiceContext();

		layoutLocalService.addLayout(
			userId, group.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "home", null, null,
			LayoutConstants.TYPE_PORTLET, false, "/home", serviceContext);

		return layoutSetPrototype;
	}

	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws PortalException, SystemException {

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.findByPrimaryKey(
				layoutSetPrototypeId);

		// Group

		Group group = layoutSetPrototype.getGroup();

		groupLocalService.deleteGroup(group.getGroupId());

		// Resources

		resourceLocalService.deleteResource(
			layoutSetPrototype.getCompanyId(),
			LayoutSetPrototype.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutSetPrototype.getLayoutSetPrototypeId());

		// Layout set prototype

		layoutSetPrototypePersistence.remove(layoutSetPrototype);

		// Permission cache

		PermissionCacheUtil.clearCache();
	}

	public List<LayoutSetPrototype> search(
			long companyId, Boolean active, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (active != null) {
			return layoutSetPrototypePersistence.findByC_A(
				companyId, active, start, end, obc);
		}
		else {
			return layoutSetPrototypePersistence.findByCompanyId(
				companyId, start, end, obc);
		}
	}

	public int searchCount(long companyId, Boolean active)
		throws SystemException {

		if (active != null) {
			return layoutSetPrototypePersistence.countByC_A(companyId, active);
		}
		else {
			return layoutSetPrototypePersistence.countByCompanyId(companyId);
		}
	}

	public LayoutSetPrototype updateLayoutSetPrototype(
			long layoutSetPrototypeId, Map<Locale, String> nameMap,
			String description, boolean active)
		throws PortalException, SystemException {

		// Layout set prototype

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypePersistence.findByPrimaryKey(
				layoutSetPrototypeId);

		layoutSetPrototype.setNameMap(nameMap);
		layoutSetPrototype.setDescription(description);
		layoutSetPrototype.setActive(active);

		layoutSetPrototypePersistence.update(layoutSetPrototype, false);

		// Group

		Group group = groupLocalService.getLayoutSetPrototypeGroup(
			layoutSetPrototype.getCompanyId(), layoutSetPrototypeId);

		group.setName(layoutSetPrototype.getName(LocaleUtil.getDefault()));

		groupPersistence.update(group, false);

		return layoutSetPrototype;
	}

}