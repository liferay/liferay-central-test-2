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
import com.liferay.portal.model.Account;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="AccountJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.AccountServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.AccountServiceJSON
 *
 */
public class AccountJSONSerializer {
	public static JSONObject toJSONObject(Account model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("accountId", model.getAccountId());
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

		jsonObj.put("parentAccountId", model.getParentAccountId());

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String legalName = model.getLegalName();

		if (legalName == null) {
			jsonObj.put("legalName", StringPool.BLANK);
		}
		else {
			jsonObj.put("legalName", legalName.toString());
		}

		String legalId = model.getLegalId();

		if (legalId == null) {
			jsonObj.put("legalId", StringPool.BLANK);
		}
		else {
			jsonObj.put("legalId", legalId.toString());
		}

		String legalType = model.getLegalType();

		if (legalType == null) {
			jsonObj.put("legalType", StringPool.BLANK);
		}
		else {
			jsonObj.put("legalType", legalType.toString());
		}

		String sicCode = model.getSicCode();

		if (sicCode == null) {
			jsonObj.put("sicCode", StringPool.BLANK);
		}
		else {
			jsonObj.put("sicCode", sicCode.toString());
		}

		String tickerSymbol = model.getTickerSymbol();

		if (tickerSymbol == null) {
			jsonObj.put("tickerSymbol", StringPool.BLANK);
		}
		else {
			jsonObj.put("tickerSymbol", tickerSymbol.toString());
		}

		String industry = model.getIndustry();

		if (industry == null) {
			jsonObj.put("industry", StringPool.BLANK);
		}
		else {
			jsonObj.put("industry", industry.toString());
		}

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		String size = model.getSize();

		if (size == null) {
			jsonObj.put("size", StringPool.BLANK);
		}
		else {
			jsonObj.put("size", size.toString());
		}

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