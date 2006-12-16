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

package com.liferay.portlet.softwarerepository.service;

/**
 * <a href="SRProductEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryServiceUtil {
	public static com.liferay.portlet.softwarerepository.model.SRProductEntry addProductEntry(
		java.lang.String plid, java.lang.String repoArtifactId,
		java.lang.String repoGroupId, java.lang.String name,
		java.lang.String type, long[] licenseIds,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.addProductEntry(plid, repoArtifactId,
			repoGroupId, name, type, licenseIds, shortDescription,
			longDescription, pageURL);
	}

	public static void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();
		srProductEntryService.deleteProductEntry(productEntryId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.getProductEntry(productEntryId);
	}

	public static java.util.List getProductEntries(java.lang.String groupId,
		int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.getProductEntries(groupId, begin, end);
	}

	public static java.util.List getProductEntriesByUserId(
		java.lang.String groupId, java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.getProductEntriesByUserId(groupId, userId,
			begin, end);
	}

	public static int getProductEntriesCountByUserId(java.lang.String groupId,
		java.lang.String userId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.getProductEntriesCountByUserId(groupId,
			userId);
	}

	public static int getProductEntriesCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.getProductEntriesCount(groupId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry updateProductEntry(
		long productEntryId, java.lang.String repoArtifactId,
		java.lang.String repoGroupId, java.lang.String name, long[] licenseIds,
		java.lang.String shortDescription, java.lang.String longDescription,
		java.lang.String pageURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException, java.rmi.RemoteException {
		SRProductEntryService srProductEntryService = SRProductEntryServiceFactory.getService();

		return srProductEntryService.updateProductEntry(productEntryId,
			repoArtifactId, repoGroupId, name, licenseIds, shortDescription,
			longDescription, pageURL);
	}
}