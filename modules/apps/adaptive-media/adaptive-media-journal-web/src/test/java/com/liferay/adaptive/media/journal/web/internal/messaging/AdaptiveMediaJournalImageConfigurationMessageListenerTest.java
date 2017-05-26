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

package com.liferay.adaptive.media.journal.web.internal.messaging;

import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.messaging.Message;

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
public class AdaptiveMediaJournalImageConfigurationMessageListenerTest {

	@Before
	public void setUp() {
		_adaptiveMediaJournalImageConfigurationMessageListener.
			setJournalContent(_journalContent);
	}

	@Test
	public void testClearsTheCacheOnAMessageToTheConfigurationDestination()
		throws Exception {

		_adaptiveMediaJournalImageConfigurationMessageListener.doReceive(
			new Message());

		Mockito.verify(
			_journalContent, Mockito.times(1)
		).clearCache();
	}

	private final AdaptiveMediaJournalImageConfigurationMessageListener
		_adaptiveMediaJournalImageConfigurationMessageListener =
			new AdaptiveMediaJournalImageConfigurationMessageListener();

	@Mock
	private JournalContent _journalContent;

}