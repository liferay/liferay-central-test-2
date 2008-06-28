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

package com.liferay.portlet;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Layout;
import com.liferay.portal.tools.PortletDeployer;
import com.liferay.portal.util.WebKeys;

import com.sun.portal.portletcontainer.appengine.filter.FilterChainImpl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="InvokerPortlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 *
 */
public class InvokerPortlet
	implements EventPortlet, Portlet, ResourceServingPortlet {

	public static void clearResponse(
		HttpSession ses, long plid, String portletId, String languageId) {

		String sesResponseId = encodeResponseKey(plid, portletId, languageId);

		getResponses(ses).remove(sesResponseId);
	}

	public static void clearResponses(HttpSession ses) {
		getResponses(ses).clear();
	}

	public static void clearResponses(PortletSession ses) {
		getResponses(ses).clear();
	}

	public static String encodeResponseKey(
		long plid, String portletId, String languageId) {

		StringBuilder sb = new StringBuilder();

		sb.append(plid);
		sb.append(StringPool.UNDERLINE);
		sb.append(portletId);
		sb.append(StringPool.UNDERLINE);
		sb.append(languageId);

		return sb.toString();
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		HttpSession ses) {

		Map<String, InvokerPortletResponse> responses =
			(Map<String, InvokerPortletResponse>)ses.getAttribute(
				WebKeys.CACHE_PORTLET_RESPONSES);

		if (responses == null) {
			responses = new HashMap<String, InvokerPortletResponse>();

			ses.setAttribute(WebKeys.CACHE_PORTLET_RESPONSES, responses);
		}

		return responses;
	}

	public static Map<String, InvokerPortletResponse> getResponses(
		PortletSession ses) {

		return getResponses(((PortletSessionImpl)ses).getHttpSession());
	}

	public InvokerPortlet(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletContext portletCtx)
		throws PortletException {

		_portletModel = portletModel;
		_portlet = portlet;
		_portletCtx = (PortletContextImpl)portletCtx;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Create root cache wrapper for " +
					_portletCtx.getPortlet().getPortletId());
		}

		if (ClassUtil.isSubclass(
				_portlet.getClass(), PortletDeployer.JSF_MYFACES) ||
			ClassUtil.isSubclass(
				_portlet.getClass(), PortletDeployer.JSF_SUN)) {

			_facesPortlet = true;
		}

		_strutsPortlet = ClassUtil.isSubclass(
			portlet.getClass(), StrutsPortlet.class);
		_strutsBridgePortlet = ClassUtil.isSubclass(
			portlet.getClass(),
			"org.apache.portals.bridges.struts.StrutsPortlet");
		_expCache = portletModel.getExpCache();
		setPortletFilters();
	}

	public InvokerPortlet(
			com.liferay.portal.model.Portlet portletModel, Portlet portlet,
			PortletConfig portletConfig, PortletContext portletCtx,
			boolean facesPortlet, boolean strutsPortlet,
			boolean strutsBridgePortlet)
		throws PortletException {

		// From constructor

		_portletModel = portletModel;
		_portlet = portlet;
		_portletCtx = (PortletContextImpl)portletCtx;
		_facesPortlet = facesPortlet;
		_strutsPortlet = strutsPortlet;
		_strutsBridgePortlet = strutsBridgePortlet;
		_expCache = portletModel.getExpCache();
		setPortletFilters();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Create instance cache wrapper for " +
					_portletCtx.getPortlet().getPortletId());
		}

		// From init

		_portletConfig = (PortletConfigImpl)portletConfig;

		_portletId = _portletConfig.getPortletId();
	}

	public void destroy() {
		if (_destroyable) {
			ClassLoader contextClassLoader =
				Thread.currentThread().getContextClassLoader();

			ClassLoader portletClassLoader = getPortletClassLoader();

			try {
				if (portletClassLoader != null) {
					Thread.currentThread().setContextClassLoader(
						portletClassLoader);
				}

				_portlet.destroy();
			}
			finally {
				if (portletClassLoader != null) {
					Thread.currentThread().setContextClassLoader(
						contextClassLoader);
				}
			}
		}

		_destroyable = false;
	}

	public ClassLoader getPortletClassLoader() {
		return (ClassLoader)_portletCtx.getAttribute(
			PortletServlet.PORTLET_CLASS_LOADER);
	}

	public PortletConfigImpl getPortletConfig() {
		return _portletConfig;
	}

	public PortletContextImpl getPortletContext() {
		return _portletCtx;
	}

	public Portlet getPortletInstance() {
		return _portlet;
	}

	public Integer getExpCache() {
		return _expCache;
	}

	public void init(PortletConfig portletConfig) throws PortletException {
		_portletConfig = (PortletConfigImpl)portletConfig;

		_portletId = _portletConfig.getPortletId();

		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		ClassLoader portletClassLoader = getPortletClassLoader();

		try {
			if (portletClassLoader != null) {
				Thread.currentThread().setContextClassLoader(
					portletClassLoader);
			}

			_portlet.init(portletConfig);
		}
		finally {
			if (portletClassLoader != null) {
				Thread.currentThread().setContextClassLoader(
					contextClassLoader);
			}
		}

		_destroyable = true;
	}

	public boolean isDestroyable() {
		return _destroyable;
	}

	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	public boolean isStrutsBridgePortlet() {
		return _strutsBridgePortlet;
	}

	public boolean isStrutsPortlet() {
		return _strutsPortlet;
	}

	public void processAction(ActionRequest req, ActionResponse res)
		throws IOException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeAction(req, res);
		}
		catch (PortletException pe) {
			req.setAttribute(_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"processAction for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	public void processEvent(EventRequest req, EventResponse res)
		throws IOException, PortletException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		invokeEvent(req, res);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"processEvent for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	public void render(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		PortletException portletException = (PortletException)req.getAttribute(
			_portletId + PortletException.class.getName());

		if (portletException != null) {
			throw portletException;
		}

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		String remoteUser = req.getRemoteUser();

		if ((remoteUser == null) || (_expCache == null) ||
			(_expCache.intValue() == 0)) {

			invokeRender(req, res);
		}
		else {
			RenderResponseImpl resImpl = (RenderResponseImpl)res;

			StringServletResponse stringServletRes =
				(StringServletResponse)resImpl.getHttpServletResponse();

			PortletSession ses = req.getPortletSession();

			long now = System.currentTimeMillis();

			Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

			Map<String, InvokerPortletResponse> sesResponses =
				getResponses(ses);

			String sesResponseId = encodeResponseKey(
				layout.getPlid(), _portletId, LanguageUtil.getLanguageId(req));

			InvokerPortletResponse response = sesResponses.get(sesResponseId);

			if (response == null) {
				String title = invokeRender(req, res);

				response = new InvokerPortletResponse(
					title, stringServletRes.getString(),
					now + Time.SECOND * _expCache.intValue());

				sesResponses.put(sesResponseId, response);
			}
			else if ((response.getTime() < now) &&
					 (_expCache.intValue() > 0)) {

				String title = invokeRender(req, res);

				response.setTitle(title);
				response.setContent(stringServletRes.getString());
				response.setTime(now + Time.SECOND * _expCache.intValue());
			}
			else {
				resImpl.setTitle(response.getTitle());
				stringServletRes.getWriter().print(response.getContent());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"render for " + _portletId + " takes " + stopWatch.getTime() +
					" ms");
		}
	}

	public void serveResource(ResourceRequest req, ResourceResponse res)
		throws IOException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			invokeResource(req, res);
		}
		catch (PortletException pe) {
			req.setAttribute(_portletId + PortletException.class.getName(), pe);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"serveResource for " + _portletId + " takes " +
					stopWatch.getTime() + " ms");
		}
	}

	public void setPortletFilters() throws PortletException {
		Map<String, com.liferay.portal.model.PortletFilter> portletFilters =
			_portletModel.getPortletFilters();

		for (Map.Entry<String, com.liferay.portal.model.PortletFilter> entry :
				portletFilters.entrySet()) {

			com.liferay.portal.model.PortletFilter portletFilterModel =
				entry.getValue();

			PortletFilter portletFilter = PortletFilterFactory.create(
				portletFilterModel, _portletCtx);

			Set<String> lifecycles = portletFilterModel.getLifecycles();

			if (lifecycles.contains(PortletRequest.ACTION_PHASE)) {
				_actionFilters.add((ActionFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.EVENT_PHASE)) {
				_eventFilters.add((EventFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RENDER_PHASE)) {
				_renderFilters.add((RenderFilter)portletFilter);
			}

			if (lifecycles.contains(PortletRequest.RESOURCE_PHASE)) {
				_resourceFilters.add((ResourceFilter)portletFilter);
			}
		}
	}

	protected void invoke(
			PortletRequest req, PortletResponse res, String lifecycle)
		throws IOException, PortletException {

		FilterChain filterChain = null;

		Map<String, String[]> properties = null;

		if (_portletConfig.isWARFile()) {
			String path =
				StringPool.SLASH + _portletConfig.getPortletName() + "/invoke";

			RequestDispatcher rd =
				_portletCtx.getServletContext().getRequestDispatcher(path);

			HttpServletRequest httpReq = null;
			HttpServletResponse httpRes = null;

			ActionRequestImpl actionReqImpl = null;
			ActionResponseImpl actionResImpl = null;

			EventRequestImpl eventReqImpl = null;
			EventResponseImpl eventResImpl = null;

			RenderRequestImpl renderReqImpl = null;
			RenderResponseImpl renderResImpl = null;

			ResourceRequestImpl resourceReqImpl = null;
			ResourceResponseImpl resourceResImpl = null;

			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				actionReqImpl = (ActionRequestImpl)req;
				actionResImpl = (ActionResponseImpl)res;

				httpReq = actionReqImpl.getHttpServletRequest();
				httpRes = actionResImpl.getHttpServletResponse();

				filterChain = new FilterChainImpl(_portlet, _actionFilters);
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				eventReqImpl = (EventRequestImpl)req;
				eventResImpl = (EventResponseImpl)res;

				httpReq = eventReqImpl.getHttpServletRequest();
				httpRes = eventResImpl.getHttpServletResponse();

				filterChain = new FilterChainImpl(_portlet, _eventFilters);
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				renderReqImpl = (RenderRequestImpl)req;
				renderResImpl = (RenderResponseImpl)res;

				httpReq = renderReqImpl.getHttpServletRequest();
				httpRes = renderResImpl.getHttpServletResponse();

				filterChain = new FilterChainImpl(_portlet, _renderFilters);
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				resourceReqImpl = (ResourceRequestImpl)req;
				resourceResImpl = (ResourceResponseImpl)res;

				httpReq = resourceReqImpl.getHttpServletRequest();
				httpRes = resourceResImpl.getHttpServletResponse();

				filterChain = new FilterChainImpl(_portlet, _resourceFilters);
			}

			httpReq.setAttribute(JavaConstants.JAVAX_PORTLET_PORTLET, _portlet);
			httpReq.setAttribute(
				PortletServlet.PORTLET_SERVLET_FILTER_CHAIN, filterChain);

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

			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				properties = actionResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				properties = eventResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				properties = renderResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				properties = resourceResImpl.getProperties();
			}
		}
		else {
			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				ActionRequestImpl actionReqImpl = (ActionRequestImpl)req;
				ActionResponseImpl actionResImpl = (ActionResponseImpl)res;

				filterChain = new FilterChainImpl(_portlet, _actionFilters);

				filterChain.doFilter(actionReqImpl, actionResImpl);

				properties = actionResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				EventRequestImpl eventReqImpl = (EventRequestImpl)req;
				EventResponseImpl eventResImpl = (EventResponseImpl)res;

				filterChain = new FilterChainImpl(_portlet, _eventFilters);

				filterChain.doFilter(eventReqImpl, eventResImpl);

				properties = eventResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				RenderRequestImpl renderReqImpl = (RenderRequestImpl)req;
				RenderResponseImpl renderResImpl = (RenderResponseImpl)res;

				filterChain = new FilterChainImpl(_portlet, _renderFilters);

				filterChain.doFilter(renderReqImpl, renderResImpl);

				properties = renderResImpl.getProperties();
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				ResourceRequestImpl resourceReqImpl = (ResourceRequestImpl)req;
				ResourceResponseImpl resourceResImpl =
					(ResourceResponseImpl)res;

				filterChain = new FilterChainImpl(_portlet, _resourceFilters);

				filterChain.doFilter(resourceReqImpl, resourceResImpl);

				properties = resourceResImpl.getProperties();
			}
		}

		if ((properties != null) && (properties.size() > 0)) {
			if (_expCache != null) {
				String[] expCache = properties.get(
					RenderResponse.EXPIRATION_CACHE);

				if ((expCache != null) && (expCache.length > 0) &&
					(expCache[0] != null)) {

					_expCache = new Integer(GetterUtil.getInteger(expCache[0]));
				}
			}
		}
	}

	protected void invokeAction(ActionRequest req, ActionResponse res)
		throws IOException, PortletException {

		invoke(req, res, PortletRequest.ACTION_PHASE);
	}

	protected void invokeEvent(EventRequest req, EventResponse res)
		throws IOException, PortletException {

		invoke(req, res, PortletRequest.EVENT_PHASE);
	}

	protected String invokeRender(RenderRequest req, RenderResponse res)
		throws IOException, PortletException {

		invoke(req, res, PortletRequest.RENDER_PHASE);

		RenderResponseImpl resImpl = (RenderResponseImpl)res;

		return resImpl.getTitle();
	}

	protected void invokeResource(ResourceRequest req, ResourceResponse res)
		throws IOException, PortletException {

		invoke(req, res, PortletRequest.RESOURCE_PHASE);
	}

	private static Log _log = LogFactory.getLog(InvokerPortlet.class);

	private com.liferay.portal.model.Portlet _portletModel;
	private String _portletId;
	private Portlet _portlet;
	private PortletConfigImpl _portletConfig;
	private PortletContextImpl _portletCtx;
	private Integer _expCache;
	private boolean _destroyable;
	private boolean _facesPortlet;
	private boolean _strutsPortlet;
	private boolean _strutsBridgePortlet;
	private List<ActionFilter> _actionFilters = new ArrayList<ActionFilter>();
	private List<EventFilter> _eventFilters = new ArrayList<EventFilter>();
	private List<RenderFilter> _renderFilters = new ArrayList<RenderFilter>();
	private List<ResourceFilter> _resourceFilters =
		new ArrayList<ResourceFilter>();

}