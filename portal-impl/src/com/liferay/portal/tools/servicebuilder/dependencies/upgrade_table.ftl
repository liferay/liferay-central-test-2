package ${upgradePackagePath};

import java.sql.Types;

/**
 * <a href="${entity.name}Table.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
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

}