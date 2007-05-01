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
import com.liferay.portal.model.Role;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="RoleJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.RoleServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.RoleServiceJSON
 *
 */
public class RoleJSONSerializer {
	public static JSONObject toJSONObject(Role model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("roleId", model.getRoleId());
		jsonObj.put("companyId", model.getCompanyId());

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

		jsonObj.put("type", model.getType());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Role model = (Role)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}