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

import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="JournalTemplateSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.journal.service.http.JournalTemplateServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.http.JournalTemplateServiceSoap
 *
 */
public class JournalTemplateSoap implements Serializable {
	public static JournalTemplateSoap toSoapModel(JournalTemplate model) {
		JournalTemplateSoap soapModel = new JournalTemplateSoap();
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setXsl(model.getXsl());
		soapModel.setLangType(model.getLangType());
		soapModel.setSmallImage(model.getSmallImage());
		soapModel.setSmallImageURL(model.getSmallImageURL());

		return soapModel;
	}

	public static JournalTemplateSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			JournalTemplate model = (JournalTemplate)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (JournalTemplateSoap[])soapModels.toArray(new JournalTemplateSoap[0]);
	}

	public JournalTemplateSoap() {
	}

	public JournalTemplatePK getPrimaryKey() {
		return new JournalTemplatePK(_companyId, _groupId, _templateId);
	}

	public void setPrimaryKey(JournalTemplatePK pk) {
		setCompanyId(pk.companyId);
		setGroupId(pk.groupId);
		setTemplateId(pk.templateId);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
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

	public String getStructureId() {
		return _structureId;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getXsl() {
		return _xsl;
	}

	public void setXsl(String xsl) {
		_xsl = xsl;
	}

	public String getLangType() {
		return _langType;
	}

	public void setLangType(String langType) {
		_langType = langType;
	}

	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		_smallImage = smallImage;
	}

	public String getSmallImageURL() {
		return _smallImageURL;
	}

	public void setSmallImageURL(String smallImageURL) {
		_smallImageURL = smallImageURL;
	}

	private long _companyId;
	private long _groupId;
	private String _templateId;
	private long _userId;
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