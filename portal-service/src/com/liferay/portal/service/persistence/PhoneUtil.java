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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.Phone;

import java.util.List;

/**
 * <a href="PhoneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PhonePersistence
 * @see       PhonePersistenceImpl
 * @generated
 */
public class PhoneUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Phone remove(Phone phone) throws SystemException {
		return getPersistence().remove(phone);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Phone update(Phone phone, boolean merge)
		throws SystemException {
		return getPersistence().update(phone, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Phone phone) {
		getPersistence().cacheResult(phone);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Phone> phones) {
		getPersistence().cacheResult(phones);
	}

	public static com.liferay.portal.model.Phone create(long phoneId) {
		return getPersistence().create(phoneId);
	}

	public static com.liferay.portal.model.Phone remove(long phoneId)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(phoneId);
	}

	public static com.liferay.portal.model.Phone updateImpl(
		com.liferay.portal.model.Phone phone, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(phone, merge);
	}

	public static com.liferay.portal.model.Phone findByPrimaryKey(long phoneId)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(phoneId);
	}

	public static com.liferay.portal.model.Phone fetchByPrimaryKey(long phoneId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(phoneId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Phone findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Phone findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Phone[] findByCompanyId_PrevAndNext(
		long phoneId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(phoneId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.Phone findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Phone findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Phone[] findByUserId_PrevAndNext(
		long phoneId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(phoneId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(companyId, classNameId, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(companyId, classNameId, obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_PrevAndNext(
		long phoneId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_PrevAndNext(phoneId, companyId, classNameId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, classNameId, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C(companyId, classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_First(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_First(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_Last(
		long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_Last(companyId, classNameId, classPK, obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_C_PrevAndNext(
		long phoneId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_PrevAndNext(phoneId, companyId, classNameId,
			classPK, obc);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary,
			start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P(companyId, classNameId, classPK, primary,
			start, end, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_P_First(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_First(companyId, classNameId, classPK,
			primary, obc);
	}

	public static com.liferay.portal.model.Phone findByC_C_C_P_Last(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_Last(companyId, classNameId, classPK,
			primary, obc);
	}

	public static com.liferay.portal.model.Phone[] findByC_C_C_P_PrevAndNext(
		long phoneId, long companyId, long classNameId, long classPK,
		boolean primary, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPhoneException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_C_P_PrevAndNext(phoneId, companyId, classNameId,
			classPK, primary, obc);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Phone> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Phone> findAll(
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

	public static int countByC_C_C_P(long companyId, long classNameId,
		long classPK, boolean primary)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByC_C_C_P(companyId, classNameId, classPK, primary);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static PhonePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PhonePersistence)PortalBeanLocatorUtil.locate(PhonePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PhonePersistence persistence) {
		_persistence = persistence;
	}

	private static PhonePersistence _persistence;
}