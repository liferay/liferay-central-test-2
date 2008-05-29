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

package com.liferay.portlet.wiki.importers.mediawiki;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.importers.WikiImporter;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.MediaWikiToCreoleTranslator;

import java.io.File;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="MediaWikiImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 *
 */
public class MediaWikiImporter implements WikiImporter {

	public void importPages(long userId, WikiNode node, File file)
		throws PortalException {

		int count = 0;

		try {
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader.read(file);

			Element root = doc.getRootElement();

			Iterator<Element> itr = root.elements("page").iterator();

			while (itr.hasNext()) {
				Element pageEl = itr.next();

				String author = pageEl.element("revision").element(
					"contributor").elementText("username");
				String title = pageEl.elementText("title");

				List<Element> revisionEls = pageEl.elements("revision");

				Element lastRevisionEl = revisionEls.get(
					revisionEls.size() - 1);

				String content = lastRevisionEl.elementText("text");

				addPage(userId, author, node, title, content);

				count++;
			}
		}
		catch (DocumentException e) {
			throw new PortalException(e);
		}

		_log.info("Imported " + count + " pages into " + node.getName());
	}

	protected void addPage(
			long userId, String author, WikiNode node, String title,
			String content)
		throws PortalException {

		try {
			long authorUserId = getUserId(userId, node, author);

			content = _translator.translate(content);

			WikiPage page = null;

			try {
				page = WikiPageLocalServiceUtil.getPage(
					node.getNodeId(), title);
			}
			catch (NoSuchPageException nspe) {
				page = WikiPageLocalServiceUtil.addPage(
					authorUserId, node.getNodeId(), title, null, null);
			}

			WikiPageLocalServiceUtil.updatePage(
				authorUserId, node.getNodeId(), title, page.getVersion(),
				content, "creole", StringPool.BLANK, StringPool.BLANK,
				new String[0], null, null);
		}
		catch (Exception e) {
			throw new PortalException("Error importing page " + title, e);
		}
	}

	protected long getUserId(long userId, WikiNode node, String author)
		throws PortalException, SystemException {

		User user = null;

		try {
			user = UserLocalServiceUtil.getUserByScreenName(
				node.getCompanyId(), author);
		}
		catch (NoSuchUserException e) {
			user = UserLocalServiceUtil.getUserById(userId);
		}

		return user.getUserId();
	}

	private static Log _log = LogFactory.getLog(MediaWikiImporter.class);

	private MediaWikiToCreoleTranslator _translator =
		new MediaWikiToCreoleTranslator();

}