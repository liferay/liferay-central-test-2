/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;

import java.io.File;
import java.io.IOException;

import java.net.URL;

/**
 * <a href="ThemeLoaderTemplateLoader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class ThemeLoaderTemplateLoader extends URLTemplateLoader {

	public URL getURL(String name) throws IOException {
		int pos = name.indexOf(THEME_LOADER_SEPARATOR);

		if (pos != -1) {
			String ctxName = name.substring(0, pos);

			ThemeLoader themeLoader =
				ThemeLoaderFactory.getThemeLoader(ctxName);

			if (themeLoader != null) {
				String templateName =
					name.substring(pos + THEME_LOADER_SEPARATOR.length());

				String themesPath = themeLoader.getThemesPath();

				if (templateName.startsWith(themesPath)) {
					name =
						templateName.substring(
							themesPath.length(), templateName.length());
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						name + " is associated with the theme loader " +
							ctxName + " " + themeLoader);
				}

				File fileStorage = themeLoader.getFileStorage();

				return new File(fileStorage.getPath() + name).toURI().toURL();

			}
			else {
				_log.error(
					name + " is not valid because " + ctxName +
						" does not map to a theme loader");
			}
		}

		return null;
	}

	private static Log _log =
		LogFactoryUtil.getLog(ThemeLoaderTemplateLoader.class);

}