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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeLayout.java.html"><b><i>View Source</i></b></a>
 *
 * @author Samuel Kong
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		String languageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where typeSettings " +
					"like '%meta-description=%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				UnicodeProperties typeSettingsProperties =
					new UnicodeProperties(true);

				typeSettingsProperties.load(typeSettings);

				String oldMetaDescription = typeSettingsProperties.getProperty(
					"meta-description");
				String newMetaDescription = typeSettingsProperties.getProperty(
					"meta-description_" + languageId);

				if (Validator.isNotNull(oldMetaDescription) &&
					Validator.isNull(newMetaDescription)) {

					typeSettingsProperties.setProperty(
						"meta-description_" + languageId, oldMetaDescription);
				}

				typeSettingsProperties.remove("meta-description");

				String oldMetaKeywords = typeSettingsProperties.getProperty(
					"meta-keywords");
				String newMetaKeywords = typeSettingsProperties.getProperty(
					"meta-keywords_" + languageId);

				if (Validator.isNotNull(oldMetaKeywords) &&
					Validator.isNull(newMetaKeywords)) {

					typeSettingsProperties.setProperty(
						"meta-keywords_" + languageId, oldMetaKeywords);
				}

				typeSettingsProperties.remove("meta-keywords");

				String oldMetaRobots = typeSettingsProperties.getProperty(
					"meta-robots");
				String newMetaRobots = typeSettingsProperties.getProperty(
					"meta-robots_" + languageId);

				if (Validator.isNotNull(oldMetaRobots) &&
					Validator.isNull(newMetaRobots)) {

					typeSettingsProperties.setProperty(
						"meta-robots_" + languageId, oldMetaRobots);
				}

				typeSettingsProperties.remove("meta-robots");

				updateTypeSettings(plid, typeSettingsProperties.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}