/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.webdav;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.model.IGImage;

import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public class IGImageResourceImpl extends BaseResourceImpl {

	public IGImageResourceImpl(IGImage image, String parentPath, String name) {
		super(
			parentPath, name, image.getNameWithExtension(),
			image.getCreateDate(), image.getModifiedDate(),
			image.getImageSize());

		setModel(image);
		setClassName(IGImage.class.getName());
		setPrimaryKey(image.getPrimaryKey());

		_image = image;
	}

	public boolean isCollection() {
		return false;
	}

	public String getContentType() {
		String type = StringPool.BLANK;

		try {
			type = _image.getImageType();
		}
		catch (Exception e) {
		}

		return MimeTypesUtil.getContentType(type);
	}

	public InputStream getContentAsStream() throws WebDAVException {
		try {
			Image image = ImageLocalServiceUtil.getImage(
				_image.getLargeImageId());

			byte[] bytes = image.getTextObj();

			return new UnsyncByteArrayInputStream(bytes);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private IGImage _image;

}