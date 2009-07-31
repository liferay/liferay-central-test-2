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

package com.liferay.portlet.imagegallery.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;

import java.util.Date;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static final String PORTLET_ID = PortletKeys.IMAGE_GALLERY;

	public static void addImage(
			long companyId, long groupId, long folderId, long imageId,
			String name, String description, Date modifiedDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getImageDocument(
			companyId, groupId, folderId, imageId, name, description,
			modifiedDate, assetCategoryIds, assetTagNames, expandoBridge);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteImage(long companyId, long imageId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getImageUID(imageId));
	}

	public static Document getImageDocument(
		long companyId, long groupId, long folderId, long imageId,
		String name, String description, Date modifiedDate,
		long[] assetCategoryIds, String[] assetTagNames,
		ExpandoBridge expandoBridge) {

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, imageId);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, groupId);

		doc.addText(Field.TITLE, name);
		doc.addText(Field.DESCRIPTION, description);
		doc.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		doc.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		doc.addKeyword("folderId", folderId);
		doc.addKeyword(Field.ENTRY_CLASS_NAME, IGImage.class.getName());
		doc.addKeyword(Field.ENTRY_CLASS_PK, imageId);

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		return doc;
	}

	public static String getImageUID(long imageId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, imageId);

		return doc.get(Field.UID);
	}

	public static void updateImage(
			long companyId, long groupId, long folderId, long imageId,
			String name, String description, Date modifiedDate,
			long[] assetCategoryIds, String[] assetTagNames,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getImageDocument(
			companyId, groupId, folderId, imageId, name, description,
			modifiedDate, assetCategoryIds, assetTagNames, expandoBridge);

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
			content = StringUtil.shorten(doc.get(Field.DESCRIPTION), 200);
		}

		// Portlet URL

		String imageId = doc.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/image_gallery/edit_image");
		portletURL.setParameter("imageId", imageId);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			IGImageLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			IGFolderLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[] {
		IGImage.class.getName()
	};

}