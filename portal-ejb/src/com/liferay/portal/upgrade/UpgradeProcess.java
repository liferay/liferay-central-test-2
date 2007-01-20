/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.DateUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.hibernate.BooleanType;
import com.liferay.util.dao.hibernate.FloatType;
import com.liferay.util.dao.hibernate.IntegerType;
import com.liferay.util.dao.hibernate.LongType;
import com.liferay.util.dao.hibernate.ShortType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.DateFormat;

import java.util.Date;

import org.hibernate.usertype.UserType;

/**
 * <a href="UpgradeProcess.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public abstract class UpgradeProcess {

	public UpgradeProcess() {
	}

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public abstract void upgrade() throws UpgradeException;

	public void upgrade(UpgradeProcess upgradeProgress)
		throws UpgradeException {

		upgradeProgress.upgrade();
	}

	protected void appendColumn(StringBuffer sb, Object value)
		throws Exception {

		appendColumn(sb, value, false);
	}

	protected void appendColumn(StringBuffer sb, Object value, boolean last)
		throws Exception {

		if (value == null) {
			throw new UpgradeException(
				"Nulls should never be inserted into the database");
		}
		else if (value instanceof String) {
			sb.append(
				StringUtil.replace(
					(String)value, StringPool.COMMA, _SAFE_COMMA_CHARACTER));
		}
		else if (value instanceof Date) {
			DateFormat df = DateUtil.getISOFormat();

			sb.append(df.format(value));
		}
		else {
			sb.append(value);
		}

		if (last) {
			sb.append(StringPool.NEW_LINE);
		}
		else {
			sb.append(StringPool.COMMA);
		}
	}

	protected void appendColumn(
			StringBuffer sb, ResultSet rs, String name, Integer type)
		throws Exception {

		appendColumn(sb, rs, name, type, false);
	}

	protected void appendColumn(
			StringBuffer sb, ResultSet rs, String name, Integer type,
			boolean last)
		throws Exception {

		Object value = null;

		int t = type.intValue();

		UserType userType = null;

		if (t == Types.BIGINT) {
			userType = new LongType();
		}
		else if (t == Types.BOOLEAN) {
			userType = new BooleanType();
		}
		else if (t == Types.TIMESTAMP) {
			try {
				value = rs.getObject(name);
			}
			catch (Exception e) {
			}

			if (value == null) {
				value = new Date();
			}
		}
		else if (t == Types.FLOAT) {
			userType = new FloatType();
		}
		else if (t == Types.INTEGER) {
			userType = new IntegerType();
		}
		else if (t == Types.SMALLINT) {
			userType = new ShortType();
		}
		else if (t == Types.VARCHAR) {
			value = GetterUtil.getString(rs.getString(name));
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}

		if (userType != null) {
			value = userType.nullSafeGet(rs, new String[] {name}, null);
		}

		appendColumn(sb, value, last);
	}

	protected void setColumn(
			PreparedStatement ps, int index, Integer type, String value)
		throws Exception {

		int t = type.intValue();

		if (t == Types.BIGINT) {
			ps.setLong(index, GetterUtil.getLong(value));
		}
		else if (t == Types.BOOLEAN) {
			ps.setBoolean(index, GetterUtil.getBoolean(value));
		}
		else if (t == Types.TIMESTAMP) {
			DateFormat df = DateUtil.getISOFormat();

			ps.setTimestamp(
				index, new Timestamp(df.parse(value).getTime()));
		}
		else if (t == Types.FLOAT) {
			ps.setFloat(index, GetterUtil.getFloat(value));
		}
		else if (t == Types.INTEGER) {
			ps.setInt(index, GetterUtil.getInteger(value));
		}
		else if (t == Types.SMALLINT) {
			ps.setShort(index, GetterUtil.getShort(value));
		}
		else if (t == Types.VARCHAR) {
			value =
				StringUtil.replace(
					value, _SAFE_COMMA_CHARACTER, StringPool.COMMA);

			ps.setString(index, value);
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}
	}

	private static final String _SAFE_COMMA_CHARACTER =
		"_SAFE_COMMA_CHARACTER_";

}