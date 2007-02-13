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
import com.liferay.portal.model.Group;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="GroupJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class GroupJSONSerializer {
	public static JSONObject toJSONObject(Group model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("groupId", model.getGroupId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String creatorUserId = model.getCreatorUserId();

		if (creatorUserId == null) {
			jsonObj.put("creatorUserId", StringPool.BLANK);
		}
		else {
			jsonObj.put("creatorUserId", creatorUserId.toString());
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

		jsonObj.put("parentGroupId", model.getParentGroupId());

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

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		String friendlyURL = model.getFriendlyURL();

		if (friendlyURL == null) {
			jsonObj.put("friendlyURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("friendlyURL", friendlyURL.toString());
		}

		jsonObj.put("active", model.getActive());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Group model = (Group)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}