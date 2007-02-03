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

package com.liferay.portlet.softwarerepository.service.http;

import com.liferay.portlet.softwarerepository.model.SRProductEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="SRProductEntryJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SRProductEntryJSONSerializer {
	public static JSONObject toJSONObject(SRProductEntry model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("productEntryId", model.getProductEntryId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("name", model.getName().toString());
		jsonObj.put("type", model.getType().toString());
		jsonObj.put("shortDescription", model.getShortDescription().toString());
		jsonObj.put("longDescription", model.getLongDescription().toString());
		jsonObj.put("pageURL", model.getPageURL().toString());
		jsonObj.put("repoGroupId", model.getRepoGroupId().toString());
		jsonObj.put("repoArtifactId", model.getRepoArtifactId().toString());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			SRProductEntry model = (SRProductEntry)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}