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
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalTemplateSmallImageIdUpgradeColumnImpl
	extends BaseUpgradeColumnImpl {

	public JournalTemplateSmallImageIdUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn groupIdColumn,
		UpgradeColumn templateIdColumn, ValueMapper imageIdMapper) {

		super("smallImageId");

		_companyIdColumn = companyIdColumn;
		_groupIdColumn = groupIdColumn;
		_templateIdColumn = templateIdColumn;
		_imageIdMapper = imageIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String companyId = (String)_companyIdColumn.getOldValue();
		Long groupId = (Long)_groupIdColumn.getOldValue();
		String oldTemplateId = (String)_templateIdColumn.getOldValue();

		String oldImageId =
			companyId + ".journal.template." + groupId + "." + oldTemplateId +
				".small";

		return _imageIdMapper.getNewValue(oldImageId);
	}

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _groupIdColumn;
	private UpgradeColumn _templateIdColumn;
	private ValueMapper _imageIdMapper;

}