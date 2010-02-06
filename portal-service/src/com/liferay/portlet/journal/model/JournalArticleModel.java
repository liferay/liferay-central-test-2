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

import com.liferay.portal.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="JournalArticleModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalArticle table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticle
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleImpl
 * @see       com.liferay.portlet.journal.model.impl.JournalArticleModelImpl
 * @generated
 */
public interface JournalArticleModel extends BaseModel<JournalArticle> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getId();

	public void setId(long id);

	public long getResourcePrimKey();

	public void setResourcePrimKey(long resourcePrimKey);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getArticleId();

	public void setArticleId(String articleId);

	public double getVersion();

	public void setVersion(double version);

	public String getTitle();

	public void setTitle(String title);

	public String getUrlTitle();

	public void setUrlTitle(String urlTitle);

	public String getDescription();

	public void setDescription(String description);

	public String getContent();

	public void setContent(String content);

	public String getType();

	public void setType(String type);

	public String getStructureId();

	public void setStructureId(String structureId);

	public String getTemplateId();

	public void setTemplateId(String templateId);

	public Date getDisplayDate();

	public void setDisplayDate(Date displayDate);

	public int getStatus();

	public void setStatus(int status);

	public long getStatusByUserId();

	public void setStatusByUserId(long statusByUserId);

	public String getStatusByUserUuid() throws SystemException;

	public void setStatusByUserUuid(String statusByUserUuid);

	public String getStatusByUserName();

	public void setStatusByUserName(String statusByUserName);

	public Date getStatusDate();

	public void setStatusDate(Date statusDate);

	public Date getExpirationDate();

	public void setExpirationDate(Date expirationDate);

	public Date getReviewDate();

	public void setReviewDate(Date reviewDate);

	public boolean getIndexable();

	public boolean isIndexable();

	public void setIndexable(boolean indexable);

	public boolean getSmallImage();

	public boolean isSmallImage();

	public void setSmallImage(boolean smallImage);

	public long getSmallImageId();

	public void setSmallImageId(long smallImageId);

	public String getSmallImageURL();

	public void setSmallImageURL(String smallImageURL);

	public JournalArticle toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(JournalArticle journalArticle);

	public int hashCode();

	public String toString();

	public String toXmlString();
}