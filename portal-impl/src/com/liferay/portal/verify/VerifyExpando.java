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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

import java.util.List;


/**
 * @author Raymond Augé
 */
public class VerifyExpando extends VerifyProcess {

	protected void doVerify() throws Exception {
		verifyExpandoColumnProperties();
	}

	protected void verifyExpandoColumnProperties() throws Exception {
		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getExpandoColumns(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ExpandoColumn column : expandoColumns) {
			UnicodeProperties properties = column.getTypeSettingsProperties();

			String indexable = properties.getProperty("indexable");
			int type = column.getType();

			if (Validator.isNotNull(indexable)) {
				if (GetterUtil.getBoolean(indexable)) {
					if ((type == ExpandoColumnConstants.STRING) ||
						(type == ExpandoColumnConstants.STRING_ARRAY)) {

						properties.setProperty(
							ExpandoColumnConstants.INDEX_TYPE,
							String.valueOf(
								ExpandoColumnConstants.INDEX_TYPE_TEXT));
					}
					else {
						properties.setProperty(
							ExpandoColumnConstants.INDEX_TYPE,
							String.valueOf(
								ExpandoColumnConstants.INDEX_TYPE_KEYWORD));
					}
				}
				else {
					properties.setProperty(
						ExpandoColumnConstants.INDEX_TYPE,
						String.valueOf(
							ExpandoColumnConstants.INDEX_TYPE_NONE));
				}

				properties.remove(indexable);

				ExpandoColumnLocalServiceUtil.updateTypeSettings(
					column.getColumnId(), properties.toString());
			}
		}
	}

}