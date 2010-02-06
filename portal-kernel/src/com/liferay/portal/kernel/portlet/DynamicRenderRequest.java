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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.filter.RenderRequestWrapper;

/**
 * <a href="DynamicRenderRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DynamicRenderRequest extends RenderRequestWrapper {

	public DynamicRenderRequest(RenderRequest renderRequest) {
		this(renderRequest, new HashMap<String, String[]>(), true);
	}

	public DynamicRenderRequest(
		RenderRequest renderRequest, Map<String, String[]> params) {

		this(renderRequest, params, true);
	}

	public DynamicRenderRequest(RenderRequest renderRequest, boolean inherit) {
		this(renderRequest, new HashMap<String, String[]>(), inherit);
	}

	public DynamicRenderRequest(
		RenderRequest renderRequest, Map<String, String[]> params,
		boolean inherit) {

		super(renderRequest);

		_params = new HashMap<String, String[]>();
		_inherit = inherit;

		if (params != null) {
			for (Map.Entry<String, String[]> entry : params.entrySet()) {
				_params.put(entry.getKey(), entry.getValue());
			}
		}

		if (_inherit && (renderRequest instanceof DynamicRenderRequest)) {
			DynamicRenderRequest dynamicRenderRequest =
				(DynamicRenderRequest)renderRequest;

			setRequest(dynamicRenderRequest.getRequest());

			params = dynamicRenderRequest.getDynamicParameterMap();

			if (params != null) {
				for (Map.Entry<String, String[]> entry : params.entrySet()) {
					String name = entry.getKey();
					String[] oldValues = entry.getValue();

					String[] curValues = _params.get(name);

					if (curValues == null) {
						_params.put(name, oldValues);
					}
					else {
						String[] newValues = ArrayUtil.append(
							oldValues, curValues);

						_params.put(name, newValues);
					}
				}
			}
		}
	}

	public String getParameter(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameter(name);
		}

		if ((values != null) && (values.length > 0)) {
			return values[0];
		}
		else {
			return null;
		}
	}

	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String s = enu.nextElement();

			map.put(s, getParameterValues(s));
		}

		return map;
	}

	public Enumeration<String> getParameterNames() {
		List<String> names = new ArrayList<String>();

		if (_inherit) {
			Enumeration<String> enu = super.getParameterNames();

			while (enu.hasMoreElements()) {
				names.add(enu.nextElement());
			}
		}

		for (String s : _params.keySet()) {
			if (!names.contains(s)) {
				names.add(s);
			}
		}

		return Collections.enumeration(names);
	}

	public String[] getParameterValues(String name) {
		String[] values = _params.get(name);

		if (_inherit && (values == null)) {
			return super.getParameterValues(name);
		}

		return values;
	}

	public void setParameter(String name, String value) {
		_params.put(name, new String[] {value});
	}

	public void setParameterValues(String name, String[] values) {
		_params.put(name, values);
	}

	public Map<String, String[]> getDynamicParameterMap() {
		return _params;
	}

	private Map<String, String[]> _params;
	private boolean _inherit;

}