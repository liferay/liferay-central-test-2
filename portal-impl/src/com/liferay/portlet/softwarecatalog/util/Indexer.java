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

package com.liferay.portlet.softwarecatalog.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;
import com.liferay.util.search.DocumentImpl;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 *
 */
public class Indexer
	implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

	public static void addProductEntry(
			long companyId, long groupId, long userId, String userName,
			long productEntryId, String name, Date modifiedDate, String version,
			String type, String shortDescription, String longDescription,
			String pageURL, String repoGroupId, String repoArtifactId)
		throws SearchException {

		Document doc = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteProductEntry(long companyId, long productEntryId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getEntryUID(productEntryId));
	}

	public static Document getProductEntryDocument(
		long companyId, long groupId, long userId, String userName,
		long productEntryId, String name, Date modifiedDate, String version,
		String type, String shortDescription, String longDescription,
		String pageURL, String repoGroupId, String repoArtifactId) {

		shortDescription = HtmlUtil.extractText(shortDescription);
		longDescription = HtmlUtil.extractText(longDescription);

		String content =
			userId + " " + userName + " " + type + " " + shortDescription +
				" " + longDescription + " " + pageURL + repoGroupId + " " +
					repoArtifactId;

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, productEntryId);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);
		doc.addKeyword(Field.USER_ID, userId);

		doc.addText(Field.NAME, name);
		doc.addText(Field.CONTENT, content);

		doc.addModifiedDate();

		doc.addKeyword("productEntryId", productEntryId);
		doc.addDate("modified-date", modifiedDate);
		doc.addText("version", version);
		doc.addKeyword("type", type);
		doc.addKeyword("repoGroupId", repoGroupId);
		doc.addKeyword("repoArtifactId", repoArtifactId);

		return doc;
	}

	public static String getEntryUID(long productEntryId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, productEntryId);

		return doc.get(Field.UID);
	}

	public static void updateProductEntry(
			long companyId, long groupId, long userId, String userName,
			long productEntryId, String name, Date modifiedDate, String version,
			String type, String shortDescription, String longDescription,
			String pageURL, String repoGroupId, String repoArtifactId)
		throws SearchException {

		Document doc = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = doc.get(Field.CONTENT);

		content = StringUtil.shorten(content, 200);

		// Portlet URL

		String productEntryId = doc.get("productEntryId");

		portletURL.setParameter(
			"struts_action", "/software_catalog/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			SCProductEntryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

}