/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("addressId", model.getAddressId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObject.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObject.put("modifiedDate", modifiedDateJSON);
		jsonObject.put("classNameId", model.getClassNameId());
		jsonObject.put("classPK", model.getClassPK());
		jsonObject.put("street1", model.getStreet1());
		jsonObject.put("street2", model.getStreet2());
		jsonObject.put("street3", model.getStreet3());
		jsonObject.put("city", model.getCity());
		jsonObject.put("zip", model.getZip());
		jsonObject.put("regionId", model.getRegionId());
		jsonObject.put("countryId", model.getCountryId());
		jsonObject.put("typeId", model.getTypeId());
		jsonObject.put("mailing", model.getMailing());
		jsonObject.put("primary", model.getPrimary());

		return jsonObject;
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