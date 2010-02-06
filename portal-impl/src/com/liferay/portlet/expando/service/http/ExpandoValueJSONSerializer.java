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

package com.liferay.portlet.expando.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.expando.model.ExpandoValue;

import java.util.List;

/**
 * <a href="ExpandoValueJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link ExpandoValueServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.expando.service.http.ExpandoValueServiceJSON
 * @generated
 */
public class ExpandoValueJSONSerializer {
	public static JSONObject toJSONObject(ExpandoValue model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("valueId", model.getValueId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("tableId", model.getTableId());
		jsonObj.put("columnId", model.getColumnId());
		jsonObj.put("rowId", model.getRowId());
		jsonObj.put("classNameId", model.getClassNameId());
		jsonObj.put("classPK", model.getClassPK());
		jsonObj.put("data", model.getData());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoValue[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.expando.model.ExpandoValue[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.expando.model.ExpandoValue> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ExpandoValue model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}