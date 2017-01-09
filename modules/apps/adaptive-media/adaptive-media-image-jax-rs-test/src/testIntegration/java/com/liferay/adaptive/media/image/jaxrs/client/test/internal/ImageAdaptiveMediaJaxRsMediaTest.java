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

package com.liferay.adaptive.media.image.jaxrs.client.test.internal;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.util.ImageAdaptiveMediaTestUtil;
import com.liferay.arquillian.deploymentscenario.annotations.BndFile;

import java.net.URL;

import java.util.List;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Hernández
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunAsClient
@RunWith(Arquillian.class)
public class ImageAdaptiveMediaJaxRsMediaTest {

	@Before
	public void setUp() {
		long groupId = ImageAdaptiveMediaTestUtil.getGroupId(_context);

		long nonAdaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			_context, groupId, "Non Adaptive Media");
		long adaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			_context, groupId, "Adaptive Media");

		_nonAdaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			_context, 2, groupId, "image-without-%d.jpeg",
			nonAdaptiveMediaFolderId);

		_adaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			_context, 3, groupId, "image-with-%d.jpeg", adaptiveMediaFolderId);
	}

	private List<Long> _adaptiveFileEntryIds;

	@ArquillianResource
	private URL _context;

	private List<Long> _nonAdaptiveFileEntryIds;

}