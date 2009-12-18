/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.imagegallery.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;

/**
 * <a href="IGImageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class IGImageImpl extends IGImageModelImpl implements IGImage {

	public static String getNameWithExtension(String name, String type) {
		if (Validator.isNotNull(type)) {
			name += StringPool.PERIOD + type;
		}

		return name;
	}

	public IGImageImpl() {
	}

	public IGFolder getFolder() {
		IGFolder folder = null;

		if (getFolderId() > 0) {
			try {
				folder = IGFolderLocalServiceUtil.getFolder(getFolderId());
			}
			catch (Exception e) {
				folder = new IGFolderImpl();

				_log.error(e);
			}
		}
		else {
			folder = new IGFolderImpl();
		}

		return folder;
	}

	public int getImageSize() {
		if (_imageSize == null) {
			try {
				Image largeImage = ImageLocalServiceUtil.getImage(
					getLargeImageId());

				_imageSize = new Integer(largeImage.getSize());
			}
			catch (Exception e) {
				_imageSize = new Integer(0);

				_log.error(e);
			}
		}

		return _imageSize.intValue();
	}

	public String getImageType() {
		if (_imageType == null) {
			try {
				Image largeImage = ImageLocalServiceUtil.getImage(
					getLargeImageId());

				_imageType = largeImage.getType();
			}
			catch (Exception e) {
				_imageType = StringPool.BLANK;

				_log.error(e);
			}
		}

		return _imageType;
	}

	public String getNameWithExtension() {
		String nameWithExtension = getName();

		if (Validator.isNull(nameWithExtension)) {
			nameWithExtension = String.valueOf(getImageId());
		}

		String type = getImageType();

		return getNameWithExtension(nameWithExtension, type);
	}

	public void setImageType(String imageType) {
		_imageType = imageType;
	}

	private static Log _log = LogFactoryUtil.getLog(IGImageImpl.class);

	private Integer _imageSize;
	private String _imageType;

}