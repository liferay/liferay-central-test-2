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
import com.liferay.portal.model.Layout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="LayoutJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.LayoutServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.LayoutServiceJSON
 *
 */
public class LayoutJSONSerializer {
	public static JSONObject toJSONObject(Layout model) {
		JSONObject jsonObj = new JSONObject();
		String layoutId = model.getLayoutId();

		if (layoutId == null) {
			jsonObj.put("layoutId", StringPool.BLANK);
		}
		else {
			jsonObj.put("layoutId", layoutId.toString());
		}

		String ownerId = model.getOwnerId();

		if (ownerId == null) {
			jsonObj.put("ownerId", StringPool.BLANK);
		}
		else {
			jsonObj.put("ownerId", ownerId.toString());
		}

		jsonObj.put("companyId", model.getCompanyId());

		String parentLayoutId = model.getParentLayoutId();

		if (parentLayoutId == null) {
			jsonObj.put("parentLayoutId", StringPool.BLANK);
		}
		else {
			jsonObj.put("parentLayoutId", parentLayoutId.toString());
		}

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String title = model.getTitle();

		if (title == null) {
			jsonObj.put("title", StringPool.BLANK);
		}
		else {
			jsonObj.put("title", title.toString());
		}

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		String typeSettings = model.getTypeSettings();

		if (typeSettings == null) {
			jsonObj.put("typeSettings", StringPool.BLANK);
		}
		else {
			jsonObj.put("typeSettings", typeSettings.toString());
		}

		jsonObj.put("hidden", model.getHidden());

		String friendlyURL = model.getFriendlyURL();

		if (friendlyURL == null) {
			jsonObj.put("friendlyURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("friendlyURL", friendlyURL.toString());
		}

		jsonObj.put("iconImage", model.getIconImage());

		String themeId = model.getThemeId();

		if (themeId == null) {
			jsonObj.put("themeId", StringPool.BLANK);
		}
		else {
			jsonObj.put("themeId", themeId.toString());
		}

		String colorSchemeId = model.getColorSchemeId();

		if (colorSchemeId == null) {
			jsonObj.put("colorSchemeId", StringPool.BLANK);
		}
		else {
			jsonObj.put("colorSchemeId", colorSchemeId.toString());
		}

		String css = model.getCss();

		if (css == null) {
			jsonObj.put("css", StringPool.BLANK);
		}
		else {
			jsonObj.put("css", css.toString());
		}

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