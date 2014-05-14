/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.settings;

import java.io.IOException;

import java.util.Collection;

import javax.portlet.ValidatorException;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Iván Zaera
 */
public interface Settings {

	public Settings getDefaultSettings();

	public Collection<String> getKeys();

	public String getValue(String key, String defaultValue);

	public String[] getValues(String key, String[] defaultValue);

	public void reset();

	public void reset(String key);

	public Settings setValue(String key, String value);

	public Settings setValues(Settings settings);

	public Settings setValues(String key, String[] values);

	public void store() throws IOException, ValidatorException;

}