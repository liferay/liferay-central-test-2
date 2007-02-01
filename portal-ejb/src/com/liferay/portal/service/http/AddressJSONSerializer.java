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

import com.liferay.portal.model.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="AddressJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressJSONSerializer {
	public static JSONObject toJSONObject(Address model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("addressId", model.getAddressId());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("className", model.getClassName().toString());
		jsonObj.put("classPK", model.getClassPK().toString());
		jsonObj.put("street1", model.getStreet1().toString());
		jsonObj.put("street2", model.getStreet2().toString());
		jsonObj.put("street3", model.getStreet3().toString());
		jsonObj.put("city", model.getCity().toString());
		jsonObj.put("zip", model.getZip().toString());
		jsonObj.put("regionId", model.getRegionId().toString());
		jsonObj.put("countryId", model.getCountryId().toString());
		jsonObj.put("typeId", model.getTypeId());
		jsonObj.put("mailing", model.getMailing());
		jsonObj.put("primary", model.getPrimary());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Address model = (Address)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}