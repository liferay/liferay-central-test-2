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
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PortletURLListener;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.lang.reflect.Constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * <a href="PortletResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class PortletResponseImpl implements PortletResponse {

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

	public LiferayPortletURL createActionURL() {
		return createActionURL(_portletName);
	}

	public LiferayPortletURL createActionURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.ACTION_PHASE);
	}

	public Element createElement(String tagName) throws DOMException {
		return null;
	}

	public PortletURLImpl createPortletURLImpl(String lifecycle) {
		return createPortletURLImpl(_portletName, lifecycle);
	}

	public PortletURLImpl createPortletURLImpl(
		String portletName, String lifecycle) {

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
						com.liferay.portlet.PortletResponseImpl.class,
						String.class
					});

				return (PortletURLImpl)constructor.newInstance(
					new Object[] {this, lifecycle});
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

		PortletApp portletApp = portlet.getPortletApp();

		List<PortletURLListener> portletURLListeners =
			portletApp.getPortletURLListeners();

		PortletURLImpl portletURLImpl = new PortletURLImpl(
			_req, portletName, plid, lifecycle);

		for (PortletURLListener portletURLListener : portletURLListeners) {
			try {
				PortletURLGenerationListener portletURLGenerationListener =
					PortletURLListenerFactory.create(portletURLListener);

				if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
					portletURLGenerationListener.filterActionURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
					portletURLGenerationListener.filterRenderURL(
						portletURLImpl);
				}
				else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
					portletURLGenerationListener.filterResourceURL(
						portletURLImpl);
				}
			}
			catch (PortletException pe) {
				_log.error(pe, pe);
			}
		}

		try {
			portletURLImpl.setWindowState(_req.getWindowState());
		}
		catch (WindowStateException wse) {
		}

		try {
			portletURLImpl.setPortletMode(_req.getPortletMode());
		}
		catch (PortletModeException pme) {
		}

		return portletURLImpl;
	}

	public LiferayPortletURL createRenderURL() {
		return createRenderURL(_portletName);
	}

	public LiferayPortletURL createRenderURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.RENDER_PHASE);
	}

	public ResourceURL createResourceURL() {
		return createResourceURL(_portletName);
	}

	public ResourceURL createResourceURL(String portletName) {
		return createPortletURLImpl(portletName, PortletRequest.RESOURCE_PHASE);
	}

	public String encodeURL(String path) {
		if ((path == null) ||
			(!path.startsWith("#") && !path.startsWith("/") &&
				(path.indexOf("://") == -1))) {

			// Allow '#' as well to workaround a bug in Oracle ADF 10.1.3

			throw new IllegalArgumentException(
				"URL path must start with a '/' or include '://'");
		}

		if (_urlEncoder != null) {
			return _urlEncoder.encodeURL(_res, path);
		}
		else {
			return path;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _req.getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return _res;
	}

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletName);
		}

		return _namespace;
	}

	public long getPlid() {
		return _plid;
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

	public String getPortletName() {
		return _portletName;
	}

	public PortletRequestImpl getPortletRequest() {
		return _req;
	}

	public Map<String, String[]> getProperties() {
		return _properties;
	}

	public URLEncoder getUrlEncoder() {
		return _urlEncoder;
	}

	public void setPlid(long plid) {
		_plid = plid;

		if (_plid <= 0) {
			Layout layout = (Layout)_req.getAttribute(WebKeys.LAYOUT);

			if (layout != null) {
				_plid = layout.getPlid();
			}
		}
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

	public void setURLEncoder(URLEncoder urlEncoder) {
		_urlEncoder = urlEncoder;
	}

	protected void init(
		PortletRequestImpl req, HttpServletResponse res, String portletName,
		long companyId, long plid) {

		_req = req;
		_res = res;
		_portletName = portletName;
		_companyId = companyId;
		setPlid(plid);
	}

	protected void recycle() {
		_req = null;
		_res = null;
		_portletName = null;
		_portlet = null;
		_namespace = null;
		_companyId = 0;
		_plid = 0;
	}

	private static Log _log = LogFactory.getLog(PortletResponseImpl.class);

	private PortletRequestImpl _req;
	private HttpServletResponse _res;
	private String _portletName;
	private Portlet _portlet;
	private String _namespace;
	private long _companyId;
	private long _plid;
	private Map<String, String[]> _properties;
	private URLEncoder _urlEncoder;

}