/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.service.TagsAssetServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="TagsAssetServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class TagsAssetServiceJSON {
	public static void deleteAsset(long assetId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		TagsAssetServiceUtil.deleteAsset(assetId);
	}

	public static JSONObject getAsset(long assetId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException, java.rmi.RemoteException {
		com.liferay.portlet.tags.model.TagsAsset returnValue = TagsAssetServiceUtil.getAsset(assetId);

		return _toJSONObject(returnValue);
	}

	private static JSONObject _toJSONObject(TagsAsset model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("assetId", model.getAssetId());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("className", model.getClassName().toString());
		jsonObj.put("classPK", model.getClassPK().toString());
		jsonObj.put("startDate", model.getStartDate().toString());
		jsonObj.put("endDate", model.getEndDate().toString());
		jsonObj.put("publishDate", model.getPublishDate().toString());
		jsonObj.put("expirationDate", model.getExpirationDate().toString());
		jsonObj.put("mimeType", model.getMimeType().toString());
		jsonObj.put("title", model.getTitle().toString());
		jsonObj.put("url", model.getUrl().toString());
		jsonObj.put("height", model.getHeight());
		jsonObj.put("width", model.getWidth());

		return jsonObj;
	}

	private static JSONArray _toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			TagsAsset model = (TagsAsset)models.get(i);
			jsonArray.put(_toJSONObject(model));
		}

		return jsonArray;
	}
}