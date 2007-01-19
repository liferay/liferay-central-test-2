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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.FileUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeEmailAddress.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradeEmailAddress extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeEmailAddress();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeEmailAddress() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String tempFilename = 
			"temp-db-EmailAddress-" + System.currentTimeMillis();
		
		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_SELECT_EMAIL_ADDRESS);

			rs = ps.executeQuery();

			while (rs.next()) {
				Long emailAddressId = 
					new Long(CounterLocalServiceUtil.increment(
						Counter.class.getName()));
				
				String companyId = rs.getString("companyId");
				String userId = rs.getString("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				String className = rs.getString("className");
				String classPK = rs.getString("classPK");
				String address = rs.getString("address");
				Integer typeId = new Integer(rs.getInt("typeId"));
				Boolean primary = getBoolean(rs, "primary_");

				StringBuffer sb =
					new StringBuffer(_INSERT_EMAIL_ADDRESS);
				
				appendWrappedColumn(sb, emailAddressId);
				appendWrappedColumn(sb, companyId);
				appendWrappedColumn(sb, userId);
				appendWrappedColumn(sb, userName);
				appendWrappedColumn(sb, createDate);
				appendWrappedColumn(sb, modifiedDate);
				appendWrappedColumn(sb, className);
				appendWrappedColumn(sb, classPK);
				appendWrappedColumn(sb, address);
				appendWrappedColumn(sb, typeId);
				appendWrappedColumn(sb, primary, true);

				FileUtil.append(tempFilename, sb.toString());
			}
			
			_log.info("EmailAddress table backed up to file " + tempFilename);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		Statement stmt = null;
		
		try {
			con = HibernateUtil.getConnection();

			stmt = con.createStatement();
			
			stmt.executeUpdate(_DELETE_EMAIL_ADDRESS);
			
			String[] sqlInserts = 
				StringUtil.split(
					FileUtil.read(tempFilename), StringPool.NEW_LINE);
			
			for (int i = 0; i < sqlInserts.length; i++) {
				stmt.executeUpdate(sqlInserts[i]);
			}
			
			FileUtil.delete(tempFilename);

			_log.info("EmailAddress table repopulated with data");
		}
		finally {
			DataAccess.cleanUp(con, stmt);
		}
	}

	private static String _SELECT_EMAIL_ADDRESS = 
		"SELECT * FROM EmailAddress";
	
	private static String _DELETE_EMAIL_ADDRESS = 
		"DELETE FROM EmailAddress";
	
	private static String _INSERT_EMAIL_ADDRESS =
		"INSERT INTO EmailAddress VALUES (";

	private static Log _log = LogFactory.getLog(UpgradeEmailAddress.class);

}