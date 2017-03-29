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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _DYNAMIC_TAG_REGEXP.matcher(replacedContent);

		while (matcher.find()) {
			long fileEntryId = Long.parseLong(matcher.group(1));

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					_exportImportPlaceholderFactory.createDynamicPlaceholder(
						fileEntry)));

			referenceExporter.exportReference(fileEntry);
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		EmbeddedReferenceSet embeddedReferenceSet =
			_embeddedReferenceSetFactory.create(
				portletDataContext, stagedModel);

		String replacedContent =
			_exportImportContentProcessor.replaceImportContentReferences(
				portletDataContext, stagedModel, content);

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _DYNAMIC_PLACEHOLDER_REGEXP.matcher(replacedContent);

		while (matcher.find()) {
			String path = matcher.group(1);

			if (embeddedReferenceSet.containsReference(path)) {
				long fileEntryId = embeddedReferenceSet.importReference(path);

				FileEntry fileEntry = _getFileEntry(fileEntryId);

				if (fileEntry != null) {
					matcher.appendReplacement(
						sb,
						Matcher.quoteReplacement(
							_adaptiveMediaTagFactory.createDynamicTag(
								fileEntry)));
				}
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
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

	@Reference(unbind = "-")
	public void setEmbeddedReferenceSetFactory(
		EmbeddedReferenceSetFactory embeddedReferenceSetFactory) {

		_embeddedReferenceSetFactory = embeddedReferenceSetFactory;
	}

	@Reference(
		target = "(objectClass=com.liferay.blogs.internal.exportimport.content.processor.BlogsEntryExportImportContentProcessor)",
		unbind = "-"
	)
	public void setExportImportContentProcessor(
		ExportImportContentProcessor<String> exportImportContentProcessor) {

		_exportImportContentProcessor = exportImportContentProcessor;
	}

	@Reference(unbind = "-")
	public void setExportImportPlaceholderFactory(
		ExportImportPlaceholderFactory exportImportPlaceholderFactory) {

		_exportImportPlaceholderFactory = exportImportPlaceholderFactory;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		_exportImportContentProcessor.validateContentReferences(
			groupId, content);

		Matcher matcher = _DYNAMIC_TAG_REGEXP.matcher(content);

		while (matcher.find()) {
			long fileEntryId = Long.parseLong(matcher.group(1));

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

	private static final Pattern _DYNAMIC_PLACEHOLDER_REGEXP = Pattern.compile(
		"\\[\\$adaptive-media-dynamic-media path=\"([^\"]+)\"\\$]",
		Pattern.CASE_INSENSITIVE);

	private static final Pattern _DYNAMIC_TAG_REGEXP = Pattern.compile(
		"<img data-fileentryid=\"(\\d+)\" src=\"[^\"]+\" />",
		Pattern.CASE_INSENSITIVE);

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaBlogsEntryExportImportContentProcessor.class);

	private AdaptiveMediaTagFactory _adaptiveMediaTagFactory;
	private DLAppLocalService _dlAppLocalService;
	private EmbeddedReferenceSetFactory _embeddedReferenceSetFactory;
	private ExportImportContentProcessor<String> _exportImportContentProcessor;
	private ExportImportPlaceholderFactory _exportImportPlaceholderFactory;

}