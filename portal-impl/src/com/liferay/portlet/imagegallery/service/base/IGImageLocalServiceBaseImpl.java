/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.imagegallery.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
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

import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGFolderService;
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence;
import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetService;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetPersistence;

import java.util.List;

/**
 * <a href="IGImageLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class IGImageLocalServiceBaseImpl implements IGImageLocalService,
	InitializingBean {
	public IGImage addIGImage(IGImage igImage) throws SystemException {
		igImage.setNew(true);

		return igImagePersistence.update(igImage, false);
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

	public TagsAssetLocalService getTagsAssetLocalService() {
		return tagsAssetLocalService;
	}

	public void setTagsAssetLocalService(
		TagsAssetLocalService tagsAssetLocalService) {
		this.tagsAssetLocalService = tagsAssetLocalService;
	}

	public TagsAssetService getTagsAssetService() {
		return tagsAssetService;
	}

	public void setTagsAssetService(TagsAssetService tagsAssetService) {
		this.tagsAssetService = tagsAssetService;
	}

	public TagsAssetPersistence getTagsAssetPersistence() {
		return tagsAssetPersistence;
	}

	public void setTagsAssetPersistence(
		TagsAssetPersistence tagsAssetPersistence) {
		this.tagsAssetPersistence = tagsAssetPersistence;
	}

	public TagsAssetFinder getTagsAssetFinder() {
		return tagsAssetFinder;
	}

	public void setTagsAssetFinder(TagsAssetFinder tagsAssetFinder) {
		this.tagsAssetFinder = tagsAssetFinder;
	}

	public void afterPropertiesSet() {
		if (igFolderLocalService == null) {
			igFolderLocalService = (IGFolderLocalService)PortalBeanLocatorUtil.locate(IGFolderLocalService.class.getName() +
					".impl");
		}

		if (igFolderService == null) {
			igFolderService = (IGFolderService)PortalBeanLocatorUtil.locate(IGFolderService.class.getName() +
					".impl");
		}

		if (igFolderPersistence == null) {
			igFolderPersistence = (IGFolderPersistence)PortalBeanLocatorUtil.locate(IGFolderPersistence.class.getName() +
					".impl");
		}

		if (igImagePersistence == null) {
			igImagePersistence = (IGImagePersistence)PortalBeanLocatorUtil.locate(IGImagePersistence.class.getName() +
					".impl");
		}

		if (igImageFinder == null) {
			igImageFinder = (IGImageFinder)PortalBeanLocatorUtil.locate(IGImageFinder.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}

		if (imageLocalService == null) {
			imageLocalService = (ImageLocalService)PortalBeanLocatorUtil.locate(ImageLocalService.class.getName() +
					".impl");
		}

		if (imagePersistence == null) {
			imagePersistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName() +
					".impl");
		}

		if (resourceLocalService == null) {
			resourceLocalService = (ResourceLocalService)PortalBeanLocatorUtil.locate(ResourceLocalService.class.getName() +
					".impl");
		}

		if (resourceService == null) {
			resourceService = (ResourceService)PortalBeanLocatorUtil.locate(ResourceService.class.getName() +
					".impl");
		}

		if (resourcePersistence == null) {
			resourcePersistence = (ResourcePersistence)PortalBeanLocatorUtil.locate(ResourcePersistence.class.getName() +
					".impl");
		}

		if (resourceFinder == null) {
			resourceFinder = (ResourceFinder)PortalBeanLocatorUtil.locate(ResourceFinder.class.getName() +
					".impl");
		}

		if (userLocalService == null) {
			userLocalService = (UserLocalService)PortalBeanLocatorUtil.locate(UserLocalService.class.getName() +
					".impl");
		}

		if (userService == null) {
			userService = (UserService)PortalBeanLocatorUtil.locate(UserService.class.getName() +
					".impl");
		}

		if (userPersistence == null) {
			userPersistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName() +
					".impl");
		}

		if (userFinder == null) {
			userFinder = (UserFinder)PortalBeanLocatorUtil.locate(UserFinder.class.getName() +
					".impl");
		}

		if (tagsAssetLocalService == null) {
			tagsAssetLocalService = (TagsAssetLocalService)PortalBeanLocatorUtil.locate(TagsAssetLocalService.class.getName() +
					".impl");
		}

		if (tagsAssetService == null) {
			tagsAssetService = (TagsAssetService)PortalBeanLocatorUtil.locate(TagsAssetService.class.getName() +
					".impl");
		}

		if (tagsAssetPersistence == null) {
			tagsAssetPersistence = (TagsAssetPersistence)PortalBeanLocatorUtil.locate(TagsAssetPersistence.class.getName() +
					".impl");
		}

		if (tagsAssetFinder == null) {
			tagsAssetFinder = (TagsAssetFinder)PortalBeanLocatorUtil.locate(TagsAssetFinder.class.getName() +
					".impl");
		}
	}

	protected IGFolderLocalService igFolderLocalService;
	protected IGFolderService igFolderService;
	protected IGFolderPersistence igFolderPersistence;
	protected IGImagePersistence igImagePersistence;
	protected IGImageFinder igImageFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected ImageLocalService imageLocalService;
	protected ImagePersistence imagePersistence;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected TagsAssetLocalService tagsAssetLocalService;
	protected TagsAssetService tagsAssetService;
	protected TagsAssetPersistence tagsAssetPersistence;
	protected TagsAssetFinder tagsAssetFinder;
}