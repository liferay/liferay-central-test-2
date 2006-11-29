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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeCalendar.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeCalendar extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeEvent();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeEvent() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_EVENT);

			ps.setString(1, GroupImpl.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String eventId = rs.getString("eventId");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Upgrading event " + eventId);

				CalEventLocalServiceUtil.addEventResources(
					eventId, addCommunityPermissions, addGuestPermissions);
			}
		}
		catch (NoSuchGroupException nsge) {
			_log.error(
				"Upgrade failed because data does not have a valid group id. " +
					"Manually assign a group id in the database.");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _UPGRADE_EVENT =
		"SELECT * FROM CalEvent WHERE groupId != ?";

	private static Log _log = LogFactory.getLog(UpgradeCalendar.class);

}