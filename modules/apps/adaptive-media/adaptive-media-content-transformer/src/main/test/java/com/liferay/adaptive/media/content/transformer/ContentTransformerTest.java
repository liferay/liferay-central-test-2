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

		String processedContentA = "processedContentA";
		String processedContentB = "processedContentB";

		_registerContentTransformer(
			contentTypeA, _ORIGINAL_CONTENT, processedContentA);

		_registerContentTransformer(
			contentTypeB, _ORIGINAL_CONTENT, processedContentB);

		Assert.assertEquals(
			processedContentA,
			_contentTransformerHandler.process(
				contentTypeA, _ORIGINAL_CONTENT));

		Assert.assertEquals(
			processedContentB,
			_contentTransformerHandler.process(
				contentTypeB, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentProcessedByAChainOfContentTransformers()
		throws Exception {

		String intermediateProcessedContent = "intermediateProcessedContent";
		String finalProcessedContent = "finalProcessedContent";

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, intermediateProcessedContent);

		_registerContentTransformer(
			_contentType, intermediateProcessedContent, finalProcessedContent);

		Assert.assertEquals(
			finalProcessedContent,
			_contentTransformerHandler.process(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentProcessedByATransformerForAContentType()
		throws Exception {

		String processedContent = "processedContent";

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, processedContent);

		Assert.assertEquals(
			processedContent,
			_contentTransformerHandler.process(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfATransformerThrowsAnException()
		throws Exception {

		_registerFailingContentTransformer(_contentType, _ORIGINAL_CONTENT);

		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandler.process(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfThereAreNoContentTransformers() {
		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentTransformerHandler.process(
				_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testRunsTheOtherTransformersEvenIfOneOfThemFails()
		throws Exception {

		String processedContent = "processedContent";

		_registerFailingContentTransformer(_contentType, _ORIGINAL_CONTENT);

		_registerContentTransformer(
			_contentType, _ORIGINAL_CONTENT, processedContent);

		Assert.assertEquals(
			processedContent,
			_contentTransformerHandler.process(
				_contentType, _ORIGINAL_CONTENT));
	}

	private ContentTransformer<String> _registerContentTransformer(
			ContentTransformerContentType<String> contentType,
			String originalContent, String processedContent)
		throws Exception {

		ContentTransformer<String> contentTransformer = Mockito.mock(
			ContentTransformer.class);

		Mockito.when(
			contentTransformer.getContentType()
		).thenReturn(
			contentType
		);

		Mockito.when(
			contentTransformer.process(originalContent)
		).thenReturn(
			processedContent
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
			failingContentTransformer.process(originalContent)
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
	}

}