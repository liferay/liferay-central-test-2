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

package com.liferay.portlet.documentlibrary.model;

import com.liferay.portal.model.BaseModel;

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPK;

import java.util.Date;

/**
 * <a href="DLFileEntryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>DLFileEntry</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.model.DLFileEntry
 * @see com.liferay.portlet.documentlibrary.service.model.impl.DLFileEntryImpl
 * @see com.liferay.portlet.documentlibrary.service.model.impl.DLFileEntryModelImpl
 *
 */
public interface DLFileEntryModel extends BaseModel {
	public DLFileEntryPK getPrimaryKey();

	public void setPrimaryKey(DLFileEntryPK pk);

	public String getFolderId();

	public void setFolderId(String folderId);

	public String getName();

	public void setName(String name);

	public String getCompanyId();

	public void setCompanyId(String companyId);

	public String getUserId();

	public void setUserId(String userId);

	public String getUserName();

	public void setUserName(String userName);

	public String getVersionUserId();

	public void setVersionUserId(String versionUserId);

	public String getVersionUserName();

	public void setVersionUserName(String versionUserName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getTitle();

	public void setTitle(String title);

	public String getDescription();

	public void setDescription(String description);

	public double getVersion();

	public void setVersion(double version);

	public int getSize();

	public void setSize(int size);

	public int getReadCount();

	public void setReadCount(int readCount);

	public String getExtraSettings();

	public void setExtraSettings(String extraSettings);
}