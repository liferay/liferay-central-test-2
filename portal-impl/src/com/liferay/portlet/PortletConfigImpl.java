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

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletInfo;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.util.PortalInstances;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import javax.xml.namespace.QName;

/**
 * <a href="PortletConfigImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletConfigImpl implements PortletConfig {

	public PortletConfigImpl(Portlet portlet, PortletContext portletCtx) {
		_portletApp = portlet.getPortletApp();
		_portlet = portlet;
		_portletName = portlet.getRootPortletId();

		int pos = _portletName.indexOf(PortletImpl.WAR_SEPARATOR);

		if (pos != -1) {
			_portletName = _portletName.substring(0, pos);
		}

		_portletCtx = portletCtx;
		_bundlePool = new HashMap<String, ResourceBundle>();
	}

	public Map<String, String[]> getContainerRuntimeOptions() {
		return null;
	}

	public String getDefaultNamespace() {
		return _portletApp.getDefaultNamespace();
	}

	public String getInitParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _portlet.getInitParams().get(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_portlet.getInitParams().keySet());
	}

	public PortletContext getPortletContext() {
		return _portletCtx;
	}

	public String getPortletId() {
		return _portlet.getPortletId();
	}

	public String getPortletName() {
		return _portletName;
	}

	public Enumeration<QName> getProcessingEventQNames() {
		return Collections.enumeration(_portlet.getProcessingEvents());
	}

	public Enumeration<String> getPublicRenderParameterNames() {
		List<String> publicRenderParameterNames = new ArrayList<String>();

		for (PublicRenderParameter publicRenderParameter :
				_portlet.getPublicRenderParameters()) {

			publicRenderParameterNames.add(
				publicRenderParameter.getIdentifier());
		}

		return Collections.enumeration(publicRenderParameterNames);
	}

	public Enumeration<QName> getPublishingEventQNames() {
		return Collections.enumeration(_portlet.getPublishingEvents());
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		String resourceBundleClassName = _portlet.getResourceBundle();

		if (resourceBundleClassName == null) {
			String poolId = _portlet.getPortletId();

			ResourceBundle bundle = _bundlePool.get(poolId);

			if (bundle == null) {
				StringMaker sm = new StringMaker();

				try {
					PortletInfo portletInfo = _portlet.getPortletInfo();

					sm.append(JavaConstants.JAVAX_PORTLET_TITLE);
					sm.append("=");
					sm.append(portletInfo.getTitle());
					sm.append("\n");

					sm.append(JavaConstants.JAVAX_PORTLET_SHORT_TITLE);
					sm.append("=");
					sm.append(portletInfo.getShortTitle());
					sm.append("\n");

					sm.append(JavaConstants.JAVAX_PORTLET_KEYWORDS);
					sm.append("=");
					sm.append(portletInfo.getKeywords());
					sm.append("\n");

					bundle = new PropertyResourceBundle(
						new ByteArrayInputStream(sm.toString().getBytes()));
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				_bundlePool.put(poolId, bundle);
			}

			return bundle;
		}
		else {
			String poolId = _portlet.getPortletId() + "." + locale.toString();

			ResourceBundle bundle = _bundlePool.get(poolId);

			if (bundle == null) {
				if (!_portletApp.isWARFile() &&
					resourceBundleClassName.equals(
						StrutsResourceBundle.class.getName())) {

					long companyId = PortalInstances.getDefaultCompanyId();

					bundle = StrutsResourceBundle.getBundle(
						_portletName, companyId, locale);
				}
				else {
					PortletBag portletBag = PortletBagPool.get(
						_portlet.getRootPortletId());

					bundle = portletBag.getResourceBundle(locale);
				}

				bundle = new PortletResourceBundle(
					bundle, _portlet.getPortletInfo());

				_bundlePool.put(poolId, bundle);
			}

			return bundle;
		}
	}

	public Enumeration<Locale> getSupportedLocales() {
		List<Locale> supportedLocales = new ArrayList<Locale>();

		for (String languageId : _portlet.getSupportedLocales()) {
			supportedLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return Collections.enumeration(supportedLocales);
	}

	public boolean isWARFile() {
		return _portletApp.isWARFile();
	}

	private PortletApp _portletApp;
	private Portlet _portlet;
	private String _portletName;
	private PortletContext _portletCtx;
	private Map<String, ResourceBundle> _bundlePool;

}