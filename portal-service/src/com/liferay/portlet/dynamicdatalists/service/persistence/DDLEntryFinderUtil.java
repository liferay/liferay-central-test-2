/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLEntryFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByKeywords(companyId, groupId, keywords);
	}

	public static int countByC_G_E_N_D(long companyId, long groupId,
		java.lang.String entryKey, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_G_E_N_D(companyId, groupId, entryKey, name,
			description, andOperator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> findByC_G_E_N_D(
		long companyId, long groupId, java.lang.String entryKey,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_E_N_D(companyId, groupId, entryKey, name,
			description, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> findByC_G_E_N_D(
		long companyId, long groupId, java.lang.String[] entryKeys,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_E_N_D(companyId, groupId, entryKeys, names,
			descriptions, andOperator, start, end, orderByComparator);
	}

	public static DDLEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (DDLEntryFinder)PortalBeanLocatorUtil.locate(DDLEntryFinder.class.getName());

			ReferenceRegistry.registerReference(DDLEntryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDLEntryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDLEntryFinderUtil.class, "_finder");
	}

	private static DDLEntryFinder _finder;
}