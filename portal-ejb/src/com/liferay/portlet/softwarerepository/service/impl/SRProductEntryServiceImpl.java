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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.softwarerepository.model.SRProductEntry;
import com.liferay.portlet.softwarerepository.service.SRProductEntryLocalServiceUtil;
import com.liferay.portlet.softwarerepository.service.SRProductEntryService;

import java.util.List;

/**
 * <a href="SRProductEntryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryServiceImpl extends PrincipalBean
	implements SRProductEntryService {

	public SRProductEntry addProductEntry(
			String plid, String repoArtifactId, String repoGroupId, String name,
			String type, long[] licenseIds, String shortDescription,
			String longDescription, String pageURL)
		throws PortalException, SystemException {

		return SRProductEntryLocalServiceUtil.addProductEntry(
			getUserId(), plid, repoArtifactId, repoGroupId, name, type,
			licenseIds, shortDescription, longDescription, pageURL);
	}

	public void deleteProductEntry(long productEntryId)
		throws PortalException, SystemException {
	 	SRProductEntryLocalServiceUtil.deleteProductEntry(productEntryId);
	}

	public SRProductEntry getProductEntry(long productEntryId)
		throws PortalException, SystemException {
		return SRProductEntryLocalServiceUtil.getProductEntry(productEntryId);
	}

	public List getProductEntries(String groupId, int begin, int end)
		throws SystemException {
		return SRProductEntryLocalServiceUtil.getProductEntries(
			groupId, begin, end);
	}

	public List getProductEntriesByUserId(
		String groupId, String userId, int begin, int end)
		throws SystemException {
		return SRProductEntryLocalServiceUtil.getProductEntriesByUserId(
			groupId, userId, begin, end);
	}

	public int getProductEntriesCountByUserId(String groupId, String userId)
		throws SystemException {
		return SRProductEntryLocalServiceUtil.getProductEntriesCountByUserId(
			groupId, userId);
	}

	public int getProductEntriesCount(String groupId)
		throws SystemException {
		return SRProductEntryLocalServiceUtil.getProductEntriesCount(groupId);
	}

	public SRProductEntry updateProductEntry(
			long productEntryId, String repoArtifactId, String repoGroupId,
			String name, long[] licenseIds, String shortDescription,
			String longDescription, String pageURL)
		throws PortalException, SystemException {
		return SRProductEntryLocalServiceUtil.updateProductEntry(
			productEntryId, repoArtifactId, repoGroupId, name, licenseIds,
			shortDescription, longDescription, pageURL);
	}

}