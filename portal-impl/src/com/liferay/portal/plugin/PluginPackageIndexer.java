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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class PluginPackageIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {PluginPackage.class.getName()};

	public static final String PORTLET_ID = "PluginPackageIndexer";

	public PluginPackageIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String moduleId = document.get("moduleId");
		String repositoryURL = document.get("repositoryURL");

		portletURL.setParameter(
			"struts_action", "/admin/view");
		portletURL.setParameter("tabs2", "repositories");
		portletURL.setParameter("moduleId", moduleId);
		portletURL.setParameter("repositoryURL", repositoryURL);

		return new Summary(title, content, portletURL);
	}

	protected void doDelete(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, pluginPackage.getModuleId());

		SearchEngineUtil.deleteDocument(
			CompanyConstants.SYSTEM, document.get(Field.UID));
	}

	protected Document doGetDocument(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		String moduleId = pluginPackage.getModuleId();
		String name = pluginPackage.getName();
		String version = pluginPackage.getVersion();
		Date modifiedDate = pluginPackage.getModifiedDate();
		String author = pluginPackage.getAuthor();
		List<String> types = pluginPackage.getTypes();
		List<String> tags = pluginPackage.getTags();
		List<License> licenses = pluginPackage.getLicenses();
		//List<String> liferayVersions = pluginPackage.getLiferayVersions();
		String shortDescription = HtmlUtil.extractText(
			pluginPackage.getShortDescription());
		String longDescription = HtmlUtil.extractText(
			pluginPackage.getLongDescription());
		String changeLog = pluginPackage.getChangeLog();
		String pageURL = pluginPackage.getPageURL();
		String repositoryURL = pluginPackage.getRepositoryURL();

		String[] statusAndInstalledVersion =
			PluginPackageUtil.getStatusAndInstalledVersion(pluginPackage);

		String status = statusAndInstalledVersion[0];
		String installedVersion = statusAndInstalledVersion[1];

		ModuleId moduleIdObj = ModuleId.getInstance(moduleId);

		StringBundler sb = new StringBundler(7);

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(author);
		sb.append(StringPool.SPACE);
		sb.append(shortDescription);
		sb.append(StringPool.SPACE);
		sb.append(longDescription);

		String content = sb.toString();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, moduleId);

		document.addModifiedDate(modifiedDate);

		document.addKeyword(Field.COMPANY_ID, CompanyConstants.SYSTEM);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, moduleIdObj.getGroupId());

		document.addText(Field.TITLE, name);
		document.addText(Field.CONTENT, content);

		document.addKeyword("moduleId", moduleId);
		document.addKeyword("artifactId", moduleIdObj.getArtifactId());
		document.addKeyword("version", version);
		document.addText("author", author);
		document.addKeyword("type", types.toArray(new String[0]));
		document.addKeyword("tag", tags.toArray(new String[0]));

		String[] licenseNames = new String[licenses.size()];

		boolean osiLicense = false;

		for (int i = 0; i < licenses.size(); i++) {
			License license = licenses.get(i);

			licenseNames[i] = license.getName();

			if (license.isOsiApproved()) {
				osiLicense = true;
			}
		}

		document.addKeyword("license", licenseNames);
		document.addKeyword("osi-approved-license", String.valueOf(osiLicense));
		document.addText("shortDescription", shortDescription);
		document.addText("longDescription", longDescription);
		document.addText("changeLog", changeLog);
		document.addText("pageURL", pageURL);
		document.addKeyword("repositoryURL", repositoryURL);
		document.addKeyword("status", status);
		document.addKeyword("installedVersion", installedVersion);

		return document;
	}

	protected void doReindex(Object obj) throws Exception {
		PluginPackage pluginPackage = (PluginPackage)obj;

		Document document = getDocument(pluginPackage);

		SearchEngineUtil.updateDocument(CompanyConstants.SYSTEM, document);
	}

	protected void doReindex(String className, long classPK) throws Exception {
	}

	protected void doReindex(String[] ids) throws Exception {
		SearchEngineUtil.deletePortletDocuments(
			CompanyConstants.SYSTEM, PORTLET_ID);

		Collection<Document> documents = new ArrayList<Document>();

		for (PluginPackage pluginPackage :
				PluginPackageUtil.getAllAvailablePluginPackages()) {

			Document document = getDocument(pluginPackage);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(CompanyConstants.SYSTEM, documents);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void postProcessFullQuery(
			BooleanQuery fullQuery, SearchContext searchContext)
		throws Exception {

		String type = (String)searchContext.getAttribute("type");

		if (Validator.isNotNull(type)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			searchQuery.addRequiredTerm("type", type);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String tag = (String)searchContext.getAttribute("tag");

		if (Validator.isNotNull(tag)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			searchQuery.addExactTerm("tag", tag);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String repositoryURL = (String)searchContext.getAttribute(
			"repositoryURL");

		if (Validator.isNotNull(repositoryURL)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			Query query = TermQueryFactoryUtil.create(
				"repositoryURL", repositoryURL);

			searchQuery.add(query, BooleanClauseOccur.SHOULD);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String license = (String)searchContext.getAttribute("license");

		if (Validator.isNotNull(license)) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			searchQuery.addExactTerm("license", license);

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}

		String status = (String)searchContext.getAttribute("status");

		if (Validator.isNotNull(status) && !status.equals("all")) {
			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (status.equals(
					PluginPackageImpl.
						STATUS_NOT_INSTALLED_OR_OLDER_VERSION_INSTALLED)) {

				searchQuery.addExactTerm(
					"status", PluginPackageImpl.STATUS_NOT_INSTALLED);
				searchQuery.addExactTerm(
					"status", PluginPackageImpl.STATUS_OLDER_VERSION_INSTALLED);
			}
			else {
				searchQuery.addExactTerm("status", status);
			}

			fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
		}
	}

}