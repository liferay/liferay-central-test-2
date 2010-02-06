/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MultiValueMap;
import com.liferay.portal.kernel.util.SerializableUtil;

import java.io.File;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="FileMultiValueMap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class FileMultiValueMap<K extends Serializable, V extends Serializable>
	extends MultiValueMap<K, V> {

	public FileMultiValueMap() {
		_fileName = FileUtil.createTempFileName();

		try {
			Class.forName("org.hsqldb.jdbcDriver");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		_createDatabase();
	}

	public void clear() {
		try {
			_deleteDatabase();
			_createDatabase();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public boolean containsKey(Object key) {
		int count = _getCount((K)key, null);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean containsValue(Object value) {
		int count = _getCount(null, (V)value);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Set<V> getAll(Object key) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Set<V> values = null;

		try {
			con = _getConnection();

			ps = con.prepareStatement("SELECT value_ FROM Map WHERE key_ = ?");

			ps.setBytes(1, SerializableUtil.serialize(key));

			rs = ps.executeQuery();

			while (rs.next()) {
				if (values == null) {
					values = new HashSet<V>();
				}

				V value = null;

				value = (V)SerializableUtil.deserialize(rs.getBytes(_VALUE));

				values.add(value);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return values;
	}

	public boolean isEmpty() {
		int count = _getCount(null, null);

		if (count == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Set<K> keySet() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Set<K> keys = null;

		try {
			con = _getConnection();

			ps = con.prepareStatement("SELECT DISTINCT (key_) FROM Map ");

			rs = ps.executeQuery();

			while (rs.next()) {
				if (keys == null) {
					keys = new HashSet<K>();
				}

				K key = null;

				key = (K)SerializableUtil.deserialize(rs.getBytes(_KEY));

				keys.add(key);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return keys;
	}

	public V put(K key, V value) {
		if ((key == null) || (value == null)) {
			return null;
		}

		if (_getCount(key, value) == 0) {
			Connection con = null;
			PreparedStatement ps = null;

			try {
				con = _getConnection();

				ps = con.prepareStatement(
					"INSERT INTO Map (key_, value_) values (?, ?)");

				ps.setBytes(1, SerializableUtil.serialize(key));
				ps.setBytes(2, SerializableUtil.serialize(value));

				ps.execute();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				DataAccess.cleanUp(con, ps);
			}
		}

		return value;
	}

	public Set<V> putAll(K key, Collection<? extends V> values) {
		Set<V> curValues = getAll(key);

		if ((values == null) || values.isEmpty()) {
			return curValues;
		}

		if (curValues == null) {
			values = new HashSet<V>();
		}

		for (V value: values) {
			if (!curValues.contains(value)) {
				curValues.add(value);

				put(key, value);
			}
		}

		return curValues;
	}

	public V remove(Object key) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		V firstValue = null;

		try {
			con = _getConnection();

			ps = con.prepareStatement("SELECT value_ FROM Map WHERE key_ = ?");

			ps.setBytes(1, SerializableUtil.serialize(key));

			rs = ps.executeQuery();

			if (rs.next()) {
				firstValue = (V)SerializableUtil.deserialize(
					rs.getBytes(_VALUE));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = _getConnection();

			ps = con.prepareStatement("DELETE FROM Map WHERE key_ = ?");

			ps.setBytes(1, SerializableUtil.serialize(key));

			ps.execute();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		return firstValue;
	}

	protected void finalize() throws Throwable {
		try {
			_deleteDatabase();
		}
		finally {
			super.finalize();
		}
	}

	private void _createDatabase() {
		Connection con = null;

		try {
			con = _getConnection();

			DB db = DBFactoryUtil.getDB(DB.TYPE_HYPERSONIC);

			db.runSQL(con, _CREATE_SQL);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	private void _deleteDatabase() throws Throwable {
		File[] files = new File[] {
			new File(_fileName + ".properties"),
			new File(_fileName + ".script"),
			new File(_fileName + ".log"),
			new File(_fileName + ".data"),
			new File(_fileName + ".backup")
		};

		for (File file : files) {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	private Connection _getConnection() throws Exception {
		return DriverManager.getConnection(
			"jdbc:hsqldb:file:" + _fileName, "sa", "");
	}

	private int _getCount(K key, V value) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = _getConnection();

			String sql = "SELECT count(*) FROM Map ";

			if ((key != null) && (value != null)) {
				sql += "WHERE key_ = ? AND value_ = ?";

				ps = con.prepareStatement(sql);

				ps.setBytes(1, SerializableUtil.serialize(key));
				ps.setBytes(2, SerializableUtil.serialize(value));
			}
			else if (key != null) {
				sql += "WHERE key_ = ?";

				ps = con.prepareStatement(sql);

				ps.setBytes(1, SerializableUtil.serialize(key));
			}
			else if (value != null) {
				sql += "WHERE value_ = ?";

				ps = con.prepareStatement(sql);

				ps.setBytes(1, SerializableUtil.serialize(value));
			}
			else {
				ps = con.prepareStatement(sql);
			}

			rs = ps.executeQuery();

			rs.next();

			return rs.getInt(1);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _CREATE_SQL =
		"CREATE TABLE Map (key_ BLOB not null, value_ BLOB not null, primary " +
			"key (key_, value_))";

	private static final String _KEY = "key_";

	private static final String _VALUE = "value_";

	private String _fileName;

}