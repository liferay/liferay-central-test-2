/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.enterpriseadmin.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
public class UserIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {User.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ENTERPRISE_ADMIN_USERS;

	public UserIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void addContextQueryParams(
			BooleanQuery contextQuery, String key, Object value)
		throws Exception {

		if (key.equals("usersOrgs")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				BooleanQuery usersOrgsQuery =
					BooleanQueryFactoryUtil.create();

				for (long organizationId : values) {
					usersOrgsQuery.addTerm(
						"organizationIds", organizationId);
					usersOrgsQuery.addTerm(
						"ancestorOrganizationIds", organizationId);
				}

				contextQuery.add(usersOrgsQuery, BooleanClauseOccur.MUST);
			}
			else {
				contextQuery.addRequiredTerm(
					"organizationIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersRoles")) {
			contextQuery.addRequiredTerm("roleIds", String.valueOf(value));
		}
		else if (key.equals("usersTeams")) {
			contextQuery.addRequiredTerm("teamIds", String.valueOf(value));
		}
		else if (key.equals("usersUserGroups")) {
			contextQuery.addRequiredTerm("userGroupIds", String.valueOf(value));
		}
	}

	protected void doDelete(Object obj) throws Exception {
		User user = (User)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, user.getUserId());

		SearchEngineUtil.deleteDocument(
			user.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		User user = (User)obj;

		long companyId = user.getCompanyId();
		long userId = user.getUserId();
		String screenName = user.getScreenName();
		String emailAddress = user.getEmailAddress();
		String firstName = user.getFirstName();
		String middleName = user.getMiddleName();
		String lastName = user.getLastName();
		String fullName = user.getFullName();
		String jobTitle = user.getJobTitle();
		int status = user.getStatus();
		long[] groupIds = user.getGroupIds();
		long[] organizationIds = user.getOrganizationIds();
		long[] roleIds = user.getRoleIds();
		long[] teamIds = user.getTeamIds();
		long[] userGroupIds = user.getUserGroupIds();

		List<Address> addresses = user.getAddresses();

		List<String> streets = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();
		List<String> zips = new ArrayList<String>();
		List<String> regions = new ArrayList<String>();
		List<String> countries = new ArrayList<String>();

		for (Address address : addresses) {
			streets.add(address.getStreet1().toLowerCase());
			streets.add(address.getStreet2().toLowerCase());
			streets.add(address.getStreet3().toLowerCase());
			cities.add(address.getCity().toLowerCase());
			zips.add(address.getZip().toLowerCase());
			regions.add(address.getRegion().getName().toLowerCase());
			countries.add(address.getCountry().getName().toLowerCase());
		}

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			User.class.getName(), userId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			User.class.getName(), userId);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, userId);

		document.addModifiedDate();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.USER_ID, userId);

		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, User.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, userId);

		document.addKeyword("screenName", screenName);
		document.addKeyword("emailAddress", emailAddress);
		document.addKeyword("firstName", firstName, true);
		document.addKeyword("middleName", middleName, true);
		document.addKeyword("lastName", lastName, true);
		document.addKeyword("fullName", fullName, true);
		document.addKeyword("jobTitle", jobTitle);
		document.addKeyword("status", status);
		document.addKeyword("groupIds", groupIds);
		document.addKeyword("organizationIds", organizationIds);
		document.addKeyword(
			"ancestorOrganizationIds",
			getAncestorOrganizationIds(userId, organizationIds));
		document.addKeyword("roleIds", roleIds);
		document.addKeyword("teamIds", teamIds);
		document.addKeyword("userGroupIds", userGroupIds);
		document.addKeyword(
			"street", streets.toArray(new String[streets.size()]));
		document.addKeyword("city", cities.toArray(new String[cities.size()]));
		document.addKeyword("zip", zips.toArray(new String[zips.size()]));
		document.addKeyword(
			"region", regions.toArray(new String[regions.size()]));
		document.addKeyword(
			"country", countries.toArray(new String[countries.size()]));

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("email-address")) {
			return "emailAddress";
		}
		else if (orderByCol.equals("first-name")) {
			return "firstName";
		}
		else if (orderByCol.equals("job-title")) {
			return "jobTitle";
		}
		else if (orderByCol.equals("last-name")) {
			return "lastName";
		}
		else if (orderByCol.equals("screen-name")) {
			return "screenName";
		}
		else {
			return orderByCol;
		}
	}

	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String firstName = document.get("firstName");
		String middleName = document.get("middleName");
		String lastName = document.get("lastName");

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String title = fullNameGenerator.getFullName(
			firstName, middleName, lastName);

		String content = null;

		String userId = document.get(Field.USER_ID);

		portletURL.setParameter("struts_action", "/enterprise_admin/edit_user");
		portletURL.setParameter("p_u_i_d", userId);

		return new Summary(title, content, portletURL);
	}

	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof List<?>) {
			List<User> users = (List<User>)obj;

			for (User user : users) {
				doReindex(user);
			}
		}
		else if (obj instanceof Long) {
			long userId = (Long)obj;

			User user = UserLocalServiceUtil.getUserById(userId);

			doReindex(user);
		}
		else if (obj instanceof long[]) {
			long[] userIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap =
				new HashMap<Long, Collection<Document>>();

			for (long userId : userIds) {
				User user = UserLocalServiceUtil.getUserById(userId);

				if (user.isDefaultUser()) {
					continue;
				}

				Document document = getDocument(user);

				long companyId = user.getCompanyId();

				Collection<Document> documents = documentsMap.get(companyId);

				if (documents == null) {
					documents = new ArrayList<Document>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(companyId, documents);
			}
		}
		else if (obj instanceof User) {
			User user = (User)obj;

			if (user.isDefaultUser()) {
				return;
			}

			Document document = getDocument(user);

			SearchEngineUtil.updateDocument(user.getCompanyId(), document);
		}
	}

	protected void doReindex(String className, long classPK) throws Exception {
		User user = UserLocalServiceUtil.getUserById(classPK);

		doReindex(user);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexUsers(companyId);
	}

	protected long[] getAncestorOrganizationIds(
			long userId, long[] organizationIds)
		throws Exception {

		List<Organization> ancestorOrganizations =
			new ArrayList<Organization>();

		for (long organizationId : organizationIds) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			ancestorOrganizations.addAll(organization.getAncestors());
		}

		long[] ancestorOrganizationIds = new long[ancestorOrganizations.size()];

		for (int i = 0; i < ancestorOrganizations.size(); i++) {
			Organization ancestorOrganization = ancestorOrganizations.get(i);

			ancestorOrganizationIds[i] =
				ancestorOrganization.getOrganizationId();
		}

		return ancestorOrganizationIds;
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		Integer status = (Integer)searchContext.getAttribute("status");

		if ((status != null) &&
			(status.intValue() != WorkflowConstants.STATUS_ANY)) {

			contextQuery.addRequiredTerm("status", status);
		}

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value == null) {
				continue;
			}

			addContextQueryParams(contextQuery, key, value);
		}
	}

	protected void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "city", true);
		addSearchTerm(searchQuery, searchContext, "country", true);
		addSearchTerm(searchQuery, searchContext, "emailAddress", true);
		addSearchTerm(searchQuery, searchContext, "firstName", true);
		addSearchTerm(searchQuery, searchContext, "fullName", true);
		addSearchTerm(searchQuery, searchContext, "lastName", true);
		addSearchTerm(searchQuery, searchContext, "middleName", true);
		addSearchTerm(searchQuery, searchContext, "region", true);
		addSearchTerm(searchQuery, searchContext, "screenName", true);
		addSearchTerm(searchQuery, searchContext, "street", true);
		addSearchTerm(searchQuery, searchContext, "zip", true);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		String expandoAttributes = (String)params.get("expandoAttributes");

		if (Validator.isNotNull(expandoAttributes)) {
			addSearchExpando(searchQuery, searchContext, expandoAttributes);
		}
	}

	protected void reindexUsers(long companyId) throws Exception {
		int count = UserLocalServiceUtil.getCompanyUsersCount(companyId);

		int pages = count / UserIndexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * UserIndexer.DEFAULT_INTERVAL);
			int end = start + UserIndexer.DEFAULT_INTERVAL;

			reindexUsers(companyId, start, end);
		}
	}

	protected void reindexUsers(long companyId, int start, int end)
		throws Exception {

		List<User> users = UserLocalServiceUtil.getCompanyUsers(
			companyId, start, end);

		if (users.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (User user : users) {
			if (user.isDefaultUser()) {
				continue;
			}

			Document document = getDocument(user);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

}