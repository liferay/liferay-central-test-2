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

package com.liferay.adaptive.media.processor.content;

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
public class ContentProcessorTest {

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

		_contentProcessor.activate(_framework.getBundleContext());
	}

	@After
	public final void tearDown() throws Exception {
		_contentProcessor.deactivate();
		_framework.stop();
		_framework.waitForStop(0);
	}

	@Test
	public void testIgnoresTheContentProcessorsForDifferentContentTypes()
		throws Exception {

		ContentType<String> contentTypeA = new TestContentType<>();
		ContentType<String> contentTypeB = new TestContentType<>();

		String processedContentA = "processedContentA";
		String processedContentB = "processedContentB";

		_registerConcreteContentProcessor(
			contentTypeA, _ORIGINAL_CONTENT, processedContentA);

		_registerConcreteContentProcessor(
			contentTypeB, _ORIGINAL_CONTENT, processedContentB);

		Assert.assertEquals(
			processedContentA,
			_contentProcessor.process(contentTypeA, _ORIGINAL_CONTENT));

		Assert.assertEquals(
			processedContentB,
			_contentProcessor.process(contentTypeB, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentProcessedByAChainOfContentProcessors()
		throws Exception {

		String intermediateProcessedContent = "intermediateProcessedContent";
		String finalProcessedContent = "finalProcessedContent";

		_registerConcreteContentProcessor(
			_contentType, _ORIGINAL_CONTENT, intermediateProcessedContent);

		_registerConcreteContentProcessor(
			_contentType, intermediateProcessedContent, finalProcessedContent);

		Assert.assertEquals(
			finalProcessedContent,
			_contentProcessor.process(_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheContentProcessedByAProcessorForAContentType()
		throws Exception {

		String processedContent = "processedContent";

		_registerConcreteContentProcessor(
			_contentType, _ORIGINAL_CONTENT, processedContent);

		Assert.assertEquals(
			processedContent,
			_contentProcessor.process(_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfAProcessorThrowsAnException()
		throws Exception {

		_registerFailingConcreteContentProcessor(
			_contentType, _ORIGINAL_CONTENT);

		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentProcessor.process(_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testReturnsTheSameContentIfThereAreNoContentProcessors() {
		Assert.assertSame(
			_ORIGINAL_CONTENT,
			_contentProcessor.process(_contentType, _ORIGINAL_CONTENT));
	}

	@Test
	public void testRunsTheOtherProcessorsEvenIfOneOfThemFails()
		throws Exception {

		String processedContent = "processedContent";

		_registerFailingConcreteContentProcessor(
			_contentType, _ORIGINAL_CONTENT);

		_registerConcreteContentProcessor(
			_contentType, _ORIGINAL_CONTENT, processedContent);

		Assert.assertEquals(
			processedContent,
			_contentProcessor.process(_contentType, _ORIGINAL_CONTENT));
	}

	private ConcreteContentProcessor<String> _registerConcreteContentProcessor(
			ContentType<String> contentType, String originalContent,
			String processedContent)
		throws Exception {

		ConcreteContentProcessor<String> concreteProcessor = Mockito.mock(
			ConcreteContentProcessor.class);

		Mockito.when(
			concreteProcessor.getContentType()
		).thenReturn(
			contentType
		);

		Mockito.when(
			concreteProcessor.process(originalContent)
		).thenReturn(
			processedContent
		);

		_framework.getBundleContext().registerService(
			ConcreteContentProcessor.class, concreteProcessor, null);

		return concreteProcessor;
	}

	private void _registerFailingConcreteContentProcessor(
			ContentType<String> contentType, String originalContent)
		throws Exception {

		ConcreteContentProcessor<String> failingContentProcessor =
			_registerConcreteContentProcessor(contentType, originalContent, "");

		Mockito.when(
			failingContentProcessor.process(originalContent)
		).thenThrow(
			new AdaptiveMediaException(
				"Do not worry :), this is an expected exception")
		);
	}

	private static final String _ORIGINAL_CONTENT = "originalContent";

	private final ContentProcessor _contentProcessor = new ContentProcessor();
	private final ContentType<String> _contentType = new TestContentType<>();
	private Framework _framework;

	private static class TestContentType<T> implements ContentType<T> {
	}

}