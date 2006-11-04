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

import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="JournalTemplateModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.journal.model.JournalTemplate"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TEMPLATEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.templateId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STRUCTUREID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.structureId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_XSL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.xsl"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LANGTYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.langType"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SMALLIMAGEURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.smallImageURL"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalTemplateModel"));

	public JournalTemplateModel() {
	}

	public JournalTemplatePK getPrimaryKey() {
		return new JournalTemplatePK(_companyId, _templateId);
	}

	public void setPrimaryKey(JournalTemplatePK pk) {
		setCompanyId(pk.companyId);
		setTemplateId(pk.templateId);
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

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
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

	public String getXsl() {
		return GetterUtil.getString(_xsl);
	}

	public void setXsl(String xsl) {
		if (((xsl == null) && (_xsl != null)) ||
				((xsl != null) && (_xsl == null)) ||
				((xsl != null) && (_xsl != null) && !xsl.equals(_xsl))) {
			if (!XSS_ALLOW_XSL) {
				xsl = XSSUtil.strip(xsl);
			}

			_xsl = xsl;
		}
	}

	public String getLangType() {
		return GetterUtil.getString(_langType);
	}

	public void setLangType(String langType) {
		if (((langType == null) && (_langType != null)) ||
				((langType != null) && (_langType == null)) ||
				((langType != null) && (_langType != null) &&
				!langType.equals(_langType))) {
			if (!XSS_ALLOW_LANGTYPE) {
				langType = XSSUtil.strip(langType);
			}

			_langType = langType;
		}
	}

	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		if (smallImage != _smallImage) {
			_smallImage = smallImage;
		}
	}

	public String getSmallImageURL() {
		return GetterUtil.getString(_smallImageURL);
	}

	public void setSmallImageURL(String smallImageURL) {
		if (((smallImageURL == null) && (_smallImageURL != null)) ||
				((smallImageURL != null) && (_smallImageURL == null)) ||
				((smallImageURL != null) && (_smallImageURL != null) &&
				!smallImageURL.equals(_smallImageURL))) {
			if (!XSS_ALLOW_SMALLIMAGEURL) {
				smallImageURL = XSSUtil.strip(smallImageURL);
			}

			_smallImageURL = smallImageURL;
		}
	}

	public Object clone() {
		JournalTemplate clone = new JournalTemplate();
		clone.setCompanyId(getCompanyId());
		clone.setTemplateId(getTemplateId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setStructureId(getStructureId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setXsl(getXsl());
		clone.setLangType(getLangType());
		clone.setSmallImage(getSmallImage());
		clone.setSmallImageURL(getSmallImageURL());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalTemplate journalTemplate = (JournalTemplate)obj;
		int value = 0;
		value = getTemplateId().compareTo(journalTemplate.getTemplateId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalTemplate journalTemplate = null;

		try {
			journalTemplate = (JournalTemplate)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		JournalTemplatePK pk = journalTemplate.getPrimaryKey();

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
	private String _templateId;
	private String _groupId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _structureId;
	private String _name;
	private String _description;
	private String _xsl;
	private String _langType;
	private boolean _smallImage;
	private String _smallImageURL;
}