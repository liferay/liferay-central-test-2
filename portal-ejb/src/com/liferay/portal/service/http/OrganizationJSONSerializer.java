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
import com.liferay.portal.model.Organization;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="OrganizationJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationJSONSerializer {
	public static JSONObject toJSONObject(Organization model) {
		JSONObject jsonObj = new JSONObject();
		String organizationId = model.getOrganizationId();

		if (organizationId == null) {
			jsonObj.put("organizationId", StringPool.BLANK);
		}
		else {
			jsonObj.put("organizationId", organizationId.toString());
		}

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String parentOrganizationId = model.getParentOrganizationId();

		if (parentOrganizationId == null) {
			jsonObj.put("parentOrganizationId", StringPool.BLANK);
		}
		else {
			jsonObj.put("parentOrganizationId", parentOrganizationId.toString());
		}

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		jsonObj.put("recursable", model.getRecursable());

		String regionId = model.getRegionId();

		if (regionId == null) {
			jsonObj.put("regionId", StringPool.BLANK);
		}
		else {
			jsonObj.put("regionId", regionId.toString());
		}

		String countryId = model.getCountryId();

		if (countryId == null) {
			jsonObj.put("countryId", StringPool.BLANK);
		}
		else {
			jsonObj.put("countryId", countryId.toString());
		}

		jsonObj.put("statusId", model.getStatusId());

		String comments = model.getComments();

		if (comments == null) {
			jsonObj.put("comments", StringPool.BLANK);
		}
		else {
			jsonObj.put("comments", comments.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			Organization model = (Organization)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}