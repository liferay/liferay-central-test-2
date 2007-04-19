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

import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.dao.DataAccess;

import java.io.StringReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeJournal extends UpgradeProcess {

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

	private long _findGroupId(
			String queryString, String companyId, String id)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(queryString);

			ps.setString(1, companyId);
			ps.setString(2, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	private String _getNewArticleImageId(String oldImageId, long groupId)
		throws Exception {

		int x = oldImageId.indexOf(".journal.article.");

		String companyId = oldImageId.substring(0, x);

		x = x + 17;
		int y = oldImageId.indexOf(".", x);

		String articleId = oldImageId.substring(x, y);
		String suffix = oldImageId.substring(y, oldImageId.length());

		if (groupId <= 0) {
			groupId = _findGroupId(
				_FIND_JOURNAL_ARTICLE_GROUP, companyId, articleId);
		}

		String newImageId = null;

		if (groupId > 0) {
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

		long groupId = _findGroupId(
			_FIND_JOURNAL_TEMPLATE_GROUP, companyId, templateId);

		String newImageId = null;

		if (groupId > 0) {
			newImageId =
				companyId + ".journal.template." + groupId + "." + templateId +
					suffix;
		}

		return newImageId;
	}

	private void _updateImageId(long groupId, Element root) throws Exception {
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

			_updateImageId(groupId, dynamicEl);
		}
	}

	private void _upgradeJournalArticle() throws Exception {
		Iterator itr = JournalArticleLocalServiceUtil.getArticles().iterator();

		while (itr.hasNext()) {
			JournalArticle article = (JournalArticle)itr.next();

			if (article.isTemplateDriven()) {
				SAXReader reader = new SAXReader();

				try {
					Document doc = reader.read(
						new StringReader(article.getContent()));

					Element root = doc.getRootElement();

					_updateImageId(article.getGroupId(), root);

					String content = JournalUtil.formatXML(doc);

					JournalArticleLocalServiceUtil.updateContent(
						article.getCompanyId(), article.getGroupId(),
						article.getArticleId(), article.getVersion(), content);
				}
				catch (DocumentException de) {

					// This should only happen if there is an article with stale
					// data

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to read article " +
								article.getPrimaryKey());
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							de.getMessage() + "\n" +
							"Unable to read article " +
								article.getPrimaryKey() + " with content\n" +
							article.getContent());
					}
				}
			}
		}
	}

	private void _upgradeJournalArticleImages() throws Exception {
		Iterator itr = ImageLocalServiceUtil.search(
			"%.journal.article.%").iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			String oldImageId = image.getImageId();
			String newImageId = _getNewArticleImageId(oldImageId, 0);

			if (newImageId != null) {
				ImageLocalServiceUtil.updateImage(
					newImageId, image.getTextObj());
			}

			ImageLocalServiceUtil.deleteImage(oldImageId);
		}
	}

	private void _upgradeJournalTemplateImages() throws Exception {
		Iterator itr = ImageLocalServiceUtil.search(
			"%.journal.template.%").iterator();

		while (itr.hasNext()) {
			Image image = (Image)itr.next();

			String oldImageId = image.getImageId();
			String newImageId = _getNewTemplateImageId(oldImageId);

			if (newImageId != null) {
				ImageLocalServiceUtil.updateImage(
					newImageId, image.getTextObj());
			}

			ImageLocalServiceUtil.deleteImage(oldImageId);
		}
	}

	private static final String _FIND_JOURNAL_ARTICLE_GROUP =
		"SELECT groupId FROM JournalArticle WHERE companyId = ? AND " +
			"articleId = ?";

	private static final String _FIND_JOURNAL_TEMPLATE_GROUP =
		"SELECT groupId FROM JournalTemplate WHERE companyId = ? AND " +
			"templateId = ?";

	private static Log _log = LogFactory.getLog(UpgradeJournal.class);

}