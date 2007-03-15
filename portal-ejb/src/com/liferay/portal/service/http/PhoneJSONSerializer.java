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
import com.liferay.portal.model.Phone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="PhoneJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.PhoneServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.PhoneServiceJSON
 *
 */
public class PhoneJSONSerializer {
	public static JSONObject toJSONObject(Phone model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("phoneId", model.getPhoneId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String userId = model.getUserId();

		if (userId == null) {
			jsonObj.put("userId", StringPool.BLANK);
		}
		else {
			jsonObj.put("userId", userId.toString());
		}

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

		String className = model.getClassName();

		if (className == null) {
			jsonObj.put("className", StringPool.BLANK);
		}
		else {
			jsonObj.put("className", className.toString());
		}

		String classPK = model.getClassPK();

		if (classPK == null) {
			jsonObj.put("classPK", StringPool.BLANK);
		}
		else {
			jsonObj.put("classPK", classPK.toString());
		}

		String number = model.getNumber();

		if (number == null) {
			jsonObj.put("number", StringPool.BLANK);
		}
		else {
			jsonObj.put("number", number.toString());
		}

		String extension = model.getExtension();

		if (extension == null) {
			jsonObj.put("extension", StringPool.BLANK);
		}
		else {
			jsonObj.put("extension", extension.toString());
		}

		jsonObj.put("typeId", model.getTypeId());
		jsonObj.put("primary", model.getPrimary());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Phone model = (Phone)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}