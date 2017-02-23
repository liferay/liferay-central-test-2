/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.content.transformer;

import com.liferay.adaptive.media.AdaptiveMediaException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author Alejandro Tardín
 */
public class ContentTransformerTest {

	@Before
	public void setUp() throws BundleException {
		FrameworkFactory factory = ServiceLoader.load(
			FrameworkFactory.class).iterator().next();

		Map<String, String> configuration = new HashMap<>();

		Path cacheFolder = Paths.get(
			System.getProperty("java.io.tmpdir"), "osgi-cache");

		configuration.put("org.osgi.framework.storage", cacheFolder.toString());

		_framework = factory.newFramework(configuration);

		_framework.start();

		_contentTransformerHandler.activate(_framework.getBundleContext());
	}

	@After
	public final void tearDown() throws Exception {
		_contentTransformerHandler.deactivate();
		_framework.stop();
		_framework.waitForStop(0);
	}

	@Test
	public void testIgnoresTheContentTransformersForDifferentContentTypes()
		throws Exception {

		ContentTransformerContentType<String> contentTypeA =
			new TestContentTransformerContentType<>();
		TestContentTransformerContentType<String> contentTypeB =
			new TestContentTransformerContentType<>();

		String transformedContentA = "transformedContentA";
		String transformedContentB = "transformedContentB";

		_registerContentTransformer(
			contentTypeA, _ORIGINAL_CONTENT, transformedContentA);

		_registerContentTransformer(
			contentTypeB, _ORIGINAL_CONTENT, transformedContentB);

		Assert.assertEquals(
			transformedContentA,
			_contentTransformerHandler.transform(
				contentTypeA, _ORIGINAL_CONTENT));

		Assert.assertEquals(
			transformedContentB,
			_contentTransformerHandler.transform(
				contentTypeB, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentTransformedByAChainOfContentTransformers()
		throws Exception {

		String intermediateTransformedContent =
			"intermediateTransformedContent";
		String finalTransformedContent = "finalTransformedContent";

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, intermediateTransformedContent);

		_registerContentTransformer(
			_contentType, intermediateTransformedContent,
			finalTransformedContent);

		Assert.assertEquals(
			finalTransformedContent,
			_contentTransformerHandler.transform(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentTransformedByATransformerForAContentType()
		throws Exception {

		String transformedContent = "transformedContent";

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandler.transform(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfATransformerThrowsAnException()
		throws Exception {

		_registerFailingContentTransformer(_contentType, _ORIGINAL_CONTENT);

		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandler.transform(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfThereAreNoContentTransformers() {
		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandler.transform(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testRunsTheOtherTransformersEvenIfOneOfThemFails()
		throws Exception {

		String transformedContent = "transformedContent";

		_registerFailingContentTransformer(_contentType, _ORIGINAL_CONTENT);

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, transformedContent);

		Assert.assertEquals(
			transformedContent,
			_contentTransformerHandler.transform(
				_contentType, _ORIGINAL_CONTENT));
	}

	private ContentTransformer<String> _registerContentTransformer(
			ContentTransformerContentType<String> contentType,
			String originalContent, String transformedContent)
		throws Exception {

		ContentTransformer<String> contentTransformer = Mockito.mock(
			ContentTransformer.class);

		Mockito.when(
			contentTransformer.getContentType()
		).thenReturn(
			contentType
		);

		Mockito.when(
			contentTransformer.transform(originalContent)
		).thenReturn(
			transformedContent
		);

		_framework.getBundleContext().registerService(
			ContentTransformer.class, contentTransformer, null);

		return contentTransformer;
	}

	private void _registerFailingContentTransformer(
			ContentTransformerContentType<String> contentType,
			String originalContent)
		throws Exception {

		ContentTransformer<String> failingContentTransformer =
			_registerContentTransformer(contentType, originalContent, "");

		Mockito.when(
			failingContentTransformer.transform(originalContent)
		).thenThrow(
			new AdaptiveMediaException(
				"Do not worry :), this is an expected exception")
		);
	}

	private static final String _ORIGINAL_CONTENT = "originalContent";

	private final ContentTransformerHandler _contentTransformerHandler =
		new ContentTransformerHandler();
	private final ContentTransformerContentType<String> _contentType =
		new TestContentTransformerContentType<>();
	private Framework _framework;

	private static class TestContentTransformerContentType<T>
		implements ContentTransformerContentType<T> {

		@Override
		public String getKey() {
			return "test";
		}

	}

}