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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KaleoLogTable {

	public static final String TABLE_NAME = "KaleoLog";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoLogId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR},
		{"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoNodeName", Types.VARCHAR},
		{"terminalKaleoNode", Types.BOOLEAN},
		{"kaleoActionId", Types.BIGINT},
		{"kaleoActionName", Types.VARCHAR},
		{"kaleoActionDescription", Types.VARCHAR},
		{"previousKaleoNodeId", Types.BIGINT},
		{"previousKaleoNodeName", Types.VARCHAR},
		{"previousAssigneeClassName", Types.VARCHAR},
		{"previousAssigneeClassPK", Types.BIGINT},
		{"currentAssigneeClassName", Types.VARCHAR},
		{"currentAssigneeClassPK", Types.BIGINT},
		{"type_", Types.VARCHAR},
		{"comment_", Types.CLOB},
		{"startDate", Types.TIMESTAMP},
		{"endDate", Types.TIMESTAMP},
		{"duration", Types.BIGINT},
		{"workflowContext", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<>();

static {
TABLE_COLUMNS_MAP.put("kaleoLogId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("terminalKaleoNode", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("kaleoActionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoActionName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoActionDescription", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("previousKaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("previousKaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("previousAssigneeClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("previousAssigneeClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("currentAssigneeClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("currentAssigneeClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("comment_", Types.CLOB);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("duration", Types.BIGINT);

TABLE_COLUMNS_MAP.put("workflowContext", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoLog (kaleoLogId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoNodeName VARCHAR(200) null,terminalKaleoNode BOOLEAN,kaleoActionId LONG,kaleoActionName VARCHAR(200) null,kaleoActionDescription STRING null,previousKaleoNodeId LONG,previousKaleoNodeName VARCHAR(200) null,previousAssigneeClassName VARCHAR(200) null,previousAssigneeClassPK LONG,currentAssigneeClassName VARCHAR(200) null,currentAssigneeClassPK LONG,type_ VARCHAR(50) null,comment_ TEXT null,startDate DATE null,endDate DATE null,duration LONG,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoLog";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_73B5F4DE on KaleoLog (companyId)",
		"create index IX_E66A153A on KaleoLog (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$])",
		"create index IX_6C64B7D4 on KaleoLog (kaleoDefinitionId)",
		"create index IX_5BC6AB16 on KaleoLog (kaleoInstanceId)",
		"create index IX_470B9FF8 on KaleoLog (kaleoInstanceTokenId, type_[$COLUMN_LENGTH:50$])",
		"create index IX_B0CDCA38 on KaleoLog (kaleoTaskInstanceTokenId)"
	};

}