/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.upgrade.v7_0_0.util.AssetEntryTable;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;

/**
 * @author Gergely Mathe
 */
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type AssetEntry description TEXT null");
			runSQL("alter_column_type AssetEntry summary TEXT null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				AssetEntryTable.TABLE_NAME, AssetEntryTable.TABLE_COLUMNS,
				AssetEntryTable.TABLE_SQL_CREATE,
				AssetEntryTable.TABLE_SQL_ADD_INDEXES);
		}

		updateAssetVocabularies();
	}

	protected void updateAssetVocabularies() throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			statement = connection.prepareStatement(
				"select vocabularyId, settings_ from AssetVocabulary");

			result = statement.executeQuery();

			while (result.next()) {
				long vocabularyId = result.getLong("vocabularyId");
				String settings = result.getString("settings_");

				updateAssetVocabulary(
					vocabularyId, upgradeVocabularySettings(settings));
			}
		}
		finally {
			DataAccess.cleanUp(connection, statement, result);
		}
	}

	protected void updateAssetVocabulary(long vocabularyId, String settings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update AssetVocabulary set settings_ = ? where vocabularyId " +
					"= ?");

			ps.setString(1, settings);
			ps.setLong(2, vocabularyId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to update vocabulary " + vocabularyId, e);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String upgradeVocabularySettings(String settings) {
		UnicodeProperties properties = new UnicodeProperties(true);

		properties.fastLoad(settings);

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setMultiValued(
			GetterUtil.getBoolean(properties.getProperty("multiValued"), true));

		long[] classNameIds = StringUtil.split(
			properties.getProperty("selectedClassNameIds"), 0L);

		long[] classTypePKs = new long[classNameIds.length];

		Arrays.fill(classTypePKs, AssetCategoryConstants.ALL_CLASS_TYPE_PK);

		long[] requiredClassNameIds = StringUtil.split(
			properties.getProperty("requiredClassNameIds"), 0L);

		boolean[] requireds = new boolean[classNameIds.length];

		for (int i = 0; i < classNameIds.length; i++) {
			requireds[i] = ArrayUtil.contains(
				requiredClassNameIds, classNameIds[i]);
		}

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requireds);

		return vocabularySettingsHelper.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeAsset.class);

}