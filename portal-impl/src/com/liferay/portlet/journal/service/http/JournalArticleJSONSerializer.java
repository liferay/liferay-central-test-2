/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.journal.model.JournalArticle;

import java.util.Date;
import java.util.List;

public class JournalArticleJSONSerializer {
	public static JSONObject toJSONObject(JournalArticle model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("id", model.getId());
		jsonObj.put("resourcePrimKey", model.getResourcePrimKey());
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
		jsonObj.put("articleId", model.getArticleId());
		jsonObj.put("version", model.getVersion());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("urlTitle", model.getUrlTitle());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("content", model.getContent());
		jsonObj.put("type", model.getType());
		jsonObj.put("structureId", model.getStructureId());
		jsonObj.put("templateId", model.getTemplateId());

		Date displayDate = model.getDisplayDate();

		String displayDateJSON = StringPool.BLANK;

		if (displayDate != null) {
			displayDateJSON = String.valueOf(displayDate.getTime());
		}

		jsonObj.put("displayDate", displayDateJSON);
		jsonObj.put("approved", model.getApproved());
		jsonObj.put("approvedByUserId", model.getApprovedByUserId());
		jsonObj.put("approvedByUserName", model.getApprovedByUserName());

		Date approvedDate = model.getApprovedDate();

		String approvedDateJSON = StringPool.BLANK;

		if (approvedDate != null) {
			approvedDateJSON = String.valueOf(approvedDate.getTime());
		}

		jsonObj.put("approvedDate", approvedDateJSON);
		jsonObj.put("expired", model.getExpired());

		Date expirationDate = model.getExpirationDate();

		String expirationDateJSON = StringPool.BLANK;

		if (expirationDate != null) {
			expirationDateJSON = String.valueOf(expirationDate.getTime());
		}

		jsonObj.put("expirationDate", expirationDateJSON);

		Date reviewDate = model.getReviewDate();

		String reviewDateJSON = StringPool.BLANK;

		if (reviewDate != null) {
			reviewDateJSON = String.valueOf(reviewDate.getTime());
		}

		jsonObj.put("reviewDate", reviewDateJSON);
		jsonObj.put("indexable", model.getIndexable());
		jsonObj.put("smallImage", model.getSmallImage());
		jsonObj.put("smallImageId", model.getSmallImageId());
		jsonObj.put("smallImageURL", model.getSmallImageURL());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalArticle[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalArticle model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.journal.model.JournalArticle[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalArticle[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.journal.model.JournalArticle> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (JournalArticle model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}