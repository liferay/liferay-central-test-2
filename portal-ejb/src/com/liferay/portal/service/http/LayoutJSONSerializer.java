/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.model.Layout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="LayoutJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutJSONSerializer {
	public static JSONObject toJSONObject(Layout model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("layoutId", model.getLayoutId().toString());
		jsonObj.put("ownerId", model.getOwnerId().toString());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("parentLayoutId", model.getParentLayoutId().toString());
		jsonObj.put("name", model.getName().toString());
		jsonObj.put("title", model.getTitle().toString());
		jsonObj.put("type", model.getType().toString());
		jsonObj.put("typeSettings", model.getTypeSettings().toString());
		jsonObj.put("hidden", model.getHidden());
		jsonObj.put("friendlyURL", model.getFriendlyURL().toString());
		jsonObj.put("iconImage", model.getIconImage());
		jsonObj.put("themeId", model.getThemeId().toString());
		jsonObj.put("colorSchemeId", model.getColorSchemeId().toString());
		jsonObj.put("priority", model.getPriority());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Layout model = (Layout)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}