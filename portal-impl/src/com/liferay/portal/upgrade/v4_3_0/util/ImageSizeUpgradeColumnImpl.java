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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageSizeUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ImageSizeUpgradeColumnImpl(ImageTextUpgradeColumnImpl textColumn) {
		super("size_");

		_textColumn = textColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		return _textColumn.getSize();
	}

	private ImageTextUpgradeColumnImpl _textColumn;

}