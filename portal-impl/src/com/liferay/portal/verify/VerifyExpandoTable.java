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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

import java.util.List;

/**
 * <a href="VerifyExpandoTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class VerifyExpandoTable extends VerifyProcess {

	protected void doVerify() throws Exception {
		List<ExpandoTable> tables =
			ExpandoTableLocalServiceUtil.getExpandoTables(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + tables.size() + " ExpandoTables");
		}

		for (ExpandoTable table : tables) {
			if (table.getName().equals(_OLD_DEFAULT_TABLE_NAME)) {
				try {
					ExpandoTableLocalServiceUtil.updateTable(
						table.getTableId(),
						ExpandoTableConstants.DEFAULT_TABLE_NAME);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update table name for table " +
								table.getTableId() + ": " + e.getMessage());
					}
				}
			}
		}
	}

	protected static final String _OLD_DEFAULT_TABLE_NAME = "DEFAULT_TABLE";

	private static Log _log = LogFactoryUtil.getLog(VerifyExpandoTable.class);

}