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
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.softwarecatalog.NoSuchProductEntryException;
import com.liferay.portlet.softwarecatalog.ProductVersionChangeLogException;
import com.liferay.portlet.softwarecatalog.ProductVersionDownloadURLException;
import com.liferay.portlet.softwarecatalog.ProductVersionFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.ProductVersionNameException;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCProductVersionLocalServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductEntryUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SCProductVersionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductVersionLocalServiceImpl
	extends SCProductVersionLocalServiceBaseImpl {

	public SCProductVersion addProductVersion(
			long userId, long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProductVersion(
			userId, productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public SCProductVersion addProductVersion(
			long userId, long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProductVersion(
			userId, productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds, null,
			null, communityPermissions, guestPermissions);
	}

	public SCProductVersion addProductVersion(
			long userId, long productEntryId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		validate(
			version, changeLog, downloadPageURL, directDownloadURL,
			frameworkVersionIds);

		// Product version

		User user = UserUtil.findByPrimaryKey(userId);
		SCProductEntry productEntry = getProductEntry(
			user.getCompanyId(), productEntryId);
		productEntryId = productEntry.getProductEntryId();
		Date now = new Date();

		long productVersionId = CounterLocalServiceUtil.increment(
			SCProductVersion.class.getName());

		SCProductVersion productVersion = SCProductVersionUtil.create(
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

		SCProductVersionUtil.update(productVersion);

		SCProductEntryUtil.update(productEntry);

		// Framework versions

		SCProductVersionUtil.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		productEntry.setModifiedDate(now);

		return productVersion;
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SCProductVersion productVersion =
			SCProductVersionUtil.findByPrimaryKey(productVersionId);

		deleteProductVersion(productVersion);
	}

	public void deleteProductVersion(SCProductVersion productVersion)
		throws PortalException, SystemException {

		// Product version

		SCProductVersionUtil.remove(productVersion.getPrimaryKey());
	}

	public void deleteProductVersions(long productEntryId)
		throws PortalException, SystemException {

		Iterator itr = SCProductVersionUtil.findByProductEntryId(
			productEntryId).iterator();

		while (itr.hasNext()) {
			SCProductVersion productVersion = (SCProductVersion)itr.next();

			deleteProductVersion(productVersion);
		}
	}

	public SCProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		return SCProductVersionUtil.findByPrimaryKey(productVersionId);
	}

	public List getProductVersions(long productEntryId, int begin, int end)
		throws SystemException {

		return SCProductVersionUtil.findByProductEntryId(
			productEntryId, begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws SystemException {

		return SCProductVersionUtil.countByProductEntryId(productEntryId);
	}

	public SCProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		validate(
			version, changeLog, downloadPageURL, directDownloadURL,
			frameworkVersionIds);

		// Product version

		Date now = new Date();

		SCProductVersion productVersion =
			SCProductVersionUtil.findByPrimaryKey(productVersionId);

		productVersion.setModifiedDate(now);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		SCProductVersionUtil.update(productVersion);

		// Framework versions

		SCProductVersionUtil.setSCFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		SCProductEntry productEntry = SCProductEntryUtil.findByPrimaryKey(
			productVersion.getProductEntryId());

		productEntry.setModifiedDate(now);

		SCProductEntryUtil.update(productEntry);

		return productVersion;
	}

	protected SCProductEntry getProductEntry(
			long companyId, long productEntryId)
		throws PortalException, SystemException {

		// Ensure product entry exists and belongs to the proper company

		SCProductEntry productEntry =
			SCProductEntryUtil.findByPrimaryKey(productEntryId);

		if (companyId != productEntry.getCompanyId()) {
			throw new NoSuchProductEntryException();
		}

		return productEntry;
	}

	private void validate(
		String version, String changeLog, String downloadPageURL,
		String directDownloadURL, long[] frameworkVersionIds)
		throws PortalException {

		if (Validator.isNull(version)) {
			throw new ProductVersionNameException();
		}
		else if (Validator.isNull(changeLog)) {
			throw new ProductVersionChangeLogException();
		}
		else if (frameworkVersionIds.length == 0) {
			throw new ProductVersionFrameworkVersionException();
		}
		else if (Validator.isNull(downloadPageURL) &&
			Validator.isNull(directDownloadURL)) {
			throw new ProductVersionDownloadURLException();
		}
	}

}