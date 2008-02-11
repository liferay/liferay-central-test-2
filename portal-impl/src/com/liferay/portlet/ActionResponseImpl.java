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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * <a href="ActionResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ActionResponseImpl implements ActionResponse {

	public void addProperty(Cookie cookie) {
		if (cookie == null) {
			throw new IllegalArgumentException();
		}
	}

	public void addProperty(String key, Element element) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public void addProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}
	}

	public PortletURL createActionURL() {
		PortletURL portletURL = createPortletURL(true);

		try {
			portletURL.setWindowState(_req.getWindowState());
		}
		catch (WindowStateException wse) {
		}

		try {
			portletURL.setPortletMode(_req.getPortletMode());
		}
		catch (PortletModeException pme) {
		}

		return portletURL;
	}

	public Element createElement(String tagName) throws DOMException {
		return null;
	}

	public PortletURL createPortletURL(boolean action) {
		return createPortletURL(_portletName, action);
	}

	public PortletURL createPortletURL(String portletName, boolean action) {

		// Wrap portlet URL with a custom wrapper if and only if a custom
		// wrapper for the portlet has been defined

		Portlet portlet = getPortlet();

		String portletURLClass = portlet.getPortletURLClass();

		if (portlet.getPortletId().equals(portletName) &&
			Validator.isNotNull(portletURLClass)) {

			try {
				Class<?> portletURLClassObj = Class.forName(portletURLClass);

				Constructor<?> constructor = portletURLClassObj.getConstructor(
					new Class[] {
						com.liferay.portlet.RenderResponseImpl.class,
						boolean.class
					});

				return (PortletURL)constructor.newInstance(
					new Object[] {this, Boolean.valueOf(action)});
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		long plid = _plid;

		try {
			Layout layout = (Layout)_req.getAttribute(WebKeys.LAYOUT);

			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, _portletName);

			plid = GetterUtil.getLong(portletSetup.getValue(
				"portlet-setup-link-to-plid", String.valueOf(_plid)));

			if (plid <= 0) {
				plid = _plid;
			}
		}
		catch (PortalException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}
		catch (SystemException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}
		}

		return new PortletURLImpl(_req, portletName, plid, action);
	}

	public PortletURL createRenderURL() {
		PortletURL portletURL = createPortletURL(false);

		try {
			portletURL.setWindowState(_req.getWindowState());
		}
		catch (WindowStateException wse) {
		}

		try {
			portletURL.setPortletMode(_req.getPortletMode());
		}
		catch (PortletModeException pme) {
		}

		return portletURL;
	}

	public ResourceURL createResourceURL() {
		return null;
	}

	public String encodeURL(String path) {
		return path;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _res;
	}

	public String getNamespace() {
		return PortalUtil.getPortletNamespace(_portletName);
	}

	public Portlet getPortlet() {
		if (_portlet == null) {
			try {
				_portlet = PortletLocalServiceUtil.getPortletById(
					_companyId, _portletName);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return _portlet;
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

	public WindowState getWindowState() {
		return _windowState;
	}

	public void removePublicRenderParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
	}

	public void sendRedirect(String location) throws IOException {
		if ((location == null) ||
			(!location.startsWith("/") && (location.indexOf("://") == -1))) {

			throw new IllegalArgumentException(
				location + " is not a valid redirect");
		}

		if (_calledSetRenderParameter) {
			throw new IllegalStateException(
				"Set render parameter has already been called");
		}

		_redirectLocation = location;
	}

	public void sendRedirect(String location, String renderUrlParamName)
		throws IOException {
	}

	public void setEvent(QName name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
	}

	public void setEvent(String name, Serializable value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
	}

	public void setPortletMode(PortletMode portletMode)
		throws PortletModeException {

		if (_redirectLocation != null) {
			throw new IllegalStateException();
		}

		if (!_req.isPortletModeAllowed(portletMode)) {
			throw new PortletModeException(portletMode.toString(), portletMode);
		}

		try {
			_portletMode = PortalUtil.updatePortletMode(
				_portletName, _user, _layout, portletMode,
				_req.getHttpServletRequest());

			_req.setPortletMode(_portletMode);
		}
		catch (Exception e) {
			throw new PortletModeException(e, portletMode);
		}

		_calledSetRenderParameter = true;
	}

	public void setProperty(String key, String value) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		if (_properties == null) {
			_properties = new HashMap<String, String[]>();
		}

		_properties.put(key, new String[] {value});
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

		_params.put(
			PortalUtil.getPortletNamespace(_portletName) + name,
			values);

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

				newParams.put(
					PortalUtil.getPortletNamespace(_portletName) + key,
					value);
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

		if (!_req.isWindowStateAllowed(windowState)) {
			throw new WindowStateException(windowState.toString(), windowState);
		}

		try {
			_windowState = PortalUtil.updateWindowState(
				_portletName, _user, _layout, windowState,
				_req.getHttpServletRequest());

			_req.setWindowState(_windowState);
		}
		catch (Exception e) {
			throw new WindowStateException(e, windowState);
		}

		_calledSetRenderParameter = true;
	}

	protected ActionResponseImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	protected ActionRequestImpl getActionRequest() {
		return _req;
	}

	protected Layout getLayout() {
		return _layout;
	}

	protected Map<String, String[]> getParameterMap() {
		return _params;
	}

	protected long getPlid() {
		return _plid;
	}

	protected String getPortletName() {
		return _portletName;
	}

	protected Map<String, String[]> getProperties() {
		return _properties;
	}

	protected User getUser() {
		return _user;
	}

	protected void init(
			ActionRequestImpl req, HttpServletResponse res, String portletName,
			User user, Layout layout, WindowState windowState,
			PortletMode portletMode)
		throws PortletModeException, WindowStateException {

		_req = req;
		_res = res;
		_portletName = portletName;
		_companyId = layout.getCompanyId();
		_user = user;
		_layout = layout;
		setPlid(layout.getPlid());
		setWindowState(windowState);
		setPortletMode(portletMode);
		_params = new LinkedHashMap<String, String[]>();
		_calledSetRenderParameter = false;
	}

	protected boolean isCalledSetRenderParameter() {
		return _calledSetRenderParameter;
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		_req = null;
		_res = null;
		_portletName = null;
		_companyId = 0;
		_user = null;
		_layout = null;
		_plid = 0;
		_windowState = null;
		_portletMode = null;
		_params = null;
		_redirectLocation = null;
		_calledSetRenderParameter = false;
	}

	protected void setPlid(long plid) {
		_plid = plid;

		if (_plid <= 0) {
			Layout layout = (Layout)_req.getAttribute(WebKeys.LAYOUT);

			if (layout != null) {
				_plid = layout.getPlid();
			}
		}
	}

	private static Log _log = LogFactory.getLog(ActionResponseImpl.class);

	private ActionRequestImpl _req;
	private HttpServletResponse _res;
	private String _portletName;
	private Portlet _portlet;
	private long _companyId;
	private User _user;
	private Layout _layout;
	private long _plid;
	private Map<String, String[]> _properties;
	private WindowState _windowState;
	private PortletMode _portletMode;
	private Map<String, String[]> _params;
	private String _redirectLocation;
	private boolean _calledSetRenderParameter;

}