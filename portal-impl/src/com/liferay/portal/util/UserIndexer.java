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

package com.liferay.portal.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.List;

import javax.portlet.PortletURL;


/**
 * <a href="UserIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class UserIndexer implements Indexer {

	public static final String PORTLET_ID = PortletKeys.ENTERPRISE_ADMIN_USERS;

	public static void addUser(
			long companyId, long userId, String firstName, String middleName,
			String lastName, String jobTitle, String screenName,
			String emailAddress, Boolean active, long[] organizationIds,
			ExpandoBridge expandoBridge)
		throws SearchException {

		Document doc = getUserDocument(
			companyId, userId, firstName, middleName, lastName, screenName,
			emailAddress, jobTitle, active, organizationIds, expandoBridge);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteUser(long companyId, long userId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getUserUID(userId));
	}

	public static Document getUserDocument(
		long companyId, long userId, String firstName, String middleName,
		String lastName, String jobTitle, String screenName,
		String emailAddress, Boolean active, long[] organizationIds,
		ExpandoBridge expandoBridge) {

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, String.valueOf(userId));

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.USER_ID, userId);

		doc.addText("firstName", firstName);
		doc.addText("middleName", middleName);
		doc.addText("lastName", lastName);
		doc.addText("jobTitle", jobTitle);
		doc.addKeyword("screenName", screenName);
		doc.addKeyword("emailAddress", emailAddress);
		doc.addKeyword("active", active.toString());

		doc.addModifiedDate();

		doc.addKeyword(
			"usersOrgs", ArrayUtil.toStringArray(organizationIds));

		try {
			List<Role> roles = RoleLocalServiceUtil.getUserRoles(userId);
			long[] roleIds = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				roleIds[i] = roles.get(i).getRoleId();
			}

			doc.addKeyword("usersRoles", ArrayUtil.toStringArray(roleIds));
		}
		catch (Exception e) {
		}

		try {
			List<UserGroup> userGroups =
				UserGroupLocalServiceUtil.getUserUserGroups(userId);
			long[] userGroupIds = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				userGroupIds[i] = userGroups.get(i).getUserGroupId();
			}

			doc.addKeyword(
				"usersUserGroups", ArrayUtil.toStringArray(userGroupIds));
		}
		catch (Exception e) {
		}

		ExpandoBridgeIndexerUtil.addAttributes(doc, expandoBridge);

		return doc;
	}

	public static String getUserUID(long userId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, String.valueOf(userId));

		return doc.get(Field.UID);
	}

	public static void updateUser(User user)
		throws SearchException {

		try {
			Contact contact = ContactLocalServiceUtil.getContact(
				user.getContactId());

			Document doc = getUserDocument(
				user.getCompanyId(), user.getUserId(), contact.getFirstName(),
				contact.getMiddleName(), contact.getLastName(),
				contact.getJobTitle(), user.getScreenName(),
				user.getEmailAddress(), user.getActive(),
				user.getOrganizationIds(), user.getExpandoBridge());

			SearchEngineUtil.updateDocument(
				user.getCompanyId(), doc.get(Field.UID), doc);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public static void updateUsers(List<User> users)
		throws SearchException {

		for (User user : users) {
			try {
				updateUser(user);
			}
			catch (Exception e) {
				throw new SearchException(e);
			}
		}
	}

	public static void updateUsers(long[] userIds)
		throws SearchException {

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

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Full Name

		String firstName = doc.get("firstName");
		String middleName = doc.get("middleName");
		String lastName = doc.get("lastName");

		// User ID

		String userId = doc.get(Field.USER_ID);

		// Portlet URL

		portletURL.setParameter("struts_action", "/enterprise_admin/edit_user");
		portletURL.setParameter("p_u_i_d", userId);

		return new DocumentSummary(
			ContactConstants.getFullName(firstName, middleName, lastName),
			null, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			UserLocalServiceUtil.reIndex(ids);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

}
