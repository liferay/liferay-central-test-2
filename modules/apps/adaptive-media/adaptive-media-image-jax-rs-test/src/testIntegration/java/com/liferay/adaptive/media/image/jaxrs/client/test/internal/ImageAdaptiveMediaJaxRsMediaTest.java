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

import static com.liferay.adaptive.media.image.jaxrs.client.test.internal.util.ImageAdaptiveMediaTestUtil.TEST_AUTH;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.util.ImageAdaptiveMediaTestUtil;
import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Hernández
 */
@BndFile("src/testIntegration/resources/bnd.bnd")
@RunAsClient
@RunWith(Arquillian.class)
public class ImageAdaptiveMediaJaxRsMediaTest {

	private static final String _GET_VARIANT_BY_CONFIG = "/config/{id}";

	@Test
	public void testGettingNonAdaptiveByConfigReturnsOriginal() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _getRandomNonAdaptiveFileEntryId();

		Response response = _getConfigResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingNonAdaptiveByConfigReturns404IfParam() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _getRandomNonAdaptiveFileEntryId();

		Response response = _getConfigResponse(id, fileEntryId, false);

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testGettingAdaptiveByConfigReturnsData() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingAdaptiveByInvalidConfigReturnsOriginal() {
		String id = ImageAdaptiveMediaTestUtil.getRandomUuid();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingAdaptiveByInvalidConfigReturns404IfParam() {
		String id = ImageAdaptiveMediaTestUtil.getRandomUuid();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigResponse(id, fileEntryId, false);

		Assert.assertEquals(404, response.getStatus());
	}

	private Response _getConfigResponse(
		String id, long fileEntryId, boolean useOriginal) {

		return _getAdaptiveMediaRequest(
			webTarget -> {
				WebTarget resolvedWebTarget = webTarget.path(
					_GET_VARIANT_BY_CONFIG).resolveTemplate("id", id);

				if (!useOriginal) {
					return resolvedWebTarget.queryParam("original", false);
				}

				return resolvedWebTarget;
			},
			fileEntryId).get();
	}

	private long _getRandomAdaptiveFileEntryId() {
		return _adaptiveFileEntryIds.get(
			RandomUtil.nextInt(_adaptiveFileEntryIds.size()));
	}

	private Invocation.Builder _getAdaptiveMediaRequest(
		Function<WebTarget, WebTarget> webTargetResolver, long fileEntryId) {

		return ImageAdaptiveMediaTestUtil.getMediaRequest(webTarget -> {
			WebTarget resolvedWebTarget = webTarget.resolveTemplate(
				"fileEntryId", fileEntryId);

			return webTargetResolver.apply(resolvedWebTarget);
		}).header("Authorization", TEST_AUTH);
	}

	}

	private long _getRandomNonAdaptiveFileEntryId() {
		return _nonAdaptiveFileEntryIds.get(
			RandomUtil.nextInt(_nonAdaptiveFileEntryIds.size()));
	}

	private String _getRandomConfigurationId() {
		return _configurationIds.get(
			RandomUtil.nextInt(_configurationIds.size()));
	}

	private static final List<String> _configurationIds = new ArrayList<>();

	static {
		_configurationIds.add("demo-xsmall");
		_configurationIds.add("demo-small");
		_configurationIds.add("demo-medium");
		_configurationIds.add("demo-large");
		_configurationIds.add("demo-xlarge");

		_attributes.add("width");
		_attributes.add("height");

		long groupId = ImageAdaptiveMediaTestUtil.getGroupId();

		long nonAdaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			groupId, "Non Adaptive Media");
		long adaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			groupId, "Adaptive Media");

		_nonAdaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			2, groupId, "image-without-%d.jpeg", nonAdaptiveMediaFolderId);

		_adaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			3, groupId, "image-with-%d.jpeg", adaptiveMediaFolderId);
	}

	private static List<Long> _adaptiveFileEntryIds;

	private static List<Long> _nonAdaptiveFileEntryIds;

}