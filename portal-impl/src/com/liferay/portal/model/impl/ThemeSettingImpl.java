/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.model.ThemeSetting;

/**
 * @author Julio Camarero
 */
public class ThemeSettingImpl implements ThemeSetting {
	public ThemeSettingImpl() {
	}

	public ThemeSettingImpl(
		boolean configurable, String[] options, String type, String value) {

		_configurable = configurable;
		_options = options;
		_type = type;
		_value = value;
	}

	public String[] getOptions() {
		return _options;
	}

	public String getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	public boolean isConfigurable() {
		return _configurable;
	}

	public void setConfigurable(boolean configurable) {
		this._configurable = configurable;
	}

	public void setOptions(String[] options) {
		_options = options;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(String value) {
		_value = value;
	}

	private boolean _configurable = false;
	private String[] _options;
	private String _type;
	private String _value;

}