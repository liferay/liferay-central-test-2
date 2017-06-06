/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.blogs.internal.exportimport.content.processor;

import com.liferay.adaptive.media.blogs.internal.util.CheckedFunction;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"model.class.name=com.liferay.blogs.kernel.model.BlogsEntry",
		"model.class.name=com.liferay.blogs.model.BlogsEntry",
		"service.ranking:Integer=100"
	}
)
public class AdaptiveMediaBlogsEntryExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		String replacedContent =
			_exportImportContentProcessor.replaceExportContentReferences(
				portletDataContext, stagedModel, content,
				exportReferencedContent, escapeContent);

		AdaptiveMediaReferenceExporter referenceExporter =
			new AdaptiveMediaReferenceExporter(
				portletDataContext, stagedModel, exportReferencedContent);

		return _replace(replacedContent, referenceExporter);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		AdaptiveMediaEmbeddedReferenceSet adaptiveMediaEmbeddedReferenceSet =
			_adaptiveMediaEmbeddedReferenceSetFactory.create(
				portletDataContext, stagedModel);

		String replacedContent =
			_exportImportContentProcessor.replaceImportContentReferences(
				portletDataContext, stagedModel, content);

		replacedContent = _replace(
			replacedContent, _DYNAMIC_PLACEHOLDER_REGEXP,
			adaptiveMediaEmbeddedReferenceSet,
			_adaptiveMediaTagFactory::createDynamicTag);

		return _replace(
			replacedContent, _STATIC_PLACEHOLDER_REGEXP,
			adaptiveMediaEmbeddedReferenceSet,
			_adaptiveMediaTagFactory::createStaticTag);
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaEmbeddedReferenceSetFactory(
		AdaptiveMediaEmbeddedReferenceSetFactory
			adaptiveMediaEmbeddedReferenceSetFactory) {

		_adaptiveMediaEmbeddedReferenceSetFactory =
			adaptiveMediaEmbeddedReferenceSetFactory;
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaExportImportPlaceholderFactory(
		AdaptiveMediaExportImportPlaceholderFactory
			adaptiveMediaExportImportPlaceholderFactory) {

		_adaptiveMediaExportImportPlaceholderFactory =
			adaptiveMediaExportImportPlaceholderFactory;
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaTagFactory(
		AdaptiveMediaTagFactory adaptiveMediaTagFactory) {

		_adaptiveMediaTagFactory = adaptiveMediaTagFactory;
	}

	@Reference(unbind = "-")
	public void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(
		target = "(objectClass=com.liferay.blogs.internal.exportimport.content.processor.BlogsEntryExportImportContentProcessor)",
		unbind = "-"
	)
	public void setExportImportContentProcessor(
		ExportImportContentProcessor<String> exportImportContentProcessor) {

		_exportImportContentProcessor = exportImportContentProcessor;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		_exportImportContentProcessor.validateContentReferences(
			groupId, content);

		Document document = _parseDocument(content);

		for (Element element : document.select("[data-fileEntryId]")) {
			long fileEntryId = Long.valueOf(element.attr("data-fileEntryId"));

			_dlAppLocalService.getFileEntry(fileEntryId);
		}
	}

	private FileEntry _getFileEntry(long fileEntryId) {
		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}

			return null;
		}
	}

	private Document _parseDocument(String html) {
		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		Document document = Jsoup.parseBodyFragment(html);

		document.outputSettings(outputSettings);

		return document;
	}

	private String _replace(
			String content, AdaptiveMediaReferenceExporter referenceExporter)
		throws PortalException {

		Document document = _parseDocument(content);

		for (Element element : document.select("[data-fileEntryId]")) {
			long fileEntryId = Long.valueOf(element.attr("data-fileEntryId"));

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			referenceExporter.exportReference(fileEntry);

			Function<FileEntry, String> placeholderFactory = null;

			if ("img".equals(element.tagName())) {
				placeholderFactory =
					_adaptiveMediaExportImportPlaceholderFactory::
						createDynamicPlaceholder;
			}
			else if ("picture".equals(element.tagName())) {
				placeholderFactory =
					_adaptiveMediaExportImportPlaceholderFactory::
						createStaticPlaceholder;
			}

			if (placeholderFactory != null) {
				element.replaceWith(
					new TextNode(placeholderFactory.apply(fileEntry), null));
			}
		}

		return document.body().html();
	}

	private String _replace(
			String content, Pattern pattern,
			AdaptiveMediaEmbeddedReferenceSet adaptiveMediaEmbeddedReferenceSet,
			CheckedFunction<FileEntry, String, PortalException> tagFactory)
		throws PortalException {

		StringBuffer sb = new StringBuffer();

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			String path = matcher.group(1);

			if (adaptiveMediaEmbeddedReferenceSet.containsReference(path)) {
				long fileEntryId =
					adaptiveMediaEmbeddedReferenceSet.importReference(path);

				FileEntry fileEntry = _getFileEntry(fileEntryId);

				if (fileEntry != null) {
					matcher.appendReplacement(
						sb,
						Matcher.quoteReplacement(tagFactory.apply(fileEntry)));
				}
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private static final Pattern _DYNAMIC_PLACEHOLDER_REGEXP = Pattern.compile(
		"\\[\\$adaptive-media-dynamic-media path=\"([^\"]+)\"\\$]",
		Pattern.CASE_INSENSITIVE);

	private static final Pattern _STATIC_PLACEHOLDER_REGEXP = Pattern.compile(
		"\\[\\$adaptive-media-static-media path=\"([^\"]+)\"\\$]",
		Pattern.CASE_INSENSITIVE);

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaBlogsEntryExportImportContentProcessor.class);

	private AdaptiveMediaEmbeddedReferenceSetFactory
		_adaptiveMediaEmbeddedReferenceSetFactory;
	private AdaptiveMediaExportImportPlaceholderFactory
		_adaptiveMediaExportImportPlaceholderFactory;
	private AdaptiveMediaTagFactory _adaptiveMediaTagFactory;
	private DLAppLocalService _dlAppLocalService;
	private ExportImportContentProcessor<String> _exportImportContentProcessor;

}