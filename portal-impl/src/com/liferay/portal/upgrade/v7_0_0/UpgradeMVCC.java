/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMVCC extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection connection = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			List<Element> classElements = getClassElements();

			for (Element classElement : classElements) {
				if (classElement.element("version") == null) {
					continue;
				}

				upgradeMVCC(databaseMetaData, classElement);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}
	}

	protected List<Element> getClassElements() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"META-INF/portal-hbm.xml");

		Document document = SAXReaderUtil.read(inputStream);

		Element rootElement = document.getRootElement();

		return rootElement.elements("class");
	}

	protected void upgradeMVCC(
			DatabaseMetaData databaseMetaData, Element classElement)
		throws Exception {

		String table = classElement.attributeValue("table");

		ResultSet tableResultSet = databaseMetaData.getTables(
			null, null, table, null);

		try {
			if (!tableResultSet.next()) {
				_log.error("Table " + table + " does not exist");

				return;
			}

			ResultSet columnResultSet = databaseMetaData.getColumns(
				null, null, table, "mvccVersion");

			try {
				if (columnResultSet.next()) {
					return;
				}

				runSQL(
					"alter table " + table + " add mvccVersion LONG default 0");

				if (_log.isDebugEnabled()) {
					_log.debug("Added column mvccVersion to table " + table);
				}
			}
			finally {
				DataAccess.cleanUp(columnResultSet);
			}
		}
		finally {
			DataAccess.cleanUp(tableResultSet);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeMVCC.class);

}