/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.events;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.tools.sql.Index;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;

import java.io.BufferedReader;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <a href="StartupHelper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Raymond Augé
 *
 */
public class StartupHelper {

	public void deleteTempImages() {
		try {
			DBUtil dbUtil = DBUtil.getInstance();

			dbUtil.runSQL(_DELETE_TEMP_IMAGES_1);
			dbUtil.runSQL(_DELETE_TEMP_IMAGES_2);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setDropIndexes(boolean dropIndexes) {
		_dropIndexes = dropIndexes;
	}

	public void updateIndexes() {
		Set<String> existingIndexNames = new HashSet<String>();

		try {
			dropIndexes(existingIndexNames);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		try {
			addIndexes(existingIndexNames);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void upgradeProcess(int buildNumber) throws UpgradeException {
		String[] upgradeProcesses = PropsUtil.getArray(
			PropsKeys.UPGRADE_PROCESSES);

		for (int i = 0; i < upgradeProcesses.length; i++) {
			if (_log.isDebugEnabled()) {
				_log.debug("Initializing upgrade " + upgradeProcesses[i]);
			}

			UpgradeProcess upgradeProcess = (UpgradeProcess)InstancePool.get(
				upgradeProcesses[i]);

			if (upgradeProcess == null) {
				_log.error(upgradeProcesses[i] + " cannot be found");

				continue;
			}

			if ((upgradeProcess.getThreshold() == 0) ||
				(upgradeProcess.getThreshold() > buildNumber)) {

				if (_log.isDebugEnabled()) {
					_log.debug("Running upgrade " + upgradeProcesses[i]);
				}

				upgradeProcess.upgrade();

				if (_log.isDebugEnabled()) {
					_log.debug("Finished upgrade " + upgradeProcesses[i]);
				}

				_upgraded = true;
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Upgrade threshold " + upgradeProcess.getThreshold() +
							" will not trigger upgrade");

					_log.debug("Skipping upgrade " + upgradeProcesses[i]);
				}
			}
		}
	}

	public void verifyProcess(boolean verified) throws VerifyException {

		// LPS-1880

		int verifyFrequency = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VERIFY_FREQUENCY));

		if ((verifyFrequency == VerifyProcess.ALWAYS) ||
			((verifyFrequency == VerifyProcess.ONCE) && !verified) ||
			(_upgraded)) {

			if (!_upgraded) {
				PropsUtil.set(PropsKeys.INDEX_ON_STARTUP, "true");
			}

			String[] verifyProcesses = PropsUtil.getArray(
				PropsKeys.VERIFY_PROCESSES);

			BatchSessionUtil.setEnabled(true);

			boolean tempIndexReadOnly = SearchEngineUtil.isIndexReadOnly();

			SearchEngineUtil.setIndexReadOnly(true);

			try {
				for (String className : verifyProcesses) {
					verifyProcess(className);
				}
			}
			finally {
				BatchSessionUtil.setEnabled(false);

				SearchEngineUtil.setIndexReadOnly(tempIndexReadOnly);
			}
		}
	}

	public boolean isUpgraded() {
		return _upgraded;
	}

	public boolean isVerified() {
		return _verified;
	}

	protected void addIndexes(Set<String> existingIndexNames) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Adding indexes");
		}

		DBUtil dbUtil = DBUtil.getInstance();

		BufferedReader bufferedReader = new BufferedReader(new StringReader(
			readIndexesSQL()));

		String sql = null;

		while ((sql = bufferedReader.readLine()) != null) {
			if (Validator.isNull(sql)) {
				continue;
			}

			int y = sql.indexOf(" on ");
			int x = sql.lastIndexOf(" ", y - 1);

			String indexName = sql.substring(x + 1, y);

			if (existingIndexNames.contains(indexName)) {
				continue;
			}

			if (_dropIndexes) {
				if (_log.isInfoEnabled()) {
					_log.info(sql);
				}
			}

			try {
				dbUtil.runSQL(sql);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}
		}
	}

	protected void dropIndexes(Set<String> existingIndexNames)
		throws Exception {

		if (!_dropIndexes) {
			return;
		}

		List<Index> indexes = null;

		DBUtil dbUtil = DBUtil.getInstance();

		String type = dbUtil.getType();

		if (type.equals(DBUtil.TYPE_DB2)) {
			indexes = getDB2Indexes();
		}
		else if (type.equals(DBUtil.TYPE_MYSQL)) {
			indexes = getMySQLIndexes();
		}
		else if (type.equals(DBUtil.TYPE_ORACLE)) {
			indexes = getOracleIndexes();
		}
		else if (type.equals(DBUtil.TYPE_POSTGRESQL)) {
			indexes = getPostgreSQLIndexes();
		}
		else if (type.equals(DBUtil.TYPE_SQLSERVER)) {
			indexes = getSQLServerIndexes();
		}
		else if (type.equals(DBUtil.TYPE_SYBASE)) {
			indexes = getSybaseIndexes();
		}

		if ((indexes == null) || indexes.isEmpty()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Dropping stale indexes");
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		String indexPropertiesString = StringUtil.read(
			classLoader,
			"com/liferay/portal/tools/sql/dependencies/indexes.properties");

		Properties indexProperties = PropertiesUtil.load(indexPropertiesString);

		Enumeration<String> indexPropertiesEnu =
			(Enumeration<String>)indexProperties.propertyNames();

		while (indexPropertiesEnu.hasMoreElements()) {
			String key = indexPropertiesEnu.nextElement();

			String value = indexProperties.getProperty(key);

			indexProperties.setProperty(key.toLowerCase(), value);
		}

		String indexesSQLString = readIndexesSQL().toLowerCase();

		String portalTablesSQLString = StringUtil.read(
			classLoader,
			"com/liferay/portal/tools/sql/dependencies/portal-tables.sql");

		portalTablesSQLString = portalTablesSQLString.toLowerCase();

		for (Index index : indexes) {
			String indexName = index.getIndexName().toUpperCase();
			String indexNameLowerCase = indexName.toLowerCase();
			String tableName = index.getTableName();
			String tableNameLowerCase = tableName.toLowerCase();
			boolean unique = index.isUnique();

			existingIndexNames.add(indexName);

			if (indexProperties.containsKey(indexNameLowerCase)) {
				if (unique &&
					indexesSQLString.contains(
						"create unique index " + indexNameLowerCase + " ")) {

					continue;
				}

				if (!unique &&
					indexesSQLString.contains(
						"create index " + indexNameLowerCase + " ")) {

					continue;
				}
			}
			else {
				if (!portalTablesSQLString.contains(
						"create table " + tableNameLowerCase + " (")) {

					continue;
				}
			}

			existingIndexNames.remove(indexName);

			String sql = "drop index " + indexName;

			if (type.equals(DBUtil.TYPE_MYSQL) ||
				type.equals(DBUtil.TYPE_SQLSERVER)) {

				sql += " on " + tableName;
			}

			if (_log.isInfoEnabled()) {
				_log.info(sql);
			}

			dbUtil.runSQL(sql);
		}
	}

	protected List<Index> getDB2Indexes() throws Exception {
		return null;
	}

	protected List<Index> getMySQLIndexes() throws Exception {
		List<Index> indexes = new ArrayList<Index>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select distinct(index_name), table_name, non_unique ");
			sb.append("from information_schema.statistics where ");
			sb.append("index_schema = database() and (index_name like ");
			sb.append("'LIFERAY_%' or index_name like 'IX_%')");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");
				boolean unique = !rs.getBoolean("non_unique");

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return indexes;
	}

	protected List<Index> getOracleIndexes() throws Exception {
		List<Index> indexes = new ArrayList<Index>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select index_name, table_name, uniqueness from ");
			sb.append("user_indexes where index_name like 'LIFERAY_%' or ");
			sb.append("index_name like 'IX_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");
				String uniqueness = rs.getString("uniqueness");

				boolean unique = true;

				if (uniqueness.equalsIgnoreCase("NONUNIQUE")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return indexes;
	}

	protected List<Index> getPostgreSQLIndexes() throws Exception {
		List<Index> indexes = new ArrayList<Index>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select indexname, tablename, indexdef from pg_indexes ");
			sb.append("where indexname like 'liferay_%' or indexname like ");
			sb.append("'ix_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("indexname");
				String tableName = rs.getString("tablename");
				String indexSQL = rs.getString("indexdef").toLowerCase().trim();

				boolean unique = true;

				if (indexSQL.startsWith("create index ")) {
					unique = false;
				}

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return indexes;
	}

	protected List<Index> getSQLServerIndexes() throws Exception {
		List<Index> indexes = new ArrayList<Index>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select sys.tables.name as table_name, ");
			sb.append("sys.indexes.name as index_name, is_unique from ");
			sb.append("sys.indexes inner join sys.tables on ");
			sb.append("sys.tables.object_id = sys.indexes.object_id where ");
			sb.append("sys.indexes.name like 'LIFERAY_%' or sys.indexes.name ");
			sb.append("like 'IX_%'");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				String indexName = rs.getString("index_name");
				String tableName = rs.getString("table_name");
				boolean unique = !rs.getBoolean("is_unique");

				indexes.add(new Index(indexName, tableName, unique));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return indexes;
	}

	protected List<Index> getSybaseIndexes() throws Exception {
		return null;
	}

	protected String readIndexesSQL() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return StringUtil.read(
			classLoader,
			"com/liferay/portal/tools/sql/dependencies/indexes.sql");
	}

	protected void verifyProcess(String className) throws VerifyException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing verification " + className);
		}

		try {
			VerifyProcess verifyProcess = (VerifyProcess)Class.forName(
				className).newInstance();

			if (_log.isDebugEnabled()) {
				_log.debug("Running verification " + className);
			}

			verifyProcess.verify();

			if (_log.isDebugEnabled()) {
				_log.debug("Finished verification " + className);
			}

			_verified = true;
		}
		catch (ClassNotFoundException cnfe) {
			_log.error(className + " cannot be found");
		}
		catch (IllegalAccessException iae) {
			_log.error(className + " cannot be accessed");
		}
		catch (InstantiationException ie) {
			_log.error(className + " cannot be initiated");
		}
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

	private static Log _log = LogFactoryUtil.getLog(StartupHelper.class);

	private boolean _dropIndexes;
	private boolean _upgraded;
	private boolean _verified;

}