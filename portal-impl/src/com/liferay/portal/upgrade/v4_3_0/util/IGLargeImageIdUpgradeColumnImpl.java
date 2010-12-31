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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;

/**
 * @author Brian Wing Shun Chan
 */
public class IGLargeImageIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public IGLargeImageIdUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn igImageIdColumn,
		ValueMapper imageIdMapper) {

		super("largeImageId");

		_companyIdColumn = companyIdColumn;
		_igImageIdColumn = igImageIdColumn;
		_imageIdMapper = imageIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String companyId = (String)_companyIdColumn.getOldValue();
		Long oldIGImageId = (Long)_igImageIdColumn.getOldValue();

		String oldImageId =
			companyId + ".image_gallery." + oldIGImageId.longValue() + ".large";

		return _imageIdMapper.getNewValue(oldImageId);
	}

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _igImageIdColumn;
	private ValueMapper _imageIdMapper;

}