/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;
import com.liferay.portal.model.Account;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.CompanyTable;
import com.liferay.portal.upgrade.v4_3_0.util.WebIdUtil;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class UpgradeCompany extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		ValueMapper companyIdMapper = ValueMapperFactoryUtil.getValueMapper();

		AvailableMappersUtil.setCompanyIdMapper(companyIdMapper);

		String[] webIds = WebIdUtil.getWebIds();

		long[] companyIds = new long[webIds.length];

		for (int i = 0; i < webIds.length; i++) {
			String webId = webIds[i];

			long companyId = upgradeWebId(webId);

			companyIds[i] = companyId;

			companyIdMapper.mapValue(webId, new Long(companyId));
		}

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			CompanyTable.TABLE_NAME, CompanyTable.TABLE_COLUMNS);

		upgradeTable.setCreateSQL(CompanyTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		runSQL(
			"update PortletPreferences set ownerId = '0', ownerType = " +
				PortletKeys.PREFS_OWNER_TYPE_COMPANY +
					" where ownerId = 'COMPANY.LIFERAY_PORTAL'");
	}

	protected String getUpdateSQL(
		String tableName, long companyId, String webId) {

		String updateSQL =
			"update " + tableName + " set companyId = '" + companyId +
				"' where companyId = '" + webId + "'";

		if (_log.isDebugEnabled()) {
			_log.debug(updateSQL);
		}

		return updateSQL;
	}

	protected long upgradeWebId(String webId) throws Exception {
		long companyId = increment();

		for (int j = 0; j < _TABLES.length; j++) {
			runSQL(getUpdateSQL(_TABLES[j], companyId, webId));
		}

		long accountId = increment();

		runSQL(
			"update Account_ set accountId = '" + accountId +
				"', companyId = '" + companyId + "' where accountId = '" +
					webId + "'");

		runSQL(
			"update Address set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		ValueMapper imageIdMapper = AvailableMappersUtil.getImageIdMapper();

		Long logoId = (Long)imageIdMapper.getNewValue(webId);

		runSQL(
			"update Company set accountId = " + accountId + ", logoId = " +
				logoId.longValue() + " where webId = '" + webId + "'");

		runSQL(
			"update Contact_ set companyId = '" + companyId +
				"', accountId = '" + accountId + "' where contactId = '" +
					webId + ".default'");

		runSQL(
			"update Contact_ set accountId = '" + accountId +
				"' where accountId = '" + webId + "'");

		runSQL(
			"update EmailAddress set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		runSQL(
			"update Phone set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		runSQL(
			"update Resource_ set primKey = '" + companyId +
				"' where scope = 'company' and primKey = '" + webId + "'");

		runSQL(
			"update User_ set companyId = '" + companyId +
				"', defaultUser = TRUE where userId = '" + webId + ".default'");

		runSQL(
			"update Website set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		return companyId;
	}

	private static final String[] _TABLES = new String[] {
		"Account_", "Address", "BlogsEntry", "BookmarksEntry",
		"BookmarksFolder", "CalEvent", "Company", "Contact_", "DLFileRank",
		"DLFileShortcut", "DLFileVersion", "DLFolder", "EmailAddress", "Group_",
		"IGFolder", "Layout", "LayoutSet", "MBCategory", "Organization_",
		"Permission_", "Phone", "PollsQuestion", "Portlet", "RatingsEntry",
		"Resource_", "Role_", "ShoppingCart", "ShoppingCategory",
		"ShoppingCoupon", "ShoppingItem", "ShoppingOrder", "Subscription",
		"UserGroup", "User_", "Website", "WikiNode", "WikiPage"
	};

	private static Log _log = LogFactoryUtil.getLog(UpgradeCompany.class);

}