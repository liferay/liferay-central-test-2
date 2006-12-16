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
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.softwarerepository.NoSuchProductVersionException;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.model.SRProductVersion;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceUtil;
import com.liferay.portlet.softwarerepository.service.SRProductVersionLocalService;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductEntryUtil;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductVersionUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="SRProductVersionLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductVersionLocalServiceImpl
	implements SRProductVersionLocalService {
	public SRProductVersion addProductVersion(
			String userId, long productEntryId, String version,
			String changeLog, long[] frameworkVersionIds,
			String downloadPageURL, String directDownloadURL,
			boolean repoStoreArtifact)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		long productVersionId = CounterLocalServiceUtil.increment(
			SRProductVersion.class.getName());

		SRProductEntry productEntry = SRProductEntryLocalServiceUtil.
			getProductEntry(productEntryId);

		SRProductVersion productVersion = SRProductVersionUtil.
			create(productVersionId);

		productVersion.setUserId(user.getUserId());
		productVersion.setCreateDate(now);
		productVersion.setModifiedDate(now);
		productVersion.setProductEntryId(productEntryId);

		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		SRProductVersionUtil.update(productVersion);
		SRProductVersionUtil.setSRFrameworkVersions(
			productEntryId, frameworkVersionIds);

		productEntry.setModifiedDate(now);
		SRProductEntryUtil.update(productEntry);
		return productVersion;
	}

	public void deleteProductVersion(long productVersionId)
		throws PortalException, SystemException {

		SRProductVersion productVersion = SRProductVersionUtil.
			findByPrimaryKey(productVersionId);

		SRProductVersionUtil.remove(productVersion.getProductVersionId());
	}

	public SRProductVersion getProductVersion(long productVersionId)
		throws PortalException, SystemException {

		return SRProductVersionUtil.findByPrimaryKey(productVersionId);
	}

	public List getProductVersions(
			long productEntryId, int begin, int end)
		throws SystemException {

		return SRProductVersionUtil.findByProductEntryId(
			productEntryId, begin, end);
	}

	public int getProductVersionsCount(long productEntryId)
		throws SystemException {

		return SRProductVersionUtil.countByProductEntryId(productEntryId);
	}

	public SRProductVersion updateProductVersion(
		long productVersionId, String version,
		String changeLog, long[] frameworkVersionIds,
		String downloadPageURL, String directDownloadURL,
		boolean repoStoreArtifact)
		throws PortalException, SystemException {

		SRProductVersion productVersion = SRProductVersionUtil.
			findByPrimaryKey(productVersionId);

		SRProductEntry productEntry = SRProductEntryLocalServiceUtil.
			getProductEntry(productVersion.getProductEntryId());

		Date now = new Date();
		productVersion.setModifiedDate(now);

		productVersion.setVersion(version);
		productVersion.setChangeLog(changeLog);
		productVersion.setDownloadPageURL(downloadPageURL);
		productVersion.setDirectDownloadURL(directDownloadURL);
		productVersion.setRepoStoreArtifact(repoStoreArtifact);

		SRProductVersionUtil.update(productVersion);
		SRProductVersionUtil.setSRFrameworkVersions(
			productVersionId, frameworkVersionIds);

		productEntry.setModifiedDate(now);
		SRProductEntryUtil.update(productEntry);
		return productVersion;
	}

	public List getSRFrameworkVersions(long productVersionId)
		throws SystemException, NoSuchProductVersionException {
		return SRProductVersionUtil.getSRFrameworkVersions(productVersionId);
	}

}