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

import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.FriendlyURLNormalizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ed Shin
 *
 */
public class UpgradeGroup extends UpgradeProcess {

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
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select companyId, groupId, classPK, name from Group_ " +
					"where friendlyURL = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long classPK = rs.getLong("classPK");
				String name = rs.getString("name");

				String friendlyURL = getFriendlyURL(
					companyId, groupId, classPK, name);

				runSQL(
					"update Group_ set friendlyURL = '" + friendlyURL + "'" +
						" where groupId = " + groupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getFriendlyURL(String friendlyURL) {
		return FriendlyURLNormalizer.normalize(friendlyURL);
	}

	protected String getFriendlyURL(
			long companyId, long groupId, long classPK, String name) {

		// See GroupLocalServiceImpl.getFriendlyURL()

		String friendlyURL = StringPool.SLASH + getFriendlyURL(name);

		if (classPK != 0) {
			friendlyURL = StringPool.SLASH + classPK;
		}
		else {
			friendlyURL = StringPool.SLASH + groupId;
		}

		String originalFriendlyURL = friendlyURL;

		for (int i = 1;; i++) {
			try {
				validateFriendlyURL(companyId, friendlyURL);

				break;
			}
			catch (GroupFriendlyURLException gfurle) {
				friendlyURL = originalFriendlyURL + i;
			}
		}

		return friendlyURL;
	}

	protected void validateFriendlyURL(
			long companyId, String friendlyURL)
		throws GroupFriendlyURLException {

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, friendlyURL);
		}
		catch (Exception e) {
		}

		if (group != null) {
			throw new GroupFriendlyURLException(
				GroupFriendlyURLException.DUPLICATE);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeGroup.class);

}