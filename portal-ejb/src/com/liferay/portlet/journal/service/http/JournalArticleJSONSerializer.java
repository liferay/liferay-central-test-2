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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.journal.model.JournalArticle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="JournalArticleJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.journal.service.http.JournalArticleServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.http.JournalArticleServiceJSON
 *
 */
public class JournalArticleJSONSerializer {
	public static JSONObject toJSONObject(JournalArticle model) {
		JSONObject jsonObj = new JSONObject();
		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		jsonObj.put("groupId", model.getGroupId());

		String articleId = model.getArticleId();

		if (articleId == null) {
			jsonObj.put("articleId", StringPool.BLANK);
		}
		else {
			jsonObj.put("articleId", articleId.toString());
		}

		jsonObj.put("version", model.getVersion());
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

		String title = model.getTitle();

		if (title == null) {
			jsonObj.put("title", StringPool.BLANK);
		}
		else {
			jsonObj.put("title", title.toString());
		}

		String description = model.getDescription();

		if (description == null) {
			jsonObj.put("description", StringPool.BLANK);
		}
		else {
			jsonObj.put("description", description.toString());
		}

		String content = model.getContent();

		if (content == null) {
			jsonObj.put("content", StringPool.BLANK);
		}
		else {
			jsonObj.put("content", content.toString());
		}

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		String structureId = model.getStructureId();

		if (structureId == null) {
			jsonObj.put("structureId", StringPool.BLANK);
		}
		else {
			jsonObj.put("structureId", structureId.toString());
		}

		String templateId = model.getTemplateId();

		if (templateId == null) {
			jsonObj.put("templateId", StringPool.BLANK);
		}
		else {
			jsonObj.put("templateId", templateId.toString());
		}

		Date displayDate = model.getDisplayDate();

		if (displayDate == null) {
			jsonObj.put("displayDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("displayDate", displayDate.toString());
		}

		jsonObj.put("approved", model.getApproved());
		jsonObj.put("approvedByUserId", model.getApprovedByUserId());

		String approvedByUserName = model.getApprovedByUserName();

		if (approvedByUserName == null) {
			jsonObj.put("approvedByUserName", StringPool.BLANK);
		}
		else {
			jsonObj.put("approvedByUserName", approvedByUserName.toString());
		}

		Date approvedDate = model.getApprovedDate();

		if (approvedDate == null) {
			jsonObj.put("approvedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("approvedDate", approvedDate.toString());
		}

		jsonObj.put("expired", model.getExpired());

		Date expirationDate = model.getExpirationDate();

		if (expirationDate == null) {
			jsonObj.put("expirationDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("expirationDate", expirationDate.toString());
		}

		Date reviewDate = model.getReviewDate();

		if (reviewDate == null) {
			jsonObj.put("reviewDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("reviewDate", reviewDate.toString());
		}

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