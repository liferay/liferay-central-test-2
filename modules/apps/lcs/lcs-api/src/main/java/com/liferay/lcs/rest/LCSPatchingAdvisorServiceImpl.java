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

package com.liferay.lcs.rest;

import java.util.List;

/**
 * @author Riccardo Ferrari
 */
public class LCSPatchingAdvisorServiceImpl
	extends BaseLCSServiceImpl implements LCSPatchingAdvisorService {

	@Override
	public List<String> getInstallablePatchIds(
			String key, int patchingToolVersion, String[] installedPatchIds)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < installedPatchIds.length-1; i++) {
			sb.append(installedPatchIds[i]);
			sb.append(",");
		}

		sb.append(installedPatchIds[installedPatchIds.length-1]);

		return doGetToList(
			String.class, _URL_PATCHING_ADVISOR, "key", key,
			"patchingToolVersion", String.valueOf(patchingToolVersion),
			"installedPatchIds", sb.toString());
	}

	private static final String _URL_PATCHING_ADVISOR =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSPatchingAdvisor";

}