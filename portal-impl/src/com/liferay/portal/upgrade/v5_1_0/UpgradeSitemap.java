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

package com.liferay.portal.upgrade.v5_1_0;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v4_4_0.UpgradeLayout;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeSitemap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class UpgradeSitemap extends UpgradeProcess {

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
				"select ownerId, ownerType, plid, portletId, preferences " +
					"from PortletPreferences where portletId like '85_%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long ownerId = rs.getLong("ownerId");
				int ownerType = rs.getInt("ownerType");
				long plid = rs.getLong("plid");
				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				String newPreferences = upgradePreferences(
					layout.getCompanyId(), ownerId, ownerType, plid, portletId,
					preferences);

				ps = con.prepareStatement(
					"update PortletPreferences set preferences = ? where " +
						"plid = " + plid + " and portletId = ?");

				ps.setString(1, newPreferences);
				ps.setString(2, portletId);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String preferences)
		throws Exception {

		PortletPreferencesImpl prefs = PortletPreferencesSerializer.fromXML(
			companyId, ownerId, ownerType, plid, portletId, preferences);

		long rootPlid = GetterUtil.getLong(
			prefs.getValue("root-plid", StringPool.BLANK));

		if (rootPlid > 0) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(rootPlid);

				prefs.setValue(
					"root-layout-id", String.valueOf(layout.getLayoutId()));
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		prefs.setValue("root-plid", null);

		return PortletPreferencesSerializer.toXML(prefs);
	}

	private static Log _log = LogFactory.getLog(UpgradeLayout.class);

}