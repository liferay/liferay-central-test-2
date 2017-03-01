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

package com.liferay.adaptive.media.image.rest.client.internal.test;

import static com.liferay.adaptive.media.image.rest.client.internal.util.ImageAdaptiveMediaTestUtil.TEST_AUTH;

import com.google.gson.JsonArray;

import com.liferay.adaptive.media.image.rest.client.internal.util.ImageAdaptiveMediaTestUtil;
import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	@Test
	public void testGettingAdaptiveByConfigReturnsData() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigEndpointResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingAdaptiveByInvalidConfigReturns404IfParam() {
		String id = ImageAdaptiveMediaTestUtil.getRandomUuid();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigEndpointResponse(id, fileEntryId, false);

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testGettingAdaptiveByInvalidConfigReturnsOriginal() {
		String id = ImageAdaptiveMediaTestUtil.getRandomUuid();

		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getConfigEndpointResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingAdaptiveDataWithAttributeReturnsData() {
		long fileEntryId = _getRandomAdaptiveFileEntryId();

		Response response = _getDataEndpointResponse(
			fileEntryId, true, _getRandomQueryParams());

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingDataWithoutAttributeReturns400() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(0);

		Response response = _getDataEndpointResponse(fileEntryId, true, null);

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testGettingNonAdaptiveByConfigReturns404IfParam() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _nonAdaptiveFileEntryIds.get(0);

		Response response = _getConfigEndpointResponse(id, fileEntryId, false);

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testGettingNonAdaptiveByConfigReturnsOriginal() {
		String id = _getRandomConfigurationId();

		long fileEntryId = _nonAdaptiveFileEntryIds.get(1);

		Response response = _getConfigEndpointResponse(id, fileEntryId, true);

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingNonAdaptiveDataWithAttributeReturns404IfParam() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(2);

		Response response = _getDataEndpointResponse(
			fileEntryId, false, _getRandomQueryParams());

		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testGettingNonAdaptiveDataWithAttributeReturnsOriginal() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(3);

		Response response = _getDataEndpointResponse(
			fileEntryId, true, _getRandomQueryParams());

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("image", response.getMediaType().getType());
	}

	@Test
	public void testGettingVariantsOfAdaptiveReturnsFullArray() {
		long fileEntryId = _getRandomAdaptiveFileEntryId();

		JsonArray jsonArray = _getVariantsInvocationBuilder(
			fileEntryId, _getRandomQueryParams(), null).get(JsonArray.class);

		Assert.assertEquals(_configurationIds.size(), jsonArray.size());
	}

	@Test
	public void testGettingVariantsOfNonAdaptiveReturnsEmptyArray() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(4);

		JsonArray jsonArray = _getVariantsInvocationBuilder(
			fileEntryId, _getRandomQueryParams(), null).get(JsonArray.class);

		Assert.assertEquals(0, jsonArray.size());
	}

	@Test
	public void testGettingVariantsWithoutAttributesReturns400() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(0);

		Response response = _getVariantsInvocationBuilder(
			fileEntryId, null, null).get();

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testGettingVariantsWithWrongAttributesReturns400() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(0);

		List<String> queryParams = new ArrayList<>();

		queryParams.add("wrong");

		Response response = _getVariantsInvocationBuilder(
			fileEntryId, queryParams, null).get();

		Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void testGettingVariantsWithWrongOrderReturns400() {
		long fileEntryId = _nonAdaptiveFileEntryIds.get(0);

		Response response = _getVariantsInvocationBuilder(
			fileEntryId, null, "wrong").get();

		Assert.assertEquals(400, response.getStatus());
	}

	private Invocation.Builder _getAdaptiveMediaRequest(
		Function<WebTarget, WebTarget> webTargetResolver, long fileEntryId) {

		return ImageAdaptiveMediaTestUtil.getMediaRequest(webTarget -> {
			WebTarget resolvedWebTarget = webTarget.resolveTemplate(
				"fileEntryId", fileEntryId);

			return webTargetResolver.apply(resolvedWebTarget);
		}).header("Authorization", TEST_AUTH);
	}

	private Response _getConfigEndpointResponse(
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

	private Response _getDataEndpointResponse(
		long fileEntryId, boolean useOriginal, List<String> queryParams) {

		return _getAdaptiveMediaRequest(
			webTarget -> {
				WebTarget resolvedWebTarget = webTarget.path(
					_GET_VARIANT_BY_ATTRIBUTE);

				if (!useOriginal) {
					resolvedWebTarget = resolvedWebTarget.queryParam(
						"original", false);
				}

				if (queryParams != null) {
					for (String param : queryParams) {
						resolvedWebTarget = resolvedWebTarget.queryParam(
							"q", param);
					}
				}

				return resolvedWebTarget;
			},
			fileEntryId).get();
	}

	private long _getRandomAdaptiveFileEntryId() {
		return _adaptiveFileEntryIds.get(
			RandomUtil.nextInt(_adaptiveFileEntryIds.size()));
	}

	private String _getRandomAttribute(String attribute) {
		return String.format("%s:%d", attribute, RandomUtil.nextInt(1000));
	}

	private String _getRandomConfigurationId() {
		return _configurationIds.get(
			RandomUtil.nextInt(_configurationIds.size()));
	}

	private List<String> _getRandomQueryParams() {
		return _attributes.stream().map(this::_getRandomAttribute).collect(
			Collectors.toList());
	}

	private Invocation.Builder _getVariantsInvocationBuilder(
		long fileEntryId, List<String> queryParams, String order) {

		return _getAdaptiveMediaRequest(
			webTarget -> {
				WebTarget resolvedWebTarget = webTarget.path(_GET_VARIANTS);

				if (queryParams != null) {
					for (String param : queryParams) {
						resolvedWebTarget = resolvedWebTarget.queryParam(
							"q", param);
					}
				}

				if (order != null) {
					resolvedWebTarget = resolvedWebTarget.queryParam(
						"order", order);
				}

				return resolvedWebTarget;
			},
			fileEntryId);
	}

	private static final String _GET_VARIANT_BY_ATTRIBUTE = "/data";

	private static final String _GET_VARIANT_BY_CONFIG = "/config/{id}";

	private static final String _GET_VARIANTS = "/variants";

	private static final List<Long> _adaptiveFileEntryIds;
	private static final List<String> _attributes = new ArrayList<>();
	private static final List<String> _configurationIds = new ArrayList<>();
	private static final List<Long> _nonAdaptiveFileEntryIds;

	static {
		_configurationIds.add("demo-xsmall");
		_configurationIds.add("demo-small");
		_configurationIds.add("demo-medium");
		_configurationIds.add("demo-large");
		_configurationIds.add("demo-xlarge");

		_attributes.add("height");
		_attributes.add("width");

		long groupId = ImageAdaptiveMediaTestUtil.getGroupId();

		long nonAdaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			groupId, "Non Adaptive Media");
		long adaptiveMediaFolderId = ImageAdaptiveMediaTestUtil.getFolderId(
			groupId, "Adaptive Media");

		_nonAdaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			5, groupId, "image-without-%d.jpeg", nonAdaptiveMediaFolderId);

		_adaptiveFileEntryIds = ImageAdaptiveMediaTestUtil.getFileEntryIds(
			2, groupId, "image-with-%d.jpeg", adaptiveMediaFolderId);
	}

}