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

package com.liferay.portal.upgrade.v4_3_4.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alexander Chow
 */
public class JournalArticleContentUpgradeColumnImpl
	extends BaseUpgradeColumnImpl {

	public JournalArticleContentUpgradeColumnImpl(
		UpgradeColumn structureIdColumn) {

		super("content");

		_structureIdColumn = structureIdColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String oldContent = (String)oldValue;

		String newContent = oldContent;

		String structureId = (String)_structureIdColumn.getOldValue();

		if (Validator.isNull(structureId)) {
			if (Validator.isNotNull(oldContent) &&
				(oldContent.indexOf("<static-content") == -1)) {

				String defaultLanguageId = LocaleUtil.toLanguageId(
					LocaleUtil.getDefault());

				newContent = LocalizationUtil.updateLocalization(
					StringPool.BLANK, "static-content", oldContent,
					defaultLanguageId, defaultLanguageId, true);
			}
		}

		return newContent;
	}

	private UpgradeColumn _structureIdColumn;

}