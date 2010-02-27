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

package com.liferay.portlet.calendar.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.mail.service.MailService;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagService;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.calendar.service.CalEventLocalService;
import com.liferay.portlet.calendar.service.CalEventService;
import com.liferay.portlet.calendar.service.persistence.CalEventFinder;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;
import com.liferay.portlet.expando.service.ExpandoValueService;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;

/**
 * <a href="CalEventServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class CalEventServiceBaseImpl extends PrincipalBean
	implements CalEventService {
	public CalEventLocalService getCalEventLocalService() {
		return calEventLocalService;
	}

	public void setCalEventLocalService(
		CalEventLocalService calEventLocalService) {
		this.calEventLocalService = calEventLocalService;
	}

	public CalEventService getCalEventService() {
		return calEventService;
	}

	public void setCalEventService(CalEventService calEventService) {
		this.calEventService = calEventService;
	}

	public CalEventPersistence getCalEventPersistence() {
		return calEventPersistence;
	}

	public void setCalEventPersistence(CalEventPersistence calEventPersistence) {
		this.calEventPersistence = calEventPersistence;
	}

	public CalEventFinder getCalEventFinder() {
		return calEventFinder;
	}

	public void setCalEventFinder(CalEventFinder calEventFinder) {
		this.calEventFinder = calEventFinder;
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

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
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

	public PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	public void setPortletPreferencesLocalService(
		PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	public PortletPreferencesService getPortletPreferencesService() {
		return portletPreferencesService;
	}

	public void setPortletPreferencesService(
		PortletPreferencesService portletPreferencesService) {
		this.portletPreferencesService = portletPreferencesService;
	}

	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
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

	@BeanReference(name = "com.liferay.portlet.calendar.service.CalEventLocalService")
	protected CalEventLocalService calEventLocalService;
	@BeanReference(name = "com.liferay.portlet.calendar.service.CalEventService")
	protected CalEventService calEventService;
	@BeanReference(name = "com.liferay.portlet.calendar.service.persistence.CalEventPersistence")
	protected CalEventPersistence calEventPersistence;
	@BeanReference(name = "com.liferay.portlet.calendar.service.persistence.CalEventFinder")
	protected CalEventFinder calEventFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.mail.service.MailService")
	protected MailService mailService;
	@BeanReference(name = "com.liferay.portal.service.CompanyLocalService")
	protected CompanyLocalService companyLocalService;
	@BeanReference(name = "com.liferay.portal.service.CompanyService")
	protected CompanyService companyService;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService")
	protected GroupLocalService groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupService")
	protected GroupService groupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupFinder")
	protected GroupFinder groupFinder;
	@BeanReference(name = "com.liferay.portal.service.PortletPreferencesLocalService")
	protected PortletPreferencesLocalService portletPreferencesLocalService;
	@BeanReference(name = "com.liferay.portal.service.PortletPreferencesService")
	protected PortletPreferencesService portletPreferencesService;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesFinder")
	protected PortletPreferencesFinder portletPreferencesFinder;
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