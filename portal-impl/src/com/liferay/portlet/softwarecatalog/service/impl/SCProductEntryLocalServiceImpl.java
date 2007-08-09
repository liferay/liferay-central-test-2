/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.plugin.ModuleId;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.ProductEntryLicenseException;
import com.liferay.portlet.softwarecatalog.ProductEntryNameException;
import com.liferay.portlet.softwarecatalog.ProductEntryPageURLException;
import com.liferay.portlet.softwarecatalog.ProductEntryShortDescriptionException;
import com.liferay.portlet.softwarecatalog.ProductEntryTypeException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.base.SCProductEntryLocalServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionUtil;
import com.liferay.portlet.softwarecatalog.util.Indexer;
import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;
import com.liferay.util.lucene.HitsImpl;
import com.liferay.util.xml.DocUtil;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="SCProductEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductEntryLocalServiceImpl
	extends SCProductEntryLocalServiceBaseImpl {

	public SCProductEntry addProductEntry(
			long userId, long plid, String name, String type,
			String shortDescription, String longDescription, String pageURL,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProductEntry(
			userId, plid, name, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, licenseIds,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public SCProductEntry addProductEntry(
			long userId, long plid, String name, String type,
			String shortDescription, String longDescription, String pageURL,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProductEntry(
			userId, plid, name, type, shortDescription, longDescription,
			pageURL, repoGroupId, repoArtifactId, licenseIds, null, null,
			communityPermissions, guestPermissions);
	}

	public SCProductEntry addProductEntry(
			long userId, long plid, String name, String type,
			String shortDescription, String longDescription, String pageURL,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Product entry

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		validate(name, type, shortDescription, pageURL, licenseIds);

		long productEntryId = CounterLocalServiceUtil.increment();

		SCProductEntry productEntry = SCProductEntryUtil.create(productEntryId);

		productEntry.setGroupId(groupId);
		productEntry.setCompanyId(user.getCompanyId());
		productEntry.setUserId(user.getUserId());
		productEntry.setUserName(user.getFullName());
		productEntry.setCreateDate(now);
		productEntry.setModifiedDate(now);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		SCProductEntryUtil.update(productEntry);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addProductEntryResources(
				productEntry, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addProductEntryResources(
				productEntry, communityPermissions, guestPermissions);
		}

		// Licenses

		SCProductEntryUtil.setSCLicenses(productEntryId, licenseIds);

		// Lucene

		try {
			Indexer.addProductEntry(
				productEntry.getCompanyId(), groupId, userId,
				user.getFullName(), productEntryId, name, type,
				shortDescription, longDescription, pageURL, repoGroupId,
				repoArtifactId);
		}
		catch (IOException ioe) {
			_log.error("Indexing " + productEntryId, ioe);
		}

		return productEntry;
	}

	public void addProductEntryResources(
			long productEntryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry = SCProductEntryUtil.findByPrimaryKey(
			productEntryId);

		addProductEntryResources(
			productEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
			SCProductEntry productEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProductEntryResources(
			long productEntryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry = SCProductEntryUtil.findByPrimaryKey(
			productEntryId);

		addProductEntryResources(
			productEntry, communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(
			SCProductEntry productEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), communityPermissions,
			guestPermissions);
	}

	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntry productEntry = SCProductEntryUtil.findByPrimaryKey(
			productEntryId);

		deleteProductEntry(productEntry);
	}

	public void deleteProductEntry(SCProductEntry productEntry)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteProductEntry(
				productEntry.getCompanyId(), productEntry.getProductEntryId());
		}
		catch (IOException ioe) {
			_log.error(
				"Deleting index " + productEntry.getProductEntryId(), ioe);
		}

		// Product versions

		SCProductVersionLocalServiceUtil.deleteProductVersions(
			productEntry.getProductEntryId());

		// Ratings

		RatingsStatsLocalServiceUtil.deleteStats(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		// Message boards

		MBMessageLocalServiceUtil.deleteDiscussionMessages(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			productEntry.getCompanyId(), SCProductEntry.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, productEntry.getProductEntryId());

		// Product entry

		SCProductEntryUtil.remove(productEntry);
	}

	public SCProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		return SCProductEntryUtil.findByPrimaryKey(productEntryId);
	}

	public List getProductEntries(long groupId, int begin, int end)
		throws SystemException {

		return SCProductEntryUtil.findByGroupId(groupId, begin, end);
	}

	public List getProductEntries(long groupId, long userId, int begin, int end)
		throws SystemException {

		return SCProductEntryUtil.findByG_U(groupId, userId, begin, end);
	}

	public int getProductEntriesCount(long groupId)
		throws SystemException {

		return SCProductEntryUtil.countByGroupId(groupId);
	}

	public int getProductEntriesCount(long groupId, long userId)
		throws SystemException {

		return SCProductEntryUtil.countByG_U(groupId, userId);
	}

	public String getRepositoryXML(
			long groupId, String baseImageURL, Date oldestDate,
			int maxNumOfVersions, Properties repoSettings)
		throws PortalException, SystemException{

		Document doc = DocumentHelper.createDocument();

		doc.setXMLEncoding("UTF-8");

		Element root = doc.addElement("plugin-repository");

		Element settingsEl = root.addElement("settings");

		populateSettingsElement(settingsEl, repoSettings);

		List productEntries = SCProductEntryUtil.findByGroupId(groupId);

		Iterator itr = productEntries.iterator();

		while (itr.hasNext()) {
			SCProductEntry productEntry = (SCProductEntry)itr.next();

			if (Validator.isNull(productEntry.getRepoGroupId()) ||
				Validator.isNull(productEntry.getRepoArtifactId())) {

				continue;
			}

			List productVersions = SCProductVersionUtil.findByProductEntryId(
				productEntry.getProductEntryId());

			Iterator itr2 = productVersions.iterator();

			for (int i = 1; itr2.hasNext(); i++) {
				SCProductVersion productVersion = (SCProductVersion)itr2.next();

				if ((maxNumOfVersions > 0) && (maxNumOfVersions < i)) {
					break;
				}

				if (!productVersion.getRepoStoreArtifact()) {
					continue;
				}

				if ((oldestDate != null) &&
					(oldestDate.after(productVersion.getModifiedDate()))) {

					continue;
				}

				Element el = root.addElement("plugin-package");

				populatePluginPackageElement(
					el, productEntry, productVersion, baseImageURL);
			}
		}

		return doc.asXML();
	}

	public void reIndex(String[] ids) throws SystemException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);

			Iterator itr = SCProductEntryUtil.findByCompanyId(
				companyId).iterator();

			while (itr.hasNext()) {
				SCProductEntry productEntry = (SCProductEntry)itr.next();

				long productEntryId = productEntry.getProductEntryId();

				try {
					Indexer.addProductEntry(
						companyId, productEntry.getGroupId(),
						productEntry.getUserId(), productEntry.getUserName(),
						productEntryId, productEntry.getName(),
						productEntry.getType(),
						productEntry.getShortDescription(),
						productEntry.getLongDescription(),
						productEntry.getPageURL(),
						productEntry.getRepoGroupId(),
						productEntry.getRepoArtifactId());
				}
				catch (Exception e1) {
					_log.error("Reindexing " + productEntryId, e1);
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public Hits search(
			long companyId, long groupId, String type, String keywords)
		throws SystemException {

		Searcher searcher = null;

		try {
			HitsImpl hits = new HitsImpl();

			if (Validator.isNull(type) && Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, Indexer.PORTLET_ID);
			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.GROUP_ID, groupId);

			if (Validator.isNotNull(type)) {
				LuceneUtil.addRequiredTerm(contextQuery, "type", type);
			}

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);

			if (Validator.isNotNull(keywords)) {
				BooleanQuery searchQuery = new BooleanQuery();

				LuceneUtil.addTerm(searchQuery, LuceneFields.TITLE, keywords);
				LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);

				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			searcher = LuceneUtil.getSearcher(companyId);

			hits.recordHits(searcher.search(fullQuery), searcher);

			return hits;
		}
		catch (Exception e) {
			return LuceneUtil.closeSearcher(searcher, keywords, e);
		}
	}

	public SCProductEntry updateProductEntry(
			long productEntryId, String name, String type,
			String shortDescription, String longDescription, String pageURL,
			String repoGroupId, String repoArtifactId, long[] licenseIds)
		throws PortalException, SystemException {

		// Product entry

		validate(name, type, shortDescription, pageURL, licenseIds);

		SCProductEntry productEntry = SCProductEntryUtil.findByPrimaryKey(
			productEntryId);

		productEntry.setModifiedDate(new Date());
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		SCProductEntryUtil.update(productEntry);

		// Licenses

		SCProductEntryUtil.setSCLicenses(productEntryId, licenseIds);

		// Lucene

		try {
			Indexer.updateProductEntry(
				productEntry.getCompanyId(), productEntry.getGroupId(),
				productEntry.getUserId(), productEntry.getUserName(),
				productEntryId, name, type, shortDescription, longDescription,
				pageURL, repoGroupId, repoArtifactId);
		}
		catch (IOException ioe) {
			_log.error("Indexing " + productEntryId, ioe);
		}

		return productEntry;
	}

	protected void populatePluginPackageElement(
			Element el, SCProductEntry productEntry,
			SCProductVersion productVersion, String baseImageURL)
		throws PortalException, SystemException {

		DocUtil.add(el, "name", productEntry.getName());

		String moduleId = ModuleId.toString(
			productEntry.getRepoGroupId(), productEntry.getRepoArtifactId(),
			productVersion.getVersion(), "war");

		DocUtil.add(el, "module-id", moduleId);

		Element typesEl = el.addElement("types");

		DocUtil.add(typesEl, "type", productEntry.getType());

		DocUtil.add(
			el, "short-descriptionid", productEntry.getShortDescription());

		if (Validator.isNotNull(productEntry.getLongDescription())) {
			DocUtil.add(
				el, "long-description", productEntry.getLongDescription());
		}

		if (Validator.isNotNull(productVersion.getChangeLog())) {
			DocUtil.add(el, "change-log", productVersion.getChangeLog());
		}

		if (Validator.isNotNull(productVersion.getDirectDownloadURL())) {
			DocUtil.add(
				el, "download-url", productVersion.getDirectDownloadURL());
		}

		DocUtil.add(el, "author", productEntry.getUserName());

		Element licensesEl = el.addElement("licenses");

		Iterator itr = productEntry.getLicenses().iterator();

		while (itr.hasNext()) {
			SCLicense license = (SCLicense)itr.next();

			Element licenseEl = licensesEl.addElement("license");

			licenseEl.addText(license.getName());
			licenseEl.addAttribute(
				"osi-approved", String.valueOf(license.isOpenSource()));
		}

		Element liferayVersionsEl = el.addElement("liferay-versions");

		itr = productVersion.getFrameworkVersions().iterator();

		while (itr.hasNext()) {
			SCFrameworkVersion frameworkVersion =
				(SCFrameworkVersion)itr.next();

			DocUtil.add(
				liferayVersionsEl, "liferay-version",
				frameworkVersion.getName());
		}
	}

	protected void populateSettingsElement(
		Element el, Properties repoSettings) {

		if (repoSettings == null) {
			return;
		}

		Iterator itr = repoSettings.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String) itr.next();

			Element settingEl = el.addElement("setting");

			settingEl.addAttribute("name", key);
			settingEl.addAttribute("value", repoSettings.getProperty(key));
		}
	}

	protected void validate(
			String name, String type, String shortDescription, String pageURL,
			long[] licenseIds)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ProductEntryNameException();
		}

		if (Validator.isNull(type)) {
			throw new ProductEntryTypeException();
		}

		if (Validator.isNull(shortDescription)) {
			throw new ProductEntryShortDescriptionException();
		}

		if (Validator.isNull(pageURL)) {
			throw new ProductEntryPageURLException();
		}
		else {
			try {
				new URL(pageURL);
			}
			catch (MalformedURLException murle) {
				throw new ProductEntryPageURLException();
			}
		}

		if (licenseIds.length == 0) {
			throw new ProductEntryLicenseException();
		}
	}

	private static Log _log =
		LogFactory.getLog(SCProductEntryLocalServiceImpl.class);

}