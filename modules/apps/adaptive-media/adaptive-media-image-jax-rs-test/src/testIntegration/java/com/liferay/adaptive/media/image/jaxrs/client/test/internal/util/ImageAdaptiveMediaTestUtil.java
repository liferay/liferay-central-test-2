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

package com.liferay.adaptive.media.image.jaxrs.client.test.internal.util;

import aQute.lib.base64.Base64;

import com.google.gson.JsonObject;

import com.liferay.adaptive.media.image.jaxrs.client.test.internal.provider.GsonProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * @author Alejandro Hernández
 */
public class ImageAdaptiveMediaTestUtil {

	public static final Function<WebTarget, WebTarget>
		IDENTITY_WEB_TARGET_RESOLVER = webTarget -> webTarget;

	public static final String TEST_AUTH =
		"Basic " + Base64.encodeBase64("test@liferay.com:test".getBytes());

	public static Invocation.Builder getBaseRequest(
		String basePath, Function<WebTarget, WebTarget> webTargetResolver) {

		Client client = ClientBuilder.newClient();

		client.register(GsonProvider.class);

		WebTarget webTarget = client.target(_SERVER_CONTEXT);

		webTarget = webTarget.path(basePath);

		webTarget = webTargetResolver.apply(webTarget);

		return webTarget.request(MediaType.APPLICATION_JSON_TYPE);
	}

	public static Invocation.Builder getConfigurationRequest(
		Function<WebTarget, WebTarget> webTargetResolver) {

		return getBaseRequest(_CONFIGURATION_BASE_PATH, webTargetResolver);
	}

	public static List<Long> getFileEntryIds(
		int number, long groupId, String fileName, long folderId) {

		ArrayList<Long> fileEntryIds = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			long fileEntryId = _getFileEntryId(
				groupId, folderId, String.format(fileName, i));

			fileEntryIds.add(fileEntryId);
		}

		return fileEntryIds;
	}

	public static long getFolderId(long groupId, String name) {
		Map<String, Object> queryParams = new HashMap<String, Object>() {
			{
				put("repositoryId", groupId);
				put("parentFolderId", 0);
				put("name", name);
			}
		};

		JsonObject jsonObject = _getJSONWSRequest(
			_GET_FOLDER_PATH, queryParams);

		return jsonObject.get("folderId").getAsLong();
	}

	public static long getGroupId() {
		Map<String, Object> queryParams = new HashMap<String, Object>() {
			{
				put("companyId", _getCompanyId());
				put("groupKey", "Guest");
			}
		};

		JsonObject jsonObject = _getJSONWSRequest(_GET_GROUP_PATH, queryParams);

		return jsonObject.get("groupId").getAsLong();
	}

	public static Invocation.Builder getMediaRequest(
		Function<WebTarget, WebTarget> webTargetResolver) {

		return getBaseRequest(_MEDIA_BASE_PATH, webTargetResolver);
	}

	public static String getRandomUuid() {
		return UUID.randomUUID().toString();
	}

	private static long _getCompanyId() {
		Map<String, Object> queryParams = new HashMap<String, Object>() {
			{
				put("virtualHost", "localhost");
			}
		};

		JsonObject jsonObject = _getJSONWSRequest(
			_GET_COMPANY_PATH, queryParams);

		return jsonObject.get("companyId").getAsLong();
	}

	private static long _getFileEntryId(
		long groupId, long folderId, String name) {

		Map<String, Object> queryParams = new HashMap<String, Object>() {
			{
				put("groupId", groupId);
				put("folderId", folderId);
				put("title", name);
			}
		};

		JsonObject jsonObject = _getJSONWSRequest(
			_GET_FILE_ENTRY_PATH, queryParams);

		return jsonObject.get("fileEntryId").getAsLong();
	}

	private static JsonObject _getJSONWSRequest(
		String path, Map<String, Object> queryParams) {

		return getBaseRequest(_JSONWS_BASE_PATH, webTarget -> {
			WebTarget finalWebTarget = webTarget.path(path);

			if (queryParams != null) {
				for (String param : queryParams.keySet()) {
					finalWebTarget = finalWebTarget.queryParam(
						param, queryParams.get(param));
				}
			}

			return finalWebTarget;
		}).header("Authorization", TEST_AUTH).get(JsonObject.class);
	}

	private static final String _CONFIGURATION_BASE_PATH =
		"/o/adaptive-media/images/configuration";

	private static final String _GET_COMPANY_PATH =
		"/company/get-company-by-virtual-host";

	private static final String _GET_FILE_ENTRY_PATH = "/dlapp/get-file-entry";

	private static final String _GET_FOLDER_PATH = "/dlapp/get-folder";

	private static final String _GET_GROUP_PATH = "/group/get-group";

	private static final String _JSONWS_BASE_PATH = "/api/jsonws";

	private static final String _MEDIA_BASE_PATH =
		"o/adaptive-media/images/content/file/{fileEntryId}/version/last";

	private static final String _SERVER_CONTEXT = "http://localhost:8080";

}