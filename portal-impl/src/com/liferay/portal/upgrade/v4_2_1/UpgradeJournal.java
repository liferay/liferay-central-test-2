/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_2_1;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 * @author  Joel Kozikowski
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeJournal extends UpgradeProcess {

	public static final String ENCODING = "UTF-8";

	public static final String XML_INDENT = "  ";

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeJournalArticle();
			_upgradeJournalArticleImages();
			_upgradeJournalTemplateImages();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new UpgradeException(e);
		}
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

	private String _getNewArticleImageId(String oldImageId, String groupId)
		throws Exception {

		int x = oldImageId.indexOf(".journal.article.");

		String companyId = oldImageId.substring(0, x);

		x = x + 17;
		int y = oldImageId.indexOf(".", x);

		String articleId = oldImageId.substring(x, y);
		String suffix = oldImageId.substring(y, oldImageId.length());

		if (groupId == null) {
			groupId = _findGroupId(
				_FIND_JOURNAL_ARTICLE_GROUP, companyId, articleId);
		}

		String newImageId = null;

		if (groupId != null) {
			newImageId =
				companyId + ".journal.article." + groupId + "." + articleId +
					suffix;
		}

		return newImageId;
	}

	private String _getNewTemplateImageId(String oldImageId) throws Exception {
		int x = oldImageId.indexOf(".journal.template.");

		String companyId = oldImageId.substring(0, x);

		x = x + 18;
		int y = oldImageId.indexOf(".", x);

		String templateId = oldImageId.substring(x, y);
		String suffix = oldImageId.substring(y, oldImageId.length());

		String groupId = _findGroupId(
			_FIND_JOURNAL_TEMPLATE_GROUP, companyId, templateId);

		String newImageId = null;

		if (groupId != null) {
			newImageId =
				companyId + ".journal.template." + groupId + "." + templateId +
					suffix;
		}

		return newImageId;
	}

	private void _updateContent(
			String queryString, String companyId, String groupId,
			String articleId, String version, String content)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(queryString);

			ps.setString(1, companyId);
			ps.setString(2, groupId);
			ps.setString(3, articleId);
			ps.setString(4, version);
			ps.setString(5, content);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private void _upgradeImageId(String groupId, Element root) throws Exception {
		Iterator itr1 = root.elements("dynamic-element").iterator();

		while (itr1.hasNext()) {
			Element dynamicEl = (Element)itr1.next();

			String type = dynamicEl.attributeValue("type");

			if (type.equals("image")) {
				Iterator itr2 = dynamicEl.elements(
					"dynamic-content").iterator();

				while (itr2.hasNext()) {
					Element dynamicContent = (Element)itr2.next();

					String id = dynamicContent.attributeValue("id");
					String content = dynamicContent.getText();

					if (id != null) {
						String newId = _getNewArticleImageId(id, groupId);

						int x = content.indexOf("?img_id=") + 8;

						String newContent =
							content.substring(0, x) + groupId + "." +
								content.substring(x, content.length());

						dynamicContent.addAttribute("id", newId);
						dynamicContent.setText(newContent);
					}
				}
			}

			_upgradeImageId(groupId, dynamicEl);
		}
	}

	private void _upgradeJournalArticle() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_FIND_JOURNAL_ARTICLE_TEMPLATE_DRIVEN);

			rs = ps.executeQuery();

			Document doc = null;

			SAXReader reader = new SAXReader();

			Element root = null;

			while (rs.next()) {
				String companyId = rs.getString("companyId");
				String groupId = rs.getString("groupId");
				String articleId = rs.getString("articleId");
				String version = rs.getString("version");
				String content = rs.getString("content");

				try {
					doc = reader.read(new StringReader(content));

					root = doc.getRootElement();

					_upgradeImageId(groupId, root);

					String xmlContent = _toString(doc, XML_INDENT);

					_updateContent(
						_UPDATE_JOURNAL_ARTICLE_CONTENT, companyId, groupId,
						articleId, version, xmlContent);
				}
				catch (DocumentException de) {

					// This should only happen if there is an article with stale
					// data

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to read article " + articleId);
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							de.getMessage() + "\n" +
							"Unable to read article " +
								articleId + " with content\n" + content);
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeJournalArticleImages() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_FIND_IMAGE_JOURNAL_ARTICLE);

			rs = ps.executeQuery();

			while (rs.next()) {
				String imageId = rs.getString("imageId");

				String newImageId = _getNewArticleImageId(imageId, null);

				if (newImageId != null) {
					ps = con.prepareStatement(_UPDATE_IMAGE);

					ps.setString(1, newImageId);
					ps.setString(2, imageId);

					ps.executeUpdate();
				}
				else {
					ps = con.prepareStatement(_DELETE_IMAGE);

					ps.setString(1, imageId);

					ps.executeUpdate();
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeJournalTemplateImages() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_FIND_IMAGE_JOURNAL_TEMPLATE);

			rs = ps.executeQuery();

			while (rs.next()) {
				String imageId = rs.getString("imageId");

				String newImageId = _getNewTemplateImageId(imageId);

				if (newImageId != null) {
					ps = con.prepareStatement(_UPDATE_IMAGE);

					ps.setString(1, newImageId);
					ps.setString(2, imageId);

					ps.executeUpdate();
				}
				else {
					ps = con.prepareStatement(_DELETE_IMAGE);

					ps.setString(1, imageId);

					ps.executeUpdate();
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static String _toString(Branch branch, String indent)
		throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		OutputFormat format = OutputFormat.createPrettyPrint();

		format.setIndent(indent);
		format.setLineSeparator("\n");

		XMLWriter writer = new XMLWriter(baos, format);

		writer.write(branch);

		String content = baos.toString(ENCODING);

		/*content = StringUtil.replace(
			content,
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
			"<?xml version=\"1.0\"?>");*/

		int x = content.indexOf("<!DOCTYPE");

		if (x != -1) {
			x = content.indexOf(">", x) + 1;

			content = content.substring(0, x) + "\n" +
				content.substring(x, content.length());
		}

		content = StringUtil.replace(content, "\n\n\n", "\n\n");

		if (content.endsWith("\n\n")) {
			content = content.substring(0, content.length() - 2);
		}

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		while (content.indexOf(" \n") != -1) {
			content = StringUtil.replace(content, " \n", "\n");
		}

		return content;
	}

	private static final String _DELETE_IMAGE =
		"DELETE FROM Image WHERE imageId = ?";

	private static final String _FIND_IMAGE_JOURNAL_ARTICLE =
		"SELECT imageId FROM Image " +
			"WHERE imageId LIKE '%.journal.article.%'";

	private static final String _FIND_IMAGE_JOURNAL_TEMPLATE =
		"SELECT imageId FROM Image " +
			"WHERE imageId LIKE '%.journal.template.%'";

	private static final String _FIND_JOURNAL_ARTICLE_GROUP =
		"SELECT groupId FROM JournalArticle WHERE companyId = ? AND " +
			"articleId = ?";

	private static final String _FIND_JOURNAL_TEMPLATE_GROUP =
		"SELECT groupId FROM JournalTemplate WHERE companyId = ? AND " +
			"templateId = ?";

	private static final String _FIND_JOURNAL_ARTICLE_TEMPLATE_DRIVEN =
		"SELECT companyId, groupId, articleId, version, content " +
			"FROM JournalArticle WHERE structureId <> ''";

	private static final String _UPDATE_IMAGE =
		"UPDATE Image SET imageId = ? WHERE imageId = ?";

	private static final String _UPDATE_JOURNAL_ARTICLE_CONTENT =
		"UPDATE JournalArticle SET content = ? WHERE companyId = ? AND " +
			"groupId = ? AND articleId = ? AND version = ?";

	private static Log _log = LogFactory.getLog(UpgradeJournal.class);

}