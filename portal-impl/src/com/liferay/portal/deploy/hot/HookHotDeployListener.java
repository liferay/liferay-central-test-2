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

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.InvokerAction;
import com.liferay.portal.kernel.events.InvokerSessionAction;
import com.liferay.portal.kernel.events.InvokerSimpleAction;
import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.InvokerModelListener;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.InvokerAutoLogin;
import com.liferay.portal.security.ldap.AttributesTransformer;
import com.liferay.portal.security.ldap.AttributesTransformerFactory;
import com.liferay.portal.security.ldap.InvokerAttributesTransformer;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.servlet.filters.autologin.AutoLoginFilter;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.struts.MultiMessageResources;
import com.liferay.portal.struts.MultiMessageResourcesFactory;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

public class HookHotDeployListener
	extends BaseHotDeployListener implements PropsKeys {

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

	protected void destroyPortalProperties(Properties portalProperties)
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

		resetPortalProperties(portalProperties);

		if (portalProperties.contains(PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {
			AttributesTransformerFactory.setInstance(null);
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

					initPortalProperties(portletClassLoader, portalProperties);
					initAutoLogins(
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
			String modelListenerClass = modelListenerEl.elementText(
				"model-listener-class");

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				modelName, modelListenerClass, portletClassLoader);

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
			String eventClass = eventEl.elementText("event-class");

			Object obj = initEvent(eventName, eventClass, portletClassLoader);

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
			destroyPortalProperties(portalProperties);
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
		String localeKey = null;

		int x = languagePropertiesLocation.indexOf(StringPool.UNDERLINE);
		int y = languagePropertiesLocation.indexOf(".properties");

		if ((x != -1) && (y != 1)) {
			localeKey = languagePropertiesLocation.substring(x + 1, y);
		}

		return localeKey;
	}

	protected BasePersistence getPersistence(String modelName) {
		int pos = modelName.lastIndexOf(StringPool.PERIOD);

		String entityName = modelName.substring(pos + 1);

		pos = modelName.lastIndexOf(".model.");

		String packagePath = modelName.substring(0, pos);

		return (BasePersistence)PortalBeanLocatorUtil.locate(
			packagePath + ".service.persistence." + entityName +
				"Persistence.impl");
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

	protected void initAutoLogins(
			String servletContextName, ClassLoader portletClassLoader,
			Properties portalProperties)
		throws Exception {

		AutoLoginsContainer autoLoginsContainer = new AutoLoginsContainer();

		_autoLoginsContainerMap.put(servletContextName, autoLoginsContainer);

		String[] autoLoginClasses = StringUtil.split(
			portalProperties.getProperty(AUTO_LOGIN_HOOKS));

		for (String autoLoginClass : autoLoginClasses) {
			AutoLogin autoLogin = (AutoLogin)portletClassLoader.loadClass(
				autoLoginClass).newInstance();

			if (autoLogin != null) {
				autoLogin = new InvokerAutoLogin(autoLogin, portletClassLoader);

				autoLoginsContainer.registerAutoLogin(autoLogin);
			}
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
			String eventName, String eventClass, ClassLoader portletClassLoader)
		throws Exception {

		if (eventName.equals(APPLICATION_STARTUP_EVENTS)) {
			SimpleAction simpleAction =
				(SimpleAction)portletClassLoader.loadClass(
					eventClass).newInstance();

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
				eventClass).newInstance();

			action = new InvokerAction(action, portletClassLoader);

			EventsProcessorUtil.registerEvent(eventName, action);

			return action;
		}

		if (ArrayUtil.contains(_PROPS_KEYS_SESSION_EVENTS, eventName)) {
			SessionAction sessionAction =
				(SessionAction)portletClassLoader.loadClass(
					eventClass).newInstance();

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
			String[] eventClasses = StringUtil.split(
				portalProperties.getProperty(key));

			for (String eventClass : eventClasses) {
				Object obj = initEvent(
					eventName, eventClass, portletClassLoader);

				if (obj == null) {
					continue;
				}

				eventsContainer.registerEvent(eventName, obj);
			}
		}
	}

	protected ModelListener<BaseModel<?>> initModelListener(
			String modelName, String modelListenerClass,
			ClassLoader portletClassLoader)
		throws Exception {

		ModelListener<BaseModel<?>> modelListener =
			(ModelListener<BaseModel<?>>)portletClassLoader.loadClass(
				modelListenerClass).newInstance();

		modelListener = new InvokerModelListener<BaseModel<?>>(
			modelListener, portletClassLoader);

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
			String modelListenerClass = portalProperties.getProperty(key);

			ModelListener<BaseModel<?>> modelListener = initModelListener(
				modelName, modelListenerClass, portletClassLoader);

			if (modelListener != null) {
				modelListenersContainer.registerModelListener(
					modelName, modelListener);
			}
		}
	}

	protected void initPortalProperties(
			ClassLoader portletClassLoader, Properties portalProperties)
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

		resetPortalProperties(portalProperties);

		if (portalProperties.contains(PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL)) {
			String attributesTransformerClass = portalProperties.getProperty(
				PropsKeys.LDAP_ATTRS_TRANSFORMER_IMPL);

			AttributesTransformer attributesTransformer =
				(AttributesTransformer)portletClassLoader.loadClass(
					attributesTransformerClass).newInstance();

			attributesTransformer = new InvokerAttributesTransformer(
				attributesTransformer, portletClassLoader);

			AttributesTransformerFactory.setInstance(attributesTransformer);
		}
	}

	protected void resetPortalProperties(Properties portalProperties)
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

				String[] value = PropsUtil.getArray(key);

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
		"theme.images.fast.load"
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
		"layout.static.portlets.all"
	};

	private static Log _log =
		LogFactoryUtil.getLog(HookHotDeployListener.class);

	private Map<String, AutoLoginsContainer> _autoLoginsContainerMap =
		new HashMap<String, AutoLoginsContainer>();
	private Map<String, CustomJspBag> _customJspBagsMap =
		new HashMap<String, CustomJspBag>();
	private Map<String, EventsContainer> _eventsContainerMap =
		new HashMap<String, EventsContainer>();
	private Map<String, LanguagesContainer> _languagesContainerMap =
		new HashMap<String, LanguagesContainer>();
	private Map<String, ModelListenersContainer> _modelListenersContainerMap =
		new HashMap<String, ModelListenersContainer>();
	private Map<String, Properties> _portalPropertiesMap =
		new HashMap<String, Properties>();
	private Set<String> _servletContextNames = new HashSet<String>();

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
			_timestamp = Time.getTimestamp();
		}

		public String getCustomJspDir() {
			return _customJspDir;
		}

		public List<String> getCustomJsps() {
			return _customJsps;
		}

		public String getTimestamp() {
			return _timestamp;
		}

		private String _customJspDir;
		private List<String> _customJsps;
		private String _timestamp;

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

	private class LanguagesContainer {

		public void addLanguage(String localeKey, Properties properties) {
			_multiMessageResources.putLocale(localeKey);

			Properties oldProperties = _multiMessageResources.putMessages(
				properties, localeKey);

			_languagesMap.put(localeKey, oldProperties);
		}

		public void unregisterLanguages() {
			for (String key : _languagesMap.keySet()) {
				Properties properties = _languagesMap.get(key);

				_multiMessageResources.putMessages(properties, key);
			}
		}

		private MultiMessageResources _multiMessageResources =
			MultiMessageResourcesFactory.getInstance();
		private Map<String, Properties> _languagesMap =
			new HashMap<String, Properties>();

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

}