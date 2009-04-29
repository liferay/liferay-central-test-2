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
 * <a href="AddressUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddressUtil {
	public static void cacheResult(com.liferay.portal.model.Address address) {
		getPersistence().cacheResult(address);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Address> addresses) {
		getPersistence().cacheResult(addresses);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.Address create(long addressId) {
		return getPersistence().create(addressId);
	}

	public static com.liferay.portal.model.Address remove(long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(addressId);
	}

	public static com.liferay.portal.model.Address remove(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(address);
	}

	/**
	 * @deprecated Use <code>update(Address address, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(address);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        address the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when address is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.Address update(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(address, merge);
	}

	public static com.liferay.portal.model.Address updateImpl(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(address, merge);
	}

	public static com.liferay.portal.model.Address findByPrimaryKey(
		long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(addressId);
	}

	public static com.liferay.portal.model.Address fetchByPrimaryKey(
		long addressId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(addressId);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Address findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Address findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Address[] findByCompanyId_PrevAndNext(
		long addressId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(addressId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.Address findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Address findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Address[] findByUserId_PrevAndNext(
		long addressId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(addressId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(companyId, classNameId, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(companyId, classNameId, obc);
	}

	public static com.liferay.portal.model.Address[] findByC_C_PrevAndNext(
		long addressId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_PrevAndNext(addressId, companyId, classNameId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_First(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_Last(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Address[] findByC_C_C_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_PrevAndNext(addressId, companyId, classNameId,
			classPK, obc);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M(companyId, classNameId, classPK, mailing);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M(companyId, classNameId, classPK, mailing,
			start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M(companyId, classNameId, classPK, mailing,
			start, end, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_M_First(
		long companyId, long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M_First(companyId, classNameId, classPK,
			mailing, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_M_Last(
		long companyId, long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M_Last(companyId, classNameId, classPK,
			mailing, obc);
	}

	public static com.liferay.portal.model.Address[] findByC_C_C_M_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean mailing, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_M_PrevAndNext(addressId, companyId,
			classNameId, classPK, mailing, obc);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary,
			start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary,
			start, end, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_P_First(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_First(companyId, classNameId, classPK,
			primary, obc);
	}

	public static com.liferay.portal.model.Address findByC_C_C_P_Last(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_Last(companyId, classNameId, classPK,
			primary, obc);
	}

	public static com.liferay.portal.model.Address[] findByC_C_C_P_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean primary, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_PrevAndNext(addressId, companyId,
			classNameId, classPK, primary, obc);
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

	public static java.util.List<com.liferay.portal.model.Address> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Address> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	public static void removeByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, classNameId, classPK);
	}

	public static void removeByC_C_C_M(long companyId, long classNameId,
		long classPK, boolean mailing)
		throws com.liferay.portal.SystemException {
		getPersistence()
			.removeByC_C_C_M(companyId, classNameId, classPK, mailing);
	}

	public static void removeByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		getPersistence()
			.removeByC_C_C_P(companyId, classNameId, classPK, primary);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(companyId, classNameId);
	}

	public static int countByC_C_C(long companyId, long classNameId,
		long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, classNameId, classPK);
	}

	public static int countByC_C_C_M(long companyId, long classNameId,
		long classPK, boolean mailing)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByC_C_C_M(companyId, classNameId, classPK, mailing);
	}

	public static int countByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByC_C_C_P(companyId, classNameId, classPK, primary);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AddressPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(AddressPersistence persistence) {
		_persistence = persistence;
	}

	private static AddressPersistence _persistence;
}