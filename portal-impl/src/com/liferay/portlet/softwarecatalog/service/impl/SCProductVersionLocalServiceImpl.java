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
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portlet.softwarecatalog.DuplicateProductVersionDirectDownloadURLException;
import com.liferay.portlet.softwarecatalog.ProductVersionChangeLogException;
import com.liferay.portlet.softwarecatalog.ProductVersionDownloadURLException;
import com.liferay.portlet.softwarecatalog.ProductVersionFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.ProductVersionNameException;
import com.liferay.portlet.softwarecatalog.UnavailableProductVersionDirectDownloadURLException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCProductVersionLocalServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.util.SCIndexer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * <a href="SCProductVersionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SCProductVersionLocalServiceImpl
	extends SCProductVersionLocalServiceBaseImpl {

	public SCProductVersion addProductVersion(
			long userId, long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, boolean repoStoreArtifact,
			long[] frameworkVersionIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Product version

		User user = userPersistence.findByPrimaryKey(userId);
		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(productEntryId);
		directDownloadURL = directDownloadURL.trim().toLowerCase();
		Date now = new Date();

		validate(
			0, version, changeLog, downloadPageURL, directDownloadURL,
			testDirectDownloadURL, frameworkVersionIds);

		long productVersionId = counterLocalService.increment();

		SCProductVersion productVersion = scProductVersionPersistence.create(
			productVersionId);

		productVersion.setCompanyId(user.getCompanyId());
		productVersion.setUserId(user.getUserId());
		productVersion.setUserName(user.getFullName());
		productVersion.setCreateDate(now);
		productVersion.setModifiedDate(now);
		productVersion.setProductEntryId(productEntryId);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		scProductVersionPersistence.update(productVersion, false);

		// Framework versions

		scProductVersionPersistence.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		productEntry.setModifiedDate(now);

		scProductEntryPersistence.update(productEntry, false);

		// Indexer

		try {
			SCIndexer.updateProductEntry(
				productEntry.getCompanyId(), productEntry.getGroupId(),
				productEntry.getUserId(), productEntry.getUserName(),
				productEntry.getProductEntryId(), productEntry.getName(), now,
				productVersion.getVersion(), productEntry.getType(),
				productEntry.getShortDescription(),
				productEntry.getLongDescription(), productEntry.getPageURL(),
				productEntry.getRepoGroupId(),
				productEntry.getRepoArtifactId(),
				productEntry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + productEntry.getProductEntryId(), se);
		}

		return productVersion;
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SCProductVersion productVersion =
			scProductVersionPersistence.findByPrimaryKey(productVersionId);

		deleteProductVersion(productVersion);
	}

	public void deleteProductVersion(SCProductVersion productVersion)
		throws SystemException {

		scProductVersionPersistence.remove(productVersion);
	}

	public void deleteProductVersions(long productEntryId)
		throws SystemException {

		List<SCProductVersion> productVersions =
			scProductVersionPersistence.findByProductEntryId(productEntryId);

		for (SCProductVersion productVersion : productVersions) {
			deleteProductVersion(productVersion);
		}
	}

	public SCProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		return scProductVersionPersistence.findByPrimaryKey(productVersionId);
	}

	public SCProductVersion getProductVersionByDirectDownloadURL(
			String directDownloadURL)
		throws PortalException, SystemException {

		return scProductVersionPersistence.findByDirectDownloadURL(
			directDownloadURL);
	}

	public List<SCProductVersion> getProductVersions(
			long productEntryId, int start, int end)
		throws SystemException {

		return scProductVersionPersistence.findByProductEntryId(
			productEntryId, start, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws SystemException {

		return scProductVersionPersistence.countByProductEntryId(
			productEntryId);
	}

	public SCProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, boolean repoStoreArtifact,
			long[] frameworkVersionIds)
		throws PortalException, SystemException {

		// Product version

		directDownloadURL = directDownloadURL.trim().toLowerCase();
		Date now = new Date();

		validate(
			productVersionId, version, changeLog, downloadPageURL,
			directDownloadURL, testDirectDownloadURL, frameworkVersionIds);

		SCProductVersion productVersion =
			scProductVersionPersistence.findByPrimaryKey(productVersionId);

		productVersion.setModifiedDate(now);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		scProductVersionPersistence.update(productVersion, false);

		// Framework versions

		scProductVersionPersistence.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		SCProductEntry productEntry =
			scProductEntryPersistence.findByPrimaryKey(
				productVersion.getProductEntryId());

		productEntry.setModifiedDate(now);

		scProductEntryPersistence.update(productEntry, false);

		// Indexer

		try {
			SCIndexer.updateProductEntry(
				productEntry.getCompanyId(), productEntry.getGroupId(),
				productEntry.getUserId(), productEntry.getUserName(),
				productEntry.getProductEntryId(), productEntry.getName(), now,
				productVersion.getVersion(), productEntry.getType(),
				productEntry.getShortDescription(),
				productEntry.getLongDescription(), productEntry.getPageURL(),
				productEntry.getRepoGroupId(),
				productEntry.getRepoArtifactId(),
				productEntry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + productEntry.getProductEntryId(), se);
		}

		return productVersion;
	}

	protected void testDirectDownloadURL(String directDownloadURL)
		throws PortalException {

		try {
			HttpImpl httpImpl = (HttpImpl)HttpUtil.getHttp();

			HostConfiguration hostConfig = httpImpl.getHostConfig(
				directDownloadURL);

			HttpClient client = httpImpl.getClient(hostConfig);

			GetMethod getFileMethod = new GetMethod(directDownloadURL);

			int responseCode = client.executeMethod(
				hostConfig, getFileMethod);

			if (responseCode != HttpServletResponse.SC_OK) {
				throw new UnavailableProductVersionDirectDownloadURLException();
			}
		}
		catch (Exception e) {
			throw new UnavailableProductVersionDirectDownloadURLException();
		}
	}

	protected void validate(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean testDirectDownloadURL, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		if (Validator.isNull(version)) {
			throw new ProductVersionNameException();
		}
		else if (Validator.isNull(changeLog)) {
			throw new ProductVersionChangeLogException();
		}
		else if (Validator.isNull(downloadPageURL) &&
				 Validator.isNull(directDownloadURL)) {

			throw new ProductVersionDownloadURLException();
		}
		else if (Validator.isNotNull(directDownloadURL)) {
			SCProductVersion productVersion =
				scProductVersionPersistence.fetchByDirectDownloadURL(
					directDownloadURL);

			if ((productVersion != null) &&
				(productVersion.getProductVersionId() != productVersionId)) {

				throw new DuplicateProductVersionDirectDownloadURLException();
			}

			if (testDirectDownloadURL) {
				testDirectDownloadURL(directDownloadURL);
			}
		}
		else if (frameworkVersionIds.length == 0) {
			throw new ProductVersionFrameworkVersionException();
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(SCProductVersionLocalServiceImpl.class);

}