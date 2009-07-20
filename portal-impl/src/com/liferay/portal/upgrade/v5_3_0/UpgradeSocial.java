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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DateUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.model.impl.SocialRelationImpl;
import com.liferay.portlet.social.model.impl.SocialRequestImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpgradeSocial extends UpgradeProcess {

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

		// SocialActivity

		UpgradeColumn createDateColumn = new DateUpgradeColumnImpl(
			"createDate");
		UpgradeColumn modifiedDateColumn = new DateUpgradeColumnImpl(
			"modifiedDate");

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			SocialActivityImpl.TABLE_NAME, SocialActivityImpl.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialActivityImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SocialRelation

		upgradeTable = new DefaultUpgradeTableImpl(
			SocialRelationImpl.TABLE_NAME, SocialRelationImpl.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialRelationImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SocialRequest

		upgradeTable = new DefaultUpgradeTableImpl(
			SocialRequestImpl.TABLE_NAME, SocialRequestImpl.TABLE_COLUMNS,
			createDateColumn, modifiedDateColumn);

		upgradeTable.setCreateSQL(SocialRequestImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
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
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateGroupId(long groupId) throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			long layoutGroupId = layout.getGroupId();

			runSQL(
				"update SocialActivity set groupId = " + layoutGroupId +
					" where groupId = " + groupId);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

}