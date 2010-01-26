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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
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

		Document document = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, expandoBridge);

		SearchEngineUtil.addDocument(companyId, document);
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

		long scopeGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				groupId = group.getParentGroupId();
			}
		}
		catch (Exception e) {
		}

		userName = PortalUtil.getUserName(userId, userName);
		shortDescription = HtmlUtil.extractText(shortDescription);
		longDescription = HtmlUtil.extractText(longDescription);

		String content =
			userId + " " + userName + " " + type + " " + shortDescription +
				" " + longDescription + " " + pageURL + repoGroupId + " " +
					repoArtifactId;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, productEntryId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, name);
		document.addText(Field.CONTENT, content);

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, SCProductEntry.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, productEntryId);
		document.addKeyword("version", version);
		document.addKeyword("type", type);
		document.addText("shortDescription", shortDescription);
		document.addText("longDescription", longDescription);
		document.addText("pageURL", pageURL);
		document.addKeyword("repoGroupId", repoGroupId);
		document.addKeyword("repoArtifactId", repoArtifactId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	public static String getEntryUID(long productEntryId) {
		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, productEntryId);

		return document.get(Field.UID);
	}

	public static void updateProductEntry(
			long companyId, long groupId, long userId, String userName,
			long productEntryId, String name, Date modifiedDate, String version,
			String type, String shortDescription, String longDescription,
			String pageURL, String repoGroupId, String repoArtifactId,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document document = getProductEntryDocument(
			companyId, groupId, userId, userName, productEntryId, name,
			modifiedDate, version, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, expandoBridge);

		SearchEngineUtil.updateDocument(
			companyId, document.get(Field.UID), document);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		// Title

		String title = document.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String productEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/software_catalog/view_product_entry");
		portletURL.setParameter("productEntryId", productEntryId);

		return new Summary(title, content, portletURL);
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