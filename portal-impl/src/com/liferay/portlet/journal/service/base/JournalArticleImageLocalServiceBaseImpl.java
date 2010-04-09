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

package com.liferay.portlet.journal.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
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

import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.JournalArticleImageLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleService;
import com.liferay.portlet.journal.service.JournalContentSearchLocalService;
import com.liferay.portlet.journal.service.JournalFeedLocalService;
import com.liferay.portlet.journal.service.JournalFeedService;
import com.liferay.portlet.journal.service.JournalStructureLocalService;
import com.liferay.portlet.journal.service.JournalStructureService;
import com.liferay.portlet.journal.service.JournalTemplateLocalService;
import com.liferay.portlet.journal.service.JournalTemplateService;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinder;
import com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.portlet.journal.service.persistence.JournalFeedFinder;
import com.liferay.portlet.journal.service.persistence.JournalFeedPersistence;
import com.liferay.portlet.journal.service.persistence.JournalStructureFinder;
import com.liferay.portlet.journal.service.persistence.JournalStructurePersistence;
import com.liferay.portlet.journal.service.persistence.JournalTemplateFinder;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence;

import java.util.List;

/**
 * <a href="JournalArticleImageLocalServiceBaseImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class JournalArticleImageLocalServiceBaseImpl
	implements JournalArticleImageLocalService {
	public JournalArticleImage addJournalArticleImage(
		JournalArticleImage journalArticleImage) throws SystemException {
		journalArticleImage.setNew(true);

		return journalArticleImagePersistence.update(journalArticleImage, false);
	}

	public JournalArticleImage createJournalArticleImage(long articleImageId) {
		return journalArticleImagePersistence.create(articleImageId);
	}

	public void deleteJournalArticleImage(long articleImageId)
		throws PortalException, SystemException {
		journalArticleImagePersistence.remove(articleImageId);
	}

	public void deleteJournalArticleImage(
		JournalArticleImage journalArticleImage) throws SystemException {
		journalArticleImagePersistence.remove(journalArticleImage);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return journalArticleImagePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return journalArticleImagePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return journalArticleImagePersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return journalArticleImagePersistence.countWithDynamicQuery(dynamicQuery);
	}

	public JournalArticleImage getJournalArticleImage(long articleImageId)
		throws PortalException, SystemException {
		return journalArticleImagePersistence.findByPrimaryKey(articleImageId);
	}

	public List<JournalArticleImage> getJournalArticleImages(int start, int end)
		throws SystemException {
		return journalArticleImagePersistence.findAll(start, end);
	}

	public int getJournalArticleImagesCount() throws SystemException {
		return journalArticleImagePersistence.countAll();
	}

	public JournalArticleImage updateJournalArticleImage(
		JournalArticleImage journalArticleImage) throws SystemException {
		journalArticleImage.setNew(false);

		return journalArticleImagePersistence.update(journalArticleImage, true);
	}

	public JournalArticleImage updateJournalArticleImage(
		JournalArticleImage journalArticleImage, boolean merge)
		throws SystemException {
		journalArticleImage.setNew(false);

		return journalArticleImagePersistence.update(journalArticleImage, merge);
	}

	public JournalArticleLocalService getJournalArticleLocalService() {
		return journalArticleLocalService;
	}

	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {
		this.journalArticleLocalService = journalArticleLocalService;
	}

	public JournalArticleService getJournalArticleService() {
		return journalArticleService;
	}

	public void setJournalArticleService(
		JournalArticleService journalArticleService) {
		this.journalArticleService = journalArticleService;
	}

	public JournalArticlePersistence getJournalArticlePersistence() {
		return journalArticlePersistence;
	}

	public void setJournalArticlePersistence(
		JournalArticlePersistence journalArticlePersistence) {
		this.journalArticlePersistence = journalArticlePersistence;
	}

	public JournalArticleFinder getJournalArticleFinder() {
		return journalArticleFinder;
	}

	public void setJournalArticleFinder(
		JournalArticleFinder journalArticleFinder) {
		this.journalArticleFinder = journalArticleFinder;
	}

	public JournalArticleImageLocalService getJournalArticleImageLocalService() {
		return journalArticleImageLocalService;
	}

	public void setJournalArticleImageLocalService(
		JournalArticleImageLocalService journalArticleImageLocalService) {
		this.journalArticleImageLocalService = journalArticleImageLocalService;
	}

	public JournalArticleImagePersistence getJournalArticleImagePersistence() {
		return journalArticleImagePersistence;
	}

	public void setJournalArticleImagePersistence(
		JournalArticleImagePersistence journalArticleImagePersistence) {
		this.journalArticleImagePersistence = journalArticleImagePersistence;
	}

	public JournalArticleResourceLocalService getJournalArticleResourceLocalService() {
		return journalArticleResourceLocalService;
	}

	public void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {
		this.journalArticleResourceLocalService = journalArticleResourceLocalService;
	}

	public JournalArticleResourcePersistence getJournalArticleResourcePersistence() {
		return journalArticleResourcePersistence;
	}

	public void setJournalArticleResourcePersistence(
		JournalArticleResourcePersistence journalArticleResourcePersistence) {
		this.journalArticleResourcePersistence = journalArticleResourcePersistence;
	}

	public JournalContentSearchLocalService getJournalContentSearchLocalService() {
		return journalContentSearchLocalService;
	}

	public void setJournalContentSearchLocalService(
		JournalContentSearchLocalService journalContentSearchLocalService) {
		this.journalContentSearchLocalService = journalContentSearchLocalService;
	}

	public JournalContentSearchPersistence getJournalContentSearchPersistence() {
		return journalContentSearchPersistence;
	}

	public void setJournalContentSearchPersistence(
		JournalContentSearchPersistence journalContentSearchPersistence) {
		this.journalContentSearchPersistence = journalContentSearchPersistence;
	}

	public JournalFeedLocalService getJournalFeedLocalService() {
		return journalFeedLocalService;
	}

	public void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {
		this.journalFeedLocalService = journalFeedLocalService;
	}

	public JournalFeedService getJournalFeedService() {
		return journalFeedService;
	}

	public void setJournalFeedService(JournalFeedService journalFeedService) {
		this.journalFeedService = journalFeedService;
	}

	public JournalFeedPersistence getJournalFeedPersistence() {
		return journalFeedPersistence;
	}

	public void setJournalFeedPersistence(
		JournalFeedPersistence journalFeedPersistence) {
		this.journalFeedPersistence = journalFeedPersistence;
	}

	public JournalFeedFinder getJournalFeedFinder() {
		return journalFeedFinder;
	}

	public void setJournalFeedFinder(JournalFeedFinder journalFeedFinder) {
		this.journalFeedFinder = journalFeedFinder;
	}

	public JournalStructureLocalService getJournalStructureLocalService() {
		return journalStructureLocalService;
	}

	public void setJournalStructureLocalService(
		JournalStructureLocalService journalStructureLocalService) {
		this.journalStructureLocalService = journalStructureLocalService;
	}

	public JournalStructureService getJournalStructureService() {
		return journalStructureService;
	}

	public void setJournalStructureService(
		JournalStructureService journalStructureService) {
		this.journalStructureService = journalStructureService;
	}

	public JournalStructurePersistence getJournalStructurePersistence() {
		return journalStructurePersistence;
	}

	public void setJournalStructurePersistence(
		JournalStructurePersistence journalStructurePersistence) {
		this.journalStructurePersistence = journalStructurePersistence;
	}

	public JournalStructureFinder getJournalStructureFinder() {
		return journalStructureFinder;
	}

	public void setJournalStructureFinder(
		JournalStructureFinder journalStructureFinder) {
		this.journalStructureFinder = journalStructureFinder;
	}

	public JournalTemplateLocalService getJournalTemplateLocalService() {
		return journalTemplateLocalService;
	}

	public void setJournalTemplateLocalService(
		JournalTemplateLocalService journalTemplateLocalService) {
		this.journalTemplateLocalService = journalTemplateLocalService;
	}

	public JournalTemplateService getJournalTemplateService() {
		return journalTemplateService;
	}

	public void setJournalTemplateService(
		JournalTemplateService journalTemplateService) {
		this.journalTemplateService = journalTemplateService;
	}

	public JournalTemplatePersistence getJournalTemplatePersistence() {
		return journalTemplatePersistence;
	}

	public void setJournalTemplatePersistence(
		JournalTemplatePersistence journalTemplatePersistence) {
		this.journalTemplatePersistence = journalTemplatePersistence;
	}

	public JournalTemplateFinder getJournalTemplateFinder() {
		return journalTemplateFinder;
	}

	public void setJournalTemplateFinder(
		JournalTemplateFinder journalTemplateFinder) {
		this.journalTemplateFinder = journalTemplateFinder;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
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

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = JournalArticleLocalService.class)
	protected JournalArticleLocalService journalArticleLocalService;
	@BeanReference(type = JournalArticleService.class)
	protected JournalArticleService journalArticleService;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleFinder.class)
	protected JournalArticleFinder journalArticleFinder;
	@BeanReference(type = JournalArticleImageLocalService.class)
	protected JournalArticleImageLocalService journalArticleImageLocalService;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourceLocalService.class)
	protected JournalArticleResourceLocalService journalArticleResourceLocalService;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchLocalService.class)
	protected JournalContentSearchLocalService journalContentSearchLocalService;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedLocalService.class)
	protected JournalFeedLocalService journalFeedLocalService;
	@BeanReference(type = JournalFeedService.class)
	protected JournalFeedService journalFeedService;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalFeedFinder.class)
	protected JournalFeedFinder journalFeedFinder;
	@BeanReference(type = JournalStructureLocalService.class)
	protected JournalStructureLocalService journalStructureLocalService;
	@BeanReference(type = JournalStructureService.class)
	protected JournalStructureService journalStructureService;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalStructureFinder.class)
	protected JournalStructureFinder journalStructureFinder;
	@BeanReference(type = JournalTemplateLocalService.class)
	protected JournalTemplateLocalService journalTemplateLocalService;
	@BeanReference(type = JournalTemplateService.class)
	protected JournalTemplateService journalTemplateService;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = JournalTemplateFinder.class)
	protected JournalTemplateFinder journalTemplateFinder;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ImageLocalService.class)
	protected ImageLocalService imageLocalService;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceFinder.class)
	protected ResourceFinder resourceFinder;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
}