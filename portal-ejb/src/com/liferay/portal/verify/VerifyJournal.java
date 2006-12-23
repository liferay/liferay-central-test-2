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
package com.liferay.portal.verify;

import com.liferay.portal.service.ResourceLocalServiceUtil;
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
 * <a href="VerifyJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class VerifyJournal extends VerifyProcess {

	public void verify() throws VerifyException {
		_log.info("Checking integrity");

		try {
			_checkJournalPermissions();
		}
		catch (Exception e) {
			throw new VerifyException(e);
		}
	}

	/**
	 * Used to verify that all entries in the Journal portlet have permissions
	 * configured for them.
	 *
	 * @throws Exception
	 */
	private void _checkJournalPermissions() throws Exception {

		// Structures

		List structures = JournalStructureLocalServiceUtil.getStructures();

		for (int i = 0; i  < structures.size(); i++) {
			JournalStructure structure = (JournalStructure)structures.get(i);

			ResourceLocalServiceUtil.addResources(
				structure.getCompanyId(), null, null,
				JournalStructure.class.getName(),
				structure.getPrimaryKey().toString(), false, true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions checked for Journal Structures");
		}

		// Templates

		List templates = JournalTemplateLocalServiceUtil.getTemplates();

		for (int i = 0; i < templates.size(); i++) {
			JournalTemplate template = (JournalTemplate)templates.get(i);

			ResourceLocalServiceUtil.addResources(
				template.getCompanyId(), null, null,
				JournalTemplate.class.getName(),
				template.getPrimaryKey().toString(), false, true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions checked for Journal Templates");
		}

		// Articles

		List articles = JournalArticleLocalServiceUtil.getArticles();

		for (int i = 0; i < articles.size(); i++) {
			JournalArticle article = (JournalArticle)articles.get(i);

			ResourceLocalServiceUtil.addResources(
				article.getCompanyId(), null, null,
				JournalArticle.class.getName(),
				article.getResourcePK().toString(), false, true, true);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Permissions checked for Journal Articles");
		}
	}

	private static Log _log = LogFactory.getLog(VerifyJournal.class);

}