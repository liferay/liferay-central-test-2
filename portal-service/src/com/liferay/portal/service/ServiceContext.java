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

package com.liferay.portal.service;

import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ServiceContext.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ServiceContext {

	public Serializable getAttribute(String name) {
		return _attributes.get(name);
	}

	public Map<String, Serializable> getAttributes() {
		return _attributes;
	}

	public Map<String, Object> getExpandoBridgeAttributes() {
		return _expandoBridgeAttributes;
	}

	public void setAttribute(String name, Serializable value) {
		_attributes.put(name, value);
	}

	public void setAttributes(Map<String, Serializable> attributes) {
		_attributes = attributes;
	}

	public void setExpandoBridgeAttributes(
		Map<String, Object> expandoBridgeAttributes) {

		_expandoBridgeAttributes = expandoBridgeAttributes;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected Map<String, Serializable> _attributes =
		new HashMap<String, Serializable>();
	protected Map<String, Object> _expandoBridgeAttributes
		= new HashMap<String, Object>();
	protected PortletPreferencesIds _portletPreferencesIds;
	protected ThemeDisplay _themeDisplay;

}