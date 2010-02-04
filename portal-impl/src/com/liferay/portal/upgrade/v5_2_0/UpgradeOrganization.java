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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.upgrade.v5_2_0.util.OrganizationTable;
import com.liferay.portal.upgrade.v5_2_0.util.OrganizationTypeUpgradeColumnImpl;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * <a href="UpgradeOrganization.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Edward Shin
 */
public class UpgradeOrganization extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		UpgradeColumn locationColumn = new TempUpgradeColumnImpl(
			"location", new Integer(Types.BOOLEAN));

		UpgradeColumn typeColumn = new OrganizationTypeUpgradeColumnImpl(
			locationColumn);

		Object[][] organizationColumns1 =
			{{"location", new Integer(Types.BOOLEAN)}};
		Object[][] organizationColumns2 =
			OrganizationTable.TABLE_COLUMNS.clone();

		Object[][] organizationColumns = ArrayUtil.append(
			organizationColumns1, organizationColumns2);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrganizationTable.TABLE_NAME, organizationColumns, locationColumn,
			typeColumn);

		upgradeTable.updateTable();

		updateLocationResources();
	}

	protected long getCodeId(long companyId, String name, int scope)
		throws Exception {

		long codeId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select codeId from ResourceCode where companyId = ? and " +
					"name = ? and scope = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);
			ps.setInt(3, scope);

			rs = ps.executeQuery();

			if (rs.next()) {
				codeId = rs.getLong("codeId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return codeId;
	}

	protected void updateCodeId(long companyId, int scope) throws Exception {
		long oldCodeId = getCodeId(
			companyId, "com.liferay.portal.model.Location", scope);
		long newCodeId = getCodeId(
			companyId, "com.liferay.portal.model.Organization", scope);

		runSQL(
			"update Resource_ set codeId = " + newCodeId + " where codeId = " +
				oldCodeId);

		runSQL("delete from ResourceCode where codeId = " + oldCodeId);
	}

	protected void updateLocationResources() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			for (int scope : ResourceConstants.SCOPES) {
				updateCodeId(companyId, scope);
			}
		}
	}

}