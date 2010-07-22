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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
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
 * @see	   com.liferay.portal.kernel.lar.PortletDataHandler
 * @see	   com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see	   com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 */
public class JournalContentPortletDataHandlerImpl
	extends BasePortletDataHandler {

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

	public boolean isPublishToLiveByDefault() {
		return 	_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("group-id", StringPool.BLANK);
		preferences.setValue("article-id", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.journal", context.getScopeGroupId());

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
					"No group id found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getLatestArticle(
				articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);
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

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("journal-content-data");

		String path = JournalPortletDataHandlerImpl.getArticlePath(
			context, article);

		Element articleElement = rootElement.addElement("article");

		articleElement.addAttribute("path", path);

		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element igFoldersElement = rootElement.addElement("ig-folders");
		Element igImagesElement = rootElement.addElement("ig-images");

		JournalPortletDataHandlerImpl.exportArticle(
			context, rootElement, dlFoldersElement, dlFilesElement,
			dlFileRanksElement, igFoldersElement, igImagesElement, article,
			false);

		String structureId = article.getStructureId();

		if (Validator.isNotNull(structureId)) {
			JournalStructure structure = JournalStructureUtil.findByG_S(
				article.getGroupId(), structureId);

			JournalPortletDataHandlerImpl.exportStructure(
				context, rootElement, structure);
		}

		String templateId = article.getTemplateId();

		if (Validator.isNotNull(templateId)) {
			JournalTemplate template = JournalTemplateUtil.findByG_T(
				article.getGroupId(), templateId);

			JournalPortletDataHandlerImpl.exportTemplate(
				context, rootElement, dlFoldersElement, dlFilesElement,
				dlFileRanksElement, igFoldersElement, igImagesElement,
				template);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.journal", context.getSourceGroupId(),
			context.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element dlFoldersElement = rootElement.element("dl-folders");

		List<Element> dlFolderElements = Collections.EMPTY_LIST;

		if (dlFoldersElement != null) {
			dlFolderElements = dlFoldersElement.elements("folder");
		}

		for (Element folderElement : dlFolderElements) {
			DLPortletDataHandlerImpl.importFolder(context, folderElement);
		}

		Element dlFileEntriesElement = rootElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = Collections.EMPTY_LIST;

		if (dlFileEntriesElement != null) {
			dlFileEntryElements = dlFileEntriesElement.elements("file-entry");
		}

		for (Element fileEntryElement : dlFileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(context, fileEntryElement);
		}

		Element dlFileRanksElement = rootElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = Collections.EMPTY_LIST;

		if (dlFileRanksElement != null) {
			dlFileRankElements = dlFileRanksElement.elements("file-rank");
		}

		for (Element fileRankElement : dlFileRankElements) {
			DLPortletDataHandlerImpl.importFileRank(context, fileRankElement);
		}

		Element igFoldersElement = rootElement.element("ig-folders");

		List<Element> igFolderElements = Collections.EMPTY_LIST;

		if (igFoldersElement != null) {
			igFolderElements = igFoldersElement.elements("folder");
		}

		for (Element folderElement : igFolderElements) {
			IGPortletDataHandlerImpl.importFolder(context, folderElement);
		}

		Element igImagesElement = rootElement.element("ig-images");

		List<Element> igImageElements = Collections.EMPTY_LIST;

		if (igImagesElement != null) {
			igImageElements = igImagesElement.elements("image");
		}

		for (Element imageElement : igImageElements) {
			IGPortletDataHandlerImpl.importImage(context, imageElement);
		}

		Element structureElement = rootElement.element("structure");

		if (structureElement != null) {
			JournalPortletDataHandlerImpl.importStructure(
				context, structureElement);
		}

		Element templateElement = rootElement.element("template");

		if (templateElement != null) {
			JournalPortletDataHandlerImpl.importTemplate(
				context, templateElement);
		}

		Element articleElement = rootElement.element("article");

		if (articleElement != null) {
			JournalPortletDataHandlerImpl.importArticle(
				context, articleElement);
		}

		String articleId = preferences.getValue("article-id", StringPool.BLANK);

		if (Validator.isNotNull(articleId)) {
			Map<String, String> articleIds =
				(Map<String, String>)context.getNewPrimaryKeysMap(
					JournalArticle.class);

			articleId = MapUtil.getString(articleIds, articleId, articleId);

			preferences.setValue(
				"group-id", String.valueOf(context.getScopeGroupId()));
			preferences.setValue("article-id", articleId);

			Layout layout = LayoutLocalServiceUtil.getLayout(
				context.getPlid());

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				context.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId, true);
		}

		return preferences;
	}

	private static final String _NAMESPACE = "journal";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _selectedArticles =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "selected-web-content", true, true);

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}