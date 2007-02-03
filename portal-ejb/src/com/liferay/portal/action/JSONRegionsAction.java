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

package com.liferay.portal.action;

import com.liferay.portal.model.Region;
import com.liferay.portal.service.RegionServiceUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.util.ParamUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="JSONRegionsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ming-Gih Lam
 *
 */
public class JSONRegionsAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		boolean nullable = ParamUtil.getBoolean(req, "nullable");
		String countryId = ParamUtil.getString(req, "sourceValue");
		List regions = RegionServiceUtil.getRegions(countryId, true);

		JSONObject jsonObj = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		if (nullable) {
			JSONObject option = new JSONObject();

			option.put("name", "");
			option.put("value", "");

			jsonArray.put(option);
		}

		for (int i = 0; i < regions.size(); i++) {
			JSONObject option = new JSONObject();

			Region region = (Region)regions.get(i);

			option.put("name", region.getName());
			option.put("value", region.getRegionId());

			jsonArray.put(option);
		}

		jsonObj.put("options", jsonArray);

		return jsonObj.toString();
	}

}