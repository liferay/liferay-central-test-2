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

package com.liferay.portlet.wiki.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.service.DLService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.PortletPreferencesLocalService;
import com.liferay.portal.service.PortletPreferencesService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.SubscriptionLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.SubscriptionPersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.social.service.SocialActivityLocalService;
import com.liferay.portlet.social.service.persistence.SocialActivityFinder;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetService;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryService;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetPersistence;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryPersistence;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalService;
import com.liferay.portlet.wiki.service.WikiNodeService;
import com.liferay.portlet.wiki.service.WikiPageLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalService;
import com.liferay.portlet.wiki.service.persistence.WikiNodePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinder;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

import java.util.List;

/**
 * <a href="WikiPageLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class WikiPageLocalServiceBaseImpl
	implements WikiPageLocalService, InitializingBean {
	public WikiPage addWikiPage(WikiPage wikiPage) throws SystemException {
		wikiPage.setNew(true);

		return wikiPagePersistence.update(wikiPage, false);
	}

	public void deleteWikiPage(long pageId)
		throws PortalException, SystemException {
		wikiPagePersistence.remove(pageId);
	}

	public void deleteWikiPage(WikiPage wikiPage) throws SystemException {
		wikiPagePersistence.remove(wikiPage);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return wikiPagePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return wikiPagePersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public WikiPage getWikiPage(long pageId)
		throws PortalException, SystemException {
		return wikiPagePersistence.findByPrimaryKey(pageId);
	}

	public List<WikiPage> getWikiPages(int start, int end)
		throws SystemException {
		return wikiPagePersistence.findAll(start, end);
	}

	public int getWikiPagesCount() throws SystemException {
		return wikiPagePersistence.countAll();
	}

	public WikiPage updateWikiPage(WikiPage wikiPage) throws SystemException {
		wikiPage.setNew(false);

		return wikiPagePersistence.update(wikiPage, true);
	}

	public WikiNodeLocalService getWikiNodeLocalService() {
		return wikiNodeLocalService;
	}

	public void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {
		this.wikiNodeLocalService = wikiNodeLocalService;
	}

	public WikiNodeService getWikiNodeService() {
		return wikiNodeService;
	}

	public void setWikiNodeService(WikiNodeService wikiNodeService) {
		this.wikiNodeService = wikiNodeService;
	}

	public WikiNodePersistence getWikiNodePersistence() {
		return wikiNodePersistence;
	}

	public void setWikiNodePersistence(WikiNodePersistence wikiNodePersistence) {
		this.wikiNodePersistence = wikiNodePersistence;
	}

	public WikiPagePersistence getWikiPagePersistence() {
		return wikiPagePersistence;
	}

	public void setWikiPagePersistence(WikiPagePersistence wikiPagePersistence) {
		this.wikiPagePersistence = wikiPagePersistence;
	}

	public WikiPageFinder getWikiPageFinder() {
		return wikiPageFinder;
	}

	public void setWikiPageFinder(WikiPageFinder wikiPageFinder) {
		this.wikiPageFinder = wikiPageFinder;
	}

	public WikiPageResourceLocalService getWikiPageResourceLocalService() {
		return wikiPageResourceLocalService;
	}

	public void setWikiPageResourceLocalService(
		WikiPageResourceLocalService wikiPageResourceLocalService) {
		this.wikiPageResourceLocalService = wikiPageResourceLocalService;
	}

	public WikiPageResourcePersistence getWikiPageResourcePersistence() {
		return wikiPageResourcePersistence;
	}

	public void setWikiPageResourcePersistence(
		WikiPageResourcePersistence wikiPageResourcePersistence) {
		this.wikiPageResourcePersistence = wikiPageResourcePersistence;
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

	public SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	public void setSubscriptionLocalService(
		SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
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

	public void afterPropertiesSet() {
		if (wikiNodeLocalService == null) {
			wikiNodeLocalService = (WikiNodeLocalService)PortalBeanLocatorUtil.locate(WikiNodeLocalService.class.getName() +
					".impl");
		}

		if (wikiNodeService == null) {
			wikiNodeService = (WikiNodeService)PortalBeanLocatorUtil.locate(WikiNodeService.class.getName() +
					".impl");
		}

		if (wikiNodePersistence == null) {
			wikiNodePersistence = (WikiNodePersistence)PortalBeanLocatorUtil.locate(WikiNodePersistence.class.getName() +
					".impl");
		}

		if (wikiPagePersistence == null) {
			wikiPagePersistence = (WikiPagePersistence)PortalBeanLocatorUtil.locate(WikiPagePersistence.class.getName() +
					".impl");
		}

		if (wikiPageFinder == null) {
			wikiPageFinder = (WikiPageFinder)PortalBeanLocatorUtil.locate(WikiPageFinder.class.getName() +
					".impl");
		}

		if (wikiPageResourceLocalService == null) {
			wikiPageResourceLocalService = (WikiPageResourceLocalService)PortalBeanLocatorUtil.locate(WikiPageResourceLocalService.class.getName() +
					".impl");
		}

		if (wikiPageResourcePersistence == null) {
			wikiPageResourcePersistence = (WikiPageResourcePersistence)PortalBeanLocatorUtil.locate(WikiPageResourcePersistence.class.getName() +
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

		if (dlLocalService == null) {
			dlLocalService = (DLLocalService)PortalBeanLocatorUtil.locate(DLLocalService.class.getName() +
					".impl");
		}

		if (dlService == null) {
			dlService = (DLService)PortalBeanLocatorUtil.locate(DLService.class.getName() +
					".impl");
		}

		if (companyLocalService == null) {
			companyLocalService = (CompanyLocalService)PortalBeanLocatorUtil.locate(CompanyLocalService.class.getName() +
					".impl");
		}

		if (companyService == null) {
			companyService = (CompanyService)PortalBeanLocatorUtil.locate(CompanyService.class.getName() +
					".impl");
		}

		if (companyPersistence == null) {
			companyPersistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName() +
					".impl");
		}

		if (groupLocalService == null) {
			groupLocalService = (GroupLocalService)PortalBeanLocatorUtil.locate(GroupLocalService.class.getName() +
					".impl");
		}

		if (groupService == null) {
			groupService = (GroupService)PortalBeanLocatorUtil.locate(GroupService.class.getName() +
					".impl");
		}

		if (groupPersistence == null) {
			groupPersistence = (GroupPersistence)PortalBeanLocatorUtil.locate(GroupPersistence.class.getName() +
					".impl");
		}

		if (groupFinder == null) {
			groupFinder = (GroupFinder)PortalBeanLocatorUtil.locate(GroupFinder.class.getName() +
					".impl");
		}

		if (portletPreferencesLocalService == null) {
			portletPreferencesLocalService = (PortletPreferencesLocalService)PortalBeanLocatorUtil.locate(PortletPreferencesLocalService.class.getName() +
					".impl");
		}

		if (portletPreferencesService == null) {
			portletPreferencesService = (PortletPreferencesService)PortalBeanLocatorUtil.locate(PortletPreferencesService.class.getName() +
					".impl");
		}

		if (portletPreferencesPersistence == null) {
			portletPreferencesPersistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName() +
					".impl");
		}

		if (portletPreferencesFinder == null) {
			portletPreferencesFinder = (PortletPreferencesFinder)PortalBeanLocatorUtil.locate(PortletPreferencesFinder.class.getName() +
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

		if (subscriptionLocalService == null) {
			subscriptionLocalService = (SubscriptionLocalService)PortalBeanLocatorUtil.locate(SubscriptionLocalService.class.getName() +
					".impl");
		}

		if (subscriptionPersistence == null) {
			subscriptionPersistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName() +
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

		if (mbMessageLocalService == null) {
			mbMessageLocalService = (MBMessageLocalService)PortalBeanLocatorUtil.locate(MBMessageLocalService.class.getName() +
					".impl");
		}

		if (mbMessageService == null) {
			mbMessageService = (MBMessageService)PortalBeanLocatorUtil.locate(MBMessageService.class.getName() +
					".impl");
		}

		if (mbMessagePersistence == null) {
			mbMessagePersistence = (MBMessagePersistence)PortalBeanLocatorUtil.locate(MBMessagePersistence.class.getName() +
					".impl");
		}

		if (mbMessageFinder == null) {
			mbMessageFinder = (MBMessageFinder)PortalBeanLocatorUtil.locate(MBMessageFinder.class.getName() +
					".impl");
		}

		if (socialActivityLocalService == null) {
			socialActivityLocalService = (SocialActivityLocalService)PortalBeanLocatorUtil.locate(SocialActivityLocalService.class.getName() +
					".impl");
		}

		if (socialActivityPersistence == null) {
			socialActivityPersistence = (SocialActivityPersistence)PortalBeanLocatorUtil.locate(SocialActivityPersistence.class.getName() +
					".impl");
		}

		if (socialActivityFinder == null) {
			socialActivityFinder = (SocialActivityFinder)PortalBeanLocatorUtil.locate(SocialActivityFinder.class.getName() +
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

		if (tagsEntryLocalService == null) {
			tagsEntryLocalService = (TagsEntryLocalService)PortalBeanLocatorUtil.locate(TagsEntryLocalService.class.getName() +
					".impl");
		}

		if (tagsEntryService == null) {
			tagsEntryService = (TagsEntryService)PortalBeanLocatorUtil.locate(TagsEntryService.class.getName() +
					".impl");
		}

		if (tagsEntryPersistence == null) {
			tagsEntryPersistence = (TagsEntryPersistence)PortalBeanLocatorUtil.locate(TagsEntryPersistence.class.getName() +
					".impl");
		}

		if (tagsEntryFinder == null) {
			tagsEntryFinder = (TagsEntryFinder)PortalBeanLocatorUtil.locate(TagsEntryFinder.class.getName() +
					".impl");
		}
	}

	protected WikiNodeLocalService wikiNodeLocalService;
	protected WikiNodeService wikiNodeService;
	protected WikiNodePersistence wikiNodePersistence;
	protected WikiPagePersistence wikiPagePersistence;
	protected WikiPageFinder wikiPageFinder;
	protected WikiPageResourceLocalService wikiPageResourceLocalService;
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected DLLocalService dlLocalService;
	protected DLService dlService;
	protected CompanyLocalService companyLocalService;
	protected CompanyService companyService;
	protected CompanyPersistence companyPersistence;
	protected GroupLocalService groupLocalService;
	protected GroupService groupService;
	protected GroupPersistence groupPersistence;
	protected GroupFinder groupFinder;
	protected PortletPreferencesLocalService portletPreferencesLocalService;
	protected PortletPreferencesService portletPreferencesService;
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	protected PortletPreferencesFinder portletPreferencesFinder;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
	protected SubscriptionLocalService subscriptionLocalService;
	protected SubscriptionPersistence subscriptionPersistence;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected MBMessageLocalService mbMessageLocalService;
	protected MBMessageService mbMessageService;
	protected MBMessagePersistence mbMessagePersistence;
	protected MBMessageFinder mbMessageFinder;
	protected SocialActivityLocalService socialActivityLocalService;
	protected SocialActivityPersistence socialActivityPersistence;
	protected SocialActivityFinder socialActivityFinder;
	protected TagsAssetLocalService tagsAssetLocalService;
	protected TagsAssetService tagsAssetService;
	protected TagsAssetPersistence tagsAssetPersistence;
	protected TagsAssetFinder tagsAssetFinder;
	protected TagsEntryLocalService tagsEntryLocalService;
	protected TagsEntryService tagsEntryService;
	protected TagsEntryPersistence tagsEntryPersistence;
	protected TagsEntryFinder tagsEntryFinder;
}