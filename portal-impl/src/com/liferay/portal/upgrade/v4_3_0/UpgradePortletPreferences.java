/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.PortletPreferencesTable;
import com.liferay.portal.upgrade.v4_3_0.util.PrefsOwnerIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PrefsOwnerTypeUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PrefsPlidUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PrefsXMLUpgradeColumnImpl;
import com.liferay.portal.util.PortletKeys;

import java.sql.Types;

import java.util.Iterator;

/**
 * <a href="UpgradePortletPreferences.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletPreferences extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// PortletPreferences

		runSQL(
			"delete from PortletPreferences where ownerId = '0' and " +
				"ownerType = " + PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		ValueMapper companyIdMapper = AvailableMappersUtil.getCompanyIdMapper();

		Iterator<Object> itr = companyIdMapper.iterator();

		while (itr.hasNext()) {
			String webId = (String)itr.next();

			Long companyIdObj = (Long)companyIdMapper.getNewValue(webId);

			runSQL(
				"delete from PortletPreferences where ownerId = '" +
					companyIdObj.longValue() + "' and ownerType = " +
						PortletKeys.PREFS_OWNER_TYPE_COMPANY);
		}

		Object[][] preferencesColumns1 =
			{{"layoutId", new Integer(Types.VARCHAR)}};
		Object[][] preferencesColumns2 =
			PortletPreferencesTable.TABLE_COLUMNS.clone();

		Object[][] preferencesColumns = ArrayUtil.append(
			preferencesColumns1, preferencesColumns2);

		PrefsOwnerIdUpgradeColumnImpl upgradeOwnerIdColumn =
			new PrefsOwnerIdUpgradeColumnImpl(
				AvailableMappersUtil.getCompanyIdMapper(),
				AvailableMappersUtil.getGroupIdMapper(),
				AvailableMappersUtil.getUserIdMapper());

		UpgradeColumn upgradeOwnerTypeColumn =
			new PrefsOwnerTypeUpgradeColumnImpl(upgradeOwnerIdColumn);

		UpgradeColumn upgradeLayoutIdColumn =
			new TempUpgradeColumnImpl("layoutId");

		UpgradeColumn upgradePlidColumn = new PrefsPlidUpgradeColumnImpl(
			upgradeOwnerIdColumn, upgradeLayoutIdColumn,
			AvailableMappersUtil.getLayoutPlidMapper());

		UpgradeColumn upgradePortletIdColumn =
			new TempUpgradeColumnImpl("portletId");

		UpgradeColumn upgradePreferencesColumn = new PrefsXMLUpgradeColumnImpl(
			upgradePortletIdColumn,	 AvailableMappersUtil.getGroupIdMapper(),
			AvailableMappersUtil.getPollsQuestionIdMapper(),
			AvailableMappersUtil.getWikiNodeIdMapper());

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			PortletPreferencesTable.TABLE_NAME, preferencesColumns,
			new PKUpgradeColumnImpl("portletPreferencesId", false),
			upgradeOwnerIdColumn, upgradeOwnerTypeColumn, upgradeLayoutIdColumn,
			upgradePlidColumn, upgradePortletIdColumn,
			upgradePreferencesColumn);

		String createSQL = PortletPreferencesTable.TABLE_SQL_CREATE;

		createSQL =
			createSQL.substring(0, createSQL.length() - 1) +
				",layoutId VARCHAR(75) null)";

		upgradeTable.setCreateSQL(createSQL);

		upgradeTable.updateTable();

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table PortletPreferences drop column layoutId"
	};

}