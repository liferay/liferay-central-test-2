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

package com.liferay.portlet.shorturl.service;

/**
 * <p>
 * This class is a wrapper for {@link ShortURLService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShortURLService
 * @generated
 */
public class ShortURLServiceWrapper implements ShortURLService {
	public ShortURLServiceWrapper(ShortURLService shortURLService) {
		_shortURLService = shortURLService;
	}

	public com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		java.lang.String url)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _shortURLService.addShortURL(url);
	}

	public ShortURLService getWrappedShortURLService() {
		return _shortURLService;
	}

	public void setWrappedShortURLService(ShortURLService shortURLService) {
		_shortURLService = shortURLService;
	}

	private ShortURLService _shortURLService;
}