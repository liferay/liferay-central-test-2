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

package com.liferay.portlet.rss.lar;

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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class RSSPortletDataHandlerImpl extends JournalPortletDataHandlerImpl {

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

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return 	_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		preferences.setValue("footer-article-values", StringPool.BLANK);
		preferences.setValue("header-article-values", StringPool.BLANK);
		preferences.setValue("urls", StringPool.BLANK);
		preferences.setValue("titles", StringPool.BLANK);
		preferences.setValue("items-per-channel", StringPool.BLANK);
		preferences.setValue("expanded-items-per-channel", StringPool.BLANK);
		preferences.setValue("show-feed-title", StringPool.BLANK);
		preferences.setValue("show-feed-published-date", StringPool.BLANK);
		preferences.setValue("show-feed-description", StringPool.BLANK);
		preferences.setValue("show-feed-image", StringPool.BLANK);
		preferences.setValue("feed-image-alignment", StringPool.BLANK);
		preferences.setValue("show-feed-item-author", StringPool.BLANK);

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		String[] footerArticleValues = preferences.getValues(
			"footer-article-values", new String[] {"0", ""});
		String[] headerArticleValues = preferences.getValues(
			"header-article-values", new String[] {"0", ""});

		String footerArticleId = footerArticleValues[1];
		String headerArticleId = headerArticleValues[1];

		if (Validator.isNull(footerArticleId) &&
			Validator.isNull(headerArticleId)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"No article ids found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
		}

		long footerArticleGroupId = GetterUtil.getLong(footerArticleValues[0]);
		long headerArticleGroupId = GetterUtil.getLong(headerArticleValues[0]);

		if ((footerArticleGroupId <= 0) && (headerArticleGroupId <= 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ids found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
		}

		List<JournalArticle> articles = new ArrayList<JournalArticle>(2);

		JournalArticle footerArticle = null;

		try {
			footerArticle = JournalArticleLocalServiceUtil.getLatestArticle(
				footerArticleGroupId, footerArticleId,
				WorkflowConstants.STATUS_APPROVED);

			articles.add(footerArticle);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No approved article found with group id " +
						footerArticleGroupId + " and article id " +
							footerArticleId);
			}
		}

		JournalArticle headerArticle = null;

		try {
			headerArticle = JournalArticleLocalServiceUtil.getLatestArticle(
				headerArticleGroupId, headerArticleId,
				WorkflowConstants.STATUS_APPROVED);

			articles.add(headerArticle);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No approved article found with group id " +
						headerArticleGroupId + " and article id " +
							headerArticleId);
			}
		}

		if ((footerArticle == null) && (headerArticle == null)) {
			return StringPool.BLANK;
		}

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("journal-content-data");

		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element igFoldersElement = rootElement.addElement("ig-folders");
		Element igImagesElement = rootElement.addElement("ig-images");

		for (JournalArticle article : articles) {
			String path = JournalPortletDataHandlerImpl.getArticlePath(
				context, article);

			Element articleElement = null;

			if (article == footerArticle) {
				articleElement = rootElement.addElement("footer-article");
			}
			else {
				articleElement = rootElement.addElement("header-article");
			}

			articleElement.addAttribute("path", path);

			JournalPortletDataHandlerImpl.exportArticle(
				context, rootElement, rootElement, rootElement,
				dlFoldersElement, dlFilesElement, dlFileRanksElement,
				igFoldersElement, igImagesElement, article, false);

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				article.getCompanyId());

			String structureId = article.getStructureId();

			if (Validator.isNotNull(structureId)) {
				JournalStructure structure = null;

				try {
					structure = JournalStructureLocalServiceUtil.getStructure(
						article.getGroupId(), structureId);
				}
				catch (NoSuchStructureException nsse) {
					structure = JournalStructureLocalServiceUtil.getStructure(
						companyGroup.getGroupId(), structureId);
				}

				JournalPortletDataHandlerImpl.exportStructure(
					context, rootElement, structure);
			}

			String templateId = article.getTemplateId();

			if (Validator.isNotNull(templateId)) {
				JournalTemplate template = null;

				try {
					template = JournalTemplateLocalServiceUtil.getTemplate(
						context.getScopeGroupId(), templateId);
				}
				catch (NoSuchTemplateException nste) {
					template = JournalTemplateLocalServiceUtil.getTemplate(
						companyGroup.getGroupId(), templateId);
				}

				JournalPortletDataHandlerImpl.exportTemplate(
					context, rootElement, dlFoldersElement, dlFilesElement,
					dlFileRanksElement, igFoldersElement, igImagesElement,
					template, false);
			}
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

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

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			JournalPortletDataHandlerImpl.importStructure(
				context, structureElement);
		}

		List<Element> templateElements = rootElement.elements("template");

		for (Element templateElement : templateElements) {
			JournalPortletDataHandlerImpl.importTemplate(
				context, templateElement);
		}

		Map<String, String> articleIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalArticle.class);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			context.getPlid());

		Element footerArticleElement = rootElement.element("footer-article");

		if (footerArticleElement != null) {
			JournalPortletDataHandlerImpl.importArticle(
				context, footerArticleElement);
		}

		String[] footerArticleValues = preferences.getValues(
			"footer-article-values", new String[] {"0", ""});

		String footerArticleId = footerArticleValues[1];

		if (Validator.isNotNull(footerArticleId)) {
			footerArticleId = MapUtil.getString(
				articleIds, footerArticleId, footerArticleId);

			preferences.setValues(
				"footer-article-values",
				new String[] {
					String.valueOf(context.getScopeGroupId()), footerArticleId
				});

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				context.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, footerArticleId, true);
		}

		Element headerArticleElement = rootElement.element("header-article");

		if (headerArticleElement != null) {
			JournalPortletDataHandlerImpl.importArticle(
				context, headerArticleElement);
		}

		String[] headerArticleValues = preferences.getValues(
			"header-article-values", new String[] {"0", ""});

		String headerArticleId = headerArticleValues[1];

		if (Validator.isNotNull(headerArticleId)) {
			headerArticleId = MapUtil.getString(
				articleIds, headerArticleId, headerArticleId);

			preferences.setValues(
				"header-article-values",
				new String[] {
					String.valueOf(context.getScopeGroupId()), headerArticleId
				});

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				context.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, headerArticleId, true);
		}

		return preferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = false;

	private static final String _NAMESPACE = "rss";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static Log _log = LogFactoryUtil.getLog(
		RSSPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _selectedArticles =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "selected-web-content", true, true);

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}