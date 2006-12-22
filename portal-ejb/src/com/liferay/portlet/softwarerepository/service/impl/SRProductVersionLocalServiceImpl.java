/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.softwarerepository.NoSuchProductEntryException;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalService;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductEntryUtil;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductVersionUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SRProductVersionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author  Jorge Ferrer
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionLocalServiceImpl
	implements SRProductVersionLocalService {

	public SRProductVersion addProductVersion(
			String userId, long productEntryId, String version,
			String changeLog, String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProductVersion(
			userId, productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public SRProductVersion addProductVersion(
			String userId, long productEntryId, String version,
			String changeLog, String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProductVersion(
			userId, productEntryId, version, changeLog, downloadPageURL,
			directDownloadURL, repoStoreArtifact, frameworkVersionIds, null,
			null, communityPermissions, guestPermissions);
	}

	public SRProductVersion addProductVersion(
			String userId, long productEntryId, String version,
			String changeLog, String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Product version

		User user = UserUtil.findByPrimaryKey(userId);
		SRProductEntry productEntry = getProductEntry(
			user.getCompanyId(), productEntryId);
		productEntryId = productEntry.getProductEntryId();
		Date now = new Date();

		long productVersionId = CounterLocalServiceUtil.increment(
			SRProductVersion.class.getName());

		SRProductVersion productVersion = SRProductVersionUtil.create(
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

		SRProductVersionUtil.update(productVersion);

		SRProductEntryUtil.update(productEntry);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addProductVersionResources(
				productEntry, productVersion,
				addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addProductVersionResources(
				productEntry, productVersion, communityPermissions,
				guestPermissions);
		}

		// Framework versions

		SRProductVersionUtil.setSRFrameworkVersions(
			productEntryId, frameworkVersionIds);

		// Product entry

		productEntry.setModifiedDate(now);

		return productVersion;
	}

	public void addProductVersionResources(
			long productEntryId, long productVersionId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		SRProductEntry productEntry =
			SRProductEntryUtil.findByPrimaryKey(productEntryId);
		SRProductVersion productVersion =
			SRProductVersionUtil.findByPrimaryKey(productVersionId);

		addProductVersionResources(
			productEntry, productVersion, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProductVersionResources(
			SRProductEntry productEntry, SRProductVersion productVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			productVersion.getCompanyId(), productEntry.getGroupId(),
			productVersion.getUserId(), SRProductVersion.class.getName(),
			productVersion.getPrimaryKey(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProductVersionResources(
			long productEntryId, long productVersionId,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		SRProductEntry productEntry =
			SRProductEntryUtil.findByPrimaryKey(productEntryId);
		SRProductVersion productVersion =
			SRProductVersionUtil.findByPrimaryKey(productVersionId);

		addProductVersionResources(
			productEntry, productVersion, communityPermissions,
			guestPermissions);
	}

	public void addProductVersionResources(
			SRProductEntry productEntry, SRProductVersion productVersion,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			productVersion.getCompanyId(), productEntry.getGroupId(),
			productVersion.getUserId(), SRProductVersion.class.getName(),
			productVersion.getPrimaryKey(), communityPermissions,
			guestPermissions);
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SRProductVersion productVersion =
			SRProductVersionUtil.findByPrimaryKey(productVersionId);

		deleteProductVersion(productVersion);
	}

	public void deleteProductVersion(SRProductVersion productVersion)
		throws PortalException, SystemException {

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			productVersion.getCompanyId(), SRProductVersion.class.getName(),
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
			productVersion.getPrimaryKey());

		// Product version

		SRProductVersionUtil.remove(productVersion.getPrimaryKey());
	}

	public void deleteProductVersions(long productEntryId)
		throws PortalException, SystemException {

		Iterator itr = SRProductVersionUtil.findByProductEntryId(
			productEntryId).iterator();

		while (itr.hasNext()) {
			SRProductVersion productVersion = (SRProductVersion)itr.next();

			deleteProductVersion(productVersion);
		}
	}

	public SRProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		return SRProductVersionUtil.findByPrimaryKey(productVersionId);
	}

	public List getProductVersions(long productEntryId, int begin, int end)
		throws SystemException {

		return SRProductVersionUtil.findByProductEntryId(
			productEntryId, begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws SystemException {

		return SRProductVersionUtil.countByProductEntryId(productEntryId);
	}

	public SRProductVersion updateProductVersion(
			long productVersionId, String version, String changeLog,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact, long[] frameworkVersionIds)
		throws PortalException, SystemException {

		// Product version

		Date now = new Date();

		SRProductVersion productVersion =
			SRProductVersionUtil.findByPrimaryKey(productVersionId);

		productVersion.setModifiedDate(now);
		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		SRProductVersionUtil.update(productVersion);

		// Framework versions

		SRProductVersionUtil.setSRFrameworkVersions(
			productVersionId, frameworkVersionIds);

		// Product entry

		SRProductEntry productEntry = SRProductEntryUtil.findByPrimaryKey(
			productVersion.getProductEntryId());

		productEntry.setModifiedDate(now);

		SRProductEntryUtil.update(productEntry);

		return productVersion;
	}

	protected SRProductEntry getProductEntry(
			String companyId, long productEntryId)
		throws PortalException, SystemException {

		// Ensure product entry exists and belongs to the proper company

		SRProductEntry productEntry =
			SRProductEntryUtil.findByPrimaryKey(productEntryId);

		if (!companyId.equals(productEntry.getCompanyId())) {
			throw new NoSuchProductEntryException();
		}

		return productEntry;
	}

}