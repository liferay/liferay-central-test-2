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

package com.liferay.portlet.tags.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.tags.model.TagsAsset;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="TagsAssetJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.tags.service.http.TagsAssetServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.http.TagsAssetServiceJSON
 *
 */
public class TagsAssetJSONSerializer {
	public static JSONObject toJSONObject(TagsAsset model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("assetId", model.getAssetId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

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

		Date startDate = model.getStartDate();

		if (startDate == null) {
			jsonObj.put("startDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("startDate", startDate.toString());
		}

		Date endDate = model.getEndDate();

		if (endDate == null) {
			jsonObj.put("endDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("endDate", endDate.toString());
		}

		Date publishDate = model.getPublishDate();

		if (publishDate == null) {
			jsonObj.put("publishDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("publishDate", publishDate.toString());
		}

		Date expirationDate = model.getExpirationDate();

		if (expirationDate == null) {
			jsonObj.put("expirationDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("expirationDate", expirationDate.toString());
		}

		String mimeType = model.getMimeType();

		if (mimeType == null) {
			jsonObj.put("mimeType", StringPool.BLANK);
		}
		else {
			jsonObj.put("mimeType", mimeType.toString());
		}

		String title = model.getTitle();

		if (title == null) {
			jsonObj.put("title", StringPool.BLANK);
		}
		else {
			jsonObj.put("title", title.toString());
		}

		String url = model.getUrl();

		if (url == null) {
			jsonObj.put("url", StringPool.BLANK);
		}
		else {
			jsonObj.put("url", url.toString());
		}

		jsonObj.put("height", model.getHeight());
		jsonObj.put("width", model.getWidth());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			TagsAsset model = (TagsAsset)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}