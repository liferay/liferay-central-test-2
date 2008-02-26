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

import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletMode;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="RenderResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RenderResponseImpl
	extends MimeResponseImpl implements LiferayRenderResponse {

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

	public String getResourceName() {
		return _resourceName;
	}

	public String getTitle() {
		return _title;
	}

	public Boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
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

	protected void init(
		PortletRequestImpl req, HttpServletResponse res, String portletName,
		long companyId, long plid) {

		super.init(req, res, portletName, companyId, plid);

		_req = req;
	}

	protected void recycle() {
		if (_log.isDebugEnabled()) {
			_log.debug("Recycling instance " + hashCode());
		}

		super.recycle();

		_req = null;
		_title = null;
		_useDefaultTemplate = null;
		_resourceName = null;
		_headers.clear();
	}

	private static Log _log = LogFactory.getLog(RenderResponseImpl.class);

	private PortletRequestImpl _req;
	private String _title;
 	private Boolean _useDefaultTemplate;
	private String _resourceName;
	private LinkedHashMap<String, Object> _headers =
		new LinkedHashMap<String, Object>();

}