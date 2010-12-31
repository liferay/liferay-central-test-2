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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeLoaderVelocityResourceListener
	extends VelocityResourceListener {

	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		InputStream is = null;

		try {
			int pos = source.indexOf(THEME_LOADER_SEPARATOR);

			if (pos != -1) {
				String ctxName = source.substring(0, pos);

				ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
					ctxName);

				if (themeLoader != null) {
					String name =
						source.substring(pos + THEME_LOADER_SEPARATOR.length());

					String themesPath = themeLoader.getThemesPath();

					if (name.startsWith(themesPath)) {
						name = name.substring(
							themesPath.length(), name.length());
					}

					if (_log.isDebugEnabled()) {
						_log.debug(
							name + " is associated with the theme loader " +
								ctxName + " " + themeLoader);
					}

					is = new FileInputStream(
						themeLoader.getFileStorage().getPath() + name);
				}
				else {
					_log.error(
						source + " is not valid because " + ctxName +
							" does not map to a theme loader");
				}
			}
		}
		catch (FileNotFoundException fnfe) {
			throw new ResourceNotFoundException(source);
		}

		return is;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeLoaderVelocityResourceListener.class);

}