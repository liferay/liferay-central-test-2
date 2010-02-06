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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

import java.util.List;

/**
 * <a href="SCLicenseJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link SCLicenseServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.softwarecatalog.service.http.SCLicenseServiceJSON
 * @generated
 */
public class SCLicenseJSONSerializer {
	public static JSONObject toJSONObject(SCLicense model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("licenseId", model.getLicenseId());
		jsonObj.put("name", model.getName());
		jsonObj.put("url", model.getUrl());
		jsonObj.put("openSource", model.getOpenSource());
		jsonObj.put("active", model.getActive());
		jsonObj.put("recommended", model.getRecommended());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.softwarecatalog.model.SCLicense[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCLicense model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.softwarecatalog.model.SCLicense[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCLicense[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.softwarecatalog.model.SCLicense> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SCLicense model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}