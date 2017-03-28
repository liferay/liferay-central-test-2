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

import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaBlogsEntryExportImportContentProcessorTest {

	@Before
	public void setUp() {
		_blogsExportImportContentProcessor.setDLAppLocalService(
			_dlAppLocalService);
		_blogsExportImportContentProcessor.setExportImportContentProcessor(
			_exportImportContentProcessor);
		_blogsExportImportContentProcessor.setExportImportPlaceholderFactory(
			_exportImportPlaceholderFactory);
		_blogsExportImportContentProcessor.setAdaptiveMediaTagFactory(
			_adaptiveMediaTagFactory);
		_blogsExportImportContentProcessor.setEmbeddedReferenceSetFactory(
			_embeddedReferenceSetFactory);

		Mockito.doReturn(
			_embeddedReferenceSet
		).when(
			_embeddedReferenceSetFactory
		).create(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class)
		);
	}

	@Test
	public void testExportContentWithMultipleReferences() throws Exception {
		String prefix = StringUtil.randomString();
		String infix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<img data-fileentryid=\"1\" src=\"url1\" />" + infix +
				"<img data-fileentryid=\"2\" src=\"url2\" />" + suffix;

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToExport(1, _fileEntry1);
		_defineFileEntryToExport(2, _fileEntry2);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false);

		StringBundler sb = new StringBundler();

		sb.append(prefix);
		sb.append("PLACEHOLDER_1");
		sb.append(infix);
		sb.append("PLACEHOLDER_2");
		sb.append(suffix);

		Assert.assertEquals(sb.toString(), replacedContent);
	}

	@Test
	public void testExportContentWithNoReferences() throws Exception {
		String content = StringUtil.randomString();

		_makeOverridenProcessorReturn(content);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false);

		Assert.assertEquals(content, replacedContent);
	}

	@Test
	public void testExportContentWithNoReferencesDoesNotEscape()
		throws Exception {

		String content = StringPool.AMPERSAND;

		_makeOverridenProcessorReturn(content);

		Assert.assertEquals(
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false),
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, Mockito.mock(BlogsEntry.class), content,
				false, true));
	}

	@Test
	public void testExportContentWithReference() throws Exception {
		String prefix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "<img data-fileentryid=\"1\" src=\"url\" />" + suffix;

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToExport(1, _fileEntry1);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false);

		Assert.assertEquals(prefix + "PLACEHOLDER_1" + suffix, replacedContent);
	}

	@Test
	public void testExportContentWithReferenceDoesNotEscape() throws Exception {
		String content = "&<img data-fileentryid=\"1\" src=\"url\" />&";

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToExport(1, _fileEntry1);

		Assert.assertEquals(
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, false),
			_blogsExportImportContentProcessor.replaceExportContentReferences(
				_portletDataContext, _blogsEntry, content, false, true));
	}

	@Test
	public void testImportContentIgnoresInvalidReferences() throws Exception {
		String content = "[$adaptive-media-dynamic-media path=\"PATH_1\"$]";

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToImport(1, _fileEntry1);

		Mockito.doThrow(
			PortalException.class
		).when(
			_dlAppLocalService
		).getFileEntry(
			Mockito.anyLong()
		);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceImportContentReferences(
				_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(content, replacedContent);
	}

	@Test
	public void testImportContentWithMultipleReferences() throws Exception {
		String prefix = StringUtil.randomString();
		String infix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "[$adaptive-media-dynamic-media path=\"PATH_1\"$]" +
				infix + "[$adaptive-media-dynamic-media path=\"PATH_2\"$]" +
					suffix;

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToImport(1, _fileEntry1);
		_defineFileEntryToImport(2, _fileEntry2);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceImportContentReferences(
				_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(
			prefix + "PLACEHOLDER_1" + infix + "PLACEHOLDER_2" + suffix,
			replacedContent);
	}

	@Test
	public void testImportContentWithNoReferences() throws Exception {
		String content = StringUtil.randomString();

		_makeOverridenProcessorReturn(content);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceImportContentReferences(
				_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(content, replacedContent);
	}

	@Test
	public void testImportContentWithReference() throws Exception {
		String prefix = StringUtil.randomString();
		String suffix = StringUtil.randomString();

		String content =
			prefix + "[$adaptive-media-dynamic-media path=\"PATH_1\"$]" +
				suffix;

		_makeOverridenProcessorReturn(content);

		_defineFileEntryToImport(1, _fileEntry1);

		String replacedContent =
			_blogsExportImportContentProcessor.replaceImportContentReferences(
				_portletDataContext, _blogsEntry, content);

		Assert.assertEquals(prefix + "PLACEHOLDER_1" + suffix, replacedContent);
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testValidateContentFailsWhenInvalidReferences()
		throws Exception {

		String content = "<img data-fileentryid=\"1\" src=\"PATH_1\" />";

		Mockito.doThrow(
			NoSuchFileEntryException.class
		).when(
			_dlAppLocalService
		).getFileEntry(
			Mockito.anyLong()
		);

		_blogsExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test(expected = PortalException.class)
	public void testValidateContentFailsWhenOverridenProcessorFails()
		throws Exception {

		String content = StringUtil.randomString();

		Mockito.doThrow(
			PortalException.class
		).when(
			_exportImportContentProcessor
		).validateContentReferences(
			Mockito.anyLong(), Mockito.anyString()
		);

		_blogsExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	@Test
	public void testValidateContentSucceedsWhenAllReferencesAreValid()
		throws Exception {

		String content = "<img data-fileentryid=\"1\" src=\"PATH_1\" />";

		Mockito.doReturn(
			_fileEntry1
		).when(
			_dlAppLocalService
		).getFileEntry(
			Mockito.anyLong()
		);

		_blogsExportImportContentProcessor.validateContentReferences(
			1, content);
	}

	private void _defineFileEntryToExport(long fileEntryId, FileEntry fileEntry)
		throws PortalException {

		Mockito.doReturn(
			fileEntry
		).when(
			_dlAppLocalService
		).getFileEntry(
			fileEntryId
		);

		Mockito.doReturn(
			"PLACEHOLDER_" + fileEntryId
		).when(
			_exportImportPlaceholderFactory
		).createDynamicPlaceholder(
			fileEntry
		);
	}

	private void _defineFileEntryToImport(long fileEntryId, FileEntry fileEntry)
		throws Exception {

		Mockito.doReturn(
			true
		).when(
			_embeddedReferenceSet
		).containsReference(
			"PATH_" + fileEntryId
		);

		Mockito.doReturn(
			fileEntryId
		).when(
			_embeddedReferenceSet
		).importReference(
			"PATH_" + fileEntryId
		);

		Mockito.doReturn(
			"PLACEHOLDER_" + fileEntryId
		).when(
			_adaptiveMediaTagFactory
		).createDynamicTag(
			fileEntry
		);

		Mockito.doReturn(
			fileEntry
		).when(
			_dlAppLocalService
		).getFileEntry(
			fileEntryId
		);
	}

	private void _makeOverridenProcessorReturn(String content)
		throws Exception {

		Mockito.doReturn(
			content
		).when(
			_exportImportContentProcessor
		).replaceExportContentReferences(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class), Mockito.anyString(),
			Mockito.anyBoolean(), Mockito.anyBoolean()
		);

		Mockito.doReturn(
			content
		).when(
			_exportImportContentProcessor
		).replaceImportContentReferences(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class), Mockito.anyString()
		);

		Mockito.doReturn(
			_embeddedReferenceSet
		).when(
			_embeddedReferenceSetFactory
		).create(
			Mockito.any(PortletDataContext.class),
			Mockito.any(StagedModel.class)
		);
	}

	private final AdaptiveMediaTagFactory _adaptiveMediaTagFactory =
		Mockito.mock(AdaptiveMediaTagFactory.class);
	private final BlogsEntry _blogsEntry = Mockito.mock(BlogsEntry.class);
	private final AdaptiveMediaBlogsEntryExportImportContentProcessor
		_blogsExportImportContentProcessor =
			new AdaptiveMediaBlogsEntryExportImportContentProcessor();
	private final DLAppLocalService _dlAppLocalService = Mockito.mock(
		DLAppLocalService.class);
	private final EmbeddedReferenceSet _embeddedReferenceSet = Mockito.mock(
		EmbeddedReferenceSet.class);
	private EmbeddedReferenceSetFactory _embeddedReferenceSetFactory =
		Mockito.mock(EmbeddedReferenceSetFactory.class);
	private final ExportImportContentProcessor<String>
		_exportImportContentProcessor = Mockito.mock(
			ExportImportContentProcessor.class);
	private final ExportImportPlaceholderFactory
		_exportImportPlaceholderFactory = Mockito.mock(
			ExportImportPlaceholderFactory.class);
	private final FileEntry _fileEntry1 = Mockito.mock(FileEntry.class);
	private final FileEntry _fileEntry2 = Mockito.mock(FileEntry.class);
	private final PortletDataContext _portletDataContext = Mockito.mock(
		PortletDataContext.class);

}