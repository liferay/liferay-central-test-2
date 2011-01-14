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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.shopping.model.ShoppingItem;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ShoppingItemJSONSerializer {
	public static JSONObject toJSONObject(ShoppingItem model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("itemId", model.getItemId());
		jsonObject.put("groupId", model.getGroupId());
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
		jsonObject.put("categoryId", model.getCategoryId());
		jsonObject.put("sku", model.getSku());
		jsonObject.put("name", model.getName());
		jsonObject.put("description", model.getDescription());
		jsonObject.put("properties", model.getProperties());
		jsonObject.put("fields", model.getFields());
		jsonObject.put("fieldsQuantities", model.getFieldsQuantities());
		jsonObject.put("minQuantity", model.getMinQuantity());
		jsonObject.put("maxQuantity", model.getMaxQuantity());
		jsonObject.put("price", model.getPrice());
		jsonObject.put("discount", model.getDiscount());
		jsonObject.put("taxable", model.getTaxable());
		jsonObject.put("shipping", model.getShipping());
		jsonObject.put("useShippingFormula", model.getUseShippingFormula());
		jsonObject.put("requiresShipping", model.getRequiresShipping());
		jsonObject.put("stockQuantity", model.getStockQuantity());
		jsonObject.put("featured", model.getFeatured());
		jsonObject.put("sale", model.getSale());
		jsonObject.put("smallImage", model.getSmallImage());
		jsonObject.put("smallImageId", model.getSmallImageId());
		jsonObject.put("smallImageURL", model.getSmallImageURL());
		jsonObject.put("mediumImage", model.getMediumImage());
		jsonObject.put("mediumImageId", model.getMediumImageId());
		jsonObject.put("mediumImageURL", model.getMediumImageURL());
		jsonObject.put("largeImage", model.getLargeImage());
		jsonObject.put("largeImageId", model.getLargeImageId());
		jsonObject.put("largeImageURL", model.getLargeImageURL());

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.shopping.model.ShoppingItem[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingItem model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.shopping.model.ShoppingItem[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingItem[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.shopping.model.ShoppingItem> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingItem model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}