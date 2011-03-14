/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.imagegallery.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGFolderConstants;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGFolderServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class IGIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {IGImage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.IMAGE_GALLERY;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.DESCRIPTION), 200);
		}

		String imageId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/image_gallery/view_image");
		portletURL.setParameter("imageId", imageId);

		return new Summary(title, content, portletURL);
	}

	protected void checkSearchFolderId(
			long folderId, SearchContext searchContext)
		throws Exception {

		if (folderId == IGFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		IGFolderServiceUtil.getFolder(folderId);
	}

	protected void doDelete(Object obj) throws Exception {
		IGImage image = (IGImage)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, image.getImageId());

		SearchEngineUtil.deleteDocument(
			image.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		IGImage image = (IGImage)obj;

		long companyId = image.getCompanyId();
		long groupId = getParentGroupId(image.getGroupId());
		long scopeGroupId = image.getGroupId();
		long userId = image.getUserId();
		long folderId = image.getFolderId();
		long imageId = image.getImageId();
		String name = image.getName();
		String description = image.getDescription();
		Date modifiedDate = image.getModifiedDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			IGImage.class.getName(), imageId);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				IGImage.class.getName(), imageId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			IGImage.class.getName(), imageId);

		ExpandoBridge expandoBridge = image.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, imageId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);

		document.addText(Field.TITLE, name);
		document.addText(Field.DESCRIPTION, description);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_CATEGORY_NAMES, assetCategoryNames);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.FOLDER_ID, folderId);
		document.addKeyword(Field.ENTRY_CLASS_NAME, IGImage.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, imageId);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		IGImage image = (IGImage)obj;

		Document document = getDocument(image);

		SearchEngineUtil.updateDocument(image.getCompanyId(), document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
		IGImage image = IGImageLocalServiceUtil.getImage(classPK);

		doReindex(image);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexFolders(long companyId) throws Exception {
		int folderCount = IGFolderLocalServiceUtil.getCompanyFoldersCount(
			companyId);

		int folderPages = folderCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= folderPages; i++) {
			int folderStart = (i * Indexer.DEFAULT_INTERVAL);
			int folderEnd = folderStart + Indexer.DEFAULT_INTERVAL;

			reindexFolders(companyId, folderStart, folderEnd);
		}
	}

	protected void reindexFolders(
			long companyId, int folderStart, int folderEnd)
		throws Exception {

		List<IGFolder> folders = IGFolderLocalServiceUtil.getCompanyFolders(
			companyId, folderStart, folderEnd);

		for (IGFolder folder : folders) {
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			int entryCount = IGImageLocalServiceUtil.getImagesCount(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexImages(
					companyId, groupId, folderId, entryStart, entryEnd);
			}
		}
	}

	protected void reindexImages(
			long companyId, long groupId, long folderId, int entryStart,
			int entryEnd)
		throws Exception {

		List<IGImage> images = IGImageLocalServiceUtil.getImages(
			groupId, folderId, entryStart, entryEnd);

		if (images.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (IGImage image : images) {
			Document document = getDocument(image);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long folderId = IGFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			int entryCount = IGImageLocalServiceUtil.getImagesCount(
				groupId, folderId);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int j = 0; j <= entryPages; j++) {
				int entryStart = (j * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexImages(
					companyId, groupId, folderId, entryStart, entryEnd);
			}
		}
	}

}