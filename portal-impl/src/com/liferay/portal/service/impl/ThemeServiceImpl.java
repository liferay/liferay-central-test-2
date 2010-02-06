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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.base.ThemeServiceBaseImpl;

import java.util.List;

/**
 * <a href="ThemeServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThemeServiceImpl extends ThemeServiceBaseImpl {

	public List<Theme> getThemes(long companyId) {
		return themeLocalService.getThemes(companyId);
	}

	public JSONArray getWARThemes() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Theme> themes = themeLocalService.getWARThemes();

		for (Theme theme : themes) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("theme_id", theme.getThemeId());
			jsonObject.put("theme_name", theme.getName());
			jsonObject.put(
				"servlet_context_name", theme.getServletContextName());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

}