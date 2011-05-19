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

package com.liferay.documentlibrary.service.persistence;

import com.liferay.documentlibrary.NoSuchContentException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.BasePersistence;

/**
 * @author Shuyang Zhou
 */
public interface ContentPersistence extends BasePersistence<Dummy> {

	public int countByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException;

	public Content fetchByC_R_P(long companyId, long repositoryId, String path)
		throws SystemException;

	public Content fetchByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException;

	public Content fetchByPrimaryKey(long contentId) throws SystemException;

	public Content findByC_R_P(
			long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException;

	public Content findByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException;

	public Content findByPrimaryKey(long contentId)
		throws NoSuchContentException, SystemException;

	public void remove(long contentId)
		throws NoSuchContentException, SystemException;

	public boolean removeByC_R_P_V(
			long companyId, long repositoryId, String path, String version)
		throws SystemException;

	public void update(Content content) throws SystemException;

}