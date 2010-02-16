/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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


/**
 * <a href="JournalTemplateSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link JournalTemplate}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalTemplate
 * @generated
 */
public class JournalTemplateWrapper implements JournalTemplate {
	public JournalTemplateWrapper(JournalTemplate journalTemplate) {
		_journalTemplate = journalTemplate;
	}

	public long getPrimaryKey() {
		return _journalTemplate.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_journalTemplate.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _journalTemplate.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_journalTemplate.setUuid(uuid);
	}

	public long getId() {
		return _journalTemplate.getId();
	}

	public void setId(long id) {
		_journalTemplate.setId(id);
	}

	public long getGroupId() {
		return _journalTemplate.getGroupId();
	}

	public void setGroupId(long groupId) {
		_journalTemplate.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _journalTemplate.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_journalTemplate.setCompanyId(companyId);
	}

	public long getUserId() {
		return _journalTemplate.getUserId();
	}

	public void setUserId(long userId) {
		_journalTemplate.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_journalTemplate.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _journalTemplate.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_journalTemplate.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _journalTemplate.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_journalTemplate.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _journalTemplate.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalTemplate.setModifiedDate(modifiedDate);
	}

	public java.lang.String getTemplateId() {
		return _journalTemplate.getTemplateId();
	}

	public void setTemplateId(java.lang.String templateId) {
		_journalTemplate.setTemplateId(templateId);
	}

	public java.lang.String getStructureId() {
		return _journalTemplate.getStructureId();
	}

	public void setStructureId(java.lang.String structureId) {
		_journalTemplate.setStructureId(structureId);
	}

	public java.lang.String getName() {
		return _journalTemplate.getName();
	}

	public void setName(java.lang.String name) {
		_journalTemplate.setName(name);
	}

	public java.lang.String getDescription() {
		return _journalTemplate.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_journalTemplate.setDescription(description);
	}

	public java.lang.String getXsl() {
		return _journalTemplate.getXsl();
	}

	public void setXsl(java.lang.String xsl) {
		_journalTemplate.setXsl(xsl);
	}

	public java.lang.String getLangType() {
		return _journalTemplate.getLangType();
	}

	public void setLangType(java.lang.String langType) {
		_journalTemplate.setLangType(langType);
	}

	public boolean getCacheable() {
		return _journalTemplate.getCacheable();
	}

	public boolean isCacheable() {
		return _journalTemplate.isCacheable();
	}

	public void setCacheable(boolean cacheable) {
		_journalTemplate.setCacheable(cacheable);
	}

	public boolean getSmallImage() {
		return _journalTemplate.getSmallImage();
	}

	public boolean isSmallImage() {
		return _journalTemplate.isSmallImage();
	}

	public void setSmallImage(boolean smallImage) {
		_journalTemplate.setSmallImage(smallImage);
	}

	public long getSmallImageId() {
		return _journalTemplate.getSmallImageId();
	}

	public void setSmallImageId(long smallImageId) {
		_journalTemplate.setSmallImageId(smallImageId);
	}

	public java.lang.String getSmallImageURL() {
		return _journalTemplate.getSmallImageURL();
	}

	public void setSmallImageURL(java.lang.String smallImageURL) {
		_journalTemplate.setSmallImageURL(smallImageURL);
	}

	public com.liferay.portlet.journal.model.JournalTemplate toEscapedModel() {
		return _journalTemplate.toEscapedModel();
	}

	public boolean isNew() {
		return _journalTemplate.isNew();
	}

	public boolean setNew(boolean n) {
		return _journalTemplate.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalTemplate.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalTemplate.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalTemplate.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalTemplate.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalTemplate.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalTemplate.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _journalTemplate.clone();
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate) {
		return _journalTemplate.compareTo(journalTemplate);
	}

	public int hashCode() {
		return _journalTemplate.hashCode();
	}

	public java.lang.String toString() {
		return _journalTemplate.toString();
	}

	public java.lang.String toXmlString() {
		return _journalTemplate.toXmlString();
	}

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalTemplate.getSmallImageType();
	}

	public void setSmallImageType(java.lang.String smallImageType) {
		_journalTemplate.setSmallImageType(smallImageType);
	}

	public JournalTemplate getWrappedJournalTemplate() {
		return _journalTemplate;
	}

	private JournalTemplate _journalTemplate;
}