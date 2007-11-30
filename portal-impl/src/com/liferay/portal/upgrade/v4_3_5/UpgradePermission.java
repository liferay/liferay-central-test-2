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

package com.liferay.portal.upgrade.v4_3_5;

import com.liferay.portal.model.impl.GroupImpl;
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
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradePermission extends UpgradeProcess {

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
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_COMPANY_IDS);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				long defaultUserId = getDefaultUserId(companyId);
				long guestGroupId = getGuestGroupId(companyId);

				movePermissions(defaultUserId, guestGroupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getDefaultUserId(long companyId) throws Exception {
		long userId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_DEFAULT_USER_ID);

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				userId = rs.getLong("userId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return userId;
	}

	protected long getGuestGroupId(long companyId) throws Exception {
		long groupId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_GUEST_GROUP_ID);

			ps.setLong(1, companyId);
			ps.setString(2, GroupImpl.GUEST);

			rs = ps.executeQuery();

			while (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected void movePermissions(long defaultUserId, long guestGroupId)
		throws Exception {

		if ((defaultUserId == 0) || (guestGroupId == 0)) {
			return;
		}

		runSQL("delete from Users_Permissions where userId = " + defaultUserId);

		runSQL(
			"insert into Users_Permissions (userId, permissionId) select " +
				defaultUserId + ", Groups_Permissions.permissionId from " +
					"Groups_Permissions where groupId = " + guestGroupId);
	}

	private static final String _GET_COMPANY_IDS =
		"select companyId from Company";

	private static final String _GET_DEFAULT_USER_ID =
		"select userId from User_ where companyId = ? and defaultUser = ?";

	private static final String _GET_GUEST_GROUP_ID =
		"select groupId from Group_ where companyId = ? and name = ?";

	private static Log _log = LogFactory.getLog(UpgradePermission.class);

	private boolean _alreadyUpgraded;

}