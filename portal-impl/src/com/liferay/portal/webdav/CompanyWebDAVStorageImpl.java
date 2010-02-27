/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="CompanyWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CompanyWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		String path = getRootPath() + webDavRequest.getPath();

		return new BaseResourceImpl(
			path, StringPool.BLANK, WebDAVUtil.getWebId(path));
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("usersGroups", new Long(webDavRequest.getUserId()));

			List<Resource> resources = new ArrayList<Resource>();

			List<Group> groups = GroupLocalServiceUtil.search(
				webDavRequest.getCompanyId(), null, null, groupParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (Group group : groups) {
				Resource resource = getResource(group);

				resources.add(resource);
			}

			Group group = GroupLocalServiceUtil.getUserGroup(
				webDavRequest.getCompanyId(), webDavRequest.getUserId());

			Resource resource = getResource(group);

			resources.add(resource);

			return resources;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected Resource getResource(Group group) throws WebDAVException {
		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				group.getCompanyId());

			String webId = company.getWebId();

			String parentPath = getRootPath() + StringPool.SLASH + webId;

			String name = group.getFriendlyURL();

			name = name.substring(1, name.length());

			return new BaseResourceImpl(parentPath, name, name);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

}