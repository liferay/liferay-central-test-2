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

package com.liferay.portal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.util.Http;
import com.liferay.util.HttpUtil;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.UploadServletRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
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

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Boolean layoutDefault = (Boolean)req.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if ((layoutDefault != null) && (layoutDefault.booleanValue())) {
			Layout requestedLayout =
				(Layout)req.getAttribute(WebKeys.REQUESTED_LAYOUT);

			if (requestedLayout != null) {
				String redirectParam = "redirect";
				
				if (Validator.isNotNull(PropsValues.AUTH_LOGIN_PORTLET_NAME) &&
					Validator.isNotNull(PropsValues.AUTH_LOGIN_URL)) {

					redirectParam =
						PortalUtil.getPortletNamespace(
							PropsValues.AUTH_LOGIN_PORTLET_NAME) +
						redirectParam;
				}

				String url = PortalUtil.getLayoutURL(
					requestedLayout, themeDisplay);

				String redirect = HttpUtil.addParameter(
					themeDisplay.getURLSignIn(), redirectParam, url);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect requested layout to " + redirect);
				}

				res.sendRedirect(redirect);
			}
			else {
				String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect default layout to " + redirect);
				}

				res.sendRedirect(redirect);
			}

			return null;
		}

		long plid = ParamUtil.getLong(req, "p_l_id");

		if (plid > 0) {
			return processLayout(mapping, req, res, plid);
		}
		else {
			try {
				forwardLayout(req);

				return mapping.findForward(ActionConstants.COMMON_FORWARD);
			}
			catch (Exception e) {
				req.setAttribute(PageContext.EXCEPTION, e);

				return mapping.findForward(ActionConstants.COMMON_ERROR);
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

	protected void includeLayoutContent(
			HttpServletRequest req, HttpServletResponse res,
			ThemeDisplay themeDisplay, Layout layout)
		throws Exception {

		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		String path = StrutsUtil.TEXT_HTML_DIR;

		if (BrowserSniffer.is_wap_xhtml(req)) {
			path = StrutsUtil.TEXT_WAP_DIR;
		}

		// Manually check the p_p_id. See LEP-1724.

		if (themeDisplay.isStateExclusive() ||
			Validator.isNotNull(ParamUtil.getString(req, "p_p_id"))) {

			path += "/portal/layout/view/portlet.jsp";
		}
		else {
			path += PortalUtil.getLayoutViewPage(layout);
		}

		RequestDispatcher rd = ctx.getRequestDispatcher(path);

		StringServletResponse stringServletRes = new StringServletResponse(res);

		rd.include(req, stringServletRes);

		req.setAttribute(WebKeys.LAYOUT_CONTENT, stringServletRes.getString());
	}

	protected ActionForward processLayout(
			ActionMapping mapping, HttpServletRequest req,
			HttpServletResponse res, long plid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		boolean resetLayout = ParamUtil.getBoolean(
			req, "p_l_reset", PropsValues.LAYOUT_DEFAULT_P_L_RESET);

		try {
			if (resetLayout) {
				RenderParametersPool.clear(req, plid);
			}

			if (themeDisplay.isLifecycleAction()) {
				Portlet portlet = processPortletRequest(
					req, res, PortletRequest.ACTION_PHASE);

				if (portlet != null) {
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
			}
			else if (themeDisplay.isLifecycleRender()) {
				processPortletRequest(req, res, PortletRequest.RENDER_PHASE);
			}

			if (layout != null) {

				// Include layout content before the page loads because
				// portlets on the page can set the page title and page
				// subtitle

				includeLayoutContent(req, res, themeDisplay, layout);

				if (themeDisplay.isStateExclusive()) {
					serveExclusiveResource(req, res, themeDisplay);

					return null;
				}
			}

			return mapping.findForward("portal.layout");
		}
		catch (Exception e) {
			req.setAttribute(PageContext.EXCEPTION, e);

			return mapping.findForward(ActionConstants.COMMON_ERROR);
		}
		finally {
			try {
				if (themeDisplay.isLifecycleAction()) {
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
				if (themeDisplay.isLifecycleAction()) {
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

	protected Portlet processPortletRequest(
			HttpServletRequest req, HttpServletResponse res, String lifecycle)
		throws Exception {

		HttpSession ses = req.getSession();

		long companyId = PortalUtil.getCompanyId(req);
		User user = PortalUtil.getUser(req);
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);
		String portletId = ParamUtil.getString(req, "p_p_id");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			return null;
		}

		ServletContext ctx = (ServletContext)req.getAttribute(WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactory.create(
			portlet, ctx);

		if (user != null) {
			InvokerPortlet.clearResponse(
				ses, layout.getPrimaryKey(), portletId,
				LanguageUtil.getLanguageId(req));
		}

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
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

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);

			if (_log.isDebugEnabled()) {
				_log.debug("Content type " + contentType);
			}

			UploadServletRequest uploadReq = null;

			try {
				if ((contentType != null) &&
					(contentType.startsWith(
						ContentTypes.MULTIPART_FORM_DATA))) {

					if (!invokerPortlet.getPortletConfig().isWARFile() ||
						invokerPortlet.isStrutsPortlet()) {

						uploadReq = new UploadServletRequest(req);

						req = uploadReq;
					}
				}

				ActionRequestImpl actionRequestImpl =
					ActionRequestFactory.create(
						req, portlet, invokerPortlet, portletCtx, windowState,
						portletMode, portletPreferences, layout.getPlid());

				ActionResponseImpl actionResponseImpl =
					ActionResponseFactory.create(
						actionRequestImpl, res, portletId, user, layout,
						windowState, portletMode);

				actionRequestImpl.defineObjects(
					portletConfig, actionResponseImpl);

				invokerPortlet.processAction(
					actionRequestImpl, actionResponseImpl);

				RenderParametersPool.put(
					req, layout.getPlid(), portletId,
					actionResponseImpl.getRenderParameterMap());
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
			layout.getLayoutId(), PortletRequest.RENDER_PHASE);

		Map<String, String[]> renderParameters =
			actionResponseImpl.getRenderParameterMap();

		for (Map.Entry<String, String[]> entry : renderParameters.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();

			portletURL.setParameter(key, value);
		}

		res.sendRedirect(portletURL.toString());
	}

	protected void serveExclusiveResource(
			HttpServletRequest req, HttpServletResponse res,
			ThemeDisplay themeDisplay)
		throws Exception {

		RenderRequestImpl renderRequestImpl = (RenderRequestImpl)
			req.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

		RenderResponseImpl renderResponseImpl = (RenderResponseImpl)
			req.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

		StringServletResponse stringServletResponse = (StringServletResponse)
			renderRequestImpl.getAttribute(WebKeys.STRING_SERVLET_RESPONSE);

		renderResponseImpl.transferHeaders(res);

		if (stringServletResponse.isCalledGetOutputStream()) {
			InputStream is = new ByteArrayInputStream(
				stringServletResponse.getByteArrayMaker().toByteArray());

			ServletResponseUtil.sendFile(
				res, renderResponseImpl.getResourceName(), is,
				renderResponseImpl.getContentType());
		}
		else {
			byte[] content = stringServletResponse.getString().getBytes(
				StringPool.UTF8);

			ServletResponseUtil.sendFile(
				res, renderResponseImpl.getResourceName(), content,
				renderResponseImpl.getContentType());
		}

		RenderRequestFactory.recycle(renderRequestImpl);
		RenderResponseFactory.recycle(renderResponseImpl);
	}

	private static Log _log = LogFactory.getLog(LayoutAction.class);

}