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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Event;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletResponse;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

/**
 * <a href="StateAwareResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class StateAwareResponseImpl
	extends PortletResponseImpl implements StateAwareResponse {

	public String getDefaultNamespace() {
		Portlet portlet = getPortlet();

		if (portlet != null) {
			return portlet.getPortletApp().getDefaultNamespace();
		}
		else {
			return XMLConstants.NULL_NS_URI;
		}
	}

	public List<Event> getEvents() {
		return _events;
	}

	public Layout getLayout() {
		return _layout;
	}

	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public String getRedirectLocation() {
		return _redirectLocation;
	}

	public Map<String, String[]> getRenderParameterMap() {
		return _params;
	}

	public User getUser() {
		return _user;
	}

	public WindowState getWindowState() {
		return _windowState;
	}

	public boolean isCalledSetRenderParameter() {
		return _calledSetRenderParameter;
	}

	public void removePublicRenderParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		PublicRenderParameter publicRenderParameter =
			getPortlet().getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Public parameter " + name + "does not exist");
			}

			return;
		}

		com.liferay.portal.kernel.xml.QName qName =
			publicRenderParameter.getQName();

		String key = PortletQNameUtil.getKey(qName);

		if (_publicRenderParameters.containsKey(key)) {
			_publicRenderParameters.remove(key);
		}
	}

	public void setEvent(QName name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_events.add(new EventImpl(name.getLocalPart(), name, value));
	}

	public void setEvent(String name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		setEvent(new QName(getDefaultNamespace(), name), value);
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (!_portletRequestImpl.isPortletModeAllowed(portletMode)) {
			throw new PortletModeException(portletMode.toString(), portletMode);
		}

		try {
			_portletMode = PortalUtil.updatePortletMode(
				_portletName, _user, _layout, portletMode,
				_portletRequestImpl.getHttpServletRequest());

			_portletRequestImpl.setPortletMode(_portletMode);
		}
		catch (Exception e) {
			throw new PortletModeException(e, portletMode);
		}

		_calledSetRenderParameter = true;
	}

	public void setRedirectLocation(String redirectLocation) {
		_redirectLocation = redirectLocation;
	}

	public void setRenderParameter(String name, String value) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if ((name == null) || (value == null)) {
			throw new IllegalArgumentException();
		}

		setRenderParameter(name, new String[] {value});
	}

	public void setRenderParameter(String name, String[] values) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if ((name == null) || (values == null)) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < values.length; i++) {
			if (values[i] == null) {
				throw new IllegalArgumentException();
			}
		}

		if (!setPublicRenderParameter(name, values)) {
			_params.put(name, values);
		}

		_calledSetRenderParameter = true;
	}

	public void setRenderParameters(Map<String, String[]> params) {
		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (params == null) {
			throw new IllegalArgumentException();
		}
		else {
			Map<String, String[]> newParams =
				new LinkedHashMap<String, String[]>();

			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				String key = entry.getKey();
				String[] value = entry.getValue();

				if (key == null) {
					throw new IllegalArgumentException();
				}
				else if (value == null) {
					throw new IllegalArgumentException();
				}

				if (setPublicRenderParameter(key, value)) {
					continue;
				}

				newParams.put(key, value);
			}

			_params = newParams;
		}

		_calledSetRenderParameter = true;
	}

	public void setWindowState(WindowState windowState)
		throws WindowStateException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (!_portletRequestImpl.isWindowStateAllowed(windowState)) {
			throw new WindowStateException(windowState.toString(), windowState);
		}

		try {
			_windowState = PortalUtil.updateWindowState(
				_portletName, _user, _layout, windowState,
				_portletRequestImpl.getHttpServletRequest());

			_portletRequestImpl.setWindowState(_windowState);
		}
		catch (Exception e) {
			throw new WindowStateException(e, windowState);
		}

		_calledSetRenderParameter = true;
	}

	protected void init(
			PortletRequestImpl portletRequestImpl, HttpServletResponse response,
			String portletName, User user, Layout layout,
			WindowState windowState, PortletMode portletMode)
		throws PortletModeException, WindowStateException {

		super.init(
			portletRequestImpl, response, portletName, layout.getCompanyId(),
			layout.getPlid());

		_portletRequestImpl = portletRequestImpl;
		_portletName = portletName;
		_user = user;
		_layout = layout;
		_publicRenderParameters = PublicRenderParametersPool.get(
			getHttpServletRequest(), layout.getPlid());

		if (windowState != null) {
			setWindowState(windowState);
		}

		if (portletMode != null) {
			setPortletMode(portletMode);
		}

		// Set _calledSetRenderParameter to false because setWindowState and
		// setPortletMode sets it to true

		_calledSetRenderParameter = false;
	}

	protected boolean setPublicRenderParameter(String name, String[] values) {
		Portlet portlet = getPortlet();

		PublicRenderParameter publicRenderParameter =
			portlet.getPublicRenderParameter(name);

		if (publicRenderParameter == null) {
			return false;
		}

		com.liferay.portal.kernel.xml.QName qName =
			publicRenderParameter.getQName();

		if (_publicRenderParameters.containsKey(name)) {
			String[] oldValues = _publicRenderParameters.get(name);

			values = ArrayUtil.append(oldValues, values);
		}

		_publicRenderParameters.put(PortletQNameUtil.getKey(qName), values);

		return true;
	}

	private static Log _log =
		LogFactoryUtil.getLog(StateAwareResponseImpl.class);

	private boolean _calledSetRenderParameter;
	private List<Event> _events = new ArrayList<Event>();
	private Layout _layout;
	private Map<String, String[]> _params =
		new LinkedHashMap<String, String[]>();
	private PortletMode _portletMode;
	private String _portletName;
	private PortletRequestImpl _portletRequestImpl;
	private Map<String, String[]> _publicRenderParameters;
	private String _redirectLocation;
	private User _user;
	private WindowState _windowState;

}