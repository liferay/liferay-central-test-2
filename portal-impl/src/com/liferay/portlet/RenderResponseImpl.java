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
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.URLEncoder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.lang.reflect.Constructor;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.CacheControl;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * <a href="RenderResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RenderResponseImpl implements LiferayRenderResponse {

	public void addDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			Long[] values = (Long[])_headers.get(name);

			ArrayUtil.append(values, new Long(date));

			_headers.put(name, values);
		}
		else {
			setDateHeader(name, date);
		}
	}

	public void addHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			String[] values = (String[])_headers.get(name);

			ArrayUtil.append(values, value);

			_headers.put(name, values);
		}
		else {
			setHeader(name, value);
		}
	}

	public void addIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_headers.containsKey(name)) {
			Integer[] values = (Integer[])_headers.get(name);

			ArrayUtil.append(values, new Integer(value));

			_headers.put(name, values);
		}
		else {
			setIntHeader(name, value);
		}
	}

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
		return createActionURL(_portletName);
	}

	public PortletURL createActionURL(String portletName) {
		PortletURL portletURL = createPortletURL(portletName, true);

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
		return createRenderURL(_portletName);
	}

	public PortletURL createRenderURL(String portletName) {
		PortletURL portletURL = createPortletURL(portletName, false);

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

	public int getBufferSize() {
		return _res.getBufferSize();
	}

	public CacheControl getCacheControl() {
		return null;
	}

	public String getCharacterEncoding() {
		return _res.getCharacterEncoding();
	}

	public String getContentType() {
		return _contentType;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _res;
	}

	public Locale getLocale() {
		return _req.getLocale();
	}

	public String getNamespace() {
		if (_namespace == null) {
			_namespace = PortalUtil.getPortletNamespace(_portletName);
		}

		return _namespace;
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

	public OutputStream getPortletOutputStream() throws IOException {
		if (_calledGetWriter) {
			throw new IllegalStateException();
		}

		if (_contentType == null) {
			throw new IllegalStateException();
		}

		_calledGetPortletOutputStream = true;

		return _res.getOutputStream();
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getTitle() {
		return _title;
	}

	public Boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	public PrintWriter getWriter() throws IOException {
		if (_calledGetPortletOutputStream) {
			throw new IllegalStateException();
		}

		if (_contentType == null) {
			throw new IllegalStateException();
		}

		_calledGetWriter = true;

		return _res.getWriter();
	}

	public void flushBuffer() throws IOException {
		_res.flushBuffer();
	}

	public boolean isCommitted() {
		return false;
	}

	public void reset() {
	}

	public void resetBuffer() {
		_res.resetBuffer();
	}

	public void setBufferSize(int size) {
		_res.setBufferSize(size);
	}

	public void setContentType(String contentType) {
		if (Validator.isNull(contentType)) {
			throw new IllegalArgumentException();
		}

		Enumeration<String> enu = _req.getResponseContentTypes();

		boolean valid = false;

		while (enu.hasMoreElements()) {
			String resContentType = enu.nextElement();

			if (contentType.startsWith(resContentType)) {
				valid = true;

				break;
			}
		}

		if (_req.getWindowState().equals(LiferayWindowState.EXCLUSIVE)) {
			valid = true;
		}

		if (!valid) {
			throw new IllegalArgumentException();
		}

		_contentType = contentType;
	}

	public void setDateHeader(String name, long date) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (date <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Long[] {new Long(date)});
		}
	}

	public void setHeader(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (Validator.isNull(value)) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new String[] {value});
		}
	}

	public void setIntHeader(String name, int value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (value <= 0) {
			_headers.remove(name);
		}
		else {
			_headers.put(name, new Integer[] {new Integer(value)});
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

	public void setResourceName(String resourceName) {
		_resourceName = resourceName;
	}

	public void setNextPossiblePortletModes(
		Collection<PortletMode> portletModes) {
	}

	public void setTitle(String title) {
		_title = title;

		// See LEP-2188

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_req.getAttribute(WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setTitle(_title);
	}

	public void setURLEncoder(URLEncoder urlEncoder) {
		_urlEncoder = urlEncoder;
	}

	public void setUseDefaultTemplate(Boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	public void transferHeaders(HttpServletResponse res) {
		for (Map.Entry<String, Object> entry : _headers.entrySet()) {
			String name = entry.getKey();
			Object values = entry.getValue();

			if (values instanceof Integer[]) {
				Integer[] intValues = (Integer[])values;

				for (int i = 0; i < intValues.length; i++) {
					if (res.containsHeader(name)) {
						res.addIntHeader(name, intValues[i].intValue());
					}
					else {
						res.addIntHeader(name, intValues[i].intValue());
					}
				}
			}
			else if (values instanceof Long[]) {
				Long[] dateValues = (Long[])values;

				for (int i = 0; i < dateValues.length; i++) {
					if (res.containsHeader(name)) {
						res.addDateHeader(name, dateValues[i].longValue());
					}
					else {
						res.addDateHeader(name, dateValues[i].longValue());
					}
				}
			}
			else if (values instanceof String[]) {
				String[] stringValues = (String[])values;

				for (int i = 0; i < stringValues.length; i++) {
					if (res.containsHeader(name)) {
						res.addHeader(name, stringValues[i]);
					}
					else {
						res.addHeader(name, stringValues[i]);
					}
				}
			}
		}
	}

	protected RenderResponseImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Creating new instance " + hashCode());
		}
	}

	protected long getCompanyId() {
		return _companyId;
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

	protected RenderRequestImpl getRenderRequest() {
		return _req;
	}

	protected URLEncoder getUrlEncoder() {
		return _urlEncoder;
	}

	protected void init(
		RenderRequestImpl req, HttpServletResponse res, String portletName,
		long companyId, long plid) {

		_req = req;
		_res = res;
		_portletName = portletName;
		_companyId = companyId;
		setPlid(plid);
		_headers.clear();
	}

	protected boolean isCalledGetPortletOutputStream() {
		return _calledGetPortletOutputStream;
	}

	protected boolean isCalledGetWriter() {
		return _calledGetWriter;
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		_req = null;
		_res = null;
		_portletName = null;
		_portlet = null;
		_namespace = null;
		_companyId = 0;
		_plid = 0;
		_urlEncoder = null;
		_title = null;
		_useDefaultTemplate = null;
		_contentType = null;
		_calledGetPortletOutputStream = false;
		_calledGetWriter = false;
		_headers = null;
		_resourceName = null;
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

	private static Log _log = LogFactory.getLog(RenderRequestImpl.class);

	private RenderRequestImpl _req;
	private HttpServletResponse _res;
	private String _portletName;
	private Portlet _portlet;
	private String _namespace;
	private long _companyId;
	private long _plid;
	private Map<String, String[]> _properties;
	private URLEncoder _urlEncoder;
	private String _title;
 	private Boolean _useDefaultTemplate;
	private String _contentType;
	private boolean _calledGetPortletOutputStream;
 	private boolean _calledGetWriter;
	private LinkedHashMap<String, Object> _headers =
		new LinkedHashMap<String, Object>();
	private String _resourceName;

}