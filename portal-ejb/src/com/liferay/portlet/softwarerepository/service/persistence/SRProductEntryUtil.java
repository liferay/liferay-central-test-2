/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="SRProductEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRProductEntryUtil {
	public static com.liferay.portlet.softwarerepository.model.SRProductEntry create(
		long productEntryId) {
		return getPersistence().create(productEntryId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry remove(
		long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(productEntryId));
		}

		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry =
			getPersistence().remove(productEntryId);

		if (listener != null) {
			listener.onAfterRemove(srProductEntry);
		}

		return srProductEntry;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry remove(
		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(srProductEntry);
		}

		srProductEntry = getPersistence().remove(srProductEntry);

		if (listener != null) {
			listener.onAfterRemove(srProductEntry);
		}

		return srProductEntry;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry update(
		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srProductEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srProductEntry);
			}
			else {
				listener.onBeforeUpdate(srProductEntry);
			}
		}

		srProductEntry = getPersistence().update(srProductEntry);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srProductEntry);
			}
			else {
				listener.onAfterUpdate(srProductEntry);
			}
		}

		return srProductEntry;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry update(
		com.liferay.portlet.softwarerepository.model.SRProductEntry srProductEntry,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = srProductEntry.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(srProductEntry);
			}
			else {
				listener.onBeforeUpdate(srProductEntry);
			}
		}

		srProductEntry = getPersistence().update(srProductEntry, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(srProductEntry);
			}
			else {
				listener.onAfterUpdate(srProductEntry);
			}
		}

		return srProductEntry;
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByPrimaryKey(
		long productEntryId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByPrimaryKey(productEntryId);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry fetchByPrimaryKey(
		long productEntryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(productEntryId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByGroupId_First(
		java.lang.String groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry[] findByGroupId_PrevAndNext(
		long productEntryId, java.lang.String groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByGroupId_PrevAndNext(productEntryId,
			groupId, obc);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry[] findByCompanyId_PrevAndNext(
		long productEntryId, java.lang.String companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByCompanyId_PrevAndNext(productEntryId,
			companyId, obc);
	}

	public static java.util.List findByG_U(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List findByG_U(java.lang.String groupId,
		java.lang.String userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end);
	}

	public static java.util.List findByG_U(java.lang.String groupId,
		java.lang.String userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, begin, end, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByG_U_First(
		java.lang.String groupId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry findByG_U_Last(
		java.lang.String groupId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.softwarerepository.model.SRProductEntry[] findByG_U_PrevAndNext(
		long productEntryId, java.lang.String groupId, java.lang.String userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().findByG_U_PrevAndNext(productEntryId, groupId,
			userId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_U(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_U(java.lang.String groupId,
		java.lang.String userId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List getSRLicenses(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().getSRLicenses(pk);
	}

	public static java.util.List getSRLicenses(long pk, int begin, int end)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().getSRLicenses(pk, begin, end);
	}

	public static java.util.List getSRLicenses(long pk, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		return getPersistence().getSRLicenses(pk, begin, end, obc);
	}

	public static int getSRLicensesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getSRLicensesSize(pk);
	}

	public static boolean containsSRLicense(long pk, long srLicensePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSRLicense(pk, srLicensePK);
	}

	public static boolean containsSRLicenses(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSRLicenses(pk);
	}

	public static void addSRLicense(long pk, long srLicensePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().addSRLicense(pk, srLicensePK);
	}

	public static void addSRLicense(long pk,
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().addSRLicense(pk, srLicense);
	}

	public static void addSRLicenses(long pk, long[] srLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().addSRLicenses(pk, srLicensePKs);
	}

	public static void addSRLicenses(long pk, java.util.List srLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().addSRLicenses(pk, srLicenses);
	}

	public static void clearSRLicenses(long pk)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException {
		getPersistence().clearSRLicenses(pk);
	}

	public static void removeSRLicense(long pk, long srLicensePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().removeSRLicense(pk, srLicensePK);
	}

	public static void removeSRLicense(long pk,
		com.liferay.portlet.softwarerepository.model.SRLicense srLicense)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().removeSRLicense(pk, srLicense);
	}

	public static void removeSRLicenses(long pk, long[] srLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().removeSRLicenses(pk, srLicensePKs);
	}

	public static void removeSRLicenses(long pk, java.util.List srLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().removeSRLicenses(pk, srLicenses);
	}

	public static void setSRLicenses(long pk, long[] srLicensePKs)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().setSRLicenses(pk, srLicensePKs);
	}

	public static void setSRLicenses(long pk, java.util.List srLicenses)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.softwarerepository.NoSuchProductEntryException, 
			com.liferay.portlet.softwarerepository.NoSuchLicenseException {
		getPersistence().setSRLicenses(pk, srLicenses);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static SRProductEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SRProductEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static SRProductEntryUtil _getUtil() {
		if (_util == null) {
			_util = (SRProductEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = SRProductEntryUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.softwarerepository.model.SRProductEntry"));
	private static Log _log = LogFactory.getLog(SRProductEntryUtil.class);
	private static SRProductEntryUtil _util;
	private SRProductEntryPersistence _persistence;
}