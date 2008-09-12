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

package com.liferay.portlet.blogs.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.OrganizationService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.OrganizationFinder;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryService;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.ratings.service.RatingsStatsLocalService;
import com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence;
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

import java.util.List;

/**
 * <a href="BlogsEntryLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BlogsEntryLocalServiceBaseImpl
	implements BlogsEntryLocalService {
	public BlogsEntry addBlogsEntry(BlogsEntry blogsEntry)
		throws SystemException {
		blogsEntry.setNew(true);

		return blogsEntryPersistence.update(blogsEntry, false);
	}

	public BlogsEntry createBlogsEntry(long entryId) {
		return blogsEntryPersistence.create(entryId);
	}

	public void deleteBlogsEntry(long entryId)
		throws PortalException, SystemException {
		blogsEntryPersistence.remove(entryId);
	}

	public void deleteBlogsEntry(BlogsEntry blogsEntry)
		throws SystemException {
		blogsEntryPersistence.remove(blogsEntry);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return blogsEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return blogsEntryPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public BlogsEntry getBlogsEntry(long entryId)
		throws PortalException, SystemException {
		return blogsEntryPersistence.findByPrimaryKey(entryId);
	}

	public List<BlogsEntry> getBlogsEntries(int start, int end)
		throws SystemException {
		return blogsEntryPersistence.findAll(start, end);
	}

	public int getBlogsEntriesCount() throws SystemException {
		return blogsEntryPersistence.countAll();
	}

	public BlogsEntry updateBlogsEntry(BlogsEntry blogsEntry)
		throws SystemException {
		blogsEntry.setNew(false);

		return blogsEntryPersistence.update(blogsEntry, true);
	}

	public BlogsEntryLocalService getBlogsEntryLocalService() {
		return blogsEntryLocalService;
	}

	public void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {
		this.blogsEntryLocalService = blogsEntryLocalService;
	}

	public BlogsEntryService getBlogsEntryService() {
		return blogsEntryService;
	}

	public void setBlogsEntryService(BlogsEntryService blogsEntryService) {
		this.blogsEntryService = blogsEntryService;
	}

	public BlogsEntryPersistence getBlogsEntryPersistence() {
		return blogsEntryPersistence;
	}

	public void setBlogsEntryPersistence(
		BlogsEntryPersistence blogsEntryPersistence) {
		this.blogsEntryPersistence = blogsEntryPersistence;
	}

	public BlogsEntryFinder getBlogsEntryFinder() {
		return blogsEntryFinder;
	}

	public void setBlogsEntryFinder(BlogsEntryFinder blogsEntryFinder) {
		this.blogsEntryFinder = blogsEntryFinder;
	}

	public BlogsStatsUserLocalService getBlogsStatsUserLocalService() {
		return blogsStatsUserLocalService;
	}

	public void setBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		this.blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	public BlogsStatsUserPersistence getBlogsStatsUserPersistence() {
		return blogsStatsUserPersistence;
	}

	public void setBlogsStatsUserPersistence(
		BlogsStatsUserPersistence blogsStatsUserPersistence) {
		this.blogsStatsUserPersistence = blogsStatsUserPersistence;
	}

	public BlogsStatsUserFinder getBlogsStatsUserFinder() {
		return blogsStatsUserFinder;
	}

	public void setBlogsStatsUserFinder(
		BlogsStatsUserFinder blogsStatsUserFinder) {
		this.blogsStatsUserFinder = blogsStatsUserFinder;
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

	public OrganizationLocalService getOrganizationLocalService() {
		return organizationLocalService;
	}

	public void setOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {
		this.organizationLocalService = organizationLocalService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public OrganizationPersistence getOrganizationPersistence() {
		return organizationPersistence;
	}

	public void setOrganizationPersistence(
		OrganizationPersistence organizationPersistence) {
		this.organizationPersistence = organizationPersistence;
	}

	public OrganizationFinder getOrganizationFinder() {
		return organizationFinder;
	}

	public void setOrganizationFinder(OrganizationFinder organizationFinder) {
		this.organizationFinder = organizationFinder;
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

	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.BlogsEntryLocalService.impl")
	protected BlogsEntryLocalService blogsEntryLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.BlogsEntryService.impl")
	protected BlogsEntryService blogsEntryService;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence.impl")
	protected BlogsEntryPersistence blogsEntryPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder.impl")
	protected BlogsEntryFinder blogsEntryFinder;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.BlogsStatsUserLocalService.impl")
	protected BlogsStatsUserLocalService blogsStatsUserLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence.impl")
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder.impl")
	protected BlogsStatsUserFinder blogsStatsUserFinder;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterLocalService.impl")
	protected CounterLocalService counterLocalService;
	@javax.annotation.Resource(name = "com.liferay.counter.service.CounterService.impl")
	protected CounterService counterService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.CompanyLocalService.impl")
	protected CompanyLocalService companyLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.CompanyService.impl")
	protected CompanyService companyService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.CompanyPersistence.impl")
	protected CompanyPersistence companyPersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.GroupLocalService.impl")
	protected GroupLocalService groupLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.GroupService.impl")
	protected GroupService groupService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected GroupPersistence groupPersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.GroupFinder.impl")
	protected GroupFinder groupFinder;
	@javax.annotation.Resource(name = "com.liferay.portal.service.OrganizationLocalService.impl")
	protected OrganizationLocalService organizationLocalService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.OrganizationService.impl")
	protected OrganizationService organizationService;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.OrganizationPersistence.impl")
	protected OrganizationPersistence organizationPersistence;
	@javax.annotation.Resource(name = "com.liferay.portal.service.persistence.OrganizationFinder.impl")
	protected OrganizationFinder organizationFinder;
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
	@javax.annotation.Resource(name = "com.liferay.portlet.messageboards.service.MBMessageLocalService.impl")
	protected MBMessageLocalService mbMessageLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.messageboards.service.MBMessageService.impl")
	protected MBMessageService mbMessageService;
	@javax.annotation.Resource(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected MBMessagePersistence mbMessagePersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.messageboards.service.persistence.MBMessageFinder.impl")
	protected MBMessageFinder mbMessageFinder;
	@javax.annotation.Resource(name = "com.liferay.portlet.ratings.service.RatingsStatsLocalService.impl")
	protected RatingsStatsLocalService ratingsStatsLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.ratings.service.persistence.RatingsStatsPersistence.impl")
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.social.service.SocialActivityLocalService.impl")
	protected SocialActivityLocalService socialActivityLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected SocialActivityPersistence socialActivityPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.social.service.persistence.SocialActivityFinder.impl")
	protected SocialActivityFinder socialActivityFinder;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsAssetLocalService.impl")
	protected TagsAssetLocalService tagsAssetLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsAssetService.impl")
	protected TagsAssetService tagsAssetService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsAssetPersistence.impl")
	protected TagsAssetPersistence tagsAssetPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsAssetFinder.impl")
	protected TagsAssetFinder tagsAssetFinder;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsEntryLocalService.impl")
	protected TagsEntryLocalService tagsEntryLocalService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.TagsEntryService.impl")
	protected TagsEntryService tagsEntryService;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsEntryPersistence.impl")
	protected TagsEntryPersistence tagsEntryPersistence;
	@javax.annotation.Resource(name = "com.liferay.portlet.tags.service.persistence.TagsEntryFinder.impl")
	protected TagsEntryFinder tagsEntryFinder;
}