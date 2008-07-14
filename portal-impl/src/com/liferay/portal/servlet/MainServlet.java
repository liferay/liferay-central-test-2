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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.deploy.hot.PluginPackageHotDeployListener;
import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.job.Scheduler;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PortletSessionTracker;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalInitableUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lastmodified.LastModifiedAction;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.User;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialRequestInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.model.impl.SocialRequestInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.portlet.social.service.SocialRequestInterpreterLocalServiceUtil;
import com.liferay.util.servlet.DynamicServletRequest;
import com.liferay.util.servlet.EncryptedServletRequest;

import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesUtilImpl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * <a href="MainServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Brian Myunghun Kim
 *
 */
public class MainServlet extends ActionServlet {

	static {
		InitUtil.init();
	}

	public void init() throws ServletException {

		// Initialize

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize");
		}

		super.init();

		// Startup events

		if (_log.isDebugEnabled()) {
			_log.debug("Process startup events");
		}

		try {
			StartupAction startupAction = new StartupAction();

			startupAction.run(null);
		}
		catch (RuntimeException re) {
			ShutdownUtil.shutdown(0);

			throw new ServletException(re);
		}
		catch (ActionException ae) {
			_log.error(ae, ae);
		}

		// Velocity

		String contextPath = PortalUtil.getPathContext();

		ServletContext servletContext = getServletContext();

		VelocityContextPool.put(contextPath, servletContext);

		// Plugin package

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize plugin package");
		}

		PluginPackage pluginPackage = null;

		try {
			pluginPackage =
				PluginPackageHotDeployListener.readPluginPackage(
					servletContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Portlets

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize portlets");
		}

		List<Portlet> portlets = null;

		try {
			String[] xmls = new String[] {
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_CUSTOM)),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/portlet-ext.xml")),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-portlet.xml")),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-portlet-ext.xml")),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/web.xml"))
			};

			PortletLocalServiceUtil.initEAR(xmls, pluginPackage);

			portlets = PortletLocalServiceUtil.getPortlets();

			for (int i = 0; i < portlets.size(); i++) {
				Portlet portlet = portlets.get(i);

				if (i == 0) {
					initPortletApp(portlet, servletContext);
				}

				PortletInstanceFactory.create(portlet, servletContext);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Layout templates

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize layout templates");
		}

		try {
			String[] xmls = new String[] {
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-layout-templates.xml")),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-layout-templates-ext.xml"))
			};

			LayoutTemplateLocalServiceUtil.init(
				servletContext, xmls, pluginPackage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Look and feel

		if (_log.isDebugEnabled()) {
			_log.debug("Initialize look and feel");
		}

		try {
			String[] xmls = new String[] {
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel.xml")),
				HttpUtil.URLtoString(servletContext.getResource(
					"/WEB-INF/liferay-look-and-feel-ext.xml"))
			};

			ThemeLocalServiceUtil.init(
				servletContext, null, true, xmls, pluginPackage);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Scheduler

		if (_log.isDebugEnabled()) {
			_log.debug("Scheduler");
		}

		try {
			if (PropsValues.SCHEDULER_ENABLED) {
				for (String className : PropsValues.SCHEDULER_CLASSES) {
					Scheduler scheduler = (Scheduler)InstancePool.get(
						className);

					scheduler.schedule();
				}

				Iterator<Portlet> itr = portlets.iterator();

				while (itr.hasNext()) {
					Portlet portlet = itr.next();

					String className = portlet.getSchedulerClass();

					if (portlet.isActive() && Validator.isNotNull(className)) {
						Scheduler scheduler = (Scheduler)InstancePool.get(
							className);

						scheduler.schedule();
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// POP message listener

		if (_log.isDebugEnabled()) {
			_log.debug("POP message listener");
		}

		try {
			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				MessageListener popMessageListener =
					portlet.getPopMessageListenerInstance();

				if (portlet.isActive() && (popMessageListener != null)) {
					POPServerUtil.addListener(popMessageListener);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Social activity interpreter

		if (_log.isDebugEnabled()) {
			_log.debug("Social activity interpreter");
		}

		try {
			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				SocialActivityInterpreter socialActivityInterpreter =
					portlet.getSocialActivityInterpreterInstance();

				if (portlet.isActive() && (socialActivityInterpreter != null)) {
					socialActivityInterpreter =
						new SocialActivityInterpreterImpl(
							portlet.getPortletId(), socialActivityInterpreter);

					SocialActivityInterpreterLocalServiceUtil.
						addActivityInterpreter(socialActivityInterpreter);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Social request interpreter

		if (_log.isDebugEnabled()) {
			_log.debug("Social request interpreter");
		}

		try {
			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				SocialRequestInterpreter socialRequestInterpreter =
					portlet.getSocialRequestInterpreterInstance();

				if (portlet.isActive() && (socialRequestInterpreter != null)) {
					socialRequestInterpreter = new SocialRequestInterpreterImpl(
						portlet.getPortletId(), socialRequestInterpreter);

					SocialRequestInterpreterLocalServiceUtil.
						addRequestInterpreter(socialRequestInterpreter);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Check web settings

		if (_log.isDebugEnabled()) {
			_log.debug("Check web settings");
		}

		try {
			String xml = HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/web.xml"));

			checkWebSettings(xml);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Last modified paths

		if (_log.isDebugEnabled()) {
			_log.debug("Last modified paths");
		}

		if (_lastModifiedPaths == null) {
			_lastModifiedPaths = new HashSet<String>();

			for (String lastModifiedPath : PropsValues.LAST_MODIFIED_PATHS) {
				_lastModifiedPaths.add(lastModifiedPath);
			}
		}

		// Global startup events

		if (_log.isDebugEnabled()) {
			_log.debug("Process global startup events");
		}

		try {
			EventsProcessor.process(
				PropsKeys.GLOBAL_STARTUP_EVENTS,
				PropsValues.GLOBAL_STARTUP_EVENTS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Companies

		String[] webIds = PortalInstances.getWebIds();

		for (int i = 0; i < webIds.length; i++) {
			PortalInstances.initCompany(servletContext, webIds[i]);
		}

		// See LEP-2885. Don't flush hot deploy events until after the portal
		// has initialized.

		PortalInitableUtil.flushInitables();
		HotDeployUtil.flushEvents();
	}

	public void callParentService(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		super.service(request, response);
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Process service request");
		}

		if (ShutdownUtil.isShutdown()) {
			response.setContentType(ContentTypes.TEXT_HTML_UTF8);

			String html = ContentUtil.get(
				"com/liferay/portal/dependencies/shutdown.html");

			response.getOutputStream().print(html);

			return;
		}

		HttpSession session = request.getSession();

		// Company id

		long companyId = PortalInstances.getCompanyId(request);

		//CompanyThreadLocal.setCompanyId(companyId);

		// Portal port

		PortalUtil.setPortalPort(request);

		// CTX

		ServletContext servletContext = getServletContext();

		request.setAttribute(WebKeys.CTX, servletContext);

		// Struts module config

		ModuleConfig moduleConfig = getModuleConfig(request);

		// Last modified check

		if (PropsValues.LAST_MODIFIED_CHECK) {
			String path = request.getPathInfo();

			if ((path != null) && _lastModifiedPaths.contains(path)) {
				ActionMapping mapping =
					(ActionMapping)moduleConfig.findActionConfig(path);

				LastModifiedAction lastModifiedAction =
					(LastModifiedAction)InstancePool.get(mapping.getType());

				String lmKey = lastModifiedAction.getLastModifiedKey(request);

				if (lmKey != null) {
					long ifModifiedSince =
						request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);

					if (ifModifiedSince <= 0) {
						lastModifiedAction.setLastModifiedValue(lmKey, lmKey);
					}
					else {
						String lmValue =
							lastModifiedAction.getLastModifiedValue(lmKey);

						if (lmValue != null) {
							response.setStatus(
								HttpServletResponse.SC_NOT_MODIFIED);

							return;
						}
						else {
							lastModifiedAction.setLastModifiedValue(
								lmKey, lmKey);
						}
					}
				}
			}
		}

		// Portlet session tracker

		if (session.getAttribute(WebKeys.PORTLET_SESSION_TRACKER) == null ) {
			session.setAttribute(
				WebKeys.PORTLET_SESSION_TRACKER,
				PortletSessionTracker.getInstance());
		}

		// Portlet Request Processor

		PortletRequestProcessor portletReqProcessor =
			(PortletRequestProcessor)servletContext.getAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR);

		if (portletReqProcessor == null) {
			portletReqProcessor =
				PortletRequestProcessor.getInstance(this, moduleConfig);

			servletContext.setAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR, portletReqProcessor);
		}

		// Tiles definitions factory

		if (servletContext.getAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY) == null) {

			servletContext.setAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY,
				servletContext.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY));
		}

		Object applicationAssociate = servletContext.getAttribute(
			WebKeys.ASSOCIATE_KEY);

		if (servletContext.getAttribute(WebKeys.ASSOCIATE_KEY) == null) {
			servletContext.setAttribute(
				WebKeys.ASSOCIATE_KEY, applicationAssociate);
		}

		// Encrypt request

		if (ParamUtil.get(request, WebKeys.ENCRYPT, false)) {
			try {
				Company company = CompanyLocalServiceUtil.getCompanyById(
					companyId);

				request = new EncryptedServletRequest(
					request, company.getKeyObj());
			}
			catch (Exception e) {
			}
		}

		// Current URL

		PortalUtil.getCurrentURL(request);

		// Login

		long userId = PortalUtil.getUserId(request);
		String remoteUser = request.getRemoteUser();

		// Is JAAS enabled?

		if (!PropsValues.PORTAL_JAAS_ENABLE) {
			String jRemoteUser = (String)session.getAttribute("j_remoteuser");

			if (jRemoteUser != null) {
				remoteUser = jRemoteUser;

				session.removeAttribute("j_remoteuser");
			}
		}

		if ((userId > 0) && (remoteUser == null)) {
			remoteUser = String.valueOf(userId);
		}

		// WebSphere will not return the remote user unless you are
		// authenticated AND accessing a protected path. Other servers will
		// return the remote user for all threads associated with an
		// authenticated user. We use ProtectedServletRequest to ensure we get
		// similar behavior across all servers.

		request = new ProtectedServletRequest(request, remoteUser);

		if ((userId > 0) || (remoteUser != null)) {

			// Set the principal associated with this thread

			String name = String.valueOf(userId);

			if (remoteUser != null) {
				name = remoteUser;
			}

			PrincipalThreadLocal.setName(name);
		}

		if ((userId <= 0) && (remoteUser != null)) {
			try {

				// User id

				userId = GetterUtil.getLong(remoteUser);

				// Pre login events

				EventsProcessor.process(
					PropsKeys.LOGIN_EVENTS_PRE, PropsValues.LOGIN_EVENTS_PRE,
					request, response);

				// User

				User user = UserLocalServiceUtil.getUserById(userId);

				UserLocalServiceUtil.updateLastLogin(
					userId, request.getRemoteAddr());

				// User id

				session.setAttribute(WebKeys.USER_ID, new Long(userId));

				// User locale

				session.setAttribute(Globals.LOCALE_KEY, user.getLocale());

				// Post login events

				EventsProcessor.process(
					PropsKeys.LOGIN_EVENTS_POST, PropsValues.LOGIN_EVENTS_POST,
					request, response);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		// Pre service events

		try {
			EventsProcessor.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof NoSuchLayoutException) {
				DynamicServletRequest dynamicRequest =
					new DynamicServletRequest(request);

				// Reset p_l_id or there will be an infinite loop

				dynamicRequest.setParameter("p_l_id", StringPool.BLANK);

				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					(NoSuchLayoutException)cause, dynamicRequest, response);

				return;
			}

			_log.error(e, e);

			request.setAttribute(PageContext.EXCEPTION, e);

			StrutsUtil.forward(
				PropsValues.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE,
				servletContext, request, response);

			return;
		}

		try {

			// Struts service

			callParentService(request, response);
		}
		finally {

			// Post service events

			try {
				EventsProcessor.process(
					PropsKeys.SERVLET_SERVICE_EVENTS_POST,
					PropsValues.SERVLET_SERVICE_EVENTS_POST, request, response);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			response.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getReleaseInfo());

			// Clear the company id associated with this thread

			CompanyThreadLocal.setCompanyId(0);

			// Clear the principal associated with this thread

			PrincipalThreadLocal.setName(null);
		}
	}

	public void destroy() {
		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();

		// Scheduler

		if (_log.isDebugEnabled()) {
			_log.debug("Scheduler");
		}

		try {
			if (PropsValues.SCHEDULER_ENABLED) {
				for (String className : PropsValues.SCHEDULER_CLASSES) {
					Scheduler scheduler = (Scheduler)InstancePool.get(
						className);

					scheduler.unschedule();
				}

				Iterator<Portlet> itr = portlets.iterator();

				while (itr.hasNext()) {
					Portlet portlet = itr.next();

					String className = portlet.getSchedulerClass();

					if (portlet.isActive() && Validator.isNotNull(className)) {
						Scheduler scheduler = (Scheduler)InstancePool.get(
							className);

						scheduler.unschedule();
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Portlets

		try {
			Iterator<Portlet> itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = itr.next();

				PortletInstanceFactory.destroy(portlet);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Companies

		long[] companyIds = PortalInstances.getCompanyIds();

		for (int i = 0; i < companyIds.length; i++) {
			destroyCompany(companyIds[i]);
		}

		// Global shutdown events

		if (_log.isDebugEnabled()) {
			_log.debug("Process global shutdown events");
		}

		try {
			EventsProcessor.process(
				PropsKeys.GLOBAL_SHUTDOWN_EVENTS,
				PropsValues.GLOBAL_SHUTDOWN_EVENTS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		super.destroy();
	}

	protected void checkWebSettings(String xml) throws DocumentException {
		Document doc = DocumentUtil.readDocumentFromXML(xml);

		Element root = doc.getRootElement();

		int timeout = PropsValues.SESSION_TIMEOUT;

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout = sessionConfig.elementText(
				"session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsKeys.SESSION_TIMEOUT, String.valueOf(timeout));

		PropsValues.SESSION_TIMEOUT = timeout;
	}

	protected void destroyCompany(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Process shutdown events");
		}

		try {
			EventsProcessor.process(
				PropsKeys.APPLICATION_SHUTDOWN_EVENTS,
				PropsValues.APPLICATION_SHUTDOWN_EVENTS,
				new String[] {String.valueOf(companyId)});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext servletContext)
		throws PortletException {

		PortletApp portletApp = portlet.getPortletApp();

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			PortletFilterFactory.create(portletFilter, portletContext);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	private static final String _LIFERAY_PORTAL_REQUEST_HEADER =
		"Liferay-Portal";

	private static Log _log = LogFactory.getLog(MainServlet.class);

	private Set<String> _lastModifiedPaths;

}