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
import com.liferay.portal.model.Contact;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ContactJSONSerializer {
	public static JSONObject toJSONObject(Contact model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("contactId", model.getContactId());
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
		jsonObj.put("accountId", model.getAccountId());
		jsonObj.put("parentContactId", model.getParentContactId());
		jsonObj.put("firstName", model.getFirstName());
		jsonObj.put("middleName", model.getMiddleName());
		jsonObj.put("lastName", model.getLastName());
		jsonObj.put("prefixId", model.getPrefixId());
		jsonObj.put("suffixId", model.getSuffixId());
		jsonObj.put("male", model.getMale());

		Date birthday = model.getBirthday();

		String birthdayJSON = StringPool.BLANK;

		if (birthday != null) {
			birthdayJSON = String.valueOf(birthday.getTime());
		}

		jsonObj.put("birthday", birthdayJSON);
		jsonObj.put("smsSn", model.getSmsSn());
		jsonObj.put("aimSn", model.getAimSn());
		jsonObj.put("facebookSn", model.getFacebookSn());
		jsonObj.put("icqSn", model.getIcqSn());
		jsonObj.put("jabberSn", model.getJabberSn());
		jsonObj.put("msnSn", model.getMsnSn());
		jsonObj.put("mySpaceSn", model.getMySpaceSn());
		jsonObj.put("skypeSn", model.getSkypeSn());
		jsonObj.put("twitterSn", model.getTwitterSn());
		jsonObj.put("ymSn", model.getYmSn());
		jsonObj.put("employeeStatusId", model.getEmployeeStatusId());
		jsonObj.put("employeeNumber", model.getEmployeeNumber());
		jsonObj.put("jobTitle", model.getJobTitle());
		jsonObj.put("jobClass", model.getJobClass());
		jsonObj.put("hoursOfOperation", model.getHoursOfOperation());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Contact[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Contact model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portal.model.Contact[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Contact[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portal.model.Contact> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Contact model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}