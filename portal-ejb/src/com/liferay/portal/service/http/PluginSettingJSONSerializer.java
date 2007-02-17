/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PluginSetting;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="PluginSettingJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PluginSettingJSONSerializer {
	public static JSONObject toJSONObject(PluginSetting model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("pluginSettingId", model.getPluginSettingId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String pluginId = model.getPluginId();

		if (pluginId == null) {
			jsonObj.put("pluginId", StringPool.BLANK);
		}
		else {
			jsonObj.put("pluginId", pluginId.toString());
		}

		String pluginType = model.getPluginType();

		if (pluginType == null) {
			jsonObj.put("pluginType", StringPool.BLANK);
		}
		else {
			jsonObj.put("pluginType", pluginType.toString());
		}

		String roles = model.getRoles();

		if (roles == null) {
			jsonObj.put("roles", StringPool.BLANK);
		}
		else {
			jsonObj.put("roles", roles.toString());
		}

		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			PluginSetting model = (PluginSetting)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}