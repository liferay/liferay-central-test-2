/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.util.Constants;
import com.liferay.util.dao.DataAccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;
import org.hibernate.sql.CaseFragment;
import org.hibernate.sql.JoinFragment;

/**
 * <a href="DynamicDialect.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DynamicDialect extends Dialect {

	public DynamicDialect() {

		// Instantiate the proper dialect

		Connection con = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			DatabaseMetaData metaData = con.getMetaData();

			String dbName = metaData.getDatabaseProductName();
			int dbMajorVersion = metaData.getDatabaseMajorVersion();

			_dialect = DialectFactory.determineDialect(dbName, dbMajorVersion);
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			DataAccess.cleanUp(con);
		}

		if (_dialect == null) {
			throw new DynamicDialectException("No dialect found");
		}

		// Synchorize default properties

		Properties dynamicDefaultProps = getDefaultProperties();
		Properties dialectDefaultProps = _dialect.getDefaultProperties();

		dynamicDefaultProps.clear();

		Enumeration enu = dialectDefaultProps.propertyNames();

		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = dialectDefaultProps.getProperty(key);

			dynamicDefaultProps.setProperty(key, value);
		}
	}

	public String appendIdentitySelectToInsert(String insertSQL) {
		return _dialect.appendIdentitySelectToInsert(insertSQL);
	}

	public String appendLockHint(LockMode mode, String tableName) {
		return _dialect.appendLockHint(mode, tableName);
	}

	public boolean bindLimitParametersFirst() {
		return _dialect.bindLimitParametersFirst();
	}

	public boolean bindLimitParametersInReverseOrder() {
		return _dialect.bindLimitParametersInReverseOrder();
	}

	public SQLExceptionConverter buildSQLExceptionConverter() {
		return _dialect.buildSQLExceptionConverter();
	}

	public char closeQuote() {
		return _dialect.closeQuote();
	}

	public CaseFragment createCaseFragment() {
		return _dialect.createCaseFragment();
	}

	public JoinFragment createOuterJoinFragment() {
		return _dialect.createOuterJoinFragment();
	}

	public boolean dropConstraints() {
		return _dialect.dropConstraints();
	}

	public boolean dropTemporaryTableAfterUse() {
		return _dialect.dropTemporaryTableAfterUse();
	}

	public boolean forUpdateOfColumns() {
		return _dialect.forUpdateOfColumns();
	}

	public String generateTemporaryTableName(String baseTableName) {
		return _dialect.generateTemporaryTableName(baseTableName);
	}

	public String getAddColumnString() {
		return _dialect.getAddColumnString();
	}

	public String getAddForeignKeyConstraintString(
		String constraintName, String[] foreignKey, String referencedTable,
		String[] primaryKey, boolean referencesPrimaryKey) {

		return _dialect.getAddForeignKeyConstraintString(
			constraintName, foreignKey, referencedTable, primaryKey,
			referencesPrimaryKey);
	}

	public String getAddPrimaryKeyConstraintString(String constraintName) {
		return _dialect.getAddPrimaryKeyConstraintString(constraintName);
	}

	public String getCascadeConstraintsString() {
		return _dialect.getCascadeConstraintsString();
	}

	public String getCastTypeName(int code) {
		return _dialect.getCastTypeName(code);
	}

	public String getColumnComment(String comment) {
		return _dialect.getColumnComment(comment);
	}

	public String[] getCreateSequenceStrings(String sequenceName)
		throws MappingException {

		return _dialect.getCreateSequenceStrings(sequenceName);
	}

	public String getCreateTemporaryTablePostfix() {
		return _dialect.getCreateTemporaryTablePostfix();
	}

	public String getCreateTemporaryTableString() {
		return _dialect.getCreateTemporaryTableString();
	}

	public String getCurrentTimestampSelectString() {
		return _dialect.getCurrentTimestampSelectString();
	}

	public String getCurrentTimestampSQLFunctionName() {
		return _dialect.getCurrentTimestampSQLFunctionName();
	}

	public String getDropForeignKeyString() {
		return _dialect.getDropForeignKeyString();
	}

	public String[] getDropSequenceStrings(String sequenceName)
		throws MappingException {

		return _dialect.getDropSequenceStrings(sequenceName);
	}

	public String getForUpdateNowaitString() {
		return _dialect.getForUpdateNowaitString();
	}

	public String getForUpdateNowaitString(String aliases) {
		return _dialect.getForUpdateNowaitString(aliases);
	}

	public String getForUpdateString() {
		return _dialect.getForUpdateString();
	}

	public String getForUpdateString(LockMode lockMode) {
		return _dialect.getForUpdateString(lockMode);
	}

	public String getForUpdateString(String aliases) {
		return _dialect.getForUpdateString(aliases);
	}

	public String getHibernateTypeName(int code) throws HibernateException {
		return _dialect.getHibernateTypeName(code);
	}

	public String getHibernateTypeName(
			int code, int length, int precision, int scale)
		throws HibernateException {

		return _dialect.getHibernateTypeName(code, length, precision, scale);
	}

	public String getIdentityColumnString(int type) throws MappingException {
		return _dialect.getIdentityColumnString(type);
	}

	public String getIdentityInsertString() {
		return _dialect.getIdentityInsertString();
	}

	public String getIdentitySelectString(String table, String column, int type)
		throws MappingException {

		return _dialect.getIdentitySelectString(table, column, type);
	}

	public Set getKeywords() {
		return _dialect.getKeywords();
	}

	public String getLimitString(String querySelect, boolean hasOffset) {
		return _dialect.getLimitString(querySelect, hasOffset);
	}

	public String getLimitString(String querySelect, int hasOffset, int limit) {
		return _dialect.getLimitString(querySelect, hasOffset, limit);
	}

	public String getLowercaseFunction() {
		return _dialect.getLowercaseFunction();
	}

	public Class getNativeIdentifierGeneratorClass() {
		return _dialect.getNativeIdentifierGeneratorClass();
	}

	public String getNoColumnsInsertString() {
		return _dialect.getNoColumnsInsertString();
	}

	public String getNullColumnString() {
		return _dialect.getNullColumnString();
	}

	public String getQuerySequencesString() {
		return _dialect.getQuerySequencesString();
	}

	public ResultSet getResultSet(CallableStatement ps) throws SQLException {
		return _dialect.getResultSet(ps);
	}

	public String getSelectClauseNullString(int sqlType) {
		return _dialect.getSelectClauseNullString(sqlType);
	}

	public String getSelectGUIDString() {
		return _dialect.getSelectGUIDString();
	}

	public String getSelectSequenceNextValString(String sequenceName)
		throws MappingException {

		return _dialect.getSelectSequenceNextValString(sequenceName);
	}

	public String getSequenceNextValString(String sequenceName)
		throws MappingException {

		return _dialect.getSequenceNextValString(sequenceName);
	}

	public String getTableTypeString() {
		return _dialect.getTableTypeString();
	}

	public String getTypeName(int code) throws HibernateException {
		return _dialect.getTypeName(code);
	}

	public String getTypeName(int code, int length, int precision, int scale)
		throws HibernateException {

		return _dialect.getTypeName(code, length, precision, scale);
	}

	public ViolatedConstraintNameExtracter
		getViolatedConstraintNameExtracter() {

		return _dialect.getViolatedConstraintNameExtracter();
	}

	public boolean hasAlterTable() {
		return _dialect.hasAlterTable();
	}

	public boolean hasDataTypeInIdentityColumn() {
		return _dialect.hasDataTypeInIdentityColumn();
	}

	public boolean hasSelfReferentialForeignKeyBug() {
		return _dialect.hasSelfReferentialForeignKeyBug();
	}

	public boolean isCurrentTimestampSelectStringCallable() {
		return _dialect.isCurrentTimestampSelectStringCallable();
	}

	public char openQuote() {
		return _dialect.openQuote();
	}

	public boolean qualifyIndexName() {
		return _dialect.qualifyIndexName();
	}

	public int registerResultSetOutParameter(
			CallableStatement statement, int col)
		throws SQLException {

		return _dialect.registerResultSetOutParameter(statement, col);
	}

	public boolean supportsCascadeDelete() {
		return _dialect.supportsCascadeDelete();
	}

	public boolean supportsColumnCheck() {
		return _dialect.supportsColumnCheck();
	}

	public boolean supportsCommentOn() {
		return _dialect.supportsCommentOn();
	}

	public boolean supportsCurrentTimestampSelection() {
		return _dialect.supportsCurrentTimestampSelection();
	}

	public boolean supportsIdentityColumns() {
		return _dialect.supportsIdentityColumns();
	}

	public boolean supportsIfExistsAfterTableName() {
		return _dialect.supportsIfExistsAfterTableName();
	}

	public boolean supportsIfExistsBeforeTableName() {
		return _dialect.supportsIfExistsBeforeTableName();
	}

	public boolean supportsInsertSelectIdentity() {
		return _dialect.supportsInsertSelectIdentity();
	}

	public boolean supportsLimit() {
		return _dialect.supportsLimit();
	}

	public boolean supportsLimitOffset() {
		return _dialect.supportsLimitOffset();
	}

	public boolean supportsNotNullUnique() {
		return _dialect.supportsNotNullUnique();
	}

	public boolean supportsOuterJoinForUpdate() {
		return _dialect.supportsOuterJoinForUpdate();
	}

	public boolean supportsParametersInInsertSelect() {
		return _dialect.supportsParametersInInsertSelect();
	}

	public boolean supportsSequences() {
		return _dialect.supportsSequences();
	}

	public boolean supportsTableCheck() {
		return _dialect.supportsTableCheck();
	}

	public boolean supportsTemporaryTables() {
		return _dialect.supportsTemporaryTables();
	}

	public boolean supportsUnionAll() {
		return _dialect.supportsUnionAll();
	}

	public boolean supportsUnique() {
		return _dialect.supportsUnique();
	}

	public boolean supportsUniqueConstraintInCreateAlterTable() {
		return _dialect.supportsUniqueConstraintInCreateAlterTable();
	}

	public boolean supportsVariableLimit() {
		return _dialect.supportsVariableLimit();
	}

	public String toBooleanValueString(boolean bool) {
		return _dialect.toBooleanValueString(bool);
	}

	public String toString() {
		if (_dialect != null) {
			return _dialect.toString();
		}
		else {
			return null;
		}
	}

	public String transformSelectString(String select) {
		return _dialect.transformSelectString(select);
	}

	public boolean useInputStreamToInsertBlob() {
		return _dialect.useInputStreamToInsertBlob();
	}

	public boolean useMaxForLimit() {
		return _dialect.useMaxForLimit();
	}

	private static Log _log = LogFactory.getLog(DynamicDialect.class);

	private Dialect _dialect;

}