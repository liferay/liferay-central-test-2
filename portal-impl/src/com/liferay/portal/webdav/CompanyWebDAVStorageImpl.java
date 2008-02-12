/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="CompanyWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CompanyWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public Resource getResource(WebDAVRequest webDavReq)
		throws WebDAVException {

		String path = getRootPath() + webDavReq.getPath();

		return new BaseResourceImpl(
			path, StringPool.BLANK, WebDAVUtil.getWebId(path));
	}

	public List getResources(WebDAVRequest webDavReq) throws WebDAVException {
		try {
			LinkedHashMap groupParams = new LinkedHashMap();

			groupParams.put("usersGroups", new Long(webDavReq.getUserId()));

			List communities = new ArrayList();

			Iterator itr = GroupLocalServiceUtil.search(
				webDavReq.getCompanyId(), null, null, groupParams,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS).iterator();

			while (itr.hasNext()) {
				Group group = (Group)itr.next();

				Resource resource = getResource(group);

				communities.add(resource);
			}

			Group group = GroupLocalServiceUtil.getUserGroup(
				webDavReq.getCompanyId(), webDavReq.getUserId());

			Resource resource = getResource(group);

			communities.add(resource);

			return communities;
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

			String name = group.getResolvedFriendlyURL();

			name = name.substring(1, name.length());

			return new BaseResourceImpl(parentPath, name, name);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

}