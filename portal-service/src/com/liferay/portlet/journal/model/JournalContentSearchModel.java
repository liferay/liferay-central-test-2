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

import com.liferay.portal.model.BaseModel;

import com.liferay.portlet.journal.service.persistence.JournalContentSearchPK;

/**
 * <a href="JournalContentSearchModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>JournalContentSearch</code>
 * table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.model.JournalContentSearch
 * @see com.liferay.portlet.journal.service.model.impl.JournalContentSearchImpl
 * @see com.liferay.portlet.journal.service.model.impl.JournalContentSearchModelImpl
 *
 */
public interface JournalContentSearchModel extends BaseModel {
	public JournalContentSearchPK getPrimaryKey();

	public void setPrimaryKey(JournalContentSearchPK pk);

	public String getPortletId();

	public void setPortletId(String portletId);

	public String getLayoutId();

	public void setLayoutId(String layoutId);

	public String getOwnerId();

	public void setOwnerId(String ownerId);

	public String getArticleId();

	public void setArticleId(String articleId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getGroupId();

	public void setGroupId(long groupId);
}