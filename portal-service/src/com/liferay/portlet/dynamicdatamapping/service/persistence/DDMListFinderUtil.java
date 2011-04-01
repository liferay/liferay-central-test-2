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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMListFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByKeywords(companyId, groupId, keywords);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMList> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMList> findByC_G_L_N_D(
		long companyId, long groupId, java.lang.String listKey,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_L_N_D(companyId, groupId, listKey, name,
			description, andOperator, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMList> findByC_G_L_N_D(
		long companyId, long groupId, java.lang.String[] listKeys,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_G_L_N_D(companyId, groupId, listKeys, names,
			descriptions, andOperator, start, end, orderByComparator);
	}

	public static DDMListFinder getFinder() {
		if (_finder == null) {
			_finder = (DDMListFinder)PortalBeanLocatorUtil.locate(DDMListFinder.class.getName());

			ReferenceRegistry.registerReference(DDMListFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(DDMListFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(DDMListFinderUtil.class, "_finder");
	}

	private static DDMListFinder _finder;
}