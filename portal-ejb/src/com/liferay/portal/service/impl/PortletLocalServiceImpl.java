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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchPortletException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.base.PortletLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.PortletPK;
import com.liferay.portal.service.persistence.PortletUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.SimpleCachePool;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLSafeReader;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PreferencesValidator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * <a href="PortletLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Auge
 *
 */
public class PortletLocalServiceImpl extends PortletLocalServiceBaseImpl {

	public PortletCategory getEARDisplay(String xml) throws SystemException {
		try {
			return _readLiferayDisplayXML(xml);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public PortletCategory getWARDisplay(String servletContextName, String xml)
		throws SystemException {

		try {
			return _readLiferayDisplayXML(servletContextName, xml);
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Map getFriendlyURLMappers() {
		return _getFriendlyURLMappers();
	}

	public Portlet getPortletById(String companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletName(portletId);

		if (companyId.equals(UserImpl.DEFAULT)) {
			throw new SystemException(
				"Company id should never be " + UserImpl.DEFAULT);
		}

		Portlet portlet = null;

		Map companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletImpl.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = (Portlet)companyPortletsPool.get(portletId);
		}
		else {
			portlet = (Portlet)companyPortletsPool.get(rootPortletId);

			if (portlet != null) {
				portlet = portlet.getClonedInstance(portletId);
			}
		}

		if ((portlet == null) &&
			(!portletId.equals(PortletKeys.LIFERAY_PORTAL))) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Portlet not found for " + companyId + " " + portletId);
			}
		}

		return portlet;
	}

	public Portlet getPortletByStrutsPath(String companyId, String strutsPath)
		throws SystemException {

		return getPortletById(companyId, _getPortletId(strutsPath));
	}

	public List getPortlets(String companyId) throws SystemException {
		return getPortlets(companyId, true, true);
	}

	public List getPortlets(
			String companyId, boolean showSystem, boolean showPortal)
		throws SystemException {

		Map portletsPool = _getPortletsPool(companyId);

		List portlets = ListUtil.fromCollection(portletsPool.values());

		if (!showSystem || !showPortal) {
			Iterator itr = portlets.iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				if (showPortal &&
					portlet.getPortletId().equals(PortletKeys.PORTAL)) {

				}
				else if (!showPortal &&
						 portlet.getPortletId().equals(PortletKeys.PORTAL)) {

					itr.remove();
				}
				else if (!showSystem && portlet.isSystem()) {
					itr.remove();
				}
			}
		}

		return portlets;
	}

	public boolean hasPortlet(String companyId, String portletId)
		throws SystemException {

		portletId = PortalUtil.getJsSafePortletName(portletId);

		if (companyId.equals(UserImpl.DEFAULT)) {
			throw new SystemException();
		}

		Portlet portlet = null;

		Map companyPortletsPool = _getPortletsPool(companyId);

		String rootPortletId = PortletImpl.getRootPortletId(portletId);

		if (portletId.equals(rootPortletId)) {
			portlet = (Portlet)companyPortletsPool.get(portletId);
		}
		else {
			portlet = (Portlet)companyPortletsPool.get(rootPortletId);
		}

		if (portlet == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void initEAR(String[] xmls, PluginPackage pluginPackage) {
		String scpId = PortletServiceImpl.class.getName() + "." + _SHARED_KEY;

		Map portletsPool = (Map)SimpleCachePool.get(scpId);

		if (portletsPool == null) {
			portletsPool = CollectionFactory.getSyncHashMap();

			SimpleCachePool.put(scpId, portletsPool);
		}

		try {
			List servletURLPatterns = _readWebXML(xmls[4]);

			Set portletIds = _readPortletXML(
				xmls[0], portletsPool, servletURLPatterns, pluginPackage);

			portletIds.addAll(_readPortletXML(
				xmls[1], portletsPool, servletURLPatterns, pluginPackage));

			Set liferayPortletIds =
				_readLiferayPortletXML(xmls[2], portletsPool);

			liferayPortletIds.addAll(
				_readLiferayPortletXML(xmls[3], portletsPool));

			// Check for missing entries in liferay-portlet.xml

			Iterator itr = portletIds.iterator();

			while (itr.hasNext()) {
				String portletId = (String)itr.next();

				if (!liferayPortletIds.contains(portletId)) {
					_log.error(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			itr = liferayPortletIds.iterator();

			while (itr.hasNext()) {
				String portletId = (String)itr.next();

				if (!portletIds.contains(portletId)) {
					_log.error(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Remove portlets that should not be included

			itr = portletsPool.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				Portlet portletModel = (Portlet)entry.getValue();

				if (!portletModel.getPortletId().equals(PortletKeys.ADMIN) &&
					!portletModel.getPortletId().equals(
						PortletKeys.MY_ACCOUNT) &&
					!portletModel.isInclude()) {

					itr.remove();
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public List initWAR(
		String servletContextName, String[] xmls, PluginPackage pluginPackage) {

		List portlets = new ArrayList();

		String scpId = PortletServiceImpl.class.getName() + "." + _SHARED_KEY;

		Map portletsPool = (Map)SimpleCachePool.get(scpId);

		if (portletsPool == null) {
			portletsPool = CollectionFactory.getSyncHashMap();

			SimpleCachePool.put(scpId, portletsPool);
		}

		try {
			List servletPatterns = _readWebXML(xmls[2]);

			Set portletIds = _readPortletXML(
				servletContextName, xmls[0], portletsPool, servletPatterns,
				pluginPackage);

			Set liferayPortletIds = _readLiferayPortletXML(
				servletContextName, xmls[1], portletsPool);

			// Check for missing entries in liferay-portlet.xml

			Iterator itr = portletIds.iterator();

			while (itr.hasNext()) {
				String portletId = (String)itr.next();

				if (!liferayPortletIds.contains(portletId)) {
					_log.error(
						"Portlet with the name " + portletId +
							" is described in portlet.xml but does not " +
								"have a matching entry in liferay-portlet.xml");
				}
			}

			// Check for missing entries in portlet.xml

			itr = liferayPortletIds.iterator();

			while (itr.hasNext()) {
				String portletId = (String)itr.next();

				if (!portletIds.contains(portletId)) {
					_log.error(
						"Portlet with the name " + portletId +
							" is described in liferay-portlet.xml but does " +
								"not have a matching entry in portlet.xml");
				}
			}

			// Return the new portlets

			itr = portletIds.iterator();

			while (itr.hasNext()) {
				String portletId = (String)itr.next();

				Portlet portlet = (Portlet)_getPortletsPool().get(portletId);

				portlets.add(portlet);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		// Refresh security path to portlet id mapping for all portlets

		SimpleCachePool.remove(PortletServiceImpl.class.getName());

		// Refresh company portlets

		SimpleCachePool.remove(
			PortletServiceImpl.class.getName() + ".companyPortletsPool");

		return portlets;
	}

	public Portlet updatePortlet(
			String companyId, String portletId, String roles, boolean active)
		throws PortalException, SystemException {

		portletId = PortalUtil.getJsSafePortletName(portletId);

		// Primary key should be reordered to companyId, portletId FIX ME

		PortletPK pk = new PortletPK(portletId, companyId);

		Portlet portlet = null;

		try {
			portlet = PortletUtil.findByPrimaryKey(pk);
		}
		catch (NoSuchPortletException nspe) {
			portlet = PortletUtil.create(pk);
		}

		portlet.setRoles(roles);
		portlet.setActive(active);

		PortletUtil.update(portlet);

		portlet = getPortletById(companyId, portletId);

		portlet.setRoles(roles);
		portlet.setActive(active);

		return portlet;
	}

	private Map _getFriendlyURLMappers() {
		String scpId =
			PortletServiceImpl.class.getName() + ".friendlyURLMappers";

		Map friendlyURLMappers = (Map)SimpleCachePool.get(scpId);

		if (friendlyURLMappers == null) {
			friendlyURLMappers = CollectionFactory.getHashMap();

			SimpleCachePool.put(scpId, friendlyURLMappers);
		}

		return friendlyURLMappers;
	}

	private String _getPortletId(String securityPath) throws SystemException {
		String scpId = PortletServiceImpl.class.getName();

		Map portletIds = (Map)SimpleCachePool.get(scpId);

		if (portletIds == null) {
			portletIds = CollectionFactory.getHashMap();

			Iterator itr = _getPortletsPool().values().iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				portletIds.put(portlet.getStrutsPath(), portlet.getPortletId());
			}

			SimpleCachePool.put(scpId, portletIds);
		}

		String portletId = (String)portletIds.get(securityPath);

		if (Validator.isNull(portletId)) {
			_log.error(
				"Struts path " + securityPath + " is not mapped to a portlet " +
					"in liferay-portlet.xml");
		}

		return portletId;
	}

	private Map _getPortletsPool() {
		String scpId = PortletServiceImpl.class.getName() + "." + _SHARED_KEY;

		return (Map)SimpleCachePool.get(scpId);
	}

	private Map _getPortletsPool(String companyId) throws SystemException {
		String scpId =
			PortletServiceImpl.class.getName() + ".companyPortletsPool";

		Map companyPortletsPool = (Map)SimpleCachePool.get(scpId);

		if (companyPortletsPool == null) {
			companyPortletsPool = CollectionFactory.getSyncHashMap();

			SimpleCachePool.put(scpId, companyPortletsPool);
		}

		Map portletsPool = (Map)companyPortletsPool.get(companyId);

		if (portletsPool == null) {
			portletsPool = CollectionFactory.getSyncHashMap();

			Map parentPortletsPool = _getPortletsPool();

			if (parentPortletsPool == null) {

				// The Upgrade scripts sometimes try to access portlet
				// preferences before the portal's been initialized. Return an
				// empty pool.

				return portletsPool;
			}

			Iterator itr = parentPortletsPool.values().iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)((Portlet)itr.next()).clone();

				portlet.setCompanyId(companyId);

				portletsPool.put(portlet.getPortletId(), portlet);
			}

			itr = PortletUtil.findByCompanyId(companyId).iterator();

			while (itr.hasNext()) {
				Portlet portlet = (Portlet)itr.next();

				Portlet portletModel =
					(Portlet)portletsPool.get(portlet.getPortletId());

				// Portlet may be null if it exists in the database but its
				// portlet WAR is not yet loaded

				if (portletModel != null) {
					portletModel.setPluginPackage(portlet.getPluginPackage());
					portletModel.setDefaultPluginSetting(
						portlet.getDefaultPluginSetting());
					portletModel.setRoles(portlet.getRoles());
					portletModel.setActive(portlet.getActive());
				}
			}

			companyPortletsPool.put(companyId, portletsPool);
		}

		return portletsPool;
	}

	private Set _readPortletXML(
			String xml, Map portletsPool, List servletURLPatterns,
			PluginPackage pluginPackage)
		throws DocumentException, IOException {

		return _readPortletXML(
			null, xml, portletsPool, servletURLPatterns, pluginPackage);
	}

	private Set _readPortletXML(
			String servletContextName, String xml, Map portletsPool,
			List servletURLPatterns, PluginPackage pluginPackage)
		throws DocumentException, IOException {

		Set portletIds = new HashSet();

		if (xml == null) {
			return portletIds;
		}

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Set userAttributes = new HashSet();

		Iterator itr1 = root.elements("user-attribute").iterator();

		while (itr1.hasNext()) {
			Element userAttribute = (Element)itr1.next();

			String name = userAttribute.elementText("name");

			userAttributes.add(name);
		}

		itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = (Element)itr1.next();

			String portletId = portlet.elementText("portlet-name");

			if (servletContextName != null) {
				portletId =
					portletId + PortletImpl.WAR_SEPARATOR + servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletName(portletId);

			if (_log.isDebugEnabled()) {
				_log.debug("Reading portlet " + portletId);
			}

			portletIds.add(portletId);

			Portlet portletModel = (Portlet)portletsPool.get(portletId);

			if (portletModel == null) {
				portletModel = new PortletImpl(
					new PortletPK(portletId, _SHARED_KEY));

				portletsPool.put(portletId, portletModel);
			}

			portletModel.setPluginPackage(pluginPackage);

			if (servletContextName != null) {
				portletModel.setServletContextName(servletContextName);
			}

			if (servletURLPatterns != null) {
				portletModel.setServletURLPatterns(servletURLPatterns);
			}

			portletModel.setDisplayName(GetterUtil.getString(
				portlet.elementText("display-name"),
				portletModel.getDisplayName()));
			portletModel.setPortletClass(GetterUtil.getString(
				portlet.elementText("portlet-class")));

			Iterator itr2 = portlet.elements("init-param").iterator();

			while (itr2.hasNext()) {
				Element initParam = (Element)itr2.next();

				portletModel.getInitParams().put(
					initParam.elementText("name"),
					initParam.elementText("value"));
			}

			Element expirationCache = portlet.element("expiration-cache");

			if (expirationCache != null) {
				portletModel.setExpCache(new Integer(GetterUtil.getInteger(
					expirationCache.getText())));
			}

			itr2 = portlet.elements("supports").iterator();

			while (itr2.hasNext()) {
				Element supports = (Element)itr2.next();

				String mimeType = supports.elementText("mime-type");

				Iterator itr3 = supports.elements("portlet-mode").iterator();

				while (itr3.hasNext()) {
					Element portletMode = (Element)itr3.next();

					Set mimeTypeModes =
						(Set)portletModel.getPortletModes().get(mimeType);

					if (mimeTypeModes == null) {
						mimeTypeModes = new HashSet();

						portletModel.getPortletModes().put(
							mimeType, mimeTypeModes);
					}

					mimeTypeModes.add(portletMode.getTextTrim().toLowerCase());
				}
			}

			Set supportedLocales = portletModel.getSupportedLocales();

			supportedLocales.add(Locale.getDefault().getLanguage());

			itr2 = portlet.elements("supported-locale").iterator();

			while (itr2.hasNext()) {
				Element supportedLocaleEl = (Element)itr2.next();

				String supportedLocale = supportedLocaleEl.getText();

				supportedLocales.add(supportedLocale);
			}

			portletModel.setResourceBundle(
				portlet.elementText("resource-bundle"));

			Element portletInfo = portlet.element("portlet-info");

			String portletInfoTitle = null;
			String portletInfoShortTitle = null;
			String portletInfoKeyWords = null;

			if (portletInfo != null) {
				portletInfoTitle = portletInfo.elementText("title");
				portletInfoShortTitle = portletInfo.elementText("short-title");
				portletInfoKeyWords = portletInfo.elementText("keywords");
			}

			portletModel.setPortletInfo(new PortletInfo(
				portletInfoTitle, portletInfoShortTitle, portletInfoKeyWords));

			Element portletPreferences = portlet.element("portlet-preferences");

			String defaultPreferences = null;
			String prefsValidator = null;

			if (portletPreferences != null) {
				Element prefsValidatorEl =
					portletPreferences.element("preferences-validator");

				if (prefsValidatorEl != null) {
					prefsValidator = prefsValidatorEl.getText();

					portletPreferences.remove(prefsValidatorEl);
				}

				StringWriter sw = new StringWriter();

				XMLWriter writer = new XMLWriter(
					sw, OutputFormat.createCompactFormat());

				writer.write(portletPreferences);

				defaultPreferences = sw.toString();
			}

			portletModel.setDefaultPreferences(defaultPreferences);
			portletModel.setPreferencesValidator(prefsValidator);

			if (!portletModel.isWARFile() &&
				Validator.isNotNull(prefsValidator) &&
				GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.PREFERENCE_VALIDATE_ON_STARTUP))) {

				try {
					PreferencesValidator prefsValidatorObj =
						PortalUtil.getPreferencesValidator(portletModel);

					prefsValidatorObj.validate(
						PortletPreferencesSerializer.fromDefaultXML(
							defaultPreferences));
				}
				catch (Exception e) {
					_log.warn(
						"Portlet with the name " + portletId +
							" does not have valid default preferences");
				}
			}

			Set unlikedRoles = portletModel.getUnlinkedRoles();

			itr2 = portlet.elements("security-role-ref").iterator();

			while (itr2.hasNext()) {
				Element role = (Element)itr2.next();

				unlikedRoles.add(role.elementText("role-name"));
			}

			portletModel.getUserAttributes().addAll(userAttributes);
		}

		return portletIds;
	}

	private void _readLiferayDisplay(
		String servletContextName, Element el, PortletCategory portletCategory,
		Set portletIds) {

		Iterator itr1 = el.elements("category").iterator();

		while (itr1.hasNext()) {
			Element category = (Element)itr1.next();

			String name = category.attributeValue("name");

			PortletCategory curPortletCategory = new PortletCategory(name);

			portletCategory.addCategory(curPortletCategory);

			Set curPortletIds = curPortletCategory.getPortlets();

			Iterator itr2 = category.elements("portlet").iterator();

			while (itr2.hasNext()) {
				Element portlet = (Element)itr2.next();

				String portletId = portlet.attributeValue("id");

				if (servletContextName != null) {
					portletId =
						portletId + PortletImpl.WAR_SEPARATOR +
							servletContextName;
				}

				portletId = PortalUtil.getJsSafePortletName(portletId);

				portletIds.add(portletId);
				curPortletIds.add(portletId);
			}

			_readLiferayDisplay(
				servletContextName, category, curPortletCategory, portletIds);
		}
	}

	private PortletCategory _readLiferayDisplayXML(String xml)
		throws DocumentException, IOException {

		return _readLiferayDisplayXML(null, xml);
	}

	private PortletCategory _readLiferayDisplayXML(
			String servletContextName, String xml)
		throws DocumentException, IOException {

		PortletCategory portletCategory = new PortletCategory();

		if (xml == null) {
			xml = ContentUtil.get(
				"com/liferay/portal/deploy/dependencies/liferay-display.xml");
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Set portletIds = new HashSet();

		_readLiferayDisplay(
			servletContextName, root, portletCategory, portletIds);

		// Portlets that do not belong to any categories should default to the
		// Undefined category

		Set undefinedPortletIds = new HashSet();

		Iterator itr = _getPortletsPool().values().iterator();

		while (itr.hasNext()) {
			Portlet portlet = (Portlet)itr.next();

			String portletId = portlet.getPortletId();

			if ((servletContextName != null) && (portlet.isWARFile()) &&
				(portletId.endsWith(
					PortletImpl.WAR_SEPARATOR +
						PortalUtil.getJsSafePortletName(servletContextName)) &&
				(!portletIds.contains(portletId)))) {

				undefinedPortletIds.add(portletId);
			}
			else if ((servletContextName == null) && (!portlet.isWARFile()) &&
					 (portletId.indexOf(PortletImpl.WAR_SEPARATOR) == -1) &&
					 (!portletIds.contains(portletId))) {

				undefinedPortletIds.add(portletId);
			}
		}

		if (undefinedPortletIds.size() > 0) {
			PortletCategory undefinedCategory =
				new PortletCategory("category.undefined");

			portletCategory.addCategory(undefinedCategory);

			undefinedCategory.getPortlets().addAll(undefinedPortletIds);
		}

		return portletCategory;
	}

	private Set _readLiferayPortletXML(String xml, Map portletsPool)
		throws DocumentException, IOException {

		return _readLiferayPortletXML(null, xml, portletsPool);
	}

	private Set _readLiferayPortletXML(
			String servletContextName, String xml, Map portletsPool)
		throws DocumentException, IOException {

		Set liferayPortletIds = new HashSet();

		if (xml == null) {
			return liferayPortletIds;
		}

		SAXReader reader = SAXReaderFactory.getInstance();

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Map roleMappers = new HashMap();

		Iterator itr1 = root.elements("role-mapper").iterator();

		while (itr1.hasNext()) {
			Element roleMapper = (Element)itr1.next();

			String roleName = roleMapper.elementText("role-name");
			String roleLink = roleMapper.elementText("role-link");

			roleMappers.put(roleName, roleLink);
		}

		Map customUserAttributes = new HashMap();

		itr1 = root.elements("custom-user-attribute").iterator();

		while (itr1.hasNext()) {
			Element customUserAttribute = (Element)itr1.next();

			String customClass = customUserAttribute.elementText(
				"custom-class");

			Iterator itr2 = customUserAttribute.elements("name").iterator();

			while (itr2.hasNext()) {
				Element nameEl = (Element)itr2.next();

				String name = nameEl.getText();

				customUserAttributes.put(name, customClass);
			}
		}

		Map friendlyURLMappers = _getFriendlyURLMappers();

		itr1 = root.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portlet = (Element)itr1.next();

			String portletId = portlet.elementText("portlet-name");

			if (servletContextName != null) {
				portletId =
					portletId + PortletImpl.WAR_SEPARATOR + servletContextName;
			}

			portletId = PortalUtil.getJsSafePortletName(portletId);

			if (_log.isDebugEnabled()) {
				_log.debug("Reading portlet extension " + portletId);
			}

			liferayPortletIds.add(portletId);

			Portlet portletModel = (Portlet)portletsPool.get(portletId);

			if (portletModel != null) {
				portletModel.setStrutsPath(GetterUtil.getString(
					portlet.elementText("struts-path"),
					portletModel.getStrutsPath()));
				portletModel.setConfigurationPath(GetterUtil.getString(
					portlet.elementText("configuration-path"),
					portletModel.getConfigurationPath()));
				portletModel.setIndexerClass(GetterUtil.getString(
					portlet.elementText("indexer-class"),
					portletModel.getIndexerClass()));
				portletModel.setOpenSearchClass(GetterUtil.getString(
					portlet.elementText("open-search-class"),
					portletModel.getOpenSearchClass()));
				portletModel.setSchedulerClass(GetterUtil.getString(
					portlet.elementText("scheduler-class"),
					portletModel.getSchedulerClass()));
				portletModel.setPortletURLClass(GetterUtil.getString(
					portlet.elementText("portlet-url-class"),
					portletModel.getPortletURLClass()));

				portletModel.setFriendlyURLMapperClass(GetterUtil.getString(
					portlet.elementText("friendly-url-mapper-class"),
					portletModel.getFriendlyURLMapperClass()));

				if (Validator.isNull(
						portletModel.getFriendlyURLMapperClass())) {

					friendlyURLMappers.remove(portletId);
				}
				else {
					friendlyURLMappers.put(
						portletId, portletModel.getFriendlyURLMapperClass());
				}

				portletModel.setURLEncoderClass(GetterUtil.getString(
					portlet.elementText("url-encoder-class"),
					portletModel.getURLEncoderClass()));
				portletModel.setPortletDataHandlerClass(GetterUtil.getString(
					portlet.elementText("portlet-data-handler-class"),
					portletModel.getPortletDataHandlerClass()));
				portletModel.setSmtpMessageListenerClass(GetterUtil.getString(
					portlet.elementText("smtp-message-listener-class"),
					portletModel.getSmtpMessageListenerClass()));
				portletModel.setPreferencesCompanyWide(GetterUtil.getBoolean(
					portlet.elementText("preferences-company-wide"),
					portletModel.isPreferencesCompanyWide()));
				portletModel.setPreferencesUniquePerLayout(
					GetterUtil.getBoolean(
						portlet.elementText("preferences-unique-per-layout"),
						portletModel.isPreferencesUniquePerLayout()));
				portletModel.setPreferencesOwnedByGroup(GetterUtil.getBoolean(
					portlet.elementText("preferences-owned-by-group"),
					portletModel.isPreferencesOwnedByGroup()));
				portletModel.setUseDefaultTemplate(GetterUtil.getBoolean(
					portlet.elementText("use-default-template"),
					portletModel.isUseDefaultTemplate()));
				portletModel.setShowPortletAccessDenied(GetterUtil.getBoolean(
					portlet.elementText("show-portlet-access-denied"),
					portletModel.isShowPortletAccessDenied()));
				portletModel.setShowPortletInactive(GetterUtil.getBoolean(
					portlet.elementText("show-portlet-inactive"),
					portletModel.isShowPortletInactive()));
				portletModel.setActionURLRedirect(GetterUtil.getBoolean(
					portlet.elementText("action-url-redirect"),
					portletModel.isActionURLRedirect()));
				portletModel.setRestoreCurrentView(GetterUtil.getBoolean(
					portlet.elementText("restore-current-view"),
					portletModel.isRestoreCurrentView()));
				portletModel.setMaximizeEdit(GetterUtil.getBoolean(
					portlet.elementText("maximize-edit"),
					portletModel.isMaximizeEdit()));
				portletModel.setMaximizeHelp(GetterUtil.getBoolean(
					portlet.elementText("maximize-help"),
					portletModel.isMaximizeHelp()));
				portletModel.setPopUpPrint(GetterUtil.getBoolean(
					portlet.elementText("pop-up-print"),
					portletModel.isPopUpPrint()));
				portletModel.setLayoutCacheable(GetterUtil.getBoolean(
					portlet.elementText("layout-cacheable"),
					portletModel.isLayoutCacheable()));
				portletModel.setInstanceable(GetterUtil.getBoolean(
					portlet.elementText("instanceable"),
					portletModel.isInstanceable()));
				portletModel.setPrivateRequestAttributes(GetterUtil.getBoolean(
					portlet.elementText("private-request-attributes"),
					portletModel.isPrivateRequestAttributes()));
				portletModel.setPrivateSessionAttributes(GetterUtil.getBoolean(
					portlet.elementText("private-session-attributes"),
					portletModel.isPrivateSessionAttributes()));
				portletModel.setRenderWeight(GetterUtil.getInteger(
					portlet.elementText("render-weight"),
					portletModel.getRenderWeight()));
				portletModel.setAjaxable(GetterUtil.getBoolean(
					portlet.elementText("ajaxable"),
					portletModel.isAjaxable()));

				List headerCssList = portletModel.getHeaderCss();

				Iterator itr2 = portlet.elements("header-css").iterator();

				while (itr2.hasNext()) {
					Element headerCssEl = (Element)itr2.next();

					headerCssList.add(headerCssEl.getText());
				}

				List headerJavaScriptList = portletModel.getHeaderJavaScript();

				itr2 = portlet.elements("header-javascript").iterator();

				while (itr2.hasNext()) {
					Element headerJavaScriptEl = (Element)itr2.next();

					headerJavaScriptList.add(headerJavaScriptEl.getText());
				}

				portletModel.setAddDefaultResource(GetterUtil.getBoolean(
					portlet.elementText("add-default-resource"),
					portletModel.isAddDefaultResource()));
				portletModel.setSystem(GetterUtil.getBoolean(
					portlet.elementText("system"),
					portletModel.isSystem()));
				portletModel.setActive(GetterUtil.getBoolean(
					portlet.elementText("active"),
					portletModel.isActive()));
				portletModel.setInclude(GetterUtil.getBoolean(
					portlet.elementText("include"),
					portletModel.isInclude()));

				if (!portletModel.isAjaxable() &&
					(portletModel.getRenderWeight() < 1)) {

					portletModel.setRenderWeight(1);
				}

				portletModel.getRoleMappers().putAll(roleMappers);
				portletModel.linkRoles();

				portletModel.getCustomUserAttributes().putAll(
					customUserAttributes);
			}
		}

		return liferayPortletIds;
	}

	private List _readWebXML(String xml) throws DocumentException, IOException {
		List servletURLPatterns = new ArrayList();

		if (xml == null) {
			return servletURLPatterns;
		}

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		Iterator itr = root.elements("servlet-mapping").iterator();

		while (itr.hasNext()) {
			Element servletMapping = (Element)itr.next();

			String urlPattern = servletMapping.elementText("url-pattern");

			servletURLPatterns.add(urlPattern);
		}

		return servletURLPatterns;

	}

	private static final String _SHARED_KEY = "SHARED_KEY";

	private static Log _log = LogFactory.getLog(PortletLocalServiceImpl.class);

}