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

package com.liferay.portlet.journal.model;

import com.liferay.portlet.journal.service.persistence.JournalArticlePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="JournalArticleSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.journal.service.http.JournalArticleServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.http.JournalArticleServiceSoap
 *
 */
public class JournalArticleSoap implements Serializable {
	public static JournalArticleSoap toSoapModel(JournalArticle model) {
		JournalArticleSoap soapModel = new JournalArticleSoap();
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setArticleId(model.getArticleId());
		soapModel.setVersion(model.getVersion());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setContent(model.getContent());
		soapModel.setType(model.getType());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setDisplayDate(model.getDisplayDate());
		soapModel.setApproved(model.getApproved());
		soapModel.setApprovedByUserId(model.getApprovedByUserId());
		soapModel.setApprovedByUserName(model.getApprovedByUserName());
		soapModel.setApprovedDate(model.getApprovedDate());
		soapModel.setExpired(model.getExpired());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setReviewDate(model.getReviewDate());

		return soapModel;
	}

	public static JournalArticleSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			JournalArticle model = (JournalArticle)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (JournalArticleSoap[])soapModels.toArray(new JournalArticleSoap[0]);
	}

	public JournalArticleSoap() {
	}

	public JournalArticlePK getPrimaryKey() {
		return new JournalArticlePK(_companyId, _groupId, _articleId, _version);
	}

	public void setPrimaryKey(JournalArticlePK pk) {
		setCompanyId(pk.companyId);
		setGroupId(pk.groupId);
		setArticleId(pk.articleId);
		setVersion(pk.version);
	}

	public String getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(String companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getArticleId() {
		return _articleId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getStructureId() {
		return _structureId;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public boolean getApproved() {
		return _approved;
	}

	public boolean isApproved() {
		return _approved;
	}

	public void setApproved(boolean approved) {
		_approved = approved;
	}

	public String getApprovedByUserId() {
		return _approvedByUserId;
	}

	public void setApprovedByUserId(String approvedByUserId) {
		_approvedByUserId = approvedByUserId;
	}

	public String getApprovedByUserName() {
		return _approvedByUserName;
	}

	public void setApprovedByUserName(String approvedByUserName) {
		_approvedByUserName = approvedByUserName;
	}

	public Date getApprovedDate() {
		return _approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		_approvedDate = approvedDate;
	}

	public boolean getExpired() {
		return _expired;
	}

	public boolean isExpired() {
		return _expired;
	}

	public void setExpired(boolean expired) {
		_expired = expired;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public Date getReviewDate() {
		return _reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		_reviewDate = reviewDate;
	}

	private String _companyId;
	private long _groupId;
	private String _articleId;
	private double _version;
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