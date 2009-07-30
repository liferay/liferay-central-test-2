/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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


/**
 * <a href="CompanyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    CompanyPersistence
 * @see    CompanyPersistenceImpl
 * @generated
 */
public class CompanyUtil {
	public static void cacheResult(com.liferay.portal.model.Company company) {
		getPersistence().cacheResult(company);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Company> companies) {
		getPersistence().cacheResult(companies);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.Company create(long companyId) {
		return getPersistence().create(companyId);
	}

	public static com.liferay.portal.model.Company remove(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(companyId);
	}

	public static com.liferay.portal.model.Company remove(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(company);
	}

	/**
	 * @deprecated Use {@link #update(Company, boolean merge)}.
	 */
	public static com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(company);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  company the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when company is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public static com.liferay.portal.model.Company update(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(company, merge);
	}

	public static com.liferay.portal.model.Company updateImpl(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(company, merge);
	}

	public static com.liferay.portal.model.Company findByPrimaryKey(
		long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company fetchByPrimaryKey(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company findByWebId(
		java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findByWebId(webId);
	}

	public static com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByWebId(webId);
	}

	public static com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByWebId(webId, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByVirtualHost(virtualHost, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findByMx(mx);
	}

	public static com.liferay.portal.model.Company fetchByMx(
		java.lang.String mx) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByMx(mx);
	}

	public static com.liferay.portal.model.Company fetchByMx(
		java.lang.String mx, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByMx(mx, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company fetchByLogoId(long logoId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company fetchByLogoId(long logoId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByLogoId(logoId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system) throws com.liferay.portal.SystemException {
		return getPersistence().findBySystem(system);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySystem(system, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySystem(system, start, end, obc);
	}

	public static com.liferay.portal.model.Company findBySystem_First(
		boolean system, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySystem_First(system, obc);
	}

	public static com.liferay.portal.model.Company findBySystem_Last(
		boolean system, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySystem_Last(system, obc);
	}

	public static com.liferay.portal.model.Company[] findBySystem_PrevAndNext(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		return getPersistence().findBySystem_PrevAndNext(companyId, system, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		getPersistence().removeByWebId(webId);
	}

	public static void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		getPersistence().removeByVirtualHost(virtualHost);
	}

	public static void removeByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		getPersistence().removeByMx(mx);
	}

	public static void removeByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.SystemException {
		getPersistence().removeByLogoId(logoId);
	}

	public static void removeBySystem(boolean system)
		throws com.liferay.portal.SystemException {
		getPersistence().removeBySystem(system);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByWebId(java.lang.String webId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByWebId(webId);
	}

	public static int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVirtualHost(virtualHost);
	}

	public static int countByMx(java.lang.String mx)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByMx(mx);
	}

	public static int countByLogoId(long logoId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLogoId(logoId);
	}

	public static int countBySystem(boolean system)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySystem(system);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static CompanyPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(CompanyPersistence persistence) {
		_persistence = persistence;
	}

	private static CompanyPersistence _persistence;
}