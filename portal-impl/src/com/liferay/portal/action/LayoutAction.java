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

package com.liferay.portal.action;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.CachePortlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.util.Http;
import com.liferay.util.HttpHeaders;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.UploadServletRequest;

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
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		Boolean layoutDefault  = (Boolean)req.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if ((layoutDefault != null) && (layoutDefault.booleanValue())) {
			Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect default layout to " + redirect);
			}

			res.sendRedirect(redirect);

			return null;
		}

		long plid = ParamUtil.getLong(req, "p_l_id");
		boolean resetLayout = ParamUtil.getBoolean(
			req, "p_l_reset", PortalUtil.DEFAULT_P_L_RESET);
		String action = ParamUtil.getString(req, "p_p_action");

		if (plid > 0) {
			try {
				if (resetLayout) {
					RenderParametersPool.clear(req, plid);
				}

				if (action.equals("1")) {
					Portlet portlet = processActionRequest(req, res);

					ActionResponseImpl actionResponseImpl =
						(ActionResponseImpl)req.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE);

					String redirectLocation =
						actionResponseImpl.getRedirectLocation();

					if (Validator.isNotNull(redirectLocation)) {
						res.sendRedirect(redirectLocation);

						return null;
					}

					if (portlet.isActionURLRedirect()) {
						redirectActionURL(
							req, res, actionResponseImpl, portlet);
					}
				}
				else if (action.equals("0")) {
					processRenderRequest(req, res);
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
								JavaConstants.JAVAX_PORTLET_REQUEST);

						ActionRequestFactory.recycle(actionRequestImpl);
					}
				}
				catch (Exception e) {
					_log.error(e);
				}

				req.removeAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

				try {
					if (action.equals("1")) {
						ActionResponseImpl actionResponseImpl =
							(ActionResponseImpl)req.getAttribute(
								JavaConstants.JAVAX_PORTLET_RESPONSE);

						ActionResponseFactory.recycle(actionResponseImpl);
					}
				}
				catch (Exception e) {
					_log.error(e);
				}

				req.removeAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);
			}
		}
		else {
			try {
				forwardLayout(req);

				return mapping.findForward(Constants.COMMON_FORWARD);
			}
			catch (Exception e) {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(Constants.COMMON_ERROR);
			}
		}
	}

	protected void forwardLayout(HttpServletRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);
		long plid = LayoutImpl.DEFAULT_PLID;
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

		if (_log.isDebugEnabled()) {
			_log.debug("Forward layout to " + forwardURL);
		}

		req.setAttribute(WebKeys.FORWARD_URL, forwardURL);
	}

	protected Portlet processPortletRequest(
			HttpServletRequest req, HttpServletResponse res, boolean action)
		throws Exception {

		HttpSession ses = req.getSession();

		long companyId = PortalUtil.getCompanyId(req);
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

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactory.getPortletPreferencesIds(
				req, portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactory.create(portlet, ctx);
		PortletContext portletCtx = portletConfig.getPortletContext();

		WindowState windowState = new WindowState(
			ParamUtil.getString(req, "p_p_state"));

		PortletMode portletMode = new PortletMode(
			ParamUtil.getString(req, "p_p_mode"));

		if (action) {
			String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);

			if (_log.isDebugEnabled()) {
				_log.debug("Content type " + contentType);
			}

			UploadServletRequest uploadReq = null;

			try {
				if ((contentType != null) &&
					(contentType.startsWith(
						Constants.MULTIPART_FORM_DATA))) {

					if (!cachePortlet.getPortletConfig().isWARFile() ||
						cachePortlet.isStrutsPortlet()) {

						uploadReq = new UploadServletRequest(req);

						req = uploadReq;
					}
				}

				ActionRequestImpl actionRequestImpl =
					ActionRequestFactory.create(
						req, portlet, cachePortlet, portletCtx, windowState,
						portletMode, portletPreferences, layout.getPlid());

				ActionResponseImpl actionResponseImpl =
					ActionResponseFactory.create(
						actionRequestImpl, res, portletId, user, layout,
						windowState, portletMode);

				actionRequestImpl.defineObjects(
					portletConfig, actionResponseImpl);

				cachePortlet.processAction(
					actionRequestImpl, actionResponseImpl);

				RenderParametersPool.put(
					req, layout.getPlid(), portletId,
					actionResponseImpl.getRenderParameters());
			}
			finally {
				if (uploadReq != null) {
					uploadReq.cleanUp();
				}
			}
		}
		else {
			PortalUtil.updateWindowState(
				portletId, user, layout, windowState, req);

			PortalUtil.updatePortletMode(
				portletId, user, layout, portletMode, req);
		}

		return portlet;
	}

	protected Portlet processActionRequest(
			HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		return processPortletRequest(req, res, true);
	}

	protected Portlet processRenderRequest(
			HttpServletRequest req, HttpServletResponse res)
		throws Exception {

		return processPortletRequest(req, res, false);
	}

	protected void redirectActionURL(
			HttpServletRequest req, HttpServletResponse res,
			ActionResponseImpl actionResponseImpl, Portlet portlet)
		throws Exception {

		ActionRequestImpl actionRequestImpl =
			(ActionRequestImpl)req.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

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