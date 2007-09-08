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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.GroupServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.GroupServiceJSON
 *
 */
public class GroupJSONSerializer {
	public static JSONObject toJSONObject(Group model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("creatorUserId", model.getCreatorUserId());
		jsonObj.put("classNameId", model.getClassNameId());
		jsonObj.put("classPK", model.getClassPK());
		jsonObj.put("parentGroupId", model.getParentGroupId());
		jsonObj.put("liveGroupId", model.getLiveGroupId());

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

		String typeSettings = model.getTypeSettings();

		if (typeSettings == null) {
			jsonObj.put("typeSettings", StringPool.BLANK);
		}
		else {
			jsonObj.put("typeSettings", typeSettings.toString());
		}

		String friendlyURL = model.getFriendlyURL();

		if (friendlyURL == null) {
			jsonObj.put("friendlyURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("friendlyURL", friendlyURL.toString());
		}

		jsonObj.put("active", model.isActive());

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