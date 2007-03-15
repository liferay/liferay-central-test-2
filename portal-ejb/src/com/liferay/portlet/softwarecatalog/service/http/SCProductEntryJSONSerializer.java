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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="SCProductEntryJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.softwarecatalog.service.http.SCProductEntryServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.service.http.SCProductEntryServiceJSON
 *
 */
public class SCProductEntryJSONSerializer {
	public static JSONObject toJSONObject(SCProductEntry model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("productEntryId", model.getProductEntryId());
		jsonObj.put("groupId", model.getGroupId());

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

		String name = model.getName();

		if (name == null) {
			jsonObj.put("name", StringPool.BLANK);
		}
		else {
			jsonObj.put("name", name.toString());
		}

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		String shortDescription = model.getShortDescription();

		if (shortDescription == null) {
			jsonObj.put("shortDescription", StringPool.BLANK);
		}
		else {
			jsonObj.put("shortDescription", shortDescription.toString());
		}

		String longDescription = model.getLongDescription();

		if (longDescription == null) {
			jsonObj.put("longDescription", StringPool.BLANK);
		}
		else {
			jsonObj.put("longDescription", longDescription.toString());
		}

		String pageURL = model.getPageURL();

		if (pageURL == null) {
			jsonObj.put("pageURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("pageURL", pageURL.toString());
		}

		String repoGroupId = model.getRepoGroupId();

		if (repoGroupId == null) {
			jsonObj.put("repoGroupId", StringPool.BLANK);
		}
		else {
			jsonObj.put("repoGroupId", repoGroupId.toString());
		}

		String repoArtifactId = model.getRepoArtifactId();

		if (repoArtifactId == null) {
			jsonObj.put("repoArtifactId", StringPool.BLANK);
		}
		else {
			jsonObj.put("repoArtifactId", repoArtifactId.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			SCProductEntry model = (SCProductEntry)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}