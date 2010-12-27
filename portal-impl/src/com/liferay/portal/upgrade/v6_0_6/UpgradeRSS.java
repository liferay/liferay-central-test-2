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

package com.liferay.portal.upgrade.v6_0_6;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Raymond Augé
 */
public class UpgradeRSS extends BaseUpgradePortletPreferences {

	protected String[] getArticleValues(long resourcePrimKey) {
		long groupId = 0;
		String articleId = StringPool.BLANK;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId, articleId from JournalArticle where " +
					"resourcePrimKey = ?");

			ps.setLong(1, resourcePrimKey);

			rs = ps.executeQuery();

			rs.next();

			groupId = rs.getLong("groupId");
			articleId = rs.getString("articleId");
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return new String[] {String.valueOf(groupId), articleId};
	}

	protected String[] getPortletIds() {
		return new String[] {"39_INSTANCE_%"};
	}

	protected void upgradeFooterValues(PortletPreferencesImpl preferences)
		throws Exception {

		String[] footerArticleResouceValues = preferences.getValues(
			"footer-article-resource-values", new String[] {"0", ""});

		long footerArticleResourcePrimKey = GetterUtil.getLong(
			footerArticleResouceValues[0]);

		String[] values = getArticleValues(footerArticleResourcePrimKey);

		preferences.setValues("footer-article-values", values);
		preferences.reset("footer-article-resource-values");
	}

	protected void upgradeHeaderValues(PortletPreferencesImpl preferences)
		throws Exception {

		String[] headerArticleResouceValues = preferences.getValues(
			"header-article-resource-values", new String[] {"0", ""});

		long headerArticleResourcePrimKey = GetterUtil.getLong(
			headerArticleResouceValues[0]);

		String[] values = getArticleValues(headerArticleResourcePrimKey);

		preferences.setValues("header-article-values", values);
		preferences.reset("header-article-resource-values");
	}

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeFooterValues(preferences);
		upgradeHeaderValues(preferences);

		return PortletPreferencesSerializer.toXML(preferences);
	}

}