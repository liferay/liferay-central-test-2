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

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.NoSuchRegionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Region;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CountryServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RegionServiceUtil;
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
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="OrganzationIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
public class OrganizationIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {Organization.class.getName()};

	public static final String PORTLET_ID =
		PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS;

	public OrganizationIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get("name");

		String content = null;

		String organizationId = document.get(Field.ORGANIZATION_ID);

		portletURL.setParameter(
			"struts_action", "/enterprise_admin/edit_organization");
		portletURL.setParameter("organizationId", organizationId);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, organization.getOrganizationId());

		SearchEngineUtil.deleteDocument(
			organization.getCompanyId(), document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		Organization organization = (Organization)obj;

		long companyId = organization.getCompanyId();
		long organizationId = organization.getOrganizationId();
		long parentOrganizationId = organization.getParentOrganizationId();
		long leftOrganizationId = organization.getLeftOrganizationId();
		long rightOrganizationId = organization.getRightOrganizationId();
		String name = organization.getName();
		String type = organization.getType();
		long regionId = organization.getRegionId();
		long countryId = organization.getCountryId();

		List<Address> addresses = organization.getAddresses();

		List<String> streets = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();
		List<String> zips = new ArrayList<String>();
		List<String> regions = new ArrayList<String>();
		List<String> countries = new ArrayList<String>();

		if (regionId > 0) {
			try {
				Region region = RegionServiceUtil.getRegion(regionId);

				regions.add(region.getName().toLowerCase());
			}
			catch (NoSuchRegionException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsre.getMessage());
				}
			}
		}

		if (countryId > 0) {
			try {
				Country country = CountryServiceUtil.getCountry(countryId);

				countries.add(country.getName().toLowerCase());
			}
			catch (NoSuchCountryException nsce) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsce.getMessage());
				}
			}
		}

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
			User.class.getName(), organizationId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			User.class.getName(), organizationId);

		ExpandoBridge expandoBridge = organization.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, organizationId);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.ORGANIZATION_ID, organizationId);

		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(
			Field.ENTRY_CLASS_NAME, Organization.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, organizationId);

		document.addKeyword("parentOrganizationId", parentOrganizationId);
		document.addNumber("leftOrganizationId", leftOrganizationId);
		document.addNumber("rightOrganizationId", rightOrganizationId);
		document.addKeyword("name", name, true);
		document.addKeyword("type", type);
		document.addKeyword(
			"street", streets.toArray(new String[streets.size()]));
		document.addKeyword(
			"city", cities.toArray(new String[cities.size()]));
		document.addKeyword(
			"zip", zips.toArray(new String[zips.size()]));
		document.addKeyword(
			"region", regions.toArray(new String[regions.size()]));
		document.addKeyword(
			"country", countries.toArray(new String[countries.size()]));

		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof List<?>) {
			List<Organization> organizations = (List<Organization>)obj;

			for (Organization organization : organizations) {
				doReindex(organization);
			}
		}
		else if (obj instanceof Long) {
			long organizationId = (Long)obj;

			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(organizationId);

			doReindex(organization);
		}
		else if (obj instanceof long[]) {
			long[] organizationIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap =
				new HashMap<Long, Collection<Document>>();

			for (long organizationId : organizationIds) {
				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				Document document = getDocument(organization);

				long companyId = organization.getCompanyId();

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
		else if (obj instanceof Organization) {
			Organization organization = (Organization)obj;

			Document document = getDocument(organization);

			SearchEngineUtil.updateDocument(
				organization.getCompanyId(), document);
		}
	}

	protected void doReindex(String className, long classPK) throws Exception {
		Organization organization =
			OrganizationLocalServiceUtil.getOrganization(classPK);

		doReindex(organization);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexOrganizations(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		Long[][] leftAndRightOrganizationIds = (Long[][])params.get(
			"organizationsTree");

		if (leftAndRightOrganizationIds != null) {
			BooleanQuery organizationsTreeQuery =
				BooleanQueryFactoryUtil.create();

			if (leftAndRightOrganizationIds.length == 0) {
				organizationsTreeQuery.addRequiredTerm(
					Field.ORGANIZATION_ID, -1);
			}
			else if (leftAndRightOrganizationIds.length > 0) {
				for (int i = 0; i < leftAndRightOrganizationIds.length; i++) {
					organizationsTreeQuery.addNumericRangeTerm(
						"leftOrganizationId", leftAndRightOrganizationIds[i][0],
						leftAndRightOrganizationIds[i][1]);
				}
			}

			contextQuery.add(organizationsTreeQuery, BooleanClauseOccur.MUST);
		}
	}

	protected void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		String city = (String)searchContext.getAttribute("city");

		if (Validator.isNotNull(city)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("city", city, true);
			}
			else {
				searchQuery.addTerm("city", city, true);
			}
		}

		String country = (String)searchContext.getAttribute("country");

		if (Validator.isNotNull(country)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("country", country, true);
			}
			else {
				searchQuery.addTerm("country", country, true);
			}
		}

		String name = (String)searchContext.getAttribute("name");

		if (Validator.isNotNull(name)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("name", name, true);
			}
			else {
				searchQuery.addTerm("name", name, true);
			}
		}

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		String expandoAttributes = (String)params.get("expandoAttributes");

		if (Validator.isNotNull(expandoAttributes)) {
			addSearchExpando(searchQuery, searchContext, expandoAttributes);
		}

		String parentOrganizationId = (String)searchContext.getAttribute(
			"parentOrganizationId");

		if (Validator.isNotNull(parentOrganizationId)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm(
					"parentOrganizationId", parentOrganizationId, true);
			}
			else {
				searchQuery.addTerm(
					"parentOrganizationId", parentOrganizationId, true);
			}
		}

		String region = (String)searchContext.getAttribute("region");

		if (Validator.isNotNull(region)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("region", region, true);
			}
			else {
				searchQuery.addTerm("region", region, true);
			}
		}

		String street = (String)searchContext.getAttribute("street");

		if (Validator.isNotNull(street)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("street", street, true);
			}
			else {
				searchQuery.addTerm("street", street, true);
			}
		}

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("type", type, true);
			}
			else {
				searchQuery.addTerm("type", type, true);
			}
		}

		String zip = (String)searchContext.getAttribute("zip");

		if (Validator.isNotNull(zip)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm("zip", zip, true);
			}
			else {
				searchQuery.addTerm("zip", zip, true);
			}
		}
	}

	protected void reindexOrganizations(long companyId) throws Exception {
		int count = OrganizationLocalServiceUtil.getOrganizationsCount();

		int pages = count / OrganizationIndexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * OrganizationIndexer.DEFAULT_INTERVAL);
			int end = start + OrganizationIndexer.DEFAULT_INTERVAL;

			reindexOrganizations(companyId, start, end);
		}
	}

	protected void reindexOrganizations(long companyId, int start, int end)
		throws Exception {

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getOrganizations(start, end);

		if (organizations.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (Organization organization : organizations) {
			Document document = getDocument(organization);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	private static Log _log = LogFactoryUtil.getLog(OrganizationIndexer.class);

}