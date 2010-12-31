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

import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class IGImageIdUpgradeColumnImpl extends PKUpgradeColumnImpl {

	public IGImageIdUpgradeColumnImpl(UpgradeColumn companyIdColumn) {
		super("imageId", false);

		_companyIdColumn = companyIdColumn;
		_igImageIdMapper = ValueMapperFactoryUtil.getValueMapper();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Object newValue = super.getNewValue(oldValue);

		String companyId = (String)_companyIdColumn.getOldValue();
		Long imageId = (Long)oldValue;

		String oldImageIdValue =
			"{companyId=" + companyId + ", imageId=" + imageId + "}";

		_igImageIdMapper.mapValue(oldImageIdValue, newValue);

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _igImageIdMapper;
	}

	private UpgradeColumn _companyIdColumn;
	private ValueMapper _igImageIdMapper;

}