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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.shopping.model.ShoppingItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingItemJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.shopping.service.http.ShoppingItemServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.service.http.ShoppingItemServiceJSON
 *
 */
public class ShoppingItemJSONSerializer {
	public static JSONObject toJSONObject(ShoppingItem model) {
		JSONObject jsonObj = new JSONObject();
		String itemId = model.getItemId();

		if (itemId == null) {
			jsonObj.put("itemId", StringPool.BLANK);
		}
		else {
			jsonObj.put("itemId", itemId.toString());
		}

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

		String categoryId = model.getCategoryId();

		if (categoryId == null) {
			jsonObj.put("categoryId", StringPool.BLANK);
		}
		else {
			jsonObj.put("categoryId", categoryId.toString());
		}

		String sku = model.getSku();

		if (sku == null) {
			jsonObj.put("sku", StringPool.BLANK);
		}
		else {
			jsonObj.put("sku", sku.toString());
		}

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String description = model.getDescription();

		if (description == null) {
			jsonObj.put("description", StringPool.BLANK);
		}
		else {
			jsonObj.put("description", description.toString());
		}

		String properties = model.getProperties();

		if (properties == null) {
			jsonObj.put("properties", StringPool.BLANK);
		}
		else {
			jsonObj.put("properties", properties.toString());
		}

		jsonObj.put("fields", model.isFields());

		String fieldsQuantities = model.getFieldsQuantities();

		if (fieldsQuantities == null) {
			jsonObj.put("fieldsQuantities", StringPool.BLANK);
		}
		else {
			jsonObj.put("fieldsQuantities", fieldsQuantities.toString());
		}

		jsonObj.put("minQuantity", model.getMinQuantity());
		jsonObj.put("maxQuantity", model.getMaxQuantity());
		jsonObj.put("price", model.getPrice());
		jsonObj.put("discount", model.getDiscount());
		jsonObj.put("taxable", model.isTaxable());
		jsonObj.put("shipping", model.getShipping());
		jsonObj.put("useShippingFormula", model.isUseShippingFormula());
		jsonObj.put("requiresShipping", model.isRequiresShipping());
		jsonObj.put("stockQuantity", model.getStockQuantity());
		jsonObj.put("featured", model.isFeatured());
		jsonObj.put("sale", model.isSale());
		jsonObj.put("smallImage", model.isSmallImage());

		String smallImageURL = model.getSmallImageURL();

		if (smallImageURL == null) {
			jsonObj.put("smallImageURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("smallImageURL", smallImageURL.toString());
		}

		jsonObj.put("mediumImage", model.isMediumImage());

		String mediumImageURL = model.getMediumImageURL();

		if (mediumImageURL == null) {
			jsonObj.put("mediumImageURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("mediumImageURL", mediumImageURL.toString());
		}

		jsonObj.put("largeImage", model.isLargeImage());

		String largeImageURL = model.getLargeImageURL();

		if (largeImageURL == null) {
			jsonObj.put("largeImageURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("largeImageURL", largeImageURL.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			ShoppingItem model = (ShoppingItem)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}