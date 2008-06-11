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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.tags.TagsEntryException;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.PageTitleException;
import com.liferay.portlet.wiki.PageVersionException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class EditPageAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		WikiPage page = null;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				page = updatePage(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deletePage(req);
			}
			else if (cmd.equals(Constants.REVERT)) {
				revertPage(req);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribePage(req);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribePage(req);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(req, "redirect");

				if (page != null) {
					boolean saveAndContinue = ParamUtil.getBoolean(
						req, "saveAndContinue");

					if (saveAndContinue) {
						redirect = getSaveAndContinueRedirect(
							config, req, page, redirect);
					}
					else if (redirect.endsWith("title=")) {
						redirect += page.getTitle();
					}
				}

				sendRedirect(req, res, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof NoSuchPageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.wiki.error");
			}
			else if (e instanceof PageContentException ||
					 e instanceof PageVersionException ||
					 e instanceof PageTitleException) {

				SessionErrors.add(req, e.getClass().getName());
			}
			else if (e instanceof TagsEntryException) {
				SessionErrors.add(req, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getNode(req);
			getPage(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchNodeException ||
				e instanceof PageTitleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.wiki.error");
			}
			else if (e instanceof NoSuchPageException) {

				// Let edit_page.jsp handle this case

			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(req, "portlet.wiki.edit_page"));
	}

	protected void deletePage(ActionRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");

		WikiPageServiceUtil.deletePage(nodeId, title);
	}

	protected void getPage(RenderRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		double version = ParamUtil.getDouble(req, "version");
		boolean removeRedirect = ParamUtil.getBoolean(req, "removeRedirect");

		if (nodeId == 0) {
			WikiNode node = (WikiNode)req.getAttribute(WebKeys.WIKI_NODE);

			if (node != null) {
				nodeId = node.getNodeId();
			}
		}

		WikiPage page = null;

		if (Validator.isNotNull(title)) {
			try {
				page = WikiPageServiceUtil.getPage(nodeId, title, version);
			}
			catch (NoSuchPageException nspe) {
				if (title.equals(WikiPageImpl.FRONT_PAGE) && (version == 0)) {
					page = WikiPageServiceUtil.addPage(
						nodeId, title, null, null, null);
				}
				else {
					throw nspe;
				}
			}

			if (removeRedirect) {
				page.setContent(StringPool.BLANK);
				page.setRedirectTitle(StringPool.BLANK);
			}
		}

		req.setAttribute(WebKeys.WIKI_PAGE, page);
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig config, ActionRequest req, WikiPage page,
			String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String originalRedirect = ParamUtil.getString(req, "originalRedirect");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)req, config.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(layout.getGroupId()), false);
		portletURL.setParameter(
			"nodeId", String.valueOf(page.getNodeId()), false);
		portletURL.setParameter("title", page.getTitle(), false);

		return portletURL.toString();
	}

	protected void revertPage(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences prefs = req.getPreferences();

		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		double version = ParamUtil.getDouble(req, "version");

		WikiPageServiceUtil.revertPage(
			nodeId, title, version, prefs, themeDisplay);
	}

	protected void subscribePage(ActionRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");

		WikiPageServiceUtil.subscribePage(nodeId, title);
	}

	protected void unsubscribePage(ActionRequest req) throws Exception {
		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");

		WikiPageServiceUtil.unsubscribePage(nodeId, title);
	}

	protected WikiPage updatePage(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences prefs = req.getPreferences();

		long nodeId = ParamUtil.getLong(req, "nodeId");
		String title = ParamUtil.getString(req, "title");
		double version = ParamUtil.getDouble(req, "version");

		String content = ParamUtil.getString(req, "content");
		String format = ParamUtil.getString(req, "format");
		String parentTitle = ParamUtil.getString(req, "parentTitle");
		String redirectTitle = null;

		String[] tagsEntries = StringUtil.split(
			ParamUtil.getString(req, "tagsEntries"));

		return WikiPageServiceUtil.updatePage(
			nodeId, title, version, content, format, parentTitle, redirectTitle,
			tagsEntries, prefs, themeDisplay);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}