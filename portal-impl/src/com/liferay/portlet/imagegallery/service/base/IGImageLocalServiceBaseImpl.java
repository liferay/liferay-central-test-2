/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.imagegallery.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetCategoryService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGFolderService;
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.IGImageService;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

import java.util.List;

/**
 * <a href="IGImageLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class IGImageLocalServiceBaseImpl implements IGImageLocalService {
	public IGImage addIGImage(IGImage igImage) throws SystemException {
		igImage.setNew(true);

		return igImagePersistence.update(igImage, false);
	}

	public IGImage createIGImage(long imageId) {
		return igImagePersistence.create(imageId);
	}

	public void deleteIGImage(long imageId)
		throws PortalException, SystemException {
		igImagePersistence.remove(imageId);
	}

	public void deleteIGImage(IGImage igImage) throws SystemException {
		igImagePersistence.remove(igImage);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return igImagePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return igImagePersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public IGImage getIGImage(long imageId)
		throws PortalException, SystemException {
		return igImagePersistence.findByPrimaryKey(imageId);
	}

	public List<IGImage> getIGImages(int start, int end)
		throws SystemException {
		return igImagePersistence.findAll(start, end);
	}

	public int getIGImagesCount() throws SystemException {
		return igImagePersistence.countAll();
	}

	public IGImage updateIGImage(IGImage igImage) throws SystemException {
		igImage.setNew(false);

		return igImagePersistence.update(igImage, true);
	}

	public IGImage updateIGImage(IGImage igImage, boolean merge)
		throws SystemException {
		igImage.setNew(false);

		return igImagePersistence.update(igImage, merge);
	}

	public IGFolderLocalService getIGFolderLocalService() {
		return igFolderLocalService;
	}

	public void setIGFolderLocalService(
		IGFolderLocalService igFolderLocalService) {
		this.igFolderLocalService = igFolderLocalService;
	}

	public IGFolderService getIGFolderService() {
		return igFolderService;
	}

	public void setIGFolderService(IGFolderService igFolderService) {
		this.igFolderService = igFolderService;
	}

	public IGFolderPersistence getIGFolderPersistence() {
		return igFolderPersistence;
	}

	public void setIGFolderPersistence(IGFolderPersistence igFolderPersistence) {
		this.igFolderPersistence = igFolderPersistence;
	}

	public IGImageLocalService getIGImageLocalService() {
		return igImageLocalService;
	}

	public void setIGImageLocalService(IGImageLocalService igImageLocalService) {
		this.igImageLocalService = igImageLocalService;
	}

	public IGImageService getIGImageService() {
		return igImageService;
	}

	public void setIGImageService(IGImageService igImageService) {
		this.igImageService = igImageService;
	}

	public IGImagePersistence getIGImagePersistence() {
		return igImagePersistence;
	}

	public void setIGImagePersistence(IGImagePersistence igImagePersistence) {
		this.igImagePersistence = igImagePersistence;
	}

	public IGImageFinder getIGImageFinder() {
		return igImageFinder;
	}

	public void setIGImageFinder(IGImageFinder igImageFinder) {
		this.igImageFinder = igImageFinder;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public ImageLocalService getImageLocalService() {
		return imageLocalService;
	}

	public void setImageLocalService(ImageLocalService imageLocalService) {
		this.imageLocalService = imageLocalService;
	}

	public ImagePersistence getImagePersistence() {
		return imagePersistence;
	}

	public void setImagePersistence(ImagePersistence imagePersistence) {
		this.imagePersistence = imagePersistence;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	public void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	public AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	public void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	public AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	public AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	public void setAssetEntryService(AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	public AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	public void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	public AssetTagService getAssetTagService() {
		return assetTagService;
	}

	public void setAssetTagService(AssetTagService assetTagService) {
		this.assetTagService = assetTagService;
	}

	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
	}

	public ExpandoValueLocalService getExpandoValueLocalService() {
		return expandoValueLocalService;
	}

	public void setExpandoValueLocalService(
		ExpandoValueLocalService expandoValueLocalService) {
		this.expandoValueLocalService = expandoValueLocalService;
	}

	public ExpandoValueService getExpandoValueService() {
		return expandoValueService;
	}

	public void setExpandoValueService(ExpandoValueService expandoValueService) {
		this.expandoValueService = expandoValueService;
	}

	public ExpandoValuePersistence getExpandoValuePersistence() {
		return expandoValuePersistence;
	}

	public void setExpandoValuePersistence(
		ExpandoValuePersistence expandoValuePersistence) {
		this.expandoValuePersistence = expandoValuePersistence;
	}

	public SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	public void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		this.socialActivityLocalService = socialActivityLocalService;
	}

	public SocialActivityPersistence getSocialActivityPersistence() {
		return socialActivityPersistence;
	}

	public void setSocialActivityPersistence(
		SocialActivityPersistence socialActivityPersistence) {
		this.socialActivityPersistence = socialActivityPersistence;
	}

	public SocialActivityFinder getSocialActivityFinder() {
		return socialActivityFinder;
	}

	public void setSocialActivityFinder(
		SocialActivityFinder socialActivityFinder) {
		this.socialActivityFinder = socialActivityFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.imagegallery.service.IGFolderLocalService")
	protected IGFolderLocalService igFolderLocalService;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.IGFolderService")
	protected IGFolderService igFolderService;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence")
	protected IGFolderPersistence igFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.IGImageLocalService")
	protected IGImageLocalService igImageLocalService;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.IGImageService")
	protected IGImageService igImageService;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence")
	protected IGImagePersistence igImagePersistence;
	@BeanReference(name = "com.liferay.portlet.imagegallery.service.persistence.IGImageFinder")
	protected IGImageFinder igImageFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.ImageLocalService")
	protected ImageLocalService imageLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryLocalService")
	protected AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetCategoryService")
	protected AssetCategoryService assetCategoryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence")
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetCategoryFinder")
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryLocalService")
	protected AssetEntryLocalService assetEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryService")
	protected AssetEntryService assetEntryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryFinder")
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagLocalService")
	protected AssetTagLocalService assetTagLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetTagService")
	protected AssetTagService assetTagService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagPersistence")
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetTagFinder")
	protected AssetTagFinder assetTagFinder;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueLocalService")
	protected ExpandoValueLocalService expandoValueLocalService;
	@BeanReference(name = "com.liferay.portlet.expando.service.ExpandoValueService")
	protected ExpandoValueService expandoValueService;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence")
	protected ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialActivityLocalService")
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityFinder")
	protected SocialActivityFinder socialActivityFinder;
}