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

package com.liferay.portal.model;


/**
 * <a href="Company.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Company table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.CompanyImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyModel
 * @see       com.liferay.portal.model.impl.CompanyImpl
 * @see       com.liferay.portal.model.impl.CompanyModelImpl
 * @generated
 */
public interface Company extends CompanyModel {
	public int compareTo(com.liferay.portal.model.Company company);

	public com.liferay.portal.model.Account getAccount();

	public java.lang.String getAdminName();

	public java.lang.String getAuthType()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.User getDefaultUser();

	public java.lang.String getDefaultWebId();

	public java.lang.String getEmailAddress();

	public com.liferay.portal.model.Group getGroup();

	public java.security.Key getKeyObj();

	public java.util.Locale getLocale();

	public java.lang.String getName();

	public java.lang.String getShardName();

	public java.lang.String getShortName();

	public java.util.TimeZone getTimeZone();

	public boolean hasCompanyMx(java.lang.String emailAddress);

	public boolean isAutoLogin()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isCommunityLogo()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isSendPassword()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangers()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersVerify()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersWithMx()
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setKey(java.lang.String key);

	public void setKeyObj(java.security.Key keyObj);
}