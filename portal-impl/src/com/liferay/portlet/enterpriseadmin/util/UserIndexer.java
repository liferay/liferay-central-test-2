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

package com.liferay.portlet.enterpriseadmin.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="UserIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class UserIndexer extends BaseIndexer {

	public static final String PORTLET_ID = PortletKeys.ENTERPRISE_ADMIN_USERS;

	public static void deleteUser(long companyId, long userId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getUserUID(userId));
	}

	public static Document getUserDocument(
		long companyId, long userId, String screenName, String emailAddress,
		String firstName, String middleName, String lastName, String jobTitle,
		boolean active, long[] groupIds, long[] organizationIds,
		long[] roleIds, long[] userGroupIds, String[] assetTagNames,
		ExpandoBridge expandoBridge) {

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, String.valueOf(userId));

		document.addModifiedDate();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.USER_ID, userId);

		document.addKeyword("screenName", screenName);
		document.addKeyword("emailAddress", emailAddress);
		document.addKeyword("firstName", firstName, true);
		document.addKeyword("middleName", middleName, true);
		document.addKeyword("lastName", lastName, true);
		document.addKeyword("jobTitle", jobTitle);
		document.addKeyword("active", active);
		document.addKeyword("groupIds", groupIds);
		document.addKeyword("organizationIds", organizationIds);
		document.addKeyword(
			"ancestorOrganizationIds",
			_getAncestorOrganizationIds(userId, organizationIds));
		document.addKeyword("roleIds", roleIds);
		document.addKeyword("userGroupIds", userGroupIds);

		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	public static String getUserUID(long userId) {
		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, String.valueOf(userId));

		return document.get(Field.UID);
	}

	public static void updateUser(User user) throws SearchException {
		try {
			if (user.isDefaultUser()) {
				return;
			}

			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				User.class.getName(), user.getUserId());

			Document document = getUserDocument(
				user.getCompanyId(), user.getUserId(), user.getScreenName(),
				user.getEmailAddress(), user.getFirstName(),
				user.getMiddleName(), user.getLastName(), user.getJobTitle(),
				user.getActive(), user.getGroupIds(), user.getOrganizationIds(),
				user.getRoleIds(), user.getUserGroupIds(), assetTagNames,
				user.getExpandoBridge());

			SearchEngineUtil.updateDocument(
				user.getCompanyId(), document.get(Field.UID), document);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public static void updateUsers(long[] userIds) throws SearchException {
		for (long userId : userIds) {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				updateUser(user);
			}
			catch (Exception e) {
				throw new SearchException(e);
			}
		}
	}

	public static void updateUsers(List<User> users) throws SearchException {
		for (User user : users) {
			updateUser(user);
		}
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		// Title

		String firstName = document.get("firstName");
		String middleName = document.get("middleName");
		String lastName = document.get("lastName");

		String title = ContactConstants.getFullName(
			firstName, middleName, lastName);

		// Content

		String content = null;

		// Portlet URL

		String userId = document.get(Field.USER_ID);

		portletURL.setParameter("struts_action", "/enterprise_admin/edit_user");
		portletURL.setParameter("p_u_i_d", userId);

		return new Summary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) throws SearchException {
		try {
			UserLocalServiceUtil.reIndex(classPK);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			UserLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static long[] _getAncestorOrganizationIds(
		long userId, long[] organizationIds) {

		List<Organization> ancestorOrganizations =
			new ArrayList<Organization>();

		for (long organizationId : organizationIds) {
			try {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				ancestorOrganizations.addAll(organization.getAncestors());
			}
			catch (Exception e) {
				_log.error("Error while indexing user " + userId, e);
			}
		}

		long[] ancestorOrganizationIds = new long[ancestorOrganizations.size()];

		for (int i = 0; i < ancestorOrganizations.size(); i++) {
			Organization ancestorOrganization = ancestorOrganizations.get(i);

			ancestorOrganizationIds[i] =
				ancestorOrganization.getOrganizationId();
		}

		return ancestorOrganizationIds;
	}

	private static final String[] _CLASS_NAMES = new String[] {
		User.class.getName()
	};

	private static Log _log = LogFactoryUtil.getLog(UserIndexer.class);

}