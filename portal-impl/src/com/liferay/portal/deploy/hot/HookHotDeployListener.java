/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.bean.ContextClassLoaderBeanHandler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.InvokerAction;
import com.liferay.portal.kernel.events.InvokerSessionAction;
import com.liferay.portal.kernel.events.InvokerSimpleAction;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Release;
import com.liferay.portal.security.auth.AuthFailure;
import com.liferay.portal.security.auth.AuthPipeline;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.security.auth.FullNameValidator;
import com.liferay.portal.security.auth.FullNameValidatorFactory;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.security.auth.ScreenNameValidator;
import com.liferay.portal.security.auth.ScreenNameValidatorFactory;
import com.liferay.portal.security.ldap.AttributesTransformer;
import com.liferay.portal.security.ldap.AttributesTransformerFactory;
import com.liferay.portal.service.ReleaseLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.servlet.filters.autologin.AutoLoginFilter;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.spring.aop.ServiceHookAdvice;
import com.liferay.portal.struts.MultiMessageResources;
import com.liferay.portal.struts.MultiMessageResourcesFactory;
import com.liferay.portal.upgrade.UpgradeProcessUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.DefaultControlPanelEntryFactory;
import com.liferay.util.UniqueList;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * <a href="HookHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class HookHotDeployListener
	extends BaseHotDeployListener implements PropsKeys {

	public HookHotDeployListener() {
		for (String key : _PROPS_VALUES_STRING_ARRAY) {
			_stringArraysContainerMap.put(key, new StringArraysContainer(key));
		}
	}

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error registering hook for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error unregistering hook for ", t);
		}
	}

	protected boolean containsKey(Properties portalProperties, String key) {
		if (_log.isDebugEnabled()) {
			return true;
		}
		else {
			return portalProperties.containsKey(key);
		}
	}

	protected void destroyCustomJspBag(CustomJspBag customJspBag) {
		String customJspDir = customJspBag.getCustomJspDir();
		List<String> customJsps = customJspBag.getCustomJsps();
		//String timestamp = customJspBag.getTimestamp();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(
				pos + customJspDir.length(), customJsp.length());

			File portalJspFile = new File(portalWebDir + portalJsp);
			File portalJspBackupFile = getPortalJspBackupFile(portalJspFile);

			if (portalJspBackupFile.exists()) {
				FileUtil.copyFile(portalJspBackupFile, portalJspFile);

				portalJspBackupFile.delete();
			}
			else if (portalJspFile.exists()) {
				portalJspFile.delete();
			}
		}
	}

	protected void destroyPortalProperties(
			String servletContextName, Properties portalProperties)
		throws Exception {

		PropsUtil.removeProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Original locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Original locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		resetPortalProperties(servletContextName, portalProperties, false);

		if (portalProperties.containsKey(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS)) {

			DefaultControlPanelEntryFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.DL_HOOK_IMPL)) {
			com.liferay.documentlibrary.util.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.IMAGE_HOOK_IMPL)) {
			com.liferay.portal.image.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {

			AttributesTransformerFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.MAIL_HOOK_IMPL)) {
			com.liferay.mail.util.HookFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR)) {

			EmailAddressGeneratorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_VALIDATOR)) {
			FullNameValidatorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR)) {

			ScreenNameGeneratorFactory.setInstance(null);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR)) {

			ScreenNameValidatorFactory.setInstance(null);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-hook.xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering hook for " + servletContextName);
		}

		_servletContextNames.add(servletContextName);

		ClassLoader portletClassLoader = event.getContextClassLoader();

		Document doc = SAXReaderUtil.read(xml, true);

		Element root = doc.getRootElement();

		String portalPropertiesLocation = root.elementText("portal-properties");

		if (Validator.isNotNull(portalPropertiesLocation)) {
			Configuration portalPropertiesConfiguration = null;

			try {
				String name = portalPropertiesLocation;

				int pos = name.lastIndexOf(".properties");

				if (pos != -1) {
					name = name.substring(0, pos);
				}

				portalPropertiesConfiguration =
					ConfigurationFactoryUtil.getConfiguration(
						portletClassLoader, name);
			}
			catch (Exception e) {
				_log.error("Unable to read " + portalPropertiesLocation, e);
			}

			if (portalPropertiesConfiguration != null) {
				Properties portalProperties =
					portalPropertiesConfiguration.getProperties();

				if (portalProperties.size() > 0) {
					_portalPropertiesMap.put(
						servletContextName, portalProperties);

					// Initialize properties, auto logins, model listeners, and
					// events in that specific order. Events have to be loaded
					// last because they may require model listeners to have
					// been registered.

					initPortalProperties(
						servletContextName, portletClassLoader,
						portalProperties);
					initAuthFailures(
						servletContextName, portletClassLoader,
						portalProperties);
					initAutoDeployListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initAutoLogins(
						servletContextName, portletClassLoader,
						portalProperties);
					initHotDeployListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initModelListeners(
						servletContextName, portletClassLoader,
						portalProperties);
					initEvents(
						servletContextName, portletClassLoader,
						portalProperties);
				}
			}
		}

		LanguagesContainer languagesContainer = new LanguagesContainer();

		_languagesContainerMap.put(servletContextName, languagesContainer);

		List<Element> languagePropertiesEls = root.elements(
			"language-properties");

		for (Element languagePropertiesEl : languagePropertiesEls) {
			String languagePropertiesLocation = languagePropertiesEl.getText();

			try {
				URL url = portletClassLoader.getResource(
					languagePropertiesLocation);

				if (url == null) {
					continue;
				}

				InputStream is = url.openStream();

				Properties properties = new Properties();

				properties.load(is);

				is.close();

				String localeKey = getLocaleKey(languagePropertiesLocation);

				if (localeKey != null) {
					languagesContainer.addLanguage(localeKey, properties);
				}
			}
			catch (Exception e) {
				_log.error("Unable to read " + languagePropertiesLocation, e);
			}
		}

		String customJspDir = root.elementText("custom-jsp-dir");

		if (Validator.isNotNull(customJspDir)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Custom JSP directory: " + customJspDir);
			}

			List<String> customJsps = new ArrayList<String>();

			String webDir = servletContext.getRealPath(StringPool.SLASH);

			getCustomJsps(servletContext, webDir, customJspDir, customJsps);

			if (customJsps.size() > 0) {
				CustomJspBag customJspBag = new CustomJspBag(
					customJspDir, customJsps);

				if (_log.isDebugEnabled()) {
					StringBuilder sb = new StringBuilder();

					sb.append("Custom JSP files:\n");

					Iterator<String> itr = customJsps.iterator();

					while (itr.hasNext()) {
						String customJsp = itr.next();

						sb.append(customJsp);

						if (itr.hasNext()) {
							sb.append(StringPool.NEW_LINE);
						}
					}

					_log.debug(sb.toString());
				}

				_customJspBagsMap.put(servletContextName, customJspBag);

				initCustomJspBag(customJspBag);
			}
		}

		ServicesContainer servicesContainer = new ServicesContainer();

		_servicesContainerMap.put(servletContextName, servicesContainer);

		List<Element> serviceEls = root.elements("service");

		for (Element serviceEl : serviceEls) {
			String serviceType = serviceEl.elementText("service-type");
			String serviceImpl = serviceEl.elementText("service-impl");

			Class<?> serviceTypeClass = portletClassLoader.loadClass(
				serviceType);
			Class<?> serviceImplClass = portletClassLoader.loadClass(
				serviceImpl);

			Constructor<?> serviceImplConstructor =
				serviceImplClass.getConstructor(new Class[] {serviceTypeClass});

			Object serviceImplInstance = serviceImplConstructor.newInstance(
				PortalBeanLocatorUtil.locate(serviceType));

			serviceImplInstance = Proxy.newProxyInstance(
				portletClassLoader, new Class[] {serviceTypeClass},
				new ContextClassLoaderBeanHandler(
					serviceImplInstance, portletClassLoader));

			servicesContainer.addService(serviceType, serviceImplInstance);
		}

		// Begin backwards compatibility for 5.1.0

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.get(servletContextName);

		if (modelListenersContainer == null) {
			modelListenersContainer = new ModelListenersContainer();

			_modelListenersContainerMap.put(
				servletContextName, modelListenersContainer);
		}

		List<Element> modelListenerEls = root.elements("model-listener");

		for (Element modelListenerEl : modelListenerEls) {
			String modelName = modelListenerEl.elementText("model-name");
			String modelListenerClassName = modelListenerEl.elementText(
				"model-listener-class");

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				modelName, modelListenerClassName, portletClassLoader);

			if (modelListener != null) {
				modelListenersContainer.registerModelListener(
					modelName, modelListener);
			}
		}

		EventsContainer eventsContainer = _eventsContainerMap.get(
			servletContextName);

		if (eventsContainer == null) {
			eventsContainer = new EventsContainer();

			_eventsContainerMap.put(servletContextName, eventsContainer);
		}

		List<Element> eventEls = root.elements("event");

		for (Element eventEl : eventEls) {
			String eventName = eventEl.elementText("event-type");
			String eventClassName = eventEl.elementText("event-class");

			Object obj = initEvent(
				eventName, eventClassName, portletClassLoader);

			if (obj != null) {
				eventsContainer.registerEvent(eventName, obj);
			}
		}

		// End backwards compatibility for 5.1.0

		registerClpMessageListeners(servletContext, portletClassLoader);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + servletContextName + " is available for use");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		if (!_servletContextNames.remove(servletContextName)) {
			return;
		}

		AuthenticatorsContainer authenticatorsContainer =
			_authenticatorsContainerMap.remove(servletContextName);

		if (authenticatorsContainer != null) {
			authenticatorsContainer.unregisterAuthenticators();
		}

		AuthFailuresContainer authFailuresContainer =
			_authFailuresContainerMap.remove(servletContextName);

		if (authFailuresContainer != null) {
			authFailuresContainer.unregisterAuthFailures();
		}

		AutoDeployListenersContainer autoDeployListenersContainer =
			_autoDeployListenersContainerMap.remove(servletContextName);

		if (autoDeployListenersContainer != null) {
			autoDeployListenersContainer.unregisterAutoDeployListeners();
		}

		AutoLoginsContainer autoLoginsContainer =
			_autoLoginsContainerMap.remove(servletContextName);

		if (autoLoginsContainer != null) {
			autoLoginsContainer.unregisterAutoLogins();
		}

		CustomJspBag customJspBag = _customJspBagsMap.remove(
			servletContextName);

		if (customJspBag != null) {
			destroyCustomJspBag(customJspBag);
		}

		EventsContainer eventsContainer = _eventsContainerMap.remove(
			servletContextName);

		if (eventsContainer != null) {
			eventsContainer.unregisterEvents();
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			_hotDeployListenersContainerMap.remove(servletContextName);

		if (hotDeployListenersContainer != null) {
			hotDeployListenersContainer.unregisterHotDeployListeners();
		}

		LanguagesContainer languagesContainer = _languagesContainerMap.remove(
			servletContextName);

		if (languagesContainer != null) {
			languagesContainer.unregisterLanguages();
		}

		ModelListenersContainer modelListenersContainer =
			_modelListenersContainerMap.remove(servletContextName);

		if (modelListenersContainer != null) {
			modelListenersContainer.unregisterModelListeners();
		}

		Properties portalProperties = _portalPropertiesMap.remove(
			servletContextName);

		if (portalProperties != null) {
			destroyPortalProperties(servletContextName, portalProperties);
		}

		ServicesContainer servicesContainer = _servicesContainerMap.remove(
			servletContextName);

		if (servicesContainer != null) {
			servicesContainer.unregisterServices();
		}

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			_log.info("Hook for " + servletContextName + " was unregistered");
		}
	}

	protected void getCustomJsps(
		ServletContext servletContext, String webDir, String resourcePath,
		List<String> customJsps) {

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		for (String curResourcePath : resourcePaths) {
			if (curResourcePath.endsWith(StringPool.SLASH)) {
				getCustomJsps(
					servletContext, webDir, curResourcePath, customJsps);
			}
			else {
				String customJsp = webDir + curResourcePath;

				customJsp = StringUtil.replace(
					customJsp, StringPool.DOUBLE_SLASH, StringPool.SLASH);

				customJsps.add(customJsp);
			}
		}
	}

	protected String getLocaleKey(String languagePropertiesLocation) {
		int x = languagePropertiesLocation.indexOf(StringPool.UNDERLINE);
		int y = languagePropertiesLocation.indexOf(".properties");

		if ((x != -1) && (y != 1)) {
			String localeKey = languagePropertiesLocation.substring(x + 1, y);

			Locale locale = LocaleUtil.fromLanguageId(localeKey);

			locale = LanguageUtil.getLocale(locale.getLanguage());

			if (locale != null) {
				return locale.toString();
			}
		}

		return null;
	}

	protected BasePersistence<?> getPersistence(String modelName) {
		int pos = modelName.lastIndexOf(StringPool.PERIOD);

		String entityName = modelName.substring(pos + 1);

		pos = modelName.lastIndexOf(".model.");

		String packagePath = modelName.substring(0, pos);

		return (BasePersistence<?>)PortalBeanLocatorUtil.locate(
			packagePath + ".service.persistence." + entityName + "Persistence");
	}

	protected File getPortalJspBackupFile(File portalJspFile) {
		String fileName = portalJspFile.toString();

		if (fileName.endsWith(".jsp")) {
			fileName =
				fileName.substring(0, fileName.length() - 4) + ".portal.jsp";
		}
		else if (fileName.endsWith(".jspf")) {
			fileName =
				fileName.substring(0, fileName.length() - 5) + ".portal.jspf";
		}

		return new File(fileName);
	}

	protected void initAuthenticators(
			ClassLoader portletClassLoader, Properties portalProperties,
			String key, AuthenticatorsContainer authenticatorsContainer)
		throws Exception {

		String[] authenticatorClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authenticatorClassName : authenticatorClassNames) {
			Authenticator authenticator =
				(Authenticator)portletClassLoader.loadClass(
					authenticatorClassName).newInstance();

			authenticator = (Authenticator)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {Authenticator.class},
				new ContextClassLoaderBeanHandler(
					authenticator, portletClassLoader));

			authenticatorsContainer.registerAuthenticator(
				key, authenticator);
		}
	}

	protected void initAuthenticators(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AuthenticatorsContainer authenticatorsContainer =
			new AuthenticatorsContainer();

		_authenticatorsContainerMap.put(
			servletContextName, authenticatorsContainer);

		initAuthenticators(
			portletClassLoader, portalProperties, AUTH_PIPELINE_PRE,
			authenticatorsContainer);
		initAuthenticators(
			portletClassLoader, portalProperties, AUTH_PIPELINE_POST,
			authenticatorsContainer);
	}

	protected void initAuthFailures(
			ClassLoader portletClassLoader, Properties portalProperties,
			String key, AuthFailuresContainer authFailuresContainer)
		throws Exception {

		String[] authFailureClassNames = StringUtil.split(
			portalProperties.getProperty(key));

		for (String authFailureClassName : authFailureClassNames) {
			AuthFailure authFailure = (AuthFailure)portletClassLoader.loadClass(
				authFailureClassName).newInstance();

			authFailure = (AuthFailure)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {AuthFailure.class},
				new ContextClassLoaderBeanHandler(
					authFailure, portletClassLoader));

			authFailuresContainer.registerAuthFailure(
				key, authFailure);
		}
	}

	protected void initAuthFailures(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AuthFailuresContainer authFailuresContainer =
			new AuthFailuresContainer();

		_authFailuresContainerMap.put(
			servletContextName, authFailuresContainer);

		initAuthFailures(
			portletClassLoader, portalProperties, AUTH_FAILURE,
			authFailuresContainer);
		initAuthFailures(
			portletClassLoader, portalProperties, AUTH_MAX_FAILURES,
			authFailuresContainer);
	}

	protected void initAutoDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] autoDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.AUTO_DEPLOY_LISTENERS));

		if (autoDeployListenerClassNames.length == 0) {
			return;
		}

		AutoDeployListenersContainer autoDeployListenersContainer =
			new AutoDeployListenersContainer();

		_autoDeployListenersContainerMap.put(
			servletContextName, autoDeployListenersContainer);

		for (String autoDeployListenerClassName :
				autoDeployListenerClassNames) {

			AutoDeployListener autoDeployListener =
				(AutoDeployListener)portletClassLoader.loadClass(
					autoDeployListenerClassName).newInstance();

			autoDeployListener = (AutoDeployListener)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {AutoDeployListener.class},
				new ContextClassLoaderBeanHandler(
					autoDeployListener, portletClassLoader));

			autoDeployListenersContainer.registerAutoDeployListener(
				autoDeployListener);
		}
	}

	protected void initAutoLogins(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AutoLoginsContainer autoLoginsContainer = new AutoLoginsContainer();

		_autoLoginsContainerMap.put(servletContextName, autoLoginsContainer);

		String[] autoLoginClassNames = StringUtil.split(
			portalProperties.getProperty(AUTO_LOGIN_HOOKS));

		for (String autoLoginClassName : autoLoginClassNames) {
			AutoLogin autoLogin = (AutoLogin)portletClassLoader.loadClass(
				autoLoginClassName).newInstance();

			autoLogin = (AutoLogin)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {AutoLogin.class},
				new ContextClassLoaderBeanHandler(
					autoLogin, portletClassLoader));

			autoLoginsContainer.registerAutoLogin(autoLogin);
		}
	}

	protected void initCustomJspBag(CustomJspBag customJspBag)
		throws Exception {

		String customJspDir = customJspBag.getCustomJspDir();
		List<String> customJsps = customJspBag.getCustomJsps();
		//String timestamp = customJspBag.getTimestamp();

		String portalWebDir = PortalUtil.getPortalWebDir();

		for (String customJsp : customJsps) {
			int pos = customJsp.indexOf(customJspDir);

			String portalJsp = customJsp.substring(
				pos + customJspDir.length(), customJsp.length());

			File portalJspFile = new File(portalWebDir + portalJsp);
			File portalJspBackupFile = getPortalJspBackupFile(portalJspFile);

			if (portalJspFile.exists() && !portalJspBackupFile.exists()) {
				FileUtil.copyFile(portalJspFile, portalJspBackupFile);
			}

			String customJspContent = FileUtil.read(customJsp);

			FileUtil.write(portalJspFile, customJspContent);
		}
	}

	protected Object initEvent(
			String eventName, String eventClassName,
			ClassLoader portletClassLoader)
		throws Exception {

		if (eventName.equals(APPLICATION_STARTUP_EVENTS)) {
			SimpleAction simpleAction =
				(SimpleAction)portletClassLoader.loadClass(
					eventClassName).newInstance();

			simpleAction = new InvokerSimpleAction(
				simpleAction, portletClassLoader);

			long companyId = CompanyThreadLocal.getCompanyId();

			long[] companyIds = PortalInstances.getCompanyIds();

			for (long curCompanyId : companyIds) {
				CompanyThreadLocal.setCompanyId(curCompanyId);

				simpleAction.run(new String[] {String.valueOf(curCompanyId)});
			}

			CompanyThreadLocal.setCompanyId(companyId);

			return null;
		}

		if (ArrayUtil.contains(_PROPS_KEYS_EVENTS, eventName)) {
			Action action = (Action)portletClassLoader.loadClass(
				eventClassName).newInstance();

			action = new InvokerAction(action, portletClassLoader);

			EventsProcessorUtil.registerEvent(eventName, action);

			return action;
		}

		if (ArrayUtil.contains(_PROPS_KEYS_SESSION_EVENTS, eventName)) {
			SessionAction sessionAction =
				(SessionAction)portletClassLoader.loadClass(
					eventClassName).newInstance();

			sessionAction = new InvokerSessionAction(
				sessionAction, portletClassLoader);

			EventsProcessorUtil.registerEvent(eventName, sessionAction);

			return sessionAction;
		}

		return null;
	}

	protected void initEvents(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		EventsContainer eventsContainer = new EventsContainer();

		_eventsContainerMap.put(servletContextName, eventsContainer);

		Iterator<Object> itr = portalProperties.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String)itr.next();

			if (!key.equals(APPLICATION_STARTUP_EVENTS) &&
				!ArrayUtil.contains(_PROPS_KEYS_EVENTS, key) &&
				!ArrayUtil.contains(_PROPS_KEYS_SESSION_EVENTS, key)) {

				continue;
			}

			String eventName = key;
			String[] eventClassNames = StringUtil.split(
				portalProperties.getProperty(key));

			for (String eventClassName : eventClassNames) {
				Object obj = initEvent(
					eventName, eventClassName, portletClassLoader);

				if (obj == null) {
					continue;
				}

				eventsContainer.registerEvent(eventName, obj);
			}
		}
	}

	protected void initHotDeployListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		String[] hotDeployListenerClassNames = StringUtil.split(
			portalProperties.getProperty(PropsKeys.HOT_DEPLOY_LISTENERS));

		if (hotDeployListenerClassNames.length == 0) {
			return;
		}

		HotDeployListenersContainer hotDeployListenersContainer =
			new HotDeployListenersContainer();

		_hotDeployListenersContainerMap.put(
			servletContextName, hotDeployListenersContainer);

		for (String hotDeployListenerClassName : hotDeployListenerClassNames) {
			HotDeployListener hotDeployListener =
				(HotDeployListener)portletClassLoader.loadClass(
					hotDeployListenerClassName).newInstance();

			hotDeployListener = (HotDeployListener)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {HotDeployListener.class},
				new ContextClassLoaderBeanHandler(
					hotDeployListener, portletClassLoader));

			hotDeployListenersContainer.registerHotDeployListener(
				hotDeployListener);
		}
	}

	@SuppressWarnings("unchecked")
	protected ModelListener<BaseModel<?>> initModelListener(
			String modelName, String modelListenerClassName,
			ClassLoader portletClassLoader)
		throws Exception {

		ModelListener<BaseModel<?>> modelListener =
			(ModelListener<BaseModel<?>>)portletClassLoader.loadClass(
				modelListenerClassName).newInstance();

		modelListener = (ModelListener<BaseModel<?>>)Proxy.newProxyInstance(
			portletClassLoader, new Class[] {ModelListener.class},
			new ContextClassLoaderBeanHandler(
				modelListener, portletClassLoader));

		BasePersistence persistence = getPersistence(modelName);

		persistence.registerListener(modelListener);

		return modelListener;
	}

	protected void initModelListeners(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		ModelListenersContainer modelListenersContainer =
			new ModelListenersContainer();

		_modelListenersContainerMap.put(
			servletContextName, modelListenersContainer);

		Iterator<Object> itr = portalProperties.keySet().iterator();

		while (itr.hasNext()) {
			String key = (String)itr.next();

			if (!key.startsWith(VALUE_OBJECT_LISTENER)) {
				continue;
			}

			String modelName = key.substring(VALUE_OBJECT_LISTENER.length());
			String modelListenerClassName = portalProperties.getProperty(key);

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				modelName, modelListenerClassName, portletClassLoader);

			if (modelListener != null) {
				modelListenersContainer.registerModelListener(
					modelName, modelListener);
			}
		}
	}

	protected void initPortalProperties(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		PropsUtil.addProperties(portalProperties);

		if (_log.isDebugEnabled() && portalProperties.containsKey(LOCALES)) {
			_log.debug(
				"Portlet locales " + portalProperties.getProperty(LOCALES));
			_log.debug("Merged locales " + PropsUtil.get(LOCALES));
			_log.debug(
				"Merged locales array length " +
					PropsUtil.getArray(LOCALES).length);
		}

		resetPortalProperties(servletContextName, portalProperties, true);

		if (portalProperties.containsKey(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS)) {

			String controlPanelEntryClassName = portalProperties.getProperty(
				PropsKeys.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);

			ControlPanelEntry controlPanelEntry =
				(ControlPanelEntry)portletClassLoader.loadClass(
					controlPanelEntryClassName).newInstance();

			controlPanelEntry = (ControlPanelEntry)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {ControlPanelEntry.class},
				new ContextClassLoaderBeanHandler(
					controlPanelEntry, portletClassLoader));

			DefaultControlPanelEntryFactory.setInstance(controlPanelEntry);
		}

		if (portalProperties.containsKey(PropsKeys.DL_HOOK_IMPL)) {
			String dlHookClassName = portalProperties.getProperty(
				PropsKeys.DL_HOOK_IMPL);

			com.liferay.documentlibrary.util.Hook dlHook =
				(com.liferay.documentlibrary.util.Hook)
					portletClassLoader.loadClass(dlHookClassName).newInstance();

			dlHook =
				(com.liferay.documentlibrary.util.Hook)Proxy.newProxyInstance(
					portletClassLoader,
					new Class[] {com.liferay.documentlibrary.util.Hook.class},
					new ContextClassLoaderBeanHandler(
						dlHook, portletClassLoader));

			com.liferay.documentlibrary.util.HookFactory.setInstance(dlHook);
		}

		if (portalProperties.containsKey(PropsKeys.IMAGE_HOOK_IMPL)) {
			String imageHookClassName = portalProperties.getProperty(
				PropsKeys.IMAGE_HOOK_IMPL);

			com.liferay.portal.image.Hook imageHook =
				(com.liferay.portal.image.Hook)portletClassLoader.loadClass(
					imageHookClassName).newInstance();

			imageHook =
				(com.liferay.portal.image.Hook)Proxy.newProxyInstance(
					portletClassLoader,
					new Class[] {com.liferay.portal.image.Hook.class},
					new ContextClassLoaderBeanHandler(
						imageHook, portletClassLoader));

			com.liferay.portal.image.HookFactory.setInstance(imageHook);
		}

		if (portalProperties.containsKey(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {

			String attributesTransformerClassName =
				portalProperties.getProperty(
					PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL);

			AttributesTransformer attributesTransformer =
				(AttributesTransformer)portletClassLoader.loadClass(
					attributesTransformerClassName).newInstance();

			attributesTransformer =
				(AttributesTransformer)Proxy.newProxyInstance(
					portletClassLoader,
					new Class[] {AttributesTransformer.class},
					new ContextClassLoaderBeanHandler(
						attributesTransformer, portletClassLoader));

			AttributesTransformerFactory.setInstance(attributesTransformer);
		}

		if (portalProperties.containsKey(PropsKeys.MAIL_HOOK_IMPL)) {
			String mailHookClassName = portalProperties.getProperty(
				PropsKeys.MAIL_HOOK_IMPL);

			com.liferay.mail.util.Hook mailHook =
				(com.liferay.mail.util.Hook)portletClassLoader.loadClass(
					mailHookClassName).newInstance();

			mailHook =
				(com.liferay.mail.util.Hook)Proxy.newProxyInstance(
					portletClassLoader,
					new Class[] {com.liferay.mail.util.Hook.class},
					new ContextClassLoaderBeanHandler(
						mailHook, portletClassLoader));

			com.liferay.mail.util.HookFactory.setInstance(mailHook);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR)) {

			String emailAddressGeneratorClassName =
				portalProperties.getProperty(
					PropsKeys.USERS_EMAIL_ADDRESS_GENERATOR);

			EmailAddressGenerator emailAddressGenerator =
				(EmailAddressGenerator)portletClassLoader.loadClass(
					emailAddressGeneratorClassName).newInstance();

			emailAddressGenerator =
				(EmailAddressGenerator)Proxy.newProxyInstance(
					portletClassLoader,
					new Class[] {EmailAddressGenerator.class},
					new ContextClassLoaderBeanHandler(
						emailAddressGenerator, portletClassLoader));

			EmailAddressGeneratorFactory.setInstance(emailAddressGenerator);
		}

		if (portalProperties.containsKey(PropsKeys.USERS_FULL_NAME_VALIDATOR)) {
			String fullNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_FULL_NAME_VALIDATOR);

			FullNameValidator fullNameValidator =
				(FullNameValidator)portletClassLoader.loadClass(
					fullNameValidatorClassName).newInstance();

			fullNameValidator = (FullNameValidator)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {FullNameValidator.class},
				new ContextClassLoaderBeanHandler(
					fullNameValidator, portletClassLoader));

			FullNameValidatorFactory.setInstance(fullNameValidator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR)) {

			String screenNameGeneratorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_GENERATOR);

			ScreenNameGenerator screenNameGenerator =
				(ScreenNameGenerator)portletClassLoader.loadClass(
					screenNameGeneratorClassName).newInstance();

			screenNameGenerator = (ScreenNameGenerator)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {ScreenNameGenerator.class},
				new ContextClassLoaderBeanHandler(
					screenNameGenerator, portletClassLoader));

			ScreenNameGeneratorFactory.setInstance(screenNameGenerator);
		}

		if (portalProperties.containsKey(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR)) {

			String screenNameValidatorClassName = portalProperties.getProperty(
				PropsKeys.USERS_SCREEN_NAME_VALIDATOR);

			ScreenNameValidator screenNameValidator =
				(ScreenNameValidator)portletClassLoader.loadClass(
					screenNameValidatorClassName).newInstance();

			screenNameValidator = (ScreenNameValidator)Proxy.newProxyInstance(
				portletClassLoader, new Class[] {ScreenNameValidator.class},
				new ContextClassLoaderBeanHandler(
					screenNameValidator, portletClassLoader));

			ScreenNameValidatorFactory.setInstance(screenNameValidator);
		}

		if (portalProperties.containsKey(PropsKeys.RELEASE_INFO_BUILD_NUMBER) ||
			portalProperties.containsKey(PropsKeys.UPGRADE_PROCESSES)) {

			updateRelease(
				servletContextName, portletClassLoader, portalProperties);
		}
	}

	protected void resetPortalProperties(
			String servletContextName, Properties portalProperties,
			boolean initPhase)
		throws Exception {

		for (String key : _PROPS_VALUES_BOOLEAN) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Boolean value = Boolean.valueOf(GetterUtil.getBoolean(
					PropsUtil.get(key)));

				field.setBoolean(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_INTEGER) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Integer value = Integer.valueOf(GetterUtil.getInteger(
					PropsUtil.get(key)));

				field.setInt(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_LONG) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				Long value = Long.valueOf(GetterUtil.getLong(
					PropsUtil.get(key)));

				field.setLong(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_STRING) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				String value = GetterUtil.getString(PropsUtil.get(key));

				field.set(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		for (String key : _PROPS_VALUES_STRING_ARRAY) {
			String fieldName = StringUtil.replace(
				key.toUpperCase(), StringPool.PERIOD,  StringPool.UNDERLINE);

			if (!containsKey(portalProperties, key)) {
				continue;
			}

			try {
				Field field = PropsValues.class.getField(fieldName);

				StringArraysContainer stringArraysContainer =
					_stringArraysContainerMap.get(key);

				String[] value = null;

				if (initPhase) {
					value = PropsUtil.getArray(key);
				}

				stringArraysContainer.setPluginStringArray(
					servletContextName, value);

				value = stringArraysContainer.getMergedStringArray();

				field.set(null, value);
			}
			catch (Exception e) {
				_log.error(
					"Error setting field " + fieldName + ": " + e.getMessage());
			}
		}

		if (containsKey(portalProperties, LOCALES)) {
			PropsValues.LOCALES = PropsUtil.getArray(LOCALES);

			LanguageUtil.init();
		}

		CacheUtil.clearCache();
	}

	protected void updateRelease(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		int buildNumber = GetterUtil.getInteger(
			portalProperties.getProperty(PropsKeys.RELEASE_INFO_BUILD_NUMBER));

		if (buildNumber <= 0) {
			_log.error(
				"Skipping upgrade processes for " + servletContextName +
					" because \"release.info.build.number\" is not specified");

			return;
		}

		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.getRelease(
				servletContextName, buildNumber);
		}
		catch (PortalException pe) {
			int previousBuildNumber = GetterUtil.getInteger(
				portalProperties.getProperty(
					PropsKeys.RELEASE_INFO_PREVIOUS_BUILD_NUMBER),
				buildNumber);

			release = ReleaseLocalServiceUtil.addRelease(
				servletContextName, previousBuildNumber);
		}

		if (buildNumber == release.getBuildNumber()) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping upgrade processes for " + servletContextName +
						" because it is already up to date");
			}
		}
		else if (buildNumber < release.getBuildNumber()) {
			throw new UpgradeException(
				"Skipping upgrade processes for " + servletContextName +
					" because you are trying to upgrade with an older version");
		}
		else {
			String[] upgradeProcessClassNames = StringUtil.split(
				portalProperties.getProperty(PropsKeys.UPGRADE_PROCESSES));

			UpgradeProcessUtil.upgradeProcess(
				release.getBuildNumber(), upgradeProcessClassNames,
				portletClassLoader);
		}

		ReleaseLocalServiceUtil.updateRelease(
			release.getReleaseId(), buildNumber, null, true);
	}

	private static final String[] _PROPS_KEYS_EVENTS = new String[] {
		LOGIN_EVENTS_POST,
		LOGIN_EVENTS_PRE,
		LOGOUT_EVENTS_POST,
		LOGOUT_EVENTS_PRE,
		SERVLET_SERVICE_EVENTS_POST,
		SERVLET_SERVICE_EVENTS_PRE
	};

	private static final String[] _PROPS_KEYS_SESSION_EVENTS = new String[] {
		SERVLET_SESSION_CREATE_EVENTS,
		SERVLET_SESSION_DESTROY_EVENTS
	};

	private static final String[] _PROPS_VALUES_BOOLEAN = new String[] {
		"auth.forward.by.last.path",
		"captcha.check.portal.create_account",
		"field.enable.com.liferay.portal.model.Contact.birthday",
		"field.enable.com.liferay.portal.model.Contact.male",
		"field.enable.com.liferay.portal.model.Organization.status",
		"javascript.fast.load",
		"layout.template.cache.enabled",
		"layout.user.private.layouts.auto.create",
		"layout.user.private.layouts.enabled",
		"layout.user.private.layouts.modifiable",
		"layout.user.public.layouts.auto.create",
		"layout.user.public.layouts.enabled",
		"layout.user.public.layouts.modifiable",
		"login.create.account.allow.custom.password",
		"my.places.show.community.private.sites.with.no.layouts",
		"my.places.show.community.public.sites.with.no.layouts",
		"my.places.show.organization.private.sites.with.no.layouts",
		"my.places.show.organization.public.sites.with.no.layouts",
		"my.places.show.user.private.sites.with.no.layouts",
		"my.places.show.user.public.sites.with.no.layouts",
		"terms.of.use.required",
		"theme.css.fast.load",
		"theme.images.fast.load",
		"users.email.address.required",
		"users.screen.name.always.autogenerate"
	};

	private static final String[] _PROPS_VALUES_INTEGER = new String[] {
	};

	private static final String[] _PROPS_VALUES_LONG = new String[] {
	};

	private static final String[] _PROPS_VALUES_STRING = new String[] {
		"default.landing.page.path",
		"passwords.passwordpolicytoolkit.generator",
		"passwords.passwordpolicytoolkit.static"
	};

	private static final String[] _PROPS_VALUES_STRING_ARRAY = new String[] {
		"layout.static.portlets.all",
		"session.phishing.protected.attributes"
	};

	private static Log _log =
		LogFactoryUtil.getLog(HookHotDeployListener.class);

	private Map<String, AuthenticatorsContainer> _authenticatorsContainerMap =
		new HashMap<String, AuthenticatorsContainer>();
	private Map<String, AuthFailuresContainer> _authFailuresContainerMap =
		new HashMap<String, AuthFailuresContainer>();
	private Map<String, AutoDeployListenersContainer>
		_autoDeployListenersContainerMap =
			new HashMap<String, AutoDeployListenersContainer>();
	private Map<String, AutoLoginsContainer> _autoLoginsContainerMap =
		new HashMap<String, AutoLoginsContainer>();
	private Map<String, CustomJspBag> _customJspBagsMap =
		new HashMap<String, CustomJspBag>();
	private Map<String, EventsContainer> _eventsContainerMap =
		new HashMap<String, EventsContainer>();
	private Map<String, HotDeployListenersContainer>
		_hotDeployListenersContainerMap =
			new HashMap<String, HotDeployListenersContainer>();
	private Map<String, LanguagesContainer> _languagesContainerMap =
		new HashMap<String, LanguagesContainer>();
	private Map<String, ModelListenersContainer> _modelListenersContainerMap =
		new HashMap<String, ModelListenersContainer>();
	private Map<String, Properties> _portalPropertiesMap =
		new HashMap<String, Properties>();
	private Map<String, ServicesContainer> _servicesContainerMap =
		new HashMap<String, ServicesContainer>();
	private Set<String> _servletContextNames = new HashSet<String>();
	private Map<String, StringArraysContainer> _stringArraysContainerMap =
		new HashMap<String, StringArraysContainer>();

	private class AuthenticatorsContainer {

		public void registerAuthenticator(
			String key, Authenticator authenticator) {

			List<Authenticator> authenticators = _authenticators.get(key);

			if (authenticators == null) {
				authenticators = new ArrayList<Authenticator>();

				_authenticators.put(key, authenticators);
			}

			AuthPipeline.registerAuthenticator(key, authenticator);

			authenticators.add(authenticator);
		}

		public void unregisterAuthenticators() {
			for (Map.Entry<String, List<Authenticator>> entry :
					_authenticators.entrySet()) {

				String key = entry.getKey();
				List<Authenticator> authenticators = entry.getValue();

				for (Authenticator authenticator : authenticators) {
					AuthPipeline.unregisterAuthenticator(key, authenticator);
				}
			}
		}

		Map<String, List<Authenticator>> _authenticators =
			new HashMap<String, List<Authenticator>>();

	}

	private class AuthFailuresContainer {

		public void registerAuthFailure(String key, AuthFailure authFailure) {
			List<AuthFailure> authFailures = _authFailures.get(key);

			if (authFailures == null) {
				authFailures = new ArrayList<AuthFailure>();

				_authFailures.put(key, authFailures);
			}

			AuthPipeline.registerAuthFailure(key, authFailure);

			authFailures.add(authFailure);
		}

		public void unregisterAuthFailures() {
			for (Map.Entry<String, List<AuthFailure>> entry :
					_authFailures.entrySet()) {

				String key = entry.getKey();
				List<AuthFailure> authFailures = entry.getValue();

				for (AuthFailure authFailure : authFailures) {
					AuthPipeline.unregisterAuthFailure(key, authFailure);
				}
			}
		}

		Map<String, List<AuthFailure>> _authFailures =
			new HashMap<String, List<AuthFailure>>();

	}

	private class AutoDeployListenersContainer {

		public void registerAutoDeployListener(
			AutoDeployListener autoDeployListener) {

			AutoDeployDir autoDeployDir = AutoDeployUtil.getDir(
				AutoDeployDir.DEFAULT_NAME);

			if (autoDeployDir == null) {
				return;
			}

			autoDeployDir.registerListener(autoDeployListener);

			_autoDeployListeners.add(autoDeployListener);
		}

		public void unregisterAutoDeployListeners() {
			AutoDeployDir autoDeployDir = AutoDeployUtil.getDir(
				AutoDeployDir.DEFAULT_NAME);

			if (autoDeployDir == null) {
				return;
			}

			for (AutoDeployListener autoDeployListener : _autoDeployListeners) {
				autoDeployDir.unregisterListener(autoDeployListener);
			}
		}

		private List<AutoDeployListener> _autoDeployListeners =
			new ArrayList<AutoDeployListener>();

	}

	private class AutoLoginsContainer {

		public void registerAutoLogin(AutoLogin autoLogin) {
			AutoLoginFilter.registerAutoLogin(autoLogin);

			_autoLogins.add(autoLogin);
		}

		public void unregisterAutoLogins() {
			for (AutoLogin autoLogin : _autoLogins) {
				AutoLoginFilter.unregisterAutoLogin(autoLogin);
			}
		}

		List<AutoLogin> _autoLogins = new ArrayList<AutoLogin>();

	}

	private class CustomJspBag {

		public CustomJspBag(String customJspDir, List<String> customJsps) {
			_customJspDir = customJspDir;
			_customJsps = customJsps;
		}

		public String getCustomJspDir() {
			return _customJspDir;
		}

		public List<String> getCustomJsps() {
			return _customJsps;
		}

		private String _customJspDir;
		private List<String> _customJsps;

	}

	private class EventsContainer {

		public void registerEvent(String eventName, Object event) {
			List<Object> events = _eventsMap.get(eventName);

			if (events == null) {
				events = new ArrayList<Object>();

				_eventsMap.put(eventName, events);
			}

			events.add(event);
		}

		public void unregisterEvents() {
			for (Map.Entry<String, List<Object>> entry :
					_eventsMap.entrySet()) {

				String eventName = entry.getKey();
				List<Object> events = entry.getValue();

				for (Object event : events) {
					EventsProcessorUtil.unregisterEvent(eventName, event);
				}
			}
		}

		private Map<String, List<Object>> _eventsMap =
			new HashMap<String, List<Object>>();

	}

	private class HotDeployListenersContainer {

		public void registerHotDeployListener(
			HotDeployListener hotDeployListener) {

			HotDeployUtil.registerListener(hotDeployListener);

			_hotDeployListeners.add(hotDeployListener);
		}

		public void unregisterHotDeployListeners() {
			for (HotDeployListener hotDeployListener : _hotDeployListeners) {
				HotDeployUtil.unregisterListener(hotDeployListener);
			}
		}

		private List<HotDeployListener> _hotDeployListeners =
			new ArrayList<HotDeployListener>();

	}

	private class LanguagesContainer {

		public void addLanguage(String localeKey, Properties properties) {
			_multiMessageResources.putLocale(localeKey);

			Properties oldProperties = _multiMessageResources.putMessages(
				properties, localeKey);

			_languagesMap.put(localeKey, oldProperties);

			LanguageResources.clearCache();
		}

		public void unregisterLanguages() {
			for (String key : _languagesMap.keySet()) {
				Properties properties = _languagesMap.get(key);

				_multiMessageResources.putMessages(properties, key);
			}

			LanguageResources.clearCache();
		}

		private Map<String, Properties> _languagesMap =
			new HashMap<String, Properties>();
		private MultiMessageResources _multiMessageResources =
			MultiMessageResourcesFactory.getInstance();

	}

	private class ModelListenersContainer {

		public void registerModelListener(
			String modelName, ModelListener<BaseModel<?>> modelListener) {

			List<ModelListener<BaseModel<?>>> modelListeners =
				_modelListenersMap.get(modelName);

			if (modelListeners == null) {
				modelListeners = new ArrayList<ModelListener<BaseModel<?>>>();

				_modelListenersMap.put(modelName, modelListeners);
			}

			modelListeners.add(modelListener);
		}

		@SuppressWarnings("unchecked")
		public void unregisterModelListeners() {
			for (Map.Entry<String, List<ModelListener<BaseModel<?>>>> entry :
					_modelListenersMap.entrySet()) {

				String modelName = entry.getKey();
				List<ModelListener<BaseModel<?>>> modelListeners =
					entry.getValue();

				BasePersistence persistence = getPersistence(modelName);

				for (ModelListener<BaseModel<?>> modelListener :
						modelListeners) {

					persistence.unregisterListener(modelListener);
				}
			}
		}

		private Map<String, List<ModelListener<BaseModel<?>>>>
			_modelListenersMap =
				new HashMap<String, List<ModelListener<BaseModel<?>>>>();

	}

	private class ServicesContainer {

		public void addService(String serviceType, Object serviceImplInstance) {
			ServiceHookAdvice.setService(serviceType, serviceImplInstance);

			_serviceTypes.add(serviceType);
		}

		public void unregisterServices() {
			for (String serviceType : _serviceTypes) {
				ServiceHookAdvice.setService(serviceType, null);
			}
		}

		private List<String> _serviceTypes = new ArrayList<String>();

	}

	private class StringArraysContainer {

		private StringArraysContainer(String key) {
			_portalStringArray = PropsUtil.getArray(key);
		}

		public String[] getMergedStringArray() {
			List<String> mergedStringList = new UniqueList<String>();

			mergedStringList.addAll(ListUtil.fromArray(_portalStringArray));

			for (Map.Entry<String, String[]> entry :
					_pluginStringArrayMap.entrySet()) {

				String[] pluginStringArray = entry.getValue();

				mergedStringList.addAll(ListUtil.fromArray(pluginStringArray));
			}

			return mergedStringList.toArray(
				new String[mergedStringList.size()]);
		}

		public void setPluginStringArray(
			String servletContextName, String[] pluginStringArray) {

			if (pluginStringArray != null) {
				_pluginStringArrayMap.put(
					servletContextName, pluginStringArray);
			}
			else {
				_pluginStringArrayMap.remove(servletContextName);
			}
		}

		private String[] _portalStringArray;
		private Map<String, String[]> _pluginStringArrayMap =
			new HashMap<String, String[]>();

	}

}