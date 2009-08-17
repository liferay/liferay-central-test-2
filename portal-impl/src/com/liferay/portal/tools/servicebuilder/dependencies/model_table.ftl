package com.liferay.portal.upgrade.${packageVersion}.util;

import java.sql.Types;

/**
 * <a href="${entity.name}Table.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    ${author}
 * @generated
 */
public class ${entity.name}Table {

	public static final String TABLE_NAME = "${entity.table}";

	public static final Object[][] TABLE_COLUMNS = {
		<#list entity.getRegularColList() as column>
			<#assign sqlType = serviceBuilder.getSqlType(packagePath + ".model." + entity.getName(), column.getName(), column.getType())>

			{"${column.DBName}", new Integer(Types.${sqlType})}

			<#if column_has_next>
				,
			</#if>
		</#list>
	};

	public static final String TABLE_SQL_CREATE = "${serviceBuilder.getCreateTableSQL(entity)}";

	public static final String TABLE_SQL_DROP = "drop table ${entity.table}";

}