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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeAssetPublisher.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class UpgradeAssetPublisher extends UpgradeProcess {

	protected void deletePortletPreferences(long portletPreferencesId)
		throws Exception {

		runSQL(
			"delete from PortletPreferences where portletPreferencesId = " +
				portletPreferencesId);
	}

	protected void doUpgrade() throws Exception {
		updatePortletPreferences();
	}

	protected Object[] getLayout(long plid) throws Exception {
		Object[] layout = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				layout = new Object[] {companyId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
	}

	protected void updatePortletPreferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, ownerId, ownerType, plid, " +
					"portletId, preferences from PortletPreferences where " +
						"portletId like '101_INSTANCE_%' ");

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long ownerId = rs.getLong("ownerId");
				int ownerType = rs.getInt("ownerType");
				long plid = rs.getLong("plid");
				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				Object[] layout = getLayout(plid);

				if (layout != null) {
					long companyId = (Long)layout[0];

					String newPreferences = upgradePreferences(
						companyId, ownerId, ownerType, plid, portletId,
						preferences);

					updatePortletPreferences(
						portletPreferencesId, newPreferences);
				}
				else {
					deletePortletPreferences(portletPreferencesId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortletPreferences(
			long portletPreferencesId, String preferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set preferences = ? where " +
					"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, preferences);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long layoutId = GetterUtil.getLong(
			preferences.getValue("lfr-scope-layout-id", null));

		preferences.reset("lfr-scope-layout-id");

		if (layoutId != 0) {
			preferences.setValues(
				"scope-ids", new String[] {"Layout_" + layoutId});

			preferences.setValue("default-scope", Boolean.FALSE.toString());
		}

		long classNameId = GetterUtil.getLong(
			preferences.getValue("class-name-id", null));

		preferences.reset("class-name-id");

		if (classNameId != 0) {
			preferences.setValues(
				"class-name-ids", new String[] {String.valueOf(classNameId)});

			preferences.setValue("any-asset-type", Boolean.FALSE.toString());
		}

		boolean andOperator = GetterUtil.getBoolean(
			preferences.getValue("and-operator", null));

		preferences.reset("and-operator");

		String[] assetTagNames = preferences.getValues("entries", null);
		String[] notAssetTagNames = preferences.getValues("not-entries", null);

		int i = 0;

		if (assetTagNames != null) {
			preferences.reset("entries");

			preferences.setValue("queryContains" + i, Boolean.TRUE.toString());
			preferences.setValue(
				"queryAndOperator" + i, String.valueOf(andOperator));
			preferences.setValue("queryName" + i, "assetTags");
			preferences.setValues("queryValues" + i, assetTagNames);

			i++;
		}

		if (notAssetTagNames != null) {
			preferences.reset("not-entries");

			preferences.setValue("queryContains" + i, Boolean.FALSE.toString());
			preferences.setValue(
				"queryAndOperator" + i, String.valueOf(andOperator));
			preferences.setValue("queryName" + i, "assetTags");
			preferences.setValues("queryValues" + i, notAssetTagNames);

			i++;
		}

		return PortletPreferencesSerializer.toXML(preferences);
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

}