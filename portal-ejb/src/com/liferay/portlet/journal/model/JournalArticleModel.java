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

package com.liferay.portlet.journal.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.journal.service.persistence.JournalArticlePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="JournalArticleModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalArticleModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.journal.model.JournalArticle"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ARTICLEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.articleId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TITLE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.title"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CONTENT = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.content"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.type"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STRUCTUREID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.structureId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TEMPLATEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.templateId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_APPROVEDBYUSERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.approvedByUserId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_APPROVEDBYUSERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalArticle.approvedByUserName"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalArticleModel"));

	public JournalArticleModel() {
	}

	public JournalArticlePK getPrimaryKey() {
		return new JournalArticlePK(_companyId, _articleId, _version);
	}

	public void setPrimaryKey(JournalArticlePK pk) {
		setCompanyId(pk.companyId);
		setArticleId(pk.articleId);
		setVersion(pk.version);
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		if (((articleId == null) && (_articleId != null)) ||
				((articleId != null) && (_articleId == null)) ||
				((articleId != null) && (_articleId != null) &&
				!articleId.equals(_articleId))) {
			if (!XSS_ALLOW_ARTICLEID) {
				articleId = XSSUtil.strip(articleId);
			}

			_articleId = articleId;
		}
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		if (((title == null) && (_title != null)) ||
				((title != null) && (_title == null)) ||
				((title != null) && (_title != null) && !title.equals(_title))) {
			if (!XSS_ALLOW_TITLE) {
				title = XSSUtil.strip(title);
			}

			_title = title;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public String getContent() {
		return GetterUtil.getString(_content);
	}

	public void setContent(String content) {
		if (((content == null) && (_content != null)) ||
				((content != null) && (_content == null)) ||
				((content != null) && (_content != null) &&
				!content.equals(_content))) {
			if (!XSS_ALLOW_CONTENT) {
				content = XSSUtil.strip(content);
			}

			_content = content;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			if (!XSS_ALLOW_TYPE) {
				type = XSSUtil.strip(type);
			}

			_type = type;
		}
	}

	public String getStructureId() {
		return GetterUtil.getString(_structureId);
	}

	public void setStructureId(String structureId) {
		if (((structureId == null) && (_structureId != null)) ||
				((structureId != null) && (_structureId == null)) ||
				((structureId != null) && (_structureId != null) &&
				!structureId.equals(_structureId))) {
			if (!XSS_ALLOW_STRUCTUREID) {
				structureId = XSSUtil.strip(structureId);
			}

			_structureId = structureId;
		}
	}

	public String getTemplateId() {
		return GetterUtil.getString(_templateId);
	}

	public void setTemplateId(String templateId) {
		if (((templateId == null) && (_templateId != null)) ||
				((templateId != null) && (_templateId == null)) ||
				((templateId != null) && (_templateId != null) &&
				!templateId.equals(_templateId))) {
			if (!XSS_ALLOW_TEMPLATEID) {
				templateId = XSSUtil.strip(templateId);
			}

			_templateId = templateId;
		}
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		if (((displayDate == null) && (_displayDate != null)) ||
				((displayDate != null) && (_displayDate == null)) ||
				((displayDate != null) && (_displayDate != null) &&
				!displayDate.equals(_displayDate))) {
			_displayDate = displayDate;
		}
	}

	public boolean getApproved() {
		return _approved;
	}

	public boolean isApproved() {
		return _approved;
	}

	public void setApproved(boolean approved) {
		if (approved != _approved) {
			_approved = approved;
		}
	}

	public String getApprovedByUserId() {
		return GetterUtil.getString(_approvedByUserId);
	}

	public void setApprovedByUserId(String approvedByUserId) {
		if (((approvedByUserId == null) && (_approvedByUserId != null)) ||
				((approvedByUserId != null) && (_approvedByUserId == null)) ||
				((approvedByUserId != null) && (_approvedByUserId != null) &&
				!approvedByUserId.equals(_approvedByUserId))) {
			if (!XSS_ALLOW_APPROVEDBYUSERID) {
				approvedByUserId = XSSUtil.strip(approvedByUserId);
			}

			_approvedByUserId = approvedByUserId;
		}
	}

	public String getApprovedByUserName() {
		return GetterUtil.getString(_approvedByUserName);
	}

	public void setApprovedByUserName(String approvedByUserName) {
		if (((approvedByUserName == null) && (_approvedByUserName != null)) ||
				((approvedByUserName != null) && (_approvedByUserName == null)) ||
				((approvedByUserName != null) && (_approvedByUserName != null) &&
				!approvedByUserName.equals(_approvedByUserName))) {
			if (!XSS_ALLOW_APPROVEDBYUSERNAME) {
				approvedByUserName = XSSUtil.strip(approvedByUserName);
			}

			_approvedByUserName = approvedByUserName;
		}
	}

	public Date getApprovedDate() {
		return _approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		if (((approvedDate == null) && (_approvedDate != null)) ||
				((approvedDate != null) && (_approvedDate == null)) ||
				((approvedDate != null) && (_approvedDate != null) &&
				!approvedDate.equals(_approvedDate))) {
			_approvedDate = approvedDate;
		}
	}

	public boolean getExpired() {
		return _expired;
	}

	public boolean isExpired() {
		return _expired;
	}

	public void setExpired(boolean expired) {
		if (expired != _expired) {
			_expired = expired;
		}
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		if (((expirationDate == null) && (_expirationDate != null)) ||
				((expirationDate != null) && (_expirationDate == null)) ||
				((expirationDate != null) && (_expirationDate != null) &&
				!expirationDate.equals(_expirationDate))) {
			_expirationDate = expirationDate;
		}
	}

	public Date getReviewDate() {
		return _reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		if (((reviewDate == null) && (_reviewDate != null)) ||
				((reviewDate != null) && (_reviewDate == null)) ||
				((reviewDate != null) && (_reviewDate != null) &&
				!reviewDate.equals(_reviewDate))) {
			_reviewDate = reviewDate;
		}
	}

	public Object clone() {
		JournalArticle clone = new JournalArticle();
		clone.setCompanyId(getCompanyId());
		clone.setArticleId(getArticleId());
		clone.setVersion(getVersion());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setContent(getContent());
		clone.setType(getType());
		clone.setStructureId(getStructureId());
		clone.setTemplateId(getTemplateId());
		clone.setDisplayDate(getDisplayDate());
		clone.setApproved(getApproved());
		clone.setApprovedByUserId(getApprovedByUserId());
		clone.setApprovedByUserName(getApprovedByUserName());
		clone.setApprovedDate(getApprovedDate());
		clone.setExpired(getExpired());
		clone.setExpirationDate(getExpirationDate());
		clone.setReviewDate(getReviewDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalArticle journalArticle = (JournalArticle)obj;
		int value = 0;
		value = getArticleId().compareTo(journalArticle.getArticleId());

		if (value != 0) {
			return value;
		}

		if (getVersion() < journalArticle.getVersion()) {
			value = -1;
		}
		else if (getVersion() > journalArticle.getVersion()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalArticle journalArticle = null;

		try {
			journalArticle = (JournalArticle)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		JournalArticlePK pk = journalArticle.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _companyId;
	private String _articleId;
	private double _version;
	private String _groupId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _title;
	private String _description;
	private String _content;
	private String _type;
	private String _structureId;
	private String _templateId;
	private Date _displayDate;
	private boolean _approved;
	private String _approvedByUserId;
	private String _approvedByUserName;
	private Date _approvedDate;
	private boolean _expired;
	private Date _expirationDate;
	private Date _reviewDate;
}