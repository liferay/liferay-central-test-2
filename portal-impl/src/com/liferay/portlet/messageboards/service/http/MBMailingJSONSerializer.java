/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.messageboards.model.MBMailing;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.messageboards.service.http.MBMailingServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.http.MBMailingServiceJSON
 *
 */
public class MBMailingJSONSerializer {
	public static JSONObject toJSONObject(MBMailing model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("mailingId", model.getMailingId());
		jsonObj.put("groupId", model.getGroupId());
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
		jsonObj.put("categoryId", model.getCategoryId());
		jsonObj.put("mailingListAddress", model.getMailingListAddress());
		jsonObj.put("mailAddress", model.getMailAddress());
		jsonObj.put("mailInProtocol", model.getMailInProtocol());
		jsonObj.put("mailInServerName", model.getMailInServerName());
		jsonObj.put("mailInUseSSL", model.getMailInUseSSL());
		jsonObj.put("mailInServerPort", model.getMailInServerPort());
		jsonObj.put("mailInUserName", model.getMailInUserName());
		jsonObj.put("mailInPassword", model.getMailInPassword());
		jsonObj.put("mailInReadInterval", model.getMailInReadInterval());
		jsonObj.put("mailOutConfigured", model.getMailOutConfigured());
		jsonObj.put("mailOutServerName", model.getMailOutServerName());
		jsonObj.put("mailOutUseSSL", model.getMailOutUseSSL());
		jsonObj.put("mailOutServerPort", model.getMailOutServerPort());
		jsonObj.put("mailOutUserName", model.getMailOutUserName());
		jsonObj.put("mailOutPassword", model.getMailOutPassword());
		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.messageboards.model.MBMailing> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (MBMailing model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}