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

package com.liferay.portlet.dynamicdatalist.service;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntryService
 * @generated
 */
public class DDLEntryServiceWrapper implements DDLEntryService {
	public DDLEntryServiceWrapper(DDLEntryService ddlEntryService) {
		_ddlEntryService = ddlEntryService;
	}

	public DDLEntryService getWrappedDDLEntryService() {
		return _ddlEntryService;
	}

	public void setWrappedDDLEntryService(DDLEntryService ddlEntryService) {
		_ddlEntryService = ddlEntryService;
	}

	private DDLEntryService _ddlEntryService;
}