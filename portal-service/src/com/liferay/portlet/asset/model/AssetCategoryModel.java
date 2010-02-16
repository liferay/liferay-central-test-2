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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="AssetCategoryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetCategory table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategory
 * @see       com.liferay.portlet.asset.model.impl.AssetCategoryImpl
 * @see       com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl
 * @generated
 */
public interface AssetCategoryModel extends BaseModel<AssetCategory> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getCategoryId();

	public void setCategoryId(long categoryId);

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

	public long getParentCategoryId();

	public void setParentCategoryId(long parentCategoryId);

	public long getLeftCategoryId();

	public void setLeftCategoryId(long leftCategoryId);

	public long getRightCategoryId();

	public void setRightCategoryId(long rightCategoryId);

	public String getName();

	public void setName(String name);

	public String getTitle();

	public String getTitle(Locale locale);

	public String getTitle(Locale locale, boolean useDefault);

	public String getTitle(String languageId);

	public String getTitle(String languageId, boolean useDefault);

	public Map<Locale, String> getTitleMap();

	public void setTitle(String title);

	public void setTitle(Locale locale, String title);

	public void setTitleMap(Map<Locale, String> titleMap);

	public long getVocabularyId();

	public void setVocabularyId(long vocabularyId);

	public AssetCategory toEscapedModel();

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

	public int compareTo(AssetCategory assetCategory);

	public int hashCode();

	public String toString();

	public String toXmlString();
}