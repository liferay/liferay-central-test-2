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

package com.liferay.portlet.journal.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.WebDAVPropsLocalService;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;

import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleImageLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleService;
import com.liferay.portlet.journal.service.JournalContentSearchLocalService;
import com.liferay.portlet.journal.service.JournalFeedLocalService;
import com.liferay.portlet.journal.service.JournalFeedService;
import com.liferay.portlet.journal.service.JournalStructureLocalService;
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
 * <a href="JournalStructureLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class JournalStructureLocalServiceBaseImpl
	implements JournalStructureLocalService, InitializingBean {
	public JournalStructure addJournalStructure(
		JournalStructure journalStructure) throws SystemException {
		journalStructure.setNew(true);

		return journalStructurePersistence.update(journalStructure, false);
	}

	public void deleteJournalStructure(long id)
		throws PortalException, SystemException {
		journalStructurePersistence.remove(id);
	}

	public void deleteJournalStructure(JournalStructure journalStructure)
		throws SystemException {
		journalStructurePersistence.remove(journalStructure);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return journalStructurePersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return journalStructurePersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	public JournalStructure getJournalStructure(long id)
		throws PortalException, SystemException {
		return journalStructurePersistence.findByPrimaryKey(id);
	}

	public List<JournalStructure> getJournalStructures(int start, int end)
		throws SystemException {
		return journalStructurePersistence.findAll(start, end);
	}

	public int getJournalStructuresCount() throws SystemException {
		return journalStructurePersistence.countAll();
	}

	public JournalStructure updateJournalStructure(
		JournalStructure journalStructure) throws SystemException {
		journalStructure.setNew(false);

		return journalStructurePersistence.update(journalStructure, true);
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

	public void afterPropertiesSet() {
		if (journalArticleLocalService == null) {
			journalArticleLocalService = (JournalArticleLocalService)PortalBeanLocatorUtil.locate(JournalArticleLocalService.class.getName() +
					".impl");
		}

		if (journalArticleService == null) {
			journalArticleService = (JournalArticleService)PortalBeanLocatorUtil.locate(JournalArticleService.class.getName() +
					".impl");
		}

		if (journalArticlePersistence == null) {
			journalArticlePersistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName() +
					".impl");
		}

		if (journalArticleFinder == null) {
			journalArticleFinder = (JournalArticleFinder)PortalBeanLocatorUtil.locate(JournalArticleFinder.class.getName() +
					".impl");
		}

		if (journalArticleImageLocalService == null) {
			journalArticleImageLocalService = (JournalArticleImageLocalService)PortalBeanLocatorUtil.locate(JournalArticleImageLocalService.class.getName() +
					".impl");
		}

		if (journalArticleImagePersistence == null) {
			journalArticleImagePersistence = (JournalArticleImagePersistence)PortalBeanLocatorUtil.locate(JournalArticleImagePersistence.class.getName() +
					".impl");
		}

		if (journalArticleResourceLocalService == null) {
			journalArticleResourceLocalService = (JournalArticleResourceLocalService)PortalBeanLocatorUtil.locate(JournalArticleResourceLocalService.class.getName() +
					".impl");
		}

		if (journalArticleResourcePersistence == null) {
			journalArticleResourcePersistence = (JournalArticleResourcePersistence)PortalBeanLocatorUtil.locate(JournalArticleResourcePersistence.class.getName() +
					".impl");
		}

		if (journalContentSearchLocalService == null) {
			journalContentSearchLocalService = (JournalContentSearchLocalService)PortalBeanLocatorUtil.locate(JournalContentSearchLocalService.class.getName() +
					".impl");
		}

		if (journalContentSearchPersistence == null) {
			journalContentSearchPersistence = (JournalContentSearchPersistence)PortalBeanLocatorUtil.locate(JournalContentSearchPersistence.class.getName() +
					".impl");
		}

		if (journalFeedLocalService == null) {
			journalFeedLocalService = (JournalFeedLocalService)PortalBeanLocatorUtil.locate(JournalFeedLocalService.class.getName() +
					".impl");
		}

		if (journalFeedService == null) {
			journalFeedService = (JournalFeedService)PortalBeanLocatorUtil.locate(JournalFeedService.class.getName() +
					".impl");
		}

		if (journalFeedPersistence == null) {
			journalFeedPersistence = (JournalFeedPersistence)PortalBeanLocatorUtil.locate(JournalFeedPersistence.class.getName() +
					".impl");
		}

		if (journalFeedFinder == null) {
			journalFeedFinder = (JournalFeedFinder)PortalBeanLocatorUtil.locate(JournalFeedFinder.class.getName() +
					".impl");
		}

		if (journalStructurePersistence == null) {
			journalStructurePersistence = (JournalStructurePersistence)PortalBeanLocatorUtil.locate(JournalStructurePersistence.class.getName() +
					".impl");
		}

		if (journalStructureFinder == null) {
			journalStructureFinder = (JournalStructureFinder)PortalBeanLocatorUtil.locate(JournalStructureFinder.class.getName() +
					".impl");
		}

		if (journalTemplateLocalService == null) {
			journalTemplateLocalService = (JournalTemplateLocalService)PortalBeanLocatorUtil.locate(JournalTemplateLocalService.class.getName() +
					".impl");
		}

		if (journalTemplateService == null) {
			journalTemplateService = (JournalTemplateService)PortalBeanLocatorUtil.locate(JournalTemplateService.class.getName() +
					".impl");
		}

		if (journalTemplatePersistence == null) {
			journalTemplatePersistence = (JournalTemplatePersistence)PortalBeanLocatorUtil.locate(JournalTemplatePersistence.class.getName() +
					".impl");
		}

		if (journalTemplateFinder == null) {
			journalTemplateFinder = (JournalTemplateFinder)PortalBeanLocatorUtil.locate(JournalTemplateFinder.class.getName() +
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

		if (webDAVPropsLocalService == null) {
			webDAVPropsLocalService = (WebDAVPropsLocalService)PortalBeanLocatorUtil.locate(WebDAVPropsLocalService.class.getName() +
					".impl");
		}

		if (webDAVPropsPersistence == null) {
			webDAVPropsPersistence = (WebDAVPropsPersistence)PortalBeanLocatorUtil.locate(WebDAVPropsPersistence.class.getName() +
					".impl");
		}
	}

	protected JournalArticleLocalService journalArticleLocalService;
	protected JournalArticleService journalArticleService;
	protected JournalArticlePersistence journalArticlePersistence;
	protected JournalArticleFinder journalArticleFinder;
	protected JournalArticleImageLocalService journalArticleImageLocalService;
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	protected JournalArticleResourceLocalService journalArticleResourceLocalService;
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	protected JournalContentSearchLocalService journalContentSearchLocalService;
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	protected JournalFeedLocalService journalFeedLocalService;
	protected JournalFeedService journalFeedService;
	protected JournalFeedPersistence journalFeedPersistence;
	protected JournalFeedFinder journalFeedFinder;
	protected JournalStructurePersistence journalStructurePersistence;
	protected JournalStructureFinder journalStructureFinder;
	protected JournalTemplateLocalService journalTemplateLocalService;
	protected JournalTemplateService journalTemplateService;
	protected JournalTemplatePersistence journalTemplatePersistence;
	protected JournalTemplateFinder journalTemplateFinder;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected ResourceLocalService resourceLocalService;
	protected ResourceService resourceService;
	protected ResourcePersistence resourcePersistence;
	protected ResourceFinder resourceFinder;
	protected UserLocalService userLocalService;
	protected UserService userService;
	protected UserPersistence userPersistence;
	protected UserFinder userFinder;
	protected WebDAVPropsLocalService webDAVPropsLocalService;
	protected WebDAVPropsPersistence webDAVPropsPersistence;
}