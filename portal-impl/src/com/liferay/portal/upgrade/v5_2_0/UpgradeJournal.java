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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.PwdGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeJournal extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {
		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles();

		for (JournalArticle article : articles) {
			String content = GetterUtil.getString(article.getContent());

			if (Validator.isNotNull(article.getStructureId())) {
				String newContent = addDynamicElementInstanceId(content);

				if (!content.equals(newContent)) {
					JournalArticleLocalServiceUtil.updateContent(
						article.getGroupId(), article.getArticleId(),
						article.getVersion(), newContent);
				}
			}
		}

		deleteJournalArticleImages();
	}

	protected String addDynamicElementInstanceId(String content)
		throws Exception {

		Document doc = SAXReaderUtil.read(content);

		Element root = doc.getRootElement();

		addDynamicElementInstanceId(root);

		return JournalUtil.formatXML(doc);
	}

	protected void addDynamicElementInstanceId(Element root) throws Exception {
		Iterator<Element> itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element element = itr.next();

			if (!element.getName().equals("dynamic-element")) {
				continue;
			}

			String instanceId = element.attributeValue("instance-id");
			String type = element.attributeValue("type");

			if (Validator.isNull(instanceId)) {
				instanceId = PwdGenerator.getPassword();

				element.addAttribute("instance-id", instanceId);

				if (type.equals("image")) {
					updateJournalArticleImageInstanceId(element, instanceId);
				}
			}

			addDynamicElementInstanceId(element);
		}
	}

	protected void deleteJournalArticleImages() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"delete from JournalArticleImage where elInstanceId is null " +
					"or elInstanceId = ''");

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateJournalArticleImageInstanceId(
			Element parentElement, String instanceId)
		throws Exception {

		Iterator<Element> itr = parentElement.elements(
			"dynamic-content").iterator();

		while (itr.hasNext()) {
			Element element = itr.next();

			long articleImageId = GetterUtil.getLong(
				element.attributeValue("id"));

			if (articleImageId <= 0) {
				continue;
			}

			JournalArticleImage articleImage =
				JournalArticleImageLocalServiceUtil.getArticleImage(
					articleImageId);

			articleImage.setElInstanceId(instanceId);

			JournalArticleImageLocalServiceUtil.updateJournalArticleImage(
				articleImage);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeJournal.class);

}