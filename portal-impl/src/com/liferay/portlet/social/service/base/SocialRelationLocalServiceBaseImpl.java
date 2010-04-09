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

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.social.model.SocialRelation;
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
 * <a href="SocialRelationLocalServiceBaseImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class SocialRelationLocalServiceBaseImpl
	implements SocialRelationLocalService {
	public SocialRelation addSocialRelation(SocialRelation socialRelation)
		throws SystemException {
		socialRelation.setNew(true);

		return socialRelationPersistence.update(socialRelation, false);
	}

	public SocialRelation createSocialRelation(long relationId) {
		return socialRelationPersistence.create(relationId);
	}

	public void deleteSocialRelation(long relationId)
		throws PortalException, SystemException {
		socialRelationPersistence.remove(relationId);
	}

	public void deleteSocialRelation(SocialRelation socialRelation)
		throws SystemException {
		socialRelationPersistence.remove(socialRelation);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return socialRelationPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return socialRelationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return socialRelationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public int dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return socialRelationPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public SocialRelation getSocialRelation(long relationId)
		throws PortalException, SystemException {
		return socialRelationPersistence.findByPrimaryKey(relationId);
	}

	public List<SocialRelation> getSocialRelations(int start, int end)
		throws SystemException {
		return socialRelationPersistence.findAll(start, end);
	}

	public int getSocialRelationsCount() throws SystemException {
		return socialRelationPersistence.countAll();
	}

	public SocialRelation updateSocialRelation(SocialRelation socialRelation)
		throws SystemException {
		socialRelation.setNew(false);

		return socialRelationPersistence.update(socialRelation, true);
	}

	public SocialRelation updateSocialRelation(SocialRelation socialRelation,
		boolean merge) throws SystemException {
		socialRelation.setNew(false);

		return socialRelationPersistence.update(socialRelation, merge);
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

	@BeanReference(type = SocialActivityLocalService.class)
	protected SocialActivityLocalService socialActivityLocalService;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityFinder.class)
	protected SocialActivityFinder socialActivityFinder;
	@BeanReference(type = SocialActivityInterpreterLocalService.class)
	protected SocialActivityInterpreterLocalService socialActivityInterpreterLocalService;
	@BeanReference(type = SocialRelationLocalService.class)
	protected SocialRelationLocalService socialRelationLocalService;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestLocalService.class)
	protected SocialRequestLocalService socialRequestLocalService;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = SocialRequestInterpreterLocalService.class)
	protected SocialRequestInterpreterLocalService socialRequestInterpreterLocalService;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
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