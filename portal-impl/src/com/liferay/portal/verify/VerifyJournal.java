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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.util.List;

/**
 * <a href="VerifyJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class VerifyJournal extends VerifyProcess {

	public static final long DEFAULT_GROUP_ID = 14;

	public static final int NUM_OF_ARTICLES = 5;

	protected void doVerify() throws Exception {

		// Oracle new line

		verifyOracleNewLine();

		// Structures

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures();

		for (JournalStructure structure : structures) {
			ResourceLocalServiceUtil.addResources(
				structure.getCompanyId(), 0, 0,
				JournalStructure.class.getName(), structure.getId(), false,
				true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for Journal structures");
		}

		// Templates

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates();

		for (JournalTemplate template : templates) {
			ResourceLocalServiceUtil.addResources(
				template.getCompanyId(), 0, 0,
				JournalTemplate.class.getName(), template.getId(), false, true,
				true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions verified for Journal templates");
		}

		// Articles

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles();

		for (JournalArticle article : articles) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();
			String structureId = article.getStructureId();

			if (article.getResourcePrimKey() <= 0) {
				article =
					JournalArticleLocalServiceUtil.checkArticleResourcePrimKey(
						groupId, articleId, version);
			}

			ResourceLocalServiceUtil.addResources(
				article.getCompanyId(), 0, 0, JournalArticle.class.getName(),
				article.getResourcePrimKey(), false, true, true);

			try {
				AssetEntryLocalServiceUtil.getEntry(
					JournalArticle.class.getName(),
					article.getResourcePrimKey());
			}
			catch (NoSuchEntryException nsee) {
				try {
					JournalArticleLocalServiceUtil.updateAsset(
						article.getUserId(), article, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to update tags asset for article " +
								article.getId() + ": " + e.getMessage());
					}
				}
			}

			String content = GetterUtil.getString(article.getContent());

			String newContent = HtmlUtil.replaceMsWordCharacters(content);

			if (Validator.isNotNull(structureId)) {
				/*JournalStructure structure =
					JournalStructureLocalServiceUtil.getStructure(
						groupId, structureId);

				newContent = JournalUtil.removeOldContent(
					newContent, structure.getXsd());*/
			}

			if (!content.equals(newContent)) {
				JournalArticleLocalServiceUtil.updateContent(
					groupId, articleId, version, newContent);
			}

			JournalArticleLocalServiceUtil.checkStructure(
				groupId, articleId, version);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Permissions and Tags assets verified for Journal articles");
		}
	}

	protected void verifyOracleNewLine() throws Exception {
		DB db = DBFactoryUtil.getDB();

		if (!db.getType().equals(DB.TYPE_ORACLE)) {
			return;
		}

		// This is a workaround for a limitation in Oracle sqlldr's inability
		// insert new line characters for long varchar columns. See
		// http://forums.liferay.com/index.php?showtopic=2761&hl=oracle for more
		// information. Check several articles because some articles may not
		// have new lines.

		boolean checkNewLine = false;

		List<JournalArticle> articles = null;

		if (NUM_OF_ARTICLES <= 0) {
			checkNewLine = true;

			articles = JournalArticleLocalServiceUtil.getArticles(
				DEFAULT_GROUP_ID);
		}
		else {
			articles = JournalArticleLocalServiceUtil.getArticles(
				DEFAULT_GROUP_ID, 0, NUM_OF_ARTICLES);
		}

		for (JournalArticle article : articles) {
			String content = article.getContent();

			if ((content != null) && (content.indexOf("\\n") != -1)) {
				articles = JournalArticleLocalServiceUtil.getArticles(
					DEFAULT_GROUP_ID);

				for (int j = 0; j < articles.size(); j++) {
					article = articles.get(j);

					JournalArticleLocalServiceUtil.checkNewLine(
						article.getGroupId(), article.getArticleId(),
						article.getVersion());
				}

				checkNewLine = true;

				break;
			}
		}

		// Only process this once

		if (!checkNewLine) {
			if (_log.isInfoEnabled()) {
				_log.debug("Do not fix oracle new line");
			}

			return;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Fix oracle new line");
			}
		}

		List<JournalStructure> structures =
			JournalStructureLocalServiceUtil.getStructures(
				DEFAULT_GROUP_ID, 0, 1);

		if (structures.size() == 1) {
			JournalStructure structure = structures.get(0);

			String xsd = structure.getXsd();

			if ((xsd != null) && (xsd.indexOf("\\n") != -1)) {
				structures = JournalStructureLocalServiceUtil.getStructures(
					DEFAULT_GROUP_ID);

				for (int i = 0; i < structures.size(); i++) {
					structure = structures.get(i);

					JournalStructureLocalServiceUtil.checkNewLine(
						structure.getGroupId(), structure.getStructureId());
				}
			}
		}

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates(
				DEFAULT_GROUP_ID, 0, 1);

		if (templates.size() == 1) {
			JournalTemplate template = templates.get(0);

			String xsl = template.getXsl();

			if ((xsl != null) && (xsl.indexOf("\\n") != -1)) {
				templates = JournalTemplateLocalServiceUtil.getTemplates(
					DEFAULT_GROUP_ID);

				for (int i = 0; i < templates.size(); i++) {
					template = templates.get(i);

					JournalTemplateLocalServiceUtil.checkNewLine(
						template.getGroupId(), template.getTemplateId());
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyJournal.class);

}