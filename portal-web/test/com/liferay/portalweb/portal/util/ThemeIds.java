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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.model.Theme;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.ThemeServiceHttp;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ThemeIds.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThemeIds {

	public static int getCount() {
		return _instance._getCount();
	}

	public static String getThemeId() {
		return _instance._getThemeId();
	}

	public static void iterate() {
		_instance._iterate();
	}

	private ThemeIds() {
		try {
			HttpPrincipal httpPrincipal = new HttpPrincipal(
				TestPropsValues.PORTAL_URL);

			List<String> themeIds = new ArrayList<String>();

			List<Theme> themes = ThemeServiceHttp.getThemes(
				httpPrincipal, TestPropsValues.COMPANY_ID);

			for (Theme theme : themes) {
				if (!theme.isWapTheme()) {
					themeIds.add(theme.getThemeId());
				}
			}

			_themeIds = themeIds.toArray(new String[themeIds.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int _getCount() {
		return _themeIds.length;
	}

	private String _getThemeId() {
		return _themeIds[_pos];
	}

	private void _iterate() {
		_pos++;
	}

	private static ThemeIds _instance = new ThemeIds();

	private String[] _themeIds = new String[0];
	private int _pos;

}