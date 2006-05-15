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

import com.liferay.portal.model.Address;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.spring.AddressLocalServiceUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.util.StringPool;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeUser.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeUser extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeUser();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeUser() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_USER);

			rs = ps.executeQuery();

			while (rs.next()) {
				String userId = rs.getString("userId");
				String companyId = rs.getString("companyId");
				String password = StringPool.BLANK;
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");
				String nickName = rs.getString("nickName");
				boolean male = rs.getBoolean("male");
				Timestamp birthday = rs.getTimestamp("birthday");
				String emailAddress = rs.getString("emailAddress");
				String smsId = rs.getString("smsId");
				String aimId = rs.getString("aimId");
				String icqId = rs.getString("icqId");
				String msnId = rs.getString("msnId");
				String ymId = rs.getString("ymId");
				String languageId = rs.getString("languageId");
				String timeZoneId = rs.getString("timeZoneId");
				String greeting = rs.getString("greeting");
				String resolution = rs.getString("resolution");
				String comments = rs.getString("comments");

				String prefixId = StringPool.BLANK;
				String suffixId = StringPool.BLANK;

				Calendar birthdayCal = new GregorianCalendar();

				birthdayCal.setTime(birthday);

				int birthdayMonth = birthdayCal.get(Calendar.MONTH);
				int birthdayDay = birthdayCal.get(Calendar.DATE);
				int birthdayYear = birthdayCal.get(Calendar.YEAR);

				String smsSn = smsId;
				String aimSn = aimId;
				String icqSn = icqId;
				String msnSn = msnId;
				String skypeSn = null;
				String ymSn = ymId;
				String jobTitle = StringPool.BLANK;
				String organizationId = null;
				String locationId = null;

				_log.debug("Upgrading user " + userId);

				User user = UserLocalServiceUtil.getUserById(userId);

				user = UserLocalServiceUtil.updateUser(
					userId, password, emailAddress, languageId, timeZoneId,
					greeting, resolution, comments, firstName, middleName,
					lastName, nickName, prefixId, suffixId, male, birthdayMonth,
					birthdayDay, birthdayYear, smsSn, aimSn, icqSn, msnSn,
					skypeSn, ymSn, jobTitle, organizationId, locationId);

				Contact contact = user.getContact();

				List addresses = AddressLocalServiceUtil.getAddresses(
					companyId, User.class.getName(), userId);

				AddressLocalServiceUtil.deleteAddresses(
					companyId, User.class.getName(), userId);

				for (int i = 0; i < addresses.size(); i++) {
					Address address = (Address)addresses.get(i);

					AddressLocalServiceUtil.addAddress(
						userId, Contact.class.getName(), contact.getContactId(),
						address.getStreet1(), address.getStreet2(),
						address.getStreet3(), address.getCity(),
						address.getZip(), address.getRegionId(),
						address.getCountryId(), address.getTypeId(),
						address.isMailing(), address.isPrimary());
				}

				if (!User.isDefaultUser(userId)) {
					GroupLocalServiceUtil.addGroup(
						user.getUserId(), User.class.getName(),
						user.getPrimaryKey().toString(), null, null);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static final String _UPGRADE_USER = "SELECT * FROM User_";

	private static Log _log = LogFactory.getLog(UpgradeUser.class);

}