/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Address;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class AddressJSONSerializer {
	public static JSONObject toJSONObject(Address model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("addressId", model.getAddressId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObj.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObj.put("modifiedDate", modifiedDateJSON);
		jsonObj.put("classNameId", model.getClassNameId());
		jsonObj.put("classPK", model.getClassPK());
		jsonObj.put("street1", model.getStreet1());
		jsonObj.put("street2", model.getStreet2());
		jsonObj.put("street3", model.getStreet3());
		jsonObj.put("city", model.getCity());
		jsonObj.put("zip", model.getZip());
		jsonObj.put("regionId", model.getRegionId());
		jsonObj.put("countryId", model.getCountryId());
		jsonObj.put("typeId", model.getTypeId());
		jsonObj.put("mailing", model.getMailing());
		jsonObj.put("primary", model.getPrimary());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Address[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Address model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Address[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Address[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Address> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Address model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}