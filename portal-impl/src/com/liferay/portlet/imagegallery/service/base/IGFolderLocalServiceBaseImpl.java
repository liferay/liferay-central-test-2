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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGFolderService;
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.IGImageService;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryService;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryPersistence;

import java.util.List;

/**
 * <a href="IGFolderLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class IGFolderLocalServiceBaseImpl
	implements IGFolderLocalService {
	public IGFolder addIGFolder(IGFolder igFolder) throws SystemException {
		igFolder.setNew(true);

		return igFolderPersistence.update(igFolder, false);
	}

	public IGFolder createIGFolder(long folderId) {
		return igFolderPersistence.create(folderId);
	}

	public void deleteIGFolder(long folderId)
		throws PortalException, SystemException {
		igFolderPersistence.remove(folderId);
	}

	public void deleteIGFolder(IGFolder igFolder) throws SystemException {
		igFolderPersistence.remove(igFolder);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return igFolderPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return igFolderPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public IGFolder getIGFolder(long folderId)
		throws PortalException, SystemException {
		return igFolderPersistence.findByPrimaryKey(folderId);
	}

	public List<IGFolder> getIGFolders(int start, int end)
		throws SystemException {
		return igFolderPersistence.findAll(start, end);
	}

	public int getIGFoldersCount() throws SystemException {
		return igFolderPersistence.countAll();
	}

	public IGFolder updateIGFolder(IGFolder igFolder) throws SystemException {
		igFolder.setNew(false);

		return igFolderPersistence.update(igFolder, true);
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

	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	public LayoutService getLayoutService() {
		return layoutService;
	}

	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
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

	public TagsEntryLocalService getTagsEntryLocalService() {
		return tagsEntryLocalService;
	}

	public void setTagsEntryLocalService(
		TagsEntryLocalService tagsEntryLocalService) {
		this.tagsEntryLocalService = tagsEntryLocalService;
	}

	public TagsEntryService getTagsEntryService() {
		return tagsEntryService;
	}

	public void setTagsEntryService(TagsEntryService tagsEntryService) {
		this.tagsEntryService = tagsEntryService;
	}

	public TagsEntryPersistence getTagsEntryPersistence() {
		return tagsEntryPersistence;
	}

	public void setTagsEntryPersistence(
		TagsEntryPersistence tagsEntryPersistence) {
		this.tagsEntryPersistence = tagsEntryPersistence;
	}

	public TagsEntryFinder getTagsEntryFinder() {
		return tagsEntryFinder;
	}

	public void setTagsEntryFinder(TagsEntryFinder tagsEntryFinder) {
		this.tagsEntryFinder = tagsEntryFinder;
	}

	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.IGFolderLocalService.impl")
	protected IGFolderLocalService igFolderLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.IGFolderService.impl")
	protected IGFolderService igFolderService;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence.impl")
	protected IGFolderPersistence igFolderPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.IGImageLocalService.impl")
	protected IGImageLocalService igImageLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.IGImageService.impl")
	protected IGImageService igImageService;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence.impl")
	protected IGImagePersistence igImagePersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.imagegallery.service.persistence.IGImageFinder.impl")
	protected IGImageFinder igImageFinder;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.ImageLocalService.impl")
	protected ImageLocalService imageLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.ImagePersistence.impl")
	protected ImagePersistence imagePersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.LayoutLocalService.impl")
	protected LayoutLocalService layoutLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.LayoutService.impl")
	protected LayoutService layoutService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.LayoutPersistence.impl")
	protected LayoutPersistence layoutPersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.LayoutFinder.impl")
	protected LayoutFinder layoutFinder;
	@javax.annotation.Resource(name = "com.liferay.portal.service.ResourceLocalService.impl")
	protected ResourceLocalService resourceLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.ResourceService.impl")
	protected ResourceService resourceService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected ResourcePersistence resourcePersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.ResourceFinder.impl")
	protected ResourceFinder resourceFinder;
	@javax.annotation.Resource(name = "com.liferay.portal.service.UserLocalService.impl")
	protected UserLocalService userLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.UserService.impl")
	protected UserService userService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected UserPersistence userPersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.UserFinder.impl")
	protected UserFinder userFinder;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsEntryLocalService.impl")
	protected TagsEntryLocalService tagsEntryLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsEntryService.impl")
	protected TagsEntryService tagsEntryService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected TagsEntryPersistence tagsEntryPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsEntryFinder.impl")
	protected TagsEntryFinder tagsEntryFinder;
}