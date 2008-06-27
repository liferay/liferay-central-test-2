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

import com.liferay.portal.events.EventsProcessor;
import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.InvokerSimpleAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.servlet.filters.layoutcache.LayoutCacheUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * <a href="HookHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class HookHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Exception e) {
			throwHotDeployException(event, "Error registering hook for ", e);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Exception e) {
			throwHotDeployException(event, "Error unregistering hook for ", e);
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

	protected void destroyPortalProperties(Properties portalProperties)
		throws Exception {

		PropsUtil.removeProperties(portalProperties);

		if (_log.isDebugEnabled() &&
			portalProperties.containsKey(PropsKeys.LOCALES)) {

			_log.debug(
				"Portlet locales " +
					portalProperties.getProperty(PropsKeys.LOCALES));
			_log.debug(
				"Original locales " + PropsUtil.get(PropsKeys.LOCALES));
			_log.debug(
				"Original locales array length " +
					PropsUtil.getArray(PropsKeys.LOCALES).length);
		}

		resetPortalProperties(portalProperties);
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext ctx = event.getServletContext();

		String servletContextName = ctx.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(
			ctx.getResource("/WEB-INF/liferay-hook.xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering hook for " + servletContextName);
		}

		ClassLoader portletClassLoader = event.getContextClassLoader();

		Document doc = DocumentUtil.readDocumentFromXML(xml, true);

		Element root = doc.getRootElement();

		List<Element> eventEls = root.elements("event");

		for (Element eventEl : eventEls) {
			String eventClass = eventEl.elementText("event-class");
			String eventType = eventEl.elementText("event-type");

			Object obj = initEvent(eventClass, eventType, portletClassLoader);

			if (obj != null) {
				List<Object> events = _eventsMap.get(eventType);

				if (events == null) {
					events = new ArrayList<Object>();

					_eventsMap.put(eventType, events);
				}

				events.add(obj);
			}
		}

		List<Element> modelListenerEls = root.elements("model-listener");

		for (Element modelListenerEl : modelListenerEls) {
			String modelListenerClass = modelListenerEl.elementText(
				"model-listener-class");
			String modelName = modelListenerEl.elementText("model-name");

			ModelListener modelListener = initModelListener(
				modelListenerClass, modelName, portletClassLoader);

			if (modelListener != null) {
				List<ModelListener> modelListeners = _modelListenersMap.get(
					modelName);

				if (modelListeners == null) {
					modelListeners = new ArrayList<ModelListener>();

					_modelListenersMap.put(modelName, modelListeners);
				}

				modelListeners.add(modelListener);
			}
		}

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

					initPortalProperties(portalProperties);
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + servletContextName + " registered successfully");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext ctx = event.getServletContext();

		String servletContextName = ctx.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		for (Map.Entry<String, List<Object>> entry : _eventsMap.entrySet()) {
			String eventType = entry.getKey();
			List<Object> events = entry.getValue();

			for (Object obj : events) {
				EventsProcessor.unregisterEvent(eventType, obj);
			}
		}

		for (Map.Entry<String, List<ModelListener>> entry :
				_modelListenersMap.entrySet()) {

			String modelName = entry.getKey();
			List<ModelListener> modelListeners = entry.getValue();

			BasePersistence persistence = getPersistence(modelName);

			for (ModelListener modelListener : modelListeners) {
				persistence.unregisterListener(modelListener);
			}
		}

		Properties portalProperties = _portalPropertiesMap.get(
			servletContextName);

		if (portalProperties != null) {
			destroyPortalProperties(portalProperties);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Hook for " + servletContextName +
					" unregistered successfully");
		}
	}

	protected BasePersistence getPersistence(String modelName) {
		int pos = modelName.lastIndexOf(StringPool.PERIOD);

		String entityName = modelName.substring(pos + 1);

		pos = modelName.lastIndexOf(".model.");

		String packagePath = modelName.substring(0, pos);

		return (BasePersistence)BeanLocatorUtil.locate(
			packagePath + ".service.persistence." + entityName +
				"Persistence.impl");
	}

	protected Object initEvent(
			String eventClass, String eventType, ClassLoader portletClassLoader)
		throws Exception {

		if (eventType.equals(PropsKeys.APPLICATION_STARTUP_EVENTS)) {
			SimpleAction simpleAction = new InvokerSimpleAction(
				(SimpleAction)portletClassLoader.loadClass(
					eventClass).newInstance());

			long[] companyIds = PortalInstances.getCompanyIds();

			for (long companyId : companyIds) {
				simpleAction.run(new String[] {String.valueOf(companyId)});
			}

			return null;
		}

		if (eventType.equals(PropsKeys.LOGIN_EVENTS_POST) ||
			eventType.equals(PropsKeys.LOGIN_EVENTS_PRE) ||
			eventType.equals(PropsKeys.LOGOUT_EVENTS_POST) ||
			eventType.equals(PropsKeys.LOGOUT_EVENTS_PRE)) {

			Action action = (Action)portletClassLoader.loadClass(
				eventClass).newInstance();

			EventsProcessor.registerEvent(eventType, action);

			return action;
		}

		return null;
	}

	protected ModelListener initModelListener(
			String modelListenerClass, String modelName,
			ClassLoader portletClassLoader)
		throws Exception {

		ModelListener modelListener =
			(ModelListener)portletClassLoader.loadClass(
				modelListenerClass).newInstance();

		BasePersistence persistence = getPersistence(modelName);

		persistence.registerListener(modelListener);

		return modelListener;
	}

	protected void initPortalProperties(Properties portalProperties)
		throws Exception {

		PropsUtil.addProperties(portalProperties);

		if (_log.isDebugEnabled() &&
			portalProperties.containsKey(PropsKeys.LOCALES)) {

			_log.debug(
				"Portlet locales " +
					portalProperties.getProperty(PropsKeys.LOCALES));
			_log.debug(
				"Merged locales " + PropsUtil.get(PropsKeys.LOCALES));
			_log.debug(
				"Merged locales array length " +
					PropsUtil.getArray(PropsKeys.LOCALES).length);
		}

		resetPortalProperties(portalProperties);
	}

	protected void resetPortalProperties(Properties portalProperties)
		throws Exception {

		for (String fieldName : _PROPS_KEYS_BOOLEAN) {
			String key = StringUtil.replace(
				fieldName.toLowerCase(), StringPool.UNDERLINE,
				StringPool.PERIOD);

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

		for (String fieldName : _PROPS_KEYS_INTEGER) {
			String key = StringUtil.replace(
				fieldName.toLowerCase(), StringPool.UNDERLINE,
				StringPool.PERIOD);

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

		for (String fieldName : _PROPS_KEYS_LONG) {
			String key = StringUtil.replace(
				fieldName.toLowerCase(), StringPool.UNDERLINE,
				StringPool.PERIOD);

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

		for (String fieldName : _PROPS_KEYS_STRING) {
			String key = StringUtil.replace(
				fieldName.toLowerCase(), StringPool.UNDERLINE,
				StringPool.PERIOD);

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

		if (containsKey(portalProperties, PropsKeys.LOCALES)) {
			PropsValues.LOCALES = PropsUtil.getArray(PropsKeys.LOCALES);

			LanguageUtil.init();
		}

		LayoutCacheUtil.clearCache();
	}

	private static final String[] _PROPS_KEYS_BOOLEAN = new String[] {
		"JAVASCRIPT_FAST_LOAD",
		"LAYOUT_TEMPLATE_CACHE_ENABLED",
		"LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE",
		"LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED",
		"LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE",
		"LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE",
		"LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED",
		"LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE",
		"MY_PLACES_SHOW_COMMUNITY_PRIVATE_SITES_WITH_NO_LAYOUTS",
		"MY_PLACES_SHOW_COMMUNITY_PUBLIC_SITES_WITH_NO_LAYOUTS",
		"MY_PLACES_SHOW_ORGANIZATION_PRIVATE_SITES_WITH_NO_LAYOUTS",
		"MY_PLACES_SHOW_ORGANIZATION_PUBLIC_SITES_WITH_NO_LAYOUTS",
		"MY_PLACES_SHOW_USER_PRIVATE_SITES_WITH_NO_LAYOUTS",
		"MY_PLACES_SHOW_USER_PUBLIC_SITES_WITH_NO_LAYOUTS",
		"ORGANIZATIONS_COUNTRY_REQUIRED",
		"THEME_CSS_FAST_LOAD"
	};

	private static final String[] _PROPS_KEYS_INTEGER = new String[] {
	};

	private static final String[] _PROPS_KEYS_LONG = new String[] {
	};

	private static final String[] _PROPS_KEYS_STRING = new String[] {
		"PASSWORDS_PASSWORDPOLICYTOOLKIT_GENERATOR",
		"PASSWORDS_PASSWORDPOLICYTOOLKIT_STATIC"
	};

	private static Log _log = LogFactory.getLog(HookHotDeployListener.class);

	private Map<String, List<Object>> _eventsMap =
		new HashMap<String, List<Object>>();
	private Map<String, List<ModelListener>> _modelListenersMap =
		new HashMap<String, List<ModelListener>>();
	private Map<String, Properties> _portalPropertiesMap =
		new HashMap<String, Properties>();

}