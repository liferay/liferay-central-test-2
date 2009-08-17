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
 * <a href="CountryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryPersistenceImpl
 * @see       CountryUtil
 * @generated
 */
public interface CountryPersistence extends BasePersistence {
	public void cacheResult(com.liferay.portal.model.Country country);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Country> countries);

	public void clearCache();

	public com.liferay.portal.model.Country create(long countryId);

	public com.liferay.portal.model.Country remove(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country remove(
		com.liferay.portal.model.Country country)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use {@link #update(Country, boolean merge)}.
	 */
	public com.liferay.portal.model.Country update(
		com.liferay.portal.model.Country country)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  country the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when country is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public com.liferay.portal.model.Country update(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country updateImpl(
		com.liferay.portal.model.Country country, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByPrimaryKey(long countryId)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByPrimaryKey(long countryId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByName(java.lang.String name,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByA2(java.lang.String a2,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country fetchByA3(java.lang.String a3,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByActive_First(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country findByActive_Last(boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Country[] findByActive_PrevAndNext(
		long countryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Country> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public void removeByA2(java.lang.String a2)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public void removeByA3(java.lang.String a3)
		throws com.liferay.portal.NoSuchCountryException,
			com.liferay.portal.SystemException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException;

	public int countByA2(java.lang.String a2)
		throws com.liferay.portal.SystemException;

	public int countByA3(java.lang.String a3)
		throws com.liferay.portal.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}