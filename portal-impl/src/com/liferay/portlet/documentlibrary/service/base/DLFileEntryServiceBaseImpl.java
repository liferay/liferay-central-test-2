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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.service.DLService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.LockLocalService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.WebDAVPropsLocalService;
import com.liferay.portal.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.LockPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence;

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
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.RatingsEntryLocalService;
import com.liferay.portlet.ratings.service.RatingsEntryService;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

/**
 * <a href="DLFileEntryServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class DLFileEntryServiceBaseImpl extends PrincipalBean
	implements DLFileEntryService {
	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	public DLFileRankLocalService getDLFileRankLocalService() {
		return dlFileRankLocalService;
	}

	public void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {
		this.dlFileRankLocalService = dlFileRankLocalService;
	}

	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	public DLFileShortcutLocalService getDLFileShortcutLocalService() {
		return dlFileShortcutLocalService;
	}

	public void setDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	public DLFileShortcutService getDLFileShortcutService() {
		return dlFileShortcutService;
	}

	public void setDLFileShortcutService(
		DLFileShortcutService dlFileShortcutService) {
		this.dlFileShortcutService = dlFileShortcutService;
	}

	public DLFileShortcutPersistence getDLFileShortcutPersistence() {
		return dlFileShortcutPersistence;
	}

	public void setDLFileShortcutPersistence(
		DLFileShortcutPersistence dlFileShortcutPersistence) {
		this.dlFileShortcutPersistence = dlFileShortcutPersistence;
	}

	public DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	public void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public DLFileVersionService getDLFileVersionService() {
		return dlFileVersionService;
	}

	public void setDLFileVersionService(
		DLFileVersionService dlFileVersionService) {
		this.dlFileVersionService = dlFileVersionService;
	}

	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
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

	public DLLocalService getDLLocalService() {
		return dlLocalService;
	}

	public void setDLLocalService(DLLocalService dlLocalService) {
		this.dlLocalService = dlLocalService;
	}

	public DLService getDLService() {
		return dlService;
	}

	public void setDLService(DLService dlService) {
		this.dlService = dlService;
	}

	public LockLocalService getLockLocalService() {
		return lockLocalService;
	}

	public void setLockLocalService(LockLocalService lockLocalService) {
		this.lockLocalService = lockLocalService;
	}

	public LockPersistence getLockPersistence() {
		return lockPersistence;
	}

	public void setLockPersistence(LockPersistence lockPersistence) {
		this.lockPersistence = lockPersistence;
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

	public WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	public void setWebDAVPropsLocalService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	public WorkflowInstanceLinkLocalService getWorkflowInstanceLinkLocalService() {
		return workflowInstanceLinkLocalService;
	}

	public void setWorkflowInstanceLinkLocalService(
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		this.workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	public WorkflowInstanceLinkPersistence getWorkflowInstanceLinkPersistence() {
		return workflowInstanceLinkPersistence;
	}

	public void setWorkflowInstanceLinkPersistence(
		WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence) {
		this.workflowInstanceLinkPersistence = workflowInstanceLinkPersistence;
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

	public MBDiscussionLocalService getMBDiscussionLocalService() {
		return mbDiscussionLocalService;
	}

	public void setMBDiscussionLocalService(
		MBDiscussionLocalService mbDiscussionLocalService) {
		this.mbDiscussionLocalService = mbDiscussionLocalService;
	}

	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
	}

	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	public MBMessageService getMBMessageService() {
		return mbMessageService;
	}

	public void setMBMessageService(MBMessageService mbMessageService) {
		this.mbMessageService = mbMessageService;
	}

	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
	}

	public RatingsEntryLocalService getRatingsEntryLocalService() {
		return ratingsEntryLocalService;
	}

	public void setRatingsEntryLocalService(
		RatingsEntryLocalService ratingsEntryLocalService) {
		this.ratingsEntryLocalService = ratingsEntryLocalService;
	}

	public RatingsEntryService getRatingsEntryService() {
		return ratingsEntryService;
	}

	public void setRatingsEntryService(RatingsEntryService ratingsEntryService) {
		this.ratingsEntryService = ratingsEntryService;
	}

	public RatingsEntryPersistence getRatingsEntryPersistence() {
		return ratingsEntryPersistence;
	}

	public void setRatingsEntryPersistence(
		RatingsEntryPersistence ratingsEntryPersistence) {
		this.ratingsEntryPersistence = ratingsEntryPersistence;
	}

	public RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	public void setRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
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

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService")
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryService")
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder")
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileRankLocalService")
	protected DLFileRankLocalService dlFileRankLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence")
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService")
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutService")
	protected DLFileShortcutService dlFileShortcutService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence")
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService")
	protected DLFileVersionLocalService dlFileVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileVersionService")
	protected DLFileVersionService dlFileVersionService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence")
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderLocalService")
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderService")
	protected DLFolderService dlFolderService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder")
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.documentlibrary.service.DLLocalService")
	protected DLLocalService dlLocalService;
	@BeanReference(name = "com.liferay.documentlibrary.service.DLService")
	protected DLService dlService;
	@BeanReference(name = "com.liferay.portal.service.LockLocalService")
	protected LockLocalService lockLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected LockPersistence lockPersistence;
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
	@BeanReference(name = "com.liferay.portal.service.WebDAVPropsLocalService")
	protected WebDAVPropsLocalService webDAVPropsLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.WorkflowInstanceLinkLocalService")
	protected WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
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
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBDiscussionLocalService")
	protected MBDiscussionLocalService mbDiscussionLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence")
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageLocalService")
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.MBMessageService")
	protected MBMessageService mbMessageService;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence")
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFinder")
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryLocalService")
	protected RatingsEntryLocalService ratingsEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsEntryService")
	protected RatingsEntryService ratingsEntryService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsEntryPersistence")
	protected RatingsEntryPersistence ratingsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.ratings.service.RatingsStatsLocalService")
	protected RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence")
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialActivityLocalService")
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityFinder")
	protected SocialActivityFinder socialActivityFinder;
}