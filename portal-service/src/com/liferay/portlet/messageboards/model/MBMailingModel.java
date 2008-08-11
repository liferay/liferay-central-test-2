/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="MBMailingModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the <code>MBMailing</code>
 * table in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.model.MBMailing
 * @see com.liferay.portlet.messageboards.service.model.impl.MBMailingImpl
 * @see com.liferay.portlet.messageboards.service.model.impl.MBMailingModelImpl
 *
 */
public interface MBMailingModel extends BaseModel {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public String getUuid();

	public void setUuid(String uuid);

	public long getMailingId();

	public void setMailingId(long mailingId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public long getCategoryId();

	public void setCategoryId(long categoryId);

	public String getMailingListAddress();

	public void setMailingListAddress(String mailingListAddress);

	public String getMailAddress();

	public void setMailAddress(String mailAddress);

	public String getMailInProtocol();

	public void setMailInProtocol(String mailInProtocol);

	public String getMailInServerName();

	public void setMailInServerName(String mailInServerName);

	public boolean getMailInUseSSL();

	public boolean isMailInUseSSL();

	public void setMailInUseSSL(boolean mailInUseSSL);

	public int getMailInServerPort();

	public void setMailInServerPort(int mailInServerPort);

	public String getMailInUserName();

	public void setMailInUserName(String mailInUserName);

	public String getMailInPassword();

	public void setMailInPassword(String mailInPassword);

	public int getMailInReadInterval();

	public void setMailInReadInterval(int mailInReadInterval);

	public boolean getMailOutConfigured();

	public boolean isMailOutConfigured();

	public void setMailOutConfigured(boolean mailOutConfigured);

	public String getMailOutServerName();

	public void setMailOutServerName(String mailOutServerName);

	public boolean getMailOutUseSSL();

	public boolean isMailOutUseSSL();

	public void setMailOutUseSSL(boolean mailOutUseSSL);

	public int getMailOutServerPort();

	public void setMailOutServerPort(int mailOutServerPort);

	public String getMailOutUserName();

	public void setMailOutUserName(String mailOutUserName);

	public String getMailOutPassword();

	public void setMailOutPassword(String mailOutPassword);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public MBMailing toEscapedModel();
}