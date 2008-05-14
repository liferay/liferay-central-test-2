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

package com.liferay.portal.upgrade.v5_0_2;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {

		// LEP-5761

		while (getMessageIdsCount() > 0) {
			updateMessage();
		}
	}

	protected long getMessageIdsCount() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("select count(*) from ");
		sm.append("MBMessage childMessage ");
		sm.append("inner join MBMessage parentMessage on ");
		sm.append("childMessage.parentMessageId = parentMessage.messageId ");
		sm.append("where parentMessage.categoryId != childMessage.categoryId ");
		sm.append("or parentMessage.threadId != childMessage.threadId");

		String sql = sm.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong(1);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessage() throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("select childMessage.messageId, parentMessage.categoryId, ");
		sm.append("parentMessage.threadId ");
		sm.append("from MBMessage childMessage ");
		sm.append("inner join MBMessage parentMessage on ");
		sm.append("childMessage.parentMessageId = parentMessage.messageId ");
		sm.append("where parentMessage.categoryId != childMessage.categoryId ");
		sm.append("or parentMessage.threadId != childMessage.threadId");

		String sql = sm.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong(1);
				long categoryId = rs.getLong(2);
				long threadId = rs.getLong(3);

				runSQL(
					"update MBMessage set categoryId = " + categoryId +
						", threadId = " + threadId + " where messageId = " +
							messageId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}