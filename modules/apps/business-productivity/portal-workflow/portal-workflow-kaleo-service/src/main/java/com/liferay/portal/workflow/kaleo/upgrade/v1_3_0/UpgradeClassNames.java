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

package com.liferay.portal.workflow.kaleo.upgrade.v1_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lino Alves
 */
public class UpgradeClassNames extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateClassName("KaleoInstance");
		updateClassName("KaleoInstanceToken");
		updateClassName("KaleoTaskInstanceToken");

		updateWorkflowContextEntryClassName("KaleoInstance", "kaleoInstanceId");
		updateWorkflowContextEntryClassName("KaleoLog", "kaleoLogId");
		updateWorkflowContextEntryClassName(
			"KaleoTaskInstanceToken", "kaleoTaskInstanceTokenId");
	}

	protected void updateClassName(String tableName) {
		Table table = new Table(tableName);

		for (Map.Entry<String, String> entry : _classNamesMap.entrySet()) {
			table.updateColumnValue(
				"className", entry.getKey(), entry.getValue());
		}
	}

	protected void updateWorkflowContext(
			String tableName, String primaryKeyName, long primaryKeyValue,
			String workflowContext)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update " + tableName + " set workflowContext = ? where " +
					primaryKeyName + " = ?")) {

			ps.setString(1, workflowContext);
			ps.setLong(2, primaryKeyValue);

			ps.executeUpdate();
		}
	}

	protected void updateWorkflowContextEntryClassName(
			String tableName, String primaryKeyName)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select " + primaryKeyName + ", workflowContext from " +
					tableName + " where workflowContext is not null");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long primaryKeyValue = rs.getLong(primaryKeyName);
				String workflowContextJSON = rs.getString("workflowContext");

				if (Validator.isNull(workflowContextJSON)) {
					continue;
				}

				Map<String, Serializable> workflowContext =
					WorkflowContextUtil.convert(workflowContextJSON);

				String oldEntryClassName = (String)workflowContext.get(
					"entryClassName");

				String newEntryClassName = _classNamesMap.get(
					oldEntryClassName);

				if (newEntryClassName == null) {
					continue;
				}

				workflowContext.put("entryClassName", newEntryClassName);

				updateWorkflowContext(
					tableName, primaryKeyName, primaryKeyValue,
					WorkflowContextUtil.convert(workflowContext));
			}
		}
	}

	private static final Map<String, String> _classNamesMap = new HashMap<>();

	static {
		_classNamesMap.put(
			"com.liferay.portal.model.Company",
			"com.liferay.portal.kernel.model.Company");
		_classNamesMap.put(
			"com.liferay.portal.model.Group",
			"com.liferay.portal.kernel.model.Group");
		_classNamesMap.put(
			"com.liferay.portal.model.LayoutRevision",
			"com.liferay.portal.kernel.model.LayoutRevision");
		_classNamesMap.put(
			"com.liferay.portal.model.Role",
			"com.liferay.portal.kernel.model.Role");
		_classNamesMap.put(
			"com.liferay.portal.model.User",
			"com.liferay.portal.kernel.model.User");
		_classNamesMap.put(
			"com.liferay.portal.model.UserGroup",
			"com.liferay.portal.kernel.model.UserGroup");
		_classNamesMap.put(
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle");
		_classNamesMap.put(
			"com.liferay.portlet.dynamicdatalists.model.DDLRecord",
			"com.liferay.dynamic.data.lists.model.DDLRecord");
		_classNamesMap.put(
			"com.liferay.portlet.wiki.model.WikiPage",
			"com.liferay.wiki.model.WikiPage");
	}

}