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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link LayoutRevisionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutRevisionService
 * @generated
 */
public class LayoutRevisionServiceWrapper implements LayoutRevisionService {
	public LayoutRevisionServiceWrapper(
		LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	public LayoutRevisionService getWrappedLayoutRevisionService() {
		return _layoutRevisionService;
	}

	public void setWrappedLayoutRevisionService(
		LayoutRevisionService layoutRevisionService) {
		_layoutRevisionService = layoutRevisionService;
	}

	private LayoutRevisionService _layoutRevisionService;
}