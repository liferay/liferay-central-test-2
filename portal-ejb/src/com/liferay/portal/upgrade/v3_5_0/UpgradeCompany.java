/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.upgrade.v3_5_0;

import com.liferay.portal.service.spring.PropertiesLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeCompany.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeCompany extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeCompany();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeCompany() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_COMPANY);

			rs = ps.executeQuery();

			while (rs.next()) {
				String companyId = rs.getString("companyId");
				String authType = rs.getString("authType");
				boolean autoLogin = rs.getBoolean("autoLogin");
				boolean strangers = rs.getBoolean("strangers");

				_log.debug("Upgrading company " + companyId);

				Properties propertiesObj =
					PropertiesLocalServiceUtil.getPortalProperties(companyId);

				propertiesObj.setProperty(
					PropsUtil.COMPANY_SECURITY_AUTH_TYPE, authType);
				propertiesObj.setProperty(
					PropsUtil.COMPANY_SECURITY_AUTO_LOGIN,
					Boolean.toString(autoLogin));
				propertiesObj.setProperty(
					PropsUtil.COMPANY_SECURITY_STRANGERS,
					Boolean.toString(strangers));

				PropertiesLocalServiceUtil.updatePortalProperties(
					companyId, propertiesObj);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static final String _UPGRADE_COMPANY = "SELECT * FROM Company";

	private static Log _log = LogFactory.getLog(UpgradeCompany.class);

}