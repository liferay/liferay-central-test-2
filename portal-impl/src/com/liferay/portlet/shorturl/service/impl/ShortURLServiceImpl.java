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

package com.liferay.portlet.shorturl.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.shorturl.model.ShortURL;
import com.liferay.portlet.shorturl.service.base.ShortURLServiceBaseImpl;

/**
 * The implementation of the short u r l remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.shorturl.service.ShortURLService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.shorturl.service.base.ShortURLServiceBaseImpl
 * @see com.liferay.portlet.shorturl.service.ShortURLServiceUtil
 */
public class ShortURLServiceImpl extends ShortURLServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.shorturl.service.ShortURLServiceUtil} to access the short u r l remote service.
	 */
	
	public ShortURL addShortURL(String url) throws SystemException {
		return shortURLLocalService.addShortURL(url);
	}
}