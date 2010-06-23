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
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
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

	public Resource getResource(WebDAVRequest webDavRequest) {
		String path = getRootPath() + webDavRequest.getPath();

		return new BaseResourceImpl(path, StringPool.BLANK, StringPool.BLANK);
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			long companyId = webDavRequest.getCompanyId();
			long userId = webDavRequest.getUserId();

			return getResources(companyId, userId);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	protected List<Resource> getResources(long companyId, long userId)
		throws Exception {

		List<Group> groups = getGroups(companyId, userId);

		List<Resource> resources = new ArrayList<Resource>(groups.size());

		for (Group group : groups) {
			Company company = CompanyLocalServiceUtil.getCompanyById(
				group.getCompanyId());

			String webId = company.getWebId();

			String parentPath = getRootPath() + StringPool.SLASH + webId;

			String name = group.getFriendlyURL();

			name = name.substring(1, name.length());

			resources.add(new BaseResourceImpl(parentPath, name, name));
		}

		return resources;
	}

	protected static List<Group> getGroups(long companyId, long userId)
		throws Exception {

		List<Group> groups = new ArrayList<Group>();

		// Communities

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		params.put("usersGroups", userId);

		groups.addAll(GroupLocalServiceUtil.search(
			companyId, null, null, params, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS));

		// Organizations

		groups.addAll(GroupLocalServiceUtil.getUserOrganizationsGroups(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		// User

		groups.add(GroupLocalServiceUtil.getUserGroup(companyId, userId));

		return groups;
	}

}