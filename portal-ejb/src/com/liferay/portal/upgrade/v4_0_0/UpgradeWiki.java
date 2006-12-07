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
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPagePK;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeWiki.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeWiki extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeNode();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeNode() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_NODE);

			ps.setString(1, GroupImpl.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String nodeId = rs.getString("nodeId");
				String groupId = rs.getString("groupId");

				if (groupId.equals(GroupImpl.DEFAULT_PARENT_GROUP_ID)) {
					_log.warn(
						"Skip node " + nodeId + " because it belongs to a " +
							"personal page.");
				}
				else {
					boolean addCommunityPermissions = true;
					boolean addGuestPermissions = true;

					_log.debug("Upgrading node " + nodeId);

					WikiNodeLocalServiceUtil.addNodeResources(
						nodeId, addCommunityPermissions, addGuestPermissions);

					_upgradePage(nodeId);
				}
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

	private void _upgradePage(String nodeId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_PAGE);

			ps.setString(1, nodeId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				double version = rs.getDouble("version");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug(
					"Upgrading page " + new WikiPagePK(nodeId, title, version));

				WikiPageLocalServiceUtil.addPageResources(
					nodeId, title, addCommunityPermissions,
					addGuestPermissions);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _UPGRADE_NODE =
		"SELECT * FROM WikiNode WHERE groupId != ?";

	private static final String _UPGRADE_PAGE =
		"SELECT * FROM WikiPage WHERE nodeId = ? AND head = ?";

	private static Log _log = LogFactory.getLog(UpgradeWiki.class);

}