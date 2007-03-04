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

package com.liferay.portal.servlet;

import com.liferay.portal.deploy.HotDeployPluginPackageListener;
import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.job.Scheduler;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.PortletSessionTracker;
import com.liferay.portal.kernel.smtp.MessageListener;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lastmodified.LastModifiedAction;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalFinder;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.ldap.LDAPImportUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.LayoutTemplateLocalUtil;
import com.liferay.portal.service.impl.ThemeLocalUtil;
import com.liferay.portal.smtp.SMTPServerUtil;
import com.liferay.portal.struts.MultiMessageResources;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;
import com.liferay.util.HttpHeaders;
import com.liferay.util.InstancePool;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.EncryptedServletRequest;
import com.liferay.util.servlet.ProtectedServletRequest;
import com.liferay.util.servlet.UploadServletRequest;

import java.io.IOException;
import java.io.StringReader;

import java.util.Iterator;
import java.util.Set;

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
import org.dom4j.io.SAXReader;

import org.quartz.ObjectAlreadyExistsException;

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

	public static final String DEFAULT_MAIN_PATH = "/c";

	public void init() throws ServletException {
		synchronized (MainServlet.class) {

			// Initialize

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize");
			}

			super.init();

			// Process startup events

			if (_log.isDebugEnabled()) {
				_log.debug("Process startup events");
			}

			try {
				EventsProcessor.process(
					new String[] {
						StartupAction.class.getName()
					},
					true);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Company id

			if (_log.isDebugEnabled()) {
				_log.debug("Company id");
			}

			ServletContext ctx = getServletContext();

			String servletContextName = GetterUtil.getString(
				ctx.getServletContextName());

			_companyId = ctx.getInitParameter("company_id");

			ctx.setAttribute(WebKeys.COMPANY_ID, _companyId);

			CompanyThreadLocal.setCompanyId(_companyId);

			// Paths

			if (_log.isDebugEnabled()) {
				_log.debug("Paths");
			}

			String rootPath = GetterUtil.getString(
				ctx.getInitParameter("root_path"), StringPool.SLASH);

			if (rootPath.equals(StringPool.SLASH)) {
				rootPath = StringPool.BLANK;
			}

			ctx.setAttribute(WebKeys.ROOT_PATH, rootPath);
			ctx.setAttribute(WebKeys.MAIN_PATH, rootPath + DEFAULT_MAIN_PATH);

			String friendlyURLPrivatePath = rootPath + PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_SERVLET_MAPPING);

			ctx.setAttribute(
				WebKeys.FRIENDLY_URL_PRIVATE_PATH, friendlyURLPrivatePath);

			String friendlyURLPublicPath = rootPath + PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

			ctx.setAttribute(
				WebKeys.FRIENDLY_URL_PUBLIC_PATH, friendlyURLPublicPath);

			ctx.setAttribute(WebKeys.IMAGE_PATH, rootPath + "/image");

			// Read bundled plugins package information

			PluginPackage pluginPackage = null;

			try {
				pluginPackage =
					HotDeployPluginPackageListener.readPluginPackage(ctx);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Initialize portlets

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize portlets");
			}

			try {
				String[] xmls = new String[] {
					Http.URLtoString(ctx.getResource("/WEB-INF/portlet.xml")),
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/portlet-ext.xml")),
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-portlet.xml")),
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-portlet-ext.xml")),
					Http.URLtoString(ctx.getResource("/WEB-INF/web.xml"))
				};

				PortletLocalServiceUtil.initEAR(xmls, pluginPackage);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Initialize display

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize display");
			}

			try {
				String xml = Http.URLtoString(ctx.getResource(
					"/WEB-INF/liferay-display.xml"));

				PortletCategory portletCategory =
					(PortletCategory)WebAppPool.get(
						_companyId, WebKeys.PORTLET_CATEGORY);

				if (portletCategory == null) {
					portletCategory = new PortletCategory();
				}

				PortletCategory newPortletCategory =
					PortletLocalServiceUtil.getEARDisplay(xml);

				portletCategory.merge(newPortletCategory);

				WebAppPool.put(
					_companyId, WebKeys.PORTLET_CATEGORY, portletCategory);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Initialize layout templates

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize layout templates");
			}

			try {
				String[] xmls = new String[] {
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-layout-templates.xml")),
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-layout-templates-ext.xml"))
				};

				LayoutTemplateLocalUtil.init(ctx, xmls, pluginPackage);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Initialize look and feel

			if (_log.isDebugEnabled()) {
				_log.debug("Initialize look and feel");
			}

			try {
				String[] xmls = new String[] {
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-look-and-feel.xml")),
					Http.URLtoString(ctx.getResource(
						"/WEB-INF/liferay-look-and-feel-ext.xml"))
				};

				ThemeLocalUtil.init(ctx, xmls, pluginPackage);

				VelocityContextPool.put(servletContextName, ctx);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Check company

			if (_log.isDebugEnabled()) {
				_log.debug("Check company");
			}

			try {
				CompanyLocalServiceUtil.checkCompany(_companyId);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Check journal content search

			if (_log.isDebugEnabled()) {
				_log.debug("Check journal content search");
			}

			if (GetterUtil.getBoolean(PropsUtil.get(
					null, PropsUtil.JOURNAL_SYNC_CONTENT_SEARCH_ON_STARTUP))) {

				try {
					JournalContentSearchLocalServiceUtil.checkContentSearches(
						_companyId);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			// Check web settings

			if (_log.isDebugEnabled()) {
				_log.debug("Check web settings");
			}

			try {
				String xml = Http.URLtoString(ctx.getResource(
					"/WEB-INF/web.xml"));

				_checkWebSettings(xml);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Scheduler

			if (_log.isDebugEnabled()) {
				_log.debug("Scheduler");
			}

			try {
				Iterator itr = PortletLocalServiceUtil.getPortlets(
					_companyId).iterator();

				while (itr.hasNext()) {
					Portlet portlet = (Portlet)itr.next();

					String className = portlet.getSchedulerClass();

					if (portlet.isActive() && Validator.isNotNull(className)) {
						Scheduler scheduler =
							(Scheduler)InstancePool.get(className);

						scheduler.schedule();
					}
				}
			}
			catch (ObjectAlreadyExistsException oaee) {
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// SMTP message listener

			if (_log.isDebugEnabled()) {
				_log.debug("SMTP message listener");
			}

			try {
				Iterator itr = PortletLocalServiceUtil.getPortlets(
					_companyId).iterator();

				while (itr.hasNext()) {
					Portlet portlet = (Portlet)itr.next();

					MessageListener smtpMessageListener =
						portlet.getSmtpMessageListener();

					if (portlet.isActive() && (smtpMessageListener != null)) {
						SMTPServerUtil.addListener(smtpMessageListener);
					}
				}
			}
			catch (ObjectAlreadyExistsException oaee) {
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// LDAP Import

			try {
				if (PrefsPropsUtil.getBoolean(
						_companyId, PropsUtil.LDAP_IMPORT_ENABLED) &&
					PrefsPropsUtil.getBoolean(
						_companyId, PropsUtil.LDAP_IMPORT_ON_STARTUP)) {

					LDAPImportUtil.importLDAP(_companyId);
				}
			}
			catch (Exception e){
				_log.error(e, e);
			}

			// Message resources

			if (_log.isDebugEnabled()) {
				_log.debug("Message resources");
			}

			MultiMessageResources messageResources =
				(MultiMessageResources)ctx.getAttribute(Globals.MESSAGES_KEY);

			messageResources.setServletContext(ctx);

			WebAppPool.put(_companyId, Globals.MESSAGES_KEY, messageResources);

			// Last modified paths

			if (_log.isDebugEnabled()) {
				_log.debug("Last modified paths");
			}

			if (_lastModifiedPaths == null) {
				_lastModifiedPaths = CollectionFactory.getHashSet();

				for (int i = 0;; i++) {
					String lastModifiedPath =
						PropsUtil.get(PropsUtil.LAST_MODIFIED_PATH + i);

					if (lastModifiedPath == null) {
						break;
					}
					else {
						_lastModifiedPaths.add(lastModifiedPath);
					}
				}
			}

			// Process startup events

			if (_log.isDebugEnabled()) {
				_log.debug("Process startup events");
			}

			try {
				EventsProcessor.process(PropsUtil.getArray(
					PropsUtil.GLOBAL_STARTUP_EVENTS), true);

				EventsProcessor.process(PropsUtil.getArray(
					PropsUtil.APPLICATION_STARTUP_EVENTS),
					new String[] {_companyId});
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			// Portal instance

			if (_log.isDebugEnabled()) {
				_log.debug("Portal instance " + _companyId);
			}

			PortalInstances.init(_companyId);
		}
	}

	public void callParentService(
			HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		super.service(req, res);
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {

		if (!PortalInstances.matches()) {
			res.setContentType(Constants.TEXT_HTML);

			String html = ContentUtil.get(
				"com/liferay/portal/dependencies/init.html");

			res.getOutputStream().print(html);

			return;
		}

		if (ShutdownUtil.isShutdown()) {
			res.setContentType(Constants.TEXT_HTML);

			String html = ContentUtil.get(
				"com/liferay/portal/dependencies/shutdown.html");

			res.getOutputStream().print(html);

			return;
		}

		HttpSession ses = req.getSession();

		// Test CAS auto login

		/*ses.setAttribute(
			com.liferay.portal.security.auth.CASAutoLogin.CAS_FILTER_USER,
			"liferay.com.1");*/

		// CTX

		ServletContext ctx = getServletContext();

		ServletContext portalCtx = null;

		try {
			portalCtx = ctx.getContext(
				PrefsPropsUtil.getString(_companyId, PropsUtil.PORTAL_CTX));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (portalCtx == null) {
			portalCtx = ctx;
		}

		req.setAttribute(WebKeys.CTX, portalCtx);

		// Struts module config

		ModuleConfig moduleConfig = getModuleConfig(req);

		// Last modified check

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.LAST_MODIFIED_CHECK))) {

			String path = req.getPathInfo();

			if ((path != null) && _lastModifiedPaths.contains(path)) {
				ActionMapping mapping =
					(ActionMapping)moduleConfig.findActionConfig(path);

				LastModifiedAction lastModifiedAction =
					(LastModifiedAction)InstancePool.get(mapping.getType());

				String lmKey = lastModifiedAction.getLastModifiedKey(req);

				if (lmKey != null) {
					long ifModifiedSince =
						req.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);

					if (ifModifiedSince <= 0) {
						lastModifiedAction.setLastModifiedValue(lmKey, lmKey);
					}
					else {
						String lmValue =
							lastModifiedAction.getLastModifiedValue(lmKey);

						if (lmValue != null) {
							res.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

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

		if (ses.getAttribute(WebKeys.PORTLET_SESSION_TRACKER) == null ) {
			ses.setAttribute(
				WebKeys.PORTLET_SESSION_TRACKER,
				PortletSessionTracker.getInstance());
		}

		// WebKeys.COMPANY_ID variable

		String companyId = (String)ctx.getAttribute(WebKeys.COMPANY_ID);

		if (portalCtx.getAttribute(WebKeys.COMPANY_ID) == null) {
			portalCtx.setAttribute(WebKeys.COMPANY_ID, companyId);
		}

		if (ses.getAttribute(WebKeys.COMPANY_ID) == null) {
			ses.setAttribute(WebKeys.COMPANY_ID, companyId);
		}

		req.setAttribute(WebKeys.COMPANY_ID, companyId);

		CompanyThreadLocal.setCompanyId(companyId);

		// ROOT_PATH variable

		String rootPath = (String)ctx.getAttribute(WebKeys.ROOT_PATH);

		if (portalCtx.getAttribute(WebKeys.ROOT_PATH) == null) {
			portalCtx.setAttribute(WebKeys.ROOT_PATH, rootPath);
		}

		if (ses.getAttribute(WebKeys.ROOT_PATH) == null) {
			ses.setAttribute(WebKeys.ROOT_PATH, rootPath);
		}

		req.setAttribute(WebKeys.ROOT_PATH, rootPath);

		// MAIN_PATH variable

		String mainPath = (String)ctx.getAttribute(WebKeys.MAIN_PATH);

		if (portalCtx.getAttribute(WebKeys.MAIN_PATH) == null) {
			portalCtx.setAttribute(WebKeys.MAIN_PATH, mainPath);
		}

		if (ses.getAttribute(WebKeys.MAIN_PATH) == null) {
			ses.setAttribute(WebKeys.MAIN_PATH, mainPath);
		}

		req.setAttribute(WebKeys.MAIN_PATH, mainPath);

		// FRIENDLY_URL_PRIVATE_PATH variable

		String friendlyURLPrivatePath =
			(String)ctx.getAttribute(WebKeys.FRIENDLY_URL_PRIVATE_PATH);

		if (portalCtx.getAttribute(WebKeys.FRIENDLY_URL_PRIVATE_PATH) == null) {
			portalCtx.setAttribute(
				WebKeys.FRIENDLY_URL_PRIVATE_PATH, friendlyURLPrivatePath);
		}

		if (ses.getAttribute(WebKeys.FRIENDLY_URL_PRIVATE_PATH) == null) {
			ses.setAttribute(
				WebKeys.FRIENDLY_URL_PRIVATE_PATH, friendlyURLPrivatePath);
		}

		req.setAttribute(
			WebKeys.FRIENDLY_URL_PRIVATE_PATH, friendlyURLPrivatePath);

		// FRIENDLY_URL_PUBLIC_PATH variable

		String friendlyURLPublicPath =
			(String)ctx.getAttribute(WebKeys.FRIENDLY_URL_PUBLIC_PATH);

		if (portalCtx.getAttribute(WebKeys.FRIENDLY_URL_PUBLIC_PATH) == null) {
			portalCtx.setAttribute(
				WebKeys.FRIENDLY_URL_PUBLIC_PATH, friendlyURLPublicPath);
		}

		if (ses.getAttribute(WebKeys.FRIENDLY_URL_PUBLIC_PATH) == null) {
			ses.setAttribute(
				WebKeys.FRIENDLY_URL_PUBLIC_PATH, friendlyURLPublicPath);
		}

		req.setAttribute(
			WebKeys.FRIENDLY_URL_PUBLIC_PATH, friendlyURLPublicPath);

		// IMAGE_PATH variable

		String imagePath = (String)ctx.getAttribute(WebKeys.IMAGE_PATH);

		if (portalCtx.getAttribute(WebKeys.IMAGE_PATH) == null) {
			portalCtx.setAttribute(WebKeys.IMAGE_PATH, imagePath);
		}

		if (ses.getAttribute(WebKeys.IMAGE_PATH) == null) {
			ses.setAttribute(WebKeys.IMAGE_PATH, imagePath);
		}

		req.setAttribute(WebKeys.IMAGE_PATH, imagePath);

		// Portlet Request Processor

		PortletRequestProcessor portletReqProcessor =
			(PortletRequestProcessor)portalCtx.getAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR);

		if (portletReqProcessor == null) {
			portletReqProcessor =
				PortletRequestProcessor.getInstance(this, moduleConfig);

			portalCtx.setAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR, portletReqProcessor);
		}

		// Tiles definitions factory

		if (portalCtx.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY) == null) {
			portalCtx.setAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY,
				ctx.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY));
		}

		Object applicationAssociate = ctx.getAttribute(WebKeys.ASSOCIATE_KEY);

		if (portalCtx.getAttribute(WebKeys.ASSOCIATE_KEY) == null) {
			portalCtx.setAttribute(WebKeys.ASSOCIATE_KEY, applicationAssociate);
		}

		// Set character encoding

		String strutsCharEncoding =
			PropsUtil.get(PropsUtil.STRUTS_CHAR_ENCODING);

		req.setCharacterEncoding(strutsCharEncoding);

		/*if (!BrowserSniffer.is_wml(req)) {
			res.setContentType(
				Constants.TEXT_HTML + "; charset=" + strutsCharEncoding);
		}*/

		// Determine content type

		String contentType = req.getHeader(HttpHeaders.CONTENT_TYPE);

		if (_log.isDebugEnabled()) {
			_log.debug("Content type " + contentType);
		}

		UploadServletRequest uploadReq = null;

		if ((contentType != null) &&
			(contentType.startsWith(Constants.MULTIPART_FORM_DATA))) {

			uploadReq = new UploadServletRequest(req);

			req = uploadReq;
		}
		else if (ParamUtil.get(req, WebKeys.ENCRYPT, false)) {
			try {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				req = new EncryptedServletRequest(req, company.getKeyObj());
			}
			catch (Exception e) {
			}
		}

		// Current URL

		String completeURL = Http.getCompleteURL(req);

		if ((Validator.isNotNull(completeURL)) &&
			(completeURL.indexOf("j_security_check") == -1)) {

			completeURL = completeURL.substring(
				completeURL.indexOf("://") + 3, completeURL.length());

			completeURL = completeURL.substring(
				completeURL.indexOf("/"), completeURL.length());
		}

		if (Validator.isNull(completeURL)) {
			completeURL = mainPath;
		}

		req.setAttribute(WebKeys.CURRENT_URL, completeURL);

		// Login

		String userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();

		// Is JAAS enabled?

		if (!GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.PORTAL_JAAS_ENABLE))) {

			String jRemoteUser = (String)ses.getAttribute("j_remoteuser");

			if (jRemoteUser != null) {
				remoteUser = jRemoteUser;

				ses.removeAttribute("j_remoteuser");
			}
		}

		if ((userId != null) && (remoteUser == null)) {
			remoteUser = userId;
		}

		// WebSphere will not return the remote user unless you are
		// authenticated AND accessing a protected path. Other servers will
		// return the remote user for all threads associated with an
		// authenticated user. We use ProtectedServletRequest to ensure we get
		// similar behavior across all servers.

		req = new ProtectedServletRequest(req, remoteUser);

		if ((userId != null) || (remoteUser != null)) {

			// Set the principal associated with this thread

			String name = userId;

			if (remoteUser != null) {
				name = remoteUser;
			}

			PrincipalThreadLocal.setName(name);
		}

		if ((userId == null) && (remoteUser != null)) {
			try {

				// User id

				userId = remoteUser;

				try {
					PrincipalFinder principalFinder =
						(PrincipalFinder)InstancePool.get(
							PropsUtil.get(PropsUtil.PRINCIPAL_FINDER));

					userId = principalFinder.toLiferay(userId);
				}
				catch (Exception e) {
				}

				// Pre login events

				EventsProcessor.process(PropsUtil.getArray(
					PropsUtil.LOGIN_EVENTS_PRE), req, res);

				// User

				User user = UserLocalServiceUtil.getUserById(userId);

				UserLocalServiceUtil.updateLastLogin(
					userId, req.getRemoteAddr());

				// User id

				ses.setAttribute(WebKeys.USER_ID, userId);

				// User locale

				ses.setAttribute(Globals.LOCALE_KEY, user.getLocale());

				// Post login events

				EventsProcessor.process(PropsUtil.getArray(
					PropsUtil.LOGIN_EVENTS_POST), req, res);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		// Process pre service events

		try {
			EventsProcessor.process(PropsUtil.getArray(
				PropsUtil.SERVLET_SERVICE_EVENTS_PRE), req, res);
		}
		catch (Exception e) {
			_log.error(e, e);

			req.setAttribute(PageContext.EXCEPTION, e);

			StrutsUtil.forward(
				PropsUtil.get(
					PropsUtil.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE),
				portalCtx, req, res);
		}

		try {

			// Struts service

			callParentService(req, res);
		}
		finally {

			// Process post service events

			try {
				EventsProcessor.process(PropsUtil.getArray(
					PropsUtil.SERVLET_SERVICE_EVENTS_POST), req, res);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			res.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getReleaseInfo());

			if (uploadReq != null) {
				uploadReq.cleanUp();
			}

			// Clear the company id associated with this thread

			CompanyThreadLocal.setCompanyId(null);

			// Clear the principal associated with this thread

			PrincipalThreadLocal.setName(null);
		}
	}

	public void destroy() {

		// Destroy portlets

		try {
			Iterator itr = PortletLocalServiceUtil.getPortlets(
				_companyId).iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				PortletInstanceFactory.destroy(portlet);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Process shutdown events

		if (_log.isDebugEnabled()) {
			_log.debug("Process shutdown events");
		}

		try {
			EventsProcessor.process(PropsUtil.getArray(
				PropsUtil.GLOBAL_SHUTDOWN_EVENTS), true);

			EventsProcessor.process(PropsUtil.getArray(
				PropsUtil.APPLICATION_SHUTDOWN_EVENTS),
				new String[] {_companyId});
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Parent

		super.destroy();
	}

	private void _checkWebSettings(String xml) throws DocumentException {
		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new StringReader(xml));

		Element root = doc.getRootElement();

		int timeout = GetterUtil.getInteger(
			PropsUtil.get(PropsUtil.SESSION_TIMEOUT));

		Element sessionConfig = root.element("session-config");

		if (sessionConfig != null) {
			String sessionTimeout =
				sessionConfig.elementText("session-timeout");

			timeout = GetterUtil.getInteger(sessionTimeout, timeout);
		}

		PropsUtil.set(PropsUtil.SESSION_TIMEOUT, Integer.toString(timeout));
	}

	private static final String _LIFERAY_PORTAL_REQUEST_HEADER =
		"Liferay-Portal";

	private static Log _log = LogFactory.getLog(MainServlet.class);

	private String _companyId;
	private Set _lastModifiedPaths;

}