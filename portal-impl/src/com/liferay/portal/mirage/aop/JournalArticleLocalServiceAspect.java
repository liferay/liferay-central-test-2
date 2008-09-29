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

import com.liferay.portal.util.PropsValues;

import com.sun.saw.Workflow;
import com.sun.saw.WorkflowException;
import com.sun.saw.WorkflowFactory;
import com.sun.saw.vo.OutputVO;
import com.sun.saw.vo.SaveTaskVO;

import java.util.Properties;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="JournalArticleLocalServiceAspect.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Joshna Reddy
 *
 */
public class JournalArticleLocalServiceAspect extends BaseMirageAspect {

	protected Object doInvoke(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		String methodName = proceedingJoinPoint.getSignature().getName();
		Object[] arguments = proceedingJoinPoint.getArgs();

		if (methodName.equals("addArticle") ||
			methodName.equals("deleteArticle") ||
			methodName.equals("getArticle") ||
			methodName.equals("getLatestArticle") ||
			methodName.equals("updateArticle")||
			methodName.equals("updateContent")) {

			ContentInvoker contentInvoker = new ContentInvoker(
				proceedingJoinPoint);

			if (methodName.equals("addArticle")) {
				contentService.createContent(contentInvoker);
			}
			else if (methodName.equals("deleteArticle")) {
				contentService.deleteContent(contentInvoker);
			}
			else if (methodName.equals("getArticle") ||
					 methodName.equals("getLatestArticle")) {

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
		else if (methodName.equals("approveArticle") ||
				 methodName.equals("expireArticle")) {

			_processWorkflowThroughSAW(methodName, arguments);

			WorkflowInvoker workflowInvoker = new WorkflowInvoker(
				proceedingJoinPoint);

			if (methodName.equals("approveArticle")) {
				workflowService.updateWorkflowComplete(workflowInvoker);
			}
			else if (methodName.equals("expireArticle")) {
				workflowService.updateWorkflowContentRejected(workflowInvoker);
			}

			return workflowInvoker.getReturnValue();
		}
		else if (methodName.equals("getArticles") ||
				 methodName.equals("getArticlesBySmallImageId")||
				 methodName.equals("getArticlesCount") ||
				 methodName.equals("getDisplayArticle") ||
				 methodName.equals("getStructureArticles") ||
				 methodName.equals("getStructureArticlesCount") ||
				 methodName.equals("getTemplateArticles") ||
				 methodName.equals("getTemplateArticlesCount") ||
				 methodName.equals("searchCount") ||
				 (methodName.equals("search") && (arguments.length > 6))) {

			SearchCriteriaInvoker searchCriteriaInvoker =
				new SearchCriteriaInvoker(proceedingJoinPoint);

			if (methodName.equals("getArticles") ||
				methodName.equals("getArticlesBySmallImageId") ||
				methodName.equals("getDisplayArticle")||
				methodName.equals("getTemplateArticles")||
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
		else {
			return proceedingJoinPoint.proceed();
		}
	}

	private Workflow _getWorkflowImpl() throws WorkflowException {
		Properties props = new Properties();
		props.setProperty(
			"sawworkflowimplclass", PropsValues.JOURNAL_WORKFLOW_IMPL);

		WorkflowFactory workflowFactory = WorkflowFactory.getInstance();

		return workflowFactory.getWorkflowInstance(props);
	}

	private OutputVO _processWorkflowThroughSAW(
		String methodName, Object[] args) {

		OutputVO outputVO = null;

		try {
			SaveTaskVO saveTaskVO = new SaveTaskVO();
			Workflow workflow = _getWorkflowImpl();
			outputVO = workflow.saveTasks(saveTaskVO);
		}
		catch (WorkflowException we) {
			//Do nothing
		}

		return outputVO;
	}

}