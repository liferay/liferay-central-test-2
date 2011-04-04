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

package com.liferay.portlet.shorturl.model.impl;

import com.liferay.portlet.shorturl.model.ShortURL;

/**
 * The model implementation for the ShortURL service. Represents a row in the &quot;ShortURL&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.shorturl.model.ShortURL} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class ShortURLImpl extends ShortURLModelImpl implements ShortURL {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a short u r l model instance should use the {@link ShortURL} interface instead.
	 */
	public ShortURLImpl() {
	}
	
	public String getURL() {
		return getHash() + getDescriptor();
	}
}