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

package com.liferay.portal.upgrade.v5_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// LEP-5761

		while (getMessageIdsCount() > 0) {
			updateMessage();
		}
	}

	protected long getMessageIdsCount() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select count(*) from ");
		sb.append("MBMessage childMessage ");
		sb.append("inner join MBMessage parentMessage on ");
		sb.append("childMessage.parentMessageId = parentMessage.messageId ");
		sb.append("where parentMessage.categoryId != childMessage.categoryId ");
		sb.append("or parentMessage.threadId != childMessage.threadId");

		String sql = sb.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

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
		StringBundler sb = new StringBundler(7);

		sb.append("select childMessage.messageId, parentMessage.categoryId, ");
		sb.append("parentMessage.threadId ");
		sb.append("from MBMessage childMessage ");
		sb.append("inner join MBMessage parentMessage on ");
		sb.append("childMessage.parentMessageId = parentMessage.messageId ");
		sb.append("where parentMessage.categoryId != childMessage.categoryId ");
		sb.append("or parentMessage.threadId != childMessage.threadId");

		String sql = sb.toString();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

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

}