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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.apache.bridges.struts.LiferayServletContextProvider;
import com.liferay.portal.job.Scheduler;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.ServletContextProvider;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.CustomUserAttributes;
import com.liferay.portlet.PortletBag;
import com.liferay.portlet.PortletBagPool;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletContextBag;
import com.liferay.portlet.PortletContextBagPool;
import com.liferay.portlet.PortletFilterFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.PortletResourceBundles;
import com.liferay.portlet.PortletURLListenerFactory;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.util.CollectionFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.PreferencesValidator;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.portals.bridges.struts.StrutsPortlet;

/**
 * <a href="PortletHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 *
 */
public class PortletHotDeployListener implements HotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		String servletContextName = null;

		try {

			// Servlet context

			ServletContext ctx = event.getServletContext();

			servletContextName = ctx.getServletContextName();

			if (_log.isDebugEnabled()) {
				_log.debug("Invoking deploy for " + servletContextName);
			}

			// Company ids

			long[] companyIds = PortalInstances.getCompanyIds();

			// Initialize portlets

			String[] xmls = new String[] {
				HttpUtil.URLtoString(ctx.getResource(
					"/WEB-INF/" + PortalUtil.PORTLET_XML_FILE_NAME_STANDARD)),
				HttpUtil.URLtoString(ctx.getResource(
					"/WEB-INF/" + PortalUtil.PORTLET_XML_FILE_NAME_CUSTOM)),
				HttpUtil.URLtoString(ctx.getResource(
					"/WEB-INF/liferay-portlet.xml")),
				HttpUtil.URLtoString(ctx.getResource("/WEB-INF/web.xml"))
			};

			if (xmls[0] == null) {
				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Registering portlets for " + servletContextName);
			}

			List<Portlet> portlets = PortletLocalServiceUtil.initWAR(
				servletContextName, xmls, event.getPluginPackage());

			if (_log.isInfoEnabled()) {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" are ready for registration");
			}

			// Class loader

			ClassLoader portletClassLoader = event.getContextClassLoader();

			ctx.setAttribute(
				PortletServlet.PORTLET_CLASS_LOADER, portletClassLoader);

			// Portlet context wrapper

			boolean strutsBridges = false;

			Iterator<Portlet> portletsItr = portlets.iterator();

			while (portletsItr.hasNext()) {
				Portlet portlet = portletsItr.next();

				Class<?> portletClass = null;

				try {
					portletClass = portletClassLoader.loadClass(
						portlet.getPortletClass());
				}
				catch (Exception e1) {
					_log.error(e1, e1);

					portletsItr.remove();

					PortletLocalServiceUtil.destroyPortlet(portlet);

					continue;
				}

				javax.portlet.Portlet portletInstance =
					(javax.portlet.Portlet)portletClass.newInstance();

				if (ClassUtil.isSubclass(portletClass,
					StrutsPortlet.class.getName())) {

					strutsBridges = true;
				}

				ConfigurationAction configurationActionInstance = null;

				if (Validator.isNotNull(
						portlet.getConfigurationActionClass())) {

					configurationActionInstance =
						(ConfigurationAction)portletClassLoader.loadClass(
							portlet.getConfigurationActionClass()).
								newInstance();
				}

				Indexer indexerInstance = null;

				if (Validator.isNotNull(portlet.getIndexerClass())) {
					indexerInstance = (Indexer)portletClassLoader.loadClass(
						portlet.getIndexerClass()).newInstance();
				}

				Scheduler schedulerInstance = null;

				if (Validator.isNotNull(portlet.getSchedulerClass())) {
					schedulerInstance = (Scheduler)portletClassLoader.loadClass(
						portlet.getSchedulerClass()).newInstance();

					schedulerInstance.schedule();
				}

				FriendlyURLMapper friendlyURLMapperInstance = null;

				if (Validator.isNotNull(portlet.getFriendlyURLMapperClass())) {
					friendlyURLMapperInstance =
						(FriendlyURLMapper)portletClassLoader.loadClass(
							portlet.getFriendlyURLMapperClass()).newInstance();
				}

				URLEncoder urlEncoderInstance = null;

				if (Validator.isNotNull(portlet.getURLEncoderClass())) {
					urlEncoderInstance =
						(URLEncoder)portletClassLoader.loadClass(
							portlet.getURLEncoderClass()).newInstance();
				}

				PortletDataHandler portletDataHandlerInstance = null;

				if (Validator.isNotNull(portlet.getPortletDataHandlerClass())) {
					portletDataHandlerInstance =
						(PortletDataHandler)portletClassLoader.loadClass(
							portlet.getPortletDataHandlerClass()).newInstance();
				}

				PortletLayoutListener portletLayoutListenerInstance = null;

				if (Validator.isNotNull(
						portlet.getPortletLayoutListenerClass())) {

					portletLayoutListenerInstance =
						(PortletLayoutListener)portletClassLoader.loadClass(
							portlet.getPortletLayoutListenerClass()).
								newInstance();
				}

				MessageListener popMessageListenerInstance = null;

				if (Validator.isNotNull(
						portlet.getPopMessageListenerClass())) {

					popMessageListenerInstance =
						(MessageListener)portletClassLoader.loadClass(
							portlet.getPopMessageListenerClass()).
								newInstance();

					POPServerUtil.addListener(popMessageListenerInstance);
				}

				SocialActivityInterpreter socialActivityInterpreterInstance =
					null;

				if (Validator.isNotNull(
						portlet.getSocialActivityInterpreterClass())) {

					socialActivityInterpreterInstance =
						(SocialActivityInterpreter)
							portletClassLoader.loadClass(
								portlet.getSocialActivityInterpreterClass()).
									newInstance();

					socialActivityInterpreterInstance =
						new SocialActivityInterpreterImpl(
							socialActivityInterpreterInstance);

					SocialActivityInterpreterLocalServiceUtil.
						addActivityInterpreter(
							socialActivityInterpreterInstance);
				}

				PreferencesValidator prefsValidatorInstance = null;

				if (Validator.isNotNull(portlet.getPreferencesValidator())) {
					prefsValidatorInstance =
						(PreferencesValidator)portletClassLoader.loadClass(
							portlet.getPreferencesValidator()).newInstance();

					try {
						if (PropsValues.PREFERENCE_VALIDATE_ON_STARTUP) {
							prefsValidatorInstance.validate(
								PortletPreferencesSerializer.fromDefaultXML(
									portlet.getDefaultPreferences()));
						}
					}
					catch (Exception e1) {
						_log.warn(
							"Portlet with the name " + portlet.getPortletId() +
								" does not have valid default preferences");
					}
				}

				Map<String, ResourceBundle> resourceBundles = null;

				if (Validator.isNotNull(portlet.getResourceBundle())) {
					resourceBundles = CollectionFactory.getHashMap();

					initResourceBundle(
						resourceBundles, portlet, portletClassLoader,
						LocaleUtil.getDefault());

					Iterator<String> supportLocalesItr =
						portlet.getSupportedLocales().iterator();

					while (supportLocalesItr.hasNext()) {
						String supportedLocale = supportLocalesItr.next();

						Locale locale = LocaleUtil.fromLanguageId(
							supportedLocale);

						initResourceBundle(
							resourceBundles, portlet, portletClassLoader,
							locale);
					}
				}

				PortletBag portletBag = new PortletBag(
					portlet.getPortletId(), ctx, portletInstance,
					configurationActionInstance, indexerInstance,
					schedulerInstance, friendlyURLMapperInstance,
					urlEncoderInstance, portletDataHandlerInstance,
					portletLayoutListenerInstance, popMessageListenerInstance,
					socialActivityInterpreterInstance, prefsValidatorInstance,
					resourceBundles);

				PortletBagPool.put(portlet.getPortletId(), portletBag);

				if (!portletsItr.hasNext()) {
					initPortletApp(portlet, ctx, portletClassLoader);
				}

				try {
					PortletInstanceFactory.create(portlet, ctx);
				}
				catch (Exception e1) {
					_log.error(e1, e1);
				}
			}

			// Struts bridges

			if (!strutsBridges) {
				strutsBridges = GetterUtil.getBoolean(
					ctx.getInitParameter("struts-bridges-context-provider"));
			}

			if (strutsBridges) {
				ctx.setAttribute(
					ServletContextProvider.STRUTS_BRIDGES_CONTEXT_PROVIDER,
					new LiferayServletContextProvider());
			}

			// Portlet display

			String xml = HttpUtil.URLtoString(ctx.getResource(
				"/WEB-INF/liferay-display.xml"));

			PortletCategory newPortletCategory =
				PortletLocalServiceUtil.getWARDisplay(servletContextName, xml);

			for (int i = 0; i < companyIds.length; i++) {
				long companyId = companyIds[i];

				PortletCategory portletCategory =
					(PortletCategory)WebAppPool.get(
						String.valueOf(companyId), WebKeys.PORTLET_CATEGORY);

				if (portletCategory != null) {
					portletCategory.merge(newPortletCategory);
				}
				else {
					_log.error(
						"Unable to register portlet for company " + companyId +
							" because it does not exist");
				}
			}

			// Portlet properties

			String portletPropsName = ctx.getInitParameter(
				"portlet_properties");

			if (Validator.isNotNull(portletPropsName)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Loading portlet properties " + portletPropsName);
				}

				Properties portletProps = new Properties();

				PropertiesUtil.load(
					portletProps,
					StringUtil.read(portletClassLoader, portletPropsName));

				if (_log.isDebugEnabled()) {
					String portletPropsString = PropertiesUtil.list(
						portletProps);

					_log.debug(portletPropsString);
				}

				processProperties(
					servletContextName, portletClassLoader, portletProps);
			}

			// Service builder properties

			processServiceBuilder(ctx, portletClassLoader);

			// Variables

			_vars.put(
				servletContextName,
				new ObjectValuePair<long[], List<Portlet>>(
					companyIds, portlets));

			if (_log.isInfoEnabled()) {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" registered successfully");
			}
		}
		catch (Exception e2) {
			throw new HotDeployException(
				"Error registering portlets for " + servletContextName, e2);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		String servletContextName = null;

		try {
			ServletContext ctx = event.getServletContext();

			servletContextName = ctx.getServletContextName();

			if (_log.isDebugEnabled()) {
				_log.debug("Invoking undeploy for " + servletContextName);
			}

			ObjectValuePair<long[], List<Portlet>> ovp =
				_vars.remove(servletContextName);

			if (ovp == null) {
				return;
			}

			long[] companyIds = ovp.getKey();
			List<Portlet> portlets = ovp.getValue();

			Set<String> portletIds = new HashSet<String>();

			if (portlets != null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unregistering portlets for " + servletContextName);
				}

				Iterator<Portlet> itr = portlets.iterator();

				while (itr.hasNext()) {
					Portlet portlet = itr.next();

					POPServerUtil.deleteListener(
						portlet.getPopMessageListenerInstance());

					SocialActivityInterpreterLocalServiceUtil.
						deleteActivityInterpreter(
							portlet.getSocialActivityInterpreterInstance());

					PortletInstanceFactory.destroy(portlet);

					portletIds.add(portlet.getPortletId());
				}
			}

			if (portletIds.size() > 0) {
				for (int i = 0; i < companyIds.length; i++) {
					long companyId = companyIds[i];

					PortletCategory portletCategory =
						(PortletCategory)WebAppPool.get(
							String.valueOf(companyId),
							WebKeys.PORTLET_CATEGORY);

					portletCategory.separate(portletIds);
				}
			}

			PortletResourceBundles.remove(servletContextName);

			if (_log.isInfoEnabled()) {
				_log.info(
					portlets.size() + " portlets for " + servletContextName +
						" unregistered successfully");
			}
		}
		catch (Exception e) {
			throw new HotDeployException(
				"Error unregistering portlets for " + servletContextName, e);
		}
	}

	protected void initPortletApp(
			Portlet portlet, ServletContext ctx, ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = ctx.getServletContextName();

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, ctx);

		PortletContext portletCtx = portletConfig.getPortletContext();

		PortletContextBag portletContextBag = new PortletContextBag(
			servletContextName);

		PortletContextBagPool.put(servletContextName, portletContextBag);

		PortletApp portletApp = portlet.getPortletApp();

		Map<String, String> customUserAttributes =
			portletApp.getCustomUserAttributes();

		for (Map.Entry<String, String> entry :
				customUserAttributes.entrySet()) {

			String attrCustomClass = entry.getValue();

			CustomUserAttributes customUserAttributesInstance =
				(CustomUserAttributes)portletClassLoader.loadClass(
					attrCustomClass).newInstance();

			portletContextBag.getCustomUserAttributes().put(
				attrCustomClass, customUserAttributesInstance);
		}

		Set<PortletFilter> portletFilters = portletApp.getPortletFilters();

		for (PortletFilter portletFilter : portletFilters) {
			javax.portlet.filter.PortletFilter portletFilterInstance =
				(javax.portlet.filter.PortletFilter)
					portletClassLoader.loadClass(
						portletFilter.getFilterClass()).newInstance();

			portletContextBag.getPortletFilters().put(
				portletFilter.getFilterName(), portletFilterInstance);

			PortletFilterFactory.create(portletFilter, portletCtx);
		}

		Set<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		for (PortletURLListener portletURLListener : portletURLListeners) {
			PortletURLGenerationListener portletURLListenerInstance =
				(PortletURLGenerationListener)portletClassLoader.loadClass(
					portletURLListener.getListenerClass()).newInstance();

			portletContextBag.getPortletURLListeners().put(
				portletURLListener.getListenerClass(),
				portletURLListenerInstance);

			PortletURLListenerFactory.create(portletURLListener);
		}
	}

	protected void initResourceBundle(
			Map<String, ResourceBundle> resourceBundles, Portlet portlet,
			ClassLoader portletClassLoader, Locale locale) {

		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
				portlet.getResourceBundle(), locale, portletClassLoader);

			resourceBundles.put(
				LocaleUtil.toLanguageId(locale), resourceBundle);
		}
		catch (MissingResourceException mre) {
			_log.warn(mre.getMessage());
		}
	}

	protected void processProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portletProps)
		throws Exception {

		String languageBundleName = portletProps.getProperty("language.bundle");

		if (Validator.isNotNull(languageBundleName)) {
			Locale[] locales = LanguageUtil.getAvailableLocales();

			for (int i = 0; i < locales.length; i++) {
				ResourceBundle bundle = ResourceBundle.getBundle(
					languageBundleName, locales[i], portletClassLoader);

				PortletResourceBundles.put(
					servletContextName, LocaleUtil.toLanguageId(locales[i]),
					bundle);
			}
		}

		String[] resourceActionConfigs = StringUtil.split(
			portletProps.getProperty("resource.actions.configs"));

		for (int i = 0; i < resourceActionConfigs.length; i++) {
			ResourceActionsUtil.read(
				servletContextName, portletClassLoader,
				resourceActionConfigs[i]);
		}
	}

	protected void processServiceBuilder(
			ServletContext ctx, ClassLoader portletClassLoader)
		throws Exception {

		if (portletClassLoader.getResourceAsStream(
				"portlet-service.properties") == null) {

			return;
		}

		Properties serviceBuilderProps = new Properties();

		PropertiesUtil.load(
			serviceBuilderProps,
			StringUtil.read(portletClassLoader, "portlet-service.properties"));

		String buildNamespace = GetterUtil.getString(
			serviceBuilderProps.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			serviceBuilderProps.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			serviceBuilderProps.getProperty("build.date"));

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
		}

		ServiceComponentLocalServiceUtil.updateServiceComponent(
			ctx, portletClassLoader, buildNamespace, buildNumber, buildDate);
	}

	private static Log _log = LogFactory.getLog(PortletHotDeployListener.class);

	private static Map<String, ObjectValuePair<long[], List<Portlet>>> _vars =
		new HashMap<String, ObjectValuePair<long[], List<Portlet>>>();

}