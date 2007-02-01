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

import com.liferay.portal.model.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="AccountJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AccountJSONSerializer {
	public static JSONObject toJSONObject(Account model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("accountId", model.getAccountId().toString());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("parentAccountId", model.getParentAccountId().toString());
		jsonObj.put("name", model.getName().toString());
		jsonObj.put("legalName", model.getLegalName().toString());
		jsonObj.put("legalId", model.getLegalId().toString());
		jsonObj.put("legalType", model.getLegalType().toString());
		jsonObj.put("sicCode", model.getSicCode().toString());
		jsonObj.put("tickerSymbol", model.getTickerSymbol().toString());
		jsonObj.put("industry", model.getIndustry().toString());
		jsonObj.put("type", model.getType().toString());
		jsonObj.put("size", model.getSize().toString());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Account model = (Account)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}