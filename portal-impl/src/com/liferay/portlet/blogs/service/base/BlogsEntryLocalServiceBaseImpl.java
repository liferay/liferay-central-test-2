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

package com.liferay.portlet.blogs.service.base;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;

import com.liferay.portlet.blogs.service.BlogsCategoryLocalService;
import com.liferay.portlet.blogs.service.BlogsCategoryLocalServiceFactory;
import com.liferay.portlet.blogs.service.BlogsCategoryService;
import com.liferay.portlet.blogs.service.BlogsCategoryServiceFactory;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalService;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceFactory;
import com.liferay.portlet.blogs.service.persistence.BlogsCategoryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsCategoryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinderUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserFinderUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserUtil;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * <a href="BlogsEntryLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BlogsEntryLocalServiceBaseImpl
	implements BlogsEntryLocalService, InitializingBean {
	public List dynamicQuery(DynamicQueryInitializer queryInitializer)
		throws SystemException {
		return BlogsEntryUtil.findWithDynamicQuery(queryInitializer);
	}

	public List dynamicQuery(DynamicQueryInitializer queryInitializer,
		int begin, int end) throws SystemException {
		return BlogsEntryUtil.findWithDynamicQuery(queryInitializer, begin, end);
	}

	public BlogsCategoryLocalService getBlogsCategoryLocalService() {
		return blogsCategoryLocalService;
	}

	public void setBlogsCategoryLocalService(
		BlogsCategoryLocalService blogsCategoryLocalService) {
		this.blogsCategoryLocalService = blogsCategoryLocalService;
	}

	public BlogsCategoryService getBlogsCategoryService() {
		return blogsCategoryService;
	}

	public void setBlogsCategoryService(
		BlogsCategoryService blogsCategoryService) {
		this.blogsCategoryService = blogsCategoryService;
	}

	public BlogsCategoryPersistence getBlogsCategoryPersistence() {
		return blogsCategoryPersistence;
	}

	public void setBlogsCategoryPersistence(
		BlogsCategoryPersistence blogsCategoryPersistence) {
		this.blogsCategoryPersistence = blogsCategoryPersistence;
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

	public void afterPropertiesSet() {
		if (blogsCategoryLocalService == null) {
			blogsCategoryLocalService = BlogsCategoryLocalServiceFactory.getImpl();
		}

		if (blogsCategoryService == null) {
			blogsCategoryService = BlogsCategoryServiceFactory.getImpl();
		}

		if (blogsCategoryPersistence == null) {
			blogsCategoryPersistence = BlogsCategoryUtil.getPersistence();
		}

		if (blogsEntryPersistence == null) {
			blogsEntryPersistence = BlogsEntryUtil.getPersistence();
		}

		if (blogsEntryFinder == null) {
			blogsEntryFinder = BlogsEntryFinderUtil.getFinder();
		}

		if (blogsStatsUserLocalService == null) {
			blogsStatsUserLocalService = BlogsStatsUserLocalServiceFactory.getImpl();
		}

		if (blogsStatsUserPersistence == null) {
			blogsStatsUserPersistence = BlogsStatsUserUtil.getPersistence();
		}

		if (blogsStatsUserFinder == null) {
			blogsStatsUserFinder = BlogsStatsUserFinderUtil.getFinder();
		}
	}

	protected BlogsCategoryLocalService blogsCategoryLocalService;
	protected BlogsCategoryService blogsCategoryService;
	protected BlogsCategoryPersistence blogsCategoryPersistence;
	protected BlogsEntryPersistence blogsEntryPersistence;
	protected BlogsEntryFinder blogsEntryFinder;
	protected BlogsStatsUserLocalService blogsStatsUserLocalService;
	protected BlogsStatsUserPersistence blogsStatsUserPersistence;
	protected BlogsStatsUserFinder blogsStatsUserFinder;
}