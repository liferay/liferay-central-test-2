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

import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.spring.BlogsEntryLocalServiceUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeBlogs.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Wilson S. Man
 *
 */
public class UpgradeBlogs extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeEntry();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeEntry() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_ENTRY_1);

			rs = ps.executeQuery();

			while (rs.next()) {
				String entryId = rs.getString("entryId");
				String companyId = rs.getString("companyId");
				String userId = rs.getString("userId");

				Group group = GroupLocalServiceUtil.getUserGroup(
					companyId, userId);

				List actions =
					ResourceActionsUtil.getModelResourceCommunityDefaultActions(
						BlogsEntry.class.getName());

				String[] communityPermissions = 
					(String[])actions.toArray(new String[0]);

				actions =
					ResourceActionsUtil.getModelResourceGuestDefaultActions(
						BlogsEntry.class.getName());

				String[] guestPermissions =
					(String[])actions.toArray(new String[0]);

				_log.debug("Upgrading entry " + entryId);

				ps = con.prepareStatement(_UPGRADE_ENTRY_2);

				ps.setString(1, group.getGroupId());
				ps.setString(2, entryId);

				ps.executeUpdate();

				BlogsEntryLocalServiceUtil.addEntryResources(
					entryId, communityPermissions, guestPermissions);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _UPGRADE_ENTRY_1 = "SELECT * FROM BlogsEntry";

	private static final String _UPGRADE_ENTRY_2 =
		"UPDATE BlogsEntry SET groupId = ? WHERE entryId = ?";

	private static Log _log = LogFactory.getLog(UpgradeBlogs.class);

}