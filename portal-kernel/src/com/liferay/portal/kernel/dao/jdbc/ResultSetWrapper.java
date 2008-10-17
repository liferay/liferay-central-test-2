/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.util.CharPool;

/**
 * <a href="ResultSetWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Minhchau Dang
 */
public class ResultSetWrapper implements ResultSet {

	public ResultSetWrapper(ResultSet rs)
		throws SQLException {

		_rs = rs;

		_columnIndexCache = new HashMap<String, Integer>();

		_metaData = _rs.getMetaData();

		_columnCount = _metaData.getColumnCount();
	}

	public boolean absolute(int row)
		throws SQLException {

		return _rs.absolute(row);
	}

	public void afterLast()
		throws SQLException {

		_rs.afterLast();
	}

	public void beforeFirst()
		throws SQLException {

		_rs.beforeFirst();
	}

	public void cancelRowUpdates()
		throws SQLException {

		_rs.cancelRowUpdates();
	}

	public void clearWarnings()
		throws SQLException {

		_rs.clearWarnings();
	}

	public void close()
		throws SQLException {

		_rs.close();
	}

	public void deleteRow()
		throws SQLException {

		_rs.deleteRow();
	}

	public int findColumn(String columnLabel)
		throws SQLException {

		Integer columnIndex = _columnIndexCache.get(columnLabel);

		if (columnIndex != null) {
			return columnIndex;
		}

		// Check for the full column name

		for (int i = 1; i <= _columnCount; ++i) {
			String availableName = _metaData.getColumnName(i);

			if (availableName.equalsIgnoreCase(columnLabel)) {
				_columnIndexCache.put(columnLabel, i);

				return i;
			}

		}

		// Check for a shortened column name

		int pos = columnLabel.indexOf(CharPool.PERIOD);

		if (pos != -1) {
			String shortName = columnLabel.substring(pos + 1);

			for (int i = 1; i <= _columnCount; ++i) {
				String availableName = _metaData.getColumnName(i);

				if (availableName.equalsIgnoreCase(shortName)) {
					_columnIndexCache.put(columnLabel, i);

					return i;
				}
			}
		}

		// Let the result set figure it out

		columnIndex = _rs.findColumn(columnLabel);

		_columnIndexCache.put(columnLabel, columnIndex);

		return columnIndex;
	}

	public boolean first()
		throws SQLException {

		return _rs.first();
	}

	public Array getArray(int i)
		throws SQLException {

		return _rs.getArray(i);
	}

	public Array getArray(String colName)
		throws SQLException {

		return _rs.getArray(colName);
	}

	public InputStream getAsciiStream(int columnIndex)
		throws SQLException {

		return _rs.getAsciiStream(columnIndex);
	}

	public InputStream getAsciiStream(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getAsciiStream(columnIndex);
	}

	public BigDecimal getBigDecimal(int columnIndex)
		throws SQLException {

		return _rs.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale)
		throws SQLException {

		return _rs.getBigDecimal(columnIndex, scale);
	}

	public BigDecimal getBigDecimal(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getBigDecimal(columnIndex, scale);
	}

	public InputStream getBinaryStream(int columnIndex)
		throws SQLException {

		return _rs.getBinaryStream(columnIndex);
	}

	public InputStream getBinaryStream(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getBinaryStream(columnIndex);
	}

	public Blob getBlob(int i)
		throws SQLException {

		return _rs.getBlob(i);
	}

	public Blob getBlob(String colName)
		throws SQLException {

		return _rs.getBlob(colName);
	}

	public boolean getBoolean(int columnIndex)
		throws SQLException {

		return _rs.getBoolean(columnIndex);
	}

	public boolean getBoolean(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getBoolean(columnIndex);
	}

	public byte getByte(int columnIndex)
		throws SQLException {

		return _rs.getByte(columnIndex);
	}

	public byte getByte(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getByte(columnIndex);
	}

	public byte[] getBytes(int columnIndex)
		throws SQLException {

		return _rs.getBytes(columnIndex);
	}

	public byte[] getBytes(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getBytes(columnIndex);
	}

	public Reader getCharacterStream(int columnIndex)
		throws SQLException {

		return _rs.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getCharacterStream(columnIndex);
	}

	public Clob getClob(int i)
		throws SQLException {

		return _rs.getClob(i);
	}

	public Clob getClob(String colName)
		throws SQLException {

		return _rs.getClob(colName);
	}

	public int getConcurrency()
		throws SQLException {

		return _rs.getConcurrency();
	}

	public String getCursorName()
		throws SQLException {

		return _rs.getCursorName();
	}

	public Date getDate(int columnIndex)
		throws SQLException {

		return _rs.getDate(columnIndex);
	}

	public Date getDate(int columnIndex, Calendar cal)
		throws SQLException {

		return _rs.getDate(columnIndex, cal);
	}

	public Date getDate(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getDate(columnIndex);
	}

	public Date getDate(String columnLabel, Calendar cal)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getDate(columnIndex, cal);
	}

	public double getDouble(int columnIndex)
		throws SQLException {

		return _rs.getDouble(columnIndex);
	}

	public double getDouble(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getDouble(columnIndex);
	}

	public int getFetchDirection()
		throws SQLException {

		return _rs.getFetchDirection();
	}

	public int getFetchSize()
		throws SQLException {

		return _rs.getFetchSize();
	}

	public float getFloat(int columnIndex)
		throws SQLException {

		return _rs.getFloat(columnIndex);
	}

	public float getFloat(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getFloat(columnIndex);
	}

	public int getHoldability()
		throws SQLException {

		return _rs.getHoldability();
	}

	public int getInt(int columnIndex)
		throws SQLException {

		return _rs.getInt(columnIndex);
	}

	public int getInt(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getInt(columnIndex);
	}

	public long getLong(int columnIndex)
		throws SQLException {

		return _rs.getLong(columnIndex);
	}

	public long getLong(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getLong(columnIndex);
	}

	public ResultSetMetaData getMetaData()
		throws SQLException {

		return _rs.getMetaData();
	}

	public Reader getNCharacterStream(int columnIndex)
		throws SQLException {

		return _rs.getNCharacterStream(columnIndex);
	}

	public Reader getNCharacterStream(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getNCharacterStream(columnIndex);
	}

	public NClob getNClob(int columnIndex)
		throws SQLException {

		return _rs.getNClob(columnIndex);
	}

	public NClob getNClob(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getNClob(columnIndex);
	}

	public String getNString(int columnIndex)
		throws SQLException {

		return _rs.getNString(columnIndex);
	}

	public String getNString(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getNString(columnIndex);
	}

	public Object getObject(int columnIndex)
		throws SQLException {

		return _rs.getObject(columnIndex);
	}

	public Object getObject(int i, Map<String, Class<?>> map)
		throws SQLException {

		return _rs.getObject(i, map);
	}

	public Object getObject(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getObject(columnIndex);
	}

	public Object getObject(String colName, Map<String, Class<?>> map)
		throws SQLException {

		return _rs.getObject(colName, map);
	}

	public Ref getRef(int i)
		throws SQLException {

		return _rs.getRef(i);
	}

	public Ref getRef(String colName)
		throws SQLException {

		return _rs.getRef(colName);
	}

	public int getRow()
		throws SQLException {

		return _rs.getRow();
	}

	public RowId getRowId(int columnIndex)
		throws SQLException {

		return _rs.getRowId(columnIndex);
	}

	public RowId getRowId(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getRowId(columnIndex);
	}

	public short getShort(int columnIndex)
		throws SQLException {

		return _rs.getShort(columnIndex);
	}

	public short getShort(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getShort(columnIndex);
	}

	public SQLXML getSQLXML(int columnIndex)
		throws SQLException {

		return _rs.getSQLXML(columnIndex);
	}

	public SQLXML getSQLXML(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getSQLXML(columnIndex);
	}

	public Statement getStatement()
		throws SQLException {

		return _rs.getStatement();
	}

	public String getString(int columnIndex)
		throws SQLException {

		return _rs.getString(columnIndex);
	}

	public String getString(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getString(columnIndex);
	}

	public Time getTime(int columnIndex)
		throws SQLException {

		return _rs.getTime(columnIndex);
	}

	public Time getTime(int columnIndex, Calendar cal)
		throws SQLException {

		return _rs.getTime(columnIndex, cal);
	}

	public Time getTime(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getTime(columnIndex);
	}

	public Time getTime(String columnLabel, Calendar cal)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getTime(columnIndex, cal);
	}

	public Timestamp getTimestamp(int columnIndex)
		throws SQLException {

		return _rs.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
		throws SQLException {

		return _rs.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getTimestamp(columnIndex, cal);
	}

	public int getType()
		throws SQLException {

		return _rs.getType();
	}

	public InputStream getUnicodeStream(int columnIndex)
		throws SQLException {

		return _rs.getUnicodeStream(columnIndex);
	}

	public InputStream getUnicodeStream(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getUnicodeStream(columnIndex);
	}

	public URL getURL(int columnIndex)
		throws SQLException {

		return _rs.getURL(columnIndex);
	}

	public URL getURL(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		return _rs.getURL(columnIndex);
	}

	public SQLWarning getWarnings()
		throws SQLException {

		return _rs.getWarnings();
	}

	public void insertRow()
		throws SQLException {

		_rs.insertRow();
	}

	public boolean isAfterLast()
		throws SQLException {

		return _rs.isAfterLast();
	}

	public boolean isBeforeFirst()
		throws SQLException {

		return _rs.isBeforeFirst();
	}

	public boolean isClosed()
		throws SQLException {

		return _rs.isClosed();
	}

	public boolean isFirst()
		throws SQLException {

		return _rs.isFirst();
	}

	public boolean isLast()
		throws SQLException {

		return _rs.isLast();
	}

	public boolean isWrapperFor(Class<?> arg0)
		throws SQLException {

		return _rs.isWrapperFor(arg0);
	}

	public boolean last()
		throws SQLException {

		return _rs.last();
	}

	public void moveToCurrentRow()
		throws SQLException {

		_rs.moveToCurrentRow();
	}

	public void moveToInsertRow()
		throws SQLException {

		_rs.moveToInsertRow();
	}

	public boolean next()
		throws SQLException {

		return _rs.next();
	}

	public boolean previous()
		throws SQLException {

		return _rs.previous();
	}

	public void refreshRow()
		throws SQLException {

		_rs.refreshRow();
	}

	public boolean relative(int rows)
		throws SQLException {

		return _rs.relative(rows);
	}

	public boolean rowDeleted()
		throws SQLException {

		return _rs.rowDeleted();
	}

	public boolean rowInserted()
		throws SQLException {

		return _rs.rowInserted();
	}

	public boolean rowUpdated()
		throws SQLException {

		return _rs.rowUpdated();
	}

	public void setFetchDirection(int direction)
		throws SQLException {

		_rs.setFetchDirection(direction);
	}

	public void setFetchSize(int rows)
		throws SQLException {

		_rs.setFetchSize(rows);
	}

	public <T> T unwrap(Class<T> arg0)
		throws SQLException {

		return _rs.unwrap(arg0);
	}

	public void updateArray(int columnIndex, Array x)
		throws SQLException {

		_rs.updateArray(columnIndex, x);
	}

	public void updateArray(String columnLabel, Array x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateArray(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x)
		throws SQLException {

		_rs.updateAsciiStream(columnIndex, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
		throws SQLException {

		_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length)
		throws SQLException {

		_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateAsciiStream(columnIndex, x);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateAsciiStream(columnIndex, x, length);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
		throws SQLException {

		_rs.updateBigDecimal(columnIndex, x);
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBigDecimal(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x)
		throws SQLException {

		_rs.updateBinaryStream(columnIndex, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
		throws SQLException {

		_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length)
		throws SQLException {

		_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBinaryStream(String columnLabel, InputStream x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBinaryStream(columnIndex, x);
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBinaryStream(
		String columnLabel, InputStream x, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBlob(int columnIndex, Blob x)
		throws SQLException {

		_rs.updateBlob(columnIndex, x);
	}

	public void updateBlob(int columnIndex, InputStream inputStream)
		throws SQLException {

		_rs.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(int columnIndex, InputStream inputStream, long length)
		throws SQLException {

		_rs.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBlob(String columnLabel, Blob x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnLabel, InputStream inputStream)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBlob(columnIndex, inputStream);
	}

	public void updateBlob(
		String columnLabel, InputStream inputStream, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBlob(columnIndex, inputStream, length);
	}

	public void updateBoolean(int columnIndex, boolean x)
		throws SQLException {

		_rs.updateBoolean(columnIndex, x);
	}

	public void updateBoolean(String columnLabel, boolean x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBoolean(columnIndex, x);
	}

	public void updateByte(int columnIndex, byte x)
		throws SQLException {

		_rs.updateByte(columnIndex, x);
	}

	public void updateByte(String columnLabel, byte x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateByte(columnIndex, x);
	}

	public void updateBytes(int columnIndex, byte[] x)
		throws SQLException {

		_rs.updateBytes(columnIndex, x);
	}

	public void updateBytes(String columnLabel, byte[] x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateBytes(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x)
		throws SQLException {

		_rs.updateCharacterStream(columnIndex, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
		throws SQLException {

		_rs.updateCharacterStream(columnIndex, x, length);
	}

	public void updateCharacterStream(int columnIndex, Reader x, long length)
		throws SQLException {

		_rs.updateCharacterStream(columnIndex, x, length);
	}

	public void updateCharacterStream(String columnLabel, Reader reader)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateCharacterStream(columnIndex, reader);
	}

	public void updateCharacterStream(
		String columnLabel, Reader reader, int length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateCharacterStream(columnIndex, reader, length);
	}

	public void updateCharacterStream(
		String columnLabel, Reader reader, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateCharacterStream(columnIndex, reader, length);
	}

	public void updateClob(int columnIndex, Clob x)
		throws SQLException {

		_rs.updateClob(columnIndex, x);
	}

	public void updateClob(int columnIndex, Reader reader)
		throws SQLException {

		_rs.updateClob(columnIndex, reader);
	}

	public void updateClob(int columnIndex, Reader reader, long length)
		throws SQLException {

		_rs.updateClob(columnIndex, reader, length);
	}

	public void updateClob(String columnLabel, Clob x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateClob(columnIndex, x);
	}

	public void updateClob(String columnLabel, Reader reader)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateClob(columnIndex, reader);
	}

	public void updateClob(String columnLabel, Reader reader, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateClob(columnIndex, reader, length);
	}

	public void updateDate(int columnIndex, Date x)
		throws SQLException {

		_rs.updateDate(columnIndex, x);
	}

	public void updateDate(String columnLabel, Date x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateDate(columnIndex, x);
	}

	public void updateDouble(int columnIndex, double x)
		throws SQLException {

		_rs.updateDouble(columnIndex, x);
	}

	public void updateDouble(String columnLabel, double x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateDouble(columnIndex, x);
	}

	public void updateFloat(int columnIndex, float x)
		throws SQLException {

		_rs.updateFloat(columnIndex, x);
	}

	public void updateFloat(String columnLabel, float x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateFloat(columnIndex, x);
	}

	public void updateInt(int columnIndex, int x)
		throws SQLException {

		_rs.updateInt(columnIndex, x);
	}

	public void updateInt(String columnLabel, int x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateInt(columnIndex, x);
	}

	public void updateLong(int columnIndex, long x)
		throws SQLException {

		_rs.updateLong(columnIndex, x);
	}

	public void updateLong(String columnLabel, long x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateLong(columnIndex, x);
	}

	public void updateNCharacterStream(int columnIndex, Reader x)
		throws SQLException {

		_rs.updateNCharacterStream(columnIndex, x);
	}

	public void updateNCharacterStream(int columnIndex, Reader x, long length)
		throws SQLException {

		_rs.updateNCharacterStream(columnIndex, x, length);
	}

	public void updateNCharacterStream(String columnLabel, Reader reader)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNCharacterStream(columnIndex, reader);
	}

	public void updateNCharacterStream(
		String columnLabel, Reader reader, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNCharacterStream(columnIndex, reader, length);
	}

	public void updateNClob(int columnIndex, NClob clob)
		throws SQLException {

		_rs.updateNClob(columnIndex, clob);
	}

	public void updateNClob(int columnIndex, Reader reader)
		throws SQLException {

		_rs.updateNClob(columnIndex, reader);
	}

	public void updateNClob(int columnIndex, Reader reader, long length)
		throws SQLException {

		_rs.updateNClob(columnIndex, reader, length);
	}

	public void updateNClob(String columnLabel, NClob clob)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNClob(columnIndex, clob);
	}

	public void updateNClob(String columnLabel, Reader reader)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNClob(columnIndex, reader);
	}

	public void updateNClob(String columnLabel, Reader reader, long length)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNClob(columnIndex, reader, length);
	}

	public void updateNString(int columnIndex, String string)
		throws SQLException {

		_rs.updateNString(columnIndex, string);
	}

	public void updateNString(String columnLabel, String string)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNString(columnIndex, string);
	}

	public void updateNull(int columnIndex)
		throws SQLException {

		_rs.updateNull(columnIndex);
	}

	public void updateNull(String columnLabel)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateNull(columnIndex);
	}

	public void updateObject(int columnIndex, Object x)
		throws SQLException {

		_rs.updateObject(columnIndex, x);
	}

	public void updateObject(int columnIndex, Object x, int scale)
		throws SQLException {

		_rs.updateObject(columnIndex, x, scale);
	}

	public void updateObject(String columnLabel, Object x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateObject(columnIndex, x);
	}

	public void updateObject(String columnLabel, Object x, int scale)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateObject(columnIndex, x, scale);
	}

	public void updateRef(int columnIndex, Ref x)
		throws SQLException {

		_rs.updateRef(columnIndex, x);
	}

	public void updateRef(String columnLabel, Ref x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateRef(columnIndex, x);
	}

	public void updateRow()
		throws SQLException {

		_rs.updateRow();
	}

	public void updateRowId(int columnIndex, RowId x)
		throws SQLException {

		_rs.updateRowId(columnIndex, x);
	}

	public void updateRowId(String columnLabel, RowId x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateRowId(columnIndex, x);
	}

	public void updateShort(int columnIndex, short x)
		throws SQLException {

		_rs.updateShort(columnIndex, x);
	}

	public void updateShort(String columnLabel, short x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateShort(columnIndex, x);
	}

	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
		throws SQLException {

		_rs.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateSQLXML(columnIndex, xmlObject);
	}

	public void updateString(int columnIndex, String x)
		throws SQLException {

		_rs.updateString(columnIndex, x);
	}

	public void updateString(String columnLabel, String x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateString(columnIndex, x);
	}

	public void updateTime(int columnIndex, Time x)
		throws SQLException {

		_rs.updateTime(columnIndex, x);
	}

	public void updateTime(String columnLabel, Time x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateTime(columnIndex, x);
	}
	public void updateTimestamp(int columnIndex, Timestamp x)
		throws SQLException {

		_rs.updateTimestamp(columnIndex, x);
	}
	public void updateTimestamp(String columnLabel, Timestamp x)
		throws SQLException {

		int columnIndex = findColumn(columnLabel);

		_rs.updateTimestamp(columnIndex, x);
	}

	public boolean wasNull()
		throws SQLException {

		return _rs.wasNull();
	}

	private final int _columnCount;

	private final Map<String, Integer> _columnIndexCache;

	private final ResultSetMetaData _metaData;

	private final ResultSet _rs;

}
