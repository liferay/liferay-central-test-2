<#include "copyright.txt" parse="false">


package ${springHibernatePackage};

import com.liferay.portal.kernel.util.OrderByComparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CustomSQLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class CustomSQLUtil {

	public static String get(String id) {
		return _instance._portletCustomSQLUtil.get(id);
	}

	public static boolean isVendorDB2() {
		return _instance._portletCustomSQLUtil.isVendorDB2();
	}

	public static boolean isVendorInformix() {
		return _instance._portletCustomSQLUtil.isVendorInformix();
	}

	public static boolean isVendorMySQL() {
		return _instance._portletCustomSQLUtil.isVendorMySQL();
	}

	public static boolean isVendorOracle() {
		return _instance._portletCustomSQLUtil.isVendorOracle();
	}

	public static boolean isVendorSybase() {
		return _instance._portletCustomSQLUtil.isVendorSybase();
	}

	public static String[] keywords(String keywords) {
		return _instance._portletCustomSQLUtil.keywords(keywords);
	}

	public static String[] keywords(String keywords, boolean lowerCase) {
		return _instance._portletCustomSQLUtil.keywords(keywords, lowerCase);
	}

	public static String[] keywords(String[] keywordsArray) {
		return _instance._portletCustomSQLUtil.keywords(keywordsArray);
	}

	public static String[] keywords(String[] keywordsArray, boolean lowerCase) {
		return _instance._portletCustomSQLUtil.keywords(
			keywordsArray, lowerCase);
	}

	public static String replaceAndOperator(String sql, boolean andOperator) {
		return _instance._portletCustomSQLUtil.replaceAndOperator(
			sql, andOperator);
	}

	public static String replaceIsNull(String sql) {
		return _instance._portletCustomSQLUtil.replaceIsNull(sql);
	}

	public static String replaceKeywords(
		String sql, String field, String operator, boolean last,
		String[] values) {

		return _instance._portletCustomSQLUtil.replaceKeywords(
			sql, field, operator, last, values);
	}

	public static String removeOrderBy(String sql) {
		return _instance._portletCustomSQLUtil.removeOrderBy(sql);
	}

	public static String replaceOrderBy(String sql, OrderByComparator obc) {
		return _instance._portletCustomSQLUtil.replaceOrderBy(sql, obc);
	}

	private CustomSQLUtil() {
		try {
			_portletCustomSQLUtil = new PortletCustomSQLUtil();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactory.getLog(CustomSQLUtil.class);

	private static CustomSQLUtil _instance = new CustomSQLUtil();

	private PortletCustomSQLUtil _portletCustomSQLUtil;

}