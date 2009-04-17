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

package com.liferay.portal.upgrade.v4_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v4_2_0.util.JournalArticlePK;
import com.liferay.portal.upgrade.v4_2_0.util.JournalStructurePK;
import com.liferay.portal.upgrade.v4_2_0.util.JournalTemplatePK;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Ed Shin
 *
 */
public class UpgradeJournal extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeJournalContentPreferences();
			_upgradeJournalContentSearch();
			_upgradeJournalArticleResourceIds();
			_upgradeJournalStructureResourceIds();
			_upgradeJournalTemplateResourceIds();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private void _upgradeJournalArticleResourceIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_UPGRADE_RESOURCE_1);

			ps.setString(1, JournalArticle.class.getName());

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = rs.getString("primKey");

				if (primKey.indexOf("groupId") == -1) {
					String companyId = _extractValue(primKey, "companyId");
					String articleId = _extractValue(primKey, "articleId");

					String groupId = _findGroupId(
						_FIND_JOURNAL_ARTICLE_GROUP, companyId, articleId);

					if (groupId != null) {
						double version = _extractDouble(primKey, "version");

						JournalArticlePK pk = new JournalArticlePK(
							companyId, groupId, articleId, version);

						_updatePrimKey(
							rs.getString("resourceId"), pk.toString());
					}
					else {
						_log.warn(
							"No group id found for article id " + articleId);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeJournalContentPreferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_FIND_PORTLET_PREFERENCES);

			ps.setString(1, "56_INSTANCE_%");

			rs = ps.executeQuery();

			while (rs.next()) {
				String portletId = rs.getString("portletId");
				String layoutId = rs.getString("layoutId");
				String ownerId = rs.getString("ownerId");
				String preferences = rs.getString("preferences");
				String companyId = rs.getString("companyId");

				PortletPreferences portletPreferences =
					PortletPreferencesSerializer.fromDefaultXML(preferences);

				String groupId = portletPreferences.getValue(
					"group-id", StringPool.BLANK);
				String[] articleIds = portletPreferences.getValues(
					"article-id", new String[0]);

				if ((Validator.isNull(groupId)) && (articleIds != null) &&
					(articleIds.length > 0)) {

					String articleId = articleIds[0];

					groupId = _findGroupId(
						_FIND_JOURNAL_ARTICLE_GROUP, companyId, articleId);

					String primaryKey =
						StringPool.OPEN_CURLY_BRACE + portletId +
							StringPool.COMMA + layoutId + StringPool.COMMA +
								ownerId + StringPool.CLOSE_CURLY_BRACE;

					if (groupId != null) {
						_log.info(
							"Upgrading portlet preferences " + primaryKey);

						// Insert groupId in PortletPreferences

						int beginIndex = "<portlet-preferences>".length();

						StringBuilder sb = new StringBuilder(
							preferences.substring(0, beginIndex) +
								"<preference><name>group-id</name><value>" +
									groupId + "</value></preference>" +
										preferences.substring(beginIndex));

						ps = con.prepareStatement(_UPDATE_PORTLET_PREFERENCES);

						ps.setString(1, sb.toString());
						ps.setString(2, portletId);
						ps.setString(3, layoutId);
						ps.setString(4, ownerId);

						ps.executeUpdate();
					}
					else {
						_log.warn(
							"No group id found for joural content with " +
								"portletpreferences id " + primaryKey +
									" and article id " + articleId);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeJournalContentSearch() throws Exception {
		PropsUtil.set(
			PropsKeys.JOURNAL_SYNC_CONTENT_SEARCH_ON_STARTUP, "true");
	}

	private void _upgradeJournalStructureResourceIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_UPGRADE_RESOURCE_1);

			ps.setString(1, JournalStructure.class.getName());

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = rs.getString("primKey");

				if (primKey.indexOf("groupId") == -1) {
					String companyId = _extractValue(primKey, "companyId");
					String structureId = _extractValue(primKey, "structureId");

					String groupId = _findGroupId(
						_FIND_JOURNAL_STRUCTURE_GROUP, companyId, structureId);

					if (groupId != null) {
						JournalStructurePK pk = new JournalStructurePK(
							companyId, groupId, structureId);

						_updatePrimKey(
							rs.getString("resourceId"), pk.toString());
					}
					else {
						_log.warn(
							"No group id found for structure id " +
								structureId);
					}
				}

			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	private void _upgradeJournalTemplateResourceIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_UPGRADE_RESOURCE_1);

			ps.setString(1, JournalTemplate.class.getName());

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = rs.getString("primKey");

				if (primKey.indexOf("groupId") == -1) {
					String companyId = _extractValue(primKey, "companyId");
					String templateId = _extractValue(primKey, "templateId");

					String groupId = _findGroupId(
						_FIND_JOURNAL_TEMPLATE_GROUP, companyId, templateId);

					if (groupId != null) {
						JournalTemplatePK pk = new JournalTemplatePK(
							companyId, groupId, templateId);

						_updatePrimKey(
							rs.getString("resourceId"), pk.toString());
					}
					else {
						_log.warn(
							"No group id found for template id " + templateId);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	private double _extractDouble(String primKey, String name) {
		return GetterUtil.getDouble(_extractValue(primKey, name));
	}

	private String _extractValue(String primKey, String name) {
		int start = primKey.indexOf(name);

		int end = primKey.indexOf(StringPool.COMMA, start);

		if (end < 0) {
			end = primKey.indexOf(StringPool.CLOSE_CURLY_BRACE, start);
		}

		return primKey.substring(start + name.length() + 1, end);
	}

	private String _findGroupId(
			String queryString, String companyId, String id)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String groupId = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(queryString);

			ps.setString(1, companyId);
			ps.setString(2, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getString("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	private void _updatePrimKey(String resourceId, String primKey)
		throws Exception {

		_log.info("Upgrading resource " + resourceId);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_UPGRADE_RESOURCE_2);

			ps.setString(1, primKey);
			ps.setString(2, resourceId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _FIND_PORTLET_PREFERENCES =
		"SELECT p.portletId, p.layoutId, p.ownerId, p.preferences, " +
			"l.companyId " +
				"FROM PortletPreferences p " +
					"INNER JOIN Layout l " +
						"ON p.portletId LIKE ? " +
							"AND p.layoutId = l.layoutId " +
								"AND p.ownerId = l.ownerId";

	private static final String _FIND_JOURNAL_ARTICLE_GROUP =
		"SELECT groupId FROM JournalArticle WHERE companyId = ? AND " +
			"articleId = ?";

	private static final String _FIND_JOURNAL_STRUCTURE_GROUP =
		"SELECT groupId FROM JournalStructure WHERE companyId = ? AND " +
			"structureId = ?";

	private static final String _FIND_JOURNAL_TEMPLATE_GROUP =
		"SELECT groupId FROM JournalTemplate WHERE companyId = ? AND " +
			"templateId = ?";

	private static final String _UPDATE_PORTLET_PREFERENCES =
		"UPDATE PortletPreferences SET preferences = ? " +
			"WHERE portletId = ? AND layoutId = ? AND ownerId = ?";

	private static final String _UPGRADE_RESOURCE_1 =
		"SELECT resourceId, primKey FROM Resource_ WHERE name = ? AND " +
			"scope = 'individual'";

	private static final String _UPGRADE_RESOURCE_2 =
		"UPDATE Resource_ SET primKey = ? WHERE resourceId = ?";

	private static Log _log = LogFactory.getLog(UpgradeJournal.class);

}