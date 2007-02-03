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

import com.liferay.portlet.shopping.model.ShoppingItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="ShoppingItemJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingItemJSONSerializer {
	public static JSONObject toJSONObject(ShoppingItem model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("itemId", model.getItemId().toString());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("categoryId", model.getCategoryId().toString());
		jsonObj.put("sku", model.getSku().toString());
		jsonObj.put("name", model.getName().toString());
		jsonObj.put("description", model.getDescription().toString());
		jsonObj.put("properties", model.getProperties().toString());
		jsonObj.put("fields", model.getFields());
		jsonObj.put("fieldsQuantities", model.getFieldsQuantities().toString());
		jsonObj.put("minQuantity", model.getMinQuantity());
		jsonObj.put("maxQuantity", model.getMaxQuantity());
		jsonObj.put("price", model.getPrice());
		jsonObj.put("discount", model.getDiscount());
		jsonObj.put("taxable", model.getTaxable());
		jsonObj.put("shipping", model.getShipping());
		jsonObj.put("useShippingFormula", model.getUseShippingFormula());
		jsonObj.put("requiresShipping", model.getRequiresShipping());
		jsonObj.put("stockQuantity", model.getStockQuantity());
		jsonObj.put("featured", model.getFeatured());
		jsonObj.put("sale", model.getSale());
		jsonObj.put("smallImage", model.getSmallImage());
		jsonObj.put("smallImageURL", model.getSmallImageURL().toString());
		jsonObj.put("mediumImage", model.getMediumImage());
		jsonObj.put("mediumImageURL", model.getMediumImageURL().toString());
		jsonObj.put("largeImage", model.getLargeImage());
		jsonObj.put("largeImageURL", model.getLargeImageURL().toString());

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