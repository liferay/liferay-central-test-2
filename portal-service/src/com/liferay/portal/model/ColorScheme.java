/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public interface ColorScheme extends Comparable<ColorScheme>, Serializable {

	public String getColorSchemeId();

	public String getName();

	public void setName(String name);

	public boolean getDefaultCs();

	public boolean isDefaultCs();

	public void setDefaultCs(boolean defaultCs);

	public String getCssClass();

	public void setCssClass(String cssClass);

	public String getColorSchemeImagesPath();

	public void setColorSchemeImagesPath(String colorSchemeImagesPath);

	public String getColorSchemeThumbnailPath();

	public String getSettings();

	public void setSettings(String settings);

	public Properties getSettingsProperties();

	public void setSettingsProperties(Properties settingsProperties);

	public String getSetting(String key);

}