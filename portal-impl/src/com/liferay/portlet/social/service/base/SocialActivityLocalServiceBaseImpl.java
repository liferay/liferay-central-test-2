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

package com.liferay.portlet.social.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalService;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.SocialRelationLocalService;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.social.service.persistence.SocialRelationPersistence;
import com.liferay.portlet.social.service.persistence.SocialRequestPersistence;

import java.util.List;

/**
 * <a href="SocialActivityLocalServiceBaseImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class SocialActivityLocalServiceBaseImpl
	implements SocialActivityLocalService {
	public SocialActivity addSocialActivity(SocialActivity socialActivity)
		throws SystemException {
		socialActivity.setNew(true);

		return socialActivityPersistence.update(socialActivity, false);
	}

	public SocialActivity createSocialActivity(long activityId) {
		return socialActivityPersistence.create(activityId);
	}

	public void deleteSocialActivity(long activityId)
		throws PortalException, SystemException {
		socialActivityPersistence.remove(activityId);
	}

	public void deleteSocialActivity(SocialActivity socialActivity)
		throws SystemException {
		socialActivityPersistence.remove(socialActivity);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return socialActivityPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return socialActivityPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public SocialActivity getSocialActivity(long activityId)
		throws PortalException, SystemException {
		return socialActivityPersistence.findByPrimaryKey(activityId);
	}

	public List<SocialActivity> getSocialActivities(int start, int end)
		throws SystemException {
		return socialActivityPersistence.findAll(start, end);
	}

	public int getSocialActivitiesCount() throws SystemException {
		return socialActivityPersistence.countAll();
	}

	public SocialActivity updateSocialActivity(SocialActivity socialActivity)
		throws SystemException {
		socialActivity.setNew(false);

		return socialActivityPersistence.update(socialActivity, true);
	}

	public SocialActivity updateSocialActivity(SocialActivity socialActivity,
		boolean merge) throws SystemException {
		socialActivity.setNew(false);

		return socialActivityPersistence.update(socialActivity, merge);
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

	public SocialActivityInterpreterLocalService getSocialActivityInterpreterLocalService() {
		return socialActivityInterpreterLocalService;
	}

	public void setSocialActivityInterpreterLocalService(
		SocialActivityInterpreterLocalService socialActivityInterpreterLocalService) {
		this.socialActivityInterpreterLocalService = socialActivityInterpreterLocalService;
	}

	public SocialRelationLocalService getSocialRelationLocalService() {
		return socialRelationLocalService;
	}

	public void setSocialRelationLocalService(
		SocialRelationLocalService socialRelationLocalService) {
		this.socialRelationLocalService = socialRelationLocalService;
	}

	public SocialRelationPersistence getSocialRelationPersistence() {
		return socialRelationPersistence;
	}

	public void setSocialRelationPersistence(
		SocialRelationPersistence socialRelationPersistence) {
		this.socialRelationPersistence = socialRelationPersistence;
	}

	public SocialRequestLocalService getSocialRequestLocalService() {
		return socialRequestLocalService;
	}

	public void setSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {
		this.socialRequestLocalService = socialRequestLocalService;
	}

	public SocialRequestPersistence getSocialRequestPersistence() {
		return socialRequestPersistence;
	}

	public void setSocialRequestPersistence(
		SocialRequestPersistence socialRequestPersistence) {
		this.socialRequestPersistence = socialRequestPersistence;
	}

	public SocialRequestInterpreterLocalService getSocialRequestInterpreterLocalService() {
		return socialRequestInterpreterLocalService;
	}

	public void setSocialRequestInterpreterLocalService(
		SocialRequestInterpreterLocalService socialRequestInterpreterLocalService) {
		this.socialRequestInterpreterLocalService = socialRequestInterpreterLocalService;
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

	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
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

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.social.service.SocialActivityLocalService")
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence")
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityFinder")
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialActivityInterpreterLocalService")
	protected SocialActivityInterpreterLocalService socialActivityInterpreterLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialRelationLocalService")
	protected SocialRelationLocalService socialRelationLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRelationPersistence")
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialRequestLocalService")
	protected SocialRequestLocalService socialRequestLocalService;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialRequestPersistence")
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.SocialRequestInterpreterLocalService")
	protected SocialRequestInterpreterLocalService socialRequestInterpreterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService")
	protected GroupLocalService groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupService")
	protected GroupService groupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupFinder")
	protected GroupFinder groupFinder;
	@BeanReference(name = "com.liferay.portal.service.LayoutLocalService")
	protected LayoutLocalService layoutLocalService;
	@BeanReference(name = "com.liferay.portal.service.LayoutService")
	protected LayoutService layoutService;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutFinder")
	protected LayoutFinder layoutFinder;
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
}