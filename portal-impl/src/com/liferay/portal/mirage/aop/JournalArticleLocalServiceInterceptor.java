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

package com.liferay.portal.mirage.aop;

import com.liferay.portal.mirage.service.MirageServiceFactory;

import com.sun.portal.cms.mirage.service.custom.ContentService;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="JournalArticleLocalServiceInterceptor.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author K.Joshna Reddy
 *
 */
public class JournalArticleLocalServiceInterceptor extends MirageInterceptor {

	protected Object doInvoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();

		if (methodName.equals("addArticle") ||
			methodName.equals("deleteArticle") ||
			methodName.equals("getArticle") ||
			methodName.equals("updateArticle")||
			methodName.equals("updateContent")) {

			ContentInvoker contentInvoker = new ContentInvoker(invocation);

			ContentService contentService =
				MirageServiceFactory.getContentService();

			if (methodName.equals("addArticle")) {
				contentService.createContent(contentInvoker);
			}
			else if (methodName.equals("deleteArticle")) {
				contentService.deleteContent(contentInvoker);
			}
			else if (methodName.equals("getArticle")) {
				contentService.getContent(contentInvoker, null);
			}
			else if (methodName.equals("updateArticle")) {
				contentService.updateContent(contentInvoker);
			}
			else if (methodName.equals("updateContent")) {
				contentService.updateContent(contentInvoker, null);
			}

			return contentInvoker.getReturnValue();
		}
		else if (methodName.equals("getArticles") ||
				 methodName.equals("getArticlesBySmallImageId")||
				 methodName.equals("getArticlesCount") ||
				 methodName.equals("getDisplayArticle") ||
				 methodName.equals("getLatestArticle") ||
				 methodName.equals("getStructureArticles") ||
				 methodName.equals("getStructureArticlesCount") ||
				 methodName.equals("getTemplateArticles") ||
				 methodName.equals("getTemplateArticlesCount") ||
				 methodName.equals("search") ||
				 methodName.equals("searchCount")) {

			SearchCriteriaInvoker searchCriteriaInvoker =
				new SearchCriteriaInvoker(invocation);

			ContentService contentService =
				MirageServiceFactory.getContentService();

			if (methodName.equals("getArticles") ||
				methodName.equals("getArticlesBySmallImageId") ||
				methodName.equals("getDisplayArticle")||
				methodName.equals("getTemplateArticles")||
				methodName.equals("getLatestArticle")||
				methodName.equals("search")) {

				contentService.searchContents(searchCriteriaInvoker);
			}
			else if (methodName.equals("getStructureArticles")) {
				contentService.searchContentsByType(
					null, searchCriteriaInvoker);
			}
			else if (methodName.equals("getArticlesCount") ||
					 methodName.equals("searchCount")||
					 methodName.equals("getTemplateArticlesCount")){

				contentService.contentSearchCount(searchCriteriaInvoker);
			}
			else if (methodName.equals("getStructureArticlesCount")) {
				contentService.contentSearchCount(null,searchCriteriaInvoker);
			}

			return searchCriteriaInvoker.getReturnValue();
		}
		else if (methodName.equals("approveArticle") ||
				 methodName.equals("expireArticle")) {

			WorkflowInvoker workflowInvoker = new WorkflowInvoker(invocation);

			WorkflowService workflowService =
				MirageServiceFactory.getWorkflowService();

			if (methodName.equals("approveArticle")) {
				workflowService.updateWorkflowComplete(workflowInvoker);
			}
			else if (methodName.equals("expireArticle")) {
				workflowService.updateWorkflowContentRejected(workflowInvoker);
			}

			return workflowInvoker.getReturnValue();
		}
		else {
			return invocation.proceed();
		}
	}

}