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

package com.liferay.portlet.journal.service.http;

import com.liferay.portlet.journal.model.JournalArticle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="JournalArticleJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleJSONSerializer {
	public static JSONObject toJSONObject(JournalArticle model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("articleId", model.getArticleId().toString());
		jsonObj.put("version", model.getVersion());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("title", model.getTitle().toString());
		jsonObj.put("description", model.getDescription().toString());
		jsonObj.put("content", model.getContent().toString());
		jsonObj.put("type", model.getType().toString());
		jsonObj.put("structureId", model.getStructureId().toString());
		jsonObj.put("templateId", model.getTemplateId().toString());
		jsonObj.put("displayDate", model.getDisplayDate().toString());
		jsonObj.put("approved", model.getApproved());
		jsonObj.put("approvedByUserId", model.getApprovedByUserId().toString());
		jsonObj.put("approvedByUserName",
			model.getApprovedByUserName().toString());
		jsonObj.put("approvedDate", model.getApprovedDate().toString());
		jsonObj.put("expired", model.getExpired());
		jsonObj.put("expirationDate", model.getExpirationDate().toString());
		jsonObj.put("reviewDate", model.getReviewDate().toString());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			JournalArticle model = (JournalArticle)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}