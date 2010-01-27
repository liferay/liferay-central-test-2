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

package com.liferay.portlet.softwarecatalog.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SCProductEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SCProductEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductEntryLocalService
 * @generated
 */
public class SCProductEntryLocalServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		return getService().addSCProductEntry(scProductEntry);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry createSCProductEntry(
		long productEntryId) {
		return getService().createSCProductEntry(productEntryId);
	}

	public static void deleteSCProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteSCProductEntry(productEntryId);
	}

	public static void deleteSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		getService().deleteSCProductEntry(scProductEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getSCProductEntry(productEntryId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getSCProductEntries(start, end);
	}

	public static int getSCProductEntriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getSCProductEntriesCount();
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		return getService().updateSCProductEntry(scProductEntry);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateSCProductEntry(scProductEntry, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addProductEntry(userId, name, type, tags, shortDescription,
			longDescription, pageURL, author, repoGroupId, repoArtifactId,
			licenseIds, thumbnails, fullImages, serviceContext);
	}

	public static void addProductEntryResources(long productEntryId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProductEntryResources(productEntryId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addProductEntryResources(long productEntryId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProductEntryResources(productEntryId, communityPermissions,
			guestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProductEntryResources(productEntry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProductEntryResources(productEntry, communityPermissions,
			guestPermissions);
	}

	public static void deleteProductEntries(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProductEntries(groupId);
	}

	public static void deleteProductEntry(long productEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProductEntry(productEntryId);
	}

	public static void deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProductEntry(productEntry);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntries(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntries(groupId, userId, start, end, obc);
	}

	public static int getProductEntriesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntriesCount(groupId);
	}

	public static int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getProductEntriesCount(groupId, userId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProductEntry(productEntryId);
	}

	public static java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getRepositoryXML(groupId, baseImageURL, oldestDate,
			maxNumOfVersions, repoSettings);
	}

	public static java.lang.String getRepositoryXML(long groupId,
		java.lang.String version, java.lang.String baseImageURL,
		java.util.Date oldestDate, int maxNumOfVersions,
		java.util.Properties repoSettings)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getRepositoryXML(groupId, version, baseImageURL,
			oldestDate, maxNumOfVersions, repoSettings);
	}

	public static void reindex(long productEntryId)
		throws com.liferay.portal.SystemException {
		getService().reindex(productEntryId);
	}

	public static void reindex(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.SystemException {
		getService().reindex(productEntry);
	}

	public static void reindex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		getService().reindex(ids);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, java.lang.String keywords, java.lang.String type,
		int start, int end) throws com.liferay.portal.SystemException {
		return getService()
				   .search(companyId, groupId, keywords, type, start, end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateProductEntry(productEntryId, name, type, tags,
			shortDescription, longDescription, pageURL, author, repoGroupId,
			repoArtifactId, licenseIds, thumbnails, fullImages);
	}

	public static SCProductEntryLocalService getService() {
		if (_service == null) {
			_service = (SCProductEntryLocalService)PortalBeanLocatorUtil.locate(SCProductEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SCProductEntryLocalService service) {
		_service = service;
	}

	private static SCProductEntryLocalService _service;
}