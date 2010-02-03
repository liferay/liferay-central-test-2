/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="CompanySharepointStorageImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 */
public class CompanySharepointStorageImpl extends BaseSharepointStorageImpl {

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		Tree foldersTree = new Tree();

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", new Long(sharepointRequest.getUserId()));

		List<Group> groups = GroupLocalServiceUtil.search(
			sharepointRequest.getCompanyId(), null, null, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			sharepointRequest.getCompanyId(), sharepointRequest.getUserId());

		groups.add(userGroup);

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				sharepointRequest.getUserId(), true);

		for (Organization organization : organizations) {
			groups.add(organization.getGroup());
		}

		for (Group group : groups) {
			String path = getGroupPath(group);

			foldersTree.addChild(getFolderTree(path));
		}

		foldersTree.addChild(getFolderTree(StringPool.BLANK));

		return foldersTree;
	}

	protected String getGroupPath(Group group) throws Exception {
		StringBundler sb = new StringBundler(5);

		String name = group.getName();

		long classPK = group.getClassPK();

		if (group.isUser()) {
			User user = UserServiceUtil.getUserById(classPK);

			name = user.getFullName();
		}
		else if (group.isOrganization()) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(classPK);

			name = organization.getName();
		}

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_BRACKET);
		sb.append(group.getGroupId());
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

}