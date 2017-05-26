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

package com.liferay.adaptive.media.journal.web.internal.transformer;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.xml.Document;

import java.util.HashMap;
import java.util.Map;

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
public class AdaptiveMediaJournalTransformerListenerTest {

	@Before
	public void setUp() {
		_adaptiveMediaJournalTransformerListener.setContentTransformerHandler(
			_contentTransformerHandler);
		_adaptiveMediaJournalTransformerListener.setJournalContent(
			_journalContent);
	}

	@Test
	public void testOnActivationClearsJournalCache() throws Exception {
		_adaptiveMediaJournalTransformerListener.activate();

		Mockito.verify(
			_journalContent, Mockito.times(1)
		).clearCache();
	}

	@Test
	public void testOnDeactivationClearsJournalCache() throws Exception {
		_adaptiveMediaJournalTransformerListener.deactivate();

		Mockito.verify(
			_journalContent, Mockito.times(1)
		).clearCache();
	}

	@Test
	public void testOnOutputTransformsTheOutput() throws Exception {
		String originalOutput = "some content";
		String transformedOutput = "some transformed content";

		Mockito.when(
			_contentTransformerHandler.transform(
				ContentTransformerContentTypes.HTML, originalOutput)
		).thenReturn(
			transformedOutput
		);

		String newOutput = _adaptiveMediaJournalTransformerListener.onOutput(
			originalOutput, _LANGUAGE_ID, _tokens);

		Assert.assertSame(transformedOutput, newOutput);
	}

	@Test
	public void testOnScriptDoesNotModifyTheScript() throws Exception {
		String originalScript = "some script content";

		String newScript = _adaptiveMediaJournalTransformerListener.onScript(
			originalScript, _document, _LANGUAGE_ID, _tokens);

		Assert.assertSame(originalScript, newScript);

		Mockito.verifyZeroInteractions(_document);
	}

	@Test
	public void testOnXmlDoesNotModifyTheXml() throws Exception {
		Document newDocument = _adaptiveMediaJournalTransformerListener.onXml(
			_document, _LANGUAGE_ID, _tokens);

		Assert.assertSame(_document, newDocument);

		Mockito.verifyZeroInteractions(_document);
	}

	private static final String _LANGUAGE_ID = "en";

	private final AdaptiveMediaJournalTransformerListener
		_adaptiveMediaJournalTransformerListener =
			new AdaptiveMediaJournalTransformerListener();

	@Mock
	private ContentTransformerHandler _contentTransformerHandler;

	@Mock
	private Document _document;

	@Mock
	private JournalContent _journalContent;

	private final Map<String, String> _tokens = new HashMap<>();

}