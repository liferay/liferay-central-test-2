<#include "copyright.txt" parse="false">


package ${springHibernatePackage};

import com.liferay.portal.util.PortalUtil;
import ${propsUtilPackage}.PropsKeys;
import ${propsUtilPackage}.PropsUtil;

import java.sql.SQLException;

/**
 * <a href="PortletCustomSQLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletCustomSQLUtil
	extends com.liferay.util.dao.hibernate.CustomSQLUtil {

	public PortletCustomSQLUtil() throws SQLException {
		super(
			HibernateUtil.getConnection(),
			PropsUtil.get(PropsKeys.CUSTOM_SQL_FUNCTION_ISNULL),
			PropsUtil.get(PropsKeys.CUSTOM_SQL_FUNCTION_ISNOTNULL));
	}

	protected String[] getConfigs() {
		return PropsUtil.getArray(PropsKeys.CUSTOM_SQL_CONFIGS);
	}

	protected String transform(String sql) {
		sql = super.transform(sql);
		sql = PortalUtil.transformCustomSQL(sql);

		return sql;
	}

}