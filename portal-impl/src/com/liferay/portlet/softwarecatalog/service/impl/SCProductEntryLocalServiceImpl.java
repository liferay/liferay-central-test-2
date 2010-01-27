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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.plugin.ModuleId;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.softwarecatalog.DuplicateProductEntryModuleIdException;
import com.liferay.portlet.softwarecatalog.ProductEntryAuthorException;
import com.liferay.portlet.softwarecatalog.ProductEntryLicenseException;
import com.liferay.portlet.softwarecatalog.ProductEntryNameException;
import com.liferay.portlet.softwarecatalog.ProductEntryPageURLException;
import com.liferay.portlet.softwarecatalog.ProductEntryScreenshotsException;
import com.liferay.portlet.softwarecatalog.ProductEntryShortDescriptionException;
import com.liferay.portlet.softwarecatalog.ProductEntryTypeException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCProductEntryLocalServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.util.SCIndexer;
import com.liferay.util.xml.DocUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * <a href="SCProductEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class SCProductEntryLocalServiceImpl
	extends SCProductEntryLocalServiceBaseImpl {

	public SCProductEntry addProductEntry(
			long userId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Product entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		tags = getTags(tags);
		repoGroupId = repoGroupId.trim().toLowerCase();
		repoArtifactId = repoArtifactId.trim().toLowerCase();
		Date now = new Date();

		validate(
			0, name, type, shortDescription, pageURL, author, repoGroupId,
			repoArtifactId, licenseIds, thumbnails, fullImages);

		long productEntryId = counterLocalService.increment();

		SCProductEntry productEntry = scProductEntryPersistence.create(
			productEntryId);

		productEntry.setGroupId(groupId);
		productEntry.setCompanyId(user.getCompanyId());
		productEntry.setUserId(user.getUserId());
		productEntry.setUserName(user.getFullName());
		productEntry.setCreateDate(now);
		productEntry.setModifiedDate(now);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setTags(tags);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setAuthor(author);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		scProductEntryPersistence.update(productEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addProductEntryResources(
				productEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addProductEntryResources(
				productEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Licenses

		scProductEntryPersistence.setSCLicenses(productEntryId, licenseIds);

		// Product screenshots

		saveProductScreenshots(productEntry, thumbnails, fullImages);

		// Message boards

		if (PropsValues.SC_PRODUCT_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, productEntry.getUserName(),
				SCProductEntry.class.getName(), productEntryId,
				StatusConstants.APPROVED);
		}

		// Indexer

		reIndex(productEntry);

		return productEntry;
	}

	public void addProductEntryResources(
			long productEntryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		addProductEntryResources(
			productEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addProductEntryResources(
			long productEntryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		addProductEntryResources(
			productEntry, communityPermissions, guestPermissions);
	}

	public void addProductEntryResources(
			SCProductEntry productEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProductEntryResources(
			SCProductEntry productEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			productEntry.getCompanyId(), productEntry.getGroupId(),
			productEntry.getUserId(), SCProductEntry.class.getName(),
			productEntry.getProductEntryId(), communityPermissions,
			guestPermissions);
	}

	public void deleteProductEntries(long groupId)
		throws PortalException, SystemException {

		List<SCProductEntry> productEntries =
			scProductEntryPersistence.findByGroupId(groupId);

		for (SCProductEntry productEntry : productEntries) {
			deleteProductEntry(productEntry);
		}
	}

	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		deleteProductEntry(productEntry);
	}

	public void deleteProductEntry(SCProductEntry productEntry)
		throws PortalException, SystemException {

		// Product entry

		scProductEntryPersistence.remove(productEntry);

		// Resources

		resourceLocalService.deleteResource(
			productEntry.getCompanyId(), SCProductEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			productEntry.getProductEntryId());

		// Product screenshots

		scProductScreenshotLocalService.deleteProductScreenshots(
			productEntry.getProductEntryId());

		// Product versions

		scProductVersionLocalService.deleteProductVersions(
			productEntry.getProductEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			SCProductEntry.class.getName(), productEntry.getProductEntryId());

		// Indexer

		try {
			SCIndexer.deleteProductEntry(
				productEntry.getCompanyId(), productEntry.getProductEntryId());
		}
		catch (SearchException se) {
			_log.error(
				"Deleting index " + productEntry.getProductEntryId(), se);
		}
	}

	public List<SCProductEntry> getProductEntries(
			long groupId, int start, int end)
		throws SystemException {

		return scProductEntryPersistence.findByGroupId(groupId, start, end);
	}

	public List<SCProductEntry> getProductEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return scProductEntryPersistence.findByGroupId(
			groupId, start, end, obc);
	}

	public List<SCProductEntry> getProductEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return scProductEntryPersistence.findByG_U(groupId, userId, start, end);
	}

	public List<SCProductEntry> getProductEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return scProductEntryPersistence.findByG_U(
			groupId, userId, start, end, obc);
	}

	public int getProductEntriesCount(long groupId)
		throws SystemException {

		return scProductEntryPersistence.countByGroupId(groupId);
	}

	public int getProductEntriesCount(long groupId, long userId)
		throws SystemException {

		return scProductEntryPersistence.countByG_U(groupId, userId);
	}

	public SCProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {

		return scProductEntryPersistence.findByPrimaryKey(productEntryId);
	}

	public String getRepositoryXML(
			long groupId, String baseImageURL, Date oldestDate,
			int maxNumOfVersions, Properties repoSettings)
		throws SystemException {

		return getRepositoryXML(
			groupId, null, baseImageURL, oldestDate, maxNumOfVersions,
			repoSettings);
	}

	public String getRepositoryXML(
			long groupId, String version, String baseImageURL, Date oldestDate,
			int maxNumOfVersions, Properties repoSettings)
		throws SystemException {

		Document doc = SAXReaderUtil.createDocument();

		doc.setXMLEncoding(StringPool.UTF8);

		Element root = doc.addElement("plugin-repository");

		Element settingsEl = root.addElement("settings");

		populateSettingsElement(settingsEl, repoSettings);

		List<SCProductEntry> productEntries =
			scProductEntryPersistence.findByGroupId(groupId);

		for (SCProductEntry productEntry : productEntries) {
			if (Validator.isNull(productEntry.getRepoGroupId()) ||
				Validator.isNull(productEntry.getRepoArtifactId())) {

				continue;
			}

			List<SCProductVersion> productVersions =
				scProductVersionPersistence.findByProductEntryId(
					productEntry.getProductEntryId());

			for (int i = 0; i < productVersions.size(); i++) {
				SCProductVersion productVersion = productVersions.get(i);

				if ((maxNumOfVersions > 0) && (maxNumOfVersions < (i + 1))) {
					break;
				}

				if (!productVersion.isRepoStoreArtifact()) {
					continue;
				}

				if ((oldestDate != null) &&
					(oldestDate.after(productVersion.getModifiedDate()))) {

					continue;
				}

				if (Validator.isNotNull(version) &&
					!isVersionSupported(
						version, productVersion.getFrameworkVersions())) {

					continue;
				}

				Element el = root.addElement("plugin-package");

				populatePluginPackageElement(
					el, productEntry, productVersion, baseImageURL);
			}
		}

		return doc.asXML();
	}

	public void reIndex(long productEntryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		SCProductEntry productEntry =
			scProductEntryPersistence.fetchByPrimaryKey(productEntryId);

		if (productEntry == null) {
			return;
		}

		reIndex(productEntry);
	}

	public void reIndex(SCProductEntry productEntry) throws SystemException {
		long companyId = productEntry.getCompanyId();
		long groupId = productEntry.getGroupId();
		long userId = productEntry.getUserId();
		String userName = productEntry.getUserName();
		long productEntryId = productEntry.getProductEntryId();
		String name = productEntry.getName();
		Date modifiedDate = productEntry.getModifiedDate();

		String version = StringPool.BLANK;

		SCProductVersion latestProductVersion = productEntry.getLatestVersion();

		if (latestProductVersion != null) {
			version = latestProductVersion.getVersion();
		}

		String type = productEntry.getType();
		String shortDescription = productEntry.getShortDescription();
		String longDescription = productEntry.getLongDescription();
		String pageURL = productEntry.getPageURL();
		String repoGroupId = productEntry.getRepoGroupId();
		String repoArtifactId = productEntry.getRepoArtifactId();

		ExpandoBridge expandoBridge = productEntry.getExpandoBridge();

		try {
			SCIndexer.updateProductEntry(
				companyId, groupId, userId, userName, productEntryId, name,
				modifiedDate, version, type, shortDescription, longDescription,
				pageURL, repoGroupId, repoArtifactId, expandoBridge);
		}
		catch (SearchException se) {
			_log.error("Reindexing " + productEntry.getProductEntryId(), se);
		}
	}

	public void reIndex(String[] ids) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			reIndexProductEntries(companyId);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public Hits search(
			long companyId, long groupId, String keywords, String type,
			int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(
				Field.PORTLET_ID, SCIndexer.PORTLET_ID);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (Validator.isNotNull(keywords)) {
				BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

				searchQuery.addTerm(Field.USER_NAME, keywords);
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);

				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			if (Validator.isNotNull(type)) {
				BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

				searchQuery.addRequiredTerm("type", type);

				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(companyId, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public SCProductEntry updateProductEntry(
			long productEntryId, String name, String type, String tags,
			String shortDescription, String longDescription, String pageURL,
			String author, String repoGroupId, String repoArtifactId,
			long[] licenseIds, List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

		// Product entry

		tags = getTags(tags);
		repoGroupId = repoGroupId.trim().toLowerCase();
		repoArtifactId = repoArtifactId.trim().toLowerCase();
		Date now = new Date();

		validate(
			productEntryId, name, type, shortDescription, pageURL, author,
			repoGroupId, repoArtifactId, licenseIds, thumbnails, fullImages);

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);

		productEntry.setModifiedDate(now);
		productEntry.setName(name);
		productEntry.setType(type);
		productEntry.setTags(tags);
		productEntry.setShortDescription(shortDescription);
		productEntry.setLongDescription(longDescription);
		productEntry.setPageURL(pageURL);
		productEntry.setAuthor(author);
		productEntry.setRepoGroupId(repoGroupId);
		productEntry.setRepoArtifactId(repoArtifactId);

		scProductEntryPersistence.update(productEntry, false);

		// Licenses

		scProductEntryPersistence.setSCLicenses(productEntryId, licenseIds);

		// Product screenshots

		if (thumbnails.size() == 0) {
			scProductScreenshotLocalService.deleteProductScreenshots(
				productEntryId);
		}
		else {
			saveProductScreenshots(productEntry, thumbnails, fullImages);
		}

		// Indexer

		reIndex(productEntry);

		return productEntry;
	}

	protected String getTags(String tags) {
		tags = tags.trim().toLowerCase();

		return StringUtil.merge(StringUtil.split(tags), ", ");
	}

	protected boolean isVersionSupported(
		String version, List<SCFrameworkVersion> frameworkVersions) {

		Version currentVersion = Version.getInstance(version);

		for (SCFrameworkVersion frameworkVersion : frameworkVersions) {
			Version supportedVersion = Version.getInstance(
				frameworkVersion.getName());

			if (supportedVersion.includes(currentVersion)) {
				return true;
			}
		}

		return false;
	}

	protected void populatePluginPackageElement(
			Element el, SCProductEntry productEntry,
			SCProductVersion productVersion, String baseImageURL)
		throws SystemException {

		DocUtil.add(el, "name", productEntry.getName());

		String moduleId = ModuleId.toString(
			productEntry.getRepoGroupId(), productEntry.getRepoArtifactId(),
			productVersion.getVersion(), "war");

		DocUtil.add(el, "module-id", moduleId);

		DocUtil.add(
			el, "modified-date",
			Time.getRFC822(productVersion.getModifiedDate()));

		Element typesEl = el.addElement("types");

		DocUtil.add(typesEl, "type", productEntry.getType());

		Element tagsEl = el.addElement("tags");

		String[] tags = StringUtil.split(productEntry.getTags());

		for (int i = 0; i < tags.length; i++) {
			DocUtil.add(tagsEl, "tag", tags[i]);
		}

		DocUtil.add(
			el, "short-description", productEntry.getShortDescription());

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

		DocUtil.add(el, "author", productEntry.getAuthor());

		Element screenshotsEl = el.addElement("screenshots");

		for (SCProductScreenshot screenshot : productEntry.getScreenshots()) {
			long thumbnailId = screenshot.getThumbnailId();
			long fullImageId = screenshot.getFullImageId();

			Element screenshotEl = screenshotsEl.addElement("screenshot");

			DocUtil.add(
				screenshotEl, "thumbnail-url",
				baseImageURL + "?img_id=" + thumbnailId + "&t=" +
					ImageServletTokenUtil.getToken(thumbnailId));
			DocUtil.add(
				screenshotEl, "large-image-url",
				baseImageURL + "?img_id=" + fullImageId + "&t=" +
					ImageServletTokenUtil.getToken(fullImageId));
		}

		Element licensesEl = el.addElement("licenses");

		for (SCLicense license : productEntry.getLicenses()) {
			Element licenseEl = licensesEl.addElement("license");

			licenseEl.addText(license.getName());
			licenseEl.addAttribute(
				"osi-approved", String.valueOf(license.isOpenSource()));
		}

		Element liferayVersionsEl = el.addElement("liferay-versions");

		for (SCFrameworkVersion frameworkVersion :
				productVersion.getFrameworkVersions()) {

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

		Iterator<Object> itr = repoSettings.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String)itr.next();

			Element settingEl = el.addElement("setting");

			settingEl.addAttribute("name", key);
			settingEl.addAttribute("value", repoSettings.getProperty(key));
		}
	}

	protected void reIndexProductEntries(long companyId)
		throws SystemException {

		int count = scProductEntryPersistence.countByCompanyId(companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reIndexProductEntries(companyId, start, end);
		}
	}

	protected void reIndexProductEntries(long companyId, int start, int end)
		throws SystemException {

		List<SCProductEntry> productEntries =
			scProductEntryPersistence.findByCompanyId(companyId, start, end);

		for (SCProductEntry productEntry : productEntries) {
			reIndex(productEntry);
		}
	}

	protected void saveProductScreenshots(
			SCProductEntry productEntry, List<byte[]> thumbnails,
			List<byte[]> fullImages)
		throws PortalException, SystemException {

		long productEntryId = productEntry.getProductEntryId();

		List<SCProductScreenshot> productScreenshots =
			scProductScreenshotPersistence.findByProductEntryId(productEntryId);

		if (thumbnails.size() < productScreenshots.size()) {
			for (int i = thumbnails.size(); i < productScreenshots.size();
					i++) {

				SCProductScreenshot productScreenshot =
					productScreenshots.get(i);

				scProductScreenshotLocalService.deleteProductScreenshot(
					productScreenshot);
			}
		}

		for (int i = 0; i < thumbnails.size(); i++) {
			int priority = i;

			byte[] thumbnail = thumbnails.get(i);
			byte[] fullImage = fullImages.get(i);

			SCProductScreenshot productScreenshot =
				scProductScreenshotPersistence.fetchByP_P(
					productEntryId, priority);

			if (productScreenshot == null) {
				long productScreenshotId = counterLocalService.increment();

				productScreenshot = scProductScreenshotPersistence.create(
					productScreenshotId);

				productScreenshot.setCompanyId(productEntry.getCompanyId());
				productScreenshot.setGroupId(productEntry.getGroupId());
				productScreenshot.setProductEntryId(productEntryId);
				productScreenshot.setThumbnailId(
					counterLocalService.increment());
				productScreenshot.setFullImageId(
					counterLocalService.increment());
				productScreenshot.setPriority(priority);

				scProductScreenshotPersistence.update(productScreenshot, false);
			}

			imageLocalService.updateImage(
				productScreenshot.getThumbnailId(), thumbnail);
			imageLocalService.updateImage(
				productScreenshot.getFullImageId(), fullImage);
		}
	}

	protected void validate(
			long productEntryId, String name, String type,
			String shortDescription, String pageURL, String author,
			String repoGroupId, String repoArtifactId, long[] licenseIds,
			List<byte[]> thumbnails, List<byte[]> fullImages)
		throws PortalException, SystemException {

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

		if (Validator.isNull(author)) {
			throw new ProductEntryAuthorException();
		}

		SCProductEntry productEntry = scProductEntryPersistence.fetchByRG_RA(
			repoGroupId, repoArtifactId);

		if ((productEntry != null) &&
			(productEntry.getProductEntryId() != productEntryId)) {

			throw new DuplicateProductEntryModuleIdException();
		}

		if (licenseIds.length == 0) {
			throw new ProductEntryLicenseException();
		}

		if (thumbnails.size() != fullImages.size()) {
			throw new ProductEntryScreenshotsException();
		}
		else {
			Iterator<byte[]> itr = thumbnails.iterator();

			while (itr.hasNext()) {
				if (itr.next() == null) {
					throw new ProductEntryScreenshotsException();
				}
			}

			itr = fullImages.iterator();

			while (itr.hasNext()) {
				if (itr.next() == null) {
					throw new ProductEntryScreenshotsException();
				}
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(SCProductEntryLocalServiceImpl.class);

}