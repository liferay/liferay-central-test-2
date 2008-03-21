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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.util.diff.DiffResult;
import com.liferay.util.diff.DiffUtil;
import com.liferay.util.servlet.SessionErrors;

import java.io.StringReader;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="CompareVersionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class CompareVersionsAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getNode(req);
			ActionUtil.getPage(req);

			compareVersions(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPageException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward("portlet.wiki.compare_versions");
	}

	protected void compareVersions(RenderRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");

		String title = ParamUtil.getString(req, "title");

		double sourceVersion = ParamUtil.getDouble(req, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(req, "targetVersion");
		String type = ParamUtil.getString(req, "type", "escape");

		WikiPage sourcePage = WikiPageServiceUtil.getPage(
			nodeId, title, sourceVersion);
		WikiPage targetPage = WikiPageServiceUtil.getPage(
			nodeId, title, targetVersion);

		String sourceContent = sourcePage.getContent();
		String targetContent = targetPage.getContent();

		sourceContent = WikiUtil.processContent(sourceContent);
		targetContent = WikiUtil.processContent(targetContent);

		if (type.equals("escape")) {
			sourceContent = HtmlUtil.escape(sourceContent);
			targetContent = HtmlUtil.escape(targetContent);
		}
		else if (type.equals("strip")) {
			sourceContent = HtmlUtil.stripHtml(sourceContent);
			targetContent = HtmlUtil.stripHtml(targetContent);
		}

		List<DiffResult>[] diffResults = DiffUtil.diff(
			new StringReader(sourceContent), new StringReader(targetContent));

		req.setAttribute(
			WebKeys.SOURCE_NAME, title + StringPool.SPACE + sourceVersion);
		req.setAttribute(
			WebKeys.TARGET_NAME, title + StringPool.SPACE + targetVersion);
		req.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
	}

}