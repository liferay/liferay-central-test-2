/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.persistence.JournalArticlePK;
import com.liferay.portlet.journal.service.persistence.JournalStructurePK;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 * @author  Joel Kozikowski
 * @author  Brian Wing Shun Chan
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
			throw new UpgradeException(e);
		}
	}

	private void _upgradeJournalArticleResourceIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

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
		Iterator itr =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByPortletId(
				"56_INSTANCE_%").iterator();

		while (itr.hasNext()) {
			PortletPreferences prefs = (PortletPreferences)itr.next();

			Layout layout = LayoutLocalServiceUtil.getLayout(
				prefs.getLayoutId(), prefs.getOwnerId());

			javax.portlet.PortletPreferences javaPrefs =
				PortletPreferencesSerializer.fromDefaultXML(
					prefs.getPreferences());

			String groupId = javaPrefs.getValue("group-id", StringPool.BLANK);
			String[] articleIds = javaPrefs.getValues(
				"article-id", new String[0]);

			if ((Validator.isNull(groupId)) && (articleIds != null) &&
				(articleIds.length > 0)) {

				String articleId = articleIds[0];

				groupId = _findGroupId(
					_FIND_JOURNAL_ARTICLE_GROUP, layout.getCompanyId(),
					articleId);

				if (groupId != null) {
					_log.info(
						"Upgrading portlet preferences " +
							prefs.getPrimaryKey());

					javaPrefs.setValue("group-id", groupId);

					PortletPreferencesLocalServiceUtil.updatePreferences(
						prefs.getPrimaryKey(), javaPrefs);
				}
				else {
					_log.warn(
						"No group id found for joural content with portlet " +
							"id " + prefs.getPortletId() + " and article id " +
								articleId);
				}
			}
		}
	}

	private void _upgradeJournalContentSearch() throws Exception {
		PropsUtil.set(PropsUtil.JOURNAL_SYNC_CONTENT_SEARCH_ON_STARTUP, "true");
	}

	private void _upgradeJournalStructureResourceIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

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
			con = HibernateUtil.getConnection();

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
			con = HibernateUtil.getConnection();

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
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_UPGRADE_RESOURCE_2);

			ps.setString(1, primKey);
			ps.setString(2, resourceId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _FIND_JOURNAL_ARTICLE_GROUP =
		"SELECT groupId FROM JournalArticle WHERE companyId = ? AND " +
			"articleId = ?";

	private static final String _FIND_JOURNAL_STRUCTURE_GROUP =
		"SELECT groupId FROM JournalStructure WHERE companyId = ? AND " +
			"structureId = ?";

	private static final String _FIND_JOURNAL_TEMPLATE_GROUP =
		"SELECT groupId FROM JournalTemplate WHERE companyId = ? AND " +
			"templateId = ?";

	private static final String _UPGRADE_RESOURCE_1 =
		"SELECT resourceId, primKey FROM Resource_ WHERE name = ? AND " +
			"scope = 'individual'";

	private static final String _UPGRADE_RESOURCE_2 =
		"UPDATE Resource_ SET primKey = ? WHERE resourceId = ?";

	private static Log _log = LogFactory.getLog(UpgradeJournal.class);

}