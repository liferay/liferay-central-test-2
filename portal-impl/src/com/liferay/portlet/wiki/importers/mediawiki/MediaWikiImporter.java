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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsPropertyLocalServiceUtil;
import com.liferay.portlet.tags.util.TagsUtil;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.importers.WikiImporter;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.translators.MediaWikiToCreoleTranslator;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		throws PortalException, SystemException {

		int count = 0;

		try {
			SAXReader saxReader = new SAXReader();

			Document doc = saxReader.read(file);

			Element root = doc.getRootElement();

			List<String> specialNamespaces = readSpecialNamespaces(root);

			// Category pages

			importCategoryPages(userId, node, root);

			// Regular pages

			Iterator<Element> itr = root.elements("page").iterator();

			while (itr.hasNext()) {
				Element pageEl = itr.next();

				String author = pageEl.element("revision").element(
					"contributor").elementText("username");
				String title = pageEl.elementText("title");

				if (isSpecialMediaWikiPage(title, specialNamespaces)) {
					continue;
				}

				List<Element> revisionEls = pageEl.elements("revision");

				Element lastRevisionEl = revisionEls.get(
					revisionEls.size() - 1);

				String content = lastRevisionEl.elementText("text");

				importPage(userId, author, node, title, content);

				count++;
			}
		}
		catch (DocumentException e) {
			throw new PortalException(e);
		}

		_log.info("Imported " + count + " pages into " + node.getName());
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

	protected void importCategoryPages(long userId, WikiNode node, Element root)
		throws PortalException, SystemException {

		Iterator<Element> itr = root.elements("page").iterator();

		while (itr.hasNext()) {
			Element page = itr.next();

			String title = page.elementText("title");

			if (!title.startsWith("Category:")) {
				continue;
			}

			String categoryName = title.substring("Category:".length());

			categoryName = normalize(categoryName, 75);

			String description = page.element("revision").elementText("text");

			description = normalizeDescription(description);

			try {
				TagsEntry tagsEntry = null;

				try {
					tagsEntry = TagsEntryLocalServiceUtil.getEntry(
						node.getCompanyId(), categoryName);
				}
				catch(NoSuchEntryException nsee) {
					tagsEntry = TagsEntryLocalServiceUtil.addEntry(
						userId, categoryName);

					if (Validator.isNotNull(description)) {
						TagsPropertyLocalServiceUtil.addProperty(
							userId, tagsEntry.getEntryId(), "description",
							description);
					}
				}
			}
			catch (SystemException se) {
				 _log.error(se, se);
			}
		}
	}

	protected void importPage(
			long userId, String author, WikiNode node, String title,
			String content)
		throws PortalException, SystemException {

		try {
			long authorUserId = getUserId(userId, node, author);

			String[] tagsEntries = readTagsEntries(userId, node, content);

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
				tagsEntries, null, null);
		}
		catch (Exception e) {
			throw new PortalException("Error importing page " + title, e);
		}
	}

	protected boolean isSpecialMediaWikiPage(
		String title, List<String> specialNamespaces) {

		for (String namespace: specialNamespaces) {
			if (title.startsWith(namespace + StringPool.COLON)) {
				return true;
			}
		}

		return false;
	}

	protected String normalize(String categoryName, int length) {
		categoryName = TagsUtil.toWord(categoryName.trim());

		return StringUtil.shorten(categoryName, length);
	}

	protected String normalizeDescription(String description) {
		description = description.replaceAll(
			_CATEGORIES_REGEXP, StringPool.BLANK);

		return normalize(description, 300);
	}

	protected List<String> readSpecialNamespaces(Element root) {
		List<String> namespaces = new ArrayList<String>();

		Iterator<Element> itr = root.element("siteinfo").element(
			"namespaces").elements("namespace").iterator();

		while (itr.hasNext()) {
			Element namespace = itr.next();

			if (!namespace.attribute("key").equals("0")) {
				namespaces.add(namespace.getText());
			}
		}

		return namespaces;
	}

	protected String[] readTagsEntries(
			long userId, WikiNode node, String content)
		throws PortalException, SystemException {

		Matcher matcher = Pattern.compile(
			_CATEGORIES_REGEXP).matcher(content);

		List<String> tagsEntries = new ArrayList<String>();

		while (matcher.find()) {
		    String categoryName = matcher.group(1);

			categoryName = normalize(categoryName, 75);

			TagsEntry tagsEntry = null;

			try {
				tagsEntry = TagsEntryLocalServiceUtil.getEntry(
					node.getCompanyId(), categoryName);
			}
			catch(NoSuchEntryException nsee) {
				tagsEntry = TagsEntryLocalServiceUtil.addEntry(
					userId, categoryName);
			}

			tagsEntries.add(tagsEntry.getName());
		}

		return tagsEntries.toArray(new String[tagsEntries.size()]);
	}

	private static final String _CATEGORIES_REGEXP =
		"\\[\\[[Cc]ategory:([^\\]]*)\\]\\][\\n]*";

	private static Log _log = LogFactory.getLog(MediaWikiImporter.class);

	private MediaWikiToCreoleTranslator _translator =
		new MediaWikiToCreoleTranslator();

}