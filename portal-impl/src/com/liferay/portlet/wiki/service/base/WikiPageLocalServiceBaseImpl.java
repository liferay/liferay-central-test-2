/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.wiki.service.WikiNodeLocalService;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceFactory;
import com.liferay.portlet.wiki.service.WikiNodeService;
import com.liferay.portlet.wiki.service.WikiNodeServiceFactory;
import com.liferay.portlet.wiki.service.WikiPageLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalService;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceFactory;
import com.liferay.portlet.wiki.service.persistence.WikiNodePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinder;
import com.liferay.portlet.wiki.service.persistence.WikiPageFinderUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiPageUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="WikiPageLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class WikiPageLocalServiceBaseImpl
	implements WikiPageLocalService, InitializingBean {
	public List dynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		return WikiPageUtil.findWithDynamicQuery(queryInitializer);
	}

	public List dynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		return WikiPageUtil.findWithDynamicQuery(queryInitializer, begin, end);
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

	public void afterPropertiesSet() {
		if (wikiNodeLocalService == null) {
			wikiNodeLocalService = WikiNodeLocalServiceFactory.getImpl();
		}

		if (wikiNodeService == null) {
			wikiNodeService = WikiNodeServiceFactory.getImpl();
		}

		if (wikiNodePersistence == null) {
			wikiNodePersistence = WikiNodeUtil.getPersistence();
		}

		if (wikiPagePersistence == null) {
			wikiPagePersistence = WikiPageUtil.getPersistence();
		}

		if (wikiPageFinder == null) {
			wikiPageFinder = WikiPageFinderUtil.getFinder();
		}

		if (wikiPageResourceLocalService == null) {
			wikiPageResourceLocalService = WikiPageResourceLocalServiceFactory.getImpl();
		}

		if (wikiPageResourcePersistence == null) {
			wikiPageResourcePersistence = WikiPageResourceUtil.getPersistence();
		}
	}

	protected WikiNodeLocalService wikiNodeLocalService;
	protected WikiNodeService wikiNodeService;
	protected WikiNodePersistence wikiNodePersistence;
	protected WikiPagePersistence wikiPagePersistence;
	protected WikiPageFinder wikiPageFinder;
	protected WikiPageResourceLocalService wikiPageResourceLocalService;
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
}