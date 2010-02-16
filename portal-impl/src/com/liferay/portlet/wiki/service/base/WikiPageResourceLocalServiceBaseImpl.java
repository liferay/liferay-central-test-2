/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiNodeLocalService;
import com.liferay.portlet.wiki.service.WikiNodeService;
import com.liferay.portlet.wiki.service.WikiPageLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalService;
import com.liferay.portlet.wiki.service.WikiPageService;
import com.liferay.portlet.wiki.service.persistence.WikiNodePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinder;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

import java.util.List;

/**
 * <a href="WikiPageResourceLocalServiceBaseImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class WikiPageResourceLocalServiceBaseImpl
	implements WikiPageResourceLocalService {
	public WikiPageResource addWikiPageResource(
		WikiPageResource wikiPageResource) throws SystemException {
		wikiPageResource.setNew(true);

		return wikiPageResourcePersistence.update(wikiPageResource, false);
	}

	public WikiPageResource createWikiPageResource(long resourcePrimKey) {
		return wikiPageResourcePersistence.create(resourcePrimKey);
	}

	public void deleteWikiPageResource(long resourcePrimKey)
		throws PortalException, SystemException {
		wikiPageResourcePersistence.remove(resourcePrimKey);
	}

	public void deleteWikiPageResource(WikiPageResource wikiPageResource)
		throws SystemException {
		wikiPageResourcePersistence.remove(wikiPageResource);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return wikiPageResourcePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return wikiPageResourcePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public WikiPageResource getWikiPageResource(long resourcePrimKey)
		throws PortalException, SystemException {
		return wikiPageResourcePersistence.findByPrimaryKey(resourcePrimKey);
	}

	public List<WikiPageResource> getWikiPageResources(int start, int end)
		throws SystemException {
		return wikiPageResourcePersistence.findAll(start, end);
	}

	public int getWikiPageResourcesCount() throws SystemException {
		return wikiPageResourcePersistence.countAll();
	}

	public WikiPageResource updateWikiPageResource(
		WikiPageResource wikiPageResource) throws SystemException {
		wikiPageResource.setNew(false);

		return wikiPageResourcePersistence.update(wikiPageResource, true);
	}

	public WikiPageResource updateWikiPageResource(
		WikiPageResource wikiPageResource, boolean merge)
		throws SystemException {
		wikiPageResource.setNew(false);

		return wikiPageResourcePersistence.update(wikiPageResource, merge);
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

	public WikiPageLocalService getWikiPageLocalService() {
		return wikiPageLocalService;
	}

	public void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {
		this.wikiPageLocalService = wikiPageLocalService;
	}

	public WikiPageService getWikiPageService() {
		return wikiPageService;
	}

	public void setWikiPageService(WikiPageService wikiPageService) {
		this.wikiPageService = wikiPageService;
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

	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiNodeLocalService")
	protected WikiNodeLocalService wikiNodeLocalService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiNodeService")
	protected WikiNodeService wikiNodeService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiNodePersistence")
	protected WikiNodePersistence wikiNodePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageLocalService")
	protected WikiPageLocalService wikiPageLocalService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageService")
	protected WikiPageService wikiPageService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence")
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageFinder")
	protected WikiPageFinder wikiPageFinder;
	@BeanReference(name = "com.liferay.portlet.wiki.service.WikiPageResourceLocalService")
	protected WikiPageResourceLocalService wikiPageResourceLocalService;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence")
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
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