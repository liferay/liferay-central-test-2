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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Randomizer;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="RandomLookAndFeelAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RandomLookAndFeelAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {

			// Do not randomize look and feel unless the user is logged in

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (!themeDisplay.isSignedIn()) {
				return;
			}

			// Do not randomize look and feel unless the user is accessing the
			// portal

			String requestURI = GetterUtil.getString(request.getRequestURI());

			if (!requestURI.endsWith("/portal/layout")) {
				return;
			}

			// Do not randomize look and feel unless the user is accessing a
			// personal layout

			Layout layout = themeDisplay.getLayout();

			if (layout == null) {
				return;
			}

			Randomizer randomizer = Randomizer.getInstance();

			boolean wapTheme = BrowserSnifferUtil.isWap(request);

			List<Theme> themes = ThemeLocalServiceUtil.getThemes(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				themeDisplay.getUserId(), wapTheme);

			if (themes.size() > 0) {
				Theme theme = themes.get(randomizer.nextInt(themes.size()));

				List<ColorScheme> colorSchemes = theme.getColorSchemes();

				ColorScheme colorScheme = colorSchemes.get(
					randomizer.nextInt(colorSchemes.size()));

				LayoutServiceUtil.updateLookAndFeel(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getPlid(), theme.getThemeId(),
					colorScheme.getColorSchemeId(), layout.getCss(), wapTheme);

				themeDisplay.setLookAndFeel(theme, colorScheme);

				request.setAttribute(WebKeys.THEME, theme);
				request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ActionException(e);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(RandomLookAndFeelAction.class);

}