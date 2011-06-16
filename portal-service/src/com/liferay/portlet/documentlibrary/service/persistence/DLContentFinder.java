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

package com.liferay.portlet.documentlibrary.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface DLContentFinder {
	public java.util.List<java.lang.String> findNamesByC_R_P(long companyId,
		long repositoryId, java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException;

	public long findSizeByC_R_P(long companyId, long repositoryId,
		java.lang.String path)
		throws com.liferay.portal.NoSuchDLContentException,
			com.liferay.portal.kernel.exception.SystemException;

	public void updateByC_R_P_N_1(long companyId, long repositoryId,
		java.lang.String path, long newRepositoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void updateByC_R_P_N_2(long companyId, long repositoryId,
		java.lang.String path, java.lang.String newPath)
		throws com.liferay.portal.kernel.exception.SystemException;
}