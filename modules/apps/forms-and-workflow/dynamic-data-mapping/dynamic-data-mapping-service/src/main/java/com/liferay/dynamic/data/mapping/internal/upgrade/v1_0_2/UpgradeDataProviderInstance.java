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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_0_2;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Inácio Nery
 */
public class UpgradeDataProviderInstance extends UpgradeProcess {

	public UpgradeDataProviderInstance(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select DDMDataProviderInstance.definition, ");
		sb.append("DDMDataProviderInstance.dataProviderInstanceId ");
		sb.append("from DDMDataProviderInstance");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMDataProviderInstance set definition = ? where " +
						"dataProviderInstanceId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					long dataProviderInstanceId = rs.getLong(2);

					String newDefinition =
						upgradeDataProviderInstanceDefinition(definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, dataProviderInstanceId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected String upgradeDataProviderInstanceDefinition(String definition)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldValuesJSONArray = definitionJSONObject.getJSONArray(
			"fieldValues");

		upgradeDataProviderInstanceFieldValues(fieldValuesJSONArray);

		return definitionJSONObject.toString();
	}

	protected void upgradeDataProviderInstanceFieldValues(
		JSONArray fieldValuesJSONArray) {

		JSONObject fieldJSONObject = _jsonFactory.createJSONObject();

		fieldJSONObject.put("instanceId", StringUtil.randomString(8));
		fieldJSONObject.put("name", "filterable");
		fieldJSONObject.put("value", "false");

		fieldValuesJSONArray.put(fieldJSONObject);

		fieldJSONObject = _jsonFactory.createJSONObject();

		fieldJSONObject.put("instanceId", StringUtil.randomString(8));
		fieldJSONObject.put("name", "filterParameterName");
		fieldJSONObject.put("value", "");

		fieldValuesJSONArray.put(fieldJSONObject);
	}

	private final JSONFactory _jsonFactory;

}