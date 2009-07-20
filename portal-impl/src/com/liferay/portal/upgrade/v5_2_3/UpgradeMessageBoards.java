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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		updateGroupId();
		updateMessageClassNameId();
		updateMessageFlagThreadId();
		updateMessagePriority();
	}

	protected void updateGroupId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select categoryId, groupId from MBCategory");

			rs = ps.executeQuery();

			while (rs.next()) {
				long categoryId = rs.getLong("categoryId");
				long groupId = rs.getLong("groupId");

				int threadCount =
					MBThreadLocalServiceUtil.getCategoryThreadsCount(
						categoryId);
				int messageCount =
					MBMessageLocalServiceUtil.getCategoryMessagesCount(
						categoryId);

				runSQL(
					"update MBCategory set threadCount = " + threadCount +
						", messageCount = " + messageCount +
							" where categoryId = " + categoryId);
				runSQL(
					"update MBMessage set groupId = " + groupId +
						" where categoryId = " + categoryId);
				runSQL(
					"update MBThread set groupId = " + groupId +
						" where categoryId = " + categoryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessageClassNameId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId, classPK, threadId from MBDiscussion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				long threadId = rs.getLong("threadId");

				runSQL(
					"update MBMessage set classNameId = " + classNameId +
						", classPK = " + classPK + " where threadId = " +
							threadId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessageFlagThreadId() throws Exception {
		if (isSupportsUpdateWithInnerJoin()) {
			StringBuilder sb = new StringBuilder();

			sb.append("update MBMessageFlag inner join MBMessage on ");
			sb.append("MBMessage.messageId = MBMessageFlag.messageId set ");
			sb.append("MBMessageFlag.threadId = MBMessage.threadId");

			runSQL(sb.toString());
		}
		else {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getConnection();

				ps = con.prepareStatement(
					"select messageId, threadId from MBMessage");

				rs = ps.executeQuery();

				while (rs.next()) {
					long messageId = rs.getLong("messageId");
					long threadId = rs.getLong("threadId");

					runSQL(
						"update MBMessageFlag set threadId = " + threadId +
							" where messageId = " + messageId);
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}
		}
	}

	protected void updateMessagePriority() throws Exception {
		if (isSupportsUpdateWithInnerJoin()) {
			StringBuilder sb = new StringBuilder();

			sb.append("update MBMessage inner join MBThread on ");
			sb.append("MBThread.threadId = MBMessage.threadId set ");
			sb.append("MBMessage.priority = MBThread.priority");

			runSQL(sb.toString());
		}
		else {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getConnection();

				ps = con.prepareStatement(
					"select threadId, priority from MBThread");

				rs = ps.executeQuery();

				while (rs.next()) {
					long threadId = rs.getLong("threadId");
					double priority = rs.getDouble("priority");

					runSQL(
						"update MBMessage set priority = " + priority +
							" where threadId = " + threadId);
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeMessageBoards.class);

}