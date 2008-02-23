/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.action.ExportAction;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="VerifyOracle.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class VerifyOracle extends VerifyProcess {

	public static final int NUM_OF_ARTICLES = GetterUtil.getInteger(
		PropsUtil.get(VerifyOracle.class.getName()), 5);

	public void verify() throws VerifyException {
		_log.info("Verifying");

		try {
			verifyOracle();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	protected void verifyOracle() throws Exception {

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
				ExportAction.DEFAULT_GROUP_ID);
		}
		else {
			articles = JournalArticleLocalServiceUtil.getArticles(
				ExportAction.DEFAULT_GROUP_ID, 0, NUM_OF_ARTICLES);
		}

		for (int i = 0; i < articles.size(); i++) {
			JournalArticle article = articles.get(i);

			String content = article.getContent();

			if ((content != null) && (content.indexOf("\\n") != -1)) {
				articles = JournalArticleLocalServiceUtil.getArticles(
					ExportAction.DEFAULT_GROUP_ID);

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
				ExportAction.DEFAULT_GROUP_ID, 0, 1);

		if (structures.size() == 1) {
			JournalStructure structure = structures.get(0);

			String xsd = structure.getXsd();

			if ((xsd != null) && (xsd.indexOf("\\n") != -1)) {
				structures = JournalStructureLocalServiceUtil.getStructures(
					ExportAction.DEFAULT_GROUP_ID);

				for (int i = 0; i < structures.size(); i++) {
					structure = structures.get(i);

					JournalStructureLocalServiceUtil.checkNewLine(
						structure.getGroupId(), structure.getStructureId());
				}
			}
		}

		List<JournalTemplate> templates =
			JournalTemplateLocalServiceUtil.getTemplates(
				ExportAction.DEFAULT_GROUP_ID, 0, 1);

		if (templates.size() == 1) {
			JournalTemplate template = templates.get(0);

			String xsl = template.getXsl();

			if ((xsl != null) && (xsl.indexOf("\\n") != -1)) {
				templates = JournalTemplateLocalServiceUtil.getTemplates(
					ExportAction.DEFAULT_GROUP_ID);

				for (int i = 0; i < templates.size(); i++) {
					template = templates.get(i);

					JournalTemplateLocalServiceUtil.checkNewLine(
						template.getGroupId(), template.getTemplateId());
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(VerifyOracle.class);

}