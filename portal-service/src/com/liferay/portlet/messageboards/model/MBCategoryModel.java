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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.BaseModel;

import java.util.Date;

/**
 * <a href="MBCategoryModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>MBCategory</code> table in
 * the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.model.MBCategory
 * @see com.liferay.portlet.messageboards.service.model.impl.MBCategoryImpl
 * @see com.liferay.portlet.messageboards.service.model.impl.MBCategoryModelImpl
 *
 */
public interface MBCategoryModel extends BaseModel {
	public String getPrimaryKey();

	public void setPrimaryKey(String pk);

	public String getCategoryId();

	public void setCategoryId(String categoryId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public String getCompanyId();

	public void setCompanyId(String companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getParentCategoryId();

	public void setParentCategoryId(String parentCategoryId);

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

	public Date getLastPostDate();

	public void setLastPostDate(Date lastPostDate);
}