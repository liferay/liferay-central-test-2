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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ActionUtil {

	public static void getArticle(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getArticle(request);

		JournalArticle article = (JournalArticle)actionRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		JournalUtil.addRecentArticle(actionRequest, article);
	}

	public static void getArticle(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getArticle(request);

		JournalArticle article = (JournalArticle)renderRequest.getAttribute(
			WebKeys.JOURNAL_ARTICLE);

		JournalUtil.addRecentArticle(renderRequest, article);
	}

	public static void getArticle(HttpServletRequest request) throws Exception {
		long groupId = ParamUtil.getLong(request, "groupId");
		String articleId = ParamUtil.getString(request, "articleId");
		double version = ParamUtil.getDouble(
			request, "version", JournalArticleConstants.DEFAULT_VERSION);

		JournalArticle article = null;

		if (Validator.isNotNull(articleId)) {
			article = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);
		}

		request.setAttribute(WebKeys.JOURNAL_ARTICLE, article);
	}

	public static void getFeed(ActionRequest actionRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getFeed(request);
	}

	public static void getFeed(RenderRequest renderRequest) throws Exception {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getFeed(request);
	}

	public static void getFeed(HttpServletRequest request) throws Exception {
		long groupId = ParamUtil.getLong(request, "groupId");
		String feedId = ParamUtil.getString(request, "feedId");

		JournalFeed feed = null;

		if (Validator.isNotNull(feedId)) {
			feed = JournalFeedServiceUtil.getFeed(groupId, feedId);
		}

		request.setAttribute(WebKeys.JOURNAL_FEED, feed);
	}

	public static void getStructure(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getStructure(request);

		JournalStructure structure =
			(JournalStructure)actionRequest.getAttribute(
				WebKeys.JOURNAL_STRUCTURE);

		JournalUtil.addRecentStructure(actionRequest, structure);
	}

	public static void getStructure(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getStructure(request);

		JournalStructure structure =
			(JournalStructure)renderRequest.getAttribute(
				WebKeys.JOURNAL_STRUCTURE);

		JournalUtil.addRecentStructure(renderRequest, structure);
	}

	public static void getStructure(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String structureId = ParamUtil.getString(request, "structureId");

		JournalStructure structure = null;

		if (Validator.isNotNull(structureId)) {
			structure = JournalStructureServiceUtil.getStructure(
				groupId, structureId);
		}

		request.setAttribute(WebKeys.JOURNAL_STRUCTURE, structure);
	}

	public static void getTemplate(ActionRequest actionRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		getTemplate(request);

		JournalTemplate template = (JournalTemplate)actionRequest.getAttribute(
			WebKeys.JOURNAL_TEMPLATE);

		JournalUtil.addRecentTemplate(actionRequest, template);
	}

	public static void getTemplate(RenderRequest renderRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		getTemplate(request);

		JournalTemplate template = (JournalTemplate)renderRequest.getAttribute(
			WebKeys.JOURNAL_TEMPLATE);

		JournalUtil.addRecentTemplate(renderRequest, template);
	}

	public static void getTemplate(HttpServletRequest request)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String templateId = ParamUtil.getString(request, "templateId");

		JournalTemplate template = null;

		if (Validator.isNotNull(templateId)) {
			template = JournalTemplateServiceUtil.getTemplate(
				groupId, templateId);
		}

		request.setAttribute(WebKeys.JOURNAL_TEMPLATE, template);
	}

}