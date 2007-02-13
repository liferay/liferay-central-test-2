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

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="SCProductVersionJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCProductVersionJSONSerializer {
	public static JSONObject toJSONObject(SCProductVersion model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("productVersionId", model.getProductVersionId());

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

		jsonObj.put("productEntryId", model.getProductEntryId());

		String version = model.getVersion();

		if (version == null) {
			jsonObj.put("version", StringPool.BLANK);
		}
		else {
			jsonObj.put("version", version.toString());
		}

		String changeLog = model.getChangeLog();

		if (changeLog == null) {
			jsonObj.put("changeLog", StringPool.BLANK);
		}
		else {
			jsonObj.put("changeLog", changeLog.toString());
		}

		String downloadPageURL = model.getDownloadPageURL();

		if (downloadPageURL == null) {
			jsonObj.put("downloadPageURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("downloadPageURL", downloadPageURL.toString());
		}

		String directDownloadURL = model.getDirectDownloadURL();

		if (directDownloadURL == null) {
			jsonObj.put("directDownloadURL", StringPool.BLANK);
		}
		else {
			jsonObj.put("directDownloadURL", directDownloadURL.toString());
		}

		jsonObj.put("repoStoreArtifact", model.getRepoStoreArtifact());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			SCProductVersion model = (SCProductVersion)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}