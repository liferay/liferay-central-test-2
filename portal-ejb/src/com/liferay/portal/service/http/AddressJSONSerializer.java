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
import com.liferay.portal.model.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="AddressJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.AddressServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.AddressServiceJSON
 *
 */
public class AddressJSONSerializer {
	public static JSONObject toJSONObject(Address model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("addressId", model.getAddressId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		jsonObj.put("classNameId", model.getClassNameId());
		jsonObj.put("classPK", model.getClassPK());

		String street1 = model.getStreet1();

		if (street1 == null) {
			jsonObj.put("street1", StringPool.BLANK);
		}
		else {
			jsonObj.put("street1", street1.toString());
		}

		String street2 = model.getStreet2();

		if (street2 == null) {
			jsonObj.put("street2", StringPool.BLANK);
		}
		else {
			jsonObj.put("street2", street2.toString());
		}

		String street3 = model.getStreet3();

		if (street3 == null) {
			jsonObj.put("street3", StringPool.BLANK);
		}
		else {
			jsonObj.put("street3", street3.toString());
		}

		String city = model.getCity();

		if (city == null) {
			jsonObj.put("city", StringPool.BLANK);
		}
		else {
			jsonObj.put("city", city.toString());
		}

		String zip = model.getZip();

		if (zip == null) {
			jsonObj.put("zip", StringPool.BLANK);
		}
		else {
			jsonObj.put("zip", zip.toString());
		}

		jsonObj.put("regionId", model.getRegionId());
		jsonObj.put("countryId", model.getCountryId());
		jsonObj.put("typeId", model.getTypeId());
		jsonObj.put("mailing", model.isMailing());
		jsonObj.put("primary", model.isPrimary());

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