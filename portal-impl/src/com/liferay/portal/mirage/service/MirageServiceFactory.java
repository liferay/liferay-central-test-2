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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import com.sun.portal.cms.mirage.service.custom.BinaryContentService;
import com.sun.portal.cms.mirage.service.custom.CategoryService;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;
import com.sun.portal.cms.mirage.service.custom.ContentService;
import com.sun.portal.cms.mirage.service.custom.ContentTypeService;
import com.sun.portal.cms.mirage.service.custom.SearchService;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

/**
 * <a href="MirageServiceFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 *
 */
public class MirageServiceFactory {

	public static BinaryContentService getArticleResourceService() {
		if (_articleResourceService == null) {
			_articleResourceService =
				(BinaryContentService)PortalBeanLocatorUtil.locate(
					_ARTICLE_RESOURCE_SERVICE);
		}

		return _articleResourceService;
	}

	public static CategoryService getCategoryService() {
		if (_categoryService == null) {
			_categoryService = (CategoryService)PortalBeanLocatorUtil.locate(
				_CATEGORY_SERVICE);
		}

		return _categoryService;
	}

	public static ContentFeedService getContentFeedService() {
		if (_contentFeedService == null) {
			_contentFeedService =
				(ContentFeedService)PortalBeanLocatorUtil.locate(
					_CONTENT_FEED_SERVICE);
		}

		return _contentFeedService;
	}

	public static ContentService getContentService() {
		if (_contentService == null) {
			_contentService = (ContentService)PortalBeanLocatorUtil.locate(
				_CONTENT_SERVICE);
		}

		return _contentService;
	}

	public static ContentTypeService getContentTypeService() {
		if (_contentTypeService == null) {
			_contentTypeService =
				(ContentTypeService)PortalBeanLocatorUtil.locate(
					_CONTENT_TYPE_SERVICE);
		}

		return _contentTypeService;
	}

	public static SearchService getSearchService() {
		if (_searchService == null) {
			_searchService = (SearchService)PortalBeanLocatorUtil.locate(
				_SEARCH_SERVICE);
		}

		return _searchService;
	}

	public static WorkflowService getWorkflowService() {
		if (_workflowService == null) {
			_workflowService = (WorkflowService)PortalBeanLocatorUtil.locate(
				_WORKFLOW_SERVICE);
		}

		return _workflowService;
	}

	private static final String _ARTICLE_RESOURCE_SERVICE =
		"com.liferay.portal.mirage.ArticleResourceService";

	private static final String _CATEGORY_SERVICE =
		"com.liferay.portal.mirage.CategoryService";

	private static final String _CONTENT_FEED_SERVICE =
		"com.liferay.portal.mirage.ContentFeedService";

	private static final String _CONTENT_SERVICE =
		"com.liferay.portal.mirage.ContentService";

	private static final String _CONTENT_TYPE_SERVICE =
		"com.liferay.portal.mirage.ContentTypeService";

	private static final String _SEARCH_SERVICE =
		"com.liferay.portal.mirage.SearchService";

	private static final String _WORKFLOW_SERVICE =
		"com.liferay.portal.mirage.WorkflowService";

	private static BinaryContentService _articleResourceService;
	private static CategoryService _categoryService;
	private static ContentFeedService _contentFeedService;
	private static ContentService _contentService;
	private static ContentTypeService _contentTypeService;
	private static SearchService _searchService;
	private static WorkflowService _workflowService;

}