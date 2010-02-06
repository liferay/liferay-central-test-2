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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="FindPageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Samuel Kong
 */
public class FindPageAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			long pageResourcePrimKey = ParamUtil.getLong(
				request, "pageResourcePrimKey");

			plid = getPlid(plid, pageResourcePrimKey);

			WikiPageResource pageResource =
				WikiPageResourceLocalServiceUtil.getPageResource(
					pageResourcePrimKey);

			WikiNode node = WikiNodeLocalServiceUtil.getNode(
				pageResource.getNodeId());

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.WIKI, plid, PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("struts_action", "/wiki/view");
			portletURL.setParameter("nodeName", node.getName());
			portletURL.setParameter("title", pageResource.getTitle());

			response.sendRedirect(portletURL.toString());

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected long getPlid(long plid, long pageResourcePrimKey)
		throws Exception {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(PortletKeys.WIKI)) {
					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				pageResourcePrimKey);

		WikiNode node = WikiNodeLocalServiceUtil.getNode(
			pageResource.getNodeId());

		plid = PortalUtil.getPlidFromPortletId(
			node.getGroupId(), PortletKeys.WIKI);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}
		else {
			throw new NoSuchLayoutException(
				"No page was found with the Wiki portlet.");
		}
	}

}