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
import com.liferay.portal.model.Country;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="CountryJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CountryJSONSerializer {
	public static JSONObject toJSONObject(Country model) {
		JSONObject jsonObj = new JSONObject();
		String countryId = model.getCountryId();

		if (countryId == null) {
			jsonObj.put("countryId", StringPool.BLANK);
		}
		else {
			jsonObj.put("countryId", countryId.toString());
		}

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String a2 = model.getA2();

		if (a2 == null) {
			jsonObj.put("a2", StringPool.BLANK);
		}
		else {
			jsonObj.put("a2", a2.toString());
		}

		String a3 = model.getA3();

		if (a3 == null) {
			jsonObj.put("a3", StringPool.BLANK);
		}
		else {
			jsonObj.put("a3", a3.toString());
		}

		String number = model.getNumber();

		if (number == null) {
			jsonObj.put("number", StringPool.BLANK);
		}
		else {
			jsonObj.put("number", number.toString());
		}

		String idd = model.getIdd();

		if (idd == null) {
			jsonObj.put("idd", StringPool.BLANK);
		}
		else {
			jsonObj.put("idd", idd.toString());
		}

		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Country model = (Country)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}