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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Company;

/**
 * <a href="CompanyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyPersistenceImpl
 * @see       CompanyUtil
 * @generated
 */
public interface CompanyPersistence extends BasePersistence<Company> {
	public void cacheResult(com.liferay.portal.model.Company company);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Company> companies);

	public com.liferay.portal.model.Company create(long companyId);

	public com.liferay.portal.model.Company remove(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company updateImpl(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByPrimaryKey(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByPrimaryKey(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByLogoId(long logoId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findBySystem_First(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findBySystem_Last(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company[] findBySystem_PrevAndNext(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}