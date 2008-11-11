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

package com.liferay.portal.search;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchPermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Algorithm5SearchPermissionCheckerImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Allen Chiang
 * @author Bruno Farache
 *
 */
public class Algorithm5SearchPermissionCheckerImpl
	implements SearchPermissionChecker {

	public void addPermissionFields(long companyId, Document doc) {
		String className = doc.get(Field.ENTRY_CLASS_NAME);
		String classPK = doc.get(Field.ENTRY_CLASS_PK);
		String groupId = doc.get(Field.GROUP_ID);

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) ||
			Validator.isNull(className) || Validator.isNull(classPK) ||
			Validator.isNull(groupId)) {

			return;
		}

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
				classPK);

			Group group = GroupLocalServiceUtil.getGroup(Long.valueOf(groupId));

			List<Role> roles = ResourceActionsUtil.getRoles(
				group, className);

			List<Long> roleIds = new ArrayList<Long>();

			for (Role role : roles) {
				List<Permission> permissions =
					PermissionLocalServiceUtil.getRolePermissions(
						role.getRoleId(), resource.getResourceId());

				List<String> actions = ResourceActionsUtil.getActions(
					permissions);

				for (String action : actions) {
					if (action.equals(ActionKeys.VIEW)) {
						roleIds.add(role.getRoleId());
					}
				}
			}

			doc.addKeyword(Field.ROLE_ID, ArrayUtil.toArray(roleIds));
		}
		catch (NoSuchResourceException nsre) {
			return;
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public Query getPermissionQuery(long userId, Query query) {
		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5)) {
			return query;
		}

		BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();
		BooleanQuery permissionQuery = BooleanQueryFactoryUtil.create();

		try {
			List<Role> roles = RoleLocalServiceUtil.getUserRoles(userId);

			for (Role role : roles) {
				if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
					return query;
				}

				permissionQuery.addTerm(Field.ROLE_ID, role.getRoleId());
			}

			fullQuery.add(query, BooleanClauseOccur.MUST);
			fullQuery.add(permissionQuery, BooleanClauseOccur.MUST);
		}
		catch (Exception e) {
			_log.error(e, e);

			return query;
		}

		return fullQuery;
	}

	public void updatePermissionFields(long resourceId) {
		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5)) {
			return;
		}

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				resourceId);

			Indexer indexer = IndexerRegistryUtil.getIndexer(
				resource.getName());

			if (indexer != null) {
				indexer.reIndex(
					resource.getName(), Long.valueOf(resource.getPrimKey()));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactory.getLog(
		Algorithm5SearchPermissionCheckerImpl.class);

}