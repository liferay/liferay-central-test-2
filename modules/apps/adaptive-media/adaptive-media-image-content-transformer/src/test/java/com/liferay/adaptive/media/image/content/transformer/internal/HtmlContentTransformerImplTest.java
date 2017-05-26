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

package com.liferay.adaptive.media.image.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AdaptiveMediaImageHTMLTagFactory;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 * @author Sergio González
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlContentTransformerImplTest {

	@Before
	public void setUp() throws PortalException {
		Mockito.when(
			_dlAppLocalService.getFileEntry(1989L)
		).thenReturn(
			_fileEntry
		);

		_htmlContentTransformer.setDLAppLocalService(_dlAppLocalService);
		_htmlContentTransformer.setAdaptiveMediaImageHTMLTagFactory(
			_adaptiveMediaImageHTMLTagFactory);
	}

	@Test
	public void testAlsoReplacesSeveralImagesInAMultilineString()
		throws Exception {

		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<div><div>");
		expectedSB.append("<whatever></whatever>");
		expectedSB.append("</div></div><br/>");

		StringBundler originalSB = new StringBundler(4);

		originalSB.append("<div><div>");
		originalSB.append("<img data-fileEntryId=\"1989\" ");
		originalSB.append("src=\"adaptable\"/>");
		originalSB.append("</div></div><br/>");

		Assert.assertEquals(
			_duplicateWithNewLine(expectedSB.toString()),
			_htmlContentTransformer.transform(
				_duplicateWithNewLine(originalSB.toString())));
	}

	@Test
	public void testContentTypeIsHTML() throws Exception {
		Assert.assertEquals(
			ContentTransformerContentTypes.HTML,
			_htmlContentTransformer.getContentType());
	}

	@Test
	public void testReplacesAnAdaptableImgAfterANonAdaptableOne()
		throws Exception {

		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<img src=\"not-adaptable\"/><whatever></whatever>",
			_htmlContentTransformer.transform(
				"<img src=\"not-adaptable\"/>" +
					"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReplacesTheAdaptableImagesWithTheAdaptiveTag()
		throws Exception {

		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<whatever></whatever>",
			_htmlContentTransformer.transform(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReplacesTwoConsecutiveImageTags() throws Exception {
		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		Assert.assertEquals(
			"<whatever></whatever><whatever></whatever>",
			_htmlContentTransformer.transform(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>" +
					"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReturnsNullForNullContent() throws Exception {
		Assert.assertNull(_htmlContentTransformer.transform(null));
	}

	@Test
	public void testReturnsTheSameHTMLIfNoImagesArePresent() throws Exception {
		Assert.assertEquals(
			"<div><div>some <a>stuff</a></div></div>",
			_htmlContentTransformer.transform(
				"<div><div>some <a>stuff</a></div></div>"));
	}

	@Test
	public void testReturnsTheSameHTMLIfThereAreNoAdaptableImagesPresent()
		throws Exception {

		Assert.assertEquals(
			"<div><div><img src=\"no.adaptable\"/></div></div>",
			_htmlContentTransformer.transform(
				"<div><div><img src=\"no.adaptable\"/></div></div>"));
	}

	@Test
	public void testSupportsImageTagsWithNewLineCharacters() throws Exception {
		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileEntryId=\"1989\" \nsrc=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		StringBundler originalSB = new StringBundler(3);

		originalSB.append("<img data-fileEntryId=\"1989\" ");
		originalSB.append(CharPool.NEW_LINE);
		originalSB.append("src=\"adaptable\"/>");

		Assert.assertEquals(
			"<whatever></whatever>",
			_htmlContentTransformer.transform(originalSB.toString()));
	}

	@Test
	public void testTheAttributeIsCaseInsensitive() throws Exception {
		Mockito.when(
			_adaptiveMediaImageHTMLTagFactory.create(
				"<img data-fileentryid=\"1989\" src=\"adaptable\"/>",
				_fileEntry)
		).thenReturn(
			"<whatever></whatever>"
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<div><div><whatever></whatever></div></div><br/>");

		StringBundler originalSB = new StringBundler(4);

		originalSB.append("<div><div>");
		originalSB.append("<img data-fileEntryId=\"1989\" ");
		originalSB.append("src=\"adaptable\"/>");
		originalSB.append("</div></div><br/>");

		Assert.assertEquals(
			expectedSB.toString(),
			_htmlContentTransformer.transform(
				StringUtil.toLowerCase(originalSB.toString())));
	}

	private String _duplicateWithNewLine(String text) {
		return text + StringPool.NEW_LINE + text;
	}

	@Mock
	private AdaptiveMediaImageHTMLTagFactory _adaptiveMediaImageHTMLTagFactory;

	@Mock
	private DLAppLocalService _dlAppLocalService;

	@Mock
	private FileEntry _fileEntry;

	private final HtmlContentTransformerImpl _htmlContentTransformer =
		new HtmlContentTransformerImpl();

}