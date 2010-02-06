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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="OrganizationFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrganizationFinderUtil {
	public static int countByKeywords(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params);
	}

	public static int countByO_U(long organizationId, long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByO_U(organizationId, userId);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator);
	}

	public static int countByC_PO_N_T_S_C_Z_R_C(long companyId,
		long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String[] names, java.lang.String type,
		java.lang.String[] streets, java.lang.String[] cities,
		java.lang.String[] zips, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByKeywords(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByKeywords(companyId, parentOrganizationId,
			parentOrganizationIdComparator, keywords, type, regionId,
			countryId, params, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, name, type, street, city, zip,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_PO_N_T_S_C_Z_R_C(
		long companyId, long parentOrganizationId,
		java.lang.String parentOrganizationIdComparator,
		java.lang.String[] names, java.lang.String type,
		java.lang.String[] streets, java.lang.String[] cities,
		java.lang.String[] zips, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<String, Object> params, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByC_PO_N_T_S_C_Z_R_C(companyId, parentOrganizationId,
			parentOrganizationIdComparator, names, type, streets, cities, zips,
			regionId, countryId, params, andOperator, start, end, obc);
	}

	public static OrganizationFinder getFinder() {
		if (_finder == null) {
			_finder = (OrganizationFinder)PortalBeanLocatorUtil.locate(OrganizationFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(OrganizationFinder finder) {
		_finder = finder;
	}

	private static OrganizationFinder _finder;
}