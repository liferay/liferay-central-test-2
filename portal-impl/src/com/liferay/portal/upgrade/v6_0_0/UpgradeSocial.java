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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DateUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.upgrade.v6_0_0.util.SocialActivityTable;
import com.liferay.portal.upgrade.v6_0_0.util.SocialRelationTable;
import com.liferay.portal.upgrade.v6_0_0.util.SocialRequestTable;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeSocial.java.html"><b><i>View Source</i></b></a>
 *
 * @author Amos Fong
 * @author Brian Wing Shun Chan
 */
public class UpgradeSocial extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateGroupId();

		// SocialActivity

		UpgradeColumn createDateColumn = new DateUpgradeColumnImpl(
			"createDate");
		UpgradeColumn modifiedDateColumn = new DateUpgradeColumnImpl(
			"modifiedDate");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialActivityTable.TABLE_NAME, SocialActivityTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialActivityTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SocialRelation

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRelationTable.TABLE_NAME, SocialRelationTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialRelationTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SocialRequest

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRequestTable.TABLE_NAME, SocialRequestTable.TABLE_COLUMNS,
			createDateColumn, modifiedDateColumn);

		upgradeTable.setCreateSQL(SocialRequestTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	protected Object[] getGroup(long groupId) throws Exception {
		Object[] group = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_GROUP);

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				group = new Object[] {classNameId, classPK};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return group;
	}

	protected Object[] getLayout(long plid) throws Exception {
		Object[] layout = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				layout = new Object[] {groupId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
	}

	protected void updateGroupId() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select distinct(groupId) from SocialActivity where groupId " +
					"> 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				try {
					updateGroupId(groupId);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupId(long groupId) throws Exception {
		Object[] group = getGroup(groupId);

		if (group == null) {
			return;
		}

		long classNameId = (Long)group[0];

		if (classNameId != PortalUtil.getClassNameId(Layout.class.getName())) {
			return;
		}

		long classPK = (Long)group[1];

		Object[] layout = getLayout(classPK);

		if (layout == null) {
			return;
		}

		long layoutGroupId = (Long)layout[0];

		runSQL(
			"update SocialActivity set groupId = " + layoutGroupId +
				" where groupId = " + groupId);
	}

	private static final String _GET_GROUP =
		"select * from Group_ where groupId = ?";

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

}