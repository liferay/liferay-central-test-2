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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Region;
import com.liferay.portal.service.spring.AddressLocalServiceUtil;
import com.liferay.portal.service.spring.CompanyLocalServiceUtil;
import com.liferay.portal.service.spring.EmailAddressLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.util.StringPool;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
				String portalURL = rs.getString("portalURL");
				String homeURL = rs.getString("homeURL");
				String mx = rs.getString("mx");
				String name = rs.getString("name");
				String type = rs.getString("type_");
				String size = rs.getString("size_");
				String street1 = rs.getString("street");
				String street2 = StringPool.BLANK;
				String street3 = StringPool.BLANK;
				String city = rs.getString("city");
				String zip = rs.getString("zip");
				String regionId = Region.DEFAULT_REGION_ID;
				String countryId = Country.DEFAULT_COUNTRY_ID;
				String emailAddress = rs.getString("emailAddress");

				_log.debug("Upgrading company " + companyId);

				Company company = CompanyLocalServiceUtil.updateCompany(
					companyId, portalURL, homeURL, mx, name, null, null, null,
					null, null, null, type, size);

				Account account = company.getAccount();

				AddressLocalServiceUtil.addAddress(
					account.getUserId(), Account.class.getName(),
					account.getAccountId(), street1, street2, street3, city,
					zip, regionId, countryId, ListType.ACCOUNT_ADDRESS_DEFAULT,
					true, true);

				EmailAddressLocalServiceUtil.addEmailAddress(
					account.getUserId(), Account.class.getName(),
					account.getAccountId(), emailAddress,
					ListType.ACCOUNT_EMAIL_ADDRESS_DEFAULT, true);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static final String _UPGRADE_COMPANY = "SELECT * FROM Company";

	private static Log _log = LogFactory.getLog(UpgradeCompany.class);

}