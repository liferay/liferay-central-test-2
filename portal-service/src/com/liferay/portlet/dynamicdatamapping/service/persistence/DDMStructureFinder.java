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

/**
 * @author Brian Wing Shun Chan
 */
public interface DDMStructureFinder {
	public int countByC_G_CN_S_ST_D(long companyId, long groupId,
		java.lang.String[] classNameIds, java.lang.String[] structureKeys,
		java.lang.String[] storageTypes, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByKeywords(long companyId, long groupId,
		java.lang.String[] classNameIds, java.lang.String[] structureKeys,
		java.lang.String[] storageTypes, java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByC_G_CN_S_ST_D(
		long companyId, long groupId, java.lang.String[] classNameIds,
		java.lang.String[] structureKeys, java.lang.String[] storageTypes,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> findByKeywords(
		long companyId, long groupId, java.lang.String[] classNameIds,
		java.lang.String[] structureKeys, java.lang.String[] storageTypes,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;
}