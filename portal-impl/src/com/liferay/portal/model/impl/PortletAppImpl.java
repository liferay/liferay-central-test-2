/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletFilter;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.SpriteImage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.XMLConstants;

/**
 * <a href="PortletAppImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletAppImpl implements PortletApp {

	public PortletAppImpl(String servletContextName) {
		_servletContextName = servletContextName;

		if (Validator.isNotNull(_servletContextName)) {
			_warFile = true;
		}
		else {
			_warFile = false;
		}
	}

	public void addEventDefinition(EventDefinition eventDefinition) {
		_eventDefinitions.add(eventDefinition);
	}

	public void addPortletFilter(PortletFilter portletFilter) {
		_portletFilters.add(portletFilter);
		_portletFiltersByFilterName.put(
			portletFilter.getFilterName(), portletFilter);
	}

	public void addPortletURLListener(PortletURLListener portletURLListener) {
		_portletURLListeners.add(portletURLListener);
		_portletURLListenersByListenerClass.put(
			portletURLListener.getListenerClass(), portletURLListener);
	}

	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter) {

		_publicRenderParameters.add(publicRenderParameter);
		_publicRenderParametersByIdentifier.put(
			publicRenderParameter.getIdentifier(), publicRenderParameter);
	}

	public void addPublicRenderParameter(String identifier, QName qName) {
		PublicRenderParameter publicRenderParameter =
			new PublicRenderParameterImpl(identifier, qName, this);

		addPublicRenderParameter(publicRenderParameter);
	}

	public Map<String, String[]> getContainerRuntimeOptions() {
		return _containerRuntimeOptions;
	}

	public Map<String, String> getCustomUserAttributes() {
		return _customUserAttributes;
	}

	public String getDefaultNamespace() {
		return _defaultNamespace;
	}

	public PortletFilter getPortletFilter(String filterName) {
		return _portletFiltersByFilterName.get(filterName);
	}

	public Set<PortletFilter> getPortletFilters() {
		return _portletFilters;
	}

	public PortletURLListener getPortletURLListener(String listenerClass) {
		return _portletURLListenersByListenerClass.get(listenerClass);
	}

	public Set<PortletURLListener> getPortletURLListeners() {
		return _portletURLListeners;
	}

	public PublicRenderParameter getPublicRenderParameter(String identifier) {
		return _publicRenderParametersByIdentifier.get(identifier);
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public Set<String> getServletURLPatterns() {
		return _servletURLPatterns;
	}

	public SpriteImage getSpriteImage(String fileName) {
		return _spriteImagesMap.get(fileName);
	}

	public Set<String> getUserAttributes() {
		return _userAttributes;
	}

	public boolean isWARFile() {
		return _warFile;
	}

	public void setDefaultNamespace(String defaultNamespace) {
		_defaultNamespace = defaultNamespace;
	}

	public void setSpriteImages(
		String spriteFileName, Properties properties) {

		Iterator<Map.Entry<Object, Object>> itr =
			properties.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			int[] values = StringUtil.split(value, 0);

			int offset = values[0];
			int height = values[1];
			int width = values[2];

			SpriteImage spriteImage = new SpriteImage(
				spriteFileName, key, offset, height, width);

			_spriteImagesMap.put(key, spriteImage);
		}
	}

	public void setWARFile(boolean warFile) {
		_warFile = warFile;
	}

	private Map<String, String[]> _containerRuntimeOptions =
		new HashMap<String, String[]>();
	private Map<String, String> _customUserAttributes =
		new LinkedHashMap<String, String>();
	private String _defaultNamespace = XMLConstants.NULL_NS_URI;
	private Set<EventDefinition> _eventDefinitions =
		new LinkedHashSet<EventDefinition>();
	private Set<PortletFilter> _portletFilters =
		new LinkedHashSet<PortletFilter>();
	private Map<String, PortletFilter> _portletFiltersByFilterName =
		new HashMap<String, PortletFilter>();
	private Set<PortletURLListener> _portletURLListeners =
		new LinkedHashSet<PortletURLListener>();
	private Map<String, PortletURLListener>
		_portletURLListenersByListenerClass =
			new HashMap<String, PortletURLListener>();
	private Set<PublicRenderParameter> _publicRenderParameters =
		new LinkedHashSet<PublicRenderParameter>();
	private Map<String, PublicRenderParameter>
		_publicRenderParametersByIdentifier =
			new HashMap<String, PublicRenderParameter>();
	private String _servletContextName = StringPool.BLANK;
	private Set<String> _servletURLPatterns = new LinkedHashSet<String>();
	private Map<String, SpriteImage> _spriteImagesMap =
		new HashMap<String, SpriteImage>();
	private Set<String> _userAttributes = new LinkedHashSet<String>();
	private boolean _warFile;

}