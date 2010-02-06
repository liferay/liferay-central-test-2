/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetTagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetTagLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagLocalService
 * @generated
 */
public class AssetTagLocalServiceWrapper implements AssetTagLocalService {
	public AssetTagLocalServiceWrapper(
		AssetTagLocalService assetTagLocalService) {
		_assetTagLocalService = assetTagLocalService;
	}

	public com.liferay.portlet.asset.model.AssetTag addAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.addAssetTag(assetTag);
	}

	public com.liferay.portlet.asset.model.AssetTag createAssetTag(long tagId) {
		return _assetTagLocalService.createAssetTag(tagId);
	}

	public void deleteAssetTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.deleteAssetTag(tagId);
	}

	public void deleteAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		_assetTagLocalService.deleteAssetTag(assetTag);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.asset.model.AssetTag getAssetTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.getAssetTag(tagId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getAssetTags(start, end);
	}

	public int getAssetTagsCount() throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getAssetTagsCount();
	}

	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.updateAssetTag(assetTag);
	}

	public com.liferay.portlet.asset.model.AssetTag updateAssetTag(
		com.liferay.portlet.asset.model.AssetTag assetTag, boolean merge)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.updateAssetTag(assetTag, merge);
	}

	public com.liferay.portlet.asset.model.AssetTag addTag(long userId,
		java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.addTag(userId, name, tagProperties,
			serviceContext);
	}

	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.addTagResources(tag, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addTagResources(com.liferay.portlet.asset.model.AssetTag tag,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.addTagResources(tag, communityPermissions,
			guestPermissions);
	}

	public void checkTags(long userId, long groupId, java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.checkTags(userId, groupId, names);
	}

	public com.liferay.portlet.asset.model.AssetTag decrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.decrementAssetCount(tagId, classNameId);
	}

	public void deleteTag(com.liferay.portlet.asset.model.AssetTag tag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.deleteTag(tag);
	}

	public void deleteTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.deleteTag(tagId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getEntryTags(
		long entryId) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getEntryTags(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getGroupTags(
		long groupId) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getGroupTags(groupId);
	}

	public com.liferay.portlet.asset.model.AssetTag getTag(long tagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.getTag(tagId);
	}

	public com.liferay.portlet.asset.model.AssetTag getTag(long groupId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.getTag(groupId, name);
	}

	public long[] getTagIds(long groupId, java.lang.String[] names)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.getTagIds(groupId, names);
	}

	public java.lang.String[] getTagNames()
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTagNames();
	}

	public java.lang.String[] getTagNames(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTagNames(classNameId, classPK);
	}

	public java.lang.String[] getTagNames(java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTagNames(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags()
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTags();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTags(classNameId, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTags(groupId, classNameId, name, start,
			end);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetTag> getTags(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTags(className, classPK);
	}

	public int getTagsSize(long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return _assetTagLocalService.getTagsSize(groupId, classNameId, name);
	}

	public boolean hasTag(long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.hasTag(groupId, name);
	}

	public com.liferay.portlet.asset.model.AssetTag incrementAssetCount(
		long tagId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.incrementAssetCount(tagId, classNameId);
	}

	public void mergeTags(long fromTagId, long toTagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetTagLocalService.mergeTags(fromTagId, toTagId);
	}

	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] tagProperties, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetTagLocalService.search(groupId, name, tagProperties,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetTag updateTag(long userId,
		long tagId, java.lang.String name, java.lang.String[] tagProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetTagLocalService.updateTag(userId, tagId, name,
			tagProperties, serviceContext);
	}

	public AssetTagLocalService getWrappedAssetTagLocalService() {
		return _assetTagLocalService;
	}

	private AssetTagLocalService _assetTagLocalService;
}