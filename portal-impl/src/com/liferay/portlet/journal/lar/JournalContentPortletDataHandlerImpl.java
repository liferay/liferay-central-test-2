/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="JournalContentPortletDataHandlerImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * <p>
 * Provides the Journal Content portlet export and import functionality, which
 * is to clone the article, structure, and template referenced in the Journal
 * Content portlet if the article is associated with the layout's group. Upon
 * import, a new instance of the corresponding article, structure, and template
 * will be created or updated. The author of the newly created objects are
 * determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from from
 * <code>JournalPortletDataHandlerImpl</code> in that it only exports articles
 * referenced in Journal Content portlets. Articles not displayed in Journal
 * Content portlets will not be exported unless
 * <code>JournalPortletDataHandlerImpl</code> is activated.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 * @see	   com.liferay.portal.lar.PortletDataHandler
 * @see	   com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see	   com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 */
public class JournalContentPortletDataHandlerImpl
	extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			preferences.setValue("group-id", StringPool.BLANK);
			preferences.setValue("article-id", StringPool.BLANK);

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			String articleId = preferences.getValue("article-id", null);

			if (articleId == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No article id found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			long articleGroupId = GetterUtil.getLong(
				preferences.getValue("group-id", StringPool.BLANK));

			if (articleGroupId <= 0) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No group id found in preferences of portlet " +
							portletId);
				}

				return StringPool.BLANK;
			}

			JournalArticle article = null;

			try {
				article = JournalArticleLocalServiceUtil.getLatestArticle(
					articleGroupId, articleId, StatusConstants.APPROVED);
			}
			catch (NoSuchArticleException nsae) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No approved article found with group id " +
							articleGroupId + " and article id " + articleId);
				}
			}

			if (article == null) {
				return StringPool.BLANK;
			}

			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("journal-content-data");

			Element dlFoldersEl = root.addElement("dl-folders");
			Element dlFilesEl = root.addElement("dl-file-entries");
			Element dlFileRanksEl = root.addElement("dl-file-ranks");
			Element igFoldersEl = root.addElement("ig-folders");
			Element igImagesEl = root.addElement("ig-images");

			JournalPortletDataHandlerImpl.exportArticle(
				context, root, dlFoldersEl, dlFilesEl, dlFileRanksEl,
				igFoldersEl, igImagesEl, article);

			String structureId = article.getStructureId();

			if (Validator.isNotNull(structureId)) {
				JournalStructure structure = JournalStructureUtil.findByG_S(
					article.getGroupId(), structureId);

				JournalPortletDataHandlerImpl.exportStructure(
					context, root, structure);
			}

			String templateId = article.getTemplateId();

			if (Validator.isNotNull(templateId)) {
				JournalTemplate template = JournalTemplateUtil.findByG_T(
					article.getGroupId(), templateId);

				JournalPortletDataHandlerImpl.exportTemplate(
					context, root, dlFoldersEl, dlFilesEl, dlFileRanksEl,
					igFoldersEl, igImagesEl, template);
			}

			context.addPermissions(
				"com.liferay.portlet.journal", context.getGroupId());

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles, _embeddedAssets, _images, _comments, _ratings,
			_tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles, _images, _comments, _ratings, _tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			if (Validator.isNull(data)) {
				return null;
			}

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			Element structureEl = root.element("structure");

			Map<String, String> structureIds =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					JournalStructure.class);

			if (structureEl != null) {
				JournalPortletDataHandlerImpl.importStructure(
					context, structureIds, structureEl);
			}

			Element templateEl = root.element("template");

			Map<String, String> templateIds =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					JournalTemplate.class);

			if (templateEl != null) {
				JournalPortletDataHandlerImpl.importTemplate(
					context, structureIds, templateIds, templateEl);
			}

			Element articleEl = root.element("article");

			Map<String, String> articleIds =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					JournalArticle.class);

			if (articleEl != null) {
				JournalPortletDataHandlerImpl.importArticle(
					context, structureIds, templateIds, articleIds, articleEl);
			}

			Element dlFoldersEl = root.element("dl-folders");

			List<Element> dlFolderEls = Collections.EMPTY_LIST;

			if (dlFoldersEl != null) {
				dlFolderEls = dlFoldersEl.elements("folder");
			}

			Map<Long, Long> dlFolderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

			for (Element folderEl : dlFolderEls) {
				String path = folderEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

				DLPortletDataHandlerImpl.importFolder(
					context, dlFolderPKs, folder);
			}

			Element dlFileEntriesEl = root.element("dl-file-entries");

			List<Element> dlFileEntryEls = Collections.EMPTY_LIST;

			if (dlFileEntriesEl != null) {
				dlFileEntryEls = dlFileEntriesEl.elements("file-entry");
			}

			Map<String, String> fileEntryNames =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					DLFileEntry.class);

			for (Element fileEntryEl : dlFileEntryEls) {
				String path = fileEntryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				DLFileEntry fileEntry =
					(DLFileEntry)context.getZipEntryAsObject(path);

				String binPath = fileEntryEl.attributeValue("bin-path");

				DLPortletDataHandlerImpl.importFileEntry(
					context, dlFolderPKs, fileEntryNames, fileEntry, binPath);
			}

			Element dlFileRanksEl = root.element("dl-file-ranks");

			List<Element> dlFileRankEls = Collections.EMPTY_LIST;

			if (dlFileRanksEl != null) {
				dlFileRankEls = dlFileRanksEl.elements("file-rank");
			}

			for (Element fileRankEl : dlFileRankEls) {
				String path = fileRankEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				DLFileRank fileRank =
					(DLFileRank)context.getZipEntryAsObject(path);

				DLPortletDataHandlerImpl.importFileRank(
					context, dlFolderPKs, fileEntryNames, fileRank);
			}

			Element igFoldersEl = root.element("ig-folders");

			List<Element> igFolderEls = Collections.EMPTY_LIST;

			if (igFoldersEl != null) {
				igFolderEls = igFoldersEl.elements("folder");
			}

			Map<Long, Long> igFolderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(IGFolder.class);

			for (Element folderEl : igFolderEls) {
				String path = folderEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				IGFolder folder = (IGFolder)context.getZipEntryAsObject(path);

				IGPortletDataHandlerImpl.importFolder(
					context, igFolderPKs, folder);
			}

			Element igImagesEl = root.element("ig-images");

			List<Element> igImageEls = Collections.EMPTY_LIST;

			if (igImagesEl != null) {
				igImageEls = igImagesEl.elements("image");
			}

			for (Element imageEl : igImageEls) {
				String path = imageEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				IGImage image = (IGImage)context.getZipEntryAsObject(path);

				String binPath = imageEl.attributeValue("bin-path");

				IGPortletDataHandlerImpl.importImage(
					context, igFolderPKs, image, binPath);
			}

			String articleId = preferences.getValue(
				"article-id", StringPool.BLANK);

			if (Validator.isNotNull(articleId)) {
				articleId = MapUtil.getString(articleIds, articleId, articleId);

				preferences.setValue(
					"group-id", String.valueOf(context.getGroupId()));
				preferences.setValue("article-id", articleId);

				Layout layout = LayoutLocalServiceUtil.getLayout(
					context.getPlid());

				JournalContentSearchLocalServiceUtil.updateContentSearch(
					context.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), portletId, articleId, true);
			}

			context.importPermissions(
				"com.liferay.portlet.journal", context.getSourceGroupId(),
				context.getGroupId());

			return preferences;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return 	_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static final String _NAMESPACE = "journal";

	private static final PortletDataHandlerBoolean _selectedArticles =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "selected-web-content", true, true);

	private static final PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static final PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentPortletDataHandlerImpl.class);

}