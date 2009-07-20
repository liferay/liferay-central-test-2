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

package com.liferay.portlet.softwarecatalog.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

public class Indexer
	implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.SOFTWARE_CATALOG;

	public static void addProductEntry(
			long companyId, long groupId, long userId, String userName,
			long productEntryId, String name, Date modifiedDate, String version,
			String type, String shortDescription, String longDescription,
			String pageURL, String repoGroupId, String repoArtifactId,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, expandoBridge);

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
		String pageURL, String repoGroupId, String repoArtifactId,
		ExpandoBridge expandoBridge) {

		userName = PortalUtil.getUserName(userId, userName);
		shortDescription = HtmlUtil.extractText(shortDescription);
		longDescription = HtmlUtil.extractText(longDescription);

		String content =
			userId + " " + userName + " " + type + " " + shortDescription +
				" " + longDescription + " " + pageURL + repoGroupId + " " +
					repoArtifactId;

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, productEntryId);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);
		doc.addKeyword(Field.USER_ID, userId);
		doc.addText(Field.USER_NAME, userName);

		doc.addText(Field.TITLE, name);
		doc.addText(Field.CONTENT, content);

		doc.addKeyword(Field.ENTRY_CLASS_NAME, SCProductEntry.class.getName());
		doc.addKeyword(Field.ENTRY_CLASS_PK, productEntryId);
		doc.addKeyword("version", version);
		doc.addKeyword("type", type);
		doc.addText("shortDescription", shortDescription);
		doc.addText("longDescription", longDescription);
		doc.addText("pageURL", pageURL);
		doc.addKeyword("repoGroupId", repoGroupId);
		doc.addKeyword("repoArtifactId", repoArtifactId);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

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
			String pageURL, String repoGroupId, String repoArtifactId,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, expandoBridge);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		Document doc, String snippet, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(doc.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String productEntryId = doc.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/software_catalog/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			SCProductEntryLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			SCProductEntryLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		SCProductEntry.class.getName()
	};

}