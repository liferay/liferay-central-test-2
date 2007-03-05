/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.StagnantRowException;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ResourceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ResourceUtil {

	public static void upgradePrimKey(ValueMapper pkMapper, String name)
		throws Exception {
		
		upgradePrimKey(pkMapper, name, true);
	}
	
	public static void upgradePrimKey(
			ValueMapper pkMapper, String name, boolean isLong)
		throws Exception {

		_log.info("Upgrading Resource_.primKey with name: " + name);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Grab appropriate primKeys

		Set oldPks = new HashSet();

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_SELECT_PRIMKEY);

			ps.setString(1, name);
			ps.setString(2, ResourceImpl.SCOPE_INDIVIDUAL);

			rs = ps.executeQuery();

			while (rs.next()) {
				oldPks.add(rs.getString("primKey"));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// Update primKeys

		Set badPks = new HashSet();

		boolean useBatch = false;

		try {
			con = HibernateUtil.getConnection();

			useBatch = con.getMetaData().supportsBatchUpdates();

			if (!useBatch) {
				if (_log.isInfoEnabled()) {
					_log.info("Database does not support batch updates");
				}
			}

			int count = 0;

			Iterator itr = oldPks.iterator();

			while (itr.hasNext()) {
				if (count == 0) {
					ps = con.prepareStatement(_UPDATE_PRIMKEY);
				}

				String oldPk = (String)itr.next();

				String newPk = null;

				try {
					if (isLong) {
						Long longPk = new Long(oldPk);

						newPk = String.valueOf(pkMapper.getNewValue(longPk));
					}
					else {
						newPk = (String)pkMapper.getNewValue(oldPk);
					}
				}
				catch (StagnantRowException sre) {
					_log.warn(
						"Resource_.primKey has stagnant data where " +
						"Resource_.name like '" + name + "': " +
						sre.getMessage());

					badPks.add(oldPk);

					continue;
				}

				ps.setString(1, newPk);
				ps.setString(2, name);
				ps.setString(3, ResourceImpl.SCOPE_INDIVIDUAL);
				ps.setString(4, oldPk);

				if (useBatch) {
					ps.addBatch();

					if (count == _BATCH_SIZE) {
						ps.executeBatch();

						ps.close();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps.executeUpdate();

					ps.close();
				}
			}

			if (useBatch) {
				if (count != 0) {
					ps.executeBatch();

					ps.close();
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}

		// Remove stagnant data

		try {
			con = HibernateUtil.getConnection();

			int count = 0;

			Iterator itr = badPks.iterator();

			while (itr.hasNext()) {
				if (count == 0) {
					ps = con.prepareStatement(_CLEAN_PRIMKEY);
				}

				String badPk = (String)itr.next();

				ps.setString(1, name);
				ps.setString(2, ResourceImpl.SCOPE_INDIVIDUAL);
				ps.setString(3, badPk);

				if (useBatch) {
					ps.addBatch();

					if (count == _BATCH_SIZE) {
						ps.executeBatch();

						ps.close();

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					ps.executeUpdate();

					ps.close();
				}
			}

			if (useBatch) {
				if (count != 0) {
					ps.executeBatch();

					ps.close();
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final int _BATCH_SIZE = GetterUtil.getInteger(
			PropsUtil.get("hibernate.jdbc.batch_size"));

	private static final String _SELECT_PRIMKEY = 
		"SELECT * FROM Resource_ WHERE name = ? AND scope = ?";

	private static final String _UPDATE_PRIMKEY =
		"UPDATE Resource_ SET primKey = ? WHERE name = ? AND scope = ? AND primKey = ?";

	private static final String _CLEAN_PRIMKEY = 
		"DELETE FROM Resource_ WHERE name = ? AND scope = ? AND primKey = ?";

	private static Log _log = LogFactory.getLog(ResourceUtil.class.getName());

}