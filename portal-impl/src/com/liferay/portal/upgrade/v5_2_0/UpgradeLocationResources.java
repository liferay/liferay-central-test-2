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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeLocationResources.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class UpgradeLocationResources extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading location resources");

		try {
			updateLocationResources();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void updateLocationResources()
		throws Exception {

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			for (int scope : _SCOPES) {
				ResourceCode oldResourceCode =
					ResourceCodeLocalServiceUtil.getResourceCode(
						company.getCompanyId(),
						"com.liferay.portal.model.Location", scope);

				ResourceCode newResourceCode =
					ResourceCodeLocalServiceUtil.getResourceCode(
						company.getCompanyId(),
						"com.liferay.portal.model.Organization", scope);

				updateCodeId(oldResourceCode, newResourceCode);

				ResourceCodeLocalServiceUtil.deleteResourceCode(
					oldResourceCode);
			}
		}
	}

	protected void updateCodeId(
			ResourceCode oldResourceCode, ResourceCode newResourceCode)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Resource_ where codeId = ? set codeId = ?");

			ps.setLong(1, oldResourceCode.getCodeId());
			ps.setLong(2, newResourceCode.getCodeId());

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		ResourceCodeLocalServiceUtil.deleteResourceCode(oldResourceCode);
	}

	private static Log _log = LogFactory.getLog(UpgradeLocationResources.class);

	private static int[] _SCOPES = {
		ResourceConstants.SCOPE_COMPANY, ResourceConstants.SCOPE_GROUP,
		ResourceConstants.SCOPE_GROUP_TEMPLATE,
		ResourceConstants.SCOPE_INDIVIDUAL
	};

}