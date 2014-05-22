/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.ClassType;
import com.liferay.portlet.asset.model.ClassTypeReader;

import java.util.List;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryClassTypeReader implements ClassTypeReader {

	@Override
	public List<ClassType> getAvailableClassTypes(
			long[] groupIds, Locale locale)
		throws SystemException {

		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public ClassType getClassType(long classTypeId, Locale locale)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException("Not implemented");
	}

}