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

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URI;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class HtmlContentTransformerImplTest {

	@Before
	public void setUp() throws AdaptiveMediaException, PortalException {
		_htmlContentTransformer.setDlAppLocalService(_dlAppLocalService);
		_htmlContentTransformer.setAdaptiveMediaImageFinder(_finder);
	}

	@Test
	public void testAlsoReplacesSeveralImagesInAMultilineString()
		throws Exception {

		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenAnswer(
			invocation -> Stream.of(adaptiveMedia)
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<div><div><picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture></div></div><br/>");

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
	public void testAppliesSeveralMediaQueries() throws Exception {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia1 =
			_createAdaptiveMedia(1986, "http://small.very.adaptive.com");

		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia2 =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenReturn(
			Stream.of(adaptiveMedia1, adaptiveMedia2)
		);

		StringBundler expectedSB = new StringBundler(8);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1986px)\" ");
		expectedSB.append("srcset=\"http://small.very.adaptive.com\"/>");
		expectedSB.append("<source media=\"(max-width:1989px) and ");
		expectedSB.append("(min-width:1986px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString(),
			_htmlContentTransformer.transform(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReplacesTheAdaptableImagesWithTheAdaptivePictureTag()
		throws Exception {

		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenReturn(
			Stream.of(adaptiveMedia)
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<div><div><picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture></div></div><br/>");

		StringBundler originalSB = new StringBundler(4);

		originalSB.append("<div><div>");
		originalSB.append("<img data-fileEntryId=\"1989\" ");
		originalSB.append("src=\"adaptable\"/>");
		originalSB.append("</div></div><br/>");

		Assert.assertEquals(
			expectedSB.toString(),
			_htmlContentTransformer.transform(originalSB.toString()));
	}

	@Test
	public void testReplacesTwoConsecutiveImageTags() throws Exception {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenAnswer(
			invocation -> Stream.of(adaptiveMedia)
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		Assert.assertEquals(
			expectedSB.toString() + expectedSB.toString(),
			_htmlContentTransformer.transform(
				"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>" +
					"<img data-fileEntryId=\"1989\" src=\"adaptable\"/>"));
	}

	@Test
	public void testReturnsTheSameHTMLIfNoAdaptiveMediaImagesArePresent()
		throws Exception {

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenReturn(
			Stream.empty()
		);

		Assert.assertEquals(
			"<div><div><img data-fileEntryId=\"1989\" " +
				"src=\"adaptable\"/></div></div><br/>",
			_htmlContentTransformer.transform(
				"<div><div><img data-fileEntryId=\"1989\" " +
					"src=\"adaptable\"/></div></div><br/>"));
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
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenAnswer(
			invocation -> Stream.of(adaptiveMedia)
		);

		StringBundler expectedSB = new StringBundler(6);

		expectedSB.append("<picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/><img ");
		expectedSB.append(CharPool.NEW_LINE);
		expectedSB.append("src=\"adaptable\"/>");
		expectedSB.append("</picture>");

		StringBundler originalSB = new StringBundler(3);

		originalSB.append("<img data-fileEntryId=\"1989\" ");
		originalSB.append(CharPool.NEW_LINE);
		originalSB.append("src=\"adaptable\"/>");

		Assert.assertEquals(
			expectedSB.toString(),
			_htmlContentTransformer.transform(originalSB.toString()));
	}

	@Test
	public void testTheAttributeIsCaseInsensitive() throws Exception {
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
			_createAdaptiveMedia(1989, "http://very.adaptive.com");

		Mockito.when(
			_finder.getAdaptiveMedia(Mockito.any())
		).thenReturn(
			Stream.of(adaptiveMedia)
		);

		StringBundler expectedSB = new StringBundler(5);

		expectedSB.append("<div><div><picture>");
		expectedSB.append("<source media=\"(max-width:1989px)\" ");
		expectedSB.append("srcset=\"http://very.adaptive.com\"/>");
		expectedSB.append("<img src=\"adaptable\"/>");
		expectedSB.append("</picture></div></div><br/>");

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

	private AdaptiveMedia<AdaptiveMediaImageProcessor> _createAdaptiveMedia(
		int imageWidth, String url) {

		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia = Mockito.mock(
			AdaptiveMedia.class);

		Mockito.when(
			adaptiveMedia.getAttributeValue(
				AdaptiveMediaImageAttribute.IMAGE_WIDTH)
		).thenReturn(
			Optional.of(imageWidth)
		);

		Mockito.when(
			adaptiveMedia.getURI()
		).thenReturn(
			URI.create(url)
		);

		return adaptiveMedia;
	}

	private String _duplicateWithNewLine(String text) {
		return text + StringPool.NEW_LINE + text;
	}

	@Mock
	private DLAppLocalService _dlAppLocalService;

	@Mock
	private AdaptiveMediaImageFinder _finder;

	private final HtmlContentTransformerImpl _htmlContentTransformer =
		new HtmlContentTransformerImpl();

}