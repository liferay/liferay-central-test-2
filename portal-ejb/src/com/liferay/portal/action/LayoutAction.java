/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.action;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.spring.PortletLocalServiceUtil;
import com.liferay.portal.service.spring.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.LiferayWindowState;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.util.Http;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="LayoutAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		String plid = ParamUtil.getString(req, "p_l_id");
		String action = ParamUtil.getString(req, "p_p_action");

		if (Validator.isNotNull(plid)) {
			try {
				if (action.equals("1")) {
					Portlet portlet = _processActionRequest(req, res);

					ActionResponseImpl actionResponseImpl =
						(ActionResponseImpl)req.getAttribute(
							WebKeys.JAVAX_PORTLET_RESPONSE);

					String redirectLocation =
						actionResponseImpl.getRedirectLocation();

					if (Validator.isNotNull(redirectLocation)) {
						res.sendRedirect(redirectLocation);

						return null;
					}

					if (LiferayWindowState.isExclusive(req)) {
						return null;
					}

					if (portlet.isActionURLRedirect()) {
						_redirectActionURL(
							req, res, actionResponseImpl, portlet);
					}
				}
				else if (action.equals("0")) {
					_processRenderRequest(req, res);
				}

				return mapping.findForward("portal.layout");
			}
			catch (Exception e) {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(Constants.COMMON_ERROR);
			}
			finally {
				try {
					if (action.equals("1")) {
						ActionRequestImpl actionRequestImpl =
							(ActionRequestImpl)req.getAttribute(
								WebKeys.JAVAX_PORTLET_REQUEST);

						ActionRequestFactory.recycle(actionRequestImpl);
					}
				}
				catch (Exception e) {
					_log.error(e);
				}

				try {
					if (action.equals("1")) {
						ActionResponseImpl actionResponseImpl =
							(ActionResponseImpl)req.getAttribute(
								WebKeys.JAVAX_PORTLET_RESPONSE);

						ActionResponseFactory.recycle(actionResponseImpl);
					}
				}
				catch (Exception e) {
					_log.error(e);
				}
			}
		}
		else {
			try {
				_forwardLayout(req);

				return mapping.findForward(Constants.COMMON_FORWARD);
			}
			catch (Exception e) {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(Constants.COMMON_ERROR);
			}
		}
	}

	private void _forwardLayout(HttpServletRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);
		String plid = Layout.DEFAULT_PLID;
		String layoutFriendlyURL = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		if (layout != null) {
			plid = layout.getPlid();
			layoutFriendlyURL =
				PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
		}

		String forwardURL = layoutFriendlyURL;

		if (Validator.isNull(forwardURL)) {
			forwardURL =
				themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid;

			if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				forwardURL = Http.addParameter(
					forwardURL, "doAsUserId", themeDisplay.getDoAsUserId());
			}
		}

		req.setAttribute(WebKeys.FORWARD_URL, forwardURL);
	}

	private Portlet _processPortletRequest(
			HttpServletRequest req, HttpServletResponse res, boolean action)
		throws Exception {

		HttpSession ses = req.getSession();

		String companyId = PortalUtil.getCompanyId(req);
		User user = PortalUtil.getUser(req);
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);
		String portletId = ParamUtil.getString(req, "p_p_id");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		CachePortlet cachePortlet = PortletInstanceFactory.create(portlet, ctx);

		if (user != null) {
			CachePortlet.clearResponse(ses, layout.getPrimaryKey(), portletId);
		}

		PortletPreferencesPK prefsPK =
			PortletPreferencesFactory.getPortletPreferencesPK(req, portletId);

		PortletPreferences prefs =
			PortletPreferencesLocalServiceUtil.getPreferences(
				companyId, prefsPK);

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);
		PortletContext portletCtx = portletConfig.getPortletContext();

		WindowState windowState = new WindowState(
			ParamUtil.getString(req, "p_p_state"));

		PortletMode portletMode = new PortletMode(
			ParamUtil.getString(req, "p_p_mode"));

		if (action) {
			ActionRequestImpl actionRequestImpl = ActionRequestFactory.create(
				req, portlet, cachePortlet, portletCtx, windowState,
				portletMode, prefs, layout.getPlid());

			ActionResponseImpl actionResponseImpl =
				ActionResponseFactory.create(
					actionRequestImpl, res, portletId, user, layout,
					windowState, portletMode);

			actionRequestImpl.defineObjects(portletConfig, actionResponseImpl);

			cachePortlet.processAction(actionRequestImpl, actionResponseImpl);

			RenderParametersPool.put(
				req, layout.getPlid(), portletId,
				actionResponseImpl.getRenderParameters());
		}
		else {
			PortalUtil.updateWindowState(
				portletId, user, layout, windowState, req);

			PortalUtil.updatePortletMode(portletId, user, layout, portletMode);
		}

		return portlet;
	}

	private Portlet _processActionRequest(
			HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		return _processPortletRequest(req, res, true);
	}

	private Portlet _processRenderRequest(
			HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		return _processPortletRequest(req, res, false);
	}

	private void _redirectActionURL(
			HttpServletRequest req, HttpServletResponse res,
			ActionResponseImpl actionResponseImpl, Portlet portlet)
		throws Exception {

		ActionRequestImpl actionRequestImpl =
			(ActionRequestImpl)req.getAttribute(WebKeys.JAVAX_PORTLET_REQUEST);

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		PortletURL portletURL = new PortletURLImpl(
			actionRequestImpl, actionRequestImpl.getPortletName(),
			layout.getLayoutId(), false);

		Map renderParameters = actionResponseImpl.getRenderParameters();

		Iterator itr = renderParameters.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			Object value = entry.getValue();

			if (value instanceof String) {
				portletURL.setParameter(key, (String)value);
			}
			else if (value instanceof String[]) {
				portletURL.setParameter(key, (String[])value);
			}
		}

		res.sendRedirect(portletURL.toString());
	}

	private static Log _log = LogFactory.getLog(LayoutAction.class);

}