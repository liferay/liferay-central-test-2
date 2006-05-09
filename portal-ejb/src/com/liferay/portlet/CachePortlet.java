/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.model.Layout;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.shared.servlet.PortletServlet;
import com.liferay.portal.shared.util.ClassUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Time;
import com.liferay.util.servlet.StringServletResponse;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="CachePortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 *
 */
public class CachePortlet implements Portlet {

	public static void clearResponse(
		HttpSession ses, LayoutPK layoutPK, String portletId) {

		String sesResponseId =
			layoutPK.getLayoutId() + StringPool.UNDERLINE + portletId;

		getResponses(ses).remove(sesResponseId);
	}

	public static void clearResponses(HttpSession ses) {
		getResponses(ses).clear();
	}

	public static void clearResponses(PortletSession ses) {
		getResponses(ses).clear();
	}

	public static Map getResponses(HttpSession ses) {
		Map responses = (Map)ses.getAttribute(WebKeys.CACHE_PORTLET_RESPONSES);

		if (responses == null) {
			responses = CollectionFactory.getHashMap();

			ses.setAttribute(WebKeys.CACHE_PORTLET_RESPONSES, responses);
		}

		return responses;
	}

	public static Map getResponses(PortletSession ses) {
		return getResponses(((PortletSessionImpl)ses).getSession());
	}

	public CachePortlet(Portlet portlet, PortletContext portletCtx,
						Integer expCache) {

		_portlet = portlet;
		_portletCtx = (PortletContextImpl)portletCtx;
		_expCache = expCache;

		if (ClassUtil.isSubclass(_portlet.getClass(), Constants.JSF_MYFACES) ||
			ClassUtil.isSubclass(_portlet.getClass(), Constants.JSF_SUN)) {

			_facesPortlet = true;
		}

		_strutsPortlet = ClassUtil.isSubclass(
			portlet.getClass(), StrutsPortlet.class);
	}

	public void init(PortletConfig config) throws PortletException {
		_portletConfig = (PortletConfigImpl)config;

		_portletId = _portletConfig.getPortletId();

		ClassLoader classLoader = _getPortletClassLoader();

		if (classLoader != null) {
			Thread.currentThread().setContextClassLoader(classLoader);
		}

		_portlet.init(config);

		if (classLoader != null) {
			Thread.currentThread().setContextClassLoader(
				classLoader.getParent());
		}

		_destroyable = true;
	}

	public void processAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {

		_invoke(req, res, true);
	}

	public void render(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		String userId = req.getRemoteUser();

		if ((userId == null) || (_expCache == null) ||
			(_expCache.intValue() == 0)) {

			_invoke(req, res, false);
		}
		else {
			RenderResponseImpl resImpl = (RenderResponseImpl)res;

			StringServletResponse stringServletRes =
				(StringServletResponse)resImpl.getHttpServletResponse();

			PortletSession ses = req.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

			Map sesResponses = getResponses(ses);

			String sesResponseId =
				layout.getLayoutId() + StringPool.UNDERLINE + _portletId;

			CachePortletResponse response =
				(CachePortletResponse)sesResponses.get(sesResponseId);

			if (response == null) {
				_invoke(req, res, false);

				response = new CachePortletResponse(
					resImpl.getTitle(),
					stringServletRes.getString(),
					now + Time.SECOND * _expCache.intValue());

				sesResponses.put(sesResponseId, response);
			}
			else if ((response.getTime() < now) &&
					 (_expCache.intValue() > 0)) {

				_invoke(req, res, false);

				response.setTitle(resImpl.getTitle());
				response.setContent(stringServletRes.getString());
				response.setTime(now + Time.SECOND * _expCache.intValue());
			}
			else {
				resImpl.setTitle(response.getTitle());
				stringServletRes.getWriter().print(response.getContent());
			}
		}
	}

	public void destroy() {
		if (_destroyable) {
			ClassLoader classLoader = _getPortletClassLoader();

			if (classLoader != null) {
				Thread.currentThread().setContextClassLoader(classLoader);
			}

			_portlet.destroy();

			if (classLoader != null) {
				Thread.currentThread().setContextClassLoader(
					classLoader.getParent());
			}
		}

		_destroyable = false;
	}

	public Portlet getPortletInstance() {
		return _portlet;
	}

	public PortletConfigImpl getPortletConfig() {
		return _portletConfig;
	}

	public PortletContextImpl getPortletContext() {
		return _portletCtx;
	}

	public boolean isDestroyable() {
		return _destroyable;
	}

	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	public boolean isStrutsPortlet() {
		return _strutsPortlet;
	}

	private ClassLoader _getPortletClassLoader() {
		return (ClassLoader)_portletCtx.getAttribute(
			PortletServlet.PORTLET_CLASS_LOADER);
	}

	private void _invoke(
			PortletRequest req, PortletResponse res, boolean action)
		throws IOException, PortletException {

		Map properties = null;

		if (_portletConfig.isWARFile()) {
			String path =
				StringPool.SLASH + _portletConfig.getPortletName() + "/invoke";

			RequestDispatcher rd =
				_portletCtx.getServletContext().getRequestDispatcher(path);

			HttpServletRequest httpReq = null;
			HttpServletResponse httpRes = null;

			ActionRequestImpl actionReqImpl = null;
			ActionResponseImpl actionResImpl = null;

			RenderRequestImpl renderReqImpl = null;
			RenderResponseImpl renderResImpl = null;

			if (action) {
				actionReqImpl = (ActionRequestImpl)req;
				actionResImpl = (ActionResponseImpl)res;

				httpReq = actionReqImpl.getHttpServletRequest();
				httpRes = actionResImpl.getHttpServletResponse();
			}
			else {
				renderReqImpl = (RenderRequestImpl)req;
				renderResImpl = (RenderResponseImpl)res;

				httpReq = renderReqImpl.getHttpServletRequest();
				httpRes = renderResImpl.getHttpServletResponse();
			}

			httpReq.setAttribute(WebKeys.JAVAX_PORTLET_PORTLET, _portlet);

			try {
				rd.include(httpReq, httpRes);
			}
			catch (ServletException se) {
				Throwable cause = se.getRootCause();

				if (cause instanceof PortletException) {
					throw (PortletException)cause;
				}

				throw new PortletException(cause);
			}

			if (action) {
				properties = actionResImpl.getProperties();
			}
			else {
				properties = renderResImpl.getProperties();
			}
		}
		else {
			if (action) {
				ActionRequestImpl actionReqImpl = (ActionRequestImpl)req;
				ActionResponseImpl actionResImpl = (ActionResponseImpl)res;

				_portlet.processAction(actionReqImpl, actionResImpl);

				properties = actionResImpl.getProperties();
			}
			else {
				RenderRequestImpl renderReqImpl = (RenderRequestImpl)req;
				RenderResponseImpl renderResImpl = (RenderResponseImpl)res;

				_portlet.render(renderReqImpl, renderResImpl);

				properties = renderResImpl.getProperties();
			}
		}

		if ((properties != null) && (properties.size() > 0)) {
			String expCache = (String)properties.get(
				RenderResponse.EXPIRATION_CACHE);

			if ((expCache != null) && (_expCache != null)) {
				_expCache = new Integer(GetterUtil.getInteger(expCache));
			}
		}
	}

	private String _portletId;
	private Portlet _portlet;
	private PortletConfigImpl _portletConfig;
	private PortletContextImpl _portletCtx;
	private Integer _expCache;
	private boolean _destroyable;
	private boolean _facesPortlet;
	private boolean _strutsPortlet;

}