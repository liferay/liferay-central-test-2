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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

/**
 * <a href="PluginPackageIndexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class PluginPackageIndexer implements Indexer {

	public static final String PORTLET_ID = "PluginPackageIndexer";

	public static void addPluginPackage(
			String moduleId, String name, String version, Date modifiedDate,
			String author, List<String> types, List<String> tags,
			List<License> licenses, List<String> liferayVersions,
			String shortDescription, String longDescription, String changeLog,
			String pageURL, String repositoryURL, String status,
			String installedVersion)
		throws SearchException {

		Document doc = getPluginPackageDocument(
			moduleId, name, version, modifiedDate, author, types, tags,
			licenses, liferayVersions, shortDescription, longDescription,
			changeLog, pageURL, repositoryURL, status, installedVersion);

		SearchEngineUtil.addDocument(CompanyConstants.SYSTEM, doc);
	}

	public static void cleanIndex() throws SearchException {
		SearchEngineUtil.deletePortletDocuments(
			CompanyConstants.SYSTEM, PORTLET_ID);
	}

	public static Document getPluginPackageDocument(
		String moduleId, String name, String version, Date modifiedDate,
		String author, List<String> types, List<String> tags,
		List<License> licenses, List<String> liferayVersions,
		String shortDescription, String longDescription, String changeLog,
		String pageURL, String repositoryURL, String status,
		String installedVersion) {

		ModuleId moduleIdObj = ModuleId.getInstance(moduleId);

		shortDescription = HtmlUtil.extractText(shortDescription);
		longDescription = HtmlUtil.extractText(longDescription);

		String content =
			name + " " + author + " " + shortDescription + " " +
				longDescription;

		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, moduleId);

		doc.addModifiedDate(modifiedDate);

		doc.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		doc.addKeyword(Field.GROUP_ID, moduleIdObj.getGroupId());

		doc.addText(Field.TITLE, name);
		doc.addText(Field.CONTENT, content);

		doc.addKeyword("moduleId", moduleId);
		doc.addKeyword("artifactId", moduleIdObj.getArtifactId());
		doc.addKeyword("version", version);
		doc.addText("author", author);
		doc.addKeyword("type", types.toArray(new String[0]));
		doc.addKeyword("tag", tags.toArray(new String[0]));

		String[] licenseNames = new String[licenses.size()];

		boolean osiLicense = false;

		for (int i = 0; i < licenses.size(); i++) {
			License license = licenses.get(i);

			licenseNames[i] = license.getName();

			if (license.isOsiApproved()) {
				osiLicense = true;
			}
		}

		doc.addKeyword("license", licenseNames);
		doc.addKeyword("osi-approved-license", String.valueOf(osiLicense));
		doc.addText("shortDescription", shortDescription);
		doc.addText("longDescription", longDescription);
		doc.addText("changeLog", changeLog);
		doc.addText("pageURL", pageURL);
		doc.addKeyword("repositoryURL", repositoryURL);
		doc.addKeyword("status", status);
		doc.addKeyword("installedVersion", installedVersion);

		return doc;
	}

	public static String getPluginPackagerUID(String moduleId) {
		Document doc = new DocumentImpl();

		doc.addUID(PORTLET_ID, moduleId);

		return doc.get(Field.UID);
	}

	public static void removePluginPackage(String moduleId)
		throws SearchException {

		SearchEngineUtil.deleteDocument(
			CompanyConstants.SYSTEM, getPluginPackagerUID(moduleId));
	}

	public static void updatePluginPackage(
			String moduleId, String name, String version, Date modifiedDate,
			String author, List<String> types, List<String> tags,
			List<License> licenses, List<String> liferayVersions,
			String shortDescription, String longDescription, String changeLog,
			String pageURL, String repositoryURL, String status,
			String installedVersion)
		throws SearchException {

		Document doc = getPluginPackageDocument(
			moduleId, name, version, modifiedDate, author, types, tags,
			licenses, liferayVersions, shortDescription, longDescription,
			changeLog, pageURL, repositoryURL, status, installedVersion);

		SearchEngineUtil.updateDocument(
			CompanyConstants.SYSTEM, doc.get(Field.UID), doc);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		Document doc, String snippet, PortletURL portletURL) {

		// Title

		String title = doc.get(Field.TITLE);

		// Content

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(doc.get(Field.CONTENT), 200);
		}

		// Portlet URL

		String moduleId = doc.get("moduleId");
		String repositoryURL = doc.get("repositoryURL");

		portletURL.setParameter(
			"struts_action", "/admin/view");
		portletURL.setParameter("tabs2", "repositories");
		portletURL.setParameter("moduleId", moduleId);
		portletURL.setParameter("repositoryURL", repositoryURL);

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String className, long classPK) {
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			PluginPackageUtil.reIndex();
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final String[] _CLASS_NAMES = new String[0];

}